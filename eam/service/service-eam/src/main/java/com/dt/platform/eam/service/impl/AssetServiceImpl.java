package com.dt.platform.eam.service.impl;


import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.constants.enums.common.CodeModuleEnum;
import com.dt.platform.constants.enums.eam.*;
import com.dt.platform.constants.enums.ops.OpsOperateEnum;
import com.dt.platform.domain.eam.*;
import com.dt.platform.domain.eam.meta.AssetMeta;
import com.dt.platform.eam.common.ResetOnCloseInputStream;
import com.dt.platform.eam.service.*;
import com.dt.platform.proxy.common.CodeModuleServiceProxy;
import com.dt.platform.proxy.common.TplFileServiceProxy;
import com.github.foxnic.api.constant.CodeTextEnum;
import com.github.foxnic.commons.bean.BeanNameUtil;
import com.github.foxnic.commons.bean.BeanUtil;
import com.github.foxnic.commons.collection.CollectorUtil;
import com.github.foxnic.commons.lang.StringUtil;
import com.github.foxnic.commons.log.Logger;
import com.github.foxnic.commons.reflect.EnumUtil;
import com.github.foxnic.dao.data.*;
import com.github.foxnic.dao.excel.*;
import com.github.foxnic.dao.meta.DBTableMeta;
import com.github.foxnic.dao.sql.SQLBuilder;
import com.github.foxnic.sql.expr.*;
import com.github.foxnic.sql.treaty.DBTreaty;
import org.apache.poi.ss.usermodel.*;
import org.github.foxnic.web.domain.changes.ProcessApproveVO;
import org.github.foxnic.web.domain.changes.ProcessStartVO;
import org.github.foxnic.web.domain.changes.meta.ExampleOrderMeta;
import org.github.foxnic.web.domain.hrm.Employee;
import org.github.foxnic.web.domain.hrm.Person;
import org.github.foxnic.web.domain.hrm.meta.EmployeeMeta;
import org.github.foxnic.web.domain.pcm.CatalogAttribute;
import org.github.foxnic.web.domain.pcm.CatalogData;
import org.github.foxnic.web.domain.pcm.DataQueryVo;
import org.github.foxnic.web.proxy.pcm.CatalogServiceProxy;
import org.github.foxnic.web.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.*;

import com.github.foxnic.api.transter.Result;
import com.github.foxnic.dao.entity.SuperService;
import com.github.foxnic.dao.spec.DAO;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.foxnic.commons.busi.id.IDGenerator;
import com.github.foxnic.api.error.ErrorDesc;

import com.github.foxnic.sql.meta.DBField;

import org.github.foxnic.web.framework.dao.DBConfigs;

/**
 * <p>
 * 资产 服务实现
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-09-20 21:49:26
*/


@Service("EamAssetService")
public class AssetServiceImpl extends SuperService<Asset> implements IAssetService {


	@Autowired
	private IOperateService operateService;

	@Autowired
	private IAssetDataService assetDataService;


	@Autowired
	private IAssetCategoryService assetCategoryService;

	@Autowired
	private IAssetItemService assetItemService;


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

