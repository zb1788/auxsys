package com.vcom.auxsys.service;

import java.util.List;

import com.vcom.auxsys.dao.WebAppDao;
import com.vcom.auxsys.entitys.pojo.WebApp;

/**
 * @description： 
 * @time： 2015-11-12
 * @author： donghaoyu
 */
public class WebAppService {
    
    private WebAppDao webAppDao;
    /**
     * @Description: 添加一条记录
     * @author 罗华森
     * @date 2015-11-19
     */
    public String add(WebApp wa){
    	return this.webAppDao.add(wa);
    }
    /**
     * @Description: 删除一条记录
     * @author 罗华森
     * @date 2015-11-19
     */
    public void remove(String[] ids){
    	this.webAppDao.remove(ids);
    }
    /**
     * @Description: 修改一条记录
     * @author 罗华森
     * @date 2015-11-19
     */
    public String edit(WebApp wa){
    	return this.webAppDao.edit(wa);
    }
    /**
     * @description：获取应用列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @return 
     */
    public List<WebApp> getWebAppList(Integer start,Integer size){
        return webAppDao.getWebAppList(start,size);
    }
    
    /**
     * @description：根据应用id获取应用对象
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param webAppId 应用id
     * @return 
     */
    public WebApp getWebAppById(String webAppId){
        return webAppDao.getWebAppById(webAppId);
    }
    
    /**
     * @description：根据应用id从投票集合中聚合目录列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return 
     */
    public List<WebApp> getDirListByVote(String appId,String voteid){
        return webAppDao.getDirListByVote(appId,voteid);
    }
    
    /**
     * @description：根据应用id从评价集合中聚合目录列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return  
     */
    public List<WebApp> getDirListByComments(String appId){
        return webAppDao.getDirListByComments(appId);
    }
    
    /**
     * @Description: 模糊查询
     * @author 罗华森
     * @date 2015-11-30
     */
    public List<WebApp> queryWebApp(WebApp wa){
    	return webAppDao.queryWebApp(wa);
    }
    public WebAppDao getWebAppDao() {
        return webAppDao;
    }

    public void setWebAppDao(WebAppDao webAppDao) {
        this.webAppDao = webAppDao;
    }
    /**
     * @Description: 获取记录个数
     * @author 罗华森
     * @date 2015-12-11
     */
    public int getCount(){
    	return this.webAppDao.getCount();
    }
}
