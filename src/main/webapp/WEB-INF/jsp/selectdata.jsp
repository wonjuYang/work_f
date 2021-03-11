<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
	<link rel="stylesheet" href=/css/main.css/>
	<link rel="stylesheet" href=/css/util.css/>
	
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<div id="map" style="width:800px;height:500px;"></div>
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
				<tr class="data_name" data-lat=${vo.lat} data-logt=${vo.logt}>
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
						등록된 업체가 없습니다.
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		<form method="post" action="search_data">
			<select id="subject" name="subject" class="form-select" aria-label="Default select example">
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
		<div style="height:500px;"></div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1b9cbd980b644753fb8268b9293d662c"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>	
	<script>
	$(function(){

		//여러개의 객체를 담을 준비
		var positions = new Array();
		
		
		
		//넘어온 객체들을 담기 (1~10개)
		<c:forEach var="vo" items="${requestScope.lentCarList }">
			var json = new Object();
			json.title="${vo.name}";
			json.latlng=new kakao.maps.LatLng(${vo.lat}, ${vo.logt});
			positions.push(json);
		</c:forEach>
		
		
		
		//만약 0개라면 남산타워를 보여주기
		if(positions.length == 0){
			var json = new Object();
			json.title="서울";
			json.latlng=new kakao.maps.LatLng(37.55133965870465, 126.98823061667736);
			positions.push(json);

		}


			

		//지도를 나타낼 div
		var container = document.getElementById('map');
		
		
		//처음 지도를 그렸을 때 중심 좌표와 확대 정도 여기서 중심 좌표는 첫번째 객체의 좌표
		var options = {
			center: new kakao.maps.LatLng(positions[0].latlng.Ma, positions[0].latlng.La),
			level: 3
		};
		
		// 옵션대로 그리기
		var map = new kakao.maps.Map(container, options);
		
		// 마커 이미지의 이미지 주소입니다
		var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
		    
		
		// 여러개의 마커 만들기
		for (var i = 0; i < positions.length; i ++) {
		    
		    // 마커 이미지의 이미지 크기 입니다
		    var imageSize = new kakao.maps.Size(24, 35); 
		    
		    // 마커 이미지를 생성합니다    
		    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
		    
		    // 마커를 생성합니다
		    var marker = new kakao.maps.Marker({
		        map: map, // 마커를 표시할 지도
		        position: positions[i].latlng, // 마커를 표시할 위치
		        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
		        image : markerImage // 마커 이미지 
		    });
		    
		    var iwContent = '<div style="padding:5px;">'+positions[i].title+' <br><a href="https://map.kakao.com/link/map/'+positions[i].title+','+positions[i].latlng.Ma+','+positions[i].latlng.La+'" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
		    	iwPosition = new kakao.maps.LatLng(positions[i].latlng.Ma, positions[i].latlng.La); //인포윈도우 표시 위치입니다

			// 인포윈도우를 생성합니다
			var infowindow = new kakao.maps.InfoWindow({
			    position : iwPosition, 
			    content : iwContent 
			});
		    
			// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
			//infowindow.open(map); 
			    
			    
			    
			    
		}
		
		
		
		
		
		
		//해당 객체를 클릭하면 해당 객체의 지도를 보여주기
		$(".data_name").click(function(){
			var moveLatLon = new kakao.maps.LatLng($(this).attr('data-lat'), $(this).attr('data-logt'));
			map.panTo(moveLatLon);
		})
		
	})
	
	</script>
</body>
</html>