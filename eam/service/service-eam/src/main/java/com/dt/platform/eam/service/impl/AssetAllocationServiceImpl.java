package com.dt.platform.eam.service.impl;


import javax.annotation.Resource;

import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.constants.enums.common.CodeModuleEnum;
import com.dt.platform.constants.enums.eam.AssetHandleConfirmOperationEnum;
import com.dt.platform.constants.enums.eam.AssetHandleStatusEnum;
import com.dt.platform.constants.enums.eam.AssetOperateEnum;
import com.dt.platform.domain.eam.*;
import com.dt.platform.domain.eam.meta.AssetAllocationMeta;
import com.dt.platform.domain.eam.meta.AssetCollectionMeta;
import com.dt.platform.domain.eam.meta.AssetDataChangeMeta;
import com.dt.platform.eam.common.AssetCommonError;
import com.dt.platform.eam.service.*;
import com.dt.platform.proxy.common.CodeModuleServiceProxy;
import com.dt.platform.proxy.eam.AssetAllocationServiceProxy;
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
import org.github.foxnic.web.framework.dao.DBConfigs;

/**
 * <p>
 * ???????????? ????????????
 * </p>
 * @author ?????? , maillank@qq.com
 * @since 2021-08-20 20:57:55
*/


@Service("EamAssetAllocationService")
public class AssetAllocationServiceImpl extends SuperService<AssetAllocation> implements IAssetAllocationService {



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
	private IAssetItemService assetItemService;

	@Autowired
	private IAssetSelectedDataService assetSelectedDataService;

	@Autowired
	private IAssetService assetService;

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
	public Result approve(String instanceId, List<AssetAllocation> assets, String approveAction, String opinion) {
		return null;
	}

	@Override
	public Map<String, Object> getBill(String id) {


		AssetAllocation data=AssetAllocationServiceProxy.api().getById(id).getData();

		join(data,AssetAllocationMeta.ASSET_LIST);
		Map<String, Object> map= BeanUtil.toMap(data);
		if(data.getStatus()!=null){
			CodeTextEnum en= EnumUtil.parseByCode(AssetHandleStatusEnum.class,data.getStatus());
			map.put("statusName", en==null?data.getStatus():en.text());
		}
		return map;

	}


	private void syncBill(String id, ChangeEvent event) {
		AssetAllocation asset4Update=AssetAllocation.create();
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
	public Result forApproval(String id){
		AssetAllocation billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_ALLOCATE.code()) ) {
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
			AssetAllocation bill=new AssetAllocation();
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
		AssetAllocation billData=getById(id);
		join(billData, AssetAllocationMeta.ASSET_LIST);
		HashMap<String,Object> map=new HashMap<>();
		map.put("own_company_id",billData.getInOwnCompanyId());
		map.put("manager_id",billData.getManagerId());
		HashMap<String,List<SQL>> resultMap=assetService.parseAssetChangeRecordWithChangeAsset(billData.getAssetList(),map,billData.getBusinessCode(),AssetOperateEnum.EAM_ASSET_ALLOCATE.code(),"");
		for(String key:resultMap.keySet()){
			List<SQL> sqls=resultMap.get(key);
			if(sqls.size()>0){
				dao.batchExecute(sqls);
			}
		}
		return ErrorDesc.success();
	}

	/**
	 * ??????
	 * @param id ID
	 * @return ????????????
	 * */
	@Override
	public Result revokeOperation(String id) {
		AssetAllocation billData=getById(id);
		if(AssetHandleStatusEnum.APPROVAL.code().equals(billData.getStatus())){

		}else{
			return ErrorDesc.failureMessage("?????????????????????????????????????????????");
		}
		return ErrorDesc.success();
	}

