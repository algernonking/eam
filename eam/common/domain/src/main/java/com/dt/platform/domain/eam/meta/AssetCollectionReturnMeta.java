package com.dt.platform.domain.eam.meta;

import com.github.foxnic.api.bean.BeanProperty;
import com.dt.platform.domain.eam.AssetCollectionReturn;
import java.util.Date;
import com.dt.platform.domain.eam.Position;
import com.dt.platform.domain.eam.Asset;



/**
 * @author 金杰 , maillank@qq.com
 * @since 2021-08-19 13:01:33
 * @sign A24D30A2E80D059AD4DA24CE600526C4
 * 此文件由工具自动生成，请勿修改。若表结构或配置发生变动，请使用工具重新生成。
*/

public class AssetCollectionReturnMeta {
	
	/**
	 * 主键 , 类型: java.lang.String
	*/
	public static final String ID="id";
	
	/**
	 * 主键 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> ID_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,ID, java.lang.String.class, "主键", "主键", java.lang.String.class, null);
	
	/**
	 * 业务名称 , 类型: java.lang.String
	*/
	public static final String NAME="name";
	
	/**
	 * 业务名称 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> NAME_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,NAME, java.lang.String.class, "业务名称", "业务名称", java.lang.String.class, null);
	
	/**
	 * 业务编号 , 类型: java.lang.String
	*/
	public static final String BUSINESS_CODE="businessCode";
	
	/**
	 * 业务编号 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> BUSINESS_CODE_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,BUSINESS_CODE, java.lang.String.class, "业务编号", "业务编号", java.lang.String.class, null);
	
	/**
	 * 流程 , 类型: java.lang.String
	*/
	public static final String PROC_ID="procId";
	
	/**
	 * 流程 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> PROC_ID_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,PROC_ID, java.lang.String.class, "流程", "流程", java.lang.String.class, null);
	
	/**
	 * 办理状态 , 类型: java.lang.String
	*/
	public static final String STATUS="status";
	
	/**
	 * 办理状态 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> STATUS_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,STATUS, java.lang.String.class, "办理状态", "办理状态", java.lang.String.class, null);
	
	/**
	 * 制单人 , 类型: java.lang.String
	*/
	public static final String ORIGINATOR_ID="originatorId";
	
	/**
	 * 制单人 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> ORIGINATOR_ID_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,ORIGINATOR_ID, java.lang.String.class, "制单人", "制单人", java.lang.String.class, null);
	
	/**
	 * 退库日期 , 类型: java.util.Date
	*/
	public static final String RETURN_DATE="returnDate";
	
	/**
	 * 退库日期 , 类型: java.util.Date
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.util.Date> RETURN_DATE_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,RETURN_DATE, java.util.Date.class, "退库日期", "退库日期", java.util.Date.class, null);
	
	/**
	 * 退库使用组织 , 类型: java.lang.String
	*/
	public static final String USER_ORGANIZATION_ID="userOrganizationId";
	
	/**
	 * 退库使用组织 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> USER_ORGANIZATION_ID_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,USER_ORGANIZATION_ID, java.lang.String.class, "退库使用组织", "退库使用组织", java.lang.String.class, null);
	
	/**
	 * 退库后位置 , 类型: java.lang.String
	*/
	public static final String POSITION_ID="positionId";
	
	/**
	 * 退库后位置 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> POSITION_ID_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,POSITION_ID, java.lang.String.class, "退库后位置", "退库后位置", java.lang.String.class, null);
	
	/**
	 * 详细位置 , 类型: java.lang.String
	*/
	public static final String POSITION_DETAIL="positionDetail";
	
	/**
	 * 详细位置 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> POSITION_DETAIL_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,POSITION_DETAIL, java.lang.String.class, "详细位置", "详细位置", java.lang.String.class, null);
	
	/**
	 * 退库说明 , 类型: java.lang.String
	*/
	public static final String CONTENT="content";
	
