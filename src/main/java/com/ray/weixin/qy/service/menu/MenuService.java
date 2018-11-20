package com.ray.weixin.qy.service.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.qy.config.Env;
import com.ray.weixin.qy.model.menu.Menu;
import com.ray.weixin.qy.util.HttpHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**@desc  :  自定义菜单业务类
 * 
 * @author: shirayner
 * @date  : 2017年10月31日 上午9:40:05
 */
public class MenuService {
	private static final Logger logger = LogManager.getLogger(MenuService.class);

	//1.菜单创建（POST） 限100（次/天）  
	public static final String CREATE_MENU_URL = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";
	//2.查询菜单数据
	public static final String GET_MENU_URL = "https://qyapi.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN&agentid=AGENTID";
    //3.删除菜单
	public static final String DELETE_MENU_URL = "https://qyapi.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN&agentid=AGENTID";


	/**
	 * @desc ：1.创建菜单 
	 *  
	 * @param menu  菜单实例 
	 * @param accessToken 有效凭证
	 * @throws Exception void
	 */
	public static void createMenu(Menu menu, String accessToken) throws Exception {

		//1.准备POST请求参数
		Object data=JSON.toJSON(menu);
		logger.info(data);

		//2.拼装创建菜单的url  
		String url=CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken).replace("AGENTID", String.valueOf(Env.AGENT_ID));

		//3.发起POST请求，获取返回结果
		JSONObject jsonObject= HttpHelper.doPost(url, data);
		logger.info("jsonObject:"+jsonObject.toString());


		if (null != jsonObject) {  

			//5.错误消息处理
			if (0 != jsonObject.getInteger("errcode")) {  

				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			
			} else {
				logger.info("菜单创建成功！");
			} 
		}   




	}  

	/**
	 * @desc ：2.查询菜单数据
	 *  
	 * @param accessToken 有效凭证
	 * @return
	 * @throws Exception JSONObject
	 */
	public static JSONObject getMenu(String accessToken) throws Exception {  
		//1.获取请求url
		String url=GET_MENU_URL.replace("ACCESS_TOKEN", accessToken).replace("AGENTID", String.valueOf(Env.AGENT_ID));

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toString());

		//3.解析结果，获取菜单数据
		JSONObject returnJsonObject=null;
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			//5.成功获取菜单数据
			} else {
				returnJsonObject= jsonObject;
			} 
		}   
		
		return returnJsonObject;
	}  
	
	/**
	 * @desc ： 3.删除菜单
	 *  
	 * @param accessToken  有效凭证
	 * @throws Exception void
	 */
	public static void deleteMenu(String accessToken) throws Exception {  
		//1.获取请求url
		String url=DELETE_MENU_URL.replace("ACCESS_TOKEN", accessToken).replace("AGENTID", String.valueOf(Env.AGENT_ID));

		//2.发起GET请求，获取返回结果
		JSONObject jsonObject=HttpHelper.doGet(url);
		logger.info("jsonObject:"+jsonObject.toString());

		//3.解析结果
		if (null != jsonObject) {  

			//4.错误消息处理
			if (jsonObject.getInteger("errcode")!=null && 0 != jsonObject.getInteger("errcode")) {  
				int errCode = jsonObject.getInteger("errcode");
				String errMsg = jsonObject.getString("errmsg");
				throw new Exception("error code:"+errCode+", error message:"+errMsg); 
			
			//5.成功删除菜单
			} else {
				logger.info("菜单删除成功！");
			} 
		}   
		
	}  
}
