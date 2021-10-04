package com.dt.platform.eam.page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dt.platform.constants.enums.eam.AssetAttributeDimensionEnum;
import com.dt.platform.constants.enums.eam.AssetAttributeItemOwnerEnum;
import com.dt.platform.constants.enums.eam.AssetAttributeOwnerEnum;
import com.dt.platform.constants.enums.eam.AssetCategoryCodeEnum;
import com.dt.platform.domain.eam.AssetAttributeItem;
import com.dt.platform.domain.eam.AssetAttributeItemVO;
import com.dt.platform.domain.eam.meta.AssetAttributeItemMeta;
import com.dt.platform.proxy.eam.AssetAttributeItemServiceProxy;
import com.dt.platform.proxy.eam.AssetCategoryServiceProxy;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.commons.bean.BeanNameUtil;
import org.github.foxnic.web.domain.pcm.Catalog;
import org.github.foxnic.web.domain.pcm.CatalogVO;
import org.github.foxnic.web.framework.view.controller.ViewController;

import org.github.foxnic.web.misc.ztree.ZTreeNode;
import org.github.foxnic.web.proxy.pcm.CatalogServiceProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.dt.platform.proxy.eam.AssetServiceProxy;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 资产 模版页面控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-09-07 16:11:51
*/

@Controller("EamAssetPageController")
@RequestMapping(AssetPageController.prefix)
public class AssetPageController extends ViewController {
	
	public static final String prefix="business/eam/asset";

	private AssetServiceProxy proxy;

	/**
	 * 获得代理对象<br> 
	 * 1、单体应用时，在应用内部调用；<br> 
	 * 2、前后端分离时，通过配置，以Rest方式调用后端；<br> 
	 * 3、微服务时，通过feign调用; <br> 
	 * */
	public AssetServiceProxy proxy() {
		if(proxy==null) {
			proxy=AssetServiceProxy.api();
		}
		return proxy;
	}

