<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
</head>
<body>
	<h2>${nickname } 님 환영합니다!</h2>
	<img src="${pic }"/>
	<!-- 데이터 가져오기 -->
	
	<div><a href="javascript:logout()">로그아웃</a></div>
	
	<div>
		<a href="javascript:loaddata()">
			자동차 렌트 업체 데이터 select
		</a>
	</div>
	<div>
		<a href="javascript:insertdata()">
			자동차 렌트 업체 데이터 insert
		</a>
	</div>
	<div>
		<a href="javascript:updatedata()">
			자동차 렌트 업체 데이터 update
		</a>
	</div>
	<div>
		<a href="javascript:deletedata()">
			자동차 렌트 업체 데이터 delete
		</a>
	</div>
	<div><table id="boardList" border = "1"></table></div>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script>
	
	
		function logout(){
			$.ajax({
				url: "logout",
				dataType:"json",
			}).done(function(data){
				console.log("done")
			})
		}
		function loaddata(){
			
			//success를 쓰는게 더 보기가 좋은가??
			$.ajax({
				url: "loaddata",
				dataType:"json",
			}).done(function(data){

				var str = '<tr>';
	            $.each(data, function(idx, val){
	                str += '<td>' + val.name + '</td><td>' + val.addr + '</td>';
	                str += '</tr>';
	           });
	           $("#boardList").append(str);

			})
			
		}
		
		
		function insertdata(){
			
			
			$.ajax({
				url: "insertdata",
				dataType:"json",
			}).done(function(data){
				console.log("done")
			})
			
		}
	
	</script>
</body>

</html>