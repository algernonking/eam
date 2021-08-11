package com.dt.platform.domain.eam;

import com.github.foxnic.dao.entity.Entity;
import javax.persistence.Table;
import com.github.foxnic.sql.meta.DBTable;
import com.dt.platform.constants.db.EAMTables.EAM_ASSET;
import javax.persistence.Id;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Transient;
import java.util.Map;
import com.github.foxnic.dao.entity.EntityContext;



/**
 * null
 * @author 金杰 , maillank@qq.com
 * @since 2021-08-05 15:17:41
 * @sign 8F2803FCFE3D79B0FA341A44DB9D25F9
 * 此文件由工具自动生成，请勿修改。若表结构或配置发生变动，请使用工具重新生成。
*/

@Table(name = "eam_asset")
public class Asset extends Entity {

	private static final long serialVersionUID = 1L;

	public static final DBTable TABLE =EAM_ASSET.$TABLE;
	
	/**
	 * 主键：主键
	*/
	@Id
	@ApiModelProperty(required = true,value="主键" , notes = "主键")
	private String id;
	
	/**
	 * 单据编号：单据编号
	*/
	@ApiModelProperty(required = false,value="单据编号" , notes = "单据编号")
	private String busiCode;
	
	/**
	 * 资产状态：资产状态
	*/
	@ApiModelProperty(required = false,value="资产状态" , notes = "资产状态")
	private String status;
	
	/**
	 * 资产编号：资产编号
	*/
	@ApiModelProperty(required = false,value="资产编号" , notes = "资产编号")
	private String assetCode;
	
	/**
	 * 分类ID：分类ID
	*/
	@ApiModelProperty(required = false,value="分类ID" , notes = "分类ID")
	private String categoryId;
	
	/**
	 * 标准型号ID：标准型号ID
	*/
	@ApiModelProperty(required = false,value="标准型号ID" , notes = "标准型号ID")
	private String goodsId;
	
	/**
	 * 标准型号资产名称：标准型号资产名称
	*/
	@ApiModelProperty(required = false,value="标准型号资产名称" , notes = "标准型号资产名称")
	private String name;
	
	/**
	 * 标准型号厂商：标准型号厂商
	*/
	@ApiModelProperty(required = false,value="标准型号厂商" , notes = "标准型号厂商")
	private String manufacturerId;
	
	/**
	 * 标准型号品牌：标准型号品牌
	*/
	@ApiModelProperty(required = false,value="标准型号品牌" , notes = "标准型号品牌")
	private String brandId;
	
	/**
	 * 标准型号规格型号：标准型号规格型号
	*/
	@ApiModelProperty(required = false,value="标准型号规格型号" , notes = "标准型号规格型号")
	private String model;
	
	/**
	 * 标准型号物品图片：标准型号物品图片
	*/
	@ApiModelProperty(required = false,value="标准型号物品图片" , notes = "标准型号物品图片")
	private String pictureId;
	
	/**
	 * 标准型号计量单位：标准型号计量单位
	*/
	@ApiModelProperty(required = false,value="标准型号计量单位" , notes = "标准型号计量单位")
	private String unit;
	
	/**
	 * 标准型号单价：标准型号单价
	*/
	@ApiModelProperty(required = false,value="标准型号单价" , notes = "标准型号单价")
	private BigDecimal unitPrice;
	
	/**
	 * 资产RFID：资产RFID
	*/
	@ApiModelProperty(required = false,value="资产RFID" , notes = "资产RFID")
	private String rfid;
	
	/**
	 * 资产数量：资产数量
	*/
	@ApiModelProperty(required = false,value="资产数量" , notes = "资产数量")
	private BigDecimal assetNumber;
	
	/**
	 * 批次号如果来自库存建议填写库存单据：批次号如果来自库存建议填写库存单据
	*/
	@ApiModelProperty(required = false,value="批次号如果来自库存建议填写库存单据" , notes = "批次号如果来自库存建议填写库存单据")
	private String batchNumber;
	
	/**
	 * 资产序列号：资产序列号
	*/
	@ApiModelProperty(required = false,value="资产序列号" , notes = "资产序列号")
	private String sn;
	
	/**
	 * 来源：来源
	*/
	@ApiModelProperty(required = false,value="来源" , notes = "来源")
	private String sourceId;
	
	/**
	 * 来源详情：来源详情
	*/
	@ApiModelProperty(required = false,value="来源详情" , notes = "来源详情")
	private String sourceDetail;
	
