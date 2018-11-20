package com.ray.weixin.qy.service.menu;

import com.alibaba.fastjson.JSONObject;
import com.ray.weixin.qy.config.Env;
import com.ray.weixin.qy.model.menu.*;
import com.ray.weixin.qy.util.AuthHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

/**@desc  : 菜单测试类
 *
 * @author: shirayner
 * @date  : 2017年10月31日 上午9:53:00
 */
public class MenuServiceTest {

    private static final Logger logger = LogManager.getLogger(MenuServiceTest.class);


    /**
     * @desc ：1. 创建菜单
     *
     * @throws Exception void
     */
    @Test
    public void testCreateMenu() throws Exception {
        //1.准备好请求参数
        String accessToken= AuthHelper.getAccessToken();
        Menu menu=getMenu_1();
        //2.调用接口，执行请求
        MenuService.createMenu(menu, accessToken);

    }

    /**
     * @desc ：2.查询菜单数据
     *
     * @throws Exception void
     */
    @Test
    public void testGetMenu() throws Exception {
        //1.准备好请求参数
        String accessToken=AuthHelper.getAccessToken();

        //2.调用接口，执行请求
        JSONObject jsonObject=MenuService.getMenu(accessToken);
        logger.info("菜单数据："+jsonObject.toJSONString());
    }

    /**
     * @desc ：3.删除菜单
     *
     * @throws Exception void
     */
    @Test
    public void testDeleteMenu() throws Exception {
        //1.准备好请求参数
        String accessToken=AuthHelper.getAccessToken();

        //2.调用接口，执行请求
        MenuService.deleteMenu(accessToken);

    }


    /**
     * @desc ：辅助1.组装菜单数据
     *
     * @return Menu
     */
    private static Menu getMenu() {
        //1.一级菜单子项_1
        ViewButton btn11 = new ViewButton();
        btn11.setName("JSSDK多图上传");
        btn11.setType("view");
        btn11.setUrl(Env.BASE_URL+"/JSSDKUploadPics.jsp");

        //1.一级菜单子项_2
        ViewButton btn21 = new ViewButton();
        btn21.setName("JSSDK测试（全）");
        btn21.setType("view");
        btn21.setUrl(Env.BASE_URL+"/jsapiTicktAll.jsp");

        ViewButton btn22 = new ViewButton();
        btn22.setName("PC端网页授权");
        btn22.setType("view");
        btn22.setUrl("https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=ww92f5da92bb24696e&agentid=1000002&redirect_uri=http%3A%2F%2Fzvuntx.natappfree.cc%2FWeiXin_QiYe_Demo%2FPCAuthorization.jsp&state=state");

        ViewButton btn23 = new ViewButton();
        btn23.setName("移动端网页授权");
        btn23.setType("view");
        btn23.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww92f5da92bb24696e&redirect_uri=http%3A%2F%2Fzvuntx.natappfree.cc%2FWeiXin_QiYe_Demo%2FMTAuthorization.jsp&response_type=code&scope=snsapi_privateinfo&agentid=1000002&state=hec#wechat_redirect");


        //1.一级菜单子项_3
        CommonButton btn31 = new CommonButton();
        btn31.setName("Q友圈");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("幽默笑话");
        btn32.setType("click");
        btn32.setKey("32");


        //2.一级菜单
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("完成");
        mainBtn1.setSub_button(new Button[] { btn11 });

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("测试");
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多");
        mainBtn3.setSub_button(new Button[] { btn31, btn32 });

        /**
         * 这是企业号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;

    }
    /**
     * @desc ：辅助1.组装菜单数据
     *
     * @return Menu
     */
    private static Menu getMenu_hec() {

        //一级菜单
        //1.一级菜单子项_1
        ViewButton btn11 = new ViewButton();
        btn11.setName("JSSDK多图上传");
        btn11.setType("view");
        btn11.setUrl(Env.BASE_URL+"/JSSDKUploadPics.jsp");

        Menu menu = new Menu();
        menu.setButton(new Button[] { btn11 });

        return menu;

    }

    private static Menu getMenu_1() {
/*
		ViewButton btn11 = new ViewButton();
		btn11.setName("添加报销单");
		btn11.setType("view");
		btn11.setUrl("http://5nffqn.natappfree.cc/WeiXin_QiYe_Demo/uploadExpenseAccaout.jsp");
*/

        ViewButton btn11 = new ViewButton();
        btn11.setName("JSSDK多图上传");
        btn11.setType("view");
        btn11.setUrl("http://zvuntx.natappfree.cc/WeiXin_QiYe_Demo/JSSDKUploadPics.jsp");

        ViewButton btn21 = new ViewButton();
        btn21.setName("JSSDK测试（全）");
        btn21.setType("view");
        btn21.setUrl("http://zvuntx.natappfree.cc/WeiXin_QiYe_Demo/jsapiTicktAll.jsp");

        ViewButton btn22 = new ViewButton();
        btn22.setName("PC端网页授权");
        btn22.setType("view");
        btn22.setUrl("https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=ww92f5da92bb24696e&agentid=1000002&redirect_uri=http%3A%2F%2Fzvuntx.natappfree.cc%2FWeiXin_QiYe_Demo%2FPCAuthorization.jsp&state=state");

        ViewButton btn23 = new ViewButton();
        btn23.setName("移动端网页授权");
        btn23.setType("view");
        btn23.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww92f5da92bb24696e&redirect_uri=http%3A%2F%2Fzvuntx.natappfree.cc%2FWeiXin_QiYe_Demo%2FMTAuthorization.jsp&response_type=code&scope=snsapi_privateinfo&agentid=1000002&state=hec#wechat_redirect");


        CommonButton btn12 = new CommonButton();
        btn12.setName("扫一扫");
        btn12.setType("click");
        btn12.setKey("12");



        CommonButton btn13 = new CommonButton();
        btn13.setName("翻译功能");
        btn13.setType("click");
        btn13.setKey("13");

        ViewButton btn14 = new ViewButton();
        btn14.setName("上传图片");
        btn14.setType("view");
        btn14.setUrl("http://5nffqn.natappfree.cc/WeiXin_SanFenBaiXue/uploadimg.jsp");



        ViewButton btn15 = new ViewButton();
        btn15.setName("上传图片2");
        btn15.setType("view");
        btn15.setUrl("http://5nffqn.natappfree.cc/WeiXin_SanFenBaiXue/index2.jsp");


        CommonButton btn24 = new CommonButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn25 = new CommonButton();
        btn25.setName("聊天唠嗑");
        btn25.setType("click");
        btn25.setKey("25");

        CommonButton btn31 = new CommonButton();
        btn31.setName("Q友圈");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn33 = new CommonButton();
        btn33.setName("幽默笑话");
        btn33.setType("click");
        btn33.setKey("33");

        CommonButton btn34 = new CommonButton();
        btn34.setName("用户反馈");
        btn34.setType("click");
        btn34.setKey("34");

        CommonButton btn35 = new CommonButton();
        btn35.setName("关于我们");
        btn35.setType("click");
        btn35.setKey("35");

        ViewButton btn32 = new ViewButton();
        btn32.setName("周边搜索");
        btn32.setType("view");
        btn32.setUrl("http://liufeng.gotoip2.com/xiaoqrobot/help.jsp");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("正在做功能");
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14, btn15 });

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("测试");
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多");
        mainBtn3.setSub_button(new Button[] { btn31, btn33, btn34, btn35, btn32 });

        /**
         * 这是企业号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }


}
