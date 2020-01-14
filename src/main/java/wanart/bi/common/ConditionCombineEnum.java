package wanart.bi.common;

public enum  ConditionCombineEnum {
    AND,
    OR,
    NONE;

    public static ConditionCombineEnum parseCombineToEnum(String str){
        switch (str.toLowerCase()){
            case "and" :
                return AND;
            case "or":
                return OR;
            default:
                return NONE;
        }
    }
}
