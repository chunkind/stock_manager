<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" rel="stylesheet" href="/css/sign-in.css?${version}">
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media (min-width: 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.b-example-divider {
	width: 100%;
	height: 3rem;
	background-color: rgba(0, 0, 0, .1);
	border: solid rgba(0, 0, 0, .15);
	border-width: 1px 0;
	box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
}

.b-example-vr {
	flex-shrink: 0;
	width: 1.5rem;
	height: 100vh;
}

.bi {
	vertical-align: -.125em;
	fill: currentColor;
}

.nav-scroller {
	position: relative;
	z-index: 2;
	height: 2.75rem;
	overflow-y: hidden;
}

.nav-scroller .nav {
	display: flex;
	flex-wrap: nowrap;
	padding-bottom: 1rem;
	margin-top: -1px;
	overflow-x: auto;
	text-align: center;
	white-space: nowrap;
	-webkit-overflow-scrolling: touch;
}

.btn-bd-primary {
	--bd-violet-bg: #712cf9;
	--bd-violet-rgb: 112.520718, 44.062154, 249.437846;

	--bs-btn-font-weight: 600;
	--bs-btn-color: var(--bs-white);
	--bs-btn-bg: var(--bd-violet-bg);
	--bs-btn-border-color: var(--bd-violet-bg);
	--bs-btn-hover-color: var(--bs-white);
	--bs-btn-hover-bg: #6528e0;
	--bs-btn-hover-border-color: #6528e0;
	--bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
	--bs-btn-active-color: var(--bs-btn-hover-color);
	--bs-btn-active-bg: #5a23c8;
	--bs-btn-active-border-color: #5a23c8;
}

.bd-mode-toggle {
	z-index: 1500;
}

.bd-mode-toggle .dropdown-menu .active .bi {
	display: block !important;
}
</style>
<main class="form-signin w-100 m-auto">
	<form>
		<img class="mb-4" src="../assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
		<h1 class="h3 mb-3 fw-normal">Please sign in</h1>

		<div class="form-floating">
			<input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
			<label for="floatingInput">Email address</label>
		</div>
		<div class="form-floating">
			<input type="password" class="form-control" id="floatingPassword" placeholder="Password">
			<label for="floatingPassword">Password</label>
		</div>

		<div class="form-check text-start my-3">
			<input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault">
			<label class="form-check-label" for="flexCheckDefault">
				Remember me
			</label>
		</div>
		<button class="btn btn-primary w-100 py-2" type="submit">Sign in</button>
		<hr>
		<button class="btn btn-primary w-100 py-2" type="submit" onclick="loginKakao('kakao');">Kakao Login</button>
	</form>
</main>
<script>
const kakaoRedirectUrl = '${kakaoUrl}';

function loginKakao(type){
	let url = 'https://kauth.kakao.com/oauth/authorize?client_id=765dee4b1a92f64ca6b4d0449ef1a48d&redirect_uri='+kakaoRedirectUrl+'&response_type=code';
	console.log(url);
	let popupWindow = window.open(url, 'kakaoLogin', 'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no');
	window.closePopupWindow = function () {
		opener.location.href="/main/home.do";
		popupWindow.close()
	}
	// u.get('/user/snsAuth.do?type=' + type, (data) => {
	// 	if(data.resultCd == 'S'){
	// 		localStorage.setItem("accessToken", data.accessToken);
	// 		localStorage.setItem("loginInfo", JSON.stringify(data.loginInfo));
	// 		alert("로그인 하였습니다.");
	// 		location.href = '/urank/main.do';
	// 	}else{
	// 		localStorage.removeItem("accessToken");
	// 		localStorage.removeItem("loginInfo");
	// 		alert("아이디/비밀번호를 확인해 주세요.");
	// 	}
	// });
}

function kakaoLoginResult(data){
	if(data.resultCd == 'S'){
		localStorage.setItem("accessToken", data.accessToken);
		localStorage.setItem("loginInfo", JSON.stringify(data.loginInfo));
	}else{
		localStorage.removeItem("accessToken");
		localStorage.removeItem("loginInfo");
	}
	location.reload();
}
</script>