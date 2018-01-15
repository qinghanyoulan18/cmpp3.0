package com.jiuying.msg;

public interface Msg_Command {

	int CMPP_CONNECT	=0x00000001;//请求连接
	int CMPP_CONNECT_RESP=	0x80000001;//请求连接应答
	int CMPP_TERMINATE	=0x00000002;//终止连接
	int CMPP_TERMINATE_RESP=	0x80000002;//终止连接应答
	int CMPP_SUBMIT		=0x00000004;//提交短信
	int CMPP_SUBMIT_RESP=	0x80000004;//提交短信应答
	int CMPP_DELIVER	=0x00000005;//短信下发
	int CMPP_DELIVER_RESP=	0x80000005;//下发短信应答
	int CMPP_QUERY	=0x00000006;//发�?短信状�?查询
	int CMPP_QUERY_RESP	=0x80000006;//发�?短信状�?查询应答
	int CMPP_CANCEL	=	0x00000007;//删除短信
	int CMPP_CANCEL_RESP=	0x80000007;//删除短信应答
	int CMPP_ACTIVE_TEST	=0x00000008;//�?��测试
	int CMPP_ACTIVE_TEST_RESP	=0x80000008;//�?��测试应答
	int CMPP_FWD	=0x00000009;//消息前转
	int CMPP_FWD_RESP=	0x80000009;//消息前转应答
	int CMPP_MT_ROUTE	=0x00000010;
	int CMPP_MT_ROUTE_RESP	=0x80000010;
	int CMPP_MO_ROUTE	=0x00000011;
	int CMPP_MO_ROUTE_RESP	=0x80000011;
	int CMPP_GET_MT_ROUTE	=0x00000012;
	int CMPP_GET_MT_ROUTE_RESP=	0x80000012;
	int CMPP_MT_ROUTE_UPDATE	=0x00000013;
	int CMPP_MT_ROUTE_UPDATE_RESP	=0x80000013;
	int CMPP_MO_ROUTE_UPDATE	=0x00000014;
	int CMPP_MO_ROUTE_UPDATE_RESP=	0x80000014;
	int CMPP_PUSH_MT_ROUTE_UPDATE	=0x00000015;
	int CMPP_PUSH_MT_ROUTE_UPDATE_RESP	=0x80000015;
	int CMPP_PUSH_MO_ROUTE_UPDATE	=0x00000016;
	int CMPP_PUSH_MO_ROUTE_UPDATE_RESP	=0x80000016;
	int CMPP_GET_MO_ROUTE	=0x00000017;
	int CMPP_GET_MO_ROUTE_RESP	=0x80000017;
}
