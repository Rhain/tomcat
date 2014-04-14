package com.rhain.tomcat.ex03.connector.http;

/**
 * 
 * @ClassName HttpHeader
 * @Description HttpHeader
 * @date 2014年3月28日
 */
final class HttpHeader {

	// --------------------------------------------常量 
	public static final int INITIAL_NAME_SIZE = 32;
	public static final int INITIAL_VALUE_SIZE = 64;
	public static final int MAX_NAME_SIZE = 128;
	public static final int MAX_VALUE_SIZE = 4096;
	
	// -------------------------------------------- 实例变量
	
	public char[] name;
	public int nameEnd;
	public char[] value;
	public int valueEnd;
	protected int hashCode = 0 ;
	
	// -------------------------------------------- 构造函数
	
	public HttpHeader(char[] name,int nameEnd,char[] value,int valueEnd){
		this.name = name;
		this.nameEnd = nameEnd;
		this.value = value;
		this.valueEnd = valueEnd;
	}
	
	public HttpHeader(){
		this(new char[INITIAL_NAME_SIZE], 0, new char[INITIAL_VALUE_SIZE], 0);
	}
	
	//-------------------------------------------- 公有方法
	
	/**
	 * 
	 * @Method: recycle 
	 * @Description: 释放所有引用的对象和实例化的实例变量，为重新使用这个对象做准备
	 * @param 
	 * @return void
	 * @throws
	 */
	public void recycle(){
		
		nameEnd = 0;
		valueEnd = 0;
		hashCode = 0;
		
	}
	
	/**
	 * 
	 * @Method: equals 
	 * @Description: 检查char 数组是否与header的name相等。所有的字符都必须是小写。
	 * @param @param buf
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean equals(char[] buf){
		return equals(buf,buf.length);
	}
	
	/**
	 * 
	 * @Method: equals 
	 * @Description: 检查char 数组是否与header的name相等。所有的字符都必须是小写。
	 * @param @param buf
	 * @param @param end
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean equals(char[] buf,int end){
		if(end != nameEnd){
			return false;
		}
		for(int i=0;i<end;i++){
			if(buf[i] != name[i])
				return false;
		}
		return true;
	}
	
	/**
	 * 
	 *检查char 数组是否与header的name相等。所有的字符都必须是小写。
	 */
	public boolean equals(String str){
		return equals(str.toCharArray(),str.length());
	}
	
	/**
	 * 
	 * 检查char数组是否与Header 的值相等
	 * 
	 */
	public boolean valueEquals(char[] buf){
		return valueEquals(buf,buf.length);
	}
	
	/**
	 * 
	 * @Method: valueEquals 
	 * @Description: 检查char数组是否与Header 的值相等
	 * @param @param buf
	 * @param @param end
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean valueEquals(char[] buf,int end){
		if(end != valueEnd)
			return false;
		for(int i = 0;i < end ;i++){
			if(buf[i] != value[i])
				return false;
		}
		return true;
	}
	
	public boolean valueEquals(String str){
		return valueEquals(str.toCharArray());
	}
	
	/**
	 * 
	 * @Method: valueIncludes 
	 * @Description: 检查Header的value是否包含给定的char数组
	 * @param @param buf
	 * @param @param end
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean valueIncludes(char[] buf ,int end){
		char firstChar = buf[0];
		int pos = 0 ;
		while(pos < valueEnd){
			pos = valueIndexOf(firstChar,pos);
			if(pos == -1)
				return false;
			if(valueEnd - pos < end)
				return false;
			for(int i = 0;i<end;i++){
				if(buf[i] != value[i+pos])
					break;
				if(i == (end - 1))
					return true;
			}
			pos ++;
		}
		return false;
	}
	
	public boolean valueIncludes(char[] buf){
		return valueIncludes(buf,buf.length);
	}
	
	public boolean valueIncludes(String str){
		return valueIncludes(str.toCharArray());
	}
	
	public int valueIndexOf(char c ,int start){
		for(int i = start;i<valueEnd;i++){
			if(value[i] == c)
				return i;
		}
		return -1;
	}
	
	/**
	 * 
	 * @Method: equals 
	 * @Description: 比较两个HttpHeader 的name 是否相等
	 * @param @param header
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean equals(HttpHeader header){
		return (equals(header.name,header.nameEnd));
	}
	
	/**
	 * 比较两个HttpHeader 是否相等
	 * @Method: headersEquals 
	 * @Description: TODO
	 * @param @param header
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean headersEquals(HttpHeader header){
		return (equals(header.name,header.nameEnd))&&(valueEquals(header.value,header.valueEnd));
	}
	
	//-------------------------------------------------- 重写hashCode 与 equals
	
	public int hashCode(){
		int h = hashCode;
		if(h == 0){
			int off = 0;
			char val[] = name;
			int len = nameEnd;
			for(int i = 0;i<len;i++){
				h = 31*h + val[off++];
			}
			hashCode = h;
		}
		return h;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof String){
			return equals(((String)obj).toLowerCase());
		}else if (obj instanceof HttpHeader){
			return equals((HttpHeader)obj);
		}
		return false;
	}
}
