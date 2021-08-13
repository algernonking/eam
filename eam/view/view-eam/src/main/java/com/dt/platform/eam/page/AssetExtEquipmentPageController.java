package com.dt.platform.eam.page;

import org.github.foxnic.web.framework.view.controller.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.dt.platform.proxy.eam.AssetExtEquipmentServiceProxy;
import javax.servlet.http.HttpServletRequest;
/**
 * <p>
 * 设备属性扩展 模版页面控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-08-13 11:05:40
*/

@Controller("EamAssetExtEquipmentPageController")
@RequestMapping(AssetExtEquipmentPageController.prefix)
public class AssetExtEquipmentPageController extends ViewController {
	
	public static final String prefix="business/eam/asset_ext_equipment";

	private AssetExtEquipmentServiceProxy proxy;
	
	/**
	 * 获得代理对象<br> 
	 * 1、单体应用时，在应用内部调用；<br> 
	 * 2、前后端分离时，通过配置，以Rest方式调用后端；<br> 
	 * 3、微服务时，通过feign调用; <br> 
	 * */
	public AssetExtEquipmentServiceProxy proxy() {
		if(proxy==null) {
			proxy=AssetExtEquipmentServiceProxy.api();
		}
		return proxy;
	}
	
	/**
	 * 设备属性扩展 功能主页面
	 */
	@RequestMapping("/asset_ext_equipment_list.html")
	public String list(Model model,HttpServletRequest request) {
		return prefix+"/asset_ext_equipment_list";
	}

	/**
	 * 设备属性扩展 表单页面
	 */
	@RequestMapping("/asset_ext_equipment_form.html")
	public String form(Model model,HttpServletRequest request , String id) {
		return prefix+"/asset_ext_equipment_form";
	}
}