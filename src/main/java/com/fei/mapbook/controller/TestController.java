package com.fei.mapbook.controller;

import com.alibaba.fastjson.JSONObject;
import com.fei.mapbook.dao.mapper.StudentPOMapper;
import com.fei.mapbook.dao.po.StudentPO;
import com.fei.mapbook.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
public class TestController {

    @Autowired
    private StudentPOMapper studentPOMapper;
    //测试mybatis完毕
    @GetMapping("selectTest")
    public Object selectTest(){
        StudentPO studentPO = studentPOMapper.selectByPrimaryKey(1);
        return studentPO;
    }

    /**
     * Tea微信登录接口   微信授权接口测试完毕 热热热
     */
    @RequestMapping("wx_login")
    public void wxLogin(HttpServletResponse response) throws IOException {
        //域名(暂时写死的)
        String sym = "http://cb6yxz.natappfree.cc";
        //这里是回调的url
        String redirect_uri = URLEncoder.encode(sym+"/callBack", "UTF-8");
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                "appid=APPID" +
                "&redirect_uri=REDIRECT_URI"+
                "&response_type=code" +
                "&scope=SCOPE" +
                "&state=123#wechat_redirect";
        response.sendRedirect(url.replace("APPID","wxf627fe1cc691375d").replace("REDIRECT_URI",redirect_uri).replace("SCOPE","snsapi_userinfo"));
    }

    /**
     * Tea微信授权成功的回调函数
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/callBack")
    protected void deGet(HttpServletRequest request, HttpServletResponse response)throws Exception {
        //获取回调地址中的code
        String code = request.getParameter("code");
        //拼接url
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + "wxf627fe1cc691375d" + "&secret="
                + "cd214e8ad86db6a5e81092db1632bb4b" + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = WeiXinUtil.doGetJson(url);
        //1.获取微信用户的openid
        String openid = jsonObject.getString("openid");
        //2.获取获取access_token
        String access_token = jsonObject.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid
                + "&lang=zh_CN";
        //3.获取微信用户信息
        JSONObject userInfo = WeiXinUtil.doGetJson(infoUrl);
        //至此拿到了微信用户的所有信息,剩下的就是业务逻辑处理部分了
        //保存openid和access_token到session
        request.getSession().setAttribute("openid", openid);
        request.getSession().setAttribute("access_token", access_token);
        System.out.println(openid);
        //去数据库查询此微信是否绑定过手机
//        UserVo user = userService.queryByOpenId(openid);
//        String mobile=user==null?"":user.getMobile();
//
//        if(null == mobile || "".equals(mobile)){
//            //如果无手机信息,则跳转手机绑定页面
//            response.sendRedirect("/front/register.html");
//        }else{
//            //否则直接跳转首页
//            response.sendRedirect("/front/index.html");
//        }
    }

}
