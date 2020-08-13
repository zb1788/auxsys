/**
 * 全页面 input type=text 加入特殊字符过滤
 * 可通过加入checktype属性控制对应checkmap中过滤的正则
 */
//限制input[type=text]输入内容
function LimitObj(){
	this.checkkeymap={"0":"",
		"1":"[<|>|\\||\\{|\\}|\\+|\\*|\/|\[|\\]|\\-|!|=|;|:|%|~|,|\'|\"]"
	};
}
var limitobj = new LimitObj();
//1为默认执行规则,0为不执行
window.onload=function(){
try{
var inputlist = document.getElementsByTagName("input");
for(var ii=0;ii<inputlist.length;ii++){
	if(inputlist[ii].type=="text"){
		inputlist[ii].onblur=function(){
			var checkKey = null;
			if(this.getAttribute("checktype")){
				checkKey=limitobj.checkkeymap[this.getAttribute("checktype")];
			}
			if(checkKey == null){
				//checkKey=limitobj.checkkeymap[1];
			};
			var reg=new RegExp(checkKey,"gmi");
			this.value = this.value.replace(reg,"");
		};
	}
}
}catch(e){}
}