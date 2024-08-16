//최종 결제 금액 계산
document.addEventListener("DOMContentLoaded", function() {
    let totalAmount = 0;
    // 모든 테이블 행(tbody tr)을 반복
    document.querySelectorAll("tbody tr").forEach(function(row) {
        // 각 행의 네 번째 열(td:nth-child(4))에서 결제 금액 텍스트를 가져옴
        const itemAmountText = row.querySelector("td:nth-child(4)").textContent;
        
        // 결제 금액에서 숫자만 추출하여 정수로 변환
        const itemAmount = parseInt(itemAmountText.replace(/[^0-9]/g, ''));

        // itemAmount가 NaN이 아닌 경우 totalAmount에 더함
        totalAmount += isNaN(itemAmount) ? 0 : itemAmount;
    });

    // 최종 결제 금액을 포맷하여 페이지에 표시
    document.querySelector(".all-price").textContent = 
        new Intl.NumberFormat('ko-KR').format(totalAmount) + "원";
        
    //배송비를 추가하여 최종 결제 금액 생성
    document.querySelector(".final-amount").textContent = 
        new Intl.NumberFormat('ko-KR').format(totalAmount+3000) + "원";
});

//장바구니에 담은 상품 삭제
function deleteCartItem(cartDetailNum) {
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
                //window.location.reload(); // 페이지를 새로고침하여 변경 사항 반영
            } else {
                alert("삭제에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("오류가 발생했습니다.");
        });
    }
}

