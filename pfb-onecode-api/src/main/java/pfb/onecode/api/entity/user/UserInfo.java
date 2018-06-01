package pfb.onecode.api.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * data 提供getter、setter方法
 * NoArgsConstructor 提供无参构造器
 * AllArgsConstructor 提供有参构造器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user_reg_info")
public class UserInfo {
    @Id
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 登陆密码
     */
    @Column(name = "login_pwd")
    private String loginPwd;
    /**
     * 交易密码
     */
    @Column(name = "trade_pwd")
    private String tradePwd;
    /**
     * 注册时间
     */
    @Column(name = "reg_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date regTime;
    /**
     * 用户状态 1:正常，2:冻结，默认1
     */
    @Column(name = "user_stat")
    private String userStat;
    /**
     * 风险类型；00:保守型,01:稳健型,02:平衡型,03:成长型,04:进取型
     */
    @Column(name = "risk_type")
    private String riskType;
    /**
     * 用户姓名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 证件类型：01:身份证
     */
    @Column(name = "cert_type")
    private String certType;
    /**
     * 证件号码
     */
    @Column(name = "cert_no")
    private String certNo;
    /**
     * 用户昵称
     */
    @Column(name = "user_nick_name")
    private String userNickName;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 注册渠道；如360
     */
    @Column(name = "reg_chnl_id")
    private String regChnlId;
    /**
     * 注册平台；Pc:p,android:a,ios:i
     */
    @Column(name = "reg_platform")
    private String regPlatform;
    /**
     * 认证标志；1:认证，2:未认证
     */
    @Column(name = "cert_flag")
    private String certFlag;
    /**
     * 是否是超级账户，1:是，0:否
     */
    @Column(name = "is_super")
    private String isSuper;

    private String remark1;

    private String remark2;

    private String remark3;
    /**
     * 短信验证码
     */
    @Transient
    private String smsCode;
    /**
     * 图形验证码
     */
    @Transient
    private String imgCode;
    /**
     * 标志-登陆是否有图形验证码，1：有，2无
     */
    @Transient
    private String isFlag;
    /**
     * token
     */
    @Transient
    private String token;
    /**
     * 登陆类型1:账户登陆，2:手机验证码登陆
     */
    @Transient
    private String loginType;
    /**
     * 银行卡号
     */
    @Transient
    private String bankCardNo;
    /**
     * 银行名称
     */
    @Transient
    private String bankName;

    @Transient
    private String amount;


}