package com.ray.weixin.qy.service.invoice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ray.weixin.qy.config.Env;
import com.ray.weixin.qy.util.AuthHelper;


/**@desc  : 发票业务测试类
 * 
 * @author: shirayner
 * @date  : 2017年11月8日 下午6:43:00
 */
public class InvoiceServiceTest {
	private static final Logger logger = LogManager.getLogger(InvoiceServiceTest.class);
	
	
	/**
	 *  @desc ：1.查询电子发票
	 *  
	 * @throws Exception 
	 *   void
	 */
	public void testGetInvoiceInfo() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.AGENT_SECRET);
		String cardId="";
		String encryptCode="";
		InvoiceService.getInvoiceInfo(accessToken, cardId, encryptCode);
	}




}
