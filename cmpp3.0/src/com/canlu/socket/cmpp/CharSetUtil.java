package com.canlu.socket.cmpp;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;  
import java.nio.charset.Charset;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
/** 
 * @author tian 
 * 
 */  
public class CharSetUtil {  

	// 1. 定义枚举类型
    public enum CharSet {
       // 利用构造函数传参
     ASCII(1), WRITE(3),BINARY(4),UNICODE(8),GBK(15);
       // 定义私有变量
       private int nCode ;

       // 构造函数，枚举类型只能为私有
       private CharSet(int _nCode) {
           this.nCode = _nCode;
       }
       
       @Override
       public String toString() {
           return String.valueOf ( this.nCode );
       }

		public int getnCode() {
			return nCode;
		}
       
		 public static String getChar(int v) {
		        switch (v) {
		        case 1:
		        	return "ASCII";
		        case 3:
		            
		        case 4:
		        
		        case 8:
		        	return "unicode";
		        case 15:
		        	return "gbk";
		        case 16:
		        	return "utf-8";
		        default:
		        	return null;
		        }
				
		    }
		

    }  
    public static String getContent(byte[] content,byte charSet) throws UnsupportedEncodingException {
    	String charsetstr="";
    	////System.out.println("工具类字符编码:====>"+charset);
    	if(charSet==15){
    		charsetstr="gbk";
    	}else if(charSet==8){
    		charsetstr="unicode";
    	}else if(charSet==0){
    		charsetstr="ISO-8859-1";
    	}else {
    		charsetstr="utf-8";
    	}
    	//System.out.println("最后用了:"+charsetstr+"编码");
       return new String(content,charsetstr);
	 }
    
	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] b_unicode = "中".getBytes("unicode");
//		String s_utf8 = new String(b_unicode,"unicode"); 
//		System.out.println("来了吗!!!!"+s_utf8);
		System.out.println(CharSetUtil.getContent(b_unicode,(byte)8));
	}
	
  
    /** 
     * 解码 Unicode \\uXXXX 
     * @param str 
     * @return 
     */  
    public static String decodeUnicode(String str) {  
        Charset set = Charset.forName("UTF-8");  
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");  
        Matcher m = p.matcher( str );  
        int start = 0 ;  
        int start2 = 0 ;  
        StringBuffer sb = new StringBuffer();  
        while( m.find( start ) ) {  
            start2 = m.start() ;  
            if( start2 > start ){  
                String seg = str.substring(start, start2) ;  
                sb.append( seg );  
            }  
            String code = m.group( 1 );  
            int i = Integer.valueOf( code , 16 );  
            byte[] bb = new byte[ 4 ] ;  
            bb[ 0 ] = (byte) ((i >> 8) & 0xFF );  
            bb[ 1 ] = (byte) ( i & 0xFF ) ;  
            ByteBuffer b = ByteBuffer.wrap(bb);  
            sb.append( String.valueOf( set.decode(b) ).trim() );  
            start = m.end() ;  
        }  
        start2 = str.length() ;  
        if( start2 > start ){  
            String seg = str.substring(start, start2) ;  
            sb.append( seg );  
        }  
        return sb.toString() ;  
    }  
      
   /* public static void main(String[] args) {  
        //System.out.println( decodeUnicode("嬀nbR�"));  
    }  */
}  