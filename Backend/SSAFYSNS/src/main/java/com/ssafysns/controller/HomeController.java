package com.ssafysns.controller;
 
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafysns.util.KakaoAPI;
 
@Controller
public class HomeController {
	
	@Autowired
	private KakaoAPI kakao;
	
		
    @RequestMapping(value="/")
    public String index() {
        
        return "index";
    }
    
//    @RequestMapping(value="/KakaoLogin")
//    public String kakaoLogin(@RequestParam("code") String code) {
//    	System.out.println("code : " + code);
//    	String access_Token = kakao.getAccessToken(code);
//        System.out.println("Controller access token : " + access_Token);
//        return "index";
//    }
    
    
    @RequestMapping(value="/KakaoLogin")
    public String login(@RequestParam("code") String code, HttpSession session) {
    	System.out.println("====================login=====================");
        String access_Token = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);
        System.out.println("====================login=====================");
        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
        
        return "index";
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
    	
    	System.out.println("====================logout=====================");
        kakao.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        System.out.println("====================logout=====================");
        return "index";
    }


}