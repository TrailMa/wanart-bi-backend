package wanart.bi.util;

import org.springframework.util.StringUtils;
import wanart.bi.common.ConditionCombineEnum;
import wanart.bi.common.ConditionOperatorEnum;
import wanart.bi.common.ConditionParamTypeEnum;
import wanart.bi.dao.event.EventDao;
import wanart.bi.entity.event.EventColumnEntity;
import wanart.bi.request.QueryConditionRequest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SqlParamUtil {

    public static String parseSeparationTime(String tableName, String separationTime, String timeColumn){
        switch (separationTime.trim()){
            case "hour":
                return String.format("DATE_FORMAT(%s, ", timeColumn) + "'%Y-%m-%d %H:00:00')";
            case "minute":
                return String.format("DATE_FORMAT(%s, ", timeColumn) + "'%Y-%m-%d %H:%i:00')";
            default:
                String[] splits = tableName.split("_") ;
                // 默认按天筛选
                // tableName_year_month_date
                if(splits.length == 4) {
                    return String.format("\"%s-%s-%s 00:00:00\"", splits[1], splits[2], splits[3]);
                }
                return String.format("DATE_FORMAT(%s, ", timeColumn) + "'%Y-%m-%d 00:00:00')";
        }
    }

    public static String parseSeparationTime(String tableName, String separationTime, List<EventColumnEntity> columnList){

        String defaultTime;
        // tableName_year_month_date
        String[] splits = tableName.split("_") ;
        if(splits.length == 4) {
            defaultTime = String.format("\"%s-%s-%s 00:00:00\"", splits[1], splits[2], splits[3]);
        }else{
            defaultTime = TimeUtil.getTimeNowString();
        }

        String defaultSeparationStr = String.format("%s as time", defaultTime);

        String timeColumn = "";
        // 每个event事件只有一个timestamp列， 标识事件发生的时间
        for(EventColumnEntity columnEntity : columnList){
            if(ConditionParamTypeEnum.parseParamToEnum(columnEntity.getColumnType()) == ConditionParamTypeEnum.TIMESTAMP){
                timeColumn = columnEntity.getColumnName();
            }
        }

        if(timeColumn.equals("")){
            return defaultSeparationStr;
        }
        switch (separationTime.trim()){
            case "hour":
                return String.format("DATE_FORMAT(`%s`, ", timeColumn) + "'%Y-%m-%d %H:00:00') as time";
            case "minute":
                return String.format("DATE_FORMAT(`%s`, ", timeColumn) + "'%Y-%m-%d %H:%i:00') as time";
            default:
                // 默认按天筛选
                return defaultSeparationStr;
        }
    }

    public static String parseCondition(String condition){
        if(condition == null || condition.equals( "null") || condition.equals("") || condition.equals("true") || condition.contains(";") || condition.contains("\\")) return "";
        condition = condition.trim().toLowerCase();
        return "where " + condition.trim();
    }

    public static ArrayList<String> parseTimeSpan(String startStr, String endStr){
        ArrayList<String> timeList = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startStr, df);
        LocalDateTime end = LocalDateTime.parse(endStr, df);

        long startSeconds = start.atZone(ZoneOffset.UTC).toEpochSecond();
        long endSeconds = end.atZone(ZoneOffset.UTC).toEpochSecond();
        if(startSeconds >= endSeconds){
            return timeList;
        }

        while(true){
            String time = String.format("%s_%s_%s", start.getYear(), start.getMonthValue(), start.getDayOfMonth());
            timeList.add(time);
            start = start.plusDays(1);
            startSeconds = start.atZone(ZoneOffset.UTC).toEpochSecond();
            if(start.getDayOfYear() != end.getDayOfYear() && startSeconds > endSeconds){
               break;
            }
        }
        return timeList;
    }


    // 根据startTimeSuffix 计算step天的 tiemSuffix 2019_12_20 step=2 [2019_12_20, 2019_12_21]
    public static ArrayList<String> calcTimeSuffix(String startTimeSuffix, int step){
        ArrayList<String> timeList = new ArrayList<>();
        if(step < 1) {
            return  timeList;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTimeSuffix, df);
        for(int i=0; i<step; i++){
            LocalDateTime dayTime = start.plusDays(i);
            String timeSuffix = String.format("%s_%s_%s", dayTime.getYear(), dayTime.getMonthValue(), dayTime.getDayOfMonth());
            timeList.add(timeSuffix);
        }
        return timeList;
    }

    public static String getDayTimeFromTableNameSuffix(String suffix){

        return suffix.replace("_", "-") + " 00:00:00";
    }
    public static String getTimeSuffixFromTime(String time){
        //time string format yyyy-MM-dd HH:mm:ss
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(time == null) {
            return "";
        }
        String suffixArr[] = time.split(" ");
        if(suffixArr.length < 2){
            return "";
        }
        // yyyy-MM-dd => yyyy_MM_dd
        return suffixArr[0].replace("-", "_");
    }

    public static String getTableName(String eventName, String timeSuffix){
        return String.format("%s_%s", eventName, timeSuffix);
    }

    public static String parseCondition(List<QueryConditionRequest> conditionList, List<EventColumnEntity> columnList){
        String conditionStr = "";
        if(conditionList == null || conditionList.size() == 0){
            return conditionStr;
        }

        String badCondition = "where false";
        // 未找到该事件
        if(columnList == null){
            return badCondition;
        }

        for(int i=0; i<conditionList.size(); i++){
            if(i == 0){
                conditionStr += "where ";
            }
            QueryConditionRequest condition = conditionList.get(i);

            // 检查是否有该列
            ConditionParamTypeEnum paramTypeEnum = ConditionParamTypeEnum.INVALID_PARAM_TYPE;
            for(EventColumnEntity columnEntity : columnList){
                if(columnEntity.getColumnName().equals(condition.getColumnName())){
                    paramTypeEnum = ConditionParamTypeEnum.parseParamToEnum(columnEntity.getColumnType());
                    break;
                }
            }
            // 未找到该列
            if(paramTypeEnum == ConditionParamTypeEnum.INVALID_PARAM_TYPE){
                return  badCondition;
            }

            // 检查参数类型 是否支持 操作符 的运算
            if(!QueryConditionUtil.checkOperatorValid(paramTypeEnum, condition.getOperatorEnum())){
                return badCondition;
            }
            // 不是IN操作 需要检查操作符与操作数是否合法
            if(condition.getOperatorEnum() != ConditionOperatorEnum.IN ){
                if(!QueryConditionUtil.checkParamValid(paramTypeEnum, condition.getConditionParam())){
                    return badCondition;
                }
            }

            // 检查通过 拼接sql
            conditionStr += QueryConditionUtil.toSqlString(condition.getColumnName(), condition.getOperatorEnum(), condition.getConditionParam());

            // 连接后续条件
            if(i != conditionList.size() -1) {
               if(condition.getCombineEnum() != ConditionCombineEnum.NONE) {
                   conditionStr += " " + condition.getCombineType() + " ";
               }
               else{
                   return conditionStr;
               }
            }

        }
        return conditionStr;
    }

    public static boolean checkColumnExist(String columnName, List<EventColumnEntity> columnList){
        for(EventColumnEntity columnEntity : columnList) {
            if (columnEntity.getColumnName().equals(columnName)){
                return true;
            }
        }
        return false;
    }

    public static String parseGroup(String groupName){
        if(StringUtils.isEmpty(groupName)){
            return "";
        }
       return String.format("group by `%s`", groupName);
    }

    public static String getInUidsStr(){
        return "ids";
    }
}
