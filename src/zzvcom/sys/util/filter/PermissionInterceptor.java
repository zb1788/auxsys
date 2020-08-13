package zzvcom.sys.util.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zzvcom.sys.pojo.AuxsysRole;
import zzvcom.sys.service.ModuleService;
import zzvcom.sys.service.RoleService;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 权限控制拦截器
 * @author liuzhiqiang
 *
 */

public class PermissionInterceptor  extends AbstractInterceptor{
	
	private static final long serialVersionUID = -8148587933080816589L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String username = (String)request.getSession().getAttribute("userName");
		String extendpath=request.getContextPath()+"/admin/index.action,"+request.getContextPath()+"/admin/login.action";
        if(username==null && (extendpath.indexOf(request.getRequestURI())<0)&&request.getRequestURI().indexOf("/admin/")>=0){
        	return "sessionNotValid";
        }
        /*
        //获取操作id
		String mid = request.getParameter("mid");
		try{
			Long.parseLong(mid);
		}catch(NumberFormatException e){
			mid = null;
		}
		*/
		return invocation.invoke();//执行调用;
	}
}
