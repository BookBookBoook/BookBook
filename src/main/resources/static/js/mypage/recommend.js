document.getElementById('selectionForm').addEventListener('submit', async function(e) {
  e.preventDefault();
  const ageGroup = document.getElementById('ageGroup').value;
  const gender = document.getElementById('gender').value;
  const kind = document.getElementById('kind').value;

  try {
    // 서버로 POST 요청 보내기
    const response = await fetch('/mypage/recommends', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [header]: token
      },
      body: JSON.stringify({
        "ageGroup": ageGroup,
        "gender": gender,
        "kind": kind
      }),
    });

    const data = await response.json();
    
    // 서버로부터 받은 데이터로 책 목록 업데이트
    updateBookList(data);
    
  } catch (error) {
    console.error('Error:', error);
    alert('책 추천을 가져오는 데 실패했습니다. 다시 시도해주세요.');
  }
});

function updateBookList(data) {
  const books = data.response.docs.map(doc => doc.doc);
  const bookGrid = document.getElementById('bookGrid');
  bookGrid.innerHTML = '';

  // Loop through each book and create a book item element
  books.forEach(book => {
    const bookElement = document.createElement('div');
    bookElement.className = 'book-item';
    bookElement.innerHTML = `
      <img src="${book.bookImageURL}" alt="${book.bookname}">
      <a href="/detail/${book.isbn13}">${book.bookname}</a>
      <p class="author">${book.authors}</p>
    `;
    bookGrid.appendChild(bookElement);
  });
}