	/**
	 * 退库说明 , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> CONTENT_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,CONTENT, java.lang.String.class, "退库说明", "退库说明", java.lang.String.class, null);
	
	/**
	 * 业务日期 , 类型: java.util.Date
	*/
	public static final String BUSINESS_DATE="businessDate";
	
	/**
	 * 业务日期 , 类型: java.util.Date
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.util.Date> BUSINESS_DATE_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,BUSINESS_DATE, java.util.Date.class, "业务日期", "业务日期", java.util.Date.class, null);
	
	/**
	 * 创建人ID , 类型: java.lang.String
	*/
	public static final String CREATE_BY="createBy";
	
	/**
	 * 创建人ID , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> CREATE_BY_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,CREATE_BY, java.lang.String.class, "创建人ID", "创建人ID", java.lang.String.class, null);
	
	/**
	 * 创建时间 , 类型: java.util.Date
	*/
	public static final String CREATE_TIME="createTime";
	
	/**
	 * 创建时间 , 类型: java.util.Date
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.util.Date> CREATE_TIME_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,CREATE_TIME, java.util.Date.class, "创建时间", "创建时间", java.util.Date.class, null);
	
	/**
	 * 修改人ID , 类型: java.lang.String
	*/
	public static final String UPDATE_BY="updateBy";
	
	/**
	 * 修改人ID , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> UPDATE_BY_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,UPDATE_BY, java.lang.String.class, "修改人ID", "修改人ID", java.lang.String.class, null);
	
	/**
	 * 修改时间 , 类型: java.util.Date
	*/
	public static final String UPDATE_TIME="updateTime";
	
	/**
	 * 修改时间 , 类型: java.util.Date
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.util.Date> UPDATE_TIME_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,UPDATE_TIME, java.util.Date.class, "修改时间", "修改时间", java.util.Date.class, null);
	
	/**
	 * 是否已删除 , 类型: java.lang.Integer
	*/
	public static final String DELETED="deleted";
	
	/**
	 * 是否已删除 , 类型: java.lang.Integer
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.Integer> DELETED_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,DELETED, java.lang.Integer.class, "是否已删除", "是否已删除", java.lang.Integer.class, null);
	
	/**
	 * 删除人ID , 类型: java.lang.String
	*/
	public static final String DELETE_BY="deleteBy";
	
	/**
	 * 删除人ID , 类型: java.lang.String
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.String> DELETE_BY_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,DELETE_BY, java.lang.String.class, "删除人ID", "删除人ID", java.lang.String.class, null);
	
	/**
	 * 删除时间 , 类型: java.util.Date
	*/
	public static final String DELETE_TIME="deleteTime";
	
	/**
	 * 删除时间 , 类型: java.util.Date
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.util.Date> DELETE_TIME_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,DELETE_TIME, java.util.Date.class, "删除时间", "删除时间", java.util.Date.class, null);
	
	/**
	 * version , 类型: java.lang.Integer
	*/
	public static final String VERSION="version";
	
	/**
	 * version , 类型: java.lang.Integer
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,java.lang.Integer> VERSION_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,VERSION, java.lang.Integer.class, "version", "version", java.lang.Integer.class, null);
	
	/**
	 * 存放位置 , 类型: com.dt.platform.domain.eam.Position
	*/
	public static final String POSITION="position";
	
	/**
	 * 存放位置 , 类型: com.dt.platform.domain.eam.Position
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,com.dt.platform.domain.eam.Position> POSITION_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,POSITION, com.dt.platform.domain.eam.Position.class, "存放位置", "存放位置", com.dt.platform.domain.eam.Position.class, null);
	
	/**
	 * 资产 , 类型: com.dt.platform.domain.eam.Asset
	*/
	public static final String ASSET="asset";
	
