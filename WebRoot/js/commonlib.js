/**
方法列表:
Date.parseString
Date.format
String.replaceAll
Array.remove
String.trim
autoselect(selecter,selvalue)自动选择 selecter对象 对应value的option
getErrorMessage(error);显示异常信息
**/
/*
js内置方法备忘：日期格式化
Javascript之中，日期对象是Date，Date对象有有四个内置方法，用于输出为字符串格式，分别为：
  1）toGMTString，将一个日期按照GMT格式显示
  2）toLocaleString，将一个日期按照本地操作系统格式显示
  3）toLocaleDateString，按照本地格式显示一个日期对象的日期部分
  4）toLocaleTimeString，按照本地格式显示一个日期对象的时间部分
*/
/*
js内置方法备忘：JS计时器方法
--------------------------------------------------------------------------------------------------------------------------------------- 
window.setIntervalsetInterval 表示每 n 毫秒執行一次某個方法, 會一直重覆, 直到调用 clearInterval(計時器名稱) 
使用方法 : setInterval(function,millisecond) ; function 不能帶任何參數, 1000 毫秒等於一毫秒 
--------------------------------------------------------------------------------------------------------------------------------------- 
window.setTimeout 
setTimeout 表示過了 n 毫秒之後要執行某個方法 (function), 只跑一次 
使用方法 : setTimeout(function,millisecond) ; function 不能帶任何參數, 1000 毫秒等於一毫秒
*/
//XHTTP_ajax请求例子
//demo回调方法
function backcall(){
	if (request.readyState == 4){
	  alert(request.status);
	  if(request.status == 200){
	   //
	  }else if(request.status == 404){
	   //page not found...
	  }
	}
}
function xmlRequest(turl,func){
	try
	{
	   request = new XMLHttpRequest();
	}catch(trymicrosoft) {
		try
		{
			request = new ActiveXObject("Msxml2.XMLHTTP");
		}catch (othermicrosoft) {
			try
			{
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}catch (failed) {
				request = false;
				alert("request could not create !");
			}
	   }
	}
	try
	{
		if (!request){
			//alert("Error initializing XMLHttpRequest!");
		}else{
			request.onreadystatechange = func;
			request.open("GET", turl, false);
			request.send(null);
		}
	}catch(e){
		alert("打开异常!"+e);
	}
}

//日期转化字符串"yyyy-MM-dd"
Date.prototype.parseString = function(dateString,formatter){
    var today = new Date();
    if(!dateString || dateString == "")
    {
        return today;
    }
    if(!formatter || formatter == "")
    {
        formatter = "yyyy-MM-dd";
    }  
    var yearMarker = formatter.replace(/[^y|Y]/g,'');   
    var monthMarker = formatter.replace(/[^m|M]/g,'');   
    var dayMarker = formatter.replace(/[^d]/g,'');
    var yearPosition = formatter.indexOf(yearMarker);
    var yearLength = yearMarker.length;
    var year =  dateString.substring(yearPosition ,yearPosition + yearLength) * 1;
    if( yearLength == 2)
    {
        if(year < 50 )
        {
            year += 2000;
        }
        else
        {
            year += 1900;
        }
    }
    var monthPosition = formatter.indexOf(monthMarker);
    var month = dateString.substring(monthPosition,monthPosition + monthMarker.length) * 1 - 1;
    var dayPosition = formatter.indexOf(dayMarker);
    var day = dateString.substring( dayPosition,dayPosition + dayMarker.length )* 1;
    return new Date(year,month,day);
}

//日期格式化方法"yyyy-MM-dd"
Date.prototype.format = function(formatter){
    if(!formatter || formatter == ""){
        formatter = "yyyy-MM-dd";
    }
    var year = this.getYear().toString();
    var month = (this.getMonth() + 1).toString();
    var day = this.getDate().toString();
    var yearMarker = formatter.replace(/[^y|Y]/g,'');
    if(yearMarker.length == 2)
    {
        year = year.substring(2,4);
    }    
    var monthMarker = formatter.replace(/[^m|M]/g,'');
    if(monthMarker.length > 1)
    {
        if(month.length == 1) 
        {
            month = "0" + month;
        }
    }    
    var dayMarker = formatter.replace(/[^d]/g,'');
    if(dayMarker.length > 1)
    {
        if(day.length == 1) 
        {
            day = "0" + day;
        }
    }    
    return formatter.replace(yearMarker,year).replace(monthMarker,month).replace(dayMarker,day);    
}
//字符串replaceAll方法
String.prototype.replaceAll = function(AFindText,ARepText){
  raRegExp = new RegExp(AFindText,"g");
  return this.replace(raRegExp,ARepText)
}
//数组移除index位置内容方法
Array.prototype.remove = function(index){
    var isremove = false;
	if(index<this.length){
	  for(var i=(index);i<this.length-1;i++)
	  {
	   this[i]= this[i+1];
	  }
	  isremove = true;
	}
    if(isremove){
  		this[this.length-1]=null;
		this.length -= 1;
    }
}
//字符串对象加入过滤空格方法
String.prototype.trim = function(){ 
    return this.replace(/(^\s*)|(\s*$)/g, ""); 
}
//autoselect(selecter,selvalue)自动选择 selecter对象 对应value的option
function autoselect(selecter,selvalue){
	if(selecter==null){
		return;
	}
	if(selvalue==null){
		selvalue="";
	}
	optionslist = selecter.options;
	for(var i=0;i<optionslist.length;i++){
		if(optionslist(i).value==selvalue){
			optionslist(i).selected="selected";
			return;
		}
	}
}
//输出异常信息
function getErrorMessage(error){
	var errMessage=new Array();
	for(i in error){
		errMessage.push(i+":"+error[i]);
	}
	return errMessage.join("\r\n");
}