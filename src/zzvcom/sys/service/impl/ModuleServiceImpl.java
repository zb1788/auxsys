package zzvcom.sys.service.impl;

import java.util.List;

import zzvcom.sys.dao.AuxsysModuleDao;
import zzvcom.sys.pojo.AuxsysModule;
import zzvcom.sys.service.ModuleService;

public class ModuleServiceImpl implements ModuleService {
	private AuxsysModuleDao moduleDao;
	/**
	 * 
	 * @return 1成功，-1名称冲突
	 */
	public int add(AuxsysModule amodule) {
		// TODO Auto-generated method stub
		AuxsysModule tmodule= new AuxsysModule();
		tmodule.setName(amodule.getName());
		List rlist = moduleDao.queryByAuxsysModules(tmodule, 0, 2);
		if(rlist.size()>0){
			return -1;
		}
		moduleDao.save(amodule);
		return 1;
	}

	public void deleteModuleByIds(String ids) {
		// TODO Auto-generated method stub
		moduleDao.deleteByIds(ids);
	}

	public AuxsysModule getModuleById(String id) {
		// TODO Auto-generated method stub
		return moduleDao.getModuleById(id);
	}

	public int getModuleCount(AuxsysModule amodule) {
		// TODO Auto-generated method stub
		return moduleDao.getModuleCount(amodule);
	}

	public List<AuxsysModule> queryByAuxsysModules(AuxsysModule amodule,
			Integer start, Integer size) {
		// TODO Auto-generated method stub
		return moduleDao.queryByAuxsysModules(amodule,start,size);
	}

	public int update(AuxsysModule amodule) {
		int rcount = moduleDao.getModuleCountByName(amodule.getName(), amodule.getId());
		if(rcount>0){
			return -1;
		}
		moduleDao.update(amodule);
		return 1;
	}

	public AuxsysModuleDao getModuleDao() {
		return moduleDao;
	}

	public void setModuleDao(AuxsysModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

}
