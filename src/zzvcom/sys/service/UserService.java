package zzvcom.sys.service;

import java.util.List;

import zzvcom.sys.pojo.AuxsysRole;

public interface UserService
{
    /**
     * @description：根据用户名密码查询用户列表
     * @time： 2015-11-5
     * @author：donghaoyu
     * @param role 用户对象
     * @return 
     */
    public List<AuxsysRole> queryByAuxsysRoles(AuxsysRole role);

}
