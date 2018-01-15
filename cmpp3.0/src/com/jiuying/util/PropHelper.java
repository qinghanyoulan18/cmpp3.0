package com.jiuying.util;

import java.io.InputStream;

public class PropHelper{   
	/**  
	*guessPropFile:  
	*@param cls:和要寻找的属性文件处于相同的包中的任意的类  
	*@param propFile:要寻找的属性文件名  
	*/  
	@SuppressWarnings("unchecked")
	public static java.io.InputStream guessPropFile(Class cls,String propFile){   
	try{   
	ClassLoader loader=cls.getClassLoader();   
	InputStream in = null;
	Package pack=cls.getPackage();   
	if(pack!=null){   
	String packName=pack.getName();   
	String path="";   
	if(packName.indexOf(".") < 0 )   
	path=packName+"/";   
	else{   
	int start=0,end=0;   
	end=packName.indexOf(".");   
	while(end!=-1){   
	path=path+packName.substring(start,end)+"/";   
	start=end+1;   
	end=packName.indexOf(".",start);   
	}   
	path=path+packName.substring(start)+"/";   
	}   
	in=loader.getResourceAsStream(path+propFile);
	if(in!=null) return in;   
	}   
	  
	//如果没有找到，再从当前系统的用户目录中进行查找   
	java.io.File f=null;   
	String curDir=System.getProperty("user.dir");   
	f=new java.io.File(curDir,propFile);   
	if(f.exists()) return new java.io.FileInputStream(f);   
	  
	//如果还是没有找到，则从系统所有的类路径中查找   
	String classpath=System.getProperty("java.class.path");   
	String[] cps=classpath.split(System.getProperty("path.separator"));   
	  
	for(int i=0;i < cps.length; i++){   
	f=new java.io.File(cps[i],propFile);   
	if(f.exists()) break;   
	f=null;   
	}   
	if(f!=null) return new java.io.FileInputStream(f);   
	return null;   
	}catch(Exception e){throw new RuntimeException(e);}   
	  
	}   
	
}   
