package com.xfp.gmall.order.passport.controller;

import com.alibaba.fastjson.JSON;
import com.xfp.gmall.order.passport.service.UserService;
import com.xfp.gmall.order.passport.utils.HttpClientUtil;
import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.order.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {

    @Autowired
    private UserService userService;


    @RequestMapping("/vlogin")
    @ResponseBody
    public String vlogin(String code,HttpServletRequest request) {
        String s3 = "https://api.weibo.com/oauth2/access_token";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", "w3232323232");
        paramMap.put("client_secret", "dsuidsdius78ds8d8s78");
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("redirect_uri", "http://localhost:8085/vlogin");
        paramMap.put("code", code);
        String doPost = HttpClientUtil.doPost(s3, paramMap);
        Map parseObject = JSON.parseObject(doPost, Map.class);
        String access_token = (String) parseObject.get("access_token");
        String uid = (String) parseObject.get("uid");
        String s4 = "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid;
        String userInfo = HttpClientUtil.doGet(s4);
        Map userInfoMap = JSON.parseObject(userInfo, Map.class);
        UmsMember umsMember = new UmsMember();
        umsMember.setSourceType(2);
        umsMember.setAccessCode(code);
        umsMember.setAccessToken(access_token);
        umsMember.setSourceUid(Long.parseLong((String) userInfoMap.get("id")));
        umsMember.setNickname((String) userInfoMap.get("screen_name"));
        umsMember.setCity((String) userInfoMap.get("location"));
        umsMember.setGender(Integer.valueOf((String) userInfoMap.get("gender")));
        userService.addOAuthUser(umsMember);
        //去数据库查一遍  如有有说明以前进行过社交登录
        //如果没有的话说明是新的社交登录  需要将其信息添加到数据库里面去
        //使用memberId和nickname进行token的设置
        String memberId = umsMember.getId();
        String nickname = umsMember.getNickname();
        Map<String, Object> map = new HashMap<>();
        String token = "";
        if (umsMember != null) {
            map.put("memberId", memberId);
            map.put("nickname", nickname);
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip)) {
                ip = request.getRemoteAddr();
            }
            token = JwtUtil.encode("gmall2015", map, ip);
        }//携带token重定向到当前的地址
        return "redirect:http://localhost:8083/index?token="+token;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(UmsMember umsMember, HttpServletRequest request){
        //调用用户服务验明用户名和密码
        Map<String,Object> map=new HashMap<>();
        String token="";
        UmsMember member = userService.login(umsMember);
        if(member!=null){
            String id = member.getId();
            String nickname = member.getNickname();
            map.put("memberId",id);
            map.put("nickname",nickname);
            String ip = request.getHeader("x-forwarded-for");
            if(StringUtils.isBlank(ip)){
              ip = request.getRemoteAddr();
            }
            token  = JwtUtil.encode("gmall2015", map, ip);

        }else {
            token="fail";
        }
        return "token";
    }
    @RequestMapping("/verify")
    @ResponseBody
    public String verify(String token,String currentIp){
        //调用用户服务验明用户名和密码
        Map<String,Object> map=new HashMap<>();
        Map<String, Object> gmall2015 = JwtUtil.decode(token, "gmall2015", currentIp);
        if(gmall2015!=null){
            String memberId = (String) gmall2015.get("memberId");
            String nickname = (String) gmall2015.get("nickname");
            map.put("memberId",memberId);map.put("nickname",nickname);
            map.put("status","success");
        }else {
            map.put("status","fail");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("/index")
    public String index(String ReturnUrl, ModelMap map){
        map.put("ReturnUrl",ReturnUrl);
        return "index";
    }
}