	private String lineToHump(String str) {
		Pattern linePattern = Pattern.compile("_(\\w)");
		str = str.toLowerCase();
		Matcher matcher = linePattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	@Override
	public HashMap<String,List<SQL>> parseAssetChangeRecordWithChangeAsset(List<Asset> assetBefore, HashMap<String, Object> changeMap,String businessCode,String operType,String notes){
		DBTableMeta tm=dao().getTableMeta(this.table());
		DBTreaty dbTreaty= dao().getDBTreaty();
		HashMap<String,List<SQL>> data=new HashMap<>();
		List<SQL> changeSqls=new ArrayList<>();
		List<SQL> updateSqls=new ArrayList<>();

		//获取after数据
		String changeId="";
		if(changeMap.containsKey("id")){
			changeId=changeMap.get("id").toString();
		}else{
			changeId=IDGenerator.getSnowflakeIdString();
			Insert ins=new Insert("eam_asset");
			ins.set("owner_code",AssetOwnerCodeEnum.ASSET_CHANGE_RECORD.code());
			ins.setIf("tenant_id",SessionUser.getCurrent().getActivatedCompanyId());
			for(String key:changeMap.keySet()){
				ins.set(key,changeMap.get(key));
			}
			ins.set("id",changeId);
			//System.out.println("AssetInsert "+ins.getSQL());
			dao.execute(ins);
		}
		Rcd assetAfterRcd=dao.queryRecord("select * from eam_asset where id=?",changeId);
		Asset assetAfter=getById(changeId);
		dao.fill(assetAfter).with(AssetMeta.CATEGORY)
				.with(AssetMeta.CATEGORY_FINANCE)
				.with(AssetMeta.GOODS)
				.with(AssetMeta.MANUFACTURER)
				.with(AssetMeta.POSITION)
				.with(AssetMeta.MAINTNAINER)
				.with(AssetMeta.SUPPLIER)
				.with(AssetMeta.OWNER_COMPANY)
				.with(AssetMeta.USE_ORGANIZATION)
				.with(AssetMeta.MANAGER)
				.with(AssetMeta.USE_USER)
				.with(AssetMeta.ORIGINATOR)
				.with(AssetMeta.RACK)
				.with(AssetMeta.SOURCE)
				.with(AssetMeta.SAFETY_LEVEL)
				.with(AssetMeta.EQUIPMENT_ENVIRONMENT)
				.with(AssetMeta.ASSET_MAINTENANCE_STATUS)
				.execute();
		dao().join(assetAfter.getManager(), Person.class);
		dao().join(assetAfter.getUseUser(), Person.class);
		JSONObject assetJsonAfter=BeanUtil.toJSONObject(assetAfter);


		//获取map
		String sql="select id,code,label,value_type,value_path from eam_asset_attribute where code not in ('status','business_code','batch_code') and deleted='0' and tenant_id=?";
		RcdSet rs=dao.query(sql,SessionUser.getCurrent().getActivatedTenantId());
		HashMap<String,Rcd> colsMap=(HashMap<String,Rcd>)rs.getMappedRcds("code",String.class);
		//删除不存在的
		HashMap<String, Object> rChangeMap=new HashMap<>();
		for(String key:changeMap.keySet()){
			if(colsMap.containsKey(key)){
				rChangeMap.put(key,changeMap.get(key));
			}
		}
		System.out.println("changeMap"+rChangeMap);
		//获取before数据
		dao.fill(assetBefore).with(AssetMeta.CATEGORY)
				.with(AssetMeta.CATEGORY_FINANCE)
				.with(AssetMeta.GOODS)
				.with(AssetMeta.MANUFACTURER)
				.with(AssetMeta.POSITION)
				.with(AssetMeta.MAINTNAINER)
				.with(AssetMeta.SUPPLIER)
				.with(AssetMeta.OWNER_COMPANY)
				.with(AssetMeta.USE_ORGANIZATION)
				.with(AssetMeta.MANAGER)
				.with(AssetMeta.USE_USER)
				.with(AssetMeta.ORIGINATOR)
				.with(AssetMeta.RACK)
				.with(AssetMeta.SOURCE)
				.with(AssetMeta.SAFETY_LEVEL)
				.with(AssetMeta.EQUIPMENT_ENVIRONMENT)
				.with(AssetMeta.ASSET_MAINTENANCE_STATUS)
				.execute();
		List<Employee> managers= CollectorUtil.collectList(assetBefore,Asset::getManager);
		dao().join(managers, Person.class);
		List<Employee> useUser= CollectorUtil.collectList(assetBefore,Asset::getUseUser);
		dao().join(useUser, Person.class);
	//	System.out.println("assetBefore"+assetBefore);
		//开始比较
	//	System.out.println("assetAfter"+assetJsonAfter);
		for(Asset asset: assetBefore){
			JSONObject assetJsonBefore=BeanUtil.toJSONObject(asset);
		//	System.out.println(assetJsonBefore.toJSONString());
			Update ups=new Update("eam_asset");
			String ct="";
			for(String key:rChangeMap.keySet()){
				Rcd rcd=colsMap.get(key);
				String label=rcd.getString("label");
				String valueType=rcd.getString("value_type");
				String valuePath=rcd.getString("value_path");
				String rkey=lineToHump(key);
				String before="-";
				String after="-";
				System.out.println(rkey+" rcd:"+rcd.toJSONObject().toJSONString());
				if(AssetAttributeValueTypeEnum.ENUM.code().equals(valueType)){
					if("asset_status".equals(key)){
						if(!StringUtil.isBlank(assetJsonBefore.getString(rkey))){
							CodeTextEnum v=EnumUtil.parseByCode(AssetStatusEnum.class,assetJsonBefore.getString(rkey));
							if(v!=null){
								before=v.text();
							}
						}
						if(!StringUtil.isBlank(assetJsonAfter.getString(rkey))){
							CodeTextEnum v=EnumUtil.parseByCode(AssetStatusEnum.class,assetJsonAfter.getString(rkey));
							if(v!=null){
								after=v.text();
							}
						}
					}else if("equipment_environment_code".equals(key)){
						if(!StringUtil.isBlank(assetJsonBefore.getString(rkey))){
							CodeTextEnum v=EnumUtil.parseByCode(AssetEquipmentStatusEnum.class,assetJsonBefore.getString(rkey));
							if(v!=null){
								before=v.text();
							}
						}
						if(!StringUtil.isBlank(assetJsonAfter.getString(rkey))){
							CodeTextEnum v=EnumUtil.parseByCode(AssetEquipmentStatusEnum.class,assetJsonAfter.getString(rkey));
							if(v!=null){
								after=v.text();
							}
						}
					}else{
						continue;
					}
				}else if(AssetAttributeValueTypeEnum.ENTITY.code().equals(valueType) || AssetAttributeValueTypeEnum.DICT.code().equals(valueType) ){
					if(!StringUtil.isBlank(valuePath)){
						String[] valuePathArr=valuePath.split(",");
						if(valuePathArr.length==2){
							String entity=valuePathArr[0];
							String name=valuePathArr[1];
							if(assetJsonBefore.getJSONObject(entity)!=null){
								before=assetJsonBefore.getJSONObject(entity).getString(name);
							}
							if(assetJsonAfter.getJSONObject(entity)!=null){
								after=assetJsonAfter.getJSONObject(entity).getString(name);
							}
						}
					}
				}else if(AssetAttributeValueTypeEnum.STRING.code().equals(valueType) ){
					before=assetJsonBefore.getString(rkey);
					after=assetJsonAfter.getString(rkey);
				}else{
					continue;
				}

			//	System.out.println("tsql"+key+","+assetAfterRcd.getOriginalValue(key));
				ups.setIf(key,assetAfterRcd.getOriginalValue(key));
				if( !((before+"").equals(after+"") ) ){
					ct=ct+"【"+label+"】由"+before+"变更为"+after+" ";
				}
			}

			//开始填充数据
			if(tm.getColumn(dbTreaty.getUpdateTimeField())!=null) {
				ups.set(dbTreaty.getUpdateTimeField(),new Date());
			}
			if(tm.getColumn(dbTreaty.getUpdateUserIdField())!=null) {
				ups.set(dbTreaty.getUpdateUserIdField(), dbTreaty.getLoginUserId());
			}
			ups.where().and("id=?",asset.getId());
			Insert ins=new Insert("eam_asset_process_record");
			ins.set("id",IDGenerator.getSnowflakeIdString());
			ins.setIf("asset_id",asset.getId());
			ins.setIf("business_code",businessCode);
			ins.setIf("process_type",operType);
			ins.setIf("notes",notes);
			ins.setIf("content",ct);
			ins.set("processd_time",new Date());
			if(tm.getColumn(dbTreaty.getCreateTimeField())!=null) {
				ins.set(dbTreaty.getCreateTimeField(),new Date());
			}
			if(tm.getColumn(dbTreaty.getCreateUserIdField())!=null) {
				ins.set(dbTreaty.getCreateUserIdField(), dbTreaty.getLoginUserId());
			}
			if(tm.getColumn(dbTreaty.getDeletedField())!=null) {
				ins.set(dbTreaty.getDeletedField(), dbTreaty.getFalseValue());
			}
			updateSqls.add(ups);
			changeSqls.add(ins);
		}
		System.out.println("update change size:"+updateSqls.size());
		for(SQL s:updateSqls){
			System.out.println(s.getSQL());
		}

		System.out.println("change change size:"+changeSqls.size());
		for(SQL s:changeSqls){
			System.out.println(s.getSQL());
		}
		data.put("change",changeSqls);
		data.put("update",updateSqls);
		return data;
	}


	@Override
	public Result startProcess(ProcessStartVO startVO) {
		return null;
	}

	@Override
	public Result approve(ProcessApproveVO approveVO) {
		return null;
	}


	/**
	 * 操作成功
	 * @param id ID
	 * @return 是否成功
	 * */
	public Result operateSuccess(String id) {

		return ErrorDesc.success();
	}

	/**
	 * 操作失败
	 * @param id ID
	 * @return 是否成功
	 * */
	public Result operateFailed(String id) {
		return ErrorDesc.success();
	}


	/**
	 * 操作
	 * @param id  ID
	 * @param result 结果
	 * @return
	 * */
	public Result operateResult(String id,String result) {

		if(AssetHandleConfirmOperationEnum.SUCCESS.code().equals(result)){
			return operateSuccess(id);
		}else if(AssetHandleConfirmOperationEnum.FAILED.code().equals(result)){
			return operateFailed(id);
		}else{
			return ErrorDesc.failureMessage("返回未知结果");
		}
	}


	@Override
	public Result joinData(PagedList<Asset> list) {
		// 关联出 资产分类 数据

		dao.fill(list).with(AssetMeta.CATEGORY)
				.with(AssetMeta.GOODS)
				.with(AssetMeta.MANUFACTURER)
				.with(AssetMeta.POSITION)
				.with(AssetMeta.MAINTNAINER)
				.with(AssetMeta.SUPPLIER)
				.with(AssetMeta.OWNER_COMPANY)
				.with(AssetMeta.USE_ORGANIZATION)
				.with(AssetMeta.MANAGER)
				.with(AssetMeta.USE_USER)
				.with(AssetMeta.ORIGINATOR)
				.with(AssetMeta.RACK)

				.with(AssetMeta.SOURCE)
				.with(AssetMeta.SAFETY_LEVEL)
				.with(AssetMeta.EQUIPMENT_ENVIRONMENT)
				.with(AssetMeta.ASSET_MAINTENANCE_STATUS)
				.execute();

		List<Employee> originators= CollectorUtil.collectList(list,Asset::getOriginator);
		dao().join(originators, Person.class);

		List<Employee> managers= CollectorUtil.collectList(list,Asset::getManager);
		dao().join(managers, Person.class);

		List<Employee> useUser= CollectorUtil.collectList(list,Asset::getUseUser);
		dao().join(useUser, Person.class);

		return  ErrorDesc.success();
	}

	/**
	 * 批量送审batchConfirmOperation
	 * @param ids 主键清单
	 * @return 是否成功
	 * */
	public Result forBatchApproval(List<String> ids){

		ConditionExpr expr=new ConditionExpr();
		expr.andIn("id",ids);
		expr.and("status=?",AssetHandleStatusEnum.INCOMPLETE.code());
		List<Asset> list=this.queryList(expr);
		if(ids.size()!=list.size()){
			return ErrorDesc.failureMessage("当前选择的资产中部分资产不满足状态要求");
		}
		return ErrorDesc.success();
	}


	/**
	 * 批量确认
	 * @param ids 主键清单
	 * @return 是否成功
	 * */
	public Result batchConfirmOperation(List<String> ids ){
		ConditionExpr expr=new ConditionExpr();
		expr.andIn("id",ids);
		expr.and("status=?",AssetHandleStatusEnum.INCOMPLETE.code());
		List<Asset> list=this.queryList(expr);
		if(ids.size()!=list.size()){
			return ErrorDesc.failureMessage("当前选择的资产中部分资产不满足状态要求");
		}

		if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_INSERT.code()) ) {
			return ErrorDesc.failureMessage("当前操作选用审核,请先送审");
		}