	/**
	 * 资产 , 类型: com.dt.platform.domain.eam.Asset
	*/
	public static final BeanProperty<com.dt.platform.domain.eam.AssetCollectionReturn,com.dt.platform.domain.eam.Asset> ASSET_PROP = new BeanProperty(com.dt.platform.domain.eam.AssetCollectionReturn.class ,ASSET, com.dt.platform.domain.eam.Asset.class, "资产", "资产", com.dt.platform.domain.eam.Asset.class, null);
	
	/**
	 * 全部属性清单
	*/
	public static final String[] $PROPS={ ID , NAME , BUSINESS_CODE , PROC_ID , STATUS , ORIGINATOR_ID , RETURN_DATE , USER_ORGANIZATION_ID , POSITION_ID , POSITION_DETAIL , CONTENT , BUSINESS_DATE , CREATE_BY , CREATE_TIME , UPDATE_BY , UPDATE_TIME , DELETED , DELETE_BY , DELETE_TIME , VERSION , POSITION , ASSET };
	
	/**
	 * 代理类
	*/
	public static class $$proxy$$ extends com.dt.platform.domain.eam.AssetCollectionReturn {

		private static final long serialVersionUID = 1L;

		
		/**
		 * 设置 主键
		 * @param id 主键
		 * @return 当前对象
		*/
		public AssetCollectionReturn setId(String id) {
			super.change(ID,super.getId(),id);
			super.setId(id);
			return this;
		}
		
		/**
		 * 设置 业务名称
		 * @param name 业务名称
		 * @return 当前对象
		*/
		public AssetCollectionReturn setName(String name) {
			super.change(NAME,super.getName(),name);
			super.setName(name);
			return this;
		}
		
		/**
		 * 设置 业务编号
		 * @param businessCode 业务编号
		 * @return 当前对象
		*/
		public AssetCollectionReturn setBusinessCode(String businessCode) {
			super.change(BUSINESS_CODE,super.getBusinessCode(),businessCode);
			super.setBusinessCode(businessCode);
			return this;
		}
		
		/**
		 * 设置 流程
		 * @param procId 流程
		 * @return 当前对象
		*/
		public AssetCollectionReturn setProcId(String procId) {
			super.change(PROC_ID,super.getProcId(),procId);
			super.setProcId(procId);
			return this;
		}
		
		/**
		 * 设置 办理状态
		 * @param status 办理状态
		 * @return 当前对象
		*/
		public AssetCollectionReturn setStatus(String status) {
			super.change(STATUS,super.getStatus(),status);
			super.setStatus(status);
			return this;
		}
		
		/**
		 * 设置 制单人
		 * @param originatorId 制单人
		 * @return 当前对象
		*/
		public AssetCollectionReturn setOriginatorId(String originatorId) {
			super.change(ORIGINATOR_ID,super.getOriginatorId(),originatorId);
			super.setOriginatorId(originatorId);
			return this;
		}
		
		/**
		 * 设置 退库日期
		 * @param returnDate 退库日期
		 * @return 当前对象
		*/
		public AssetCollectionReturn setReturnDate(Date returnDate) {
			super.change(RETURN_DATE,super.getReturnDate(),returnDate);
			super.setReturnDate(returnDate);
			return this;
		}
		
		/**
		 * 设置 退库使用组织
		 * @param userOrganizationId 退库使用组织
		 * @return 当前对象
		*/
		public AssetCollectionReturn setUserOrganizationId(String userOrganizationId) {
			super.change(USER_ORGANIZATION_ID,super.getUserOrganizationId(),userOrganizationId);
			super.setUserOrganizationId(userOrganizationId);
			return this;
		}
		
		/**
		 * 设置 退库后位置
		 * @param positionId 退库后位置
		 * @return 当前对象
		*/
		public AssetCollectionReturn setPositionId(String positionId) {
			super.change(POSITION_ID,super.getPositionId(),positionId);
			super.setPositionId(positionId);
			return this;
		}
		
