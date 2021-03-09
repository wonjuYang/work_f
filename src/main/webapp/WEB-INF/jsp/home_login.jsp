<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
</head>
<body>
    <c:if test="${sessionScope.dto ne null}">
        <h1>로그인 성공입니다</h1>
        <h2>${nickname } 님 환영합니다!</h2>       
		<img src="${pic }"/>

        <input type="button" value="로그아웃" onclick="location.href='/logout'">
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
		<a href="javascript:updatedata()">
			자동차 렌트 업체 데이터 update
		</a>
	</div>
	<div>
		<a href="javascript:deletedata()">
			자동차 렌트 업체 데이터 delete
		</a>
	</div>
	
	
	<div>${p_code }</div>
	
	
	<c:if test="${lentCarList ne null }">
		<c:forEach var="vo" items="${requestScope.lentCarList }" varStatus="st">
		<tr>
			<td>${rowTotal - (cPage*blockList) -st.index }</td>
			<td>${vo.name }</td>
			<td>${vo.addr }</td>
			<td>
			</td>
		</tr>
		</c:forEach>
	</c:if>
	<c:if test="${lentCarList eq null }">
		<tr>
			<td colspan="5" class="empty">
				등록된 게시물이 없습니다.
			</td>
		</tr>
	</c:if>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script>
	
	
		function logout(){
			console.log("로그아웃")
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
				console.log(data)
				if(data.length == 0){
					$("#boardList").empty();
					var str = '<tr>';
					str += "<td>데이터가 없습니다</td>"
					str += '</tr>';
					$("#boardList").append(str);
				}else{
					var str = '<tr>';
		            $.each(data, function(idx, val){
		                str += '<td>' + val.name + '</td><td>' + val.addr + '</td>';
		                str += '</tr>';
		           });
		           $("#boardList").append(str);
					
				}

				
	           
	           console.log("load done")

			})
			
		}
		
		function deletedata(){
			
			$.ajax({
				url: "deletedata",
				dataType:"json",
			}).done(function(data){
				console.log("???")
				console.log(data);
				console.log("delete done")
				//여기서 다시 select를 호출하기
				$("#boardList").empty();
				loaddata();
			})
		}
		
		
		function insertdata(){
			
			
			$.ajax({
				url: "insertdata",
				dataType:"json",
			}).done(function(data){
				$("#boardList").empty();
				console.log("insert done")
			})
			
		}
	
	</script>
</body>

</html>