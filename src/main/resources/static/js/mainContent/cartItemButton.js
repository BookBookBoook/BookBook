function addToCartItems(form, event) {
     event.preventDefault();
    const isbn = form.querySelector('input[name="isbn"]').value;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

    console.log("Client: Sending request to add book to CartItems: " + isbn);

    fetch(form.action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken
        },
        body: new URLSearchParams(new FormData(form))
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Server responded with status: ' + response.status);
        }
        return response.text();
    })
    .then(data => {
        console.log('Client: Success: Book added to cart', data);
        // 성공 시 UI 업데이트
        const cartButton = form.querySelector('.cart-button');
        cartButton.textContent = '장바구니에 추가됨';
        cartButton.disabled = true;
        // 선택적: 애니메이션 효과 추가
        cartButton.classList.add('added-to-cart');
    })
    .catch((error) => {
        console.error('Client: Error:', error);
        alert('장바구니에 추가하는 중 오류가 발생했습니다: ' + error.message);
    });

    return false;
}