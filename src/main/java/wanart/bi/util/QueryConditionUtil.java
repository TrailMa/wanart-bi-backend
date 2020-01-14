package wanart.bi.util;

import wanart.bi.common.ConditionOperatorEnum;
import wanart.bi.common.ConditionParamTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;

class QueryConditionUtil {
    private static HashMap<ConditionParamTypeEnum, ArrayList<ConditionOperatorEnum>> conditionParamOperationList;
    static {
        conditionParamOperationList = new HashMap<>();

        // int类型可执行的条件判断运算符
        ArrayList<ConditionOperatorEnum> intTypeOperationList = new ArrayList<>();
        intTypeOperationList.add(ConditionOperatorEnum.EQUAL);
        intTypeOperationList.add(ConditionOperatorEnum.NOT_EQUAL);
        intTypeOperationList.add(ConditionOperatorEnum.GREATER);
        intTypeOperationList.add(ConditionOperatorEnum.LESS);
        intTypeOperationList.add(ConditionOperatorEnum.IN);
        conditionParamOperationList.put(ConditionParamTypeEnum.INT, intTypeOperationList);

        // 字符串
        ArrayList<ConditionOperatorEnum> varcharTypeOperationList = new ArrayList<>();
        varcharTypeOperationList.add(ConditionOperatorEnum.EQUAL);
        varcharTypeOperationList.add(ConditionOperatorEnum.NOT_EQUAL);
        varcharTypeOperationList.add(ConditionOperatorEnum.CONTAIN);
        varcharTypeOperationList.add(ConditionOperatorEnum.NOT_CONTAIN);
        varcharTypeOperationList.add(ConditionOperatorEnum.IN);
        conditionParamOperationList.put(ConditionParamTypeEnum.VARCHAR, intTypeOperationList);

        // 时间戳
        ArrayList<ConditionOperatorEnum> timestampTypeOperationList = new ArrayList<>();
        timestampTypeOperationList.add(ConditionOperatorEnum.EQUAL);
        timestampTypeOperationList.add(ConditionOperatorEnum.NOT_CONTAIN);
        timestampTypeOperationList.add(ConditionOperatorEnum.GREATER);
        timestampTypeOperationList.add(ConditionOperatorEnum.LESS);
        timestampTypeOperationList.add(ConditionOperatorEnum.IN);
        conditionParamOperationList.put(ConditionParamTypeEnum.TIMESTAMP, intTypeOperationList);

    }

    static boolean checkOperatorValid(ConditionParamTypeEnum paramTypeEnum, ConditionOperatorEnum operatorEnum){
        ArrayList<ConditionOperatorEnum> operatorList = conditionParamOperationList.get(paramTypeEnum);
        return operatorList != null && operatorList.contains(operatorEnum);
    }

    static boolean checkParamValid(ConditionParamTypeEnum paramTypeEnum, String param){
        if(param == null){
            return false;
        }
        try{
            switch (paramTypeEnum){
                case INT:
                    Integer.parseInt(param);
                    break;
                case VARCHAR:
                    if(param.contains(";") || param.contains("\\")){
                        return false;
                    }
                    break;
                case TIMESTAMP:
                    TimeUtil.validTimeFormat(param);
                    break;
                default:
                    return false;
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    static String toSqlString(String conditionColumn, ConditionOperatorEnum operatorEnum, String conditionParam){
        switch (operatorEnum){
            case EQUAL:
                return String.format("`%s`='%s'", conditionColumn, conditionParam);
            case NOT_EQUAL:
                return String.format("`%s`<>'%s'", conditionColumn, conditionParam);
            case GREATER:
                return String.format("`%s`>'%s'", conditionColumn, conditionParam);
            case LESS:
                return String.format("`%s`<'%s'", conditionColumn, conditionParam);
            case CONTAIN:
                return String.format("`%s` like %s'%s'%s", conditionColumn, "%", conditionParam, "%");
            case NOT_CONTAIN:
                return String.format("`%s` not like %s'%s'%s", conditionColumn, "%", conditionParam, "%");
            case IN:
                return String.format("%s in (:%s)", conditionColumn, conditionParam);
            default:
                return "";
        }
    }
    /*
    private static boolean checkConditionParamValid(ConditionParamTypeEnum paramTypeEnum){

    }
    */

    //private kkkkk
}
