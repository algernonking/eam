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


import com.dt.platform.proxy.ops.DbInstanceServiceProxy;
import com.dt.platform.domain.ops.meta.DbInstanceVOMeta;
import com.dt.platform.domain.ops.DbInstance;
import com.dt.platform.domain.ops.DbInstanceVO;
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
import com.dt.platform.domain.ops.meta.DbInstanceMeta;
import java.math.BigDecimal;
import com.dt.platform.domain.ops.Host;
import com.dt.platform.domain.ops.ServiceInfo;
import com.dt.platform.domain.ops.meta.HostMeta;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dt.platform.ops.service.IDbInstanceService;
import com.github.foxnic.api.validate.annotations.NotNull;

/**
 * <p>
 * ??????????????? ???????????????
 * </p>
 * @author ?????? , maillank@qq.com
 * @since 2021-10-26 15:28:23
*/

@Api(tags = "???????????????")
@ApiSort(0)
@RestController("OpsDbInstanceController")
public class DbInstanceController extends SuperController {

	@Autowired
	private IDbInstanceService dbInstanceService;


	/**
	 * ?????????????????????
	*/
	@ApiOperation(value = "?????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "478966734288584704"),
		@ApiImplicitParam(name = DbInstanceVOMeta.HOST_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "480863361848840192"),
		@ApiImplicitParam(name = DbInstanceVOMeta.DATABASE_ID , value = "???????????????" , required = false , dataTypeClass=String.class , example = "478685370704203776"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NAME , value = "????????????" , required = false , dataTypeClass=String.class , example = "12hgjhjg "),
		@ApiImplicitParam(name = DbInstanceVOMeta.LOG_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STRATEGY , value = "????????????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TYPE , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_DATAKEEP , value = "??????????????????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TIME , value = "????????????" , required = false , dataTypeClass=Date.class , example = "2021-08-06 12:00:00"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_SIZE , value = "????????????" , required = false , dataTypeClass=BigDecimal.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.LABELS , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w'q'w"),
	})
	@ApiOperationSupport(order=1)
	@SentinelResource(value = DbInstanceServiceProxy.INSERT , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.INSERT)
	public Result insert(DbInstanceVO dbInstanceVO) {
		Result result=dbInstanceService.insert(dbInstanceVO);
		return result;
	}



