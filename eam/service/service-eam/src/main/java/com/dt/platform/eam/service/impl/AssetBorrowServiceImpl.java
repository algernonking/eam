package com.dt.platform.eam.service.impl;


import javax.annotation.Resource;
import javax.xml.crypto.Data;


import com.dt.platform.constants.db.EAMTables;
import com.dt.platform.constants.enums.common.CodeModuleEnum;
import com.dt.platform.constants.enums.eam.*;
import com.dt.platform.domain.eam.*;
import com.dt.platform.domain.eam.meta.AssetAllocationMeta;
import com.dt.platform.domain.eam.meta.AssetBorrowMeta;
import com.dt.platform.domain.eam.meta.AssetCollectionMeta;
import com.dt.platform.domain.eam.meta.AssetSelectedDataMeta;
import com.dt.platform.eam.common.AssetCommonError;
import com.dt.platform.eam.service.*;
import com.dt.platform.proxy.common.CodeAllocationServiceProxy;
import com.dt.platform.proxy.common.CodeModuleServiceProxy;
import com.dt.platform.proxy.eam.AssetAllocationServiceProxy;
import com.dt.platform.proxy.eam.AssetBorrowServiceProxy;
import com.github.foxnic.api.constant.CodeTextEnum;
import com.github.foxnic.api.error.CommonError;
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

import org.github.foxnic.web.framework.dao.DBConfigs;

/**
 * <p>
 * ???????????? ????????????
 * </p>
 * @author ?????? , maillank@qq.com
 * @since 2021-08-19 13:01:38
*/


@Service("EamAssetBorrowService")
public class AssetBorrowServiceImpl extends SuperService<AssetBorrow> implements IAssetBorrowService {


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
	private IAssetDataChangeService assetDataChangeService;

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
	public Result approve(String instanceId, List<AssetBorrow> assets, String approveAction, String opinion) {
		return null;
	}


	/**
	 * ????????????
	 * @param ids ??????IDS
	 * @return ????????????
	 * */
	public Result assetReturn(List<String> ids){
		Result result=new Result();
		for(String id:ids){
			Result r=assetReturn(id);
			if(!r.isSuccess()){
				result.addError(r.getMessage());
			}
		}
		return result;
	}

	/**
	 * ????????????
	 * @param id ??????ID
	 * @return ????????????
	 * */
	private Result assetReturn(String id){
		AssetBorrow bill=getById(id);
		join(bill,AssetBorrowMeta.ASSET_ITEM_LIST);
		if(!AssetBorrowStatusEnum.BORROWED.code().equals(bill.getBorrowStatus())){
			return ErrorDesc.failureMessage("?????????????????????????????????");
		}
		String str="";
		for(AssetItem item:bill.getAssetItemList()){
			Asset asset=assetService.getById(item.getAssetId());
			if(!AssetStatusEnum.BORROW.code().equals(asset.getAssetStatus())){
				str=str+"????????????"+asset.getAssetCode()+",??????????????????????????????,????????????  ";
				continue;
			}
			List<Asset> changeList=new ArrayList<>();
			changeList.add(asset);
			HashMap<String,Object> map=new HashMap<>();
			map.put("asset_status",item.getBeforeAssetStatus());
			map.put("use_user_id",item.getBeforeUseUserId()==null?"":item.getBeforeUseUserId());
			HashMap<String,List<SQL>> resultMap=assetService.parseAssetChangeRecordWithChangeAsset(changeList,map,bill.getBusinessCode(),AssetOperateEnum.EAM_ASSET_BORROW_RETURN.code(),"????????????");
			for(String key:resultMap.keySet()){
				List<SQL> sqls=resultMap.get(key);
				if(sqls.size()>0){
					dao.batchExecute(sqls);
				}
			}

		}
		if(StringUtil.isBlank(str)){
			bill.setBorrowStatus(AssetBorrowStatusEnum.RETURNED.code());
			bill.setReturnDate(new Date());
			super.update(bill,SaveMode.NOT_NULL_FIELDS);
			return  ErrorDesc.success();
		}else{
			return ErrorDesc.failureMessage(str);
		}
	}


