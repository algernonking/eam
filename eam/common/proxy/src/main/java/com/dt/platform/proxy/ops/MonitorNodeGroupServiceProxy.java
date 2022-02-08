package com.dt.platform.proxy.ops;

import org.springframework.web.bind.annotation.RequestMapping;
import org.github.foxnic.web.proxy.api.APIProxy;
import org.github.foxnic.web.proxy.FeignConfiguration;

import org.springframework.cloud.openfeign.FeignClient;


import com.dt.platform.domain.ops.MonitorNodeGroup;
import com.dt.platform.domain.ops.MonitorNodeGroupVO;
import java.util.List;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.PagedList;
import com.dt.platform.proxy.ServiceNames;

/**
 * <p>
 * 节点分组  控制器服务代理
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2022-02-08 13:15:00
*/

@FeignClient(value = ServiceNames.OPS, contextId = MonitorNodeGroupServiceProxy.API_CONTEXT_PATH , configuration = FeignConfiguration.class)
public interface MonitorNodeGroupServiceProxy {

	/**
	 * 基础路径 , service-ops
	*/
	public static final String API_BASIC_PATH = "service-ops";

	/**
	 * API 上下文路径 , ops-monitor-node-group
	*/
	public static final String API_CONTEXT_PATH = "ops-monitor-node-group";

	/**
	 * API 基础路径 , 由 API_BASIC_PATH 和 API_CONTEXT_PATH 两部分组成
	*/
	public static final String API_PREFIX = "/" + API_BASIC_PATH + "/"+API_CONTEXT_PATH+"/";

	/**
	 * 添加节点分组
	 */
	public static final String INSERT = API_PREFIX + "insert";

;
	/**
	 * 删除节点分组
	 */
	public static final String DELETE = API_PREFIX + "delete";

	/**
	 * 批量删除节点分组
	 */
	public static final String DELETE_BY_IDS = API_PREFIX + "delete-by-ids";

	/**
	 * 更新节点分组
	 */
	public static final String UPDATE = API_PREFIX + "update";


	/**
	 * 保存节点分组
	 */
	public static final String SAVE = API_PREFIX + "save";

	/**
	 * 获取单个节点分组
	 */
	public static final String GET_BY_ID = API_PREFIX + "get-by-id";

	/**
	 * 获取多个节点分组
	 */
	public static final String GET_BY_IDS = API_PREFIX + "get-by-ids";

	/**
	 * 查询节点分组
	 */
	public static final String QUERY_LIST = API_PREFIX + "query-list";

	/**
	 * 分页查询节点分组
	 */
	public static final String QUERY_PAGED_LIST = API_PREFIX + "query-paged-list";

	/**
	 * 导出节点分组数据(Excel)
	 */
	public static final String EXPORT_EXCEL = API_PREFIX + "export-excel";

	/**
	 * 下载节点分组导入模版(Excel)
	 */
	public static final String EXPORT_EXCEL_TEMPLATE = API_PREFIX + "export-excel-template";

	/**
	 * 导入节点分组数据(Excel)
	 */
	public static final String IMPORT_EXCEL = API_PREFIX + "import-excel";

	/**
	 * 添加节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.INSERT)
	Result insert(MonitorNodeGroupVO monitorNodeGroupVO);

	/**
	 * 删除节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.DELETE)
	Result deleteById(String id);

	/**
	 * 批量删除节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.DELETE_BY_IDS)
	Result deleteByIds(List<String> ids);

	/**
	 * 更新节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.UPDATE)
	Result update(MonitorNodeGroupVO monitorNodeGroupVO);

	/**
	 * 更新节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.SAVE)
	Result save(MonitorNodeGroupVO monitorNodeGroupVO);

	/**
	 * 获取节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.GET_BY_ID)
	Result<MonitorNodeGroup> getById(String id);

	/**
	 * 获取多个节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.GET_BY_IDS)
	Result<List<MonitorNodeGroup>> getByIds(List<String> ids);
	/**
	 * 查询节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.QUERY_LIST)
	Result<List<MonitorNodeGroup>> queryList(MonitorNodeGroupVO sample);

	/**
	 * 分页查询节点分组
	*/
	@RequestMapping(MonitorNodeGroupServiceProxy.QUERY_PAGED_LIST)
	Result<PagedList<MonitorNodeGroup>> queryPagedList(MonitorNodeGroupVO sample);


	/**
	 * 控制器类名
	 * */
	public static final String CONTROLLER_CLASS_NAME="com.dt.platform.ops.controller.MonitorNodeGroupController";

	/**
	 * 统一的调用接口，实现在单体应用和微服务应用下的无差异调用
	 * */
	public static MonitorNodeGroupServiceProxy api() {
		return APIProxy.get(MonitorNodeGroupServiceProxy.class,CONTROLLER_CLASS_NAME);
	}

}