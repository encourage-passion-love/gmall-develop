package com.xfp.gmall.passport.controller;

import com.alibaba.fastjson.JSON;
import com.xfp.gmall.passport.service.UserService;
import com.xfp.gmall.user.bean.UmsMember;
import com.xfp.gmall.utils.JwtUtil;
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
