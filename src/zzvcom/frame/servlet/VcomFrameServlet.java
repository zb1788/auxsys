package zzvcom.frame.servlet;

import zzvcom.frame.beans.VcomFrameSettingImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.struts2.ServletActionContext;

public class VcomFrameServlet extends HttpServlet
{

    private String rootPath="vcomframe/";
    private String fileend=".css|.html|.js|.htc";
    private static int type=0;
    private static HashMap filepath=new HashMap();
    public VcomFrameServlet()
    {
    }

    public void init()
        throws ServletException
    {
        super.init();
    }
    /**
     * 因不能解析jsp页面，故第一次启动先初始化需要的jsp页面
     * @param httpservletrequest
     */
    public void createjsp(HttpServletRequest httpservletrequest){
    	File file=new File(getServletContext().getRealPath("/")+"ewebeditor/jsp");
        if(!(file.exists()&&file.isDirectory())){
        	file.mkdirs();
        	writejsppage(getHTML("vcomframe/list/ewebeditor/jsp/upload.jsp"),getServletContext().getRealPath("/")+"ewebeditor/jsp/upload.jsp");
        	writejsppage(getHTML("vcomframe/list/ewebeditor/jsp/config.jsp"),getServletContext().getRealPath("/")+"ewebeditor/jsp/config.jsp");
        	writejsppage(getHTML("vcomframe/list/ewebeditor/jsp/browse.jsp"),getServletContext().getRealPath("/")+"ewebeditor/jsp/browse.jsp");
        	type=1;
        }
        file=new File(getServletContext().getRealPath("/")+"ewebeditor/uploadfile");
        if(!(file.exists()&&file.isDirectory())){
        	file.mkdirs();
        }
    }
    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        if(type==0)createjsp(httpservletrequest);
    	VcomFrameSettingImpl frameindexpageimpl = new VcomFrameSettingImpl();
        Map<String, String> map = frameindexpageimpl.getFrameSetting();
    	String path=httpservletrequest.getRequestURI();
    	String userName=(String) httpservletrequest.getSession().getAttribute("userName");
    	String menuTree=(String) httpservletrequest.getSession().getAttribute("menuTree");
    	if(path.endsWith(".frame")){
    		String jarpath="vcomframe/common/ext.html";
    		String value="";
    		if(filepath.get(jarpath)==null){
				value=getHTML(jarpath).replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath);
				value=value.replace("${projectname}", map.get("project-name"))
					.replace("${menutype}", map.get("menu-type"))
					.replace("${tabcount}", map.get("tab-count"))
					.replace("${oncontextmenu}", map.get("oncontext-menu"))
					.replace("${path}", httpservletrequest.getContextPath())
					.replace("${welcomepage}", (map.get("welcome-page")==null&&map.get("welcome-page").equals(""))?""+httpservletrequest.getContextPath()+"/vcomframe/common/today.htm":(map.get("welcome-page").replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath)))
					.replace("${projecticon}", map.get("project-icon").replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath));
				if(map.get("url")==null||map.get("url").equals("")){
					//value=value.replace("${html}", map.get("html").replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath));
					// ------添加单点登陆控制 
					String _html = map.get("html").replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath);
					String _sso = map.get("sso");
					if (_sso != null && _sso.equals("1")) {
						_html = _html.replace("javascript:quit();", "javascript:window.close();");
						_html = _html.replace("<span id=\"change_pwd\">",
								"<span id=\"change_pwd\" style=\"display: none\">");
					}
					value=value.replace("${html}", _html);
					// ------2012.12.12
				}else{
					value=value.replaceAll("<data>[\\s\\S]*?</data>","<iframe id=\"{id}\" scrolling=\"no\"  marginheight=\"0\" marginwidth=\"0\" width=\"100%\" height=\"60\" src="+map.get("url")+" frameborder=\"0\"></iframe>");
				}
				filepath.put(jarpath, value);
			}else{
				value=(String)filepath.get(jarpath);
			} 
    		value=value.replace("${user}", userName==null?"系统管理员":userName);
    		writeHTML(value.replace("${menuTree}", menuTree==null?(map.get("menuTree").replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath)):menuTree),httpservletresponse);
    	}else if(path.indexOf(".")>=0){
    		if(fileend.indexOf(path.substring(path.lastIndexOf("."), path.length()))>=0){
    			String jarpath=path.substring(path.lastIndexOf(rootPath), path.length()).replaceAll("//", "/");
    			if(filepath.get(jarpath)==null){
    				String value=getHTML(jarpath).replaceAll(rootPath, httpservletrequest.getContextPath()+"/"+rootPath);
    				filepath.put(jarpath, value);
    				writeHTML(value,httpservletresponse);
    			}else{
    				writeHTML((String)filepath.get(jarpath),httpservletresponse);
    			}
    			
    		}else{
    			String jarpath=path.substring(path.lastIndexOf("vcomframe"), path.length());
    			InputStream inputstream=getInputstream(jarpath);
    			writePic(inputstream,httpservletresponse);
    		}
    	}else{
    		
    	}
    }

    public void doPost(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException
    {
        doGet(httpservletrequest, httpservletresponse);
    }
    
    private Class class$(String name){
    	
    	try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }
    //获取内容
    private String getHTML( String s1){
    	return getStringFromResource(getInputstream(s1));
    }
    //输出内容
    private void writeHTML( String s1, HttpServletResponse httpservletresponse)
    {
    	PrintWriter printwriter;
		try {
			httpservletresponse.setCharacterEncoding("utf8");
			printwriter = httpservletresponse.getWriter();
			printwriter.write(s1);
	        printwriter.flush();
	        printwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    private InputStream getInputstream(String s1){
    	InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream(s1);
    	return inputstream;
    }
    //输出图片
    private void writePic(InputStream inputstream, HttpServletResponse httpservletresponse){
    	BufferedInputStream bufferedinputstream = new BufferedInputStream(inputstream);
    	BufferedOutputStream bufferedoutputstream=null;
        try {
        	
            byte abyte0[] = new byte[4096];
        	bufferedoutputstream = new BufferedOutputStream(httpservletresponse.getOutputStream());
			for(int i = bufferedinputstream.read(abyte0); i != -1; i = bufferedinputstream.read(abyte0))
			    bufferedoutputstream.write(abyte0, 0, i);
			bufferedinputstream.close();
	        bufferedoutputstream.flush();
	        bufferedoutputstream.close();
		} catch (IOException e) {
	        try {
				bufferedoutputstream.flush();
				bufferedinputstream.close();
				bufferedoutputstream.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}finally{
			try {
				bufferedoutputstream.flush();
				bufferedinputstream.close();
				bufferedoutputstream.close();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}
       
    }
    private void writejsppage(String content,String path){
    	File file=new File(path);
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(file);
			OutputStreamWriter writer=new OutputStreamWriter(stream);
			BufferedWriter output = new BufferedWriter(writer);
			output.write(content); 
			output.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    private String getStringFromResource(InputStream inputstream)
    {
	    BufferedReader bufferedreader = null;
	    StringBuffer stringbuffer=new StringBuffer();
	    String s;
	    try
	    {
	    	bufferedreader = new BufferedReader(new InputStreamReader(inputstream,"utf-8"));
	    	while((s = bufferedreader.readLine()) != null) 
	        {
	            stringbuffer.append(s);
	            stringbuffer.append("\r\n");
	        }
	    }catch(IOException o){
	    	try {
				bufferedreader.close();
				inputstream.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
	    }finally
	    {
	        try {
				bufferedreader.close();
				inputstream.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
	        
	    }
	    return stringbuffer.toString();
    }
    private void gotoUrl(HttpServletResponse httpservletresponse, String s)
        throws IOException
    {
        httpservletresponse.sendRedirect(s);
    }
    
    public void getSessionInfo(){
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String userName=(String) request.getSession().getAttribute("userName");
        String menuTree=(String) request.getSession().getAttribute("menuTree");
        String retJson ="";
        retJson +=userName+"|" + menuTree ;
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.print(retJson);
                out.flush();
                out.close();
            }
        }
    }
}
