//장바구니에 담은 상품 삭제
function deleteCartItem(cartDetailNum) {
	//csrf 토큰
	const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
	const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
	
    if (confirm("정말로 삭제하시겠습니까?")) { // 사용자에게 확인 요청
        const deleteUrl = `/cart/${cartDetailNum}`;

        fetch(deleteUrl, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            }
        })
        .then(response => {
		    if (response.ok) {
		        alert("삭제되었습니다.");
		    	window.location.reload(); // 페이지를 새로고침하여 변경 사항 반영
		    } else {
		        throw new Error('삭제 과정에서 오류가 발생했습니다.. '); //http 오류
		    }
		})
		.catch(error => {
		    console.error('Error:', error);
		    alert("삭제 과정에서 오류가 발생했습니다. ");
		});
    }
}


//전체 상품 구매 (장바구니에 담은 상품 전체 결제로 보내기)
function paymentAll() {
    // CSRF 토큰
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // cartList 내의 모든 tr 요소에서 cartDetailNum 추출
    const cartItems = document.querySelectorAll('#cartList tr');
    const cartDetailNums = Array.from(cartItems).map(item => item.getAttribute('data-cart-detail-num'));

    fetch('/cart/all', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [header]: token
        },
        body: JSON.stringify({ cartDetailNums })  // cartDetailNums 배열을 JSON으로 변환
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('장바구니에 상품이 없습니다.');
        }
        return response.text(); // 응답을 텍스트로 변환
    })
    .then(merchantUid => {
        // 응답에서 orderNum 추출 후 결제 페이지로 리디렉션
        window.location.href = `/payment/${merchantUid}`;
    })
    .catch(error => {
        alert(error);
    });
}


// 최종 결제 금액 계산
document.addEventListener("DOMContentLoaded", function() {
    let totalAmount = 0;
    let cartItems = document.querySelectorAll("tbody tr"); // cartItems에 tbody tr 요소를 모두 가져옴

    // 모든 테이블 행(tbody tr)을 반복
    cartItems.forEach(function(row) {
        // 각 행의 네 번째 열(td:nth-child(4))에서 결제 금액 텍스트를 가져옴
        const itemAmountText = row.querySelector("td:nth-child(4)").textContent;
        
        // 결제 금액에서 숫자만 추출하여 정수로 변환
        const itemAmount = parseInt(itemAmountText.replace(/[^0-9]/g, ''));

        // itemAmount가 NaN이 아닌 경우 totalAmount에 더함
        totalAmount += isNaN(itemAmount) ? 0 : itemAmount;
    });

    // 최종 결제 금액을 포맷하여 페이지에 표시
    document.querySelector(".all-price").textContent = 
        new Intl.NumberFormat('ko-KR').format(totalAmount) + " 원";
    
    // 리스트가 비어있지 않은 경우에만 배송비 3000원을 추가
    let finalAmount = totalAmount;
    if (cartItems.length > 0) {
        finalAmount += 3000; // 배송비 추가
    }
    
    document.querySelector(".final-amount").textContent = 
        new Intl.NumberFormat('ko-KR').format(finalAmount) + "원";
});


//수량변경
document.querySelectorAll('.quantity-control').forEach(function(control) {
    const decrementButton = control.querySelector('.decrement');
    const incrementButton = control.querySelector('.increment');
    const quantityInput = control.querySelector('.quantity-input');
    const priceElement = document.querySelector('.price');

    function updatePrice() {
        const quantity = parseInt(quantityInput.value);
        const totalPrice = priceElement * quantity;
        priceElement.textContent = `${totalPrice.toLocaleString()}원`;
    }

    decrementButton.addEventListener('click', function() {
        let currentValue = parseInt(quantityInput.value);
        if (currentValue > parseInt(quantityInput.min)) {
            quantityInput.value = currentValue - 1;
            updatePrice();
        }
    });

    incrementButton.addEventListener('click', function() {
        let currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
        updatePrice();
    });

    updatePrice(); // 페이지 로드 시 초기 가격 설정
});


