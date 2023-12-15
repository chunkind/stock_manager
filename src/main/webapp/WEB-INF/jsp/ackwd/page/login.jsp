<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="login_btn">
	<a href="javascript:loginKakao('kakao');"><img src="/img/sns/kakao/kakao_login_medium_narrow.png" /></a>
</div>
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