package wanart.bi.common;

public enum ConditionParamTypeEnum {
    INT,
    VARCHAR,
    TIMESTAMP,
    INVALID_PARAM_TYPE;

    public static ConditionParamTypeEnum parseParamToEnum(String param){
        switch (param){
            case "int":
                return INT;
            case "varchar":
                return VARCHAR;
            case "timestamp":
                return TIMESTAMP;
            default:
                return INVALID_PARAM_TYPE;
        }
    }
}
