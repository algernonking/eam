package com.dt.platform.eam.service.impl;


import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.constants.enums.common.CodeModuleEnum;
import com.dt.platform.constants.enums.eam.AssetHandleConfirmOperationEnum;
import com.dt.platform.constants.enums.eam.AssetHandleStatusEnum;
import com.dt.platform.constants.enums.eam.AssetOperateEnum;
import com.dt.platform.constants.enums.eam.AssetStatusEnum;
import com.dt.platform.domain.eam.AssetCollectionReturn;
import com.dt.platform.domain.eam.AssetItem;
import com.dt.platform.domain.eam.meta.AssetCollectionMeta;
import com.dt.platform.domain.eam.meta.AssetCollectionReturnMeta;
import com.dt.platform.eam.common.AssetCommonError;
import com.dt.platform.eam.service.IAssetSelectedDataService;
import com.dt.platform.eam.service.IAssetService;
import com.dt.platform.eam.service.IOperateService;
import com.dt.platform.proxy.common.CodeModuleServiceProxy;
import com.dt.platform.proxy.eam.AssetCollectionReturnServiceProxy;
import com.dt.platform.proxy.eam.AssetCollectionServiceProxy;
import com.github.foxnic.api.constant.CodeTextEnum;
import com.github.foxnic.commons.bean.BeanUtil;
import com.github.foxnic.commons.lang.StringUtil;
import com.github.foxnic.commons.reflect.EnumUtil;
import com.github.foxnic.sql.expr.SQL;
import org.github.foxnic.web.domain.changes.ChangeEvent;
import org.github.foxnic.web.domain.changes.ProcessApproveVO;
import org.github.foxnic.web.domain.changes.ProcessStartVO;
import org.github.foxnic.web.session.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dt.platform.domain.eam.AssetCollection;
import com.dt.platform.domain.eam.AssetCollectionVO;

import java.util.*;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.dt.platform.eam.service.IAssetCollectionService;
import org.github.foxnic.web.framework.dao.DBConfigs;

/**
 * <p>
 * ???????????? ????????????
 * </p>
 * @author ?????? , maillank@qq.com
 * @since 2021-08-20 20:54:09
*/


@Service("EamAssetCollectionService")
public class AssetCollectionServiceImpl extends SuperService<AssetCollection> implements IAssetCollectionService {
	
	/**
	 * ??????DAO??????
	 * */
	@Resource(name=DBConfigs.PRIMARY_DAO) 
	private DAO dao=null;
	
	/**
	 * ?????? DAO ??????
	 * */
	public DAO dao() { return dao; }

	@Autowired
	private IAssetService assetService;

	@Autowired
	private AssetItemServiceImpl assetItemService;

	@Autowired
	private IAssetSelectedDataService assetSelectedDataService;

	@Autowired
	private IOperateService operateService;


	@Override
	public Object generateId(Field field) {
		return IDGenerator.getSnowflakeIdString();
	}



	@Override
	public Result startProcess(ProcessStartVO startVO) {
		return null;
	}

	@Override
	public Result approve(ProcessApproveVO approveVO) {
		return null;
	}



	@Override
	public Result approve(String instanceId, List<AssetCollection> assets, String approveAction, String opinion) {
		return null;
	}


	@Override
	public Map<String, Object> getBill(String id) {

		AssetCollection data= AssetCollectionServiceProxy.api().getById(id).getData();
		join(data, AssetCollectionMeta.ASSET_LIST);

		Map<String, Object> map= BeanUtil.toMap(data);
		if(data.getStatus()!=null){
			CodeTextEnum en= EnumUtil.parseByCode(AssetHandleStatusEnum.class,data.getStatus());
			map.put("statusName", en==null?data.getStatus():en.text());
		}
		return map;
	}
	private void syncBill(String id, ChangeEvent event) {
		AssetCollection asset4Update=AssetCollection.create();
//		asset4Update.setId(id)
//				//????????????ID
//				.setChangeInstanceId(event.getInstance().getId())
//				//????????????
//				.setChsStatus(event.getInstance().getStatusEnum().code())
//				//?????????????????????
//				.setLatestApproverId(event.getApproverId())
//				.setLatestApproverName(event.getApproverName())
//				//???????????????????????????
//				.setNextApproverIds(event.getSimpleNextApproverIds())
//				.setNextApproverNames(event.getSimpleNextApproverNames())
//				//??????????????????
//				.setSummary(event.getDefinition().getName()+","+event.getApproveActionEnum().text());
		//????????????
		this.update(asset4Update,SaveMode.BESET_FIELDS);
	}

