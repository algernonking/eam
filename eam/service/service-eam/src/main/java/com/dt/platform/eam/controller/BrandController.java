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


import com.dt.platform.proxy.eam.BrandServiceProxy;
import com.dt.platform.domain.eam.meta.BrandVOMeta;
import com.dt.platform.domain.eam.Brand;
import com.dt.platform.domain.eam.BrandVO;
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
import com.dt.platform.domain.eam.meta.BrandMeta;
import java.math.BigDecimal;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dt.platform.eam.service.IBrandService;
import com.github.foxnic.api.validate.annotations.NotNull;

/**
 * <p>
 * 品牌 接口控制器
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2022-05-27 04:48:15
*/

@Api(tags = "品牌")
@ApiSort(0)
@RestController("EamBrandController")
public class BrandController extends SuperController {

	@Autowired
	private IBrandService brandService;


	/**
	 * 添加品牌
	*/
	@ApiOperation(value = "添加品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "569145667469967360"),
		@ApiImplicitParam(name = BrandVOMeta.STATUS , value = "状态" , required = false , dataTypeClass=String.class , example = "enable"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_CODE , value = "编码" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.SORT , value = "排序" , required = false , dataTypeClass=BigDecimal.class),
	})
	@ApiOperationSupport(order=1)
	@SentinelResource(value = BrandServiceProxy.INSERT , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.INSERT)
	public Result insert(BrandVO brandVO) {
		Result result=brandService.insert(brandVO,false);
		return result;
	}



	/**
	 * 删除品牌
	*/
	@ApiOperation(value = "删除品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "569145667469967360")
	})
	@ApiOperationSupport(order=2)
	@NotNull(name = BrandVOMeta.ID)
	@SentinelResource(value = BrandServiceProxy.DELETE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.DELETE)
	public Result deleteById(String id) {
		Result result=brandService.deleteByIdLogical(id);
		return result;
	}


	/**
	 * 批量删除品牌 <br>
	 * 联合主键时，请自行调整实现
	*/
	@ApiOperation(value = "批量删除品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.IDS , value = "主键清单" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
	})
	@ApiOperationSupport(order=3) 
	@NotNull(name = BrandVOMeta.IDS)
	@SentinelResource(value = BrandServiceProxy.DELETE_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.DELETE_BY_IDS)
	public Result deleteByIds(List<String> ids) {
		Result result=brandService.deleteByIdsLogical(ids);
		return result;
	}

	/**
	 * 更新品牌
	*/
	@ApiOperation(value = "更新品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "569145667469967360"),
		@ApiImplicitParam(name = BrandVOMeta.STATUS , value = "状态" , required = false , dataTypeClass=String.class , example = "enable"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_CODE , value = "编码" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.SORT , value = "排序" , required = false , dataTypeClass=BigDecimal.class),
	})
	@ApiOperationSupport( order=4 , ignoreParameters = { BrandVOMeta.PAGE_INDEX , BrandVOMeta.PAGE_SIZE , BrandVOMeta.SEARCH_FIELD , BrandVOMeta.FUZZY_FIELD , BrandVOMeta.SEARCH_VALUE , BrandVOMeta.DIRTY_FIELDS , BrandVOMeta.SORT_FIELD , BrandVOMeta.SORT_TYPE , BrandVOMeta.IDS } )
	@NotNull(name = BrandVOMeta.ID)
	@SentinelResource(value = BrandServiceProxy.UPDATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.UPDATE)
	public Result update(BrandVO brandVO) {
		Result result=brandService.update(brandVO,SaveMode.DIRTY_OR_NOT_NULL_FIELDS,false);
		return result;
	}


	/**
	 * 保存品牌
	*/
	@ApiOperation(value = "保存品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "569145667469967360"),
		@ApiImplicitParam(name = BrandVOMeta.STATUS , value = "状态" , required = false , dataTypeClass=String.class , example = "enable"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_CODE , value = "编码" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.SORT , value = "排序" , required = false , dataTypeClass=BigDecimal.class),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { BrandVOMeta.PAGE_INDEX , BrandVOMeta.PAGE_SIZE , BrandVOMeta.SEARCH_FIELD , BrandVOMeta.FUZZY_FIELD , BrandVOMeta.SEARCH_VALUE , BrandVOMeta.DIRTY_FIELDS , BrandVOMeta.SORT_FIELD , BrandVOMeta.SORT_TYPE , BrandVOMeta.IDS } )
	@NotNull(name = BrandVOMeta.ID)
	@SentinelResource(value = BrandServiceProxy.SAVE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.SAVE)
	public Result save(BrandVO brandVO) {
		Result result=brandService.save(brandVO,SaveMode.DIRTY_OR_NOT_NULL_FIELDS,false);
		return result;
	}


	/**
	 * 获取品牌
	*/
	@ApiOperation(value = "获取品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "1"),
	})
	@ApiOperationSupport(order=6)
	@NotNull(name = BrandVOMeta.ID)
	@SentinelResource(value = BrandServiceProxy.GET_BY_ID , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.GET_BY_ID)
	public Result<Brand> getById(String id) {
		Result<Brand> result=new Result<>();
		Brand brand=brandService.getById(id);
		result.success(true).data(brand);
		return result;
	}


	/**
	 * 批量获取品牌 <br>
	 * 联合主键时，请自行调整实现
	*/
		@ApiOperation(value = "批量获取品牌")
		@ApiImplicitParams({
				@ApiImplicitParam(name = BrandVOMeta.IDS , value = "主键清单" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
		})
		@ApiOperationSupport(order=3) 
		@NotNull(name = BrandVOMeta.IDS)
		@SentinelResource(value = BrandServiceProxy.GET_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.GET_BY_IDS)
	public Result<List<Brand>> getByIds(List<String> ids) {
		Result<List<Brand>> result=new Result<>();
		List<Brand> list=brandService.queryListByIds(ids);
		result.success(true).data(list);
		return result;
	}


	/**
	 * 查询品牌
	*/
	@ApiOperation(value = "查询品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "569145667469967360"),
		@ApiImplicitParam(name = BrandVOMeta.STATUS , value = "状态" , required = false , dataTypeClass=String.class , example = "enable"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_CODE , value = "编码" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.SORT , value = "排序" , required = false , dataTypeClass=BigDecimal.class),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { BrandVOMeta.PAGE_INDEX , BrandVOMeta.PAGE_SIZE } )
	@SentinelResource(value = BrandServiceProxy.QUERY_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.QUERY_LIST)
	public Result<List<Brand>> queryList(BrandVO sample) {
		Result<List<Brand>> result=new Result<>();
		List<Brand> list=brandService.queryList(sample);
		result.success(true).data(list);
		return result;
	}


	/**
	 * 分页查询品牌
	*/
	@ApiOperation(value = "分页查询品牌")
	@ApiImplicitParams({
		@ApiImplicitParam(name = BrandVOMeta.ID , value = "主键" , required = true , dataTypeClass=String.class , example = "569145667469967360"),
		@ApiImplicitParam(name = BrandVOMeta.STATUS , value = "状态" , required = false , dataTypeClass=String.class , example = "enable"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_CODE , value = "编码" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.BRAND_NAME , value = "名称" , required = false , dataTypeClass=String.class , example = "联想"),
		@ApiImplicitParam(name = BrandVOMeta.SORT , value = "排序" , required = false , dataTypeClass=BigDecimal.class),
	})
	@ApiOperationSupport(order=8)
	@SentinelResource(value = BrandServiceProxy.QUERY_PAGED_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(BrandServiceProxy.QUERY_PAGED_LIST)
	public Result<PagedList<Brand>> queryPagedList(BrandVO sample) {
		Result<PagedList<Brand>> result=new Result<>();
		PagedList<Brand> list=brandService.queryPagedList(sample,sample.getPageSize(),sample.getPageIndex());
		result.success(true).data(list);
		return result;
	}



	/**
	 * 导出 Excel
	 * */
	@SentinelResource(value = BrandServiceProxy.EXPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(BrandServiceProxy.EXPORT_EXCEL)
	public void exportExcel(BrandVO  sample,HttpServletResponse response) throws Exception {
		try{
			//生成 Excel 数据
			ExcelWriter ew=brandService.exportExcel(sample);
			//下载
			DownloadUtil.writeToOutput(response,ew.getWorkBook(),ew.getWorkBookName());
		} catch (Exception e) {
			DownloadUtil.writeDownloadError(response,e);
		}
	}


	/**
	 * 导出 Excel 模板
	 * */
	@SentinelResource(value = BrandServiceProxy.EXPORT_EXCEL_TEMPLATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(BrandServiceProxy.EXPORT_EXCEL_TEMPLATE)
	public void exportExcelTemplate(HttpServletResponse response) throws Exception {
		try{
			//生成 Excel 模版
			ExcelWriter ew=brandService.exportExcelTemplate();
			//下载
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
		} catch (Exception e) {
			DownloadUtil.writeDownloadError(response,e);
		}
	}



	@SentinelResource(value = BrandServiceProxy.IMPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(BrandServiceProxy.IMPORT_EXCEL)
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

		List<ValidateResult> errors=brandService.importExcel(input,0,true);
		if(errors==null || errors.isEmpty()) {
			return ErrorDesc.success();
		} else {
			return ErrorDesc.failure().message("导入失败").data(errors);
		}
	}


}