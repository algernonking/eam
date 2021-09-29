package com.dt.platform.constants.enums.dict;

import com.github.foxnic.api.constant.CodeTextEnum;
import com.github.foxnic.commons.reflect.EnumUtil;



/**
 * @since 2021-09-29 11:26:59
 * @author 金杰 , maillank@qq.com
 * 此文件由工具自动生成，请勿修改。若表结构变动，请使用工具重新生成。
*/

public enum OpsSystemStatus implements CodeTextEnum {
	
	/**
	 * 在线
	*/
	ONLINE("online" , "在线"),
	
	/**
	 * 下线
	*/
	OFFLINE("offline" , "下线"),
	
	/**
	 * unknown
	*/
	UNKNOWN("unknown" , "unknown"),
	;
	
	private String code;
	private String text;
	private OpsSystemStatus(String code,String text)  {
		this.code=code;
		this.text=text;
	}
	
	public String code() {
		return code;
	}
	
	public String text() {
		return text;
	}
	
	/**
	 * 从字符串转换成当前枚举类型
	*/
	public static OpsSystemStatus parseByCode(String code) {
		return (OpsSystemStatus) EnumUtil.parseByCode(OpsSystemStatus.values(),code);
	}
}