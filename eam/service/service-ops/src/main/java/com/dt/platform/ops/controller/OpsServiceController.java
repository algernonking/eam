package com.dt.platform.ops.controller;

 
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.github.foxnic.web.framework.web.SuperController;
import org.github.foxnic.web.framework.sentinel.SentinelExceptionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.csp.sentinel.annotation.SentinelResource;


import com.dt.platform.proxy.ops.OpsServiceServiceProxy;
import com.dt.platform.domain.ops.meta.OpsServiceVOMeta;
import com.dt.platform.domain.ops.OpsService;
import com.dt.platform.domain.ops.OpsServiceVO;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.SaveMode;
import com.github.foxnic.dao.excel.ExcelWriter;
import com.github.foxnic.springboot.web.DownloadUtil;
import com.github.foxnic.dao.data.PagedList;
import java.util.Date;
import java.sql.Timestamp;
import com.github.foxnic.api.error.ErrorDesc;
import com.github.foxnic.commons.io.StreamUtil;
import java.util.Map;
import com.github.foxnic.dao.excel.ValidateResult;
import java.io.InputStream;
import com.dt.platform.domain.ops.meta.OpsServiceMeta;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dt.platform.ops.service.IOpsServiceService;
import com.github.foxnic.api.validate.annotations.NotNull;

/**
 * <p>
 * 服务类型 接口控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-08-01 21:59:43
*/

@Api(tags = "服务类型")
@ApiSort(0)
@RestController("OpsServiceController")
public class OpsServiceController extends SuperController {

