package com.ray.weixin.qy.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.ray.weixin.qy.config.Env;
import com.ray.weixin.qy.service.message.reply.ReplyMessageService;

/**@desc  : 
 * 
 * @author: shirayner
 * @date  : 2017年11月8日 下午4:30:43
 */
public class AuthHelperTest {
	private static final Logger log = LogManager.getLogger(AuthHelperTest.class);

	@Test
	public void testGetAccessToken() throws Exception {
		AuthHelper.getAccessToken(Env.CORP_ID, Env.AGENT_SECRET);
		
	}
	
	@Test
	public void testlog() throws Exception {
		log.info("assafe f");
		
	}
	
	
/**
 * @desc ：3.获取电子发票ticket
 *  
 * @throws Exception 
 *   void
 */
	@Test
	public void testInvoiceTicket() throws Exception {
		String accessToken=AuthHelper.getAccessToken(Env.CORP_ID, Env.AGENT_SECRET);
		String ticket=AuthHelper.getInvoiceTicket(accessToken);
		
		log.info("tikect:"+ticket);
	}
	
	
}
