package com.dt.eam.domain.ops;

import com.github.foxnic.dao.entity.Entity;
import javax.persistence.Table;
import com.github.foxnic.sql.meta.DBTable;
import com.dt.eam.constants.db.EAMTables.OPS_HOST;
import javax.persistence.Id;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Transient;
import java.util.Map;
import com.github.foxnic.dao.entity.EntityContext;



/**
 * null
 * @author 李方捷 , leefangjie@qq.com
 * @since 2021-07-26 13:38:43
 * @sign 1D4EF255FD2B9E973CF28F1E981B0FF6
 * 此文件由工具自动生成，请勿修改。若表结构或配置发生变动，请使用工具重新生成。
*/

@Table(name = "ops_host")
public class Host extends Entity {

	private static final long serialVersionUID = 1L;

	public static final DBTable TABLE =OPS_HOST.$TABLE;
	
	/**
	 * 主键：主键
	*/
	@Id
	@ApiModelProperty(required = true,value="主键" , notes = "主键")
	private String id;
	
	/**
	 * 主机类型：主机类型
	*/
	@ApiModelProperty(required = false,value="主机类型" , notes = "主机类型")
	private String hostType;
	
	/**
	 * 名称：名称
	*/
	@ApiModelProperty(required = false,value="名称" , notes = "名称")
	private String hostName;
	
	/**
	 * IP：IP
	*/
	@ApiModelProperty(required = true,value="IP" , notes = "IP")
	private String hostIp;
	
	/**
	 * CPU：CPU
	*/
	@ApiModelProperty(required = false,value="CPU" , notes = "CPU")
	private String hostConfCpu;
	
	/**
	 * memory：memory
	*/
	@ApiModelProperty(required = false,value="memory" , notes = "memory")
	private String hostConfMemory;
	
	/**
	 * 虚拟IP：虚拟IP
	*/
	@ApiModelProperty(required = false,value="虚拟IP" , notes = "虚拟IP")
	private String vip;
	
	/**
	 * 运行环境:prod：uat,tst
	*/
	@ApiModelProperty(required = false,value="运行环境:prod" , notes = "uat,tst")
	private String environment;
	
	/**
	 * online：offline
	*/
	@ApiModelProperty(required = false,value="online" , notes = "offline")
	private String status;
	
	/**
	 * 监控状态:valid：invalid,unknow
	*/
	@ApiModelProperty(required = false,value="监控状态:valid" , notes = "invalid,unknow")
	private String monitorStatus;
	
	/**
	 * 上线时间：上线时间
	*/
	@ApiModelProperty(required = false,value="上线时间" , notes = "上线时间")
	private Date onlineTime;
	
	/**
	 * 下线时间：下线时间
	*/
	@ApiModelProperty(required = false,value="下线时间" , notes = "下线时间")
	private Date offlineTime;
	
	/**
	 * 操作系统：不使用
	*/
	@ApiModelProperty(required = false,value="操作系统" , notes = "不使用")
	private String os;
	
	/**
	 * 数据库：不使用
	*/
	@ApiModelProperty(required = false,value="数据库" , notes = "不使用")
	private String db;
	
	/**
	 * 中间件：不使用
	*/
	@ApiModelProperty(required = false,value="中间件" , notes = "不使用")
	private String middleware;
	
	/**
	 * 标签：标签
	*/
	@ApiModelProperty(required = false,value="标签" , notes = "标签")
	private String labels;
	
	/**
	 * 是否归档：1归档，0没有归档
	*/
	@ApiModelProperty(required = false,value="是否归档" , notes = "1归档，0没有归档")
	private String arch;
	
	/**
	 * 系统管理员：系统管理员
	*/
	@ApiModelProperty(required = false,value="系统管理员" , notes = "系统管理员")
	private String userOsAdmin;
	
	/**
	 * 数据库管理员：数据库管理员
	*/
	@ApiModelProperty(required = false,value="数据库管理员" , notes = "数据库管理员")
	private String userDbAdmin;
	
	/**
	 * 数据库使用用户：数据库使用用户
	*/
	@ApiModelProperty(required = false,value="数据库使用用户" , notes = "数据库使用用户")
	private String userDbUsed;
	
