function addToWishlist(isbn) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

   // console.log("클라이언트: 위시리스트에 책 추가 요청 - ISBN: " + isbn);

    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken
        },
        body: new URLSearchParams({isbn: isbn})
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text) });
        }
        return response.text();
    })
    .then(data => {
        console.log('클라이언트: 성공:', data);
        alert("위시리스트에 추가되었습니다.");  // 성공 메시지 alert
        // UI 업데이트 로직
        const heartButton = document.querySelector(`button[data-isbn="${isbn}"] i`);
        if (heartButton) {
            heartButton.classList.remove('far');
            heartButton.classList.add('fas');
        }
    })
    .catch((error) => {
        console.error('클라이언트: 오류:', error);
        alert('위시리스트 추가 중 오류가 발생했습니다: ' + error.message);  // 에러 메시지 alert
    });
}

// HTML에서 하트 버튼에 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const wishButtons = document.querySelectorAll('.wish-button');
    wishButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const isbn = this.getAttribute('data-isbn');
            addToWishlist(isbn);
        });
    });
});