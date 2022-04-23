package com.dt.platform.eam.page;

import org.github.foxnic.web.framework.view.controller.ViewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.dt.platform.proxy.eam.AssetStockGoodsTranferServiceProxy;
import javax.servlet.http.HttpServletRequest;
/**
 * <p>
 * 库存调拨 模版页面控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2022-04-23 07:42:08
*/

@Controller("EamAssetStockGoodsTranferPageController")
@RequestMapping(AssetStockGoodsTranferPageController.prefix)
public class AssetStockGoodsTranferPageController extends ViewController {
	
	public static final String prefix="business/eam/asset_stock_goods_tranfer";

	private AssetStockGoodsTranferServiceProxy proxy;
	
	/**
	 * 获得代理对象<br> 
	 * 1、单体应用时，在应用内部调用；<br> 
	 * 2、前后端分离时，通过配置，以Rest方式调用后端；<br> 
	 * 3、微服务时，通过feign调用; <br> 
	 * */
	public AssetStockGoodsTranferServiceProxy proxy() {
		if(proxy==null) {
			proxy=AssetStockGoodsTranferServiceProxy.api();
		}
		return proxy;
	}
	
	/**
	 * 库存调拨 功能主页面
	 */
	@RequestMapping("/asset_stock_goods_tranfer_list.html")
	public String list(Model model,HttpServletRequest request) {
		return prefix+"/asset_stock_goods_tranfer_list";
	}

	/**
	 * 库存调拨 表单页面
	 */
	@RequestMapping("/asset_stock_goods_tranfer_form.html")
	public String form(Model model,HttpServletRequest request , String id) {
		return prefix+"/asset_stock_goods_tranfer_form";
	}
}