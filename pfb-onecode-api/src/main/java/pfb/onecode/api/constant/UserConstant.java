package pfb.onecode.api.constant;

/**
 * ${DESCRIPTION}
 *
 * @author admin
 * @create 2017-06-14 8:36
 */
public class UserConstant {
    public static int PW_ENCORDER_SALT = 12;


    /**
     * 用户状态：1正常
     */
    public static String USER_STAT_NORMAL="1";
    /**
     * 用户状态：2冻结
     */
    public static String USER_STAT_LOCKED="2";
    /**
     * 用户认证状态：1认证
     */
    public static String USER_CERT_FLAG1="1";
    /**
     * 用户认证状态：2未认证
     */
    public static String USER_CERT_FLAG2="2";
    /**
     * 用户绑卡状态：1已绑定
     */
    public static String USER_BIND_STATUS="1";
    /**
     * 用户绑卡状态：2已解除
     */
    public static String USER_U_BIND_STATUS="2";
    /**
     * 鉴权成功 0000
     */
    public static String USER_AUTH_SUCC="0000";
}
