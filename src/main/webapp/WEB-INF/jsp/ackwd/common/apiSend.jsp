<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
	<div class="row">
		<div class="col-md-2">
			<label for="exampleInputEmail1" class="form-label">method</label>
			<select class="form-select" aria-label="Method" aria-describedby="urlHelp">
			  <option value="get">GET</option>
			  <option value="post">POST</option>
			</select>
			<div id="urlHelp" class="form-text">전달방식을 선택해 주세요.</div>
		</div>
		<div class="col-md-10">
			<label for="exampleInputEmail1" class="form-label">url</label>
			<input type="text" class="form-control" aria-describedby="emailHelp">
			<div id="emailHelp" class="form-text">호출할 api의 주소를 입력해 주세요.</div>
		</div>
	</div>
	<div class="row">
		<ul class="nav nav-underline">
			<li class="nav-item">
				<a class="nav-link" href="#">Params</a>
			</li>
			<li class="nav-item">
				<a class="nav-link active" aria-current="page" href="#">Headers</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="#">Body</a>
			</li>
			<!-- 
			<li class="nav-item">
				<a class="nav-link disabled" aria-disabled="true">Disabled</a>
			</li> -->
		</ul>
	</div>
</div>