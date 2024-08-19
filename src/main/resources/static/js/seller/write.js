function setPlaceholderWithTodayDate() {
	var today = new Date();
	var yyyy = today.getFullYear();
	var mm = String(today.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
	var dd = String(today.getDate()).padStart(2, '0');

	var formattedDate = yyyy + '-' + mm + '-' + dd;

	document.getElementById('pubdate').placeholder = formattedDate;
}

function setPublicationDate() {
	var today = new Date();
	var yyyy = today.getFullYear();
	var mm = String(today.getMonth() + 1).padStart(2, '0');
	var dd = String(today.getDate()).padStart(2, '0');
	var hh = String(today.getHours()).padStart(2, '0');
	var min = String(today.getMinutes()).padStart(2, '0');
	var ss = String(today.getSeconds()).padStart(2, '0');

	var formattedDateTime = yyyy + '-' + mm + '-' + dd + ' ' + hh + ':' + min + ':' + ss;

	document.getElementById('pubdate').value = formattedDateTime;
}

function uploadImage(url, formData){
	return fetch(url,{
		method: "POST",
		body: formData
	})
	.then(response => response.json())
	.catch(error => {
		console.log('Error :' , error );
		throw error;
	});
}

function fileupload(input) {
	const files = input.files;
	if (files.length < 1) {
		console.log("파일이 선택되지 않았어요");
		return;
	}

	const fileType = files[0].type;
	if (!fileType.startsWith('image/')) {
		alert("이미지 파일이 아닙니다.");
		input.value = '';
		return;
	}

	const fileSize = files[0].size;
	if (fileSize > 2 * 1024 * 1024) {
		alert("파일용량제한:2MB이내의 파일을 사용하세요" + fileSize);
		input.value = '';
		return;
	}
	
	var formData = new FormData();
	formData.append("coverimg", input.files[0]);
	
	// 이미지 파일을 서버에 업로드
	uploadImage("/seller/fileupload", formData)
	.then(result => {
		console.log(result);
		const url = result.url;
		const bucketkey = result.bucketkey;
		const orgName = result.orgName;
		
		// 업로드된 이미지 URL을 백그라운드 이미지로 설정
		input.parentElement.style.backgroundImage = `url(${url})`;
		input.parentElement.style.backgroundColor = "transparent";
		
		// 숨겨진 input 필드에 값 설정
		let bucketkeyInput = input.nextElementSibling;
		bucketkeyInput.value = bucketkey;
		let orgNameInput = bucketkeyInput.nextElementSibling; 
		orgNameInput.value = orgName;
	})
	.catch(error => {
		alert("파일업로드 실패! : " + error.message);
	});
}