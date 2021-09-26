package com.dt.platform.common.page;

import org.github.foxnic.web.framework.view.controller.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.dt.platform.proxy.common.CodeRuleServiceProxy;
import javax.servlet.http.HttpServletRequest;
/**
 * <p>
 * 编码规则 模版页面控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-09-26 11:14:47
*/

@Controller("SysCodeRulePageController")
@RequestMapping(CodeRulePageController.prefix)
public class CodeRulePageController extends ViewController {
	
	public static final String prefix="business/common/code_rule";

	private CodeRuleServiceProxy proxy;
	
	/**
	 * 获得代理对象<br> 
	 * 1、单体应用时，在应用内部调用；<br> 
	 * 2、前后端分离时，通过配置，以Rest方式调用后端；<br> 
	 * 3、微服务时，通过feign调用; <br> 
	 * */
	public CodeRuleServiceProxy proxy() {
		if(proxy==null) {
			proxy=CodeRuleServiceProxy.api();
		}
		return proxy;
	}
	
	/**
	 * 编码规则 功能主页面
	 */
	@RequestMapping("/code_rule_list.html")
	public String list(Model model,HttpServletRequest request) {
		return prefix+"/code_rule_list";
	}

	/**
	 * 编码规则 表单页面
	 */
	@RequestMapping("/code_rule_form.html")
	public String form(Model model,HttpServletRequest request , String id) {
		return prefix+"/code_rule_form";
	}
}