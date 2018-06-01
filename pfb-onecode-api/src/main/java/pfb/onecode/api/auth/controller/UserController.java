/**
 * Project Name:bibabel
 * Package Name:com.sunfund.bibabel.api.userCenter.controller
 * Date:2017/12/11 15:09
 * SunFund.com Inc.
 * Copyright (c) 2017, wang.yi@sunfund.com All Rights Reserved.
 */
package pfb.onecode.api.auth.controller;

import com.alibaba.fastjson.JSONObject;

import pfb.onecode.api.common.Request;
import pfb.onecode.api.common.Response;
import pfb.onecode.api.entity.user.UserInfo;
import pfb.onecode.api.service.user.UserInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:User0001Controller
 * Date:     2017/12/20 13:50
 * @author jdy
 * @version V1.0
 * @since JDK 1.8
 * @decrible 用户controller
 */
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询手机号是否已注册
     * @param json
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/registerCheck", method = RequestMethod.POST)
    @ResponseBody
    public Response registerIdentifyCheck(@RequestBody JSONObject json) throws Exception {
        String mobile = json.getJSONObject("body").getString("mobile");
        String flag = json.getJSONObject("body").getString("flag");
        return  userInfoService.registerIdentifyCheck(mobile, flag);

    }
    /**
     * 用户注册
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public Response register(@RequestBody Request<UserInfo> request) throws Exception {
        UserInfo userReg = request.getBody();
        Response  response=userInfoService.register(userReg);
        return  response;

    }
    /**
     * 用户登陆
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public Response login(@RequestBody Request<UserInfo> request) throws Exception {
        UserInfo userReg = request.getBody();
        Response  response=userInfoService.login(userReg);
        return  response;

    }
    /**
     * 用户交易密码重置
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/resetTradePwd", method = RequestMethod.POST)
    @ResponseBody
    public Response resetTradePwd(@RequestBody Request<UserInfo> request,Response response) throws Exception {

        response=userInfoService.resetTradePwd(request,response);
        return  response;

    }
    /**
     * 登陆密码重置
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/resetLoginPwd", method = RequestMethod.POST)
    @ResponseBody
    public Response resetLoginPwd(@RequestBody Request<UserInfo> request,Response response) throws Exception {

        response=userInfoService.resetLoginPwd(request,response);
        return  response;

    }
    /**
     * 获取用户信息
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Response getUserInfo(@RequestBody Request<UserInfo> request,Response response) throws Exception {
        response=userInfoService.getUserInfo(request,response);
        return  response;

    }
    /**
     * 实名认证
     * @param
     * @return
     * @throws Exception
     */
/*    @RequestMapping(value = "/user/authentication", method = RequestMethod.POST)
    @ResponseBody
    public Response cert(@RequestBody Request<CertVO> request, Response response) throws Exception {
        response=userInfoService.cert(request,response);
        return  response;

    }*/

}
