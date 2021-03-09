<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
	<link rel="stylesheet" href=/css/main.css/>
	<link rel="stylesheet" href=/css/util.css/>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<table summary="게시판 목록">
			<caption>경기도 자동차 대여업체</caption>
			<thead>
				<tr class="title">
					<th class="no">번호</th>
					<th class="subject">사업체명</th>
					<th class="writer">주소</th>
				</tr>
			</thead>
			<c:if test="${lentCarList ne null }">
				<c:forEach var="vo" items="${requestScope.lentCarList }" varStatus="st">
				<tr>
					<td>${rowTotal - ((nowPage-1)*blockList) -st.index }</td>
					<td>${vo.name }</td>
					<td>${vo.addr }</td>
					<td></td>
				</tr>
				</c:forEach>
				<tr><td colspan="3">${p_code }</td></tr>
			</c:if>
			<c:if test="${empty lentCarList}">
				<tr>
					<td colspan="5" class="empty">
						등록된 게시물이 없습니다.
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		<form method="post" action="search_data">
			<select id="subject" name="subject">
			  <option value="name">사업체명</option>
			  <option value="addr">주소</option>
			</select>
			<div class="input-group">
		      <input type="text" class="form-control" placeholder="Search for..." name="keyword">
		      <span class="input-group-btn">
		        <button class="btn btn-default" type="submit">찾기!</button>
		      </span>
		    </div><!-- /input-group -->
		</form>
	</div>
	

</body>
</html>