const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

const cameraButton = document.querySelector('#cameraButton');
const fileUploadContainer = document.querySelector('#fileUploadContainer');
const imageFile = document.querySelector('#imageFile');
const imagePreview = document.querySelector('#imagePreview');
const confirmButton = document.querySelector('#confirmButton');
//const closeButton = document.querySelector('#closeButton');
const queryInput = document.querySelector('#queryInput');
const searchForm = document.querySelector('#searchForm');
var cameraModal = document.getElementById("camera-modal");
var closeBtn = document.getElementsByClassName("close")[0];

// 카메라 버튼 클릭 시 모달 열기
cameraButton.addEventListener('click', () => {
	cameraModal.style.display = "block";
    fileUploadContainer.style.display = 'block';
});

// 닫기 버튼 클릭 시 모달 닫기
closeBtn.addEventListener('click', () => {
	cameraModal.style.display = "none";
    fileUploadContainer.style.display = 'none';
    imagePreview.style.display = 'none';
    confirmButton.style.display = 'none';
    imageFile.value = ''; // 파일 입력 초기화
});

// 파일이 선택되면 이미지 미리보기 보이기
imageFile.addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            imagePreview.src = e.target.result;
            imagePreview.style.display = 'block';
            confirmButton.style.display = 'inline-block';
        };
        reader.readAsDataURL(file);
    }
});

// 확인 버튼 클릭 시 텍스트 추출 처리
confirmButton.addEventListener('click', async () => {
    const formData = new FormData();
    formData.append('imageFile', imageFile.files[0]);

    const response = await fetch('/upload-image', {
        method: 'POST',
        headers:{
			[header]: token
		},
        body: formData
    });

    const result = await response.json();
    queryInput.value = result.text || '실패';

    // 텍스트 추출 후 파일 업로드 창 닫기
    cameraModal.style.display = "none";
    fileUploadContainer.style.display = 'none';
    imagePreview.style.display = 'none';
    confirmButton.style.display = 'none';
    imageFile.value = ''; // 파일 입력 초기화
	
	
    // 검색 폼 제출
    searchForm.submit();
});

document.addEventListener('DOMContentLoaded', function() {
  const queryInput = document.getElementById('queryInput');
  const recentSearches = document.querySelector('.recent-searches');
  const searchForm = document.getElementById('searchForm');

  queryInput.addEventListener('focus', function() {
    recentSearches.style.display = 'block';
  });

  document.addEventListener('click', function(event) {
    if (!queryInput.contains(event.target) && !recentSearches.contains(event.target)) {
      recentSearches.style.display = 'none';
    }
  });

  // 최근 검색어 클릭 시 검색창에 입력하고 폼 제출
  const searchItems = recentSearches.querySelectorAll('li');
  searchItems.forEach(item => {
    item.addEventListener('click', function(event) {
      event.preventDefault(); // 기본 동작 방지
      queryInput.value = this.textContent;
      recentSearches.style.display = 'none';
      searchForm.submit(); // 폼 제출
    });
  });
});