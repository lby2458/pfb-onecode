package pfb.onecode.api.service.user;


import org.springframework.web.bind.annotation.RequestHeader;

import pfb.onecode.api.common.Request;
import pfb.onecode.api.common.Response;
import pfb.onecode.api.entity.user.UserInfo;

/**
 * Created by jdy on 2017/12/13.
 * 用户接口
 */
public interface UserInfoService {


    /**
     * 查询手机号是否已注册
     * @param mobile
     * @return boolean
     */
    Response registerIdentifyCheck(String mobile,String flag);
    /**
     * 用户注册
     * @param entity
     * @return boolean
     */
    public Response register(UserInfo entity);
    /**
     * 用户登陆
     * @param entity
     * @return boolean
     */
    public Response login(UserInfo entity) throws Exception;
    /**
     * 用户实名认证
     * @param
     * @return boolean
     */
   // public Response cert(Request<CertVO> request, Response response)throws Exception;
    /**
     * 交易密码重置
     * @param
     * @return boolean
     */
    public Response resetTradePwd(Request<UserInfo> request, Response response) throws Exception;
    /**
     * 登陆密码重置
     * @param
     * @return boolean
     */
    public Response resetLoginPwd(Request<UserInfo> request, Response response) throws Exception;
    /**
     * 获取用户信息
     * @param
     * @return boolean
     */
    public Response getUserInfo(Request<UserInfo> request, Response response) throws Exception;

    /**
     * 获取用户信息(通过手机号)
     * @param userId
     * @return
     */
    public UserInfo getUserInfo(String userId);
    /**
     * 获取用户绑卡信息
     * @param
     * @return boolean
     */
    //public BindCardVO getBindCardInfo(String userId);
    /**
     * 用户退出
     * @param
     * @return boolean
     */
    public Response logout(@RequestHeader("access-token") String token);

    /**
     * 验证交易密码是否正确
     * @param userInfo
     * @return
     */
    public boolean checkTradePwd(UserInfo userInfo);
}