	/**
	 * 应用使用用户：应用使用用户
	*/
	@ApiModelProperty(required = false,value="应用使用用户" , notes = "应用使用用户")
	private String userAppUsed;
	
	/**
	 * 运维操作用户：运维操作用户
	*/
	@ApiModelProperty(required = false,value="运维操作用户" , notes = "运维操作用户")
	private String userOpsOper;
	
	/**
	 * 其他用户：其他用户
	*/
	@ApiModelProperty(required = false,value="其他用户" , notes = "其他用户")
	private String userOther;
	
	/**
	 * 改密策略：改密策略
	*/
	@ApiModelProperty(required = false,value="改密策略" , notes = "改密策略")
	private String passwordStrategy;
	
	/**
	 * 备注：备注
	*/
	@ApiModelProperty(required = false,value="备注" , notes = "备注")
	private String hostNotes;
	
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
	public Host setId(String id) {
		this.id=id;
		return this;
	}
	
	/**
	 * 获得 主机类型<br>
	 * 主机类型
	 * @return 主机类型
	*/
	public String getHostType() {
		return hostType;
	}
	
	/**
	 * 设置 主机类型
	 * @param hostType 主机类型
	 * @return 当前对象
	*/
	public Host setHostType(String hostType) {
		this.hostType=hostType;
		return this;
	}
	
	/**
	 * 获得 名称<br>
	 * 名称
	 * @return 名称
	*/
	public String getHostName() {
		return hostName;
	}
	
	/**
	 * 设置 名称
	 * @param hostName 名称
	 * @return 当前对象
	*/
	public Host setHostName(String hostName) {
		this.hostName=hostName;
		return this;
	}
	
	/**
	 * 获得 IP<br>
	 * IP
	 * @return IP
	*/
	public String getHostIp() {
		return hostIp;
	}
	
	/**
	 * 设置 IP
	 * @param hostIp IP
	 * @return 当前对象
	*/
	public Host setHostIp(String hostIp) {
		this.hostIp=hostIp;
		return this;
	}
	
	/**
	 * 获得 CPU<br>
	 * CPU
	 * @return CPU
	*/
	public String getHostConfCpu() {
		return hostConfCpu;
	}
	
	/**
	 * 设置 CPU
	 * @param hostConfCpu CPU
	 * @return 当前对象
	*/
	public Host setHostConfCpu(String hostConfCpu) {
		this.hostConfCpu=hostConfCpu;
		return this;
	}
	
	/**
	 * 获得 memory<br>
	 * memory
	 * @return memory
	*/
	public String getHostConfMemory() {
		return hostConfMemory;
	}
	
	/**
	 * 设置 memory
	 * @param hostConfMemory memory
	 * @return 当前对象
	*/
	public Host setHostConfMemory(String hostConfMemory) {
		this.hostConfMemory=hostConfMemory;
		return this;
	}
	
	/**
	 * 获得 虚拟IP<br>
	 * 虚拟IP
	 * @return 虚拟IP
	*/
	public String getVip() {
		return vip;
	}
	
	/**
	 * 设置 虚拟IP
	 * @param vip 虚拟IP
	 * @return 当前对象
	*/
	public Host setVip(String vip) {
		this.vip=vip;
		return this;
	}
	
	/**
	 * 获得 运行环境:prod<br>
	 * uat,tst
	 * @return 运行环境:prod
	*/
	public String getEnvironment() {
		return environment;
	}
	
	/**
	 * 设置 运行环境:prod
	 * @param environment 运行环境:prod
	 * @return 当前对象
	*/
	public Host setEnvironment(String environment) {
		this.environment=environment;
		return this;
	}
	
	/**
	 * 获得 online<br>
	 * offline
	 * @return online
	*/
	public String getStatus() {
		return status;
	}
	
	/**
	 * 设置 online
	 * @param status online
	 * @return 当前对象
	*/
	public Host setStatus(String status) {
		this.status=status;
		return this;
	}
	
	/**
	 * 获得 监控状态:valid<br>
	 * invalid,unknow
	 * @return 监控状态:valid
	*/
	public String getMonitorStatus() {
		return monitorStatus;
	}
	
