const u = {
	goUrl: async function (url) {
		// 옵션 기본 값은 *로 강조
		const response = await fetch(url, {
			method: 'GET', // *GET, POST, PUT, DELETE 등
			mode: 'cors', // no-cors, *cors, same-origin
			cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
			credentials: 'same-origin', // include, *same-origin, omit
			headers: {
				'Content-Type': 'text/html',
				'Authorization': accessToken,
			},
			redirect: 'follow', // manual, *follow, error
			referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
			//body: JSON.stringify(data), // body의 데이터 유형은 반드시 "Content-Type" 헤더와 일치해야 함
		})
		.then((response) => response.text())
		.then((data) => {
			// var win = window.open(url,'_self');
			// win.document.write(data);
			location.href = url;
		});
	},
	tokenCheck: function(url, data){
		if(data.result == 'F'){
			localStorage.setItem("message", data.message);
			localStorage.removeItem("accessToken");
			localStorage.removeItem("loginInfo");
			location.href = '/urank/main.do';
			return false;
		}else{
			return true;
		}
	},
	getUsrInfo: function(key){
		let usr = JSON.parse(localStorage.getItem("loginInfo"));
		if(null == usr){
			return null;
		}else{
			return usr[key];
		}
	},
	strzip: function(text, maxSize){
		if(u.isEmpty(text)){
			return '';
		}
		let size = text.length;
		let rtnStr = '';
		let strMaxSize = 60;
		if(!u.isEmpty(maxSize) || maxSize > 0){
			strMaxSize = maxSize;
		}
		if(size > strMaxSize){
			rtnStr = text.substr(0, strMaxSize) + "..";
		}else{
			rtnStr = text;
		}
		return rtnStr;
	},
	closePop: function(){
		u.clazz('popup_title').innerHTML = '';
		u.clazz('popup_contents').innerHTML = '';
		u.clazz('popup_btn').innerHTML = '';
		document.getElementById('popup').style.display = 'none';
	},
	popup: function(title, content, btns, height, success){
		if(u.isEmpty(title)){
			title = '';
		}
		if(u.isEmpty(content)){
			content = '';
		}
		if(u.isEmpty(btns)){
			btns = '';
		}
		if(u.isEmpty(height)){
			height = '80%';
		}
		u.clazz('popup_title').innerHTML = title;
		u.clazz('popup_contents').innerHTML = content;
		u.clazz('popup_btn').innerHTML = btns;
		u.clazz('popup').style.height = height;
		u.clazz('popup_black').style.display = 'flex';

		if(!u.isEmpty(success)){
			u.id('btn_popok').addEventListener('click', success);
		}
	},
	isEmpty: function(text){
		if(undefined == text || null == text || '' == text || 'null' == text){
			return true;
		}else{
			return false;
		}
	},
	isAdmin: function(){
		if(u.getUsrInfo("usrLv") == '99'){
			return true;
		}else{
			return false;
		}
	},
	getEditor: function(id, ph){
		let quill = new Quill('#'+id, {
			// debug: 'info',
			modules: {
				toolbar: [
					[{ header: [1, 2, false] }],
					['bold', 'italic', 'underline'],
					['image', 'code-block']
				]
			},
			placeholder: ph == null ? '글을 작성 하세요...' : ph,
			readOnly: false,
			theme: 'snow'  // or 'bubble'
		});
		quill.getModule('toolbar').addHandler('image', ()=>{
			const input = document.createElement('input');
			input.setAttribute('type', 'file');
			input.click();
			input.onchange = (e)=>{
				const imageFormData = new FormData();
				[].forEach.call(e.target.files, (f) => {
					imageFormData.append('user-file', f)
				});
				fetch('/comm/uploadFile.do',{
					method: 'POST',
					headers: {},
					body: imageFormData
				})
				.then((response) => response.json())
				.then((data) => {
					// if (response.status === 200) {
						const range = quill.getSelection(); // 사용자가 선택한 에디터 범위
						// uploadPath에 역슬래시(\) 때문에 경로가 제대로 인식되지 않는 것을 슬래시(/)로 변환
						data.uploadPath = data.uploadPath.replace(/\\/g, '/');
						quill.insertEmbed(range.index, 'image', data.filePath+data.fileName);
					// }
					// if (response.status === 400) {
					// 	alert('에러');
					// }
				});
			};
		});
		return quill;
	},
	getClsNm: function(text, color){
		if(text == undefined || text == ''){
			return '';
		}

		let clazz = '';
		if(color == 'white'){
			clazz = 'span_radius bg_white ft_black';
		}
		else if(color == 'blue'){
			clazz = 'span_radius bg_blue ft_white';
		}
		else if(color == 'yellow'){
			clazz = 'span_radius bg_yellow ft_white';
		}
		else if(color == 'black'){
			clazz = 'span_radius bg_black ft_white';
		}
		else{ //red
			clazz = 'span_radius bg_red ft_white';
		}
		return '<span class="'+clazz+'">' + text + '</span>';
	},
	getCmtCntNm: function(text){
		if(text == undefined || text == ''){
			return '';
		}
		return '<span class="span_radius">[' + text + ']</span>';
	},
	getUsrNicNm: function(usrLv, usrNic){
		if(usrLv != undefined && usrLv == '99'){
			return '<span class="ft_blue">' + usrNic + '</span>';
		}
		return usrNic;
	},
	getCheckListHtml: function(idList){
		if('object' != typeof idList){
			log(`u.getCheckListHtml() in parameter Type error. `);
			return;
		}
		if(idList.length < 1){
			log(`u.getCheckListHtml() in size error. size: ${idList.length}`);
			return;
		}
		let html = '';
		for(let i=0; i<idList.length; i++){
			html += '<label for="'+idList[i]+'"><label/>';
			html += '<input type="checkbox" id="'+idList[i]+'" name="'+idList[i]+'" />';
		}
		return html;
	},
	getSelectedBoxHtml: function(id, objs){
		if('string' != typeof id){
			log(`u.getSelectedBoxHtml() in parameter Type error. id is string.`);
			return;
		}
		if('object' != typeof objs){
			log(`u.getSelectedBoxHtml() in parameter Type error. values is JsonObject.`);
			return;
		}
		let html = '';
		html += '<select id="'+id+'" name="'+id+'">';
		for(let i=0; i<objs.length; i++){
			if(objs[i].checked == true){
				html += '<option value="'+objs[i].value+'" selected>'+objs[i].name+'</option>'
			}else{
				html += '<option value="'+objs[i].value+'">'+objs[i].name+'</option>'
			}
		}
		html += '</select>';
		return html;
	},
	getStarHtml: function(grade){
		let html = '';
		grade = grade || 0;
		html += '  <div class="starpoint_box_view">';
		html += '	<label class="label_star" title="1"><span class="blind">1점</span></label>';
		html += '	<label class="label_star" title="2"><span class="blind">2점</span></label>';
		html += '	<label class="label_star" title="3"><span class="blind">3점</span></label>';
		html += '	<label class="label_star" title="4"><span class="blind">4점</span></label>';
		html += '	<label class="label_star" title="5"><span class="blind">5점</span></label>';
		html += '	<label class="label_star" title="6"><span class="blind">6점</span></label>';
		html += '	<label class="label_star" title="7"><span class="blind">7점</span></label>';
		html += '	<label class="label_star" title="8"><span class="blind">8점</span></label>';
		html += '	<label class="label_star" title="9"><span class="blind">9점</span></label>';
		html += '	<label class="label_star" title="10"><span class="blind">10점</span></label>';
		html += '	<span class="starpoint_bg" style="width:'+grade+'0%"></span>';
		html += '  </div>';
		
		return html;
	},
	getEditStarHtml: function(grade){
		let html = '';
		grade = grade || 0;
		html += '  <div class="starpoint_box" id="starpoint_box">';
		html += '	<label for="grade_1" class="label_star" title="1"><span class="blind">1점</span></label>';
		html += '	<label for="grade_2" class="label_star" title="2"><span class="blind">2점</span></label>';
		html += '	<label for="grade_3" class="label_star" title="3"><span class="blind">3점</span></label>';
		html += '	<label for="grade_4" class="label_star" title="4"><span class="blind">4점</span></label>';
		html += '	<label for="grade_5" class="label_star" title="5"><span class="blind">5점</span></label>';
		html += '	<label for="grade_6" class="label_star" title="6"><span class="blind">6점</span></label>';
		html += '	<label for="grade_7" class="label_star" title="7"><span class="blind">7점</span></label>';
		html += '	<label for="grade_8" class="label_star" title="8"><span class="blind">8점</span></label>';
		html += '	<label for="grade_9" class="label_star" title="9"><span class="blind">9점</span></label>';
		html += '	<label for="grade_10" class="label_star" title="10"><span class="blind">10점</span></label>';
		if(grade == '1'){
			html += '	<input type="radio" name="grade" id="grade_1" value="1" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_1" value="1" class="star_radio">';
		}
		if(grade == '2'){
			html += '	<input type="radio" name="grade" id="grade_2" value="2" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_2" value="2" class="star_radio">';
		}
		if(grade == '3'){
			html += '	<input type="radio" name="grade" id="grade_3" value="3" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_3" value="3" class="star_radio">';
		}
		if(grade == '4'){
			html += '	<input type="radio" name="grade" id="grade_4" value="4" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_4" value="4" class="star_radio">';
		}
		if(grade == '5'){
			html += '	<input type="radio" name="grade" id="grade_5" value="5" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_5" value="5" class="star_radio">';
		}
		if(grade == '6'){
			html += '	<input type="radio" name="grade" id="grade_6" value="6" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_6" value="6" class="star_radio">';
		}
		if(grade == '7'){
			html += '	<input type="radio" name="grade" id="grade_7" value="7" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_7" value="7" class="star_radio">';
		}
		if(grade == '8'){
			html += '	<input type="radio" name="grade" id="grade_8" value="8" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_8" value="8" class="star_radio">';
		}
		if(grade == '9'){
			html += '	<input type="radio" name="grade" id="grade_9" value="9" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_9" value="9" class="star_radio">';
		}
		if(grade == '10'){
			html += '	<input type="radio" name="grade" id="grade_10" value="10" checked class="star_radio">';
		}else{
			html += '	<input type="radio" name="grade" id="grade_10" value="10" class="star_radio">';
		}
		html += '	<span class="starpoint_bg"></span>';
		html += '  </div>';
		
		return html;
	},
	getPageHtml: function(target, totalPage){
		let html = '';
		html += '<div class="page_btns">';
		if(totalPage != 0){
			if(target != 1){
				html += '<a href="#" onClick="javascript:fn_page(-1);"><i class="fa-sharp fa-solid fa-caret-left"></i></a>';
			}
			html += '<input type="text" class="target" id="target" value="'+target+'" onKeyup="this.value=this.value.replace(/[^0-9]/g,\'\');"/>';
			html += ' / ' + totalPage;
			if(target != totalPage){
				html += '<a href="#" onClick="javascript:fn_page(1);"><i class="fa-sharp fa-solid fa-caret-right"></i></a>';
			}
		}
		html += '</div>';
		return html;
	},
	makeLink : function (text) {
		var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
		var regEmail = new RegExp("([xA1-xFEa-z0-9_-]+@[xA1-xFEa-z0-9-]+\.[a-z0-9-]+)","gi");
		return text.replace(regURL,"<a href='$1://$2' target='_blank'>$1://$2</a>")/*.replace(regEmail,"<a href='mailto:$1'>$1</a>")*/;
	},
	addAction: function(id, act, fn){
		document.getElementById(id).addEventListener('keypress', (e)=>{
			fn(e);
		});
	},
	getStrData: function(key){
		let value = localStorage.getItem(key);
		if(undefined === value || null === value || "null" === value){
			return {};
		}else{
			return JSON.parse(value);
		}
	},
	checkDataByName: function(name, msg){
		if(u.name(name).value === undefined || u.name(name).value.trim() == ''){
			alert(msg);
			u.name(name).focus();
			return false;
		}else{
			return true;
		}
	}
}


