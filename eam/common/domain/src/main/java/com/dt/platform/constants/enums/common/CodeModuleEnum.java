package com.dt.platform.constants.enums.common;

import com.github.foxnic.api.constant.CodeTextEnum;


/**
 * @since 2021-07-24 10:25:37
 * @author 金杰 , maillank@qq.com
 * 从 select code,name from sys_dict WHERE deleted=0 and module in ('eam','hrm') 生成
 * 此文件由工具自动生成，请勿修改。若表结构变动，请使用工具重新生成
*/




public enum CodeModuleEnum implements CodeTextEnum {

	EAM_ASSET_CODE("eam_asset_code" , "EAM_资产编码"),
	//未使用
	EAM_ASSET_STOCK_CODE("eam_asse_stock_code" , "EAM_资产库存编码"),
	EAM_ASSET_IN_STOCK_INSERT("eam_asset_in_stock_insert" , "EAM_资产库存入库"),
	EAM_ASSET_OUT_STOCK_INSERT("eam_asset_out_stock_insert" , "EAM_资产库存出库"),
	EAM_ASSET_SOFTWARE_CODE("eam_asset_software_code" , "EAM_软件资产编码"),
	EAM_ASSET_COLLECTION("eam_asset_collection" , "EAM_资产单据-领用"),
	EAM_ASSET_COLLECTION_RETURN("eam_asset_collection_return" , "EAM_资产单据-退库"),
	EAM_ASSET_BORROW("eam_asset_borrow" , "EAM_资产单据-借用"),
	EAM_ASSET_BORROW_RETURN("eam_asset_borrow_return" , "EAM_资产单据-借用归还"),
	EAM_ASSET_REPAIR("eam_asset_repair" , "EAM_资产单据-报修"),
	EAM_ASSET_SCRAP("eam_asset_scrap" , "EAM_资产单据-报废"),
	EAM_ASSET_ALLOCATE("eam_asset_allocate" , "EAM_资产单据-调拨"),
	EAM_ASSET_TRANFER("eam_asset_tranfer" , "EAM_资产单据-转移"),
	EAM_ASSET_CLEAN("eam_asset_clean" , "EAM_资产单据-清理"),


	//员工领用
	EAM_ASSET_CONSUMABLES_COLLECTION("eam_asset_consumables_collection" , "EAM_资产耗材单据-领用"),
	EAM_ASSET_CONSUMABLES_IN_STOCK("eam_asset_consumables_in_stock" , "EAM_资产耗材单据-入库"),
	EAM_ASSET_CONSUMABLES_OUT_STOCK("eam_asset_consumables_out_stock" , "EAM_资产耗材单据-出库"),
	EAM_ASSET_CONSUMABLES_TRANFER("eam_asset_consumables_tranfer" , "EAM_资产耗材单据-转移"),


	EAM_ASSET_CHANGE_BASE_INFO("eam_asset_change_base_info" , "EAM_资产变更-基本"),
	EAM_ASSET_CHANGE_MAINTENANCE("eam_asset_change_maintenance" , "EAM_资产变更-维保"),
	EAM_ASSET_CHANGE_FINANCIAL("eam_asset_change_financial" , "EAM_资产变更-财务"),
	EAM_ASSET_CHANGE_EQUIPMENT("eam_asset_change_equipment" , "EAM_资产变更-设备"),

	EAM_ASSET_STOCK_OUT("eam_stock_out" , "EAM_资产库存-出库"),
	EAM_ASSET_STOCK_IN("eam_stock_in" , "EAM_资产库存-入库"),
;

	private String code;
	private String text;
	private CodeModuleEnum(String code, String text)  {
		this.code=code;
		this.text=text;
	}
	
	public String code() {
		return code;
	}
	
	public String text() {
		return text;
	}
}