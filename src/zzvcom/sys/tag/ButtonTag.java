package zzvcom.sys.tag;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import zzvcom.sys.pojo.VcomSysOperation;
import com.opensymphony.xwork2.util.ValueStack;
/**
 * 瀹炵幇鑷畾涔夊姛鑳藉尯鎸夐挳鍖烘爣绛�
 * @author liuzhiqiang
 *
 */

public class ButtonTag extends ComponentTagSupport {
	private String buttiontype;
	private String buttionsize;
	
	public String getButtionsize() {
		return buttionsize;
	}
	public void setButtionsize(String buttionsize) {
		this.buttionsize = buttionsize;
	}
	public String getButtiontype() {
		return buttiontype;
	}
	public void setButtiontype(String buttiontype) {
		this.buttiontype = buttiontype;
	}
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		return new Buttons(arg0,arg1,arg2,this.getButtiontype(),this.getButtionsize());
	}
	class Buttons extends Component {
		public Buttons(ValueStack stack,HttpServletRequest arg1,
				HttpServletResponse arg2,String buttiontype,String buttionsize) {
			super(stack);
			this.setRequest(arg1);
			this.setResponse(arg2);
			this.setButtiontype(buttiontype);
			this.setButtionsize(buttionsize);
		}
		private HttpServletRequest request;
		private HttpServletResponse response;
		private String buttiontype;
		private String buttionsize;
		public String getButtionsize() {
			return buttionsize;
		}
		public void setButtionsize(String buttionsize) {
			this.buttionsize = buttionsize;
		}
		public String getButtiontype() {
			return buttiontype;
		}
		public void setButtiontype(String buttiontype) {
			this.buttiontype = buttiontype;
		}
		public HttpServletRequest getRequest() {
			return request;
		}
		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}
		public HttpServletResponse getResponse() {
			return response;
		}
		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}
		
		@Override  
	    public boolean start(Writer writer) {   
	        boolean result = super.start(writer);   
	        try {   
	            StringBuilder str = new StringBuilder(); 
	            String path=request.getContextPath();
                List<VcomSysOperation> list=(List) this.request.getAttribute("listOperate");
	            if(this.getButtiontype().equals("1")){
	            	StringBuilder stylestr = new StringBuilder(); 
	            	stylestr.append("<style>");
	            	if(this.getButtionsize()!=null&&!this.getButtionsize().equals("")){
	            		int buttonlength=Integer.valueOf(this.buttionsize);
	            		str.append("<DIV class=\"x-toolbar x-small-editor\">");
	            		System.out.println(Math.ceil(list.size()/Double.valueOf(buttonlength))+""+(list.size()/Double.valueOf(buttonlength)));
	            		for(int j=0;j<(Math.ceil(list.size()/Double.valueOf(buttonlength)));j++){
	            			str.append("<TABLE cellSpacing=0><TBODY><TR>");
	            			int start=j*buttonlength;
	            			int typesize=(start+buttonlength)>list.size()?list.size():(start+buttonlength);
	            			for(int i=j*buttonlength;i<typesize;i++){
	            				VcomSysOperation oper=list.get(i);
	            				str.append("<TD>");
				            	str.append("<TABLE style=\"WIDTH: auto\" class=\"x-btn-wrap x-btn x-btn-text-icon\" border=0 cellSpacing=0 cellPadding=0 onmouseout=\"this.className='x-btn-wrap x-btn x-btn-text-icon'\" onmousemove=\"this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'\">");
				            	str.append("<TBODY>");
				            	str.append("<TR>");
				            	str.append("<TD class=x-btn-left><I>&nbsp;</I></TD>");
				            	str.append("<TD class=x-btn-center><EM unselectable=\"on\"><BUTTON class=\"x-btn-text "+oper.getMethod().replaceAll("\\(", "").replaceAll("\\)", "")+"\" onclick=\""+oper.getMethod()+";\">"+oper.getOpername()+"</BUTTON></EM></TD>");
				            	str.append("<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>");
				            	str.append("<TD><SPAN class=ytb-sep></SPAN></TD>");
				            	stylestr.append("."+oper.getMethod().replaceAll("\\(", "").replaceAll("\\)", "")+" { background-image:url("+this.getRequest().getContextPath()+"/"+oper.getOpercode()+ ") !important;};\r\n");
	            			}
	            			str.append("</TR></TBODY></TABLE>");
	            		}
	            		str.append("</DIV>");
	            	}else{
	            		str.append("<DIV class=\"x-toolbar x-small-editor\"><TABLE cellSpacing=0><TBODY><TR>");
			            for(VcomSysOperation oper:list){
			            	str.append("<TD>");
			            	str.append("<TABLE style=\"WIDTH: auto\" class=\"x-btn-wrap x-btn x-btn-text-icon\" border=0 cellSpacing=0 cellPadding=0 onmouseout=\"this.className='x-btn-wrap x-btn x-btn-text-icon'\" onmousemove=\"this.className='x-btn-wrap x-btn x-btn-text-icon x-btn-over x-btn-focus'\">");
			            	str.append("<TBODY>");
			            	str.append("<TR>");
			            	str.append("<TD class=x-btn-left><I>&nbsp;</I></TD>");
			            	str.append("<TD class=x-btn-center><EM unselectable=\"on\"><BUTTON class=\"x-btn-text "+oper.getMethod().replaceAll("\\(", "").replaceAll("\\)", "")+"\" onclick=\""+oper.getMethod()+";\">"+oper.getOpername()+"</BUTTON></EM></TD>");
			            	str.append("<TD class=x-btn-right><I>&nbsp;</I></TD></TR></TBODY></TABLE></TD>");
			            	str.append("<TD><SPAN class=ytb-sep></SPAN></TD>");
			            	stylestr.append("."+oper.getMethod().replaceAll("\\(", "").replaceAll("\\)", "")+" { background-image:url("+this.getRequest().getContextPath()+"/"+oper.getOpercode()+ ") !important;};\r\n");
			            }
			            str.append("</TR></TBODY></TABLE></DIV>");
	            	}
	            	
		            stylestr.append("</style>");
		            writer.write(stylestr.toString()+str.toString());   
	            }else{
	            	for(VcomSysOperation oper:list){
		            	str.append("<input type=\"button\" class=\"button\" value=\"["+oper.getOpername()+"]\"onclick=\""+oper.getMethod()+";\">\r\n");
		            }
	            	writer.write(str.toString());   
	            }
	        }catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		public int getMyInt(int   a,int   b)   {   
			 return(((double)a/(double)b)>(a/b)?a/b+1:a/b);   
		} 
	}


}
