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


import com.dt.platform.proxy.ops.MonitorNodeServiceProxy;
import com.dt.platform.domain.ops.meta.MonitorNodeVOMeta;
import com.dt.platform.domain.ops.MonitorNode;
import com.dt.platform.domain.ops.MonitorNodeVO;
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
import com.dt.platform.domain.ops.meta.MonitorNodeMeta;
import com.dt.platform.domain.ops.MonitorVoucher;
import com.dt.platform.domain.ops.MonitorNodeDb;
import com.dt.platform.domain.ops.MonitorNodeValue;
import com.dt.platform.domain.ops.MonitorNodeGroup;
import com.dt.platform.domain.ops.MonitorNodeType;
import com.dt.platform.domain.ops.MonitorNodeSubtype;
import com.dt.platform.domain.ops.MonitorTpl;
import io.swagger.annotations.Api;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.dt.platform.ops.service.IMonitorNodeService;
import com.github.foxnic.api.validate.annotations.NotNull;

/**
 * <p>
 * ?????? ???????????????
 * </p>
 * @author ?????? , maillank@qq.com
 * @since 2022-02-22 17:47:11
*/

@Api(tags = "??????")
@ApiSort(0)
@RestController("OpsMonitorNodeController")
public class MonitorNodeController extends SuperController {

	@Autowired
	private IMonitorNodeService monitorNodeService;


