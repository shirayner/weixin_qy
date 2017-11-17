package com.ray.weixin.qy.service.invoice;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.qy.util.HttpHelper;

/**@desc  : 微信电子发票接口
 * 
 * @author: shirayner
 * @date  : 2017年11月3日 下午4:31:45
 */
public class InvoiceService {
	private static final Logger logger = LogManager.getLogger(InvoiceService.class);

	//1.查询电子发票
	private static final String GET_INVOICE_INFO_URL="https://qyapi.weixin.qq.com/cgi-bin/card/invoice/reimburse/getinvoiceinfo?access_token=ACCESS_TOKEN";

	//2.批量查询电子发票
	private static final String LIST_INVOICE_INFO_URL="https://qyapi.weixin.qq.com/cgi-bin/card/invoice/reimburse/getinvoiceinfobatch?access_token=ACCESS_TOKEN";


	/**
	 * @desc ：1.查询电子发票
	 *  
	 * @param accessToken 接口调用凭证
	 * @param cardId 发票id
	 * @param encryptCode 加密code
	 * @return
	 * 
	 * JSONObject  发票详细信息
	 * 
	 * @throws Exception 
	 *   
	 */
	public static JSONObject getInvoiceInfo(String accessToken, String cardId,String encryptCode) throws Exception {
		//1.封装JSON请求参数
		JSONObject postData=new JSONObject();
		postData.put("card_id", cardId);
		postData.put("encrypt_code", encryptCode);

		//2.准备好请求url
		String url=GET_INVOICE_INFO_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发送请求
		JSONObject jsonObject=HttpHelper.doPost(url, postData);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//4.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				returnJsonObject=jsonObject;
			}
		}
		return returnJsonObject;
	}


	/**
	 * @desc ：
	 *  
	 * @param accessToken  接口调用凭证
	 * @param invoiceListStr_escape   
	 * invoiceListStr_escape="\"[{\\\"card_id\\\":\\\"pEVWfuPFrMuU3fkx5iWQeSBSefTg\\\",\\\"encrypt_code\\\":\\\"O\\\\/mPnGTpBu22a1szmK2ogzhFPBh9eYzv2p70L8yzyymmmDDmLBJfmhaRYC2+Ac1QqztRDyQ9SVEU5gdknT+Dks8RPfvOVTVRVykyQ8pe+bA9vWT04nL0w\\\\/YDmtAnfZ4+Rtvt55ZfNt5zQuiukRmETg==\\\",\\\"app_id\\\":\\\"wxbc0799a47795854a\\\"}]\"";
	 * 
	 * @return
	 * @throws Exception 
	 *   JSONObject
	 *   返回发票的详细信息列表
	 *   
	 */
	public static JSONObject listInvoiceInfo(String accessToken,String invoiceListStr_escape) throws Exception {
		//1.准备好post请求参数
		//反转义一层
		String invoiceListStr_unEscape =StringEscapeUtils.unescapeJava(invoiceListStr_escape);
		System.out.println("invoiceListStr_unEscape:"+invoiceListStr_unEscape);

		//得到想要的结果
		String invoiceListStr=invoiceListStr_unEscape.substring(1,invoiceListStr_unEscape.length() - 1);

		//去掉每个发票中的app_id
		JSONArray invoiceJsonArrayTemp=JSON.parseArray(invoiceListStr);

		//将去除app_id后的结果存在invoiceJsonArrayResult中
		JSONArray invoiceJsonArrayResult=new JSONArray();
		for(int i=0;i<invoiceJsonArrayTemp.size();i++) {

			JSONObject OneInvoice=JSON.parseObject(invoiceJsonArrayTemp.get(i).toString());
			OneInvoice.remove("app_id");
			System.out.println("OneInvoice:"+OneInvoice);

			invoiceJsonArrayResult.add(OneInvoice);
		}

		JSONObject invoiceList=new JSONObject();
		invoiceList.put("item_list", invoiceJsonArrayResult);
		System.out.println("------"+invoiceList.toJSONString());
		

		//2.准备好请求url
		String url=LIST_INVOICE_INFO_URL.replace("ACCESS_TOKEN", accessToken);

		//3.发送请求
		JSONObject jsonObject=HttpHelper.doPost(url, invoiceList);
		logger.info("jsonObject:"+jsonObject.toJSONString());

		//4.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  
			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
				//5.成功获取菜单数据
			} else {
				returnJsonObject=jsonObject;
			}
		}
		return returnJsonObject;
	}












}