	/**
	 * ????????????
	 * */
	public Result startProcess(String id) {
		return null;
	}


	/**
	 * ??????
	 * @param id ID
	 * @return ????????????
	 * */
	@Override
	public Result revokeOperation(String id) {
		AssetCollection billData=getById(id);
		if(AssetHandleStatusEnum.APPROVAL.code().equals(billData.getStatus())){
		}else{
			return ErrorDesc.failureMessage("?????????????????????????????????????????????");
		}
		return ErrorDesc.success();
	}



	/**
	 * ??????
	 * @param id ID
	 * @return ????????????
	 * */
	@Override
	public Result forApproval(String id){

		AssetCollection billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_COLLECTION.code()) ) {
				//????????????

			}else{
				return ErrorDesc.failureMessage("???????????????????????????,???????????????????????????");
			}
		}else{
			return ErrorDesc.failureMessage("???????????????:"+billData.getStatus()+",?????????????????????");
		}
		return ErrorDesc.success();
	}

	/**
	 * ??????
	 * @param id  ID
	 * @param result ??????
	 * @return
	 * */
	private Result operateResult(String id,String result,String status,String message) {
		if(AssetHandleConfirmOperationEnum.SUCCESS.code().equals(result)){
			Result verifyResult= verifyBillData(id);
			if(!verifyResult.isSuccess()) return verifyResult;

			Result applayResult=applyChange(id);
			if(!applayResult.isSuccess()) return applayResult;
			AssetCollection bill=new AssetCollection();
			bill.setId(id);
			bill.setStatus(status);
			return super.update(bill,SaveMode.NOT_NULL_FIELDS);
		}else if(AssetHandleConfirmOperationEnum.FAILED.code().equals(result)){
			return ErrorDesc.failureMessage(message);
		}else{
			return ErrorDesc.failureMessage("??????????????????");
		}
	}

	/**
	 * ????????????
	 * @param id ID
	 * @return ????????????
	 * */
	@Override
	public Result confirmOperation(String id) {
		AssetCollection billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_COLLECTION.code()) ) {
				return ErrorDesc.failureMessage("????????????????????????,?????????");
			}else{
				return operateResult(id,AssetHandleConfirmOperationEnum.SUCCESS.code(),AssetHandleStatusEnum.COMPLETE.code(),"????????????");
			}
		}else{
			return ErrorDesc.failureMessage("???????????????:"+billData.getStatus()+",?????????????????????");
		}
	}




	private Result saveBorrowData(String id){
		return ErrorDesc.success();
	}

	private Result applyChange(String id){
		AssetCollection billData=getById(id);
		join(billData, AssetCollectionMeta.ASSET_LIST);
		HashMap<String,Object> map=new HashMap<>();
		map.put("asset_status",AssetStatusEnum.USING.code());
		map.put("use_user_id",billData.getUseUserId());
		map.put("position_id",billData.getPositionId());
		map.put("position_detail",billData.getPositionDetail());
		map.put("use_organization_id",billData.getUseOrganizationId());

		HashMap<String,List<SQL>> resultMap=assetService.parseAssetChangeRecordWithChangeAsset(billData.getAssetList(),map,billData.getBusinessCode(),AssetOperateEnum.EAM_ASSET_COLLECTION.code(),"");
		for(String key:resultMap.keySet()){
			List<SQL> sqls=resultMap.get(key);
			if(sqls.size()>0){
				dao.batchExecute(sqls);
			}
		}
		return ErrorDesc.success();
	}





	/**
	 * ????????????
	 * @param assetCollection ????????????
	 * @return ??????????????????
	 * */
	@Override
	@Transactional
	public Result insert(AssetCollection assetCollection) {


		if(assetCollection.getAssetIds()==null||assetCollection.getAssetIds().size()==0){
			String assetSelectedCode=assetCollection.getSelectedCode();
			ConditionExpr condition=new ConditionExpr();
			condition.andIn("asset_selected_code",assetSelectedCode==null?"":assetSelectedCode);
			List<String> list=assetSelectedDataService.queryValues(EAMTables.EAM_ASSET_SELECTED_DATA.ASSET_ID,String.class,condition);
			assetCollection.setAssetIds(list);
		}

		if(assetCollection.getAssetIds().size()==0){
			return ErrorDesc.failure().message("???????????????");
		}
		Result ckResult=assetService.checkAssetDataForBusinessAction(AssetOperateEnum.EAM_ASSET_COLLECTION.code(),assetCollection.getAssetIds());
		if(!ckResult.isSuccess()){
			return ckResult;
		}



		//?????????
		if(StringUtil.isBlank(assetCollection.getOriginatorId())){
			assetCollection.setOriginatorId(SessionUser.getCurrent().getUser().getActivatedEmployeeId());
		}

		//????????????
		if(StringUtil.isBlank(assetCollection.getBusinessDate())){
			assetCollection.setBusinessDate(new Date());
		}

		//????????????
		if(StringUtil.isBlank(assetCollection.getStatus())){
			assetCollection.setStatus(AssetHandleStatusEnum.INCOMPLETE.code());
		}

		//??????????????????
		if(StringUtil.isBlank(assetCollection.getBusinessCode())){
			Result codeResult=CodeModuleServiceProxy.api().generateCode(AssetOperateEnum.EAM_ASSET_COLLECTION.code());
			if(!codeResult.isSuccess()){
				return codeResult;
			}else{
				assetCollection.setBusinessCode(codeResult.getData().toString());
			}
		}



		Result r=super.insert(assetCollection);
		if (r.isSuccess()){
			//??????????????????
			List<AssetItem> saveList=new ArrayList<AssetItem>();
			for(int i=0;i<assetCollection.getAssetIds().size();i++){
				AssetItem asset=new AssetItem();
				asset.setId(IDGenerator.getSnowflakeIdString());
				asset.setHandleId(assetCollection.getId());
				asset.setAssetId(assetCollection.getAssetIds().get(i));
				saveList.add(asset);
			}
			Result batchInsertReuslt= assetItemService.insertList(saveList);
			if(!batchInsertReuslt.isSuccess()){
				return batchInsertReuslt;
			}
		}
		return r;
	}
	
	/**
	 * ??????????????????????????????
	 * @param assetCollectionList ??????????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result insertList(List<AssetCollection> assetCollectionList) {
		return super.insertList(assetCollectionList);
	}
	
	
	/**
	 * ??????????????? ????????????
	 *
	 * @param id ??????
	 * @return ??????????????????
	 */
	public Result deleteByIdPhysical(String id) {
		AssetCollection assetCollection = new AssetCollection();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetCollection.setId(id);
		try {
			boolean suc = dao.deleteEntity(assetCollection);
			return suc?ErrorDesc.success():ErrorDesc.failure();
		}
		catch(Exception e) {
			Result r= ErrorDesc.failure();
			r.extra().setException(e);
			return r;
		}
	}
	
	/**
	 * ??????????????? ????????????
	 *
	 * @param id ??????
	 * @return ??????????????????
	 */
	public Result deleteByIdLogical(String id) {
		AssetCollection assetCollection = new AssetCollection();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetCollection.setId(id);
		assetCollection.setDeleted(dao.getDBTreaty().getTrueValue());
		assetCollection.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		assetCollection.setDeleteTime(new Date());
		try {
			boolean suc = dao.updateEntity(assetCollection,SaveMode.NOT_NULL_FIELDS);
			return suc?ErrorDesc.success():ErrorDesc.failure();
		}
		catch(Exception e) {
			Result r= ErrorDesc.failure();
			r.extra().setException(e);
			return r;
		}
	}

	private Result verifyBillData(String handleId){
		//c  ??????,r  ?????????,d  ??????,cd ????????????
		//????????????
		ConditionExpr itemRecordcondition=new ConditionExpr();
		itemRecordcondition.andIn("handle_id",handleId);
		itemRecordcondition.andIn("crd","c","r");
		List<String> ckDatalist=assetItemService.queryValues(EAMTables.EAM_ASSET_ITEM.ASSET_ID,String.class,itemRecordcondition);
		if(ckDatalist.size()==0){
			return ErrorDesc.failure().message("???????????????");
		}
		return assetService.checkAssetDataForBusinessAction(CodeModuleEnum.EAM_ASSET_COLLECTION.code(),ckDatalist);

	}

	/**
	 * ????????????
	 * @param assetCollection ????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	@Transactional
	public Result update(AssetCollection assetCollection , SaveMode mode) {
		Result verifyResult = verifyBillData(assetCollection.getId());
		if(!verifyResult.isSuccess())return verifyResult;

		Result r=super.update(assetCollection,mode);
		if(r.success()){
			//??????????????????
			dao.execute("update eam_asset_item set crd='r' where crd='c' and handle_id=?",assetCollection.getId());
			dao.execute("delete from eam_asset_item where crd in ('d','rd') and  handle_id=?",assetCollection.getId());
		}
		return r;
	}
	
	/**
	 * ???????????????????????????
	 * @param assetCollectionList ??????????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result updateList(List<AssetCollection> assetCollectionList , SaveMode mode) {
		return super.updateList(assetCollectionList , mode);
	}
	
	
	/**
	 * ????????????????????? ????????????
	 *
	 * @param id ??????
	 * @return ??????????????????
	 */
	public boolean update(DBField field,Object value , String id) {
		if(id==null) throw new IllegalArgumentException("id ???????????? null ");
		if(!field.table().name().equals(this.table())) throw new IllegalArgumentException("??????????????????["+field.table().name()+"]???????????????????????????["+this.table()+"]?????????");
		int suc=dao.update(field.table().name()).set(field.name(), value).where().and("id = ? ",id).top().execute();
		return suc>0;
	} 
	
	
	/**
	 * ??????????????? ????????????
	 *
	 * @param id ??????
	 * @return AssetCollection ????????????
	 */
	public AssetCollection getById(String id) {
		AssetCollection sample = new AssetCollection();
		if(id==null) throw new IllegalArgumentException("id ???????????? null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}

	@Override
	public List<AssetCollection> getByIds(List<String> ids) {
		return super.queryListByUKeys("id",ids);
	}



	/**
	 * ???????????????????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param sample  ????????????
	 * @return ????????????
	 * */
	@Override
	public List<AssetCollection> queryList(AssetCollection sample) {
		return super.queryList(sample);
	}
	
	
	/**
	 * ????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param sample  ????????????
	 * @param pageSize ????????????
	 * @param pageIndex ??????
	 * @return ????????????
	 * */
	@Override
	public PagedList<AssetCollection> queryPagedList(AssetCollection sample, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_COLLECTION.code();
		return super.queryPagedList(sample, pageSize, pageIndex,dp);
	}
	
	/**
	 * ????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param sample  ????????????
	 * @param condition ????????????
	 * @param pageSize ????????????
	 * @param pageIndex ??????
	 * @return ????????????
	 * */
	@Override
	public PagedList<AssetCollection> queryPagedList(AssetCollection sample, ConditionExpr condition, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_COLLECTION.code();
		return super.queryPagedList(sample, condition, pageSize, pageIndex,dp);
	}
	
	/**
	 * ?????? ?????? ??????????????????
	 *
	 * @param assetCollection ????????????
	 * @return ????????????
	 */
	public Result<AssetCollection> checkExists(AssetCollection assetCollection) {
		//TDOD ??????????????????????????????
		//boolean exists=this.checkExists(assetCollection, SYS_ROLE.NAME);
		//return exists;
		return ErrorDesc.success();
	}

	@Override
	public ExcelWriter exportExcel(AssetCollection sample) {
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
