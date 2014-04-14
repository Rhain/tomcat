package com.rhain.tomcat.ex03.connector.http;

/**
 * 
 * @ClassName HttpRequestLine
 * @Description TODO
 * @date 2014年3月28日
 */
public final class HttpRequestLine {

	//---------------------------------------------- 常量
	public static final int INITIAL_METHOD_SIZE = 8;
	public static final int INITIAL_URI_SIZE = 64;
	public static final int INITIAL_PROTOCOL_SIZE = 8;
	public static final int MAX_METHOD_SIZE = 1024;
	public static final int MAX_URI_SIZE = 32768;
	public static final int MAX_PROTOCOL_SIZE = 1024;
	
	
	//---------------------------------------------- 实例变量
	 
	public char[] method;
	public int methodEnd;
	public char[] uri;
	public int uriEnd;
	public char[] protocol;
	public int protocolEnd;
	
	
	//-----------------------------------------------构造函数
	
	public HttpRequestLine(char[] method,int methodEnd,char[] uri,int uriEnd,char[] protocol,int protocolEnd){
		this.method = method;
		this.methodEnd = methodEnd;
		this.uri = uri;
		this.uriEnd = uriEnd;
		this.protocol = protocol;
		this.protocolEnd = protocolEnd;
		
	}
	
	public HttpRequestLine(){
		this(new char[INITIAL_METHOD_SIZE],0,new char[INITIAL_URI_SIZE],0,new char[INITIAL_PROTOCOL_SIZE],0);
	}
	
	/**
	 * 
	 * @Method: recycle 
	 * @Description: 释放所有引用的对象和初始化的实例对象，重新使用
	 * @param 
	 * @return void
	 * @throws
	 */
	public void recycle(){
		
		methodEnd = 0;
		uriEnd = 0;
		protocolEnd = 0;
	}
	
	/**
	 * 
	 * @Method: indexOf 
	 * @Description: 检查给定的char数组是否在uri中
	 * @param @param buf
	 * @param @param end
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int indexOf(char[] buf, int end) {
        char firstChar = buf[0];
        int pos = 0;
        while (pos < uriEnd) {
            pos = indexOf(firstChar, pos);
            if (pos == -1)
                return -1;
            if ((uriEnd - pos) < end)
                return -1;
            for (int i = 0; i < end; i++) {
                if (uri[i + pos] != buf[i])
                    break;
                if (i == (end-1))
                    return pos;
            }
            pos++;
        }
        return -1;
    }


    /**
     * Test if the value of the header includes the given string.
     */
    public int indexOf(String str) {
        return indexOf(str.toCharArray(), str.length());
    }


    /**
     * Returns the index of a character in the value.
     */
    public int indexOf(char c, int start) {
        for (int i=start; i<uriEnd; i++) {
            if (uri[i] == c)
                return i;
        }
        return -1;
    }
    
    /**
     * Test if the uri includes the given char array.
     */
    public int indexOf(char[] buf) {
        return indexOf(buf, buf.length);
    }
	
	
}