	/**
	 * 设置 监控状态:valid
	 * @param monitorStatus 监控状态:valid
	 * @return 当前对象
	*/
	public Host setMonitorStatus(String monitorStatus) {
		this.monitorStatus=monitorStatus;
		return this;
	}
	
	/**
	 * 获得 上线时间<br>
	 * 上线时间
	 * @return 上线时间
	*/
	public Date getOnlineTime() {
		return onlineTime;
	}
	
	/**
	 * 设置 上线时间
	 * @param onlineTime 上线时间
	 * @return 当前对象
	*/
	public Host setOnlineTime(Date onlineTime) {
		this.onlineTime=onlineTime;
		return this;
	}
	
	/**
	 * 获得 下线时间<br>
	 * 下线时间
	 * @return 下线时间
	*/
	public Date getOfflineTime() {
		return offlineTime;
	}
	
	/**
	 * 设置 下线时间
	 * @param offlineTime 下线时间
	 * @return 当前对象
	*/
	public Host setOfflineTime(Date offlineTime) {
		this.offlineTime=offlineTime;
		return this;
	}
	
	/**
	 * 获得 操作系统<br>
	 * 不使用
	 * @return 操作系统
	*/
	public String getOs() {
		return os;
	}
	
	/**
	 * 设置 操作系统
	 * @param os 操作系统
	 * @return 当前对象
	*/
	public Host setOs(String os) {
		this.os=os;
		return this;
	}
	
	/**
	 * 获得 数据库<br>
	 * 不使用
	 * @return 数据库
	*/
	public String getDb() {
		return db;
	}
	
	/**
	 * 设置 数据库
	 * @param db 数据库
	 * @return 当前对象
	*/
	public Host setDb(String db) {
		this.db=db;
		return this;
	}
	
	/**
	 * 获得 中间件<br>
	 * 不使用
	 * @return 中间件
	*/
	public String getMiddleware() {
		return middleware;
	}
	
	/**
	 * 设置 中间件
	 * @param middleware 中间件
	 * @return 当前对象
	*/
	public Host setMiddleware(String middleware) {
		this.middleware=middleware;
		return this;
	}
	
	/**
	 * 获得 标签<br>
	 * 标签
	 * @return 标签
	*/
	public String getLabels() {
		return labels;
	}
	
	/**
	 * 设置 标签
	 * @param labels 标签
	 * @return 当前对象
	*/
	public Host setLabels(String labels) {
		this.labels=labels;
		return this;
	}
	
	/**
	 * 获得 是否归档<br>
	 * 1归档，0没有归档
	 * @return 是否归档
	*/
	public String getArch() {
		return arch;
	}
	
	/**
	 * 设置 是否归档
	 * @param arch 是否归档
	 * @return 当前对象
	*/
	public Host setArch(String arch) {
		this.arch=arch;
		return this;
	}
	
	/**
	 * 获得 系统管理员<br>
	 * 系统管理员
	 * @return 系统管理员
	*/
	public String getUserOsAdmin() {
		return userOsAdmin;
	}
	
	/**
	 * 设置 系统管理员
	 * @param userOsAdmin 系统管理员
	 * @return 当前对象
	*/
	public Host setUserOsAdmin(String userOsAdmin) {
		this.userOsAdmin=userOsAdmin;
		return this;
	}
	
	/**
	 * 获得 数据库管理员<br>
	 * 数据库管理员
	 * @return 数据库管理员
	*/
	public String getUserDbAdmin() {
		return userDbAdmin;
	}
	
	/**
	 * 设置 数据库管理员
	 * @param userDbAdmin 数据库管理员
	 * @return 当前对象
	*/
	public Host setUserDbAdmin(String userDbAdmin) {
		this.userDbAdmin=userDbAdmin;
		return this;
	}
	
	/**
	 * 获得 数据库使用用户<br>
	 * 数据库使用用户
	 * @return 数据库使用用户
	*/
	public String getUserDbUsed() {
		return userDbUsed;
	}
	
