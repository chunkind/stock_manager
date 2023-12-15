<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="background"></div>
<script>
document.addEventListener("DOMContentLoaded", () => {
	get('/user/kakaoLogin.do?code=${code}',(data)=>{
		opener.window.kakaoLoginResult(data);
		opener.window.closePopupWindow(data);
		opener.focus();
		opener.close();
		self.close();
	});
});
</script>