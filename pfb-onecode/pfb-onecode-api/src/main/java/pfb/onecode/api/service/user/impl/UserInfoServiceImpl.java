/**
 * Project Name:bibabel
 * Package Name:com.sunfund.bibabel.api.rpc.userRpc.service.impl
 * Date:2017/12/13 10:54
 * SunFund.com Inc.
 * Copyright (c) 2017, wang.yi@sunfund.com All Rights Reserved.
 */
package pfb.onecode.api.service.user.impl;

import com.alibaba.fastjson.JSONObject;

import pfb.onecode.api.common.Response;
import pfb.onecode.api.constant.UserConstant;
import pfb.onecode.api.dao.UserInfoDao;
import pfb.onecode.api.entity.user.UserInfo;
import pfb.onecode.api.enums.ApiErrorEnum;
import pfb.onecode.api.service.user.UserInfoService;
import pfb.onecode.api.util.MD5Util;
import pfb.onecode.api.util.ResponseHandleUtils;
import pfb.onecode.api.util.UUIDUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName:User0001ServiceImpl
 * Date:     2017/12/20 13：58
 * @author jdy
 * @version V1.0
 * @since JDK 1.8
 * @decrible 查询手机号是否已注册的实现类
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserInfoDao userInfoDao;
//    @Autowired
//    private UserLogInfoDao userLogInfoDao;
//    @Autowired
//    private BindCardInfoDao bindCardInfoDao;
//    @Autowired
//    private RedisComponent redisComponent;
//    private JwtTokenUtil jwtTokenUtil;
//    @Autowired
//    public UserInfoServiceImpl(JwtTokenUtil jwtTokenUtil){
//        this.jwtTokenUtil = jwtTokenUtil;
//    }

    @Value("${CERT_URL}")
    private String certUrl;//鉴权地址
    @Value("${MERCHAN_NO}")
    private String merchanNo;//商户号
    @Value("${VALTE_CODE}")
    private String valateCode;//校验码
    @Value("${SIGN_TYPE}")
    private String signTypes;//签名类型
    @Value("${VERSION}")
    private String versions;//版本号
    /**
     * 查询手机号是否已注册
     * @param mobile
     * @return response
     */
    @Override
    public Response registerIdentifyCheck(String mobile,String flag) {
        logger.info("开始进行标示检查操作");
        Response response = null;
        if (StringUtils.isBlank(mobile)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1000.getKey() + "",
                    ApiErrorEnum.Error_1000.value);
            return  response;
        }
        if (StringUtils.isBlank(flag)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1018.getKey() + "",
                    ApiErrorEnum.Error_1018.value);
            return  response;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setMobile(mobile);
        UserInfo newUser = userInfoDao.selectOne(userInfo);
        if(newUser == null){
            if ("1".equals(flag)){
                response = ResponseHandleUtils.setSuccess();
            }else {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1026.getKey() + "",
                        ApiErrorEnum.Error_1026.value);
            }

        }else {
            if ("1".equals(flag)){
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1002.getKey() + "",
                        ApiErrorEnum.Error_1002.value);
            }else {
                response = ResponseHandleUtils.setSuccess();
            }

        }
        logger.info("进行标示检查成功");
        return response;
    }

    /**
     * 用户注册
     * @param entity
     * @return response
     */
    @Override
    public Response register(UserInfo entity) {
        logger.info("开始进行用户注册操作");

        Response response = checkData(entity,"reg");
        if(response==null){
            Response resp = new Response ();
            String userId = "usr"+ UUIDUtils.createData();// 非空
            entity.setUserId(userId);
            entity.setRegTime(new Date());
            entity.setUserStat(UserConstant.USER_STAT_NORMAL);
            String pwd = entity.getLoginPwd();
            entity.setLoginPwd(MD5Util.GetMD5Code(pwd));
            entity.setCertFlag(UserConstant.USER_CERT_FLAG2);
            try{
                userInfoDao.insert(entity);
                resp = ResponseHandleUtils.setSuccess();
                logger.info("用户注册信息表写入成功");
                return resp;
            }catch (Exception e){
                resp =ResponseHandleUtils.setError(ApiErrorEnum.Error_1014.getKey() + "",
                        ApiErrorEnum.Error_1014.value);
                logger.info("用户注册信息表写入失败");
                return resp;
            }

        }
        logger.info("用户注册操作结束");
        return response;
    }

    /**
     * 登陆
     * @param entity
     * @return response
     * @throws Exception
     */
    @Override
    public Response login(UserInfo entity) throws Exception{
        logger.info("开始进行用户登陆操作");
        String mobile = entity.getMobile();
        String loginPwd = entity.getLoginPwd();
        String loginType = entity.getLoginType();

        String isFlag = entity.getIsFlag();
        Response response = checkData(entity,"login");
        if (response==null){
            UserInfo user = new UserInfo();
            user.setMobile(mobile);
            UserInfo newUser = userInfoDao.selectOne(user);
            Response resp = null;
            if(newUser==null){
                resp =ResponseHandleUtils.setError(ApiErrorEnum.Error_1001.getKey() + "",
                        ApiErrorEnum.Error_1001.value);
                return resp;
            }else{
                   if (UserConstant.USER_STAT_LOCKED.equals(newUser.getUserStat())){//用户已锁定
                       if (!redisComponent.hasKey(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile))){
                           newUser.setUserStat(UserConstant.USER_STAT_NORMAL);// 正常
                           userInfoDao.updateByPrimaryKeySelective(newUser);
                       }else {
                           resp =ResponseHandleUtils.setError(ApiErrorEnum.Error_1029.getKey() + "",
                                   ApiErrorEnum.Error_1029.value);
                           return resp;
                       }

                   }
                if ("1".equals(loginType)){//账户登陆
                    String pwd = newUser.getLoginPwd();
                    if(!pwd.equals(MD5Util.GetMD5Code(loginPwd))){//登陆密码错误
                        resp =ResponseHandleUtils.setError(ApiErrorEnum.Error_1017.getKey() + "",
                                ApiErrorEnum.Error_1017.value);
                        if (redisComponent.hasKey(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile))) {// 判断是否有登陆密码错误
                            String countStr = redisComponent.get(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile));
                            int count = Integer.parseInt(countStr)+1;
                            redisComponent.set(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile), count+"");
                            if (count==3){//密码错误3次
                                redisComponent.set(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile), count+"",3600);
                                newUser.setUserStat(UserConstant.USER_STAT_LOCKED);//锁定
                                userInfoDao.updateByPrimaryKeySelective(newUser);
                                resp=ResponseHandleUtils.setError(ApiErrorEnum.Error_1028.getKey() + "",
                                        ApiErrorEnum.Error_1028.value);
                            }

                        }else {
                            //把错误次数放到缓存中
                            redisComponent.set(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile), 1+"");
                        }

                        return resp;
                    }
                    //如果存在登陆密码错误次数的key，登陆成功后就删除
                    if (redisComponent.hasKey(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile))) {
                        redisComponent.delete(ApiCommonConstant.LOGIN_PWD_ERROR_COUNT.concat(mobile));
                    }
                }

                if("1".equals(isFlag)){//有图形验证码
                    String imgCode1 = redisComponent.get(ApiCommonConstant.IMG_CODE.concat(mobile));
                    String imgCode = entity.getImgCode().toLowerCase();
                    if (!imgCode.equals(imgCode1)){
                        resp =ResponseHandleUtils.setError(ApiErrorEnum.Error_1022.getKey() + "",
                                ApiErrorEnum.Error_1022.value);
                        return resp;
                    }
                }
                String token = jwtTokenUtil.generateToken(new JWTInfo(mobile, newUser.getUserId() + "",loginPwd));
                resp = ResponseHandleUtils.setSuccess();
                newUser.setToken(token);
                resp.setBody(newUser);
                //插入用户日志表
                UserLog userLog = new UserLog();
                userLog.setUserId(newUser.getUserId());
                userLog.setLoginTime(new Date());
                userLog.setUserName(newUser.getUserName());
                userLog.setRemark("登陆操作");
                userLogInfoDao.insert(userLog);
                logger.info("用户登陆操作结束");
                return resp;
            }
        }
        return  response;


    }

    /**
     * 绑卡鉴权，调用第三方接口
     * @param request  response
     * @return response
     * @throws Exception
     */
    @Override
    public Response cert(Request<CertVO> request, Response response) throws Exception{
        logger.info("开始进行用户认证操作");
        String token = request.getToken();
        if("".equals(token)){
        	response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1036.getKey() + "",
                    ApiErrorEnum.Error_1036.value);
            return response;
        }
        String userId=jwtTokenUtil.getInfoFromToken(token).getId();
        if (userId==null){
            response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1027.getKey() + "",
                    ApiErrorEnum.Error_1027.value);
            return response;
        }


        String url = certUrl;
        CertVO certVO = request.getBody();
        String reservedMobile = certVO.getReservedMobile();//预留手机号
        String userName=certVO.getUserName();//姓名
        String certType = certVO.getCertType();//证件类型
        String certNo = certVO.getCertNo();//证件号码
        String cardNo = certVO.getCardNo();//卡号
        String payPassWord = certVO.getPayPassWord();//支付密码
        if (StringUtils.isBlank(reservedMobile)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1030.getKey() + "",
                    ApiErrorEnum.Error_1030.value);
            return  response;
        }
        if(!RegexUtils.isMobileNum(reservedMobile)){
        	response = ResponseHandleUtils.setError(ApiErrorEnum.Error_0000.getKey() + "",
                    ApiErrorEnum.Error_0000.value);
            return  response;
        }
        if (StringUtils.isBlank(userName)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1031.getKey() + "",
                    ApiErrorEnum.Error_1031.value);
            return  response;
        }
        if(!RegexUtils.isValidUsername(userName)){
        	response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1037.getKey() + "",
                    ApiErrorEnum.Error_1037.value);
            return  response;
        }
        if (StringUtils.isBlank(certNo)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1032.getKey() + "",
                    ApiErrorEnum.Error_1032.value);
            return  response;
        }
        if (!IDCardValidate.checkCard(certNo)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1039.getKey() + "",
                    ApiErrorEnum.Error_1039.value);
            return  response;
        }
        if (StringUtils.isBlank(cardNo)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1034.getKey() + "",
                    ApiErrorEnum.Error_1034.value);
            return  response;
        }
        if (!RegexUtils.isBankAccount(cardNo)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1038.getKey() + "",
                    ApiErrorEnum.Error_1038.value);
            return  response;
        }
        if (StringUtils.isBlank(payPassWord)) {
            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1033.getKey() + "",
                    ApiErrorEnum.Error_1033.value);
            return  response;
        }
        if(!RegexUtils.isValidPassword(payPassWord)){
        	response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1041.getKey() + "",
                    ApiErrorEnum.Error_1041.value);
            return  response;
        }
        String merchantNo = merchanNo;//商户号
        String valteCode = valateCode;//校验码
        String signType=signTypes;//签名类型
        String version=versions;//版本号
        String signStr="card_no="+cardNo+"&cert_no="+certNo+"&cert_type="+certType+"&merchant_id="+merchantNo+"&owner="+userName+"&version="+version+""+valteCode ;
        String sign = MD5Util.GetMD5Code(signStr);
        System.out.print(sign);
        JSONObject object =new JSONObject();
        object.put("sign",sign);
        object.put("cert_type",certType);
        object.put("cert_no",certNo);
        object.put("owner",userName);
        object.put("card_no",cardNo);
        object.put("merchant_id",merchantNo);
        object.put("sign_type",signType);
        object.put("version ",version);
        Map map = object;
        logger.info("开始进行调用用户认证接口操作");
        //调用认证接口
        // String str = ReqUtil.sendPost("UTF-8",url,map);
        logger.info("调用用户认证接口结束操作");
        String bankCode="111";
        String bankName="建行";
        //鉴权成功，更新表用户信息，插入绑卡信息表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        UserInfo newUser = userInfoDao.selectOne(userInfo);
        newUser.setTradePwd(MD5Util.GetMD5Code(payPassWord));
        newUser.setUserName(userName);
        newUser.setCertType(certType);
        newUser.setCertNo(certNo);
        newUser.setCertFlag(UserConstant.USER_CERT_FLAG1);
        userInfoDao.updateByPrimaryKeySelective(newUser);
        //插入绑卡信息表
        BindCardInfo bindCardInfo = new BindCardInfo();
        bindCardInfo.setUserId(userId);
        bindCardInfo.setBindTime(new Date());
        bindCardInfo.setBankCardNo(cardNo);
        bindCardInfo.setBindStat(UserConstant.USER_BIND_STATUS);
        bindCardInfo.setReservedMobile(reservedMobile);
        bindCardInfo.setBankCode(bankCode);
        bindCardInfo.setBankName(bankName);
        bindCardInfo.setBankCardType("0");
        bindCardInfoDao.insert(bindCardInfo);
        response = ResponseHandleUtils.setSuccess();
        logger.info("用户认证操作结束");
        return response;

    }

    /**
     * 交易密码重置
     * @param request response
     * @return response
     * @throws Exception
     */
    @Override
    public Response resetTradePwd(Request<UserInfo> request, Response response) throws Exception{
        logger.info("开始进行用户交易密码重置操作");
        String token = request.getToken();
        String userId=jwtTokenUtil.getInfoFromToken(token).getId();
        if (userId==null){
            response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1027.getKey() + "",
                    ApiErrorEnum.Error_1027.value);
            return response;
        }
        UserInfo entity = request.getBody();
        Response resp=checkData(entity,"resetTradePwd");
        if (resp==null){

            String mobile = entity.getMobile();
            String smsCode = entity.getSmsCode();
            String tradePwd = entity.getTradePwd();
            String certNo = entity.getCertNo();
            UserInfo user = new UserInfo();
            user.setMobile(mobile);
            UserInfo newUser = userInfoDao.selectOne(user);

            if(newUser==null){
                response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1001.getKey() + "",
                        ApiErrorEnum.Error_1001.value);
                return response;
            }else {
                if (!certNo.equals(newUser.getCertNo())){
                    response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1025.getKey() + "",
                            ApiErrorEnum.Error_1025.value);
                    return response;
                }
                newUser.setTradePwd(MD5Util.GetMD5Code(tradePwd));
                userInfoDao.updateByPrimaryKeySelective(newUser);
                response = ResponseHandleUtils.setSuccess();
                return response;
            }

        }

        return resp;

    }

    /**
     * 登陆密码重置
     * @param request  response
     * @return response
     */
    @Override
    public Response resetLoginPwd(Request<UserInfo> request, Response response) throws Exception{
        logger.info("开始进行用户登陆密码重置操作");
       /* String token = request.getToken();
        String userId=jwtTokenUtil.getInfoFromToken(token).getId();
        if (userId==null){
            response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1027.getKey() + "",
                    ApiErrorEnum.Error_1027.value);
            return response;
        }*/
        UserInfo entity = request.getBody();
        Response resp=checkData(entity,"resetLoginPwd");
        if (resp==null){

            String mobile = entity.getMobile();
            String smsCode = entity.getSmsCode();
            String newLoginPwd = entity.getLoginPwd();
            UserInfo user = new UserInfo();
            user.setMobile(mobile);
            UserInfo newUser = userInfoDao.selectOne(user);
            if(newUser==null){
                response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1001.getKey() + "",
                        ApiErrorEnum.Error_1001.value);
                return response;
            }else {
                newUser.setLoginPwd(MD5Util.GetMD5Code(newLoginPwd));
                userInfoDao.updateByPrimaryKeySelective(newUser);
                response = ResponseHandleUtils.setSuccess();
                return response;
            }

        }

        return resp;
    }

    /**
     * 获取用户信息
     * @param request response
     * @return response
     */
    @Override
    public Response getUserInfo(Request<UserInfo> request, Response response) throws Exception{
        logger.info("开始进行获取用户信息操作");
        String token = request.getToken();
        String userId=jwtTokenUtil.getInfoFromToken(token).getId();
        if (userId==null){
            response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1027.getKey() + "",
                    ApiErrorEnum.Error_1027.value);
            return response;
        }
        //String userId = entity.getUserId();
        UserInfo user = new UserInfo();
        user.setUserId(userId);
        UserInfo newUser = userInfoDao.selectOne(user);
        BindCardInfo bindCardInfo = new BindCardInfo();
        bindCardInfo.setUserId(userId);
        bindCardInfo.setBindStat(UserConstant.USER_BIND_STATUS);
        BindCardInfo bindCardInfoNew=bindCardInfoDao.selectOne(bindCardInfo);

        if(newUser==null){
            response =ResponseHandleUtils.setError(ApiErrorEnum.Error_1001.getKey() + "",
                    ApiErrorEnum.Error_1001.value);
            return response;
        }else {
            newUser.setBankCardNo(bindCardInfoNew==null?"":bindCardInfoNew.getBankCardNo());
            newUser.setBankName(bindCardInfoNew==null?"":bindCardInfoNew.getBankName());
            response = ResponseHandleUtils.setSuccess();
            response.setBody(newUser);
            logger.info("获取用户信息操作结束");
            return response;
        }

    }

    /**
    * @author shen.lixin@sunfund.com
    * @describe 获取用户信息(通过手机号)
    * @date 2018/1/5 下午2:04
    **/
    @Override
    public UserInfo getUserInfo(String userId) {
        UserInfo query = new UserInfo();
        query.setUserId(userId);
        UserInfo userInfo = userInfoDao.selectOne(query);
        return userInfo;
    }

    /**
     * 获取用户绑卡信息
     * @param userId
     * @return bindCardVO
     * @throws
     */
    @Override
    public BindCardVO getBindCardInfo(String userId)  {
        BindCardInfo bindCardInfo = bindCardInfoDao.getBindCardInfo(userId);
        BindCardVO bindCardVO = new BindCardVO();
        BeanUtils.copyProperties(bindCardInfo, bindCardVO);
        return bindCardVO;
    }

    /**
     * 用户退出
     * @param token
     * @return
     */
    @Override
    public Response logout(@RequestHeader("access-token") String token) {
        //jwtTokenUtil.
        //authService.invalid(token);
        return ResponseHandleUtils.setSuccess();
    }

    /**
     * 验证交易密码是否正确
     * @param userInfo
     * @return boolean
     */
    @Override
    public boolean checkTradePwd(UserInfo userInfo) {

        boolean flag = false;

        if (Objects.nonNull(userInfo)){
            UserInfo query = new UserInfo();
            query.setUserId(userInfo.getUserId());
            UserInfo userTrue = userInfoDao.selectOne(query);
            if (Objects.nonNull(userTrue)){
                String md5TradePwd = MD5Util.GetMD5Code(userInfo.getTradePwd());
                flag = userTrue.getTradePwd().equals(md5TradePwd);
            }
        }

        return flag;
    }

    /**
     * 校验
     * @param entity flag
     * @return Response
     */
    public Response checkData(UserInfo entity,String flag){
        String mobile = entity.getMobile();
        String pwd = entity.getLoginPwd();
        String tradePwd = entity.getTradePwd();
        String certNo = entity.getCertNo();
        String smsCode = entity.getSmsCode();
        String regChnlId = entity.getRegChnlId();
        String regPlatform = entity.getRegPlatform();
        String imgCode = entity.getImgCode();
        String isFlag = entity.getIsFlag();
        String loginType = entity.getLoginType();
        Response response = null;

        if("reg".equals(flag)){//注册时，需要校验的信息
            if (StringUtils.isBlank(mobile)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1000.getKey() + "",
                        ApiErrorEnum.Error_1000.value);
                return  response;
            }
            if (StringUtils.isBlank(pwd)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1010.getKey() + "",
                        ApiErrorEnum.Error_1010.value);
                return  response;
            }
            if (StringUtils.isBlank(regChnlId)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1012.getKey() + "",
                        ApiErrorEnum.Error_1012.value);
                return  response;
            }
            if (StringUtils.isBlank(regPlatform)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1013.getKey() + "",
                        ApiErrorEnum.Error_1013.value);
                return  response;
            }
            if (StringUtils.isBlank(smsCode)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1011.getKey() + "",
                        ApiErrorEnum.Error_1011.value);
                return  response;
            }
            if (!redisComponent.hasKey(ApiCommonConstant.SMS_NO.concat(mobile))) {// 验证码已失效
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1015.getKey() + "",
                        ApiErrorEnum.Error_1015.value);
                return  response;

            }
            try{
                String smsNoCache = redisComponent.get(ApiCommonConstant.SMS_NO.concat(mobile));
                if (!smsCode.equals(smsNoCache)) {// 验证码输入错误
                    response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1016.getKey() + "",
                            ApiErrorEnum.Error_1016.value);
                    return  response;
                } else {
                    redisComponent.delete(ApiCommonConstant.SMS_NO.concat(mobile));
                    redisComponent.delete(ApiCommonConstant.SMS_RESEND_TIMEOUT.concat(mobile));
                }
            }catch (Exception e){
                e.printStackTrace();

            }
        }else if ("login".equals(flag)){//登陆校验

            if (StringUtils.isBlank(mobile)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1000.getKey() + "",
                        ApiErrorEnum.Error_1000.value);
                return  response;
            }
            if (StringUtils.isBlank(loginType)) { //登录类型非空判断
            	 	response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1040.getKey() + "",
            				ApiErrorEnum.Error_1040.value);
            		return  response;
			}
            if ("1".equals(loginType)){//账户登陆
                if (StringUtils.isBlank(pwd)) {
                    response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1010.getKey() + "",
                            ApiErrorEnum.Error_1010.value);
                    return  response;
                }

            }else {//短信验证码登陆
                if (StringUtils.isBlank(smsCode)) {
                    response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1011.getKey() + "",
                            ApiErrorEnum.Error_1011.value);
                    return  response;
                }
                if (!redisComponent.hasKey(ApiCommonConstant.SMS_NO.concat(mobile))) {// 验证码已失效
                    response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1015.getKey() + "",
                            ApiErrorEnum.Error_1015.value);
                    return  response;

                }
                try{
                    String smsNoCache = redisComponent.get(ApiCommonConstant.SMS_NO.concat(mobile));
                    if (!smsCode.equals(smsNoCache)) {// 验证码输入错误
                        response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1016.getKey() + "",
                                ApiErrorEnum.Error_1016.value);
                        return  response;
                    } else {
                        redisComponent.delete(ApiCommonConstant.SMS_NO.concat(mobile));
                        redisComponent.delete(ApiCommonConstant.SMS_RESEND_TIMEOUT.concat(mobile));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            if (!"1".equals(isFlag) && !"2".equals(isFlag)){
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1018.getKey() + "",
                        ApiErrorEnum.Error_1018.value);
                return  response;

            }else {
                if ("1".equals(isFlag)){
                    if (StringUtils.isBlank(imgCode)){
                        response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1021.getKey() + "",
                                ApiErrorEnum.Error_1021.value);
                        return  response;
                    }else {
                        if (!redisComponent.hasKey(ApiCommonConstant.IMG_CODE.concat(mobile))) {// 图形验证码已失效
                            response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1023.getKey() + "",
                                    ApiErrorEnum.Error_1023.value);
                            return  response;

                        }
                    }

                }
            }

        }else if ("resetTradePwd".equals(flag)){//交易密码重置
            if (StringUtils.isBlank(mobile)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1000.getKey() + "",
                        ApiErrorEnum.Error_1000.value);
                return  response;
            }
            if (StringUtils.isBlank(smsCode)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1011.getKey() + "",
                        ApiErrorEnum.Error_1011.value);
                return  response;
            }
            if (!redisComponent.hasKey(ApiCommonConstant.SMS_NO.concat(mobile))) {// 验证码已失效
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1015.getKey() + "",
                        ApiErrorEnum.Error_1015.value);
                return  response;

            }
            try{
                String smsNoCache = redisComponent.get(ApiCommonConstant.SMS_NO.concat(mobile));
                if (!smsCode.equals(smsNoCache)) {// 验证码输入错误
                    response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1016.getKey() + "",
                            ApiErrorEnum.Error_1016.value);
                    return  response;
                } else {
                    redisComponent.delete(ApiCommonConstant.SMS_NO.concat(mobile));
                    redisComponent.delete(ApiCommonConstant.SMS_RESEND_TIMEOUT.concat(mobile));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (StringUtils.isBlank(tradePwd)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1010.getKey() + "",
                        ApiErrorEnum.Error_1010.value);
                return  response;
            }
            if (StringUtils.isBlank(certNo)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1024.getKey() + "",
                        ApiErrorEnum.Error_1024.value);
                return  response;
            }
        }else if ("resetLoginPwd".equals(flag)){//登陆密码重置
            if (StringUtils.isBlank(mobile)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1000.getKey() + "",
                        ApiErrorEnum.Error_1000.value);
                return  response;
            }
            if (StringUtils.isBlank(pwd)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1010.getKey() + "",
                        ApiErrorEnum.Error_1010.value);
                return  response;
            }
            if (StringUtils.isBlank(smsCode)) {
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1011.getKey() + "",
                        ApiErrorEnum.Error_1011.value);
                return  response;
            }
            if (!redisComponent.hasKey(ApiCommonConstant.SMS_NO.concat(mobile))) {// 验证码已失效
                response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1015.getKey() + "",
                        ApiErrorEnum.Error_1015.value);
                return  response;

            }
            try{
                String smsNoCache = redisComponent.get(ApiCommonConstant.SMS_NO.concat(mobile));
                if (!smsCode.equals(smsNoCache)) {// 验证码输入错误
                    response = ResponseHandleUtils.setError(ApiErrorEnum.Error_1016.getKey() + "",
                            ApiErrorEnum.Error_1016.value);
                    return  response;
                } else {
                    redisComponent.delete(ApiCommonConstant.SMS_NO.concat(mobile));
                    redisComponent.delete(ApiCommonConstant.SMS_RESEND_TIMEOUT.concat(mobile));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return response;

    }


}
