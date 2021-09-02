package com.dt.platform.proxy.ops;

import org.springframework.web.bind.annotation.RequestMapping;
import org.github.foxnic.web.proxy.api.APIProxy;
import org.github.foxnic.web.proxy.FeignConfiguration;

import org.springframework.cloud.openfeign.FeignClient;


import com.dt.platform.domain.ops.VoucherOwner;
import com.dt.platform.domain.ops.VoucherOwnerVO;
import java.util.List;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.PagedList;
import com.dt.platform.proxy.ServiceNames;

/**
 * <p>
 * 所属凭证  控制器服务代理
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-09-02 22:42:49
*/

@FeignClient(value = ServiceNames.OPS, contextId = VoucherOwnerServiceProxy.API_CONTEXT_PATH , configuration = FeignConfiguration.class)
public interface VoucherOwnerServiceProxy {
	
	/**
	 * 基础路径 , service-ops
	*/
	public static final String API_BASIC_PATH = "service-ops";
	
	/**
	 * API 上下文路径 , ops-voucher-owner
	*/
	public static final String API_CONTEXT_PATH = "ops-voucher-owner";
	
	/**
	 * API 基础路径 , 由 API_BASIC_PATH 和 API_CONTEXT_PATH 两部分组成
	*/
	public static final String API_PREFIX = "/" + API_BASIC_PATH + "/"+API_CONTEXT_PATH+"/";
	
	/**
	 * 添加所属凭证
	 */
	public static final String INSERT = API_PREFIX + "insert";
	
	/**
	 * 删除所属凭证
	 */
	public static final String DELETE = API_PREFIX + "delete";

	/**
	 * 批量删除所属凭证
	 */
	public static final String DELETE_BY_IDS = API_PREFIX + "delete-by-ids";
	
	/**
	 * 更新所属凭证
	 */
	public static final String UPDATE = API_PREFIX + "update";
	
	
	/**
	 * 保存所属凭证
	 */
	public static final String SAVE = API_PREFIX + "save";
	
	/**
	 * 获取单个所属凭证
	 */
	public static final String GET_BY_ID = API_PREFIX + "get-by-id";

	/**
	 * 获取多个所属凭证
	 */
	public static final String GET_BY_IDS = API_PREFIX + "get-by-ids";
	;

	/**
	 * 查询所属凭证
	 */
	public static final String QUERY_LIST = API_PREFIX + "query-list";
	
	/**
	 * 分页查询所属凭证
	 */
	public static final String QUERY_PAGED_LIST = API_PREFIX + "query-paged-list";
	
	/**
	 * 导出所属凭证数据(Excel)
	 */
	public static final String EXPORT_EXCEL = API_PREFIX + "export-excel";

	/**
	 * 下载所属凭证导入模版(Excel)
	 */
	public static final String EXPORT_EXCEL_TEMPLATE = API_PREFIX + "export-excel-template";
	
	/**
	 * 导入所属凭证数据(Excel)
	 */
	public static final String IMPORT_EXCEL = API_PREFIX + "import-excel";
	
	/**
	 * 添加所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.INSERT)
	Result insert(VoucherOwnerVO voucherOwnerVO);
	
	/**
	 * 删除所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.DELETE)
	Result deleteById(String id);

	/**
	 * 批量删除所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.DELETE_BY_IDS)
	Result deleteByIds(List<String> ids);

	/**
	 * 更新所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.UPDATE)
	Result update(VoucherOwnerVO voucherOwnerVO);
	
	/**
	 * 更新所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.SAVE)
	Result save(VoucherOwnerVO voucherOwnerVO);
	
	/**
	 * 获取所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.GET_BY_ID)
	Result<VoucherOwner> getById(String id);

	/**
	 * 批量删除所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.GET_BY_IDS)
	Result<List<VoucherOwner>> getByIds(List<String> ids);
	/**
	 * 查询所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.QUERY_LIST)
	Result<List<VoucherOwner>> queryList(VoucherOwnerVO sample);
	
	/**
	 * 分页查询所属凭证
	*/
	@RequestMapping(VoucherOwnerServiceProxy.QUERY_PAGED_LIST)
	Result<PagedList<VoucherOwner>> queryPagedList(VoucherOwnerVO sample);
	
	
	/**
	 * 控制器类名
	 * */
	public static final String CONTROLLER_CLASS_NAME="com.dt.platform.ops.controller.VoucherOwnerController";

	/**
	 * 统一的调用接口，实现在单体应用和微服务应用下的无差异调用
	 * */
	public static VoucherOwnerServiceProxy api() {
		return APIProxy.get(VoucherOwnerServiceProxy.class,CONTROLLER_CLASS_NAME);
	}

}