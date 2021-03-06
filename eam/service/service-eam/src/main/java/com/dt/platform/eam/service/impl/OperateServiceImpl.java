package com.dt.platform.eam.service.impl;


import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.dt.platform.constants.enums.common.StatusYNEnum;
import com.dt.platform.constants.enums.eam.AssetOwnerCodeEnum;
import com.github.foxnic.commons.lang.StringUtil;
import com.github.foxnic.dao.data.Rcd;
import org.github.foxnic.web.domain.changes.ChangeEvent;
import org.github.foxnic.web.domain.changes.ProcessApproveVO;
import org.github.foxnic.web.domain.changes.ProcessStartVO;
import org.github.foxnic.web.domain.hrm.Organization;
import org.github.foxnic.web.domain.system.Config;
import org.github.foxnic.web.domain.system.ConfigVO;
import org.github.foxnic.web.proxy.system.ConfigServiceProxy;
import org.github.foxnic.web.proxy.utils.SessionUserProxyUtil;
import org.github.foxnic.web.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dt.platform.domain.eam.Operate;
import com.dt.platform.domain.eam.OperateVO;
import java.util.List;
import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.data.PagedList;
import com.github.foxnic.dao.entity.SuperService;
import com.github.foxnic.dao.spec.DAO;
import java.lang.reflect.Field;
import com.github.foxnic.commons.busi.id.IDGenerator;
import com.github.foxnic.sql.expr.ConditionExpr;
import com.github.foxnic.api.error.ErrorDesc;
import com.github.foxnic.dao.excel.ExcelWriter;
import com.github.foxnic.dao.excel.ValidateResult;
import com.github.foxnic.dao.excel.ExcelStructure;
import java.io.InputStream;
import com.github.foxnic.sql.meta.DBField;
import com.github.foxnic.dao.data.SaveMode;
import com.github.foxnic.dao.meta.DBColumnMeta;
import com.github.foxnic.sql.expr.Select;
import java.util.ArrayList;
import com.dt.platform.eam.service.IOperateService;
import org.github.foxnic.web.framework.dao.DBConfigs;
import java.util.Date;

/**
 * <p>
 * 资产操作 服务实现
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-09-26 20:02:16
*/


@Service("EamOperateService")
public class OperateServiceImpl extends SuperService<Operate> implements IOperateService {
	
	/**
	 * 注入DAO对象
	 * */
	@Resource(name=DBConfigs.PRIMARY_DAO) 
	private DAO dao=null;
	
	/**
	 * 获得 DAO 对象
	 * */
	public DAO dao() { return dao; }



	
	@Override
	public Object generateId(Field field) {
		return IDGenerator.getSnowflakeIdString();
	}


	/**
	 * 批量启动审批
	 * */
	@Override
	public Result startProcess(ProcessStartVO startVO) {
		Result result=new Result();
		for (String id : startVO.getBillIds()) {
			Result<ChangeEvent> r=startProcess(id);
			if(r.failure()) {
				result.addError(r);
			} else {
				// 处理逻辑
				ChangeEvent event=r.data();
				//syncOrder(id,event);
			}
		}
		return result;
	}

	/**
	 * 启动审批
	 * */
	public Result startProcess(String id) {

		return null;
	}


	@Override
	public Result approve(ProcessApproveVO approveVO) {
		return null;
	}