	/**
	 * 设置 数据库使用用户
	 * @param userDbUsed 数据库使用用户
	 * @return 当前对象
	*/
	public Host setUserDbUsed(String userDbUsed) {
		this.userDbUsed=userDbUsed;
		return this;
	}
	
	/**
	 * 获得 应用使用用户<br>
	 * 应用使用用户
	 * @return 应用使用用户
	*/
	public String getUserAppUsed() {
		return userAppUsed;
	}
	
	/**
	 * 设置 应用使用用户
	 * @param userAppUsed 应用使用用户
	 * @return 当前对象
	*/
	public Host setUserAppUsed(String userAppUsed) {
		this.userAppUsed=userAppUsed;
		return this;
	}
	
	/**
	 * 获得 运维操作用户<br>
	 * 运维操作用户
	 * @return 运维操作用户
	*/
	public String getUserOpsOper() {
		return userOpsOper;
	}
	
	/**
	 * 设置 运维操作用户
	 * @param userOpsOper 运维操作用户
	 * @return 当前对象
	*/
	public Host setUserOpsOper(String userOpsOper) {
		this.userOpsOper=userOpsOper;
		return this;
	}
	
	/**
	 * 获得 其他用户<br>
	 * 其他用户
	 * @return 其他用户
	*/
	public String getUserOther() {
		return userOther;
	}
	
	/**
	 * 设置 其他用户
	 * @param userOther 其他用户
	 * @return 当前对象
	*/
	public Host setUserOther(String userOther) {
		this.userOther=userOther;
		return this;
	}
	
	/**
	 * 获得 改密策略<br>
	 * 改密策略
	 * @return 改密策略
	*/
	public String getPasswordStrategy() {
		return passwordStrategy;
	}
	
	/**
	 * 设置 改密策略
	 * @param passwordStrategy 改密策略
	 * @return 当前对象
	*/
	public Host setPasswordStrategy(String passwordStrategy) {
		this.passwordStrategy=passwordStrategy;
		return this;
	}
	
	/**
	 * 获得 备注<br>
	 * 备注
	 * @return 备注
	*/
	public String getHostNotes() {
		return hostNotes;
	}
	
	/**
	 * 设置 备注
	 * @param hostNotes 备注
	 * @return 当前对象
	*/
	public Host setHostNotes(String hostNotes) {
		this.hostNotes=hostNotes;
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
	public Host setCreateBy(String createBy) {
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
	public Host setCreateTime(Date createTime) {
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
	public Host setUpdateBy(String updateBy) {
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
	public Host setUpdateTime(Date updateTime) {
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
	public Host setDeleted(Integer deleted) {
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
	public Host setDeleteBy(String deleteBy) {
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
	public Host setDeleteTime(Date deleteTime) {
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
	public Host setVersion(Integer version) {
		this.version=version;
		return this;
	}

	/**
	 * 将自己转换成指定类型的PO
	 * @param poType  PO类型
	 * @return Host , 转换好的 Host 对象
	*/
	@Transient
	public <T extends Entity> T toPO(Class<T> poType) {
		return EntityContext.create(poType, this);
	}

	/**
	 * 将自己转换成任意指定类型
	 * @param pojoType  Pojo类型
	 * @return Host , 转换好的 PoJo 对象
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
	 * 将 Map 转换成 Host
	 * @param hostMap 包含实体信息的 Map 对象
	 * @return Host , 转换好的的 Host 对象
	*/
	@Transient
	public static Host createFrom(Map<String,Object> hostMap) {
		if(hostMap==null) return null;
		Host po = EntityContext.create(Host.class, hostMap);
		return po;
	}

	/**
	 * 将 Pojo 转换成 Host
	 * @param pojo 包含实体信息的 Pojo 对象
	 * @return Host , 转换好的的 Host 对象
	*/
	@Transient
	public static Host createFrom(Object pojo) {
		if(pojo==null) return null;
		Host po = EntityContext.create(Host.class,pojo);
		return po;
	}

	/**
	 * 创建一个 Host，等同于 new
	 * @return Host 对象
	*/
	@Transient
	public static Host create() {
		return EntityContext.create(Host.class);
	}
}