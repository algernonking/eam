package com.dt.platform.proxy.eam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.github.foxnic.web.proxy.api.APIProxy;
import org.github.foxnic.web.proxy.FeignConfiguration;

import org.springframework.cloud.openfeign.FeignClient;


import com.dt.platform.domain.eam.AssetDataChange;
import com.dt.platform.domain.eam.AssetDataChangeVO;
import java.util.List;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.PagedList;
import com.dt.platform.proxy.ServiceNames;

/**
 * <p>
 * 变更明细  控制器服务代理
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-09-02 12:54:29
*/

@FeignClient(value = ServiceNames.EAM, contextId = AssetDataChangeServiceProxy.API_CONTEXT_PATH , configuration = FeignConfiguration.class)
public interface AssetDataChangeServiceProxy {
	
	/**
	 * 基础路径 , service-eam
	*/
	public static final String API_BASIC_PATH = "service-eam";
	
	/**
	 * API 上下文路径 , eam-asset-data-change
	*/
	public static final String API_CONTEXT_PATH = "eam-asset-data-change";
	
	/**
	 * API 基础路径 , 由 API_BASIC_PATH 和 API_CONTEXT_PATH 两部分组成
	*/
	public static final String API_PREFIX = "/" + API_BASIC_PATH + "/"+API_CONTEXT_PATH+"/";
	
	/**
	 * 添加变更明细
	 */
	public static final String INSERT = API_PREFIX + "insert";
	
	/**
	 * 删除变更明细
	 */
	public static final String DELETE = API_PREFIX + "delete";

	/**
	 * 批量删除变更明细
	 */
	public static final String DELETE_BY_IDS = API_PREFIX + "delete-by-ids";
	
	/**
	 * 更新变更明细
	 */
	public static final String UPDATE = API_PREFIX + "update";
	
	
	/**
	 * 保存变更明细
	 */
	public static final String SAVE = API_PREFIX + "save";
	
	/**
	 * 获取单个变更明细
	 */
	public static final String GET_BY_ID = API_PREFIX + "get-by-id";

	/**
	 * 获取多个变更明细
	 */
	public static final String GET_BY_IDS = API_PREFIX + "get-by-ids";
	;

	/**
	 * 查询变更明细
	 */
	public static final String QUERY_LIST = API_PREFIX + "query-list";
	
	/**
	 * 分页查询变更明细
	 */
	public static final String QUERY_PAGED_LIST = API_PREFIX + "query-paged-list";
	
	/**
	 * 导出变更明细数据(Excel)
	 */
	public static final String EXPORT_EXCEL = API_PREFIX + "export-excel";

	/**
	 * 下载变更明细导入模版(Excel)
	 */
	public static final String EXPORT_EXCEL_TEMPLATE = API_PREFIX + "export-excel-template";
	
	/**
	 * 导入变更明细数据(Excel)
	 */
	public static final String IMPORT_EXCEL = API_PREFIX + "import-excel";
	
	/**
	 * 添加变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.INSERT)
	Result insert(AssetDataChangeVO assetDataChangeVO);
	
	/**
	 * 删除变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.DELETE)
	Result deleteById(String id);

	/**
	 * 批量删除变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.DELETE_BY_IDS)
	Result deleteByIds(List<String> ids);

	/**
	 * 更新变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.UPDATE)
	Result update(AssetDataChangeVO assetDataChangeVO);
	
	/**
	 * 更新变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.SAVE)
	Result save(AssetDataChangeVO assetDataChangeVO);
	
	/**
	 * 获取变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.GET_BY_ID)
	Result<AssetDataChange> getById(String id);

	/**
	 * 批量删除变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.GET_BY_IDS)
	Result<List<AssetDataChange>> getByIds(List<String> ids);
	/**
	 * 查询变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.QUERY_LIST)
	Result<List<AssetDataChange>> queryList(AssetDataChangeVO sample);
	
	/**
	 * 分页查询变更明细
	*/
	@RequestMapping(AssetDataChangeServiceProxy.QUERY_PAGED_LIST)
	Result<PagedList<AssetDataChange>> queryPagedList(AssetDataChangeVO sample);
	
	
	/**
	 * 控制器类名
	 * */
	public static final String CONTROLLER_CLASS_NAME="com.dt.platform.eam.controller.AssetDataChangeController";

	/**
	 * 统一的调用接口，实现在单体应用和微服务应用下的无差异调用
	 * */
	public static AssetDataChangeServiceProxy api() {
		return APIProxy.get(AssetDataChangeServiceProxy.class,CONTROLLER_CLASS_NAME);
	}

}