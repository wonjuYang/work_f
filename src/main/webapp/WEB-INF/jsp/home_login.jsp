<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>HELLO</title>
</head>
<body>
	<h1>HELLO</h1>
    <c:if test="${sessionScope.dto ne null}">
        <h2>${dto.name } 님 환영합니다!</h2>       
		<img src="${dto.pic }"/>
    </c:if>
	
	<div><a href="javascript:logout()">로그아웃</a></div>
	
	<div>
		<a href="/select_data">
			자동차 렌트 업체 데이터 select
		</a>
	</div>
	<div>
		<a href="javascript:insertdata()">
			자동차 렌트 업체 데이터 insert
		</a>
	</div>
	<div>
		<a href="javascript:deletedata()">
			자동차 렌트 업체 데이터 delete
		</a>
	</div>
	
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script>
	
	
		function logout(){

			$.ajax({
				url: "logout",
			}).done(function(data){
				console.log("done")
			}).fail(function(error){
				console.log(error);
			})
		}
		
	
		function deletedata(){
			
			$.ajax({
				url: "deletedata",
			}).done(function(data){
				console.log("delete done")
			}).fail(function(error){
				console.log(error);
			})
		}
		
		
		function insertdata(){
			
			
			$.ajax({
				url: "insertdata",
			}).done(function(data){
				console.log("insert done")
			}).fail(function(error){
				console.log(error);
			})
			
		}
	
	</script>
</body>

</html>