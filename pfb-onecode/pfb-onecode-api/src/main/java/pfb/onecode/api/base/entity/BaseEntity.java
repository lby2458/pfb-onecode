package pfb.onecode.api.base.entity;

import java.io.Serializable;


import pfb.onecode.api.entity.user.UserInfo;

public class BaseEntity<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;
	/**
	 * 当前用户
	 */
	protected UserInfo currentUser;

}
