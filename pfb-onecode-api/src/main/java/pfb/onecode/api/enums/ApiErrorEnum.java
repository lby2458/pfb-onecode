package pfb.onecode.api.enums;

/**
 * Created by jdy on 2017/12/18.
 */
public enum ApiErrorEnum {

    Error_0000("0000", "非法手机号"),
    Error_1000("1000", "手机号码不能为空"),
    Error_1001("1001", "用户信息不存在"),
    Error_1002("1002", "手机号码已注册"),
    Error_1003("1003", "短信发送失败"),
    Error_1004("1004", "验证码输入错误"),
    Error_1005("1005", "修改密码失败"),
    Error_1006("1006", "实名认证失败"),
    Error_1007("1007", "用户已实名认证"),
    Error_1008("1008", "银行卡绑定失败"),
    Error_1009("1009", "银行卡已绑定"),
    Error_1010("1010", "密码不能为空"),
    Error_1011("1011", "短信验证码不能为空"),
    Error_1012("1012", "注册渠道不能为空"),
    Error_1013("1013", "注册平台不能为空"),
    Error_1014("1014", "用户注册失败"),
    Error_1015("1015", "短信验证码失效"),
    Error_1016("1016", "短信验证码错误"),
    Error_1017("1017", "用户密码错误"),
    Error_1018("1018", "此标志只能是1或是2"),
    Error_1019("1019", "未实名认证"),
    Error_1020("1020", "未绑定银行卡"),
    Error_1021("1021", "图形验证码不能为空"),
    Error_1022("1022", "图形验证码错误"),
    Error_1023("1023", "图形验证码失效"),
    Error_1024("1024", "身份证号不能为空"),
    Error_1025("1025", "身份证号与实名认证时的身份证号不一致"),
    Error_1026("1026", "该手机号未注册"),
    Error_1027("1027", " 请重新登陆"),
    Error_1028("1028", " 密码错误3次，将锁定账户，1小时后才可继续操作"),
    Error_1029("1029", " 用户已锁定，1小时后才可继续操作"),
    Error_1030("1030", "预留手机号不能为空"),
    Error_1031("1031", "用户姓名不能为空"),
    Error_1032("1032", "证件号码不能为空"),
    Error_1033("1033", "支付密码不能为空"),
    Error_1034("1034", "银行卡号不能为空"),
    Error_1035("1035", "风险评测失败"),
    Error_1036("1036", "token为空"),
    Error_1037("1037", "用户姓名非法"),
    Error_1038("1038", "银行卡号格式错误"),
    Error_1039("1039", "证件号码格式错误"),
    Error_1040("1040", "登录类型不能为空"),
    Error_1041("1041", "支付密码格式错误"),





    Error_2001("2001", "该订单正在支付中，请稍后再试！"),
    Error_2002("2002", "提现申请发起失败,请稍后再试!"),
    Error_2003("2003", "查询用户余额或银行信息失败,请稍后再试!"),

    Error_8001("8001", "支付密码错误"),
    Error_8002("8002", "产品已售罄"),
    Error_8003("8003", "下单失败"),
    Error_8004("8004", "已有待支付订单"),
    Error_8005("8005", "产品额度不足"),
    Error_8006("8006", "修改项目信息失败"),
    Error_8007("8007", "参数为空"),
    Error_8008("8008", "全局错误"),
    Error_8009("8009", "账户扣款失败"),
    Error_8010("8010", "非招标中产品不能下单"),

    Error_8101("8101", "该产品不支持提前赎回"),
    Error_8102("8102", "提前赎回申请失败"),
    Error_8103("8103", "锁定期间无法提前赎回"),

    Error_8201("8201", "调用第三方接口出错"),

    Error_9112("9112","申请充值失败。"),
    Error_9113("9113","发送短信失败。"),
    Error_9114("9114","二维码生成失败。");


    public String key;
    public String value;

    private ApiErrorEnum(String key, String value) {
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


    public static ApiErrorEnum get(String key) {
        ApiErrorEnum[] values = ApiErrorEnum.values();
        for (ApiErrorEnum object : values) {
            if (object.key.equals(key)) {
                return object;
            }
        }
        return null;
    }

}