	/**
	 * 资产
	 */
	@RequestMapping("/asset_select_basic_list.html")
	public String basicLlist(Model model,HttpServletRequest request,String assetSelectedCode) {
		//设置字段布局
		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_SELECT.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}
		model.addAttribute("assetSelectedCode",assetSelectedCode);
		return prefix+"/asset_select_basic_list";
	}


	/**
	 * 资产
	 */
	@RequestMapping("/asset_selected_list.html")
	public String selectedLlist(Model model,HttpServletRequest request,String assetSelectedCode) {
		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_BILL.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}
		model.addAttribute("assetSelectedCode",assetSelectedCode);
		return prefix+"/asset_selected_list";
	}

	/**
	 * 资产
	 */
	@RequestMapping("/asset_select_list.html")
	public String selectLlist(Model model,HttpServletRequest request,String assetSelectedCode) {

		Result idResult=AssetCategoryServiceProxy.api().queryNodesByCode(AssetCategoryCodeEnum.ASSET.code());
		model.addAttribute("categoryParentId",idResult.getData());
		model.addAttribute("assetSelectedCode",assetSelectedCode);
		return prefix+"/asset_select_list";
	}




	/**
	 * 资产 人员资产信息
	 */
	@RequestMapping("/asset_search/employee_assetInfo_list.html")
	public String employeeAssetInfoList(Model model,HttpServletRequest request,String employeeId) {

		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.PUBLIC_SHOW.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}

		model.addAttribute("employeeId",employeeId);
		return prefix+"/asset_search/employee_assetInfo_list";
	}



	/**
	 * 资产 台账
	 */
	@RequestMapping("/asset_search/employee_list.html")
	public String employeeList(Model model,HttpServletRequest request) {

		return prefix+"/asset_search/employee_list";
	}

	/**
	 * 资产 台账
	 */
	@RequestMapping("/asset_search/employee_tree.html")
	public String employeeTree(Model model,HttpServletRequest request) {

		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_BOOK.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}
		return prefix+"/asset_search/employee_tree";
	}


	/**
	 * 资产 台账
	 */
	@RequestMapping("/asset_search/category_tree.html")
	public String categoryTree(Model model,HttpServletRequest request) {

		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_BOOK.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}


		Result idResult=AssetCategoryServiceProxy.api().queryNodesByCode(AssetCategoryCodeEnum.ASSET.code());
		model.addAttribute("categoryParentId",idResult.getData());
		return prefix+"/asset_search/category_tree";
	}

	/**
	 * 资产 台账
	 */
	@RequestMapping("/asset_search/org_tree.html")
	public String orgTree(Model model,HttpServletRequest request) {

		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_BOOK.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}
		return prefix+"/asset_search/org_tree";
	}

	/**
	 * 资产 台账
	 */
	@RequestMapping("/asset_search/position_tree.html")
	public String positionTree(Model model,HttpServletRequest request) {
		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_BOOK.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}
		return prefix+"/asset_search/position_tree";
	}


	/**
	 * 资产 台账
	 */
	@RequestMapping("/asset_search/asset_search.html")
	public String searchList(Model model,HttpServletRequest request) {
		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.ASSET_BOOK.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}
		return prefix+"/asset_search/asset_search";
	}



	/**
	 * 资产 功能主页面
	 */
	@RequestMapping("/asset_list.html")
	public String list(Model model,HttpServletRequest request) {

		return prefix+"/asset_list";
	}

	/**
	 * 资产 功能主页面
	 */
	@RequestMapping("/asset_data_export_list.html")
	public String dataExportList(Model model,HttpServletRequest request) {

		return prefix+"/asset_data_export_list";
	}


	/**
	 * 资产 功能主页面
	 */
	@RequestMapping("/asset_info_list.html")
	public String infoList(Model model,HttpServletRequest request) {

		//设置字段布局
		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.BASE.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			List<AssetAttributeItem> list=data.get("attributeListData");
			model.addAttribute("attributeListData",list);
		}

		CatalogVO catalog=new CatalogVO();
		catalog.setCode("asset");
		Result<List<Catalog>> catalogListResult=CatalogServiceProxy.api().queryList(catalog);
		if(catalogListResult.isSuccess()){
			List<Catalog> catalogList=catalogListResult.getData();
			if(catalogList.size()>0){
				CatalogVO catalog2=new CatalogVO();
				catalog2.setParentId(catalogList.get(0).getId());
				catalog2.setIsLoadAllDescendants(1);
				Result<List<ZTreeNode>> treeResult=CatalogServiceProxy.api().queryNodes(catalog2);
				model.addAttribute("assetCategoryData",treeResult.getData());
			}
		}
		return prefix+"/asset_info_list";
	}

	/**
	 * 资产 表单页面
	 */
	@RequestMapping("/asset_form.html")
	public String form(Model model,HttpServletRequest request , String id) {

		return prefix+"/asset_form";
	}

	/**
	 * 资产 表单页面
	 */
	@RequestMapping("/asset_info_form.html")
	public String infoForm(Model model,HttpServletRequest request , String id) {
		//设置字段布局
		Result<HashMap<String,List<AssetAttributeItem>>> result = AssetAttributeItemServiceProxy.api().queryListByModule(AssetAttributeItemOwnerEnum.BASE.code());
		if(result.isSuccess()){
			HashMap<String,List<AssetAttributeItem>> data = result.getData();
			model.addAttribute("attributeData3Column1",data.get("attributeData3Column1"));
			model.addAttribute("attributeData3Column2",data.get("attributeData3Column2") );
			model.addAttribute("attributeData3Column3",data.get("attributeData3Column3") );
			model.addAttribute("attributeData1Column1",data.get("attributeData1Column1") );
		}

		//设置字段必选
		AssetAttributeItemVO attributeItem=new AssetAttributeItemVO();
		attributeItem.setOwnerCode(AssetAttributeItemOwnerEnum.BASE.code());
		attributeItem.setRequired(1);
		Result<List<AssetAttributeItem>> attributeItemRequiredListResult = AssetAttributeItemServiceProxy.api().queryList(attributeItem);
		JSONObject attributeItemRequiredObject=new JSONObject();
		if(attributeItemRequiredListResult.isSuccess()){
			List<AssetAttributeItem>  attributeItemRequiredList = attributeItemRequiredListResult.getData();
			if(attributeItemRequiredList.size()>0){
				for(int i=0;i<attributeItemRequiredList.size();i++){
					JSONObject obj=new JSONObject();
					if(AssetAttributeDimensionEnum.ATTRIBUTION.code().equals(attributeItemRequiredList.get(i).getDimension())
					||AssetAttributeDimensionEnum.FINANCIAL.code().equals(attributeItemRequiredList.get(i).getDimension())
					||AssetAttributeDimensionEnum.MAINTAINER.code().equals(attributeItemRequiredList.get(i).getDimension())){
						obj.put("labelInForm",attributeItemRequiredList.get(i).getAttribute().getLabel());
						obj.put("inputType",attributeItemRequiredList.get(i).getAttribute().getComponentType());
						obj.put("required",true);
					}
					attributeItemRequiredObject.put(BeanNameUtil.instance().getPropertyName(attributeItemRequiredList.get(i).getAttribute().getCode()),obj);
				}
			}
		}
		model.addAttribute("attributeRequiredData",attributeItemRequiredObject);


		CatalogVO catalog=new CatalogVO();
		catalog.setCode("asset");
		Result<List<Catalog>> catalogListResult=CatalogServiceProxy.api().queryList(catalog);
		if(catalogListResult.isSuccess()){
			List<Catalog> catalogList=catalogListResult.getData();
			if(catalogList.size()>0){
				CatalogVO catalog2=new CatalogVO();
				catalog2.setParentId(catalogList.get(0).getId());
				catalog2.setIsLoadAllDescendants(1);
				Result<List<ZTreeNode>> treeResult=CatalogServiceProxy.api().queryNodes(catalog2);
				model.addAttribute("assetCategoryData",treeResult.getData());
			}
		}

		return prefix+"/asset_info_form";
	}


}