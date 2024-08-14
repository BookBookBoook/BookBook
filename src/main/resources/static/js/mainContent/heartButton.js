function addToFavorites(form, event) {
     event.preventDefault();
    const isbn = form.querySelector('input[name="isbn"]').value;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    console.log("Client: Sending request to add book to favorites: " + isbn);

    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken
        },
        body: new URLSearchParams(new FormData(form))
    })
    .then(data => {
        console.log('Client: Success:', data);
        // 성공 시 UI 업데이트
        form.querySelector('.heart-button i').classList.remove('far');
        form.querySelector('.heart-button i').classList.add('fas');
    })
    .catch((error) => {
        console.error('Client: Error:', error);
        alert('즐겨찾기 추가 중 오류가 발생했습니다: ' + error.message);
    });

    return false;
}