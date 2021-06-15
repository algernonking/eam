package com.dt.eam.eam.service.impl;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.foxnic.commons.busi.id.IDGenerator;
import com.github.foxnic.dao.data.PagedList;
import com.github.foxnic.dao.data.SaveMode;
import com.github.foxnic.dao.entity.SuperService;
import com.github.foxnic.dao.spec.DAO;
import com.github.foxnic.springboot.api.error.ErrorDesc;
import com.github.foxnic.springboot.mvc.Result;
import com.github.foxnic.sql.expr.ConditionExpr;
import com.github.foxnic.sql.meta.DBField;


import com.dt.eam.domain.eam.Maintainer;
import com.dt.eam.domain.eam.MaintainerVO;
import com.dt.eam.eam.service.IMaintainerService;
import org.github.foxnic.web.framework.dao.DBConfigs;
import java.util.Date;

/**
 * <p>
 * 维保厂商 服务实现
 * </p>
 * @author 金杰 , maillank@qq.com
 * @since 2021-06-12 14:21:18
*/


@Service("EamMaintainerService")
public class MaintainerServiceImpl extends SuperService<Maintainer> implements IMaintainerService {
	
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
	 * 插入实体
	 * @param role 实体数据
	 * @return 插入是否成功
	 * */
	@Override
	public boolean insert(Maintainer maintainer) {
		return super.insert(maintainer);
	}
	
	/**
	 * 批量插入实体，事务内
	 * @param roleList 实体数据清单
	 * @return 插入是否成功
	 * */
	@Override
	public boolean insertList(List<Maintainer> maintainerList) {
		return super.insertList(maintainerList);
	}
	
	
	/**
	 * 按主键删除 维保厂商
	 *
	 * @param id 主键
	 * @return 删除是否成功
	 */
	public boolean deleteByIdPhysical(String id) {
		Maintainer maintainer = new Maintainer();
		if(id==null) throw new IllegalArgumentException("id 不允许为 null ");
		maintainer.setId(id);
		return dao.deleteEntity(maintainer);
	}
	
	/**
	 * 按主键删除 维保厂商
	 *
	 * @param id 主键
	 * @return 删除是否成功
	 */
	public boolean deleteByIdLogical(String id) {
		Maintainer maintainer = new Maintainer();
		if(id==null) throw new IllegalArgumentException("id 不允许为 null 。");
		maintainer.setId(id);
		maintainer.setDeleted(dao.getDBTreaty().getTrueValue());
		maintainer.setDeleteBy((String)dao.getDBTreaty().getLoginUserId());
		maintainer.setDeleteTime(new Date());
		return dao.updateEntity(maintainer,SaveMode.NOT_NULL_FIELDS);
	}
	
	/**
	 * 更新实体
	 * @param role 数据对象
	 * @param mode 保存模式
	 * @return 保存是否成功
	 * */
	@Override
	public boolean update(Maintainer maintainer , SaveMode mode) {
		return super.update(maintainer , mode);
	}
	
	/**
	 * 更新实体集，事务内
	 * @param roleList 数据对象列表
	 * @param mode 保存模式
	 * @return 保存是否成功
	 * */
	@Override
	public boolean updateList(List<Maintainer> maintainerList , SaveMode mode) {
		return super.updateList(maintainerList , mode);
	}
	
	
	/**
	 * 按主键更新字段 维保厂商
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
	 * 按主键获取 维保厂商
	 *
	 * @param id 主键
	 * @return Maintainer 数据对象
	 */
	public Maintainer getById(String id) {
		Maintainer sample = new Maintainer();
		if(id==null) throw new IllegalArgumentException("id 不允许为 null ");
		sample.setId(id);
		return dao.queryEntity(sample);
	}
 
	/**
	 * 查询实体集合，默认情况下，字符串使用模糊匹配，非字符串使用精确匹配
	 * 
	 * @param sample  查询条件
	 * @return 查询结果
	 * */
	@Override
	public List<Maintainer> queryList(Maintainer sample) {
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
	public PagedList<Maintainer> queryPagedList(Maintainer sample, int pageSize, int pageIndex) {
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
	public PagedList<Maintainer> queryPagedList(Maintainer sample, ConditionExpr condition, int pageSize, int pageIndex) {
		return super.queryPagedList(sample, condition, pageSize, pageIndex);
	}
	
	/**
	 * 检查 角色 是否已经存在
	 *
	 * @param roleVO 数据对象
	 * @return 判断结果
	 */
	public Result<Maintainer> checkExists(Maintainer role) {
		//TDOD 此处添加判断段的代码
		//boolean exists=this.checkExists(role, SYS_ROLE.NAME);
		//return exists;
		return ErrorDesc.success();
	}

}