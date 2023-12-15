<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
	<h2 class="pb-2 border-bottom">회원가입</h2>
	<form class="row g-3">
		<div class="col-md-6">
			<label for="inputEmail4" class="form-label">아이디</label>
			<input type="email" class="form-control" id="inputEmail4">
		</div>
		<div class="col-md-6">
			<label for="inputPassword4" class="form-label">이메일</label>
			<div class="input-group mb-3">
				<input type="text" class="form-control" placeholder="Username" aria-label="Username">
				<span class="input-group-text">@</span>
				<input type="text" class="form-control" placeholder="Server" aria-label="Server">
			</div>
		</div>
		<div class="col-md-4">
			<label for="inputPassword4" class="form-label">이름</label>
			<input type="text" class="form-control" id="inputPassword4">
		</div>
		<div class="col-md-4">
			<label for="inputPassword4" class="form-label">닉네임</label>
			<input type="text" class="form-control" id="inputPassword4">
		</div>
		<div class="col-md-4">
			<label for="inputState" class="form-label">성별</label>
			<select id="inputState" class="form-select">
				<option value="">선택</option>
				<option value="">남자</option>
				<option value="">여자</option>
			</select>
		</div>
		<div class="col-md-6">
			<label for="inputPassword4" class="form-label">생년월일</label>
			<input type="password" class="form-control" id="inputPassword4">
		</div>
		<div class="col-md-6">
			<label for="inputPassword4" class="form-label">전화번호</label>
			<div class="input-group mb-3">
				<select class="form-select">
					<option value="010">010</option>
				</select>
				<span class="input-group-text">-</span>
				<input type="text" class="form-control" placeholder="middleNumber" aria-label="middleNumber">
				<span class="input-group-text">-</span>
				<input type="text" class="form-control" placeholder="lastNumber" aria-label="lastNumber">
			</div>
		</div>
		<div class="col-md-6">
			<label for="inputEmail4" class="form-label">비밀번호</label>
			<input type="email" class="form-control" id="inputEmail4">
		</div>
		<div class="col-md-6">
			<label for="inputPassword4" class="form-label">비밀번호 확인</label>
			<input type="password" class="form-control" id="inputPassword4">
		</div>
		<div class="col-12">
			<label for="inputAddress" class="form-label">기본 주소</label>
			<input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St">
		</div>
		<div class="col-10">
			<label for="inputAddress2" class="form-label">상세 주소</label>
			<input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor">
		</div>
		<div class="col-2">
			<label for="inputAddress2" class="form-label">우편 번호</label>
			<input type="text" class="form-control" id="inputAddress2" placeholder="Apartment, studio, or floor">
		</div>
		<div class="col-12">
			<button type="submit" class="btn btn-primary">Sign in</button>
		</div>
	</form>
</div>
<script>
post('', {}, (data)=>{
	console.log(data);
});
</script>