	@Override
	public Map<String, Object> getBill(String id) {

		AssetBorrow data= AssetBorrowServiceProxy.api().getById(id).getData();
		join(data, AssetBorrowMeta.ASSET_LIST);

		Map<String, Object> map= BeanUtil.toMap(data);
		if(data.getStatus()!=null){
			CodeTextEnum en= EnumUtil.parseByCode(AssetHandleStatusEnum.class,data.getStatus());
			map.put("statusName", en==null?data.getStatus():en.text());
		}
		return map;
	}

	private void syncBill(String id, ChangeEvent event) {
		AssetBorrow asset4Update=AssetBorrow.create();
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
		AssetBorrow billData=getById(id);
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

		AssetBorrow billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_BORROW.code()) ) {
				//????????????
			}else{
				return ErrorDesc.failureMessage("???????????????????????????,???????????????????????????");
			}
		}else{
			return ErrorDesc.failureMessage("???????????????:"+billData.getStatus()+",?????????????????????");
		}
		return ErrorDesc.success();

	}


	private Result applyChange(String id){
		AssetBorrow billData=getById(id);
		join(billData, AssetBorrowMeta.ASSET_LIST);
		HashMap<String,Object> map=new HashMap<>();
		//?????????????????????
		dao.execute("update eam_asset_item a,eam_asset b set a.before_use_user_id=b.use_user_id,a.before_asset_status=b.asset_status where a.asset_id=b.id and a.handle_id=?",id);
		map.put("use_user_id",billData.getBorrowerId());
		map.put("asset_status", AssetStatusEnum.BORROW.code());
		HashMap<String,List<SQL>> resultMap=assetService.parseAssetChangeRecordWithChangeAsset(billData.getAssetList(),map,billData.getBusinessCode(),AssetOperateEnum.EAM_ASSET_BORROW.code(),"");
		for(String key:resultMap.keySet()){
			List<SQL> sqls=resultMap.get(key);
			if(sqls.size()>0){
				dao.batchExecute(sqls);
			}
		}
		billData.setBorrowStatus(AssetBorrowStatusEnum.BORROWED.code());
		super.update(billData,SaveMode.NOT_NULL_FIELDS);
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
			AssetBorrow bill=new AssetBorrow();
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
		AssetBorrow billData=getById(id);
		if(AssetHandleStatusEnum.INCOMPLETE.code().equals(billData.getStatus())){
			if(operateService.approvalRequired(AssetOperateEnum.EAM_ASSET_BORROW.code()) ) {
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
	 * @param assetBorrow ????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result insert(AssetBorrow assetBorrow) {

		if(assetBorrow.getAssetIds()==null||assetBorrow.getAssetIds().size()==0){
			String assetSelectedCode=assetBorrow.getSelectedCode();
			ConditionExpr condition=new ConditionExpr();
			condition.andIn("asset_selected_code",assetSelectedCode==null?"":assetSelectedCode);
			List<String> list=assetSelectedDataService.queryValues(EAMTables.EAM_ASSET_SELECTED_DATA.ASSET_ID,String.class,condition);
			assetBorrow.setAssetIds(list);
		}

		//??????????????????
		if(assetBorrow.getAssetIds().size()==0){
			return ErrorDesc.failure().message("???????????????");
		}

		Result ckResult=assetService.checkAssetDataForBusinessAction(AssetOperateEnum.EAM_ASSET_BORROW.code(),assetBorrow.getAssetIds());
		if(!ckResult.isSuccess()){
			return ckResult;
		}


		//???????????????
		if( StringUtil.isBlank(assetBorrow.getOriginatorId())){
			assetBorrow.setOriginatorId(SessionUser.getCurrent().getUser().getActivatedEmployeeId());
		}

		//????????????
		if(StringUtil.isBlank(assetBorrow.getBusinessDate())){
			assetBorrow.setBusinessDate(new Date());
		}

		//????????????
		if(StringUtil.isBlank( assetBorrow.getStatus())){
			assetBorrow.setStatus(AssetHandleStatusEnum.INCOMPLETE.code());
		}

		//????????????
		if(StringUtil.isBlank( assetBorrow.getBorrowStatus())){
			assetBorrow.setBorrowStatus(AssetBorrowStatusEnum.NOT_BORROWED.code());
		}

		//??????????????????
		if(StringUtil.isBlank(assetBorrow.getBusinessCode())){
			Result codeResult=CodeModuleServiceProxy.api().generateCode(AssetOperateEnum.EAM_ASSET_BORROW.code());
			if(!codeResult.isSuccess()){
				return codeResult;
			}else{
				assetBorrow.setBusinessCode(codeResult.getData().toString());
			}
		}

		Result r=super.insert(assetBorrow);
		if (r.isSuccess()){
			//??????????????????
			List<AssetItem> saveList=new ArrayList<AssetItem>();
			for(int i=0;i<assetBorrow.getAssetIds().size();i++){
				AssetItem asset=new AssetItem();
				asset.setId(IDGenerator.getSnowflakeIdString());
				asset.setHandleId(assetBorrow.getId());
				asset.setAssetId(assetBorrow.getAssetIds().get(i));
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
	 * @param assetBorrowList ??????????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result insertList(List<AssetBorrow> assetBorrowList) {
		return super.insertList(assetBorrowList);
	}


	/**
	 * ??????????????? ????????????
	 *
	 * @param id ??????
	 * @return ??????????????????
	 */
	public Result deleteByIdPhysical(String id) {
		AssetBorrow assetBorrow = new AssetBorrow();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetBorrow.setId(id);
		try {
			boolean suc = dao.deleteEntity(assetBorrow);
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
		AssetBorrow assetBorrow = new AssetBorrow();
		if(id==null) return ErrorDesc.failure().message("id ???????????? null ???");
		assetBorrow.setId(id);
		assetBorrow.setDeleted(dao.getDBTreaty().getTrueValue());
		assetBorrow.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		assetBorrow.setDeleteTime(new Date());
		try {
			boolean suc = dao.updateEntity(assetBorrow,SaveMode.NOT_NULL_FIELDS);
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
		return assetService.checkAssetDataForBusinessAction(CodeModuleEnum.EAM_ASSET_BORROW.code(),ckDatalist);
	}


	/**
	 * ????????????
	 * @param assetBorrow ????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result update(AssetBorrow assetBorrow , SaveMode mode) {
		//c  ??????,r  ?????????,d  ??????,cd ????????????
		//????????????
		Result verifyResult = verifyBillData(assetBorrow.getId());
		if(!verifyResult.isSuccess())return verifyResult;

		Result r=super.update(assetBorrow,mode);
		if(r.success()){
			//??????????????????
			dao.execute("update eam_asset_item set crd='r' where crd='c' and handle_id=?",assetBorrow.getId());
			dao.execute("delete from eam_asset_item where crd in ('d','rd') and  handle_id=?",assetBorrow.getId());
		}
		return r;
	}

	/**
	 * ???????????????????????????
	 * @param assetBorrowList ??????????????????
	 * @param mode ????????????
	 * @return ??????????????????
	 * */
	@Override
	public Result updateList(List<AssetBorrow> assetBorrowList , SaveMode mode) {
		return super.updateList(assetBorrowList , mode);
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
	 * @return AssetBorrow ????????????
	 */
	public AssetBorrow getById(String id) {
		AssetBorrow sample = new AssetBorrow();
		if(id==null) throw new IllegalArgumentException("id ???????????? null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}

	@Override
	public List<AssetBorrow> getByIds(List<String> ids) {
		return super.queryListByUKeys("id",ids);
	}



	/**
	 * ???????????????????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param sample  ????????????
	 * @return ????????????
	 * */
	@Override
	public List<AssetBorrow> queryList(AssetBorrow sample) {
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
	public PagedList<AssetBorrow> queryPagedList(AssetBorrow sample, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_BORROW.code();
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
	public PagedList<AssetBorrow> queryPagedList(AssetBorrow sample, ConditionExpr condition, int pageSize, int pageIndex) {
		String dp=AssetOperateEnum.EAM_ASSET_BORROW.code();
		return super.queryPagedList(sample, condition, pageSize, pageIndex,dp);
	}

	/**
	 * ?????? ?????? ??????????????????
	 *
	 * @param assetBorrow ????????????
	 * @return ????????????
	 */
	public Result<AssetBorrow> checkExists(AssetBorrow assetBorrow) {
		//TDOD ??????????????????????????????
		//boolean exists=this.checkExists(assetBorrow, SYS_ROLE.NAME);
		//return exists;
		return ErrorDesc.success();
	}

	@Override
	public ExcelWriter exportExcel(AssetBorrow sample) {
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
