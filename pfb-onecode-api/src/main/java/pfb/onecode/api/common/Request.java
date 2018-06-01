/**
 * Project Name:bibabel
 * Package Name:com.sunfund.bibabel.api.common
 * Date:2017/12/13 15:46
 * SunFund.com Inc.
 * Copyright (c) 2017, wang.yi@sunfund.com All Rights Reserved.
 */
package pfb.onecode.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求基类
 * 
 * ClassName:Request
 * Date:     2017/12/13 15:46
 * @author jdy
 * @version V1.0
 * @since JDK 1.8
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request<T> {

    //接口名称
    private String faceCode;
    //token
    private String token;
    //<渠道>,<版本号>,<系统版本号> 如：P,100,IE8
    private String clientInfo;
    //请求时间
    private String requestTime;
    //入参不用传，token解密后进行赋值
    private String userId;

    private T body;

}