const Util = {
	//opener관련 오류가 발생하는 경우 아래 주석을 해지하고, 사용자의 도메인정보를 입력합니다.
	//(＂팝업 API 호출 소스"도 동일하게 적용시켜야 합니다.)
	//document.domain = "abc.go.kr";
	jusoPopup : function (){
		//경로는 시스템에 맞게 수정하여 사용
		//호출된 페이지(jusopopup.jsp)에서 실제 주소검색URL(http://www.juso.go.kr/addrlink/addrLinkUrl.do)를
		//호출하게 됩니다.
		var pop = window.open("/jusoPop.do","pop","width=570,height=420, scrollbars=yes, resizable=yes");
		//** 2017년 5월 모바일용 팝업 API 기능 추가제공 **/
		//모바일 웹인 경우, 호출된 페이지(jusopopup.jsp)에서
		//실제 주소검색 URL(http://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
		//var pop = window.open("/popup/jusoPopup.jsp","pop","scrollbars=yes, resizable=yes");
	},
	jusoCallBack : function(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd,
			rnMgtSn, bdMgtSn , detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm,
			buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo){
		// 2017년 2월 제공항목이 추가되었습니다. 원하시는 항목을 추가하여 사용하시면 됩니다.
		$(".roadFullAddr").val(roadFullAddr);
		$(".roadAddrPart1").val(roadAddrPart1);
		$(".roadAddrPart2").val(roadAddrPart2);
		$(".addrDetail").val(addrDetail);
		$(".zipNo").val(zipNo);
	},
	ajax : function(obj){
		if(obj.method === undefined){
			obj.method = "POST";
		}
		if(obj.contentType === undefined){
			obj.contentType = "application/json; charset=UTF-8";
			obj.data = JSON.stringify(obj.data);
		}
		$.ajax({
			url : obj.url,
			type : obj.method,
			data : obj.data,
			dataType: obj.dataType,
			contentType : obj.contentType,
			success : obj.fn_success,
			error:function(request, status, error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	},
	memoriedList : function(){
		var checkList = localStorage.getObj('wordListSeq');
		var result = true;
		if(null != checkList){
			if(confirm('다 외우지 못한 단어장이 있습니다. 이동 하시겠습니까?')){
				var seq = localStorage.getItem('wordListSeq');
				seq = seq.replace(/"/gi, "");
				location.href = "/fo/word/detail.do?menu=0&wordListSeq=" + seq;
				result = false;
			}else{
				localStorage.clear();
				result = true;
			}
		}
		return result;
	}
};


/**
 * 웹소켓 관련 유틸.
 */
const WS = {
	socket : null,
	callback : null,
	url : null,
	init : function(url, callback){
		var self = this;
		
		console.log("sebsocket init...");
		//onclose : WebSocket 연결 해제
		self.socket = new WebSocket(url);
		self.url = url;
		self.callback = callback;
		
		//onerror : 전송 에러 발생
		self.socket.onerror = function(e) {
			self.onError(e)
		};
		//onopen : WebSocket 연결
		self.socket.onopen = function(e) {
			self.onOpen(e);
			isConnected = true;
		};
		//onmessage : 메시지 수신
		self.socket.onmessage = function(e) {
			self.onMessage(e)
		};
	},
	onOpen : function (e) {
		console.log("Connected to Endpoint!");
//		this.doSend($("#txt_ws_chat").val());
	},
	onError : function(e){
		console.log('ERROR: ' + e);
		console.log('ERROR.data: ' + e.data);
		try{
			this.init(this.url, this.callback);
		}catch(e){
			alert(e.data);
		}
	},
	doSend : function (message) {
		this.socket.send(message);
		//this.socket.close();
	},
	onMessage : function (e){ //서버측에서 메세지 수신.
		console.log(e.data);
		this.callback(e.data);
	}
}

function sendSurGameInfo(){
	if(isConnected){
		let playerObj = {
			'userId' : userId,
			'userXPos' : playerObject.x_pos,
			'userYPos' : playerObject.y_pos,
			'imgPath': '/img/game/sur/Sprite-0001.png',
			'speed' : playerObject.speed,
			'direction' : playerObject.direction,
			'hp' : playerObject.hp,
			'mp' : playerObject.mp,
			'rhp' : playerObject.rhp,
			'rmp' : playerObject.rmp,
			'width' : 48*1,
			'height' : 48*1,
			'state' : 'idle',
			'stateCnt' : 0,
			'type' : 'monster'
		};
		
		//1.웹소켓
		if(isWebSocket){
			WS.doSend(JSON.stringify(playerObj));
		}
		//2.ajax
		else{
			Util.ajax({
				url : '/fo/sur/surGameInfo.do',
				data : playerObj,
				fn_success : function(data){
					succesCallback(data);
				}
			});
		}
	}
}


/**
 * 클릭 메뉴 체크
 */
const selectMenu = (menuName, idx, url) => {
	if(menuName == 'menu_tab'){
		setCookie("menu_tab", idx);
		menuLoading();
	}
	if(menuName == 'menu_side'){
		setCookie("menu_side", idx);
		menuLoading();
	}
	if(undefined !== url){
		location.href = url;
	}
}

//초기 메뉴 선택
const menuLoading = () =>{
	//클래스 삭제
	$('.am-sideleft-tab .nav-link').each(function() {
		$(this).removeClass('active');
	});
	$('.am-sideleft-menu .nav-link').each(function() {
		$(this).removeClass('active');
	});

	//클래스 추가
	let menu_tab = getCookie("menu_tab") || 0;
	let menu_side = getCookie("menu_side") || 0;
	$(".am-sideleft-tab>.nav-item:eq("+menu_tab+")>.nav-link").addClass("active");
	$(".am-sideleft-menu>.nav-item:eq("+menu_side+")>.nav-link").addClass("active");
}

//특정 클래스의 요소들을 가져오기
const getElClass = (str) => {
	let targets = str.split(" ");
	let elements = document.querySelector(targets[0]);
	for(let i=1; i<targets.length; i++){
		let className = targets[i];
		elements = elements.childNodes;
		for(let j=0; j<elements.length; j++){
			if(elements[j] != className){
				elements.pop();
			}
		}
	}
	return elements;
}


function fn_kakaoLoginAction(type){
	let popupWindow = window.open('https://kauth.kakao.com/oauth/authorize?client_id=ba6f08997c342454dfb28ff8cdd36ad9&redirect_uri='+kakaoRedirectUrl+'&response_type=code', 'kakaoLogin', 'top=10, left=10, width=500, height=600, status=no, menubar=no, toolbar=no, resizable=no');
	window.closePopupWindow = function () {
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








/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 쿠키에 데이터 담기
 */
const setCookie = (name, value, day) => {
	if(undefined === day){
		day = 1;
	}
	let date = new Date();
	date.setTime(date.getTime() + day * 60 * 60 * 24 * 1000);
	document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
};

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 쿠키에 데이터 꺼네기
 */
const getCookie = (cname) => {
	let name = cname + "=";
	let decodedCookie = decodeURIComponent(document.cookie);
	let ca = decodedCookie.split(';');
	for(let i = 0; i <ca.length; i++) {
		let c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
};

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 쿠키 삭제.
 */
const delCookie = (name) => {
	let date = new Date();
	document.cookie = name + "= " + "; expires=" + date.toUTCString() + "; path=/";
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 로컬 스토리지에 저장
 */
const setLocal = (key, value) => {
	localStorage.setItem(key, value);
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 로컬 스토리지에 저장된 데이터 가져오기
 */
const getLocal = (key) => {
	return localStorage.getItem(key);
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 로컬 스토리지 에서 삭제
 */
const delLocal = (key) => {
	localStorage.removeItem(key);
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * Ajax Get 요청
 */
const ajaxGet = (url, fn_success, errMsg) => {
	fetch(url)
	.then((response) => response.json())
	.then((data) => {
		fn_success(data);
	})
	.catch((err)=>{
		console.log(err);
	});
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * Ajax Post 요청
 */
const ajaxPost = (url, param, fn_success, errMsg) => {
	fetch(url, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(param)
	})
	.then((response) => response.json())
	.then((data) => {
		fn_success(data);
	})
	.catch((err)=>{
		console.log(err);
	});
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 페이지 이동
 * 
 * @param url : 이동할 페이지 Url
 * @param pramData : 파라미터 데이터(Object)
 * @param method : form 전송 방식(get/post). default는 post방식.
 */
const formReq = (url, pramData, method) => {
	var form = document.createElement('form');
	var objs = document.createElement('div');
	for(var p in pramData){
		var obj = document.createElement('input');
		obj.setAttribute('type','hidden');
		obj.setAttribute('name',p);
		obj.setAttribute('value',pramData[p]);
		objs.appendChild(obj);
	}
	form.appendChild(objs);
	if(method == 'get'){
		url += '?'+$(form).serialize();
	}else{
		form.setAttribute('method', 'post');
	}
	form.setAttribute('action', url);
	document.body.appendChild(form);
	form.submit();
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 동적 폼 전송
 */
const formPost = (url, paramsJson, newPop) => {
	var form = document.createElement("form");
	form.setAttribute("id", "trasForm");
	form.setAttribute("method", "POST");
	form.setAttribute("action", url);
	if(newPop == true){
		form.setAttribute("target", "_blank");
	}
	//form.setAttribute("encType", "application/x-www-form-urlencoded");
	
	for(var key in paramsJson){
		var hiddenField = document.createElement("input");
		hiddenField.setAttribute("type", "hidden");
		hiddenField.setAttribute("name", key);
		hiddenField.setAttribute("value", paramsJson[key]);
		form.appendChild(hiddenField);
		console.log("[util.formPost] key:"+key+", value:"+paramsJson[key]);
	}
	
	document.body.appendChild(form);
	form.submit();
	document.getElementById("trasForm").remove();
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 스트링 널 체크
 */
const isStrEmpty = (obj) => {
	var result = false;
	if(undefined === obj){
		result = true;
	}
	if(null === obj){
		result = true;
	}
	if('' == obj){
		result = true;
	}
	return result;
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 
 */
const getUrlParam = () => {
	var vars = [], hash;
	var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for(var i = 0; i < hashes.length; i++)
	{
		hash = hashes[i].split('=');
		vars.push(hash[0]);
		vars[hash[0]] = hash[1];
	}
	return vars;
}

/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 
 */
const serializeObject = () => {
	var obj = null;
	try {
		if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			var arr = this.serializeArray();
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}//if ( arr ) {
		}
	} catch (e) {
		alert(e.message);
	} finally {
	}

	return obj;
}

const getHtmlById = (target) => {
	return document.querySelector("#"+target);
}

const setHtmlById = (target, html) => {
	return document.querySelector("#"+target).innerHTML = html;
}

const getHtmlByClass = (target) => {
	let list = document.querySelectorAll("."+target);
	let size = list.length;
	if(size == 1){
		return list[0];
	}
	return list;
}

const getQueryStr = (paramKey) => {
	let queryString = new URLSearchParams(location.search)
	return queryString.get(paramKey);
}

const getPage = () => {
	let page = getQueryStr('page');
	if(undefined === page){
		page = 1;
	}
	if(null === page){
		page = 1;
	}
	if('' == page){
		page = 1;
	}
	if(0 == page){
		page = 1;
	}
	return page;
}