		return  ErrorDesc.success();
	}


	/**
	 * 确认操作
	 * @param id ID
	 * @return 是否成功
	 * */
	public Result confirmOperation(String id) {

		return ErrorDesc.failureMessage("开发中");
	}

	@Override
	public AssetExtData getExtDataById(String ownerId, String categoryId){
		HashMap<String,Object> map=new HashMap<>();
		AssetExtData extData=new AssetExtData();
		extData.setOwnerId(ownerId);
		extData.setTenantId(SessionUser.getCurrent().getActivatedTenantId());
		List<String> ids=new ArrayList<String>();
		ids.add(ownerId);
		DataQueryVo vo=new DataQueryVo();
		vo.setCatalogId(categoryId);
		vo.setTenantId(SessionUser.getCurrent().getActivatedTenantId());
		vo.setOwnerIds(ids);
		Result categoryResult=CatalogServiceProxy.api().queryData(vo);
		if(categoryResult.isSuccess()){
			JSONArray dataArr=(JSONArray)categoryResult.getData();
			if(dataArr!=null&&dataArr.size()>0){
				JSONObject dataObj=dataArr.getJSONObject(0);
				for(String key:dataObj.keySet()){
					if("id".equals(key)) extData.setId(dataObj.getString(key));
					map.put(key, dataObj.get(key));
				}
			}
			extData.setData(map);
		}else{
			return null;
		}

		return extData;

	}

	/**
	 * 插入实体
	 * @param asset 实体数据
	 * @return 插入是否成功
	 * */
	@Override
	public Result insert(Asset asset) {

		//判断是否序列号要唯一,满足非空唯一即可
		if(AssetOwnerCodeEnum.ASSET.code().equals(asset.getOwnerCode())&& !StringUtil.isBlank(asset.getSerialNumber()) && operateService.queryAssetSerialNumberNeedUnique() ){
			if(!operateService.queryAssetSerialNumberIsUnique(asset.getSerialNumber(),null)){
				return ErrorDesc.failure().message("当前资产序列号不唯一:"+asset.getSerialNumber());
			}
		}


		//制单人
		if(StringUtil.isBlank(asset.getOriginatorId())){
			asset.setOriginatorId(SessionUser.getCurrent().getUser().getActivatedEmployeeId());
		}

		//办理情况
		if(StringUtil.isBlank(asset.getStatus())){
			asset.setStatus(AssetHandleStatusEnum.INCOMPLETE.code());
		}


		//编码
		if(StringUtil.isBlank(asset.getAssetCode())&&!StringUtil.isBlank(asset.getOwnerCode())){
			if(AssetOwnerCodeEnum.ASSET.code().equals(asset.getOwnerCode())){
				Result codeResult= CodeModuleServiceProxy.api().generateCode(CodeModuleEnum.EAM_ASSET_CODE.code());
				if(!codeResult.isSuccess()){
					return codeResult;
				}else{
					asset.setAssetCode(codeResult.getData().toString());
				}
			}
		}

		//资产状态
		if(StringUtil.isBlank(asset.getAssetStatus())){
			if(AssetOwnerCodeEnum.ASSET.code().equals(asset.getOwnerCode())){
				asset.setAssetStatus(AssetStatusEnum.IDLE.code());
			}
		}


		Result r=super.insert(asset);
		return r;
	}



	/**
	 * 批量插入实体，事务内
	 * @param assetList 实体数据清单
	 * @return 插入是否成功
	 * */
	@Override
	public Result insertList(List<Asset> assetList) {
		return super.insertList(assetList);
	}
	
	
	/**
	 * 按主键删除 资产
	 *
	 * @param id 主键
	 * @return 删除是否成功
	 */
	public Result deleteByIdPhysical(String id) {
		Asset asset = new Asset();
		if(id==null) return ErrorDesc.failure().message("id 不允许为 null 。");
		asset.setId(id);
		try {
			boolean suc = dao.deleteEntity(asset);
			return suc?ErrorDesc.success():ErrorDesc.failure();
		}
		catch(Exception e) {
			Result r= ErrorDesc.failure();
			r.extra().setException(e);
			return r;
		}
	}
	
	/**
	 * 按主键删除 资产
	 *
	 * @param id 主键
	 * @return 删除是否成功
	 */
	public Result deleteByIdLogical(String id) {
		Asset asset = new Asset();
		if(id==null) return ErrorDesc.failure().message("id 不允许为 null 。");
		asset.setId(id);
		asset.setDeleted(dao.getDBTreaty().getTrueValue());
		asset.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		asset.setDeleteTime(new Date());
		try {
			boolean suc = dao.updateEntity(asset,SaveMode.NOT_NULL_FIELDS);
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
	 * @param asset 数据对象
	 * @param mode 保存模式
	 * @return 保存是否成功
	 * */
	@Override
	public Result update(Asset asset , SaveMode mode) {
		if(!StringUtil.isBlank(asset.getAssetCode())){
			asset.setAssetCode(null);
		}

		//判断是否序列号要唯一,满足非空唯一即可
		if(AssetOwnerCodeEnum.ASSET.code().equals(asset.getOwnerCode())&& !StringUtil.isBlank(asset.getSerialNumber()) && operateService.queryAssetSerialNumberNeedUnique() ){
			if(!operateService.queryAssetSerialNumberIsUnique(asset.getSerialNumber(),asset.getId())){
				return ErrorDesc.failure().message("当前资产序列号不唯一:"+asset.getSerialNumber());
			}
		}


		Result r=super.update(asset , mode);
		return r;
	}
	
	/**
	 * 更新实体集，事务内
	 * @param assetList 数据对象列表
	 * @param mode 保存模式
	 * @return 保存是否成功
	 * */
	@Override
	public Result updateList(List<Asset> assetList , SaveMode mode) {
		return super.updateList(assetList , mode);
	}
	
	
	/**
	 * 按主键更新字段 资产
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
	 * 按主键获取 资产
	 *
	 * @param id 主键
	 * @return Asset 数据对象
	 */
	public Asset getById(String id) {
		Asset sample = new Asset();
		if(id==null) throw new IllegalArgumentException("id 不允许为 null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}

	@Override
	public List<Asset> getByIds(List<String> ids) {
		return new ArrayList<>(getByIdsMap(ids).values());
	}



	/**
	 * 查询实体集合，默认情况下，字符串使用模糊匹配，非字符串使用精确匹配
	 * 
	 * @param sample  查询条件
	 * @return 查询结果
	 * */
	@Override
	public List<Asset> queryList(Asset sample) {
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
	public PagedList<Asset> queryPagedList(Asset sample, int pageSize, int pageIndex) {

		Expr select=new Expr("select * from "+table()+" t ");
		ConditionExpr expr=new ConditionExpr();
		if(sample instanceof AssetVO) {
			AssetVO vo=(AssetVO) sample;
			if(!StringUtil.isBlank(vo.getUseOrganizationId())) {
				expr.and("use_organization_id in (select id from hrm_organization where deleted=0 and type in ('com','dept') and (concat('/',hierarchy) like '%/"+vo.getUseOrganizationId()+"/%' or id=?))",vo.getUseOrganizationId());
				vo.setUseOrganizationId(null);
			}
			if(!StringUtil.isBlank(vo.getCategoryId())) {
				expr.and("category_id in (select id from pcm_catalog where deleted=0 and (concat('/',hierarchy) like '%/"+vo.getCategoryId()+"/%' or id=?))",vo.getCategoryId());
				vo.setCategoryId(null);
			}
			if(!StringUtil.isBlank(vo.getCategoryFinance())) {
				expr.and("category_id in (select id from eam_category_finance where deleted=0 and (concat('/',hierarchy) like '%/"+vo.getCategoryFinance()+"/%' or id=?))",vo.getCategoryFinance());
				vo.setFinancialCategoryId(null);
			}
			if(!StringUtil.isBlank(vo.getOwnCompanyId())) {
				expr.and("own_company_id in (select id from hrm_organization where deleted=0 and type in ('com','dept') and (concat('/',hierarchy) like '%/"+vo.getOwnCompanyId()+"/%' or id=?))",vo.getOwnCompanyId());
				vo.setOwnCompanyId(null);
			}
		}

		if(StringUtil.isBlank(sample.getOwnerCode())){
			expr.and("owner_code=?",AssetOwnerCodeEnum.ASSET.code());
		}

		ConditionExpr base=this.buildQueryCondition(sample,"t");
		expr.and(base);
		select.append(expr.startWithWhere()+" order by create_time desc");
		PagedList<Asset> pagedList=this.dao().queryPagedEntities(Asset.class,pageSize,pageIndex,select);
		return pagedList;


	}

	public Result conditionAssetBusinessType(String businessType,ConditionExpr queryCondition){

		//过滤资产状态
		if(AssetOperateEnum.EAM_ASSET_BORROW.code().equals(businessType)){
			//借用
			queryCondition.andIn("asset_status",AssetStatusEnum.USING.code(),AssetStatusEnum.IDLE.code());
		}else if(AssetOperateEnum.EAM_ASSET_COLLECTION.code().equals(businessType)){
			//领用
			queryCondition.andIn("asset_status",AssetStatusEnum.IDLE.code());
		}else if(AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code().equals(businessType)){
			//退库
			queryCondition.andIn("asset_status",AssetStatusEnum.USING.code());
		}else if(AssetOperateEnum.EAM_ASSET_REPAIR.code().equals(businessType)){
			//报修
			queryCondition.andIn("asset_status",AssetStatusEnum.USING.code(),AssetStatusEnum.IDLE.code());
		}else if(AssetOperateEnum.EAM_ASSET_SCRAP.code().equals(businessType)){
			//报废
			queryCondition.andIn("asset_status",AssetStatusEnum.USING.code(),AssetStatusEnum.IDLE.code());
		}else if(AssetOperateEnum.EAM_ASSET_ALLOCATE.code().equals(businessType)){
			//调拨
			queryCondition.andIn("asset_status",AssetStatusEnum.USING.code(),AssetStatusEnum.IDLE.code());
		}else if(AssetOperateEnum.EAM_ASSET_TRANFER.code().equals(businessType)){
			//转移
			queryCondition.andIn("asset_status",AssetStatusEnum.USING.code(),AssetStatusEnum.IDLE.code());
		}else if(AssetOperateEnum.EAM_ASSET_CHANGE_BASE_INFO.code().equals(businessType)
			||AssetOperateEnum.EAM_ASSET_CHANGE_FINANCIAL.code().equals(businessType)
			||AssetOperateEnum.EAM_ASSET_CHANGE_MAINTENANCE.code().equals(businessType)){
			//转移
		}else{
			queryCondition.andIn("asset_status","unknow");
			return ErrorDesc.failure().message("不支持当前业务类型操作");
		}
		return ErrorDesc.success().data(queryCondition);
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
	public PagedList<Asset> queryPagedList(Asset sample, ConditionExpr condition, int pageSize, int pageIndex) {

		return super.queryPagedList(sample, condition, pageSize, pageIndex);
	}


	@Override
	public PagedList<Asset> queryPagedListBySelect(AssetVO sample,String businessType,String assetOwnerId,String assetSelectedCode,String assetSearchContent) {

		ConditionExpr queryCondition=new ConditionExpr();

		//过滤资产已选数据
		if(!StringUtil.isBlank(assetOwnerId)) {
			queryCondition.andIf("id not in (select asset_id from eam_asset_item where deleted=0 and handle_id=?)" ,assetOwnerId);
		}else{
			if(!StringUtil.isBlank(assetSelectedCode)) {
				queryCondition.andIf("id not in (select asset_id from eam_asset_selected_data where deleted=0 and asset_selected_code=?)" ,assetSelectedCode);
			}
		}

		if(!StringUtil.isBlank(sample.getCategoryId())) {
			queryCondition.and("category_id in (select id from pcm_catalog where deleted=0 and (concat('/',hierarchy) like '%/"+sample.getCategoryId()+"/%' or id=?))",sample.getCategoryId());
			sample.setCategoryId(null);
		}

		if(!StringUtil.isBlank(sample.getUseOrganizationId())) {
			queryCondition.and("use_organization_id in (select id from hrm_organization where deleted=0 and type in ('com','dept') and (concat('/',hierarchy) like '%/"+sample.getUseOrganizationId()+"/%' or id=?))",sample.getUseOrganizationId());
			sample.setUseOrganizationId(null);
		}


		if(!StringUtil.isBlank(sample.getOwnCompanyId())) {
			queryCondition.and("own_company_id in (select id from hrm_organization where deleted=0 and type in ('com','dept') and (concat('/',hierarchy) like '%/"+sample.getOwnCompanyId()+"/%' or id=?))",sample.getOwnCompanyId());
			sample.setOwnCompanyId(null);
		}


		if(StringUtil.isBlank(sample.getOwnerCode())){
			queryCondition.and("owner_code=?",AssetOwnerCodeEnum.ASSET.code());
		}

		Result r=conditionAssetBusinessType(businessType,queryCondition);
		if(r.isSuccess()){
			r.getData();
		}

		PagedList<Asset> list= queryPagedList(sample,queryCondition,sample.getPageSize(),sample.getPageIndex());
		return list;
	}


	/**
	 * 插入实体
	 * @param businessType 业务类型
	 * @param assetIds 资产
	 * @return 插入是否成功
	 * */
	@Override
	public Result checkAssetDataForBusiessAction(String businessType,List<String> assetIds) {
		Result result=new Result();
		ConditionExpr queryCondition=new ConditionExpr();
		queryCondition.andIn("id",assetIds);
		Result r=conditionAssetBusinessType(businessType,queryCondition);
		if(!r.isSuccess()){
			return r;
		}
		List<Asset> list=queryList(queryCondition);
//		System.out.println("parameter asset size:"+assetIds.size());
//		System.out.println("asset size:"+list.size());
		if(list.size()!=assetIds.size()){
			return ErrorDesc.failure().message("当前选择的资产中部分状态异常");
		}
		return result;
	}



	@Override
	public PagedList<Asset> queryPagedListBySelected(AssetVO sample,String assetSelectedCode,String assetOwnerId,String dataType){

		ConditionExpr queryCondition=new ConditionExpr();
		if(assetOwnerId!=null&&assetOwnerId.length()>0){
		    //实现临时快照
			if("refresh".equals(dataType)){
				dao.execute("delete from eam_asset_item where crd in ('cd','c') and handle_id=?",assetOwnerId);
				dao.execute("update eam_asset_item set crd='r' where crd='d' and handle_id=?",assetOwnerId);
			}
			queryCondition.andIf("id in (select asset_id from eam_asset_item where crd in ('c','r') and deleted=0 and handle_id=?)" ,assetOwnerId);
		}else{
			if(assetSelectedCode!=null&&assetSelectedCode.length()>0){
				queryCondition.andIf("id  in (select asset_id from eam_asset_selected_data where deleted=0 and asset_selected_code=?)" ,assetSelectedCode);
			}
		}
		PagedList<Asset> list= queryPagedList(sample,queryCondition,sample.getPageSize(),sample.getPageIndex());
		return list;
	}

	/**
	 * 检查 角色 是否已经存在
	 *
	 * @param asset 数据对象
	 * @return 判断结果
	 */
	public Result<Asset> checkExists(Asset asset) {
		return ErrorDesc.success();
	}

	@Override
	public ExcelWriter exportExcel(Asset sample) {
		return super.exportExcel(sample);
	}

	@Override
	public ExcelWriter exportExcelTemplate() {
		return null;
	}



	public List<ValidateResult> importData(RcdSet rs,HashMap<String,List<CatalogAttribute>> attributeMap,boolean batch,String assetOwner,String bussinessType,boolean dataType) {
		List<ValidateResult> errors=new ArrayList<>();
		DBTableMeta tm=dao().getTableMeta(this.table());
		DBTreaty dbTreaty= dao().getDBTreaty();

		List<CatalogAttribute> catalogAttribteList=new ArrayList<>();
		List<CatalogData> catalogDataList=new ArrayList<>();
		List<String> assetSerialNumberList=new ArrayList<>();

		HashMap<String,HashMap<String,String>> matchMap=new HashMap<>();
		matchMap.put("organizationMap",assetDataService.queryUseOrganizationNodes());
		matchMap.put("categoryMap",assetDataService.queryAssetCategoryNodes());
		matchMap.put("eam_safety_level", assetDataService.queryDictItemDataByDictCode("eam_safety_level"));
		matchMap.put("eam_equipment_environment",assetDataService.queryDictItemDataByDictCode("eam_equipment_environment"));
		matchMap.put("eam_source",assetDataService.queryDictItemDataByDictCode("eam_source"));
		matchMap.put("eam_maintenance_status",assetDataService.queryDictItemDataByDictCode("eam_maintenance_status"));


		String pcmCategoryId=null;
		if(attributeMap.size()>0){
			Logger.info("本次资产导入为自定义模式");
			for(String key:attributeMap.keySet()){
				pcmCategoryId=key;
			}
			catalogAttribteList=attributeMap.get(pcmCategoryId);
		}else{
			Logger.info("本次资产导入为通用模式");
		}

		String actionType="update";
		String assetId="";
		List<SQL> sqls=new ArrayList<>();
		int i=0;
		for (Rcd r:rs.getRcdList()) {
			i++;
			System.out.println("before:"+r);
			//判断数据是插入或更新
			if(StringUtil.isBlank(r.getString(AssetDataExportColumnEnum.ASSET_ID.text()))){
				actionType="insert";
				assetId=IDGenerator.getSnowflakeIdString();
				r.set("id",assetId);
			}else{
				actionType="update";
				assetId=r.getString(AssetDataExportColumnEnum.ASSET_ID.text());
			}


			//校验数据
			Result verifyResult=assetDataService.verifyAssetRecord(r,matchMap,dataType);
			if(!verifyResult.isSuccess()){
				errors.add(new ValidateResult(null,(i+1),verifyResult.getMessage()));
				break;
			}

			//判断资产分类是否存在
			String categoryId=r.getString(AssetDataExportColumnEnum.ASSET_CATEGORY_NAME.text());
			if(StringUtil.isBlank(categoryId)){
				errors.add(new ValidateResult(null,(i+1),"当前资产分类不存在"+r.toString()));
				break;
			}

			//判断是否序列号要唯一,满足非空唯一即可
			if(AssetOwnerCodeEnum.ASSET.code().equals(assetOwner)&& !StringUtil.isBlank(r.getString("serial_number")) && operateService.queryAssetSerialNumberNeedUnique() ){
				String sn=r.getString("serial_number");
				if(!operateService.queryAssetSerialNumberIsUnique(sn,r.getString("id"))){
					errors.add(new ValidateResult(null,(i+1),"当前资产序列号不唯一:"+sn));
					break;
				}
				if(assetSerialNumberList.contains(sn)){
					errors.add(new ValidateResult(null,(i+1),"当前资产序列号不唯一:"+sn));
					break;
				}else{
					assetSerialNumberList.add(sn);
				}
			}

			//处理自定义属性
			if(pcmCategoryId!=null){
				if(categoryId.equals(pcmCategoryId)){
					Result<CatalogData> resultExtColumn=assetDataService.verifyAssetExtColumnRecord(r,catalogAttribteList,false);
					if(resultExtColumn.isSuccess()){
						catalogDataList.add(resultExtColumn.getData());
					}else{
						errors.add(new ValidateResult(null,(i+1),resultExtColumn.getMessage()));
						break;
					}
				}else{
					errors.add(new ValidateResult(null,(i+1),"当前资产分类不匹配,categoryId:"+categoryId+",pcmCategoryId:"+pcmCategoryId));
					break;
				}
			}

			//汇总更新数据
			String sql="";
			if("insert".equals(actionType)){
				Insert insert = SQLBuilder.buildInsert(r,this.table(),this.dao(), true);
				//办理情况
				if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_INSERT.code()) ){
					insert.set("status",AssetHandleStatusEnum.INCOMPLETE.code());
					//提交审批
				}else{
					insert.set("status",AssetHandleStatusEnum.COMPLETE.code());
				}

				//资产编号
				Result codeResult= CodeModuleServiceProxy.api().generateCode(bussinessType);
				if(!codeResult.isSuccess()){
					//返回报错
					errors.add(new ValidateResult(null,(i+1),codeResult.getMessage()));
					break;
				}else{
					insert.set("asset_code",codeResult.getData().toString());
				}

				insert.set(AssetDataExportColumnEnum.ASSET_ID.text(),assetId);
				insert.set("tenant_id",SessionUser.getCurrent().getActivatedTenantId());
				insert.set("owner_code",assetOwner);
				//制单人
				insert.set("originator_id",SessionUser.getCurrent().getUser().getActivatedEmployeeId());


				//设置创建时间
				if(tm.getColumn(dbTreaty.getCreateTimeField())!=null) {
					insert.set(dbTreaty.getCreateTimeField(),new Date());
				}
				if(tm.getColumn(dbTreaty.getCreateUserIdField())!=null) {
					insert.set(dbTreaty.getCreateUserIdField(), dbTreaty.getLoginUserId());
				}
				if(tm.getColumn(dbTreaty.getDeletedField())!=null) {
					insert.set(dbTreaty.getDeletedField(), dbTreaty.getFalseValue());
				}
				if(batch) {
					sqls.add(insert);
				} else {
					this.dao().execute(insert);
				}
				sql=insert.getSQL();
			}else{
				//办理情况
				if(r.getOwnerSet().hasColumn(AssetDataExportColumnEnum.STATUS_NAME.text())){
//					//r.getOwnerSet().removeColumn(AssetDataExportColumnEnum.STATUS_NAME.text());
				}

				if(r.getOwnerSet().hasColumn(AssetDataExportColumnEnum.ASSET_CODE.text())){
					//r.getOwnerSet().removeColumn(AssetDataExportColumnEnum.ASSET_CODE.text());
				}
				Update update=SQLBuilder.buildUpdate(r,SaveMode.ALL_FIELDS,this.table(),this.dao());
				//设置创建时间
				if(tm.getColumn(dbTreaty.getUpdateTimeField())!=null) {
					update.set(dbTreaty.getUpdateTimeField(),new Date());
				}
				if(tm.getColumn(dbTreaty.getUpdateUserIdField())!=null) {
					update.set(dbTreaty.getUpdateUserIdField(), dbTreaty.getLoginUserId());
				}
				if(batch) {
					sqls.add(update);
				} else {
					this.dao().execute(update);
				}
				sql=update.getSQL();

			}
			System.out.println("after:"+r);
			System.out.println(sql);
		}

		if(batch) {
			try {
				if(errors.size()==0){
					dao().batchExecute(sqls);
					if(catalogDataList.size()>0){
						//保存自定义数据
						System.out.println("catalogDataList:"+catalogDataList);
						CatalogServiceProxy.api().saveDataList(catalogDataList);
						for(CatalogData data:catalogDataList){
							System.out.println("getId"+data.getId());
							System.out.println("getOwnerId"+data.getOwnerId());
							System.out.println("getTenantId"+data.getTenantId());
							System.out.println("getCatalogId"+data.getCatalogId());
							System.out.println("getData"+data.getData());
						}
					}
				}

			} catch (Exception e) {
				errors.add(new ValidateResult(null,3,"批量导入失败"));
				throw  e;
			}
		}
		return errors;

	}

	@Override
	public List<ValidateResult> importExcel(InputStream input,int sheetIndex,boolean batch,String assetOwner,String bussinessType,boolean dataType) {

		List<ValidateResult> errors=new ArrayList<>();
		ExcelReader er=null;
		try {
			er=new ExcelReader(input);
		} catch (Exception e) {
			errors.add(new ValidateResult(null,-1,"缺少文件"));
			return errors;
		}
		//构建 Excel 结构
		ExcelStructure es=buildExcelStructure(input);
		HashMap<String,List<CatalogAttribute>> attributeMap=this.getPcmExtAttribute(input);
		//装换成记录集
		RcdSet rs=null;
		try {
			rs=er.read(sheetIndex,es);

		} catch (Exception e) {
			Logger.error("Excel 导入错误",e);
			errors.add(new ValidateResult(null,-1,"Excel 读取失败"));
			return errors;
		}
		return importData(rs, attributeMap,batch, assetOwner,bussinessType, dataType);

	}



	public InputStream buildExcelTemplate(String categoryId){

		//categoryId="12";

		String code=AssetOperateEnum.EAM_DOWNLOAD_ASSET.code();
		InputStream inputStream= TplFileServiceProxy.api().getTplFileStreamByCode(code);
		Workbook workbook;

		if(inputStream!=null){
			try {
				BufferedInputStream bufferInput = new ResetOnCloseInputStream(inputStream);
				workbook = WorkbookFactory.create(bufferInput);
				CellStyle cs=workbook.createCellStyle();
				cs.setAlignment(HorizontalAlignment.CENTER);
				cs.setVerticalAlignment(VerticalAlignment.CENTER);
				Sheet sheet=workbook.getSheetAt(0);
//				if("data".equals(sheet.getSheetName())){
//				}
				Row firstRow=sheet.getRow(0);
				Row secondRow=sheet.getRow(1);
				System.out.println("SheetName:"+sheet.getSheetName());
				System.out.println("firstRow lastCellNum:"+firstRow.getLastCellNum());
				System.out.println("lastSecondRow lastCellNum:"+secondRow.getLastCellNum());
				System.out.println("lastSecondRow lastCellNum Value:"+secondRow.getCell(secondRow.getLastCellNum()-1));
				if(firstRow.getLastCellNum()!=secondRow.getLastCellNum()){
					return null;
				}
				Short lastNum=firstRow.getLastCellNum();
				if(!StringUtil.isBlank(categoryId)){
					List<CatalogAttribute> catalogAttributeList = assetCategoryService.queryCatalogAttributeByAssetCategory(categoryId);
					if(catalogAttributeList.size()>0){
						//修改SheetName
						Sheet categorySheet=workbook.createSheet("attribute");
						Row categoryFirstRow=categorySheet.createRow(0);

						Cell fcCategoryBase=categoryFirstRow.createCell(0, CellType.STRING);
						fcCategoryBase.setCellValue(categoryId);
						fcCategoryBase.setCellStyle(cs);
						int i=0;
						for(i=0;i<catalogAttributeList.size();i++){

							CatalogAttribute attribute=catalogAttributeList.get(i);
							//sheet2上在category上添加标记
							Cell fcCategory=categoryFirstRow.createCell(i+1, CellType.STRING);
							fcCategory.setCellValue("pcm_ext_"+attribute.getField()+"");
							fcCategory.setCellStyle(cs);

							//sheet1 上
							Cell fc=firstRow.createCell(lastNum+i, CellType.STRING);
							fc.setCellValue(attribute.getShortName());
							fc.setCellStyle(cs);

							Cell sc=secondRow.createCell(lastNum+i, CellType.STRING);
							sc.setCellStyle(cs);
							sc.setCellValue("t.pcm_ext_"+attribute.getField());

						}
						Cell fcCategory=categoryFirstRow.createCell(i+1, CellType.STRING);
						fcCategory.setCellValue("pcm_ext_id");
						fcCategory.setCellStyle(cs);
						Cell fc=firstRow.createCell(lastNum+i, CellType.STRING);
						fc.setCellValue("自定义属性主键");
						fc.setCellStyle(cs);
						Cell sc=secondRow.createCell(lastNum+i, CellType.STRING);
						sc.setCellStyle(cs);
						sc.setCellValue("t.pcm_ext_id}}");


						//修改原标记
						Cell sourceEndColumnCell=secondRow.getCell(lastNum-1);
						sourceEndColumnCell.setCellValue(sourceEndColumnCell.getStringCellValue().replaceFirst("}}",""));

						ByteArrayOutputStream out = new ByteArrayOutputStream();
						workbook.write(out);
						return new ByteArrayInputStream(out.toByteArray());

					}
				}

			} catch (Exception e) {
				Logger.debug("Excel 读取错误", e);
			}
		}
		return inputStream;

	}

	public HashMap<String,List<CatalogAttribute>> getPcmExtAttribute(InputStream dataInputStream){
		HashMap<String,List<CatalogAttribute>> map=new HashMap<>();
		//获取自定义属性
		if(dataInputStream!=null){
			Workbook dataWorkbook;
			Sheet sheetCategory = null;
			try {
				BufferedInputStream bufferInput = new ResetOnCloseInputStream(dataInputStream);
				dataWorkbook = WorkbookFactory.create(bufferInput);
				int sheetIndex=dataWorkbook.getSheetIndex("attribute");
				if(sheetIndex<=0) {
					Logger.info("当前分类数据中未找到自定义属性数据");
				}else{
					sheetCategory=dataWorkbook.getSheetAt(sheetIndex);
					Row firstRow=sheetCategory.getRow(0);
					String categoryId=firstRow.getCell(0).getStringCellValue();
					List<CatalogAttribute> catalogAttributeList = assetCategoryService.queryCatalogAttributeByAssetCategory(categoryId);
					Logger.info("当前分类数据中自定义属性字段数量为:"+catalogAttributeList.size());
					int i=0;
					List<CatalogAttribute> list=new ArrayList<>();
					//为了保持顺序
					for(i=1;i<firstRow.getLastCellNum();i++){
						String asset_column=firstRow.getCell(i).toString();
						CatalogAttribute attri=new CatalogAttribute();
						attri.setField(asset_column);
						attri.setShortName(asset_column);
						list.add(attri);
					}
					map.put(categoryId,list);
				}
			}catch (Exception e) {
				Logger.debug("Excel 读取错误", e);
			}
		}
		return map;
	}


	@Override
	public ExcelStructure buildExcelStructure(InputStream dataInputStream) {

		String code=AssetOperateEnum.EAM_DOWNLOAD_ASSET.code();
		InputStream inputStream= TplFileServiceProxy.api().getTplFileStreamByCode(code);

		ExcelStructure es=new ExcelStructure();
		es.setDataColumnBegin(0);
		es.setDataRowBegin(2);

		Short lastNum=0;
		//从模板获取属性
		Workbook workbook;
		if ( inputStream != null) {
			try {
				workbook = WorkbookFactory.create(inputStream);
				Sheet sheet=workbook.getSheetAt(0);
				Row firstRow=sheet.getRow(0);
				Row secondRow=sheet.getRow(1);
				lastNum=firstRow.getLastCellNum();
				String charIndex="";
				for(int i=0;i<secondRow.getLastCellNum();i++){
					String asset_column=secondRow.getCell(i).toString().replaceFirst("\\{\\{\\$fe:","")
							.replaceFirst("dataList","")
							.replaceFirst("}}","")
							.replaceFirst("t.","").trim();

					String rAssetColumn="";
					//filter
					if(AssetDataExportColumnEnum.USE_USER_NAME.code().equals(asset_column)
						||AssetDataExportColumnEnum.MANAGER_NAME.code().equals(asset_column)
							||AssetDataExportColumnEnum.STATUS_NAME.code().equals(asset_column)){
						continue;
					}
					rAssetColumn=EnumUtil.parseByCode(AssetDataExportColumnEnum.class,asset_column)==null?
							BeanNameUtil.instance().depart(asset_column):
							EnumUtil.parseByCode(AssetDataExportColumnEnum.class,asset_column).text();

					charIndex=ExcelStructure.toExcel26(i);
					System.out.println(charIndex+","+secondRow.getCell(i)  +","+ firstRow.getCell(i)+","+asset_column+","+rAssetColumn);
					es.addColumn(charIndex,rAssetColumn,firstRow.getCell(i).toString(), ExcelColumn.STRING_CELL_READER);
				}
				//追加自定义属性部分
			} catch (Exception e) {
				Logger.debug("Excel 读取错误", e);
				return es;
			}
		}

		HashMap<String,List<CatalogAttribute>> map=getPcmExtAttribute(dataInputStream);
		System.out.println("pcm_ext_map:"+map);
		List<CatalogAttribute> list=new ArrayList<>();
		for(String key:map.keySet()){
			list=map.get(key);
			break;
		}
		String charIndex="";
		for(int i=0;i<list.size();i++){
			CatalogAttribute attribute=list.get(i);
			charIndex=ExcelStructure.toExcel26(lastNum+i);
			System.out.println(charIndex+",自定义属性:"+attribute.getShortName()+","+ attribute.getField());
			es.addColumn(charIndex,attribute.getField(),attribute.getShortName(), ExcelColumn.STRING_CELL_READER);

		}

		return es;
	}

}