/**
 * Project Name:bibabel
 * Package Name:com.sunfund.bibabel.api.common
 * Date:2017/12/13 15:53
 * SunFund.com Inc.
 * Copyright (c) 2017, wang.yi@sunfund.com All Rights Reserved.
 */
package pfb.onecode.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:Response
 * Date:     2017/12/13 15:53
 * @author jdy
 * @version V1.0
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    //200 成功
    private String resCode;
    private String errorCode;
    private String errorMsg;
    private String responseTime;

    private T body;
}
