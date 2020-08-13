package zzvcom.sys.service;

import java.util.List;

import zzvcom.sys.pojo.AuxsysRole;

public interface RoleService
{

    /**
     * @description：根据条件获取用户总数
     * @param role  用户角色参数对象
     * @return 
     */
    public int getRoleCount(AuxsysRole auxsysRole);
    
	/**
	 * @description：根据用户名密码查询用户列表
	 * @time： 2015-11-5
	 * @param role 用户对象
	 * @param start 分页起点
	 * @param size  每页长度
	 * @return  List<AuxsysRole>
	 */
	public List<AuxsysRole> queryByAuxsysRoles(AuxsysRole role,Integer start,Integer size);
	/**
	 * 保存系统用户
	 * @param role
	 * @return -1用户名重复,1成功
	 */
    public int addRole(AuxsysRole role);
    

    /**
     * @description：根据用户Id获取对应的用户
     * @time： 2015-11-5
     * @author：donghaoyu
     * @param role 用户角色对象
     * @return 
     */
    public AuxsysRole getRoleById(String id);
    /**
     * @description：获取根据用户名和密码获取对应的用户
     * @time： 2015-11-5
     * @author：donghaoyu
     * @param role 用户角色对象
     * @return 
     */
    public List getLoginRole(AuxsysRole role);
    /**
     * 根据id串批量删除用户
     * @param ids
     */
    public void deleteRoleByIds(String ids);

    /**
     * 更新对应id的系统用户
     * @param role
     */
    public void update(AuxsysRole role);

    /**
     * 检查对应系统用户的密码,如果正确则更新密码
     * @param role      包含用户账号,密码的用户对象
     * @param npassword 新密码
     * @return 1成功,-1用户或原密码错误,-2用户数量错误
     */
    public int  editPwd(AuxsysRole role,String npassword);
    /**
     * 批量重置密码
     * @param ids
     */
    public void resetPwd(String ids);
}
