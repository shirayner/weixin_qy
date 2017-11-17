package com.ray.weixin.qy.config;

/**@desc  : 企业微信接入配置
 * 
 * @author: shirayner
 * @date  : 2017年9月27日 下午4:57:36
 */

public class Env {
	/**
	 * 1. 企业应用接入秘钥相关
	 */
	//企业ID
	public final static String CORP_ID = "ww92f5da92bb24696e";
	//应用的凭证密钥
	public final static String AGENT_SECRET = "I73733veH3uCs6H_ijPvIq0skjTaOePsFF4MyCEi3Ag";
	//通讯录秘钥
	public final static String CONTACTS_SECRET = "1m_9XP62YrXjSiYtL5ThbexiLVWBThukiK5sH7wm1TM";
	//打卡的凭证密钥
	public final static String CHECKIN_SECRET = "LLTMcHo5otbgXMF8a5HY0ThTrQLqfkVmU0F6wX_gRIc";
	//审批的凭证密钥
	public final static String APPROVE_SECRET = "6X7Ft0hIZXY6Q2IfbWGLBFvLmNfJkzBZ6k3efWZE0-8";


	  /**
     * 2.接收消息服务器配置:
     * 启用并设置服务器配置后，用户发送的普通消息、自定义菜单操作、上报地理位置，将被微信转发到该URL中
     */
	//token
	public final static String TOKEN = "ray";
	// encodingAESKey
	public final static String ENCODING_AES_KEY = "z2W9lyOAR1XjY8mopEmiSqib0TlBZzCFiCLp6IdS2Iv";

	
	//企业应用的id，整型。可在应用的设置页面查看
	public final static int AGENT_ID = 1000002;



}
