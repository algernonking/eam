package com.dt.platform.eam.controller;


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


import com.dt.platform.proxy.eam.SupplierServiceProxy;
import com.dt.platform.domain.eam.meta.SupplierVOMeta;
import com.dt.platform.domain.eam.Supplier;
import com.dt.platform.domain.eam.SupplierVO;
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
import com.dt.platform.domain.eam.meta.SupplierMeta;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dt.platform.eam.service.ISupplierService;
import com.github.foxnic.api.validate.annotations.NotNull;

/**
 * <p>
 * 供应商 接口控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2022-05-12 06:32:56
*/

@Api(tags = "供应商")
@ApiSort(0)
@RestController("EamSupplierController")
public class SupplierController extends SuperController {

	@Autowired
	private ISupplierService supplierService;


	/**
	 * 添加供应商
	*/
	@ApiOperation(value = "添加供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "471976330536157184"),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS , value = "商务联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS_INFO , value = "商务联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS , value = "售后联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS_INFO , value = "售后联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.UNIT_CODE , value = "统一社会信用代码" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.ADDRESS , value = "地址" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NOTES , value = "备注" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
	})
	@ApiOperationSupport(order=1)
	@SentinelResource(value = SupplierServiceProxy.INSERT , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.INSERT)
	public Result insert(SupplierVO supplierVO) {
		Result result=supplierService.insert(supplierVO,false);
		return result;
	}



	/**
	 * 删除供应商
	*/
	@ApiOperation(value = "删除供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "471976330536157184")
	})
	@ApiOperationSupport(order=2)
	@NotNull(name = SupplierVOMeta.ID)
	@SentinelResource(value = SupplierServiceProxy.DELETE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.DELETE)
	public Result deleteById(String id) {
		Result result=supplierService.deleteByIdLogical(id);
		return result;
	}


	/**
	 * 批量删除供应商 <br>
	 * 联合主键时，请自行调整实现
	*/
	@ApiOperation(value = "批量删除供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.IDS , value = "主键清单" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
	})
	@ApiOperationSupport(order=3) 
	@NotNull(name = SupplierVOMeta.IDS)
	@SentinelResource(value = SupplierServiceProxy.DELETE_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.DELETE_BY_IDS)
	public Result deleteByIds(List<String> ids) {
		Result result=supplierService.deleteByIdsLogical(ids);
		return result;
	}

	/**
	 * 更新供应商
	*/
	@ApiOperation(value = "更新供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "471976330536157184"),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS , value = "商务联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS_INFO , value = "商务联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS , value = "售后联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS_INFO , value = "售后联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.UNIT_CODE , value = "统一社会信用代码" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.ADDRESS , value = "地址" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NOTES , value = "备注" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
	})
	@ApiOperationSupport( order=4 , ignoreParameters = { SupplierVOMeta.PAGE_INDEX , SupplierVOMeta.PAGE_SIZE , SupplierVOMeta.SEARCH_FIELD , SupplierVOMeta.FUZZY_FIELD , SupplierVOMeta.SEARCH_VALUE , SupplierVOMeta.DIRTY_FIELDS , SupplierVOMeta.SORT_FIELD , SupplierVOMeta.SORT_TYPE , SupplierVOMeta.IDS } )
	@NotNull(name = SupplierVOMeta.ID)
	@SentinelResource(value = SupplierServiceProxy.UPDATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.UPDATE)
	public Result update(SupplierVO supplierVO) {
		Result result=supplierService.update(supplierVO,SaveMode.DIRTY_OR_NOT_NULL_FIELDS,false);
		return result;
	}


	/**
	 * 保存供应商
	*/
	@ApiOperation(value = "保存供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "471976330536157184"),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS , value = "商务联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS_INFO , value = "商务联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS , value = "售后联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS_INFO , value = "售后联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.UNIT_CODE , value = "统一社会信用代码" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.ADDRESS , value = "地址" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NOTES , value = "备注" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { SupplierVOMeta.PAGE_INDEX , SupplierVOMeta.PAGE_SIZE , SupplierVOMeta.SEARCH_FIELD , SupplierVOMeta.FUZZY_FIELD , SupplierVOMeta.SEARCH_VALUE , SupplierVOMeta.DIRTY_FIELDS , SupplierVOMeta.SORT_FIELD , SupplierVOMeta.SORT_TYPE , SupplierVOMeta.IDS } )
	@NotNull(name = SupplierVOMeta.ID)
	@SentinelResource(value = SupplierServiceProxy.SAVE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.SAVE)
	public Result save(SupplierVO supplierVO) {
		Result result=supplierService.save(supplierVO,SaveMode.DIRTY_OR_NOT_NULL_FIELDS,false);
		return result;
	}


	/**
	 * 获取供应商
	*/
	@ApiOperation(value = "获取供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
	})
	@ApiOperationSupport(order=6)
	@NotNull(name = SupplierVOMeta.ID)
	@SentinelResource(value = SupplierServiceProxy.GET_BY_ID , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.GET_BY_ID)
	public Result<Supplier> getById(String id) {
		Result<Supplier> result=new Result<>();
		Supplier supplier=supplierService.getById(id);
		result.success(true).data(supplier);
		return result;
	}


	/**
	 * 批量获取供应商 <br>
	 * 联合主键时，请自行调整实现
	*/
		@ApiOperation(value = "批量获取供应商")
		@ApiImplicitParams({
				@ApiImplicitParam(name = SupplierVOMeta.IDS , value = "主键清单" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
		})
		@ApiOperationSupport(order=3) 
		@NotNull(name = SupplierVOMeta.IDS)
		@SentinelResource(value = SupplierServiceProxy.GET_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.GET_BY_IDS)
	public Result<List<Supplier>> getByIds(List<String> ids) {
		Result<List<Supplier>> result=new Result<>();
		List<Supplier> list=supplierService.queryListByIds(ids);
		result.success(true).data(list);
		return result;
	}


	/**
	 * 查询供应商
	*/
	@ApiOperation(value = "查询供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "471976330536157184"),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS , value = "商务联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS_INFO , value = "商务联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS , value = "售后联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS_INFO , value = "售后联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.UNIT_CODE , value = "统一社会信用代码" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.ADDRESS , value = "地址" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NOTES , value = "备注" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { SupplierVOMeta.PAGE_INDEX , SupplierVOMeta.PAGE_SIZE } )
	@SentinelResource(value = SupplierServiceProxy.QUERY_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.QUERY_LIST)
	public Result<List<Supplier>> queryList(SupplierVO sample) {
		Result<List<Supplier>> result=new Result<>();
		List<Supplier> list=supplierService.queryList(sample);
		result.success(true).data(list);
		return result;
	}


	/**
	 * 分页查询供应商
	*/
	@ApiOperation(value = "分页查询供应商")
	@ApiImplicitParams({
		@ApiImplicitParam(name = SupplierVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "471976330536157184"),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS , value = "商务联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.BUSINESS_CONTACTS_INFO , value = "商务联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS , value = "售后联系人" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.AFTER_SALES_CONTACTS_INFO , value = "售后联系方式" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.UNIT_CODE , value = "统一社会信用代码" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.ADDRESS , value = "地址" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = SupplierVOMeta.SUPPLIER_NOTES , value = "备注" , required = false , dataTypeClass=String.class , example = "杭州BB有限公司"),
	})
	@ApiOperationSupport(order=8)
	@SentinelResource(value = SupplierServiceProxy.QUERY_PAGED_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(SupplierServiceProxy.QUERY_PAGED_LIST)
	public Result<PagedList<Supplier>> queryPagedList(SupplierVO sample) {
		Result<PagedList<Supplier>> result=new Result<>();
		PagedList<Supplier> list=supplierService.queryPagedList(sample,sample.getPageSize(),sample.getPageIndex());
		result.success(true).data(list);
		return result;
	}



	/**
	 * 导出 Excel
	 * */
	@SentinelResource(value = SupplierServiceProxy.EXPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(SupplierServiceProxy.EXPORT_EXCEL)
	public void exportExcel(SupplierVO  sample,HttpServletResponse response) throws Exception {
		try{
			//生成 Excel 数据
			ExcelWriter ew=supplierService.exportExcel(sample);
			//下载
			DownloadUtil.writeToOutput(response,ew.getWorkBook(),ew.getWorkBookName());
		} catch (Exception e) {
			DownloadUtil.writeDownloadError(response,e);
		}
	}


	/**
	 * 导出 Excel 模板
	 * */
	@SentinelResource(value = SupplierServiceProxy.EXPORT_EXCEL_TEMPLATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(SupplierServiceProxy.EXPORT_EXCEL_TEMPLATE)
	public void exportExcelTemplate(HttpServletResponse response) throws Exception {
		try{
			//生成 Excel 模版
			ExcelWriter ew=supplierService.exportExcelTemplate();
			//下载
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
		} catch (Exception e) {
			DownloadUtil.writeDownloadError(response,e);
		}
	}



	@SentinelResource(value = SupplierServiceProxy.IMPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(SupplierServiceProxy.IMPORT_EXCEL)
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

		List<ValidateResult> errors=supplierService.importExcel(input,0,true);
		if(errors==null || errors.isEmpty()) {
			return ErrorDesc.success();
		} else {
			return ErrorDesc.failure().message("导入失败").data(errors);
		}
	}


}