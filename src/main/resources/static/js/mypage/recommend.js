document.getElementById('selectionForm').addEventListener('submit', function(e) {
	e.preventDefault();
	const ageGroup = document.getElementById('ageGroup').value;
	const gender = document.getElementById('gender').value;

	if (!ageGroup || !gender) {
		alert('연령대와 성별을 모두 선택해주세요.');
		return;
	}

	// 로딩 표시
	document.getElementById('loading').style.display = 'block';
	document.getElementById('bookGrid').innerHTML = '';

	// 서버로 POST 요청 보내기
	fetch('/recommend-books', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			[header]: token
		},
		body: JSON.stringify({ ageGroup, gender }),
	})
		.then(response => response.json())
		.then(data => {
			// 서버로부터 받은 데이터로 책 목록 업데이트
			updateBookList(data);
		})
		.catch(error => {
			console.error('Error:', error);
			alert('책 추천을 가져오는 데 실패했습니다. 다시 시도해주세요.');
		})
});

function updateBookList(books) {
	const bookGrid = document.getElementById('bookGrid');
	bookGrid.innerHTML = '';

	books.forEach(book => {
		const bookElement = document.createElement('div');
		bookElement.className = 'book-item';
		bookElement.innerHTML = `
                    <img src="${book.imageUrl}">
                    <p>${book.title}</p>
                    <p class="author">${book.author}</p>
                `;
		bookGrid.appendChild(bookElement);
	});
}