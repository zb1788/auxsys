package zzvcom.sys.service.impl;

import java.util.List;

import zzvcom.sys.dao.AuxsysRoleDao;
import zzvcom.sys.pojo.AuxsysRole;
import zzvcom.sys.service.RoleService;

public class RoleServiceImpl implements RoleService
{

	private AuxsysRoleDao auxsysRoleDao;

    /**
     * @description：根据条件获取用户总数
     * @param role  用户角色参数对象
     * @return 
     */
    public int getRoleCount(AuxsysRole role){
    	return auxsysRoleDao.getRoleCount(role);
    }
	/**
	 * @description：根据用户名密码查询用户列表
	 * @param role 用户对象
	 * @param start 分页起点
	 * @param size  每页长度
	 * @return  List<AuxsysRole>
	 */
	public List<AuxsysRole> queryByAuxsysRoles(AuxsysRole role,Integer start,Integer size){
		return auxsysRoleDao.getRoleList(role,start,size);
	}
	/**
	 * 保存系统用户
	 * @param role
	 * @return -1用户名重复,1成功
	 */
    public int addRole(AuxsysRole role) {
    	AuxsysRole trole = new AuxsysRole();
    	if(auxsysRoleDao.getRoleCountByUserName(role.getUsername())>0){
    		return -1;
    	}
        auxsysRoleDao.save(role);
        return 1;
    }

    /**
     * @description：根据用户Id获取对应的用户
     * @param role 用户角色对象
     * @return 
     */
    public AuxsysRole getRoleById(String id){
        return auxsysRoleDao.getRoleById(id);
    }
    
    /**
     * @description：获取根据用户名和密码获取对应的用户
     * @param role 用户角色对象
     * @return 
     */
    public List getLoginRole(AuxsysRole role){
    	return auxsysRoleDao.getLoginRole(role);
    }
    
    /**
     * 根据id串批量删除用户
     * @param ids
     */
    public void deleteRoleByIds(String ids){
    	auxsysRoleDao.deleteByIds(ids);
    }
    

    /**
     * 更新对应id的系统用户
     * @param role
     */
    public void update(AuxsysRole role){
    	auxsysRoleDao.update(role);
    }
    
    public AuxsysRoleDao getAuxsysRoleDao() {
        return auxsysRoleDao;
    }
    public void setAuxsysRoleDao(AuxsysRoleDao auxsysRoleDao) {
        this.auxsysRoleDao = auxsysRoleDao;
    }
    /**
     * 修改密码
     * @param
     * @param
     * @return 1成功,-1用户或原密码错误,-2用户数量错误
     */
	public int editPwd(AuxsysRole role, String npassword) {
		List<AuxsysRole> rolelist = auxsysRoleDao.getLoginRole(role);
		if(rolelist.size()>0){
			if(rolelist.size()==1){
				AuxsysRole trole= rolelist.get(0);
				trole.setPassword(npassword);
				auxsysRoleDao.update(trole);
				return 1;
			}
			return -2;
		}
		return -1;
	}
	/**
	 * 批量重置密码
	 * @param id串
	 */
	public void resetPwd(String ids) {
		// TODO Auto-generated method stub
		auxsysRoleDao.restPwd(ids);
	}
}