	/**
	 * 存放区域：存放区域
	*/
	@ApiModelProperty(required = false,value="存放区域" , notes = "存放区域")
	private String areaId;
	
	/**
	 * 存放地点：存放地点
	*/
	@ApiModelProperty(required = false,value="存放地点" , notes = "存放地点")
	private String placeDetail;
	
	/**
	 * 使用人员：使用人员
	*/
	@ApiModelProperty(required = false,value="使用人员" , notes = "使用人员")
	private String userId;
	
	/**
	 * 生产日期：生产日期
	*/
	@ApiModelProperty(required = false,value="生产日期" , notes = "生产日期")
	private Date productionDate;
	
	/**
	 * 入库时间：入库时间
	*/
	@ApiModelProperty(required = false,value="入库时间" , notes = "入库时间")
	private Date storageTime;
	
	/**
	 * 是否显示(显示:1：不显示:0,报废后不显示)
	*/
	@ApiModelProperty(required = false,value="是否显示(显示:1" , notes = "不显示:0,报废后不显示)")
	private String display;
	
	/**
	 * 备注：备注
	*/
	@ApiModelProperty(required = false,value="备注" , notes = "备注")
	private String assetsNotes;
	
	/**
	 * 创建人ID：创建人ID
	*/
	@ApiModelProperty(required = false,value="创建人ID" , notes = "创建人ID")
	private String createBy;
	
	/**
	 * 创建时间：创建时间
	*/
	@ApiModelProperty(required = false,value="创建时间" , notes = "创建时间")
	private Date createTime;
	
	/**
	 * 修改人ID：修改人ID
	*/
	@ApiModelProperty(required = false,value="修改人ID" , notes = "修改人ID")
	private String updateBy;
	
	/**
	 * 修改时间：修改时间
	*/
	@ApiModelProperty(required = false,value="修改时间" , notes = "修改时间")
	private Date updateTime;
	
	/**
	 * 是否已删除：是否已删除
	*/
	@ApiModelProperty(required = true,value="是否已删除" , notes = "是否已删除")
	private Integer deleted;
	
	/**
	 * 删除人ID：删除人ID
	*/
	@ApiModelProperty(required = false,value="删除人ID" , notes = "删除人ID")
	private String deleteBy;
	
	/**
	 * 删除时间：删除时间
	*/
	@ApiModelProperty(required = false,value="删除时间" , notes = "删除时间")
	private Date deleteTime;
	
	/**
	 * version：version
	*/
	@ApiModelProperty(required = true,value="version" , notes = "version")
	private Integer version;
	
	/**
	 * 获得 主键<br>
	 * 主键
	 * @return 主键
	*/
	public String getId() {
		return id;
	}
	
	/**
	 * 设置 主键
	 * @param id 主键
	 * @return 当前对象
	*/
	public Asset setId(String id) {
		this.id=id;
		return this;
	}
	
	/**
	 * 获得 单据编号<br>
	 * 单据编号
	 * @return 单据编号
	*/
	public String getBusiCode() {
		return busiCode;
	}
	
	/**
	 * 设置 单据编号
	 * @param busiCode 单据编号
	 * @return 当前对象
	*/
	public Asset setBusiCode(String busiCode) {
		this.busiCode=busiCode;
		return this;
	}
	
	/**
	 * 获得 资产状态<br>
	 * 资产状态
	 * @return 资产状态
	*/
	public String getStatus() {
		return status;
	}
	
	/**
	 * 设置 资产状态
	 * @param status 资产状态
	 * @return 当前对象
	*/
	public Asset setStatus(String status) {
		this.status=status;
		return this;
	}
	
	/**
	 * 获得 资产编号<br>
	 * 资产编号
	 * @return 资产编号
	*/
	public String getAssetCode() {
		return assetCode;
	}
	
	/**
	 * 设置 资产编号
	 * @param assetCode 资产编号
	 * @return 当前对象
	*/
	public Asset setAssetCode(String assetCode) {
		this.assetCode=assetCode;
		return this;
	}
	
	/**
	 * 获得 分类ID<br>
	 * 分类ID
	 * @return 分类ID
	*/
	public String getCategoryId() {
		return categoryId;
	}
	
	/**
	 * 设置 分类ID
	 * @param categoryId 分类ID
	 * @return 当前对象
	*/
	public Asset setCategoryId(String categoryId) {
		this.categoryId=categoryId;
		return this;
	}
	