	/**
	 * ?????????????????????
	*/
	@ApiOperation(value = "?????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "478966734288584704")
	})
	@ApiOperationSupport(order=2)
	@NotNull(name = DbInstanceVOMeta.ID)
	@SentinelResource(value = DbInstanceServiceProxy.DELETE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.DELETE)
	public Result deleteById(String id) {
		Result result=dbInstanceService.deleteByIdLogical(id);
		return result;
	}


	/**
	 * ??????????????????????????? <br>
	 * ???????????????????????????????????????
	*/
	@ApiOperation(value = "???????????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.IDS , value = "????????????" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
	})
	@ApiOperationSupport(order=3) 
	@NotNull(name = DbInstanceVOMeta.IDS)
	@SentinelResource(value = DbInstanceServiceProxy.DELETE_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.DELETE_BY_IDS)
	public Result deleteByIds(List<String> ids) {
		Result result=dbInstanceService.deleteByIdsLogical(ids);
		return result;
	}

	/**
	 * ?????????????????????
	*/
	@ApiOperation(value = "?????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "478966734288584704"),
		@ApiImplicitParam(name = DbInstanceVOMeta.HOST_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "480863361848840192"),
		@ApiImplicitParam(name = DbInstanceVOMeta.DATABASE_ID , value = "???????????????" , required = false , dataTypeClass=String.class , example = "478685370704203776"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NAME , value = "????????????" , required = false , dataTypeClass=String.class , example = "12hgjhjg "),
		@ApiImplicitParam(name = DbInstanceVOMeta.LOG_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STRATEGY , value = "????????????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TYPE , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_DATAKEEP , value = "??????????????????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TIME , value = "????????????" , required = false , dataTypeClass=Date.class , example = "2021-08-06 12:00:00"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_SIZE , value = "????????????" , required = false , dataTypeClass=BigDecimal.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.LABELS , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w'q'w"),
	})
	@ApiOperationSupport( order=4 , ignoreParameters = { DbInstanceVOMeta.PAGE_INDEX , DbInstanceVOMeta.PAGE_SIZE , DbInstanceVOMeta.SEARCH_FIELD , DbInstanceVOMeta.FUZZY_FIELD , DbInstanceVOMeta.SEARCH_VALUE , DbInstanceVOMeta.SORT_FIELD , DbInstanceVOMeta.SORT_TYPE , DbInstanceVOMeta.IDS } )
	@NotNull(name = DbInstanceVOMeta.ID)
	@SentinelResource(value = DbInstanceServiceProxy.UPDATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.UPDATE)
	public Result update(DbInstanceVO dbInstanceVO) {
		Result result=dbInstanceService.update(dbInstanceVO,SaveMode.NOT_NULL_FIELDS);
		return result;
	}


	/**
	 * ?????????????????????
	*/
	@ApiOperation(value = "?????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "478966734288584704"),
		@ApiImplicitParam(name = DbInstanceVOMeta.HOST_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "480863361848840192"),
		@ApiImplicitParam(name = DbInstanceVOMeta.DATABASE_ID , value = "???????????????" , required = false , dataTypeClass=String.class , example = "478685370704203776"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NAME , value = "????????????" , required = false , dataTypeClass=String.class , example = "12hgjhjg "),
		@ApiImplicitParam(name = DbInstanceVOMeta.LOG_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STRATEGY , value = "????????????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TYPE , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_DATAKEEP , value = "??????????????????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TIME , value = "????????????" , required = false , dataTypeClass=Date.class , example = "2021-08-06 12:00:00"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_SIZE , value = "????????????" , required = false , dataTypeClass=BigDecimal.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.LABELS , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w'q'w"),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { DbInstanceVOMeta.PAGE_INDEX , DbInstanceVOMeta.PAGE_SIZE , DbInstanceVOMeta.SEARCH_FIELD , DbInstanceVOMeta.FUZZY_FIELD , DbInstanceVOMeta.SEARCH_VALUE , DbInstanceVOMeta.SORT_FIELD , DbInstanceVOMeta.SORT_TYPE , DbInstanceVOMeta.IDS } )
	@NotNull(name = DbInstanceVOMeta.ID)
	@SentinelResource(value = DbInstanceServiceProxy.SAVE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.SAVE)
	public Result save(DbInstanceVO dbInstanceVO) {
		Result result=dbInstanceService.save(dbInstanceVO,SaveMode.NOT_NULL_FIELDS);
		return result;
	}


	/**
	 * ?????????????????????
	*/
	@ApiOperation(value = "?????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
	})
	@ApiOperationSupport(order=6)
	@NotNull(name = DbInstanceVOMeta.ID)
	@SentinelResource(value = DbInstanceServiceProxy.GET_BY_ID , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.GET_BY_ID)
	public Result<DbInstance> getById(String id) {
		Result<DbInstance> result=new Result<>();
		DbInstance dbInstance=dbInstanceService.getById(id);

		// join ???????????????
		dbInstanceService.dao().fill(dbInstance)
			.with(DbInstanceMeta.HOST)
			.with(DbInstanceMeta.HOST)
			.with(DbInstanceMeta.DATABASE)
			.execute();

		result.success(true).data(dbInstance);
		return result;
	}


	/**
	 * ??????????????????????????? <br>
	 * ???????????????????????????????????????
	*/
		@ApiOperation(value = "???????????????????????????")
		@ApiImplicitParams({
				@ApiImplicitParam(name = DbInstanceVOMeta.IDS , value = "????????????" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
		})
		@ApiOperationSupport(order=3) 
		@NotNull(name = DbInstanceVOMeta.IDS)
		@SentinelResource(value = DbInstanceServiceProxy.GET_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.GET_BY_IDS)
	public Result<List<DbInstance>> getByIds(List<String> ids) {
		Result<List<DbInstance>> result=new Result<>();
		List<DbInstance> list=dbInstanceService.getByIds(ids);
		result.success(true).data(list);
		return result;
	}


	/**
	 * ?????????????????????
	*/
	@ApiOperation(value = "?????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "478966734288584704"),
		@ApiImplicitParam(name = DbInstanceVOMeta.HOST_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "480863361848840192"),
		@ApiImplicitParam(name = DbInstanceVOMeta.DATABASE_ID , value = "???????????????" , required = false , dataTypeClass=String.class , example = "478685370704203776"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NAME , value = "????????????" , required = false , dataTypeClass=String.class , example = "12hgjhjg "),
		@ApiImplicitParam(name = DbInstanceVOMeta.LOG_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STRATEGY , value = "????????????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TYPE , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_DATAKEEP , value = "??????????????????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TIME , value = "????????????" , required = false , dataTypeClass=Date.class , example = "2021-08-06 12:00:00"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_SIZE , value = "????????????" , required = false , dataTypeClass=BigDecimal.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.LABELS , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w'q'w"),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { DbInstanceVOMeta.PAGE_INDEX , DbInstanceVOMeta.PAGE_SIZE } )
	@SentinelResource(value = DbInstanceServiceProxy.QUERY_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.QUERY_LIST)
	public Result<List<DbInstance>> queryList(DbInstanceVO sample) {
		Result<List<DbInstance>> result=new Result<>();
		List<DbInstance> list=dbInstanceService.queryList(sample);
		result.success(true).data(list);
		return result;
	}


	/**
	 * ???????????????????????????
	*/
	@ApiOperation(value = "???????????????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = DbInstanceVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "478966734288584704"),
		@ApiImplicitParam(name = DbInstanceVOMeta.HOST_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "480863361848840192"),
		@ApiImplicitParam(name = DbInstanceVOMeta.DATABASE_ID , value = "???????????????" , required = false , dataTypeClass=String.class , example = "478685370704203776"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NAME , value = "????????????" , required = false , dataTypeClass=String.class , example = "12hgjhjg "),
		@ApiImplicitParam(name = DbInstanceVOMeta.LOG_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STRATEGY , value = "????????????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TYPE , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_DATAKEEP , value = "??????????????????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_METHOD , value = "????????????" , required = false , dataTypeClass=String.class , example = "[]"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_TIME , value = "????????????" , required = false , dataTypeClass=Date.class , example = "2021-08-06 12:00:00"),
		@ApiImplicitParam(name = DbInstanceVOMeta.BACKUP_SIZE , value = "????????????" , required = false , dataTypeClass=BigDecimal.class),
		@ApiImplicitParam(name = DbInstanceVOMeta.LABELS , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w"),
		@ApiImplicitParam(name = DbInstanceVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class , example = "q'w'q'w"),
	})
	@ApiOperationSupport(order=8)
	@SentinelResource(value = DbInstanceServiceProxy.QUERY_PAGED_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(DbInstanceServiceProxy.QUERY_PAGED_LIST)
	public Result<PagedList<DbInstance>> queryPagedList(DbInstanceVO sample) {
		Result<PagedList<DbInstance>> result=new Result<>();
		PagedList<DbInstance> list=dbInstanceService.queryPagedList(sample,sample.getPageSize(),sample.getPageIndex());

		// join ???????????????
		dbInstanceService.dao().fill(list)
			.with(DbInstanceMeta.HOST)
			.with(DbInstanceMeta.HOST)
			.with(DbInstanceMeta.DATABASE)
			.execute();

		result.success(true).data(list);
		return result;
	}



	/**
	 * ?????? Excel
	 * */
	@SentinelResource(value = DbInstanceServiceProxy.EXPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(DbInstanceServiceProxy.EXPORT_EXCEL)
	public void exportExcel(DbInstanceVO  sample,HttpServletResponse response) throws Exception {
			//?????? Excel ??????
			ExcelWriter ew=dbInstanceService.exportExcel(sample);
			//??????
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
	}


	/**
	 * ?????? Excel ??????
	 * */
	@SentinelResource(value = DbInstanceServiceProxy.EXPORT_EXCEL_TEMPLATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(DbInstanceServiceProxy.EXPORT_EXCEL_TEMPLATE)
	public void exportExcelTemplate(HttpServletResponse response) throws Exception {
			//?????? Excel ??????
			ExcelWriter ew=dbInstanceService.exportExcelTemplate();
			//??????
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
		}



	@SentinelResource(value = DbInstanceServiceProxy.IMPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(DbInstanceServiceProxy.IMPORT_EXCEL)
	public Result importExcel(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {

		//?????????????????????
		Map<String, MultipartFile> map = request.getFileMap();
		InputStream input=null;
		for (MultipartFile mf : map.values()) {
			input=StreamUtil.bytes2input(mf.getBytes());
			break;
		}

		if(input==null) {
			return ErrorDesc.failure().message("?????????????????????");
		}

		List<ValidateResult> errors=dbInstanceService.importExcel(input,0,true);
		if(errors==null || errors.isEmpty()) {
			return ErrorDesc.success();
		} else {
			return ErrorDesc.failure().message("????????????").data(errors);
		}
	}


}