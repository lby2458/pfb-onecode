package pfb.onecode.api.enums;

/**
 * Created by jdy on 2017/12/18.
 */
public enum CommonErrorEnum {

    Error_5000("5000", "系统内部错误"),
    Error_5001("5001", "OpenAPI-IllegalRequestParamException异常"),
    Error_5002("5002", "系统IllegalArgumentException异常"),
    Error_5003("5003", "请求接口失败，请稍后再试。");


    public String key;
    public String value;

    private CommonErrorEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public static CommonErrorEnum get(String key) {
        CommonErrorEnum[] values = CommonErrorEnum.values();
        for (CommonErrorEnum object : values) {
            if (object.key.equals(key)) {
                return object;
            }
        }
        return null;
    }

}
