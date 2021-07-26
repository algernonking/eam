package com.dt.platform.proxy.datacenter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.github.foxnic.web.proxy.api.APIProxy;
import org.github.foxnic.web.proxy.FeignConfiguration;

import org.springframework.cloud.openfeign.FeignClient;


import com.dt.platform.domain.datacenter.Rack;
import com.dt.platform.domain.datacenter.RackVO;
import java.util.List;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.PagedList;
import com.dt.platform.proxy.EAMServiceNames;

/**
 * <p>
 * 数据中心  控制器服务代理
 * </p>
 * @author 李方捷 , leefangjie@qq.com
 * @since 2021-07-26 18:16:43
*/

@FeignClient(value = EAMServiceNames.DATACENTER, contextId = RackServiceProxy.API_CONTEXT_PATH , configuration = FeignConfiguration.class)
public interface RackServiceProxy {
	
	/**
	 * 基础路径 , service-datacenter
	*/
	public static final String API_BASIC_PATH = "service-datacenter";
	
	/**
	 * API 上下文路径 , dc-rack
	*/
	public static final String API_CONTEXT_PATH = "dc-rack";
	
	/**
	 * API 基础路径 , 由 API_BASIC_PATH 和 API_CONTEXT_PATH 两部分组成
	*/
	public static final String API_PREFIX = "/" + API_BASIC_PATH + "/"+API_CONTEXT_PATH+"/";
	
	/**
	 * 添加数据中心
	 */
	public static final String INSERT = API_PREFIX + "insert";
	
	/**
	 * 删除数据中心
	 */
	public static final String DELETE = API_PREFIX + "delete";

	/**
	 * 批量删除数据中心
	 */
	public static final String DELETE_BY_IDS = API_PREFIX + "delete-by-ids";
	;
	
	/**
	 * 更新数据中心
	 */
	public static final String UPDATE = API_PREFIX + "update";
	
	
	/**
	 * 保存数据中心
	 */
	public static final String SAVE = API_PREFIX + "save";
	
	/**
	 * 获取单个数据中心
	 */
	public static final String GET_BY_ID = API_PREFIX + "get-by-id";

	/**
	 * 获取多个数据中心
	 */
	public static final String GET_BY_IDS = API_PREFIX + "get-by-ids";
	;

	/**
	 * 查询数据中心
	 */
	public static final String QUERY_LIST = API_PREFIX + "query-list";
	
	/**
	 * 分页查询数据中心
	 */
	public static final String QUERY_PAGED_LIST = API_PREFIX + "query-paged-list";
	
	/**
	 * 导出数据中心数据(Excel)
	 */
	public static final String EXPORT_EXCEL = API_PREFIX + "export-excel";

	/**
	 * 下载数据中心导入模版(Excel)
	 */
	public static final String EXPORT_EXCEL_TEMPLATE = API_PREFIX + "export-excel-template";
	
	/**
	 * 导入数据中心数据(Excel)
	 */
	public static final String IMPORT_EXCEL = API_PREFIX + "import-excel";
	
	/**
	 * 添加数据中心
	*/
	@RequestMapping(RackServiceProxy.INSERT)
	Result insert(RackVO rackVO);
	
	/**
	 * 删除数据中心
	*/
	@RequestMapping(RackServiceProxy.DELETE)
	Result deleteById(String id);

	/**
	 * 批量删除数据中心
	*/
	@RequestMapping(RackServiceProxy.DELETE_BY_IDS)
	Result deleteByIds(List<String> ids);

	/**
	 * 更新数据中心
	*/
	@RequestMapping(RackServiceProxy.UPDATE)
	Result update(RackVO rackVO);
	
	/**
	 * 更新数据中心
	*/
	@RequestMapping(RackServiceProxy.SAVE)
	Result save(RackVO rackVO);
	
	/**
	 * 获取数据中心
	*/
	@RequestMapping(RackServiceProxy.GET_BY_ID)
	Result<Rack> getById(String id);

	/**
	 * 批量删除数据中心
	*/
	@RequestMapping(RackServiceProxy.GET_BY_IDS)
	Result<List<Rack>> getByIds(List<String> ids);
	/**
	 * 查询数据中心
	*/
	@RequestMapping(RackServiceProxy.QUERY_LIST)
	Result<List<Rack>> queryList(RackVO sample);
	
	/**
	 * 分页查询数据中心
	*/
	@RequestMapping(RackServiceProxy.QUERY_PAGED_LIST)
	Result<PagedList<Rack>> queryPagedList(RackVO sample);
	
	
	/**
	 * 控制器类名
	 * */
	public static final String CONTROLLER_CLASS_NAME="com.dt.platform.datacenter.controller.RackController";

	/**
	 * 统一的调用接口，实现在单体应用和微服务应用下的无差异调用
	 * */
	public static RackServiceProxy api() {
		return APIProxy.get(RackServiceProxy.class,CONTROLLER_CLASS_NAME);
	}

}