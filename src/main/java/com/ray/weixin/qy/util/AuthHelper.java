package com.ray.weixin.qy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.qy.config.Env;
import com.ray.weixin.qy.service.invoice.InvoiceService;


/**
 * 企业微信 Token、配置工具类
 * @desc  : AccessToken、Jsticket 、Jsapi
 * 
 * @author: shirayner
 * @date  : 2017年9月27日 下午5:00:25
 */
public class AuthHelper {
	private static final Logger logger = LogManager.getLogger(AuthHelper.class);

	//1.获取access_token的接口地址,有效期为7200秒
	private static final String GET_ACCESSTOKEN_URL="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET"; 
	//2.获取getJsapiTicket的接口地址,有效期为7200秒 
	private static final String GET_JSAPITICKET_URL="https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN"; 

	//3.获取电子发票ticket
	private static final String GET_INVOICE_TICKET_URL="https://qyapi.weixin.qq.com/cgi-bin/ticket/get?access_token=ACCESS_TOKEN&type=wx_card"; 


	//3.通过code换取网页授权access_token
	private static final String GET_ACCESSTOKEN_BYCODE_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"; 

	/**
	 * @desc ：1.获取access_token 
	 *  
	 * @param appId  第三方用户唯一凭证
	 * @param appSecret  第三方用户唯一凭证密钥，即appsecret
	 * 
	 * @return
	 *      access_token	获取到的凭证
	 *      expires_in	凭证有效时间，单位：秒
	 * @throws Exception String
	 */
	public static String getAccessToken(String corpId,String corpSecret) throws Exception {
		//1.获取请求url
		String url=GET_ACCESSTOKEN_URL.replace("CORPID", corpId).replace("CORPSECRET", corpSecret);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		String accessToken="";  
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取accessToken
			}else {
				accessToken=jsonObject.getString("access_token");
			}  
		}  


		return accessToken;
	}


	/**
	 * @desc ：2.获取JsapiTicket
	 *  
	 * @param accessToken  有效凭证
	 * @return
	 * @throws Exception String
	 */
	public static String getJsapiTicket(String accessToken) throws Exception {
		//1.获取请求url
		String url=GET_JSAPITICKET_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		String jsapiTicket="";  
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取jsapiTicket
			}else {
				jsapiTicket=jsonObject.getString("ticket");
			}  
		}  


		return jsapiTicket;
	}

	
	/**
	 * @desc ：3.获取电子发票ticket
	 *  
	 * @param accessToken  有效凭证
	 * @return
	 * @throws Exception String
	 */
	public static String getInvoiceTicket(String accessToken) throws Exception {
		//1.获取请求url
		String url=GET_INVOICE_TICKET_URL.replace("ACCESS_TOKEN", accessToken);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		String jsapiTicket="";  
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取jsapiTicket
			}else {
				jsapiTicket=jsonObject.getString("ticket");
			}  
		}  


		return jsapiTicket;
	}
	

	/**
	 * @desc ： 3.通过code换取网页授权access_token
	 *  
	 * @param appId  第三方用户唯一凭证
	 * @param appSecret  第三方用户唯一凭证密钥，即appsecret
	 * @param Code  code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
	 * 
	 * @return
	 * access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * expires_in	access_token接口调用凭证超时时间，单位（秒）
	 * refresh_token	用户刷新access_token
	 * openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
	 * scope	用户授权的作用域，使用逗号（,）分隔
	 * 
	 * @throws Exception String
	 */
	public static JSONObject getAccessTokenByCode(String appId,String appSecret,String code) throws Exception {
		//1.获取请求url
		String url=GET_ACCESSTOKEN_BYCODE_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//3.解析结果，获取accessToken
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取accessToken
			}else {
				returnJsonObject=jsonObject;
			}  
		}  


		return returnJsonObject;
	}


	/**
	 * @desc ：4.获取前端jsapi需要的配置参数
	 *  
	 * @param request
	 * @return String
	 */
	public static String getJsapiConfig(HttpServletRequest request){  
		//1.准备好参与签名的字段
		//1.1 url
		/* 
		 *以http://localhost/test.do?a=b&c=d为例 
		 *request.getRequestURL的结果是http://localhost/test.do 
		 *request.getQueryString的返回值是a=b&c=d 
		 */
		String url=null; 
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		String queryStringEncode = null;

		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}

		//1.2 timestamp 必填，生成签名的时间戳
		String timestamp = Long.toString(System.currentTimeMillis() / 1000); 

		//1.3 noncestr  必填，生成签名的随机串
		String nonceStr = UUID.randomUUID().toString();

		//1.4 jsapi_ticket 必填，生成签名的H5应用调用企业微信JS接口的临时票据
		String accessToken=null;
		String jsapi_ticket=null ;
		String signature=null;
		try {
			accessToken = getAccessToken(Env.CORP_ID, Env.AGENT_SECRET);
			jsapi_ticket =getJsapiTicket(accessToken);


			//2.进行签名，获取signature
			signature=getSign(jsapi_ticket,nonceStr,timestamp,url); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("url:"+url);
		logger.info("accessToken:"+accessToken);
		logger.info("jsapi_ticket:"+jsapi_ticket);

		logger.info("appId:"+Env.CORP_ID);  //企业微信的cropID
		logger.info("timestamp:"+timestamp);
		logger.info("nonceStr:"+nonceStr);
		logger.info("signature:"+signature);

		//3.返回前端所需发票签名参数
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("appId", Env.CORP_ID);
		jsonObject.put("timestamp", timestamp);
		jsonObject.put("nonceStr",nonceStr );
		jsonObject.put("signature",signature );


		String configValue = jsonObject.toJSONString();
		logger.info("configValue:"+configValue);

		return configValue;  
	}  


	/**
	 * @desc ： 4.1 生成签名的函数 
	 *  
	 * @param ticket jsticket
	 * @param nonceStr 随机串，自己定义
	 * @param timeStamp 生成签名用的时间戳 
	 * @param url 需要进行免登鉴权的页面地址，也就是执行dd.config的页面地址 
	 * @return
	 * @throws Exception String
	 */

	public static String getSign(String jsTicket, String nonceStr, String timeStamp, String url) throws Exception {  
		String plainTex = "jsapi_ticket=" + jsTicket + "&noncestr=" + nonceStr + "&timestamp=" + timeStamp + "&url=" + url;
		System.out.println(plainTex);
		try {  
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(plainTex.getBytes("UTF-8"));
			return byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {  
			throw new Exception(e.getMessage());  
		} catch (UnsupportedEncodingException e) {  
			throw new Exception(e.getMessage());  
		}  
	}  

	/**
	 * @desc ：4.2 将bytes类型的数据转化为16进制类型  
	 *  
	 * @param hash
	 * @return 
	 *   String
	 */
	private static String byteToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", new Object[] { Byte.valueOf(b) });
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	/** 5.获取前端所需发票签名参数
	 * 
	 * @desc ：
	 *(1)将 api_ticket、appid、timestamp、nonceStr、cardType的value值进行字符串的字典序排序。
	 *(2)再将所有参数字符串拼接成一个字符串进行sha1加密，得到cardSign。
	 * 
	 * @return String
	 *  timestamp ：卡券签名时间戳
        nonceStr  : 卡券签名随机串
        signType  : 签名方式，默认'SHA1'
        cardSign  : 卡券签名
	 *   
	 */
	public static String getInvoiceConfig(){ 
		//1.准备好签名参数
		//1.1 api_ticket  授权页ticket
		String apiTicket=null;
		try {
			String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.AGENT_SECRET);
			apiTicket=AuthHelper.getInvoiceTicket(accessToken);
		} catch (Exception e) {
			logger.info("获取电子发票ticket");
			e.printStackTrace();
		}


		//1.2 appid
		String appId=Env.CORP_ID;

		//1.3 timestamp 时间戳
		String timeStamp = System.currentTimeMillis() / 1000 +"";     

		//1.4 nonceStr 随机数
		String nonceStr=UUID.randomUUID().toString();    

		//1.5 cardType
		String cardType="INVOICE";

		//2.获取签名
		String cardSign=null;
		try {

			cardSign = AuthHelper.getCardSign(apiTicket, appId, timeStamp, nonceStr, cardType);

		} catch (Exception e) {
			logger.info("获取发票签名失败");
			e.printStackTrace();
		}

		String signType="SHA1";

		logger.info("apiTicket:"+apiTicket);
		logger.info("appId:"+appId);
		logger.info("timeStamp:"+timeStamp);
		logger.info("nonceStr:"+nonceStr);
		logger.info("cardType:"+cardType);
		logger.info("cardSign:"+cardSign);
		logger.info("signType:"+signType);

		//3.返回前端所需发票签名参数
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("timestamp", timeStamp);
		jsonObject.put("nonceStr",nonceStr );
		jsonObject.put("signType",signType );
		jsonObject.put("cardSign", cardSign);

		String configValue = jsonObject.toJSONString();
		logger.info("configValue:"+configValue);

		return configValue;  
	}  



	/**
	 * @desc ：5.1获取发票签名
	 *  
	 * @param apiTicket  授权页ticket，见InvoiceService
	 * @param appId  
	 * @param timeStamp 时间戳
	 * @param nonceStr  随机串
	 * @param cardType 填入INVOICE
	 * @return
	 * @throws Exception 
	 *   String
	 */
	public static String getCardSign(String apiTicket, String appId, String timeStamp, String nonceStr,String cardType) throws Exception {  
		//1.将 api_ticket、appid、timestamp、nonceStr、cardType的value值进行字符串的字典序排序。
		//注意：是value值值
		String[] array = new String[] { apiTicket, appId, timeStamp, nonceStr,cardType};
		StringBuffer sb = new StringBuffer();
		// 字符串排序
		Arrays.sort(array);
		for (int i = 0; i < 5; i++) {
			sb.append(array[i]);
		}
		String plainTex = sb.toString();

		//String plainTex = apiTicket+appId+cardType+nonceStr+timeStamp;

		System.out.println("plainTex:"+plainTex);
		try {  
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(plainTex.getBytes("UTF-8"));
			return byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {  
			throw new Exception(e.getMessage());  
		} catch (UnsupportedEncodingException e) {  
			throw new Exception(e.getMessage());  
		}  
	}  
}

