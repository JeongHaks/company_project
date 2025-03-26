<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<title>데이터 서비스 테스트 화면</title>
<style>
        /* 기본 스타일 */
        body {
            font-family: Arial, sans-serif;
        }
        .tab-container {
            display: flex;
            margin-bottom: 10px;
        }
        .tab-button {
            padding: 10px 20px;
            margin-right: 5px;
            border: 1px solid #ccc;
            background-color: #f5f5f5;
            cursor: pointer;
        }
        .tab-button.active {
            background-color: #009688;
            color: white;
        }

        .filter-container {
            margin-bottom: 10px;
        }

        .filter-container select,
        .filter-container input {
            padding: 5px;
            margin-right: 10px;
        }

        .filter-container button {
            padding: 5px 10px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            padding: 8px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #f0f0f0;
        }

        .excel-button {
            margin: 10px 0;
            padding: 8px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            float: right;
        }
        .pagination {
		    margin-top: 20px;
		    text-align: center;
		}
		
		.pagination a, .pagination span {
		    display: inline-block;
		    margin: 0 5px;
		    padding: 5px 10px;
		    text-decoration: none;
		    border: 1px solid #ddd;
		    color: #333;
		}
		
		.pagination span {
		    font-weight: bold;
		    background-color: #eee;
		}
    </style>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 탭 메뉴 -->
    <div class="tab-container">
        <div class="tab-button active" onclick="selectTab(this)">데이터 서비스 테스트 화면1</div>
        <div class="tab-button" onclick="selectTab(this)">데이터 서비스 테스트 화면2</div>
        <div class="tab-button" onclick="selectTab(this)">데이터 서비스 테스트 화면3</div>
    </div>

    <!-- 필터 영역 -->
    <form id="searchForm" action="${contextPath}/dataservice/dataservice.do" method="get">    
	    <div class="filter-container">
	        <label>시도</label>        
	        <select id="cido" name="cido">
				<option value="">전체</option>
	        	<c:forEach items="${cido}" var="cido"> 
		            <option value="${cido.ctpv}"
		            	<c:if test="${selectcido==cido.ctpv}">selected</c:if>>
		            	<c:out value="${cido.ctpv}"/>
		            </option>
	            </c:forEach>
	        </select>
	        <label>연도</label>
	        <select id="year" name="year">
	            <option value="">전체</option>
	            <c:forEach items="${year}" var="year">
		            <option value="${year.yymmdd}" <c:if test="${selectyear == year.yymmdd}">selected</c:if>>
		            	<c:out value="${year.yymmdd}"></c:out>
		            </option>
	            </c:forEach>
	        </select>
	        <label>월</label>
	        <select id="mm" name="mm">
	            <option value="">전체</option>
	            <c:forEach items="${mm}" var="mm">
		            <option value="${mm.mm}" <c:if test="${selectmm == mm.mm}">selected</c:if>>
		            	<c:out value="${mm.mm}"></c:out></option>
	            </c:forEach>
	        </select>
	        
	        <button type="submit" id="search" name="search">검색</button>        
	    </div>
	</form>
    <!-- 데이터 건수 -->
    <div>전체 : <span id="totalCount">${total.total }</span> 건</div>

    <!-- 엑셀 다운로드 -->  
    <form id="excelform" name="excelform" method="post">
	    <button type="button" class="excel-button" onclick="exceldownload()">엑셀 다운로드</button>
	</form>
	
    <!-- 테이블 -->
    <table>
        <thead>
            <tr>
                <th>시도</th>
                <th>연도</th>
                <th>월</th>
                <th>연료</th>
                <th>차종</th>
                <th>차량유형</th>
                <th>용도</th>
                <th>등록차량(대)</th>                
            </tr>
        </thead>
        <tbody>        
        	<c:choose>
        		<c:when test="${empty dataservice}">
		            <tr><td colspan="8">조회된 데이터가 없습니다.</td></tr>
		        </c:when>
		        <c:otherwise>
		            <c:forEach var="list" items="${dataservice}">
			            <tr>
			                <td>${list.ctpv}</td>
			                <td>${list.yymmdd}</td>
			                <td>${list.mm}</td>
			                <td>${list.fuel}</td>
			                <td>${list.car_ty}</td>
			                <td>${list.vehicle_ty}</td>
			                <td>${list.purps}</td>
			                <td>${list.rgn_car}</td>
			            </tr>
		            </c:forEach>
	            </c:otherwise>
            </c:choose>
        </tbody>
    </table>
	
	
	<!-- 페이징 -->
	<div class="pagination">
	    <!-- 맨 처음 버튼 -->
	    <c:if test="${currentPage > 1}">
	        <a href="${contextPath}/dataservice/dataservice.do?page=1&cido=${selectcido}&year=${selectyear}&mm=${selectmm}">맨 처음</a>
	    </c:if>
	    <!-- 이전 버튼 -->
	    <c:if test="${currentPage > 1}">
	        <a href="${contextPath}/dataservice/dataservice.do?page=${currentPage - 1}&cido=${selectcido}&year=${selectyear}&mm=${selectmm}">이전</a>
	    </c:if>
		<!-- 페이지 번호 반복 -->
	    <c:forEach var="i" begin="1" end="${totalPages}">
	        <c:choose>
	            <c:when test="${i == currentPage}">
	                <span style="font-weight: bold; color: red;">${i}</span>
	            </c:when>
	            <c:otherwise>
	                <a href="${contextPath}/dataservice/dataservice.do?page=${i}&cido=${selectcido}&year=${selectyear}&mm=${selectmm}">${i}</a>
	            </c:otherwise>
	        </c:choose>
	    </c:forEach>
		<!-- 다음 버튼 -->
	    <c:if test="${currentPage < totalPages}">
	        <a href="${contextPath}/dataservice/dataservice.do?page=${currentPage + 1}&cido=${selectcido}&year=${selectyear}&mm=${selectmm}">다음</a>
	    </c:if>
	    <!-- 맨 끝 버튼 -->
	     <c:if test="${currentPage < totalPages}">
	        <a href="${contextPath}/dataservice/dataservice.do?page=${totalPages}&cido=${selectcido}&year=${selectyear}&mm=${selectmm}">맨 끝</a>
	    </c:if>
	</div>
    
    
    
    <script>
    // 필터 전체 선택할 때 초기화
    $("#cido").change(function(){
    	var cido = $(this).val();
    	if(cido == ""){
			$("#year").val("");
			$("#mm").val("");
    	}
    });
    
    // 연도 필터 변경시 해당 연도에 해당하는 월들 표시 
    $("#year").change(function(){
    	var year = $(this).val();
    	$.ajax({
    		url : "${contextPath}/dataservice/getMonth.do",
    		type : "POST",
    		data : {
    			"year" : year
    		},
    		dataType: "json",    		
    		success : function(data){
    			var mmval="";
    			mmval = "<option value=''>전체</option>";
    			$.each(data, function(index, item) {
    				mmval += "<option value='" + item.mm + "'>" + item.mm + "</option>";
    			});
    			$("#mm").html(mmval);
    		},
    		error : function(xhr, status, error) {   			  	
    			alert("데이터 통신 실패");
    		}
    	});
    });
    
    
    // 필터 선택 후 검색
   	$(document).ready(function(){
   		$("#search").click(function(){
   			var cido = $("#cido").val();
   			var year = $("#year").val();
   			var mm = $("#mm").val();
   			
   			$.ajax({
   				url : "${contextPath}/dataservice/dataservice.do",
   				type : "GET",
   				data : {
   					"cido" : cido,
   					"year" : year,
   					"mm" : mm
   				},
   				success: function(data){
   					console.log(data);
   				},
   				error:function(){
   					alert("데이터 요청 실패");
   				}
   			});
   		});
   	});
    
    const contextPath = "${pageContext.request.contextPath}";

    function exceldownload() {
        const cido = $("#cido").val();
        const year = $("#year").val();
        const mm = $("#mm").val();

        alert("넘길 값: " + cido + ", " + year + ", " + mm);

        if (confirm("다운받으시겠습니까??")) {
            const form = document.getElementById("excelform");
            form.action = contextPath + "/download.do?" +
                          "cido=" + encodeURIComponent(cido) +
                          "&year=" + encodeURIComponent(year) +
                          "&mm=" + encodeURIComponent(mm);
            form.submit();
        } else {
            alert("다운로드 취소!");
        }
    }
   
    
    </script>
</body>
</html>