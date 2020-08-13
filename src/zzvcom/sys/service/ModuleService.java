package zzvcom.sys.service;

import java.util.List;


import zzvcom.sys.pojo.AuxsysModule;

public interface ModuleService
{

    /**
     * @description：根据条件获取用户总数
     * @param role  用户角色参数对象
     * @return 
     */
    public int getModuleCount(AuxsysModule amodule);
    
	/**
	 * @description：根据用户名密码查询用户列表
	 * @time： 2015-11-5
	 * @param role 用户对象
	 * @param start 分页起点
	 * @param size  每页长度
	 * @return  List<AuxsysRole>
	 */
	public List<AuxsysModule> queryByAuxsysModules(AuxsysModule amodule,Integer start,Integer size);
	/**
	 * 保存系统用户
	 * @param role
	 * @return -1 菜单名冲突 1 成功
	 */
    public int add(AuxsysModule amodule);
    

    /**
     * @description：根据用户Id获取对应的用户
     * @time： 2015-11-5
     * @author：donghaoyu
     * @param role 用户角色对象
     * @return 
     */
    public AuxsysModule getModuleById(String id);
    /**
     * 根据id串批量删除用户
     * @param ids
     */
    public void deleteModuleByIds(String ids);

    /**
     * 更新对应id的系统用户
     * @param role
     * @return -1 菜单名冲突 1 成功
     */
    public int update(AuxsysModule amodule);
}