	/*
	当前的SN是否是唯一的。判断是否序列号要唯一,满足非空唯一即可
	 */
	@Override
	public boolean queryAssetSerialNumberIsUnique(String sn,String id){
		if(StringUtil.isBlank(sn)){
			return true;
		}
		String sql="select 1 from eam_asset where serial_number=? and tenant_id=? and owner_code=? and deleted='0'";
		if(!StringUtil.isBlank(id)){
			sql=sql+" and id<>'"+id+"'";
		}
		if(dao.queryRecord(sql,sn, SessionUser.getCurrent().getActivatedTenantId(),AssetOwnerCodeEnum.ASSET.code())==null){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 判断前端资产是否能更新
	 * @return 返回
	 * */
	@Override
	public boolean queryAssetSerialNumberNeedUnique(){
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode("eam.assetSerialNumberIsUnique");
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;
	}



	private boolean queryAssetControllerParameter(String par){
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode(par);
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;
	}


	@Override
	public boolean queryAssetImportAssetCodeKeep() {
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode("eam.assetImportAssetCodeKeep");
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;
	}

	/**
	 * 判断前端资产是否能更新
	 * @return 返回
	 * */
	@Override
	public boolean queryAssetDirectUpdateMode(){
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode("eam.assetDirectUpdateMode");
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;
	}

	@Override
	public boolean queryAssetStatusColumnDisable(){
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode("eam.assetStatusColumnDisable");
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;

	}

	@Override
	public boolean queryAssetDataPermissions(){
		return queryConfigValue("eam.assetDataPermissions");
	}

	private boolean queryConfigValue(String code){
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode(code);
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;
	}



	@Override
	public boolean queryAssetImportDataReplenish() {
		boolean r=false;
		ConfigVO vo=new ConfigVO();
		vo.setCode("eam.assetImportDataReplenish");
		Result<List<Config>> dataRs=ConfigServiceProxy.api().queryList(vo);
		if(dataRs.isSuccess()&&dataRs.getData().size()>0){
			Config conf=dataRs.getData().get(0);
			if(!StringUtil.isBlank(conf.getValue()) && "1".equals(conf.getValue())){
				r=true;
			}
		}
		return r;
	}

	/**
	 * 判断是否需要审批
	 * @param businessType 业务类型
	 * @return 返回是否需要审批
	 * */
	@Override
	public boolean approvalRequired(String businessType){
		Operate operate=queryEntity(Operate.create().setOperateCode(businessType));
		if(operate!=null&&operate.getApproval()!=null && operate.getApproval().equals(StatusYNEnum.NO.code())){
			return false;
		}
		return true;
	}

	/**
	 * 插入实体
	 * @param operate 实体数据
	 * @return 插入是否成功
	 * */
	@Override
	public Result insert(Operate operate) {
		Result r=super.insert(operate);

		return r;
	}
	
	/**
	 * 批量插入实体，事务内
	 * @param operateList 实体数据清单
	 * @return 插入是否成功
	 * */
	@Override
	public Result insertList(List<Operate> operateList) {
		return super.insertList(operateList);
	}
	
	
	/**
	 * 按主键删除 资产操作
	 *
	 * @param id 主键
	 * @return 删除是否成功
	 */
	public Result deleteByIdPhysical(String id) {
		Operate operate = new Operate();
		if(id==null) return ErrorDesc.failure().message("id 不允许为 null 。");
		operate.setId(id);
		try {
			boolean suc = dao.deleteEntity(operate);
			return suc?ErrorDesc.success():ErrorDesc.failure();
		}
		catch(Exception e) {
			Result r= ErrorDesc.failure();
			r.extra().setException(e);
			return r;
		}
	}
	
	/**
	 * 按主键删除 资产操作
	 *
	 * @param id 主键
	 * @return 删除是否成功
	 */
	public Result deleteByIdLogical(String id) {
		Operate operate = new Operate();
		if(id==null) return ErrorDesc.failure().message("id 不允许为 null 。");
		operate.setId(id);
		operate.setDeleted(dao.getDBTreaty().getTrueValue());
		operate.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		operate.setDeleteTime(new Date());
		try {
			boolean suc = dao.updateEntity(operate,SaveMode.NOT_NULL_FIELDS);
			return suc?ErrorDesc.success():ErrorDesc.failure();
		}
		catch(Exception e) {
			Result r= ErrorDesc.failure();
			r.extra().setException(e);
			return r;
		}
	}
	
	/**
	 * 更新实体
	 * @param operate 数据对象
	 * @param mode 保存模式
	 * @return 保存是否成功
	 * */
	@Override
	public Result update(Operate operate , SaveMode mode) {
		Result r=super.update(operate , mode);
		return r;
	}
	
	/**
	 * 更新实体集，事务内
	 * @param operateList 数据对象列表
	 * @param mode 保存模式
	 * @return 保存是否成功
	 * */
	@Override
	public Result updateList(List<Operate> operateList , SaveMode mode) {
		return super.updateList(operateList , mode);
	}
	
	
	/**
	 * 按主键更新字段 资产操作
	 *
	 * @param id 主键
	 * @return 是否更新成功
	 */
	public boolean update(DBField field,Object value , String id) {
		if(id==null) throw new IllegalArgumentException("id 不允许为 null ");
		if(!field.table().name().equals(this.table())) throw new IllegalArgumentException("更新的数据表["+field.table().name()+"]与服务对应的数据表["+this.table()+"]不一致");
		int suc=dao.update(field.table().name()).set(field.name(), value).where().and("id = ? ",id).top().execute();
		return suc>0;
	} 
	
	
	/**
	 * 按主键获取 资产操作
	 *
	 * @param id 主键
	 * @return Operate 数据对象
	 */
	public Operate getById(String id) {
		Operate sample = new Operate();
		if(id==null) throw new IllegalArgumentException("id 不允许为 null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}

	@Override
	public List<Operate> getByIds(List<String> ids) {
		return super.queryListByUKeys("id",ids);
	}



	/**
	 * 查询实体集合，默认情况下，字符串使用模糊匹配，非字符串使用精确匹配
	 * 
	 * @param sample  查询条件
	 * @return 查询结果
	 * */
	@Override
	public List<Operate> queryList(Operate sample) {
		return super.queryList(sample);
	}
	
	
	/**
	 * 分页查询实体集，字符串使用模糊匹配，非字符串使用精确匹配
	 * 
	 * @param sample  查询条件
	 * @param pageSize 分页条数
	 * @param pageIndex 页码
	 * @return 查询结果
	 * */
	@Override
	public PagedList<Operate> queryPagedList(Operate sample, int pageSize, int pageIndex) {
		return super.queryPagedList(sample, pageSize, pageIndex);
	}
	
	/**
	 * 分页查询实体集，字符串使用模糊匹配，非字符串使用精确匹配
	 * 
	 * @param sample  查询条件
	 * @param condition 其它条件
	 * @param pageSize 分页条数
	 * @param pageIndex 页码
	 * @return 查询结果
	 * */
	@Override
	public PagedList<Operate> queryPagedList(Operate sample, ConditionExpr condition, int pageSize, int pageIndex) {
		return super.queryPagedList(sample, condition, pageSize, pageIndex);
	}
	
	/**
	 * 检查 角色 是否已经存在
	 *
	 * @param operate 数据对象
	 * @return 判断结果
	 */
	public Result<Operate> checkExists(Operate operate) {
		//TDOD 此处添加判断段的代码
		//boolean exists=this.checkExists(operate, SYS_ROLE.NAME);
		//return exists;
		return ErrorDesc.success();
	}

	@Override
	public ExcelWriter exportExcel(Operate sample) {
		return super.exportExcel(sample);
	}

	@Override
	public ExcelWriter exportExcelTemplate() {
		return super.exportExcelTemplate();
	}

	@Override
	public List<ValidateResult> importExcel(InputStream input,int sheetIndex,boolean batch) {
		return super.importExcel(input,sheetIndex,batch);
	}

	@Override
	public ExcelStructure buildExcelStructure(boolean isForExport) {
		return super.buildExcelStructure(isForExport);
	}


}
