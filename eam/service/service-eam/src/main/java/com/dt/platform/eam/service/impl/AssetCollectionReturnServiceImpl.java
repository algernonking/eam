package com.dt.platform.eam.service.impl;


import javax.annotation.Resource;

import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.constants.enums.common.CodeModuleEnum;
import com.dt.platform.constants.enums.eam.AssetHandleConfirmOperationEnum;
import com.dt.platform.constants.enums.eam.AssetHandleStatusEnum;
import com.dt.platform.constants.enums.eam.AssetOperateEnum;
import com.dt.platform.constants.enums.eam.AssetStatusEnum;
import com.dt.platform.domain.eam.*;
import com.dt.platform.domain.eam.meta.AssetBorrowMeta;
import com.dt.platform.domain.eam.meta.AssetCollectionMeta;
import com.dt.platform.domain.eam.meta.AssetCollectionReturnMeta;
import com.dt.platform.eam.common.AssetCommonError;
import com.dt.platform.eam.service.IAssetSelectedDataService;
import com.dt.platform.eam.service.IAssetService;
import com.dt.platform.eam.service.IOperateService;
import com.dt.platform.proxy.common.CodeModuleServiceProxy;
import com.dt.platform.proxy.eam.AssetBorrowServiceProxy;
import com.dt.platform.proxy.eam.AssetCollectionReturnServiceProxy;
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
import com.dt.platform.eam.service.IAssetCollectionReturnService;
import org.github.foxnic.web.framework.dao.DBConfigs;

/**
 * <p>
 * ???????????? ????????????
 * </p>
 * @author ?????? , maillank@qq.com
 * @since 2021-08-21 15:45:51
*/


@Service("EamAssetCollectionReturnService")
public class AssetCollectionReturnServiceImpl extends SuperService<AssetCollectionReturn> implements IAssetCollectionReturnService {
	
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
	public Result startProcess(ProcessStartVO startVO) {
		return null;
	}

	@Override
	public Result approve(ProcessApproveVO approveVO) {
		return null;
	}


	@Override
	public Result approve(String instanceId,List<AssetCollectionReturn> assets, String approveAction, String opinion) {
		return null;
	}

	@Override
	public Map<String, Object> getBill(String id) {

		AssetCollectionReturn data= AssetCollectionReturnServiceProxy.api().getById(id).getData();
		join(data, AssetCollectionReturnMeta.ASSET_LIST);

		Map<String, Object> map= BeanUtil.toMap(data);
		if(data.getStatus()!=null){
			CodeTextEnum en= EnumUtil.parseByCode(AssetHandleStatusEnum.class,data.getStatus());
			map.put("statusName", en==null?data.getStatus():en.text());
		}
		return map;
	}

