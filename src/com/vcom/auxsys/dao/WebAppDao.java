package com.vcom.auxsys.dao;

import java.util.List;

import com.vcom.auxsys.entitys.pojo.Comments;
import com.vcom.auxsys.entitys.pojo.WebApp;

public interface WebAppDao {
	
	/**
	 * @Description: 添加一个应用
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public String add(WebApp wa);
	/**
	 * @Description: 删除一个应用
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public void remove(String[] ids);
	/**
	 * @Description: 修改一个应用
	 * @author 罗华森
	 * @date 2015-11-19
	 */
	public String edit(WebApp wa);
	
    /**
     * @description：获取应用列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @return 
     */
    public List<WebApp> getWebAppList(Integer start,Integer size);
    
    /**
     * @description：根据应用id获取应用对象
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return 
     */
    public WebApp getWebAppById(String appId);
    /**
     * @Description: 检索元素
     * @author 罗华森
     * @date 2015-11-30
     */
    public List<WebApp> queryWebApp(WebApp wa);
    
    /**
     * @description：检查提价评论项是否完整
     * @time： 2015-11-23
     * @author：donghaoyu
     * @param comments
     * @return 
     */
    public String checkAppSfcz(Comments comments);
    
    /**
     * @description：根据应用id从投票集合中聚合目录列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return 
     */
    public List<WebApp> getDirListByVote(String appId,String voteid);
    
    /**
     * @description：根据应用id从评价集合中聚合目录列表
     * @time： 2015-11-12
     * @author：donghaoyu
     * @param appId 应用id
     * @return  
     */
    public List<WebApp> getDirListByComments(String appId);
    /**
     * @Description: 获取记录个数
     * @author 罗华森
     * @date 2015-12-11
     */
    public int getCount();
}
