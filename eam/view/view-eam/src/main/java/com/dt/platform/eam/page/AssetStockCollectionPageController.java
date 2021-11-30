package com.dt.platform.eam.page;

import org.github.foxnic.web.framework.view.controller.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.dt.platform.proxy.eam.AssetStockCollectionServiceProxy;
import javax.servlet.http.HttpServletRequest;
/**
 * <p>
 * 资产领用 模版页面控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-11-29 13:22:22
*/

@Controller("EamAssetStockCollectionPageController")
@RequestMapping(AssetStockCollectionPageController.prefix)
public class AssetStockCollectionPageController extends ViewController {
	
	public static final String prefix="business/eam/asset_stock_collection";

	private AssetStockCollectionServiceProxy proxy;
	
	/**
	 * 获得代理对象<br> 
	 * 1、单体应用时，在应用内部调用；<br> 
	 * 2、前后端分离时，通过配置，以Rest方式调用后端；<br> 
	 * 3、微服务时，通过feign调用; <br> 
	 * */
	public AssetStockCollectionServiceProxy proxy() {
		if(proxy==null) {
			proxy=AssetStockCollectionServiceProxy.api();
		}
		return proxy;
	}
	
	/**
	 * 资产领用 功能主页面
	 */
	@RequestMapping("/asset_stock_collection_list.html")
	public String list(Model model,HttpServletRequest request) {
		return prefix+"/asset_stock_collection_list";
	}

	/**
	 * 资产领用 表单页面
	 */
	@RequestMapping("/asset_stock_collection_form.html")
	public String form(Model model,HttpServletRequest request , String id) {
		return prefix+"/asset_stock_collection_form";
	}
}