	@Autowired
	private IOpsServiceService opsServiceService;

	
	/**
	 * 添加服务类型
	*/
	@ApiOperation(value = "添加服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.TYPE , value = "服务类型" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "Oracle"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NOTES , value = "备注" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=1)
	@NotNull(name = OpsServiceVOMeta.ID)
	@SentinelResource(value = OpsServiceServiceProxy.INSERT , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.INSERT)
	public Result insert(OpsServiceVO opsServiceVO) {
		Result result=opsServiceService.insert(opsServiceVO);
		return result;
	}

	
	/**
	 * 删除服务类型
	*/
	@ApiOperation(value = "删除服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1")
	})
	@ApiOperationSupport(order=2)
	@NotNull(name = OpsServiceVOMeta.ID)
	@SentinelResource(value = OpsServiceServiceProxy.DELETE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.DELETE)
	public Result deleteById(String id) {
		Result result=opsServiceService.deleteByIdLogical(id);
		return result;
	}
	
	
	/**
	 * 批量删除服务类型 <br>
	 * 联合主键时，请自行调整实现
	*/
	@ApiOperation(value = "批量删除服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.IDS , value = "主键清单" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
	})
	@ApiOperationSupport(order=3) 
	@NotNull(name = OpsServiceVOMeta.IDS)
	@SentinelResource(value = OpsServiceServiceProxy.DELETE_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.DELETE_BY_IDS)
	public Result deleteByIds(List<String> ids) {
		Result result=opsServiceService.deleteByIdsLogical(ids);
		return result;
	}
	
	/**
	 * 更新服务类型
	*/
	@ApiOperation(value = "更新服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.TYPE , value = "服务类型" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "Oracle"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NOTES , value = "备注" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport( order=4 , ignoreParameters = { OpsServiceVOMeta.PAGE_INDEX , OpsServiceVOMeta.PAGE_SIZE , OpsServiceVOMeta.SEARCH_FIELD , OpsServiceVOMeta.FUZZY_FIELD , OpsServiceVOMeta.SEARCH_VALUE , OpsServiceVOMeta.SORT_FIELD , OpsServiceVOMeta.SORT_TYPE , OpsServiceVOMeta.IDS } ) 
	@NotNull(name = OpsServiceVOMeta.ID)
	@SentinelResource(value = OpsServiceServiceProxy.UPDATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.UPDATE)
	public Result update(OpsServiceVO opsServiceVO) {
		Result result=opsServiceService.update(opsServiceVO,SaveMode.NOT_NULL_FIELDS);
		return result;
	}
	
	
	/**
	 * 保存服务类型
	*/
	@ApiOperation(value = "保存服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.TYPE , value = "服务类型" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "Oracle"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NOTES , value = "备注" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { OpsServiceVOMeta.PAGE_INDEX , OpsServiceVOMeta.PAGE_SIZE , OpsServiceVOMeta.SEARCH_FIELD , OpsServiceVOMeta.FUZZY_FIELD , OpsServiceVOMeta.SEARCH_VALUE , OpsServiceVOMeta.SORT_FIELD , OpsServiceVOMeta.SORT_TYPE , OpsServiceVOMeta.IDS } )
	@NotNull(name = OpsServiceVOMeta.ID)
	@SentinelResource(value = OpsServiceServiceProxy.SAVE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.SAVE)
	public Result save(OpsServiceVO opsServiceVO) {
		Result result=opsServiceService.save(opsServiceVO,SaveMode.NOT_NULL_FIELDS);
		return result;
	}

	
	/**
	 * 获取服务类型
	*/
	@ApiOperation(value = "获取服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
	})
	@ApiOperationSupport(order=6)
	@NotNull(name = OpsServiceVOMeta.ID)
	@SentinelResource(value = OpsServiceServiceProxy.GET_BY_ID , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.GET_BY_ID)
	public Result<OpsService> getById(String id) {
		Result<OpsService> result=new Result<>();
		OpsService opsService=opsServiceService.getById(id);
		result.success(true).data(opsService);
		return result;
	}


	/**
	 * 批量删除服务类型 <br>
	 * 联合主键时，请自行调整实现
	*/
		@ApiOperation(value = "批量删除服务类型")
		@ApiImplicitParams({
				@ApiImplicitParam(name = OpsServiceVOMeta.IDS , value = "主键清单" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
		})
		@ApiOperationSupport(order=3) 
		@NotNull(name = OpsServiceVOMeta.IDS)
		@SentinelResource(value = OpsServiceServiceProxy.GET_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.GET_BY_IDS)
	public Result<List<OpsService>> getByIds(List<String> ids) {
		Result<List<OpsService>> result=new Result<>();
		List<OpsService> list=opsServiceService.getByIds(ids);
		result.success(true).data(list);
		return result;
	}

	
	/**
	 * 查询服务类型
	*/
	@ApiOperation(value = "查询服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.TYPE , value = "服务类型" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "Oracle"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NOTES , value = "备注" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { OpsServiceVOMeta.PAGE_INDEX , OpsServiceVOMeta.PAGE_SIZE } )
	@SentinelResource(value = OpsServiceServiceProxy.QUERY_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.QUERY_LIST)
	public Result<List<OpsService>> queryList(OpsServiceVO sample) {
		Result<List<OpsService>> result=new Result<>();
		List<OpsService> list=opsServiceService.queryList(sample);
		result.success(true).data(list);
		return result;
	}

	
	/**
	 * 分页查询服务类型
	*/
	@ApiOperation(value = "分页查询服务类型")
	@ApiImplicitParams({
		@ApiImplicitParam(name = OpsServiceVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.TYPE , value = "服务类型" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "Oracle"),
		@ApiImplicitParam(name = OpsServiceVOMeta.SERVICE_NOTES , value = "备注" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=8)
	@SentinelResource(value = OpsServiceServiceProxy.QUERY_PAGED_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(OpsServiceServiceProxy.QUERY_PAGED_LIST)
	public Result<PagedList<OpsService>> queryPagedList(OpsServiceVO sample) {
		Result<PagedList<OpsService>> result=new Result<>();
		PagedList<OpsService> list=opsServiceService.queryPagedList(sample,sample.getPageSize(),sample.getPageIndex());
		result.success(true).data(list);
		return result;
	}



	/**
	 * 导出 Excel
	 * */
	@SentinelResource(value = OpsServiceServiceProxy.EXPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(OpsServiceServiceProxy.EXPORT_EXCEL)
	public void exportExcel(OpsServiceVO  sample,HttpServletResponse response) throws Exception {
			//生成 Excel 数据
			ExcelWriter ew=opsServiceService.exportExcel(sample);
			//下载
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
	}


	/**
	 * 导出 Excel 模板
	 * */
	@SentinelResource(value = OpsServiceServiceProxy.EXPORT_EXCEL_TEMPLATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(OpsServiceServiceProxy.EXPORT_EXCEL_TEMPLATE)
	public void exportExcelTemplate(HttpServletResponse response) throws Exception {
			//生成 Excel 模版
			ExcelWriter ew=opsServiceService.exportExcelTemplate();
			//下载
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
		}




	@SentinelResource(value = OpsServiceServiceProxy.IMPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(OpsServiceServiceProxy.IMPORT_EXCEL)
	public Result importExcel(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

			//获得上传的文件
			Map<String, MultipartFile> map = request.getFileMap();
			InputStream input=null;
			for (MultipartFile mf : map.values()) {
				input=StreamUtil.bytes2input(mf.getBytes());
				break;
			}

			if(input==null) {
				return ErrorDesc.failure().message("缺少上传的文件");
			}

			List<ValidateResult> errors=opsServiceService.importExcel(input,0,true);
			if(errors==null || errors.isEmpty()) {
				return ErrorDesc.success();
			} else {
				return ErrorDesc.failure().message("导入失败").data(errors);
			}
		}


}