	/**
	 * 获得 标准型号ID<br>
	 * 标准型号ID
	 * @return 标准型号ID
	*/
	public String getGoodsId() {
		return goodsId;
	}
	
	/**
	 * 设置 标准型号ID
	 * @param goodsId 标准型号ID
	 * @return 当前对象
	*/
	public Asset setGoodsId(String goodsId) {
		this.goodsId=goodsId;
		return this;
	}
	
	/**
	 * 获得 标准型号资产名称<br>
	 * 标准型号资产名称
	 * @return 标准型号资产名称
	*/
	public String getName() {
		return name;
	}
	
	/**
	 * 设置 标准型号资产名称
	 * @param name 标准型号资产名称
	 * @return 当前对象
	*/
	public Asset setName(String name) {
		this.name=name;
		return this;
	}
	
	/**
	 * 获得 标准型号厂商<br>
	 * 标准型号厂商
	 * @return 标准型号厂商
	*/
	public String getManufacturerId() {
		return manufacturerId;
	}
	
	/**
	 * 设置 标准型号厂商
	 * @param manufacturerId 标准型号厂商
	 * @return 当前对象
	*/
	public Asset setManufacturerId(String manufacturerId) {
		this.manufacturerId=manufacturerId;
		return this;
	}
	
	/**
	 * 获得 标准型号品牌<br>
	 * 标准型号品牌
	 * @return 标准型号品牌
	*/
	public String getBrandId() {
		return brandId;
	}
	
	/**
	 * 设置 标准型号品牌
	 * @param brandId 标准型号品牌
	 * @return 当前对象
	*/
	public Asset setBrandId(String brandId) {
		this.brandId=brandId;
		return this;
	}
	
	/**
	 * 获得 标准型号规格型号<br>
	 * 标准型号规格型号
	 * @return 标准型号规格型号
	*/
	public String getModel() {
		return model;
	}
	
	/**
	 * 设置 标准型号规格型号
	 * @param model 标准型号规格型号
	 * @return 当前对象
	*/
	public Asset setModel(String model) {
		this.model=model;
		return this;
	}
	
	/**
	 * 获得 标准型号物品图片<br>
	 * 标准型号物品图片
	 * @return 标准型号物品图片
	*/
	public String getPictureId() {
		return pictureId;
	}
	
	/**
	 * 设置 标准型号物品图片
	 * @param pictureId 标准型号物品图片
	 * @return 当前对象
	*/
	public Asset setPictureId(String pictureId) {
		this.pictureId=pictureId;
		return this;
	}
	
	/**
	 * 获得 标准型号计量单位<br>
	 * 标准型号计量单位
	 * @return 标准型号计量单位
	*/
	public String getUnit() {
		return unit;
	}
	
	/**
	 * 设置 标准型号计量单位
	 * @param unit 标准型号计量单位
	 * @return 当前对象
	*/
	public Asset setUnit(String unit) {
		this.unit=unit;
		return this;
	}
	
