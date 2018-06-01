package com.pfb.onecode.gateway.controller.absbase;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public abstract class AbstractBase implements Serializable{
	private static final long serialVersionUID = 1L;
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
