package com.dt.platform.proxy.ops;

import org.springframework.web.bind.annotation.RequestMapping;
import org.github.foxnic.web.proxy.api.APIProxy;
import org.github.foxnic.web.proxy.FeignConfiguration;

import org.springframework.cloud.openfeign.FeignClient;


import com.dt.platform.domain.ops.MonitorNodeMap;
import com.dt.platform.domain.ops.MonitorNodeMapVO;
import java.util.List;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.PagedList;
import com.dt.platform.proxy.ServiceNames;

/**
 * <p>
 * 节点映射  控制器服务代理
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2022-02-02 14:55:29
*/

@FeignClient(value = ServiceNames.OPS, contextId = MonitorNodeMapServiceProxy.API_CONTEXT_PATH , configuration = FeignConfiguration.class)
public interface MonitorNodeMapServiceProxy {

	/**
	 * 基础路径 , service-ops
	*/
	public static final String API_BASIC_PATH = "service-ops";

	/**
	 * API 上下文路径 , ops-monitor-node-map
	*/
	public static final String API_CONTEXT_PATH = "ops-monitor-node-map";

	/**
	 * API 基础路径 , 由 API_BASIC_PATH 和 API_CONTEXT_PATH 两部分组成
	*/
	public static final String API_PREFIX = "/" + API_BASIC_PATH + "/"+API_CONTEXT_PATH+"/";

	/**
	 * 添加节点映射
	 */
	public static final String INSERT = API_PREFIX + "insert";

;
	/**
	 * 删除节点映射
	 */
	public static final String DELETE = API_PREFIX + "delete";

	/**
	 * 批量删除节点映射
	 */
	public static final String DELETE_BY_IDS = API_PREFIX + "delete-by-ids";

	/**
	 * 更新节点映射
	 */
	public static final String UPDATE = API_PREFIX + "update";


	/**
	 * 保存节点映射
	 */
	public static final String SAVE = API_PREFIX + "save";

	/**
	 * 获取单个节点映射
	 */
	public static final String GET_BY_ID = API_PREFIX + "get-by-id";

	/**
	 * 获取多个节点映射
	 */
	public static final String GET_BY_IDS = API_PREFIX + "get-by-ids";

	/**
	 * 查询节点映射
	 */
	public static final String QUERY_LIST = API_PREFIX + "query-list";

	/**
	 * 分页查询节点映射
	 */
	public static final String QUERY_PAGED_LIST = API_PREFIX + "query-paged-list";

	/**
	 * 导出节点映射数据(Excel)
	 */
	public static final String EXPORT_EXCEL = API_PREFIX + "export-excel";

	/**
	 * 下载节点映射导入模版(Excel)
	 */
	public static final String EXPORT_EXCEL_TEMPLATE = API_PREFIX + "export-excel-template";

	/**
	 * 导入节点映射数据(Excel)
	 */
	public static final String IMPORT_EXCEL = API_PREFIX + "import-excel";

	/**
	 * 添加节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.INSERT)
	Result insert(MonitorNodeMapVO monitorNodeMapVO);

	/**
	 * 删除节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.DELETE)
	Result deleteById(String id);

	/**
	 * 批量删除节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.DELETE_BY_IDS)
	Result deleteByIds(List<String> ids);

	/**
	 * 更新节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.UPDATE)
	Result update(MonitorNodeMapVO monitorNodeMapVO);

	/**
	 * 更新节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.SAVE)
	Result save(MonitorNodeMapVO monitorNodeMapVO);

	/**
	 * 获取节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.GET_BY_ID)
	Result<MonitorNodeMap> getById(String id);

	/**
	 * 获取多个节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.GET_BY_IDS)
	Result<List<MonitorNodeMap>> getByIds(List<String> ids);
	/**
	 * 查询节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.QUERY_LIST)
	Result<List<MonitorNodeMap>> queryList(MonitorNodeMapVO sample);

	/**
	 * 分页查询节点映射
	*/
	@RequestMapping(MonitorNodeMapServiceProxy.QUERY_PAGED_LIST)
	Result<PagedList<MonitorNodeMap>> queryPagedList(MonitorNodeMapVO sample);


	/**
	 * 控制器类名
	 * */
	public static final String CONTROLLER_CLASS_NAME="com.dt.platform.ops.controller.MonitorNodeMapController";

	/**
	 * 统一的调用接口，实现在单体应用和微服务应用下的无差异调用
	 * */
	public static MonitorNodeMapServiceProxy api() {
		return APIProxy.get(MonitorNodeMapServiceProxy.class,CONTROLLER_CLASS_NAME);
	}

}