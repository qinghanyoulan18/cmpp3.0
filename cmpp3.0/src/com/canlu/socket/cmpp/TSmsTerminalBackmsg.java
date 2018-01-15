package com.canlu.socket.cmpp;

/**
 * 
 * <br>
 * <b>功能：</b>TSmsTerminalBackmsgEntity<br>
 */
public class TSmsTerminalBackmsg {
	
	
	
	@Override
	public String toString() {
		return "TSmsTerminalBackmsg [bid=" + bid + ", dest=" + dest
				+ ", src_terminal_Id=" + src_terminal_Id + ", msg=" + msg
				+ ", iD=" + iD + ", time=" + back_time + "]";
	}
	private java.lang.Integer bid;//   	private java.lang.String dest;//   被叫接入号	private java.lang.String src_terminal_Id;//   源终端号	private java.lang.String msg;//   短信内容	private java.lang.String iD;//   十六进制消息	private java.lang.String back_time;//   写入数据的时间用now()	public java.lang.Integer getBid() {	    return this.bid;	}	public void setBid(java.lang.Integer bid) {	    this.bid=bid;	}	public java.lang.String getDest() {	    return this.dest;	}	public void setDest(java.lang.String dest) {	    this.dest=dest;	}		public java.lang.String getSrc_terminal_Id() {
		return src_terminal_Id;
	}
	public void setSrc_terminal_Id(java.lang.String src_terminal_Id) {
		this.src_terminal_Id = src_terminal_Id;
	}
	public java.lang.String getMsg() {	    return this.msg;	}	public void setMsg(java.lang.String msg) {	    this.msg=msg;	}	public java.lang.String getID() {	    return this.iD;	}	public void setID(java.lang.String iD) {	    this.iD=iD;	}
	public java.lang.String getBack_time() {
		return back_time;
	}
	public void setBack_time(java.lang.String back_time) {
		this.back_time = back_time;
	}
		
}

