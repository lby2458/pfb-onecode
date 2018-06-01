/**
 * Project Name:artascope
 * Package Name:com.sunfund.bibabel.common.util
 * Date:2017/12/18 16:12
 * SunFund.com Inc.
 * Copyright (c) 2017, wang.yi@sunfund.com All Rights Reserved.
 */
package pfb.onecode.api.util;


import java.util.Date;

import pfb.onecode.api.common.Response;
import pfb.onecode.api.enums.CommonErrorEnum;

/**
 * ClassName:ResponseHandleUtils
 * Date:     2017/12/18 16:12
 * @author jdy
 * @version V1.0
 * @since JDK 1.8
 */
public class ResponseHandleUtils {
    /**
     * 返回成功的对象
     * @return
     */
    public static Response setSuccess(){
        Response response = new Response();
        response.setResCode("0");
        response.setResponseTime(DateUtil.dateToString(new Date(),DateUtil.EN_LONG_DATE_FORMAT));
        return response;
    }

    /**
     * 返回对象
     * @return
     */
    public static Response getResponse(Response response){
        response.setResponseTime(DateUtil.dateToString(new Date(),DateUtil.EN_LONG_DATE_FORMAT));
        return response;
    }

    /**
     * 返回失败的对象
     * @param error
     * @param errorMsg
     * @return
     */
    public static Response setError(String error,String errorMsg){
        Response response = new Response();
        response.setResCode("1");
        response.setErrorCode(error);
        response.setErrorMsg(errorMsg);
        response.setResponseTime(DateUtil.dateToString(new Date(),DateUtil.EN_LONG_DATE_FORMAT));
        return response;
    }

    /**
     * 返回失败的对象
     * @param e
     * @return
     */
    public static Response setException(Exception e){
        Response response = new Response();
        response.setResCode("1");
        response.setErrorCode(CommonErrorEnum.Error_5000.key);
        response.setErrorMsg(e.getMessage());
        response.setResponseTime(DateUtil.dateToString(new Date(),DateUtil.EN_LONG_DATE_FORMAT));
        return response;
    }

}