		/**
		 * 设置 详细位置
		 * @param positionDetail 详细位置
		 * @return 当前对象
		*/
		public AssetCollectionReturn setPositionDetail(String positionDetail) {
			super.change(POSITION_DETAIL,super.getPositionDetail(),positionDetail);
			super.setPositionDetail(positionDetail);
			return this;
		}
		
		/**
		 * 设置 退库说明
		 * @param content 退库说明
		 * @return 当前对象
		*/
		public AssetCollectionReturn setContent(String content) {
			super.change(CONTENT,super.getContent(),content);
			super.setContent(content);
			return this;
		}
		
		/**
		 * 设置 业务日期
		 * @param businessDate 业务日期
		 * @return 当前对象
		*/
		public AssetCollectionReturn setBusinessDate(Date businessDate) {
			super.change(BUSINESS_DATE,super.getBusinessDate(),businessDate);
			super.setBusinessDate(businessDate);
			return this;
		}
		
		/**
		 * 设置 创建人ID
		 * @param createBy 创建人ID
		 * @return 当前对象
		*/
		public AssetCollectionReturn setCreateBy(String createBy) {
			super.change(CREATE_BY,super.getCreateBy(),createBy);
			super.setCreateBy(createBy);
			return this;
		}
		
		/**
		 * 设置 创建时间
		 * @param createTime 创建时间
		 * @return 当前对象
		*/
		public AssetCollectionReturn setCreateTime(Date createTime) {
			super.change(CREATE_TIME,super.getCreateTime(),createTime);
			super.setCreateTime(createTime);
			return this;
		}
		
		/**
		 * 设置 修改人ID
		 * @param updateBy 修改人ID
		 * @return 当前对象
		*/
		public AssetCollectionReturn setUpdateBy(String updateBy) {
			super.change(UPDATE_BY,super.getUpdateBy(),updateBy);
			super.setUpdateBy(updateBy);
			return this;
		}
		
		/**
		 * 设置 修改时间
		 * @param updateTime 修改时间
		 * @return 当前对象
		*/
		public AssetCollectionReturn setUpdateTime(Date updateTime) {
			super.change(UPDATE_TIME,super.getUpdateTime(),updateTime);
			super.setUpdateTime(updateTime);
			return this;
		}
		
		/**
		 * 设置 是否已删除
		 * @param deleted 是否已删除
		 * @return 当前对象
		*/
		public AssetCollectionReturn setDeleted(Integer deleted) {
			super.change(DELETED,super.getDeleted(),deleted);
			super.setDeleted(deleted);
			return this;
		}
		
		/**
		 * 设置 删除人ID
		 * @param deleteBy 删除人ID
		 * @return 当前对象
		*/
		public AssetCollectionReturn setDeleteBy(String deleteBy) {
			super.change(DELETE_BY,super.getDeleteBy(),deleteBy);
			super.setDeleteBy(deleteBy);
			return this;
		}
		
		/**
		 * 设置 删除时间
		 * @param deleteTime 删除时间
		 * @return 当前对象
		*/
		public AssetCollectionReturn setDeleteTime(Date deleteTime) {
			super.change(DELETE_TIME,super.getDeleteTime(),deleteTime);
			super.setDeleteTime(deleteTime);
			return this;
		}
		
		/**
		 * 设置 version
		 * @param version version
		 * @return 当前对象
		*/
		public AssetCollectionReturn setVersion(Integer version) {
			super.change(VERSION,super.getVersion(),version);
			super.setVersion(version);
			return this;
		}
		
		/**
		 * 设置 存放位置
		 * @param position 存放位置
		 * @return 当前对象
		*/
		public AssetCollectionReturn setPosition(Position position) {
			super.change(POSITION,super.getPosition(),position);
			super.setPosition(position);
			return this;
		}
		
		/**
		 * 设置 资产
		 * @param asset 资产
		 * @return 当前对象
		*/
		public AssetCollectionReturn setAsset(Asset asset) {
			super.change(ASSET,super.getAsset(),asset);
			super.setAsset(asset);
			return this;
		}
	}
}