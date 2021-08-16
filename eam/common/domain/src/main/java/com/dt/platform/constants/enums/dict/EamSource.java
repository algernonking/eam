package com.dt.platform.constants.enums.dict;




/**
 * @since 2021-08-16 16:56:15
 * @author 金杰 , maillank@qq.com
 * 此文件由工具自动生成，请勿修改。若表结构变动，请使用工具重新生成。
*/

public enum EamSource {
	
	/**
	 * S1
	*/
	S1("s1" , "S1"),
	;
	
	private String code;
	private String text;
	private EamSource(String code,String text)  {
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
	 * 从字符串转换成当前枚举类型，使用 valueOf 方法可能导致偏差，建议不要使用
	*/
	public static EamSource parse(String code) {
		for (EamSource dn : EamSource.values()) {
			if(code.equals(dn.code())) return dn;
		}
		return null;
	}
}