	/**
	 * 获得 标准型号单价<br>
	 * 标准型号单价
	 * @return 标准型号单价
	*/
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * 设置 标准型号单价
	 * @param unitPrice 标准型号单价
	 * @return 当前对象
	*/
	public Asset setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice=unitPrice;
		return this;
	}
	
	/**
	 * 获得 资产RFID<br>
	 * 资产RFID
	 * @return 资产RFID
	*/
	public String getRfid() {
		return rfid;
	}
	
	/**
	 * 设置 资产RFID
	 * @param rfid 资产RFID
	 * @return 当前对象
	*/
	public Asset setRfid(String rfid) {
		this.rfid=rfid;
		return this;
	}
	
	/**
	 * 获得 资产数量<br>
	 * 资产数量
	 * @return 资产数量
	*/
	public BigDecimal getAssetNumber() {
		return assetNumber;
	}
	
	/**
	 * 设置 资产数量
	 * @param assetNumber 资产数量
	 * @return 当前对象
	*/
	public Asset setAssetNumber(BigDecimal assetNumber) {
		this.assetNumber=assetNumber;
		return this;
	}
	
	/**
	 * 获得 批次号如果来自库存建议填写库存单据<br>
	 * 批次号如果来自库存建议填写库存单据
	 * @return 批次号如果来自库存建议填写库存单据
	*/
	public String getBatchNumber() {
		return batchNumber;
	}
	
	/**
	 * 设置 批次号如果来自库存建议填写库存单据
	 * @param batchNumber 批次号如果来自库存建议填写库存单据
	 * @return 当前对象
	*/
	public Asset setBatchNumber(String batchNumber) {
		this.batchNumber=batchNumber;
		return this;
	}
	
	/**
	 * 获得 资产序列号<br>
	 * 资产序列号
	 * @return 资产序列号
	*/
	public String getSn() {
		return sn;
	}
	
	/**
	 * 设置 资产序列号
	 * @param sn 资产序列号
	 * @return 当前对象
	*/
	public Asset setSn(String sn) {
		this.sn=sn;
		return this;
	}
	
	/**
	 * 获得 来源<br>
	 * 来源
	 * @return 来源
	*/
	public String getSourceId() {
		return sourceId;
	}
	
	/**
	 * 设置 来源
	 * @param sourceId 来源
	 * @return 当前对象
	*/
	public Asset setSourceId(String sourceId) {
		this.sourceId=sourceId;
		return this;
	}
	
	/**
	 * 获得 来源详情<br>
	 * 来源详情
	 * @return 来源详情
	*/
	public String getSourceDetail() {
		return sourceDetail;
	}
	
	/**
	 * 设置 来源详情
	 * @param sourceDetail 来源详情
	 * @return 当前对象
	*/
	public Asset setSourceDetail(String sourceDetail) {
		this.sourceDetail=sourceDetail;
		return this;
	}
	
	/**
	 * 获得 存放区域<br>
	 * 存放区域
	 * @return 存放区域
	*/
	public String getAreaId() {
		return areaId;
	}
	
	/**
	 * 设置 存放区域
	 * @param areaId 存放区域
	 * @return 当前对象
	*/
	public Asset setAreaId(String areaId) {
		this.areaId=areaId;
		return this;
	}
	
	/**
	 * 获得 存放地点<br>
	 * 存放地点
	 * @return 存放地点
	*/
	public String getPlaceDetail() {
		return placeDetail;
	}
	
	/**
	 * 设置 存放地点
	 * @param placeDetail 存放地点
	 * @return 当前对象
	*/
	public Asset setPlaceDetail(String placeDetail) {
		this.placeDetail=placeDetail;
		return this;
	}
	
	/**
	 * 获得 使用人员<br>
	 * 使用人员
	 * @return 使用人员
	*/
	public String getUserId() {
		return userId;
	}
	
	/**
	 * 设置 使用人员
	 * @param userId 使用人员
	 * @return 当前对象
	*/
	public Asset setUserId(String userId) {
		this.userId=userId;
		return this;
	}
	
	/**
	 * 获得 生产日期<br>
	 * 生产日期
	 * @return 生产日期
	*/
	public Date getProductionDate() {
		return productionDate;
	}
	
	/**
	 * 设置 生产日期
	 * @param productionDate 生产日期
	 * @return 当前对象
	*/
	public Asset setProductionDate(Date productionDate) {
		this.productionDate=productionDate;
		return this;
	}
	
	/**
	 * 获得 入库时间<br>
	 * 入库时间
	 * @return 入库时间
	*/
	public Date getStorageTime() {
		return storageTime;
	}
	
	/**
	 * 设置 入库时间
	 * @param storageTime 入库时间
	 * @return 当前对象
	*/
	public Asset setStorageTime(Date storageTime) {
		this.storageTime=storageTime;
		return this;
	}
	
	/**
	 * 获得 是否显示(显示:1<br>
	 * 不显示:0,报废后不显示)
	 * @return 是否显示(显示:1
	*/
	public String getDisplay() {
		return display;
	}
	
	/**
	 * 设置 是否显示(显示:1
	 * @param display 是否显示(显示:1
	 * @return 当前对象
	*/
	public Asset setDisplay(String display) {
		this.display=display;
		return this;
	}
	
	/**
	 * 获得 备注<br>
	 * 备注
	 * @return 备注
	*/
	public String getAssetsNotes() {
		return assetsNotes;
	}
	
	/**
	 * 设置 备注
	 * @param assetsNotes 备注
	 * @return 当前对象
	*/
	public Asset setAssetsNotes(String assetsNotes) {
		this.assetsNotes=assetsNotes;
		return this;
	}
	
	/**
	 * 获得 创建人ID<br>
	 * 创建人ID
	 * @return 创建人ID
	*/
	public String getCreateBy() {
		return createBy;
	}
	
	/**
	 * 设置 创建人ID
	 * @param createBy 创建人ID
	 * @return 当前对象
	*/
	public Asset setCreateBy(String createBy) {
		this.createBy=createBy;
		return this;
	}
	
	/**
	 * 获得 创建时间<br>
	 * 创建时间
	 * @return 创建时间
	*/
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 创建时间
	 * @param createTime 创建时间
	 * @return 当前对象
	*/
	public Asset setCreateTime(Date createTime) {
		this.createTime=createTime;
		return this;
	}
	
	/**
	 * 获得 修改人ID<br>
	 * 修改人ID
	 * @return 修改人ID
	*/
	public String getUpdateBy() {
		return updateBy;
	}
	
	/**
	 * 设置 修改人ID
	 * @param updateBy 修改人ID
	 * @return 当前对象
	*/
	public Asset setUpdateBy(String updateBy) {
		this.updateBy=updateBy;
		return this;
	}
	
	/**
	 * 获得 修改时间<br>
	 * 修改时间
	 * @return 修改时间
	*/
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * 设置 修改时间
	 * @param updateTime 修改时间
	 * @return 当前对象
	*/
	public Asset setUpdateTime(Date updateTime) {
		this.updateTime=updateTime;
		return this;
	}
	
	/**
	 * 获得 是否已删除<br>
	 * 是否已删除
	 * @return 是否已删除
	*/
	public Integer getDeleted() {
		return deleted;
	}
	
	/**
	 * 设置 是否已删除
	 * @param deleted 是否已删除
	 * @return 当前对象
	*/
	public Asset setDeleted(Integer deleted) {
		this.deleted=deleted;
		return this;
	}
	
	/**
	 * 获得 删除人ID<br>
	 * 删除人ID
	 * @return 删除人ID
	*/
	public String getDeleteBy() {
		return deleteBy;
	}
	
	/**
	 * 设置 删除人ID
	 * @param deleteBy 删除人ID
	 * @return 当前对象
	*/
	public Asset setDeleteBy(String deleteBy) {
		this.deleteBy=deleteBy;
		return this;
	}
	
	/**
	 * 获得 删除时间<br>
	 * 删除时间
	 * @return 删除时间
	*/
	public Date getDeleteTime() {
		return deleteTime;
	}
	
	/**
	 * 设置 删除时间
	 * @param deleteTime 删除时间
	 * @return 当前对象
	*/
	public Asset setDeleteTime(Date deleteTime) {
		this.deleteTime=deleteTime;
		return this;
	}
	
	/**
	 * 获得 version<br>
	 * version
	 * @return version
	*/
	public Integer getVersion() {
		return version;
	}
	
	/**
	 * 设置 version
	 * @param version version
	 * @return 当前对象
	*/
	public Asset setVersion(Integer version) {
		this.version=version;
		return this;
	}

	/**
	 * 将自己转换成指定类型的PO
	 * @param poType  PO类型
	 * @return Asset , 转换好的 Asset 对象
	*/
	@Transient
	public <T extends Entity> T toPO(Class<T> poType) {
		return EntityContext.create(poType, this);
	}

	/**
	 * 将自己转换成任意指定类型
	 * @param pojoType  Pojo类型
	 * @return Asset , 转换好的 PoJo 对象
	*/
	@Transient
	public <T> T toPojo(Class<T> pojoType) {
		if(Entity.class.isAssignableFrom(pojoType)) {
			return (T)this.toPO((Class<Entity>)pojoType);
		}
		try {
			T pojo=pojoType.newInstance();
			EntityContext.copyProperties(pojo, this);
			return pojo;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Map 转换成 Asset
	 * @param assetMap 包含实体信息的 Map 对象
	 * @return Asset , 转换好的的 Asset 对象
	*/
	@Transient
	public static Asset createFrom(Map<String,Object> assetMap) {
		if(assetMap==null) return null;
		Asset po = EntityContext.create(Asset.class, assetMap);
		return po;
	}

	/**
	 * 将 Pojo 转换成 Asset
	 * @param pojo 包含实体信息的 Pojo 对象
	 * @return Asset , 转换好的的 Asset 对象
	*/
	@Transient
	public static Asset createFrom(Object pojo) {
		if(pojo==null) return null;
		Asset po = EntityContext.create(Asset.class,pojo);
		return po;
	}

	/**
	 * 创建一个 Asset，等同于 new
	 * @return Asset 对象
	*/
	@Transient
	public static Asset create() {
		return EntityContext.create(Asset.class);
	}
}