	//??????:??????(?????????)  -- ????????? --- ??????
	/**
	 * ????????????,
	 * @param id ID
	 * @return ????????????
	 * */
	@Override
	public Result confirmOperation(String id) {
		AssetAllocation billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_ALLOCATE.code()) ) {
				return ErrorDesc.failureMessage("????????????????????????,?????????");
			}else{
				return operateResult(id,AssetHandleConfirmOperationEnum.SUCCESS.code(),AssetHandleStatusEnum.COMPLETE.code(),"????????????");
			}
		}else{
			return ErrorDesc.failureMessage("???????????????:"+billData.getStatus()+",?????????????????????");
		}
	}

	/**
	 * ????????????
	 * @param assetAllocation ????????????
	 * @return ??????????????????
	 * */
	@Override
	@Transactional
	public Result insert(AssetAllocation assetAllocation) {

		if(assetAllocation.getAssetIds()==null||assetAllocation.getAssetIds().size()==0){
			String assetSelectedCode=assetAllocation.getSelectedCode();
			ConditionExpr condition=new ConditionExpr();
			condition.andIn("asset_selected_code",assetSelectedCode==null?"":assetSelectedCode);
			List<String> list=assetSelectedDataService.queryValues(EAMTables.EAM_ASSET_SELECTED_DATA.ASSET_ID,String.class,condition);
			assetAllocation.setAssetIds(list);
		}



		//??????????????????
		if(assetAllocation.getAssetIds().size()==0){
			return ErrorDesc.failure().message("???????????????");
		}

		Result ckResult=assetService.checkAssetDataForBusinessAction(AssetOperateEnum.EAM_ASSET_ALLOCATE.code(),assetAllocation.getAssetIds());
		if(!ckResult.isSuccess()){
			return ckResult;
		}


		//?????????
		if(StringUtil.isBlank(assetAllocation.getOriginatorId())){
			assetAllocation.setOriginatorId(SessionUser.getCurrent().getUser().getActivatedEmployeeId());
		}

		//????????????
		if(StringUtil.isBlank(assetAllocation.getBusinessDate())){
			assetAllocation.setBusinessDate(new Date());
		}


		//????????????
		if(StringUtil.isBlank(assetAllocation.getStatus())){
			assetAllocation.setStatus(AssetHandleStatusEnum.INCOMPLETE.code());
		}

		//??????????????????
		if(StringUtil.isBlank(assetAllocation.getBusinessCode())){
			Result codeResult=CodeModuleServiceProxy.api().generateCode(AssetOperateEnum.EAM_ASSET_ALLOCATE.code());
			if(!codeResult.isSuccess()){
				return codeResult;
			}else{
				assetAllocation.setBusinessCode(codeResult.getData().toString());
			}
		}

		Result r=super.insert(assetAllocation);
		if (r.isSuccess()){
			//??????????????????
			List<AssetItem> saveList=new ArrayList<AssetItem>();
			for(int i=0;i<assetAllocation.getAssetIds().size();i++){
				AssetItem asset=new AssetItem();
				asset.setId(IDGenerator.getSnowflakeIdString());
				asset.setHandleId(assetAllocation.getId());
				asset.setAssetId(assetAllocation.getAssetIds().get(i));
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
	 * @param assetAllocationList ??????????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result insertList(List<AssetAllocation> assetAllocationList) {
		return super.insertList(assetAllocationList);
	}


	/**
	 * ??????????????? ????????????
	 *
	 * @param id ??????
	 * @return ??????????????????
	 */
	public Result deleteByIdPhysical(String id) {
		AssetAllocation assetAllocation = new AssetAllocation();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetAllocation.setId(id);
		try {
			boolean suc = dao.deleteEntity(assetAllocation);
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
		AssetAllocation assetAllocation = new AssetAllocation();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetAllocation.setId(id);
		assetAllocation.setDeleted(dao.getDBTreaty().getTrueValue());
		assetAllocation.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		assetAllocation.setDeleteTime(new Date());
		try {
			boolean suc = dao.updateEntity(assetAllocation,SaveMode.NOT_NULL_FIELDS);
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
		return assetService.checkAssetDataForBusinessAction(CodeModuleEnum.EAM_ASSET_ALLOCATE.code(),ckDatalist);

	}
	/**
	 * ????????????
	 * @param assetAllocation ????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	@Transactional
	public Result update(AssetAllocation assetAllocation , SaveMode mode) {

		Result verifyResult = verifyBillData(assetAllocation.getId());
		if(!verifyResult.isSuccess())return verifyResult;

		Result r=super.update(assetAllocation,mode);
		if(r.success()){
			//??????????????????
			dao.execute("update eam_asset_item set crd='r' where crd='c' and handle_id=?",assetAllocation.getId());
			dao.execute("delete from eam_asset_item where crd in ('d','rd') and  handle_id=?",assetAllocation.getId());
		}
		return r;

	}

	/**
	 * ???????????????????????????
	 * @param assetAllocationList ??????????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result updateList(List<AssetAllocation> assetAllocationList , SaveMode mode) {
		return super.updateList(assetAllocationList , mode);
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
	 * @return AssetAllocation ????????????
	 */
	public AssetAllocation getById(String id) {
		AssetAllocation sample = new AssetAllocation();
		if(id==null) throw new IllegalArgumentException("id ???????????? null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}

	@Override
	public List<AssetAllocation> getByIds(List<String> ids) {
		return super.queryListByUKeys("id",ids);
	}



	/**
	 * ???????????????????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param sample  ????????????
	 * @return ????????????
	 * */
	@Override
	public List<AssetAllocation> queryList(AssetAllocation sample) {
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
	public PagedList<AssetAllocation> queryPagedList(AssetAllocation sample, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_ALLOCATE.code();
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
	public PagedList<AssetAllocation> queryPagedList(AssetAllocation sample, ConditionExpr condition, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_ALLOCATE.code();
		return super.queryPagedList(sample, condition, pageSize, pageIndex,dp);
	}

	/**
	 * ?????? ?????? ??????????????????
	 *
	 * @param assetAllocation ????????????
	 * @return ????????????
	 */
	public Result<AssetAllocation> checkExists(AssetAllocation assetAllocation) {
		//TDOD ??????????????????????????????
		//boolean exists=this.checkExists(assetAllocation, SYS_ROLE.NAME);
		//return exists;
		return ErrorDesc.success();
	}

	@Override
	public ExcelWriter exportExcel(AssetAllocation sample) {
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
