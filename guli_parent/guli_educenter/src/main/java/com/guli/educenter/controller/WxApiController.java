package com.guli.educenter.controller;

import com.google.gson.Gson;
import com.guli.educenter.entity.UcenterMember;
import com.guli.educenter.service.UcenterMemberService;
import com.guli.educenter.utils.ConstantPropertiesUtil;
import com.guli.educenter.utils.HttpClientUtils;
import com.guli.educenter.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    UcenterMemberService memberService;

    //回调方法
    //state 传递的前置域名的名称
    //code 临时票据，类似于手机验证码
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {

        //1 得到授权临时票据code
        System.out.println("code = " + code);
        System.out.println("state = " + state);
        try {
            //2 请求地址（微信固定的地址），拼接参数，返回access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接参数
            baseAccessTokenUrl =
                    String.format(baseAccessTokenUrl,ConstantPropertiesUtil.WX_OPEN_APP_ID,ConstantPropertiesUtil.WX_OPEN_APP_SECRET,code);
            //使用httpclient请求拼接之后的地址
            String resultAccess = HttpClientUtils.get(baseAccessTokenUrl);
            //从字符串中获取access_token和openid
            //把字符串转换map集合，使用gson工具
            Gson gson = new Gson();
            HashMap map = gson.fromJson(resultAccess, HashMap.class);
            //从map集合获取数据
            String access_token = (String)map.get("access_token"); //获取凭证
            String openid = (String)map.get("openid"); //微信唯一标识
            //判断表是否存在相同的微信信息，如果不存在添加
            //根据openid进行判断
            UcenterMember member = memberService.getUserByOpenId(openid);
            if(member == null) {//表没有相同微信信息
            //3 拿着access_token和openid，再去请求一个固定的地址，获取扫描人信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //拼接参数
            baseUserInfoUrl = String.format(baseUserInfoUrl,access_token,openid);
            //请求这个地址
            String resultUserInfo = HttpClientUtils.get(baseUserInfoUrl);
            //System.out.println("**********resultUserInfo: "+resultUserInfo);
            //获取微信扫描人昵称和头像
            HashMap userInfoMap = gson.fromJson(resultUserInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");
            String headimgurl = (String)userInfoMap.get("headimgurl");

            //把获取到扫描人信息添加到数据库表里面
            //添加到数据库中
            member = new UcenterMember();
            member.setOpenid(openid);
            member.setNickname(nickname);
            member.setAvatar(headimgurl);
            memberService.save(member);
            }
            //生成token字符串
            String token = JwtUtils.geneJsonWebToken(member);
            //跳转首页面中
            return "redirect:http://localhost:3000?token="+token;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //生成微信扫描二维码
    @GetMapping("login")
    public String genQrConnect() {
        try {
            //重定向到微信提供固定的地址，拼接参数
            // 微信开放平台授权baseUrl
            //%s 相当于sql语句？，表示占位符
            String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                    "?appid=%s" +
                    "&redirect_uri=%s" +
                    "&response_type=code" +
                    "&scope=snsapi_login" +
                    "&state=%s" +
                    "#wechat_redirect";

            //对redirect_url进行urlEncode对处理
            String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");

            //内网穿透前置域名
            String state = "atyh";
            //把参数拼接到路径里面
            String qrcodeUrl = String.format(
                    baseUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    redirectUrl,
                    state);
            //重定向到生成路径里面
            return "redirect:"+qrcodeUrl;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
