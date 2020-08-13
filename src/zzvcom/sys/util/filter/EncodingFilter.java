/**
 * 编码过滤器
 */
package zzvcom.sys.util.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * Desc:
 * User: Administrator
 * Date: 2008-10-7
 * Time: 14:24:05
 */
public class EncodingFilter implements Filter {
    private static Logger log = Logger.getLogger(EncodingFilter.class);
    protected FilterConfig filterConfig;
    private String encoding;

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        response.setCharacterEncoding(this.encoding);
        request.setCharacterEncoding(this.encoding);
        
        if (request.getMethod().equalsIgnoreCase("post")) {
            
        } else {
            Iterator iter = request.getParameterMap().values().iterator();
            while (iter.hasNext()) {
                String[] parames = (String[]) iter.next();
                for (int i = 0; i < parames.length; i++) {
                    try {
                        parames[i] = new String(parames[i].getBytes("iso8859-1"), this.encoding);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    	this.filterConfig = filterConfig;
    	if(filterConfig.getInitParameter("encoding")!=null && filterConfig.getInitParameter("encoding").trim().length()>0){
    		this.encoding = filterConfig.getInitParameter("encoding");
    	}else{
    		this.encoding = "utf8";
    	}
    	log.info("\r\n[INFO]*************************\r\n[INFO]       EncodingFilter encoding set is :"+this.encoding+"\r\n[INFO]**************************");
    }

    public void destroy() {
    	this.encoding = null;
        this.filterConfig = null;
    }
}
