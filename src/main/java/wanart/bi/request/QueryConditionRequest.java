package wanart.bi.request;

import wanart.bi.common.ConditionCombineEnum;
import wanart.bi.common.ConditionOperatorEnum;

public class QueryConditionRequest {
    private String columnName;
    private String conditionOperator;
    private String conditionParam;
    private String combineType;

    public String getColumnName(){
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getConditionOperator(){
        return conditionOperator;
    }

    public void setConditionOperator(String conditionOperator) {
        this.conditionOperator = conditionOperator;
    }

    public String getConditionParam() {
        return conditionParam;
    }

    public void setConditionParam(String conditionParam) {
        this.conditionParam = conditionParam;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }

    public ConditionOperatorEnum getOperatorEnum(){
        ConditionOperatorEnum result;
        try{
            result = ConditionOperatorEnum.valueOf(conditionOperator.toUpperCase());
        }
        catch (Exception e){
           return ConditionOperatorEnum.INVALID_OPERATOR;
        }
        return result;
    }
    public ConditionCombineEnum getCombineEnum(){
       return ConditionCombineEnum.parseCombineToEnum(combineType);
    }

}
