package pfb.onecode.api.base.entity;

import java.util.Date;

import pfb.onecode.api.entity.user.UserInfo;


public class CrudEntity<T> extends BaseEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String remarks;	// 备注
	protected UserInfo createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected UserInfo updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	
}
