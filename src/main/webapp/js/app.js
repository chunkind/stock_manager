/**
 * @Auth: K. J. S.
 * @Date: 2023. 07. 20
 * 초기 정보 셋팅
 */
(function(window){
	//위치 정보 셋팅
	/*navigator.geolocation.getCurrentPosition((position)=>{
		latitude = position.coords.latitude;
		longitude = position.coords.longitude;
		
		ajaxGet("http://api.openweathermap.org/data/2.5/weather?appid=ff872aa55ed728d18cbecc2453c8a1f5&lang=kr&units=metric&lat="+latitude+"&lon="+longitude, (data)=>{
			whetherData = data;
		});
	});*/
})(window);

const accessToken = '1';

const mainTitleHtml = (obj) => {
	let id = obj.id;
	let date = obj.date;
	let title = obj.title;
	let titleLink = obj.titleLink;
	let content = obj.content;
	let imgLink = obj.imgLink;
	let imgPath = obj.imgPath;
	let btnLink = obj.btnLink;
	let btnTxt = obj.btnTxt;
	
	let html = ''
	+ '<header class="major">'
	+ '  <span class="date">'+date+'</span>'
	+ '  <h2><a href="'+titleLink+'">'+title+'</a></h2>'
	+ '  <p>'+content+'</p>'
	+ '</header>'
	+ '<a href="'+imgLink+'" class="image main"><img src="'+imgPath+'" alt="" /></a>'
	+ '<ul class="actions special">'
	+ '  <li><a href="'+btnLink+'" class="button large">'+btnTxt+'</a></li>'
	+ '</ul>';
	
	return html;
}

const mainContentsHtml = (obj) => {
	let date = obj.date;
	let title = obj.title;
	let titleLink = obj.titleLink;
	let imgLink = obj.imgLink;
	let imgPath = obj.imgPath;
	let content = obj.content;
	let btnLink = obj.btnLink;
	let btnTxt = obj.btnTxt;
	
	let html = ''
	+ '<article>'
	+ '  <header>'
	+ '    <span class="date">'+date+'</span>'
	+ '    <h2><a href="'+titleLink+'">'+title+'</a></h2>'
	+ '  </header>'
	+ '  <a href="'+imgLink+'" class="image fit"><img src="'+imgPath+'" alt="" /></a>'
	+ '  <p>'+content+'</p>'
	+ '  <ul class="actions special">'
	+ '    <li><a href="'+btnLink+'" class="button">'+btnTxt+'</a></li>'
	+ '  </ul>'
	+ '</article>';
	
	return html;
}

/*
<div class="pagination">
	<a href="'+targetUrl+'?page='+i+'" class="previous">Prev</a>
	<a href="#" class="page active">1</a>
	<a href="#" class="page">2</a>
	<a href="#" class="page">3</a>
	<span class="extra">&hellip;</span>
	<a href="#" class="page">8</a>
	<a href="#" class="page">9</a>
	<a href="#" class="page">10</a>
	<a href="#" class="next">Next</a>
</div>
*/
const mainFooterHtml = (obj) => {
	let targetUrl = obj.targetUrl;
	let list = obj.list;
	let currentPoint = obj.currentPoint;
	let lastPoint = list.length;
	
	let html = '<div class="pagination">';
	
	if(currentPoint > 1){
		html += '<a href="'+targetUrl+'?page='+(currentPoint-1)+'" class="previous"></a>';
	}
	for(let i=1; i<=lastPoint; i++){
		if(currentPoint == i){
			html += '<a href="'+targetUrl+'?page='+i+'" class="page active">'+i+'</a>';
		}else{
			html += '<a href="'+targetUrl+'?page='+i+'" class="page">'+i+'</a>';
		}
	}
	if(currentPoint < lastPoint){
		html += '<a href="'+targetUrl+'?page='+(currentPoint+1)+'" class="next"></a>';
	}
	html += '</div>';
	
	return html;
}

const get = async function(url = '', success) {
	// 옵션 기본 값은 *로 강조
	const response = await fetch(url, {
		method: 'GET', // *GET, POST, PUT, DELETE 등
		mode: 'cors', // no-cors, *cors, same-origin
		cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
		credentials: 'same-origin', // include, *same-origin, omit
		headers: {
			'Content-Type': 'application/json',
			'Authorization': accessToken,
		},
		redirect: 'follow', // manual, *follow, error
		referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
		//body: JSON.stringify(data), // body의 데이터 유형은 반드시 "Content-Type" 헤더와 일치해야 함
	})
	.then((response) => response.json())
	.then((data) => {
		success(data);
		/*if(u.tokenCheck(url, data)){
			
		}*/
	});
}

const post = async function(url = '', params = {}, success) {
	if(!u.isEmpty(params.file)){
		var formData = new FormData();
		formData.append('user-file', document.getElementById(params.file).files[0]);
		const response = await fetch('/upload.do', {
			method: 'POST',
//			headers: {
//				'Content-Type': 'multipart/form-data',
//				'Content-Type': 'application/x-www-form-urlencoded',
//			},
			body: formData
		})
		.then((response) => response.json())
		.then((data) => {
			delete params.file;
			Object.assign(params, data);
			let obj = u.post_ajax(url, params).then((d) => {
				if(u.tokenCheck(url, d)){
					success(d);
				}
			});
			return obj;
		});
	}else{
		if(undefined !== params.file){
			delete params.file;
		}
		let obj = u.post_ajax(url, params).then((d) => {
			if(u.tokenCheck(url, d)){
				success(d);
			}
		});
		return obj;
	}
}

const post_ajax = async function(url = '', data = {}){
	// 옵션 기본 값은 *로 강조
	const response = await fetch(url, {
		method: 'POST', // *GET, POST, PUT, DELETE 등
		mode: 'cors', // no-cors, *cors, same-origin
		cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
		credentials: 'same-origin', // include, *same-origin, omit
		headers: {
			'Content-Type': 'application/json',
			'Authorization': accessToken,
		},
		redirect: 'follow', // manual, *follow, error
		referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
		body: JSON.stringify(data), // body의 데이터 유형은 반드시 "Content-Type" 헤더와 일치해야 함
	});
	return response.json(); // JSON 응답을 네이티브 JavaScript 객체로 파싱
}

const id = function(id){
	return document.querySelector('#' + id);
}

const clazz = function(clazz){
	return document.querySelector('.' + clazz);
}

const name = function(name){
	return document.querySelector('[name=' + name + ']');
}

const query = function(text){
	return document.querySelector(text);
}

const queryAll = function(text){
	return document.querySelectorAll(text);
}