	/**
	 * ????????????
	*/
	@ApiOperation(value = "????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_IP , value = "IP" , required = false , dataTypeClass=String.class , example = "121.43.103.102"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.PID , value = "?????????" , required = false , dataTypeClass=String.class , example = "0"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.TYPE , value = "??????" , required = false , dataTypeClass=String.class , example = "os"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SUB_TYPE , value = "?????????" , required = false , dataTypeClass=String.class , example = "Redhat"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.GROUP_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "543027032871665664"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME , value = "?????????" , required = false , dataTypeClass=String.class , example = "192.168.1.1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME_SHOW , value = "???????????????" , required = false , dataTypeClass=String.class , example = "121.43.103.10222"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_TYPE , value = "??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_ENABLED , value = "????????????" , required = false , dataTypeClass=String.class , example = "disabled"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "online"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_VOUCHER_ID , value = "??????(SSH)" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_PORT , value = "SSH??????" , required = false , dataTypeClass=Integer.class , example = "22"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.AGENT_PORT , value = "Agent??????" , required = false , dataTypeClass=Integer.class , example = "10052"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.ZABBIX_AGENT_PORT , value = "Zabbix????????????" , required = false , dataTypeClass=Integer.class , example = "10050"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_PORT , value = "Snmp??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_VERSION , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_COMMUNITY , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JMX_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.IMPI_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "623"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JDBC_URL , value = "Jdbc??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=1)
	@SentinelResource(value = MonitorNodeServiceProxy.INSERT , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.INSERT)
	public Result insert(MonitorNodeVO monitorNodeVO) {
		Result result=monitorNodeService.insert(monitorNodeVO,false);
		return result;
	}



	/**
	 * ????????????
	*/
	@ApiOperation(value = "????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1")
	})
	@ApiOperationSupport(order=2)
	@NotNull(name = MonitorNodeVOMeta.ID)
	@SentinelResource(value = MonitorNodeServiceProxy.DELETE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.DELETE)
	public Result deleteById(String id) {
		Result result=monitorNodeService.deleteByIdLogical(id);
		return result;
	}


	/**
	 * ?????????????????? <br>
	 * ???????????????????????????????????????
	*/
	@ApiOperation(value = "??????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.IDS , value = "????????????" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
	})
	@ApiOperationSupport(order=3) 
	@NotNull(name = MonitorNodeVOMeta.IDS)
	@SentinelResource(value = MonitorNodeServiceProxy.DELETE_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.DELETE_BY_IDS)
	public Result deleteByIds(List<String> ids) {
		Result result=monitorNodeService.deleteByIdsLogical(ids);
		return result;
	}

	/**
	 * ????????????
	*/
	@ApiOperation(value = "????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_IP , value = "IP" , required = false , dataTypeClass=String.class , example = "121.43.103.102"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.PID , value = "?????????" , required = false , dataTypeClass=String.class , example = "0"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.TYPE , value = "??????" , required = false , dataTypeClass=String.class , example = "os"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SUB_TYPE , value = "?????????" , required = false , dataTypeClass=String.class , example = "Redhat"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.GROUP_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "543027032871665664"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME , value = "?????????" , required = false , dataTypeClass=String.class , example = "192.168.1.1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME_SHOW , value = "???????????????" , required = false , dataTypeClass=String.class , example = "121.43.103.10222"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_TYPE , value = "??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_ENABLED , value = "????????????" , required = false , dataTypeClass=String.class , example = "disabled"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "online"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_VOUCHER_ID , value = "??????(SSH)" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_PORT , value = "SSH??????" , required = false , dataTypeClass=Integer.class , example = "22"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.AGENT_PORT , value = "Agent??????" , required = false , dataTypeClass=Integer.class , example = "10052"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.ZABBIX_AGENT_PORT , value = "Zabbix????????????" , required = false , dataTypeClass=Integer.class , example = "10050"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_PORT , value = "Snmp??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_VERSION , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_COMMUNITY , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JMX_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.IMPI_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "623"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JDBC_URL , value = "Jdbc??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport( order=4 , ignoreParameters = { MonitorNodeVOMeta.PAGE_INDEX , MonitorNodeVOMeta.PAGE_SIZE , MonitorNodeVOMeta.SEARCH_FIELD , MonitorNodeVOMeta.FUZZY_FIELD , MonitorNodeVOMeta.SEARCH_VALUE , MonitorNodeVOMeta.DIRTY_FIELDS , MonitorNodeVOMeta.SORT_FIELD , MonitorNodeVOMeta.SORT_TYPE , MonitorNodeVOMeta.IDS } )
	@NotNull(name = MonitorNodeVOMeta.ID)
	@SentinelResource(value = MonitorNodeServiceProxy.UPDATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.UPDATE)
	public Result update(MonitorNodeVO monitorNodeVO) {
		Result result=monitorNodeService.update(monitorNodeVO,SaveMode.DIRTY_OR_NOT_NULL_FIELDS,false);
		return result;
	}


	/**
	 * ????????????
	*/
	@ApiOperation(value = "????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_IP , value = "IP" , required = false , dataTypeClass=String.class , example = "121.43.103.102"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.PID , value = "?????????" , required = false , dataTypeClass=String.class , example = "0"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.TYPE , value = "??????" , required = false , dataTypeClass=String.class , example = "os"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SUB_TYPE , value = "?????????" , required = false , dataTypeClass=String.class , example = "Redhat"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.GROUP_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "543027032871665664"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME , value = "?????????" , required = false , dataTypeClass=String.class , example = "192.168.1.1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME_SHOW , value = "???????????????" , required = false , dataTypeClass=String.class , example = "121.43.103.10222"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_TYPE , value = "??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_ENABLED , value = "????????????" , required = false , dataTypeClass=String.class , example = "disabled"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "online"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_VOUCHER_ID , value = "??????(SSH)" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_PORT , value = "SSH??????" , required = false , dataTypeClass=Integer.class , example = "22"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.AGENT_PORT , value = "Agent??????" , required = false , dataTypeClass=Integer.class , example = "10052"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.ZABBIX_AGENT_PORT , value = "Zabbix????????????" , required = false , dataTypeClass=Integer.class , example = "10050"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_PORT , value = "Snmp??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_VERSION , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_COMMUNITY , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JMX_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.IMPI_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "623"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JDBC_URL , value = "Jdbc??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { MonitorNodeVOMeta.PAGE_INDEX , MonitorNodeVOMeta.PAGE_SIZE , MonitorNodeVOMeta.SEARCH_FIELD , MonitorNodeVOMeta.FUZZY_FIELD , MonitorNodeVOMeta.SEARCH_VALUE , MonitorNodeVOMeta.DIRTY_FIELDS , MonitorNodeVOMeta.SORT_FIELD , MonitorNodeVOMeta.SORT_TYPE , MonitorNodeVOMeta.IDS } )
	@NotNull(name = MonitorNodeVOMeta.ID)
	@SentinelResource(value = MonitorNodeServiceProxy.SAVE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.SAVE)
	public Result save(MonitorNodeVO monitorNodeVO) {
		Result result=monitorNodeService.save(monitorNodeVO,SaveMode.DIRTY_OR_NOT_NULL_FIELDS,false);
		return result;
	}


	/**
	 * ????????????
	*/
	@ApiOperation(value = "????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
	})
	@ApiOperationSupport(order=6)
	@NotNull(name = MonitorNodeVOMeta.ID)
	@SentinelResource(value = MonitorNodeServiceProxy.GET_BY_ID , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.GET_BY_ID)
	public Result<MonitorNode> getById(String id) {
		Result<MonitorNode> result=new Result<>();
		MonitorNode monitorNode=monitorNodeService.getById(id);
		// join ???????????????
		monitorNodeService.dao().fill(monitorNode)
			.with(MonitorNodeMeta.MONITOR_TPL_LIST)
			.with(MonitorNodeMeta.SSH_VOUCHER)
			.with(MonitorNodeMeta.MONITOR_NODE_GROUP)
			.with(MonitorNodeMeta.MONITOR_NODE_TYPE)
			.execute();
		result.success(true).data(monitorNode);
		return result;
	}


	/**
	 * ?????????????????? <br>
	 * ???????????????????????????????????????
	*/
		@ApiOperation(value = "??????????????????")
		@ApiImplicitParams({
				@ApiImplicitParam(name = MonitorNodeVOMeta.IDS , value = "????????????" , required = true , dataTypeClass=List.class , example = "[1,3,4]")
		})
		@ApiOperationSupport(order=3) 
		@NotNull(name = MonitorNodeVOMeta.IDS)
		@SentinelResource(value = MonitorNodeServiceProxy.GET_BY_IDS , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.GET_BY_IDS)
	public Result<List<MonitorNode>> getByIds(List<String> ids) {
		Result<List<MonitorNode>> result=new Result<>();
		List<MonitorNode> list=monitorNodeService.getByIds(ids);
		result.success(true).data(list);
		return result;
	}


	/**
	 * ????????????
	*/
	@ApiOperation(value = "????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_IP , value = "IP" , required = false , dataTypeClass=String.class , example = "121.43.103.102"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.PID , value = "?????????" , required = false , dataTypeClass=String.class , example = "0"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.TYPE , value = "??????" , required = false , dataTypeClass=String.class , example = "os"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SUB_TYPE , value = "?????????" , required = false , dataTypeClass=String.class , example = "Redhat"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.GROUP_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "543027032871665664"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME , value = "?????????" , required = false , dataTypeClass=String.class , example = "192.168.1.1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME_SHOW , value = "???????????????" , required = false , dataTypeClass=String.class , example = "121.43.103.10222"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_TYPE , value = "??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_ENABLED , value = "????????????" , required = false , dataTypeClass=String.class , example = "disabled"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "online"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_VOUCHER_ID , value = "??????(SSH)" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_PORT , value = "SSH??????" , required = false , dataTypeClass=Integer.class , example = "22"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.AGENT_PORT , value = "Agent??????" , required = false , dataTypeClass=Integer.class , example = "10052"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.ZABBIX_AGENT_PORT , value = "Zabbix????????????" , required = false , dataTypeClass=Integer.class , example = "10050"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_PORT , value = "Snmp??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_VERSION , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_COMMUNITY , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JMX_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.IMPI_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "623"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JDBC_URL , value = "Jdbc??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=5 ,  ignoreParameters = { MonitorNodeVOMeta.PAGE_INDEX , MonitorNodeVOMeta.PAGE_SIZE } )
	@SentinelResource(value = MonitorNodeServiceProxy.QUERY_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.QUERY_LIST)
	public Result<List<MonitorNode>> queryList(MonitorNodeVO sample) {
		Result<List<MonitorNode>> result=new Result<>();
		List<MonitorNode> list=monitorNodeService.queryList(sample);
		result.success(true).data(list);
		return result;
	}


	/**
	 * ??????????????????
	*/
	@ApiOperation(value = "??????????????????")
	@ApiImplicitParams({
		@ApiImplicitParam(name = MonitorNodeVOMeta.ID , value = "??????" , required = true , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_IP , value = "IP" , required = false , dataTypeClass=String.class , example = "121.43.103.102"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.PID , value = "?????????" , required = false , dataTypeClass=String.class , example = "0"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.TYPE , value = "??????" , required = false , dataTypeClass=String.class , example = "os"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SUB_TYPE , value = "?????????" , required = false , dataTypeClass=String.class , example = "Redhat"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.GROUP_ID , value = "????????????" , required = false , dataTypeClass=String.class , example = "543027032871665664"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME , value = "?????????" , required = false , dataTypeClass=String.class , example = "192.168.1.1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_NAME_SHOW , value = "???????????????" , required = false , dataTypeClass=String.class , example = "121.43.103.10222"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_TYPE , value = "??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NODE_ENABLED , value = "????????????" , required = false , dataTypeClass=String.class , example = "disabled"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.STATUS , value = "????????????" , required = false , dataTypeClass=String.class , example = "online"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_VOUCHER_ID , value = "??????(SSH)" , required = false , dataTypeClass=String.class , example = "1"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SSH_PORT , value = "SSH??????" , required = false , dataTypeClass=Integer.class , example = "22"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.AGENT_PORT , value = "Agent??????" , required = false , dataTypeClass=Integer.class , example = "10052"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.ZABBIX_AGENT_PORT , value = "Zabbix????????????" , required = false , dataTypeClass=Integer.class , example = "10050"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_PORT , value = "Snmp??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_VERSION , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.SNMP_COMMUNITY , value = "Snmp??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JMX_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "12345"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.IMPI_PORT , value = "Jmx??????" , required = false , dataTypeClass=Integer.class , example = "623"),
		@ApiImplicitParam(name = MonitorNodeVOMeta.JDBC_URL , value = "Jdbc??????" , required = false , dataTypeClass=String.class),
		@ApiImplicitParam(name = MonitorNodeVOMeta.NOTES , value = "??????" , required = false , dataTypeClass=String.class),
	})
	@ApiOperationSupport(order=8)
	@SentinelResource(value = MonitorNodeServiceProxy.QUERY_PAGED_LIST , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@PostMapping(MonitorNodeServiceProxy.QUERY_PAGED_LIST)
	public Result<PagedList<MonitorNode>> queryPagedList(MonitorNodeVO sample) {
		Result<PagedList<MonitorNode>> result=new Result<>();
		PagedList<MonitorNode> list=monitorNodeService.queryPagedList(sample,sample.getPageSize(),sample.getPageIndex());
		// join ???????????????
		monitorNodeService.dao().fill(list)
			.with(MonitorNodeMeta.MONITOR_TPL_LIST)
			.with(MonitorNodeMeta.SSH_VOUCHER)
			.with(MonitorNodeMeta.MONITOR_NODE_GROUP)
			.with(MonitorNodeMeta.MONITOR_NODE_TYPE)
			.execute();
		result.success(true).data(list);
		return result;
	}



	/**
	 * ?????? Excel
	 * */
	@SentinelResource(value = MonitorNodeServiceProxy.EXPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(MonitorNodeServiceProxy.EXPORT_EXCEL)
	public void exportExcel(MonitorNodeVO  sample,HttpServletResponse response) throws Exception {
		try{
			//?????? Excel ??????
			ExcelWriter ew=monitorNodeService.exportExcel(sample);
			//??????
			DownloadUtil.writeToOutput(response,ew.getWorkBook(),ew.getWorkBookName());
		} catch (Exception e) {
			DownloadUtil.writeDownloadError(response,e);
		}
	}


	/**
	 * ?????? Excel ??????
	 * */
	@SentinelResource(value = MonitorNodeServiceProxy.EXPORT_EXCEL_TEMPLATE , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(MonitorNodeServiceProxy.EXPORT_EXCEL_TEMPLATE)
	public void exportExcelTemplate(HttpServletResponse response) throws Exception {
		try{
			//?????? Excel ??????
			ExcelWriter ew=monitorNodeService.exportExcelTemplate();
			//??????
			DownloadUtil.writeToOutput(response, ew.getWorkBook(), ew.getWorkBookName());
		} catch (Exception e) {
			DownloadUtil.writeDownloadError(response,e);
		}
	}



	@SentinelResource(value = MonitorNodeServiceProxy.IMPORT_EXCEL , blockHandlerClass = { SentinelExceptionUtil.class } , blockHandler = SentinelExceptionUtil.HANDLER )
	@RequestMapping(MonitorNodeServiceProxy.IMPORT_EXCEL)
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

		List<ValidateResult> errors=monitorNodeService.importExcel(input,0,true);
		if(errors==null || errors.isEmpty()) {
			return ErrorDesc.success();
		} else {
			return ErrorDesc.failure().message("????????????").data(errors);
		}
	}


}