<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
</head>
<body>
	<h1>WELCOME</h1>
	<div>
			<a href="https://kauth.kakao.com/oauth/authorize?client_id=d0c2de2c1a07232359c7898ac6c66a85&redirect_uri=http://localhost:8080/kakao_login&response_type=code">
				<img src="/img/kakao_login.png"/>
			</a>
	</div>
	<%
    String clientId = "AznusgMnMmQNpXUQ_qnM";//애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost:8080/naver_login", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
    apiURL += "&client_id=" + clientId;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&state=" + state;
    session.setAttribute("state", state);
 %>
  <a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
</body>
</html>


