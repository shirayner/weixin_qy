package com.ray.weixin.qy.service.message.notice;


import com.ray.weixin.qy.util.AuthHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : shirayner
 * @date : 2018/3/8
 * @time : 11:11
 * @desc :
 **/
public class NoticeServiceTest {
    private static final Logger logger = LogManager.getLogger(NoticeServiceTest.class);

    String accessToken=null;
    String userId=null;

    @Before
    public void init() throws Exception {
        accessToken= AuthHelper.getAccessToken();
        userId="ShiRui";

    }


    /**@desc ：1.发送文本消息
     */
    @Test
    public void testSendTextNotice() throws Exception {
        String content="你的快递已到，请携带工卡前往邮件中心领取。\n出发前可查看<a href=\"http://work.weixin.qq.com\">邮件中心视频实况</a>，聪明避开排队。";

        NoticeService ns=new NoticeService();
        ns.sendTextNotice( accessToken, userId, content);
    }



    /**2.发送文本卡片消息
     *
     * @throws Exception
     */
    @Test
    public void testSendTextCardNotice() throws Exception {
        String title="<div class=\"highlight\">代办事项</div>";
        String description="<div class=\"gray\">2018年3月14日</div> <div class=\"normal\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\"highlight\">请于2016年10月10日前联系行政同事领取</div>";
        String url="www.baidu.com";

        NoticeService ns=new NoticeService();
        ns.sendTextCardNotice(accessToken,userId,title,description,url);
    }


    /**3.发送语音消息
     *
     * @throws Exception
     */
    @Test
    public void testSendImgNotice() throws Exception {
        String mediaId="7n_ysJvJbAhd0FhCWXWlkb1p3J8a3EA7KsZyLFu1ufBvnttrr-Qw2dmXPyiArfnu";

        NoticeService ns=new NoticeService();
        ns.sendImgNotice(accessToken,userId,mediaId);
    }
}
