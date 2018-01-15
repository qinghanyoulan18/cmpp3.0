package com.canlu.socket.cmpp;

public class test {

	public static void main(String[] args) {
		//接收网关短信的手机号码集合
		String recP[]={"1064782467052"};
		//初始化短信发送
		CMPPClient cMPPClient=new CMPPClient("183.230.96.94",17890, "110307", "110307","110307","ykclw");
		//发送短信
		cMPPClient.sendNotifySms("1064899110307", "6666666", recP);
	}

}