	private void syncBill(String id, ChangeEvent event) {
		AssetCollectionReturn asset4Update=AssetCollectionReturn.create();
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
		AssetCollectionReturn billData=getById(id);
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

		AssetCollectionReturn billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code()) ) {
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
	 * ????????????
	 * @param id ID
	 * @return ????????????
	 * */
	@Override
	public Result confirmOperation(String id) {
		AssetCollectionReturn billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code()) ) {
				return ErrorDesc.failureMessage("????????????????????????,?????????");
			}else{
				return operateResult(id,AssetHandleConfirmOperationEnum.SUCCESS.code(),AssetHandleStatusEnum.COMPLETE.code(),"????????????");
			}
		}else{
			return ErrorDesc.failureMessage("???????????????:"+billData.getStatus()+",?????????????????????");
		}
	}


	@Override
	public Object generateId(Field field) {
		return IDGenerator.getSnowflakeIdString();
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
			AssetCollectionReturn bill=new AssetCollectionReturn();
			bill.setId(id);
			bill.setStatus(status);
			return super.update(bill,SaveMode.NOT_NULL_FIELDS);
		}else if(AssetHandleConfirmOperationEnum.FAILED.code().equals(result)){
			return ErrorDesc.failureMessage(message);
		}else{
			return ErrorDesc.failureMessage("??????????????????");
		}
	}

	private Result applyChange(String id){
		AssetCollectionReturn billData=getById(id);
		join(billData, AssetCollectionReturnMeta.ASSET_LIST);
		HashMap<String,Object> map=new HashMap<>();
		map.put("asset_status", AssetStatusEnum.IDLE.code());
		map.put("use_user_id","");
		map.put("position_id",billData.getPositionId());
		map.put("position_detail",billData.getPositionDetail());
		map.put("use_organization_id",billData.getUseOrganizationId());
		HashMap<String,List<SQL>> resultMap=assetService.parseAssetChangeRecordWithChangeAsset(billData.getAssetList(),map,billData.getBusinessCode(),AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code(),"");
		List<SQL> updateSqls=resultMap.get("update");
		List<SQL> changeSqls=resultMap.get("change");
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
	 * @param assetCollectionReturn ????????????
	 * @return ??????????????????
	 * */
	@Override
	@Transactional
	public Result insert(AssetCollectionReturn assetCollectionReturn) {

		if(assetCollectionReturn.getAssetIds()==null||assetCollectionReturn.getAssetIds().size()==0){
			String assetSelectedCode=assetCollectionReturn.getSelectedCode();
			ConditionExpr condition=new ConditionExpr();
			condition.andIn("asset_selected_code",assetSelectedCode==null?"":assetSelectedCode);
			List<String> list=assetSelectedDataService.queryValues(EAMTables.EAM_ASSET_SELECTED_DATA.ASSET_ID,String.class,condition);
			assetCollectionReturn.setAssetIds(list);
		}


		//??????????????????
		if(assetCollectionReturn.getAssetIds().size()==0){
			return ErrorDesc.failure().message("???????????????");
		}
		Result ckResult=assetService.checkAssetDataForBusinessAction(AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code(),assetCollectionReturn.getAssetIds());
		if(!ckResult.isSuccess()){
			return ckResult;
		}



		//?????????
		if(StringUtil.isBlank(assetCollectionReturn.getOriginatorId())){
			assetCollectionReturn.setOriginatorId(SessionUser.getCurrent().getUser().getActivatedEmployeeId());
		}

		//????????????
		if(StringUtil.isBlank(assetCollectionReturn.getBusinessDate())){
			assetCollectionReturn.setBusinessDate(new Date());
		}

		//????????????
		if(StringUtil.isBlank(assetCollectionReturn.getStatus())){
			assetCollectionReturn.setStatus(AssetHandleStatusEnum.INCOMPLETE.code());
		}

		//??????????????????
		if(StringUtil.isBlank(assetCollectionReturn.getBusinessCode())){
			Result codeResult=CodeModuleServiceProxy.api().generateCode(AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code());
			if(!codeResult.isSuccess()){
				return codeResult;
			}else{
				assetCollectionReturn.setBusinessCode(codeResult.getData().toString());
			}
		}


		Result r=super.insert(assetCollectionReturn);
		if (r.isSuccess()){
			//??????????????????
			List<AssetItem> saveList=new ArrayList<AssetItem>();
			for(int i=0;i<assetCollectionReturn.getAssetIds().size();i++){
				AssetItem asset=new AssetItem();
				asset.setId(IDGenerator.getSnowflakeIdString());
				asset.setHandleId(assetCollectionReturn.getId());
				asset.setAssetId(assetCollectionReturn.getAssetIds().get(i));
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
	 * @param assetCollectionReturnList ??????????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result insertList(List<AssetCollectionReturn> assetCollectionReturnList) {
		return super.insertList(assetCollectionReturnList);
	}
	
	
	/**
	 * ??????????????? ????????????
	 *
	 * @param id ??????
	 * @return ??????????????????
	 */
	public Result deleteByIdPhysical(String id) {
		AssetCollectionReturn assetCollectionReturn = new AssetCollectionReturn();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetCollectionReturn.setId(id);
		try {
			boolean suc = dao.deleteEntity(assetCollectionReturn);
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
		AssetCollectionReturn assetCollectionReturn = new AssetCollectionReturn();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetCollectionReturn.setId(id);
		assetCollectionReturn.setDeleted(dao.getDBTreaty().getTrueValue());
		assetCollectionReturn.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		assetCollectionReturn.setDeleteTime(new Date());
		try {
			boolean suc = dao.updateEntity(assetCollectionReturn,SaveMode.NOT_NULL_FIELDS);
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
		return assetService.checkAssetDataForBusinessAction(CodeModuleEnum.EAM_ASSET_COLLECTION_RETURN.code(),ckDatalist);
	}

	/**
	 * ????????????
	 * @param assetCollectionReturn ????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	@Transactional
	public Result update(AssetCollectionReturn assetCollectionReturn , SaveMode mode) {

		Result verifyResult = verifyBillData(assetCollectionReturn.getId());
		if(!verifyResult.isSuccess())return verifyResult;

		Result r=super.update(assetCollectionReturn,mode);
		if(r.success()){
			//??????????????????
			dao.execute("update eam_asset_item set crd='r' where crd='c' and handle_id=?",assetCollectionReturn.getId());
			dao.execute("delete from eam_asset_item where crd in ('d','rd') and  handle_id=?",assetCollectionReturn.getId());
		}
		return r;
	}
	
	/**
	 * ???????????????????????????
	 * @param assetCollectionReturnList ??????????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result updateList(List<AssetCollectionReturn> assetCollectionReturnList , SaveMode mode) {
		return super.updateList(assetCollectionReturnList , mode);
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
	 * @return AssetCollectionReturn ????????????
	 */
	public AssetCollectionReturn getById(String id) {
		AssetCollectionReturn sample = new AssetCollectionReturn();
		if(id==null) throw new IllegalArgumentException("id ???????????? null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}

	@Override
	public List<AssetCollectionReturn> getByIds(List<String> ids) {
		return super.queryListByUKeys("id",ids);
	}



	/**
	 * ???????????????????????????????????????????????????????????????????????????????????????????????????
	 * 
	 * @param sample  ????????????
	 * @return ????????????
	 * */
	@Override
	public List<AssetCollectionReturn> queryList(AssetCollectionReturn sample) {
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
	public PagedList<AssetCollectionReturn> queryPagedList(AssetCollectionReturn sample, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code();
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
	public PagedList<AssetCollectionReturn> queryPagedList(AssetCollectionReturn sample, ConditionExpr condition, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_COLLECTION_RETURN.code();
		return super.queryPagedList(sample, condition, pageSize, pageIndex,dp);
	}
	
	/**
	 * ?????? ?????? ??????????????????
	 *
	 * @param assetCollectionReturn ????????????
	 * @return ????????????
	 */
	public Result<AssetCollectionReturn> checkExists(AssetCollectionReturn assetCollectionReturn) {
		//TDOD ??????????????????????????????
		//boolean exists=this.checkExists(assetCollectionReturn, SYS_ROLE.NAME);
		//return exists;
		return ErrorDesc.success();
	}

	@Override
	public ExcelWriter exportExcel(AssetCollectionReturn sample) {
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
