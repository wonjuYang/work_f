package com.wonju.work.util;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.github.scribejava.apis.NaverApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

public class NaverLoginUtil {
	private final static String CLIENT_ID = "AznusgMnMmQNpXUQ_qnM";
	private final static String CLIENT_SECRET = "uYtlIKvxTS";
	private final static String REDIRECT_URI = "http://localhost:8080/naver_login";
	private final static String SESSION_STATE = "oauth_state";
	
	/* 프로필 조회 API URL */
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
	
	public String getAuthorizationUrl(HttpSession session) {
		
		String state = generateRandomString();
		
		setSession(session, state);
		
		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
											.apiSecret(CLIENT_SECRET)
											.callback(REDIRECT_URI)
											.build(NaverApi.instance());
			
		
		return oauthService.getAuthorizationUrl();
	}
	
	public static OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException, InterruptedException, ExecutionException{
		

			OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
												.apiSecret(CLIENT_SECRET)
												.callback(REDIRECT_URI)
												.build(NaverApi.instance());
			
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			
			return accessToken;

	
	}
	
	
	//난수 생성
	private static String generateRandomString() {
		return UUID.randomUUID().toString();
	}
	
	//세션 저장
	private static void setSession(HttpSession session,String state){
		session.setAttribute(SESSION_STATE, state);
	}
	
	//세션 불러오기
	private static String getSession(HttpSession session){
		return (String) session.getAttribute(SESSION_STATE);
	}
	
	
	public static String getUserProfile(OAuth2AccessToken oauthToken) throws IOException, InterruptedException, ExecutionException{
		
		String state = generateRandomString();
		
		OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
											.apiSecret(CLIENT_SECRET)
											.callback(REDIRECT_URI)
											.build(NaverApi.instance());
		
		
		
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL);
		
		oauthService.signRequest(oauthToken, request);
		
		
		
		try(Response response = oauthService.execute(request)) {
			System.out.println("Got it! Lets see what we found...");
            System.out.println(response.getCode());
            System.out.println(response.getBody());
            return response.getBody();
		}
		
		
		
		
		
	}
	
	
	

	
}
