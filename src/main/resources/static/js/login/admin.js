document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');
    const customerTable = document.getElementById('customerTable').getElementsByTagName('tbody')[0];

    // 가상의 고객 데이터
    const customers = [
        { id: 1, name: "홍길동", email: "hong@example.com", phone: "010-1234-5678", joinDate: "2023-01-15" },
        { id: 2, name: "김철수", email: "kim@example.com", phone: "010-2345-6789", joinDate: "2023-02-20" },
        { id: 3, name: "이영희", email: "lee@example.com", phone: "010-3456-7890", joinDate: "2023-03-25" },
        // 더 많은 고객 데이터를 추가할 수 있습니다.
    ];

    // 가상의 리뷰 데이터
    const reviews = {
        1: [
            { id: 1, product: "상품 A", content: "좋아요!", rating: 5, date: "2023-01-16" },
            { id: 2, product: "상품 B", content: "보통이에요.", rating: 3, date: "2023-01-17" }
        ],
        2: [
            { id: 3, product: "상품 C", content: "별로예요.", rating: 2, date: "2023-02-21" }
        ],
        3: [
            { id: 4, product: "상품 D", content: "정말 좋습니다.", rating: 5, date: "2023-03-26" }
        ],
    };

    function displayCustomers(customersToShow) {
        customerTable.innerHTML = '';
        customersToShow.forEach(customer => {
            const row = customerTable.insertRow();
            row.innerHTML = `
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.email}</td>
                <td>${customer.phone}</td>
                <td>${customer.joinDate}</td>
                <td>
                    <button class="action-button">수정</button>
                    <button class="action-button delete-button">삭제</button>
                </td>
                <td><a href="/review"><button class="review-button">작성 리뷰</button</a></td>
            `;
        });
    }

    function searchCustomers() {
        const searchTerm = searchInput.value.toLowerCase();
        const filteredCustomers = customers.filter(customer => 
            customer.name.toLowerCase().includes(searchTerm) ||
            customer.email.toLowerCase().includes(searchTerm) ||
            customer.phone.includes(searchTerm)
        );
        displayCustomers(filteredCustomers);
    }

    // 리뷰 보기 함수
    window.showReviews = function(customerId) {
        const reviewData = reviews[customerId] || [];
        const reviewContent = reviewData.map(review => `
            <div>
                <strong>${review.product}</strong>: ${review.content} (평점: ${review.rating}, 작성일: ${review.date})
            </div>
        `).join('');

        // 리뷰 다이얼로그 생성
        const reviewDialog = document.createElement('div');
        reviewDialog.innerHTML = `
            <div style="border: 1px solid #ddd; padding: 10px; background: #fff; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1000;">
                <h2>리뷰 목록</h2>
                <div>${reviewContent || '리뷰가 없습니다.'}</div>
                <button onclick="closeReviewDialog(this)">닫기</button>
            </div>
            <div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); z-index: 999;"></div>
        `;
        document.body.appendChild(reviewDialog);
    };

    // 리뷰 다이얼로그 닫기
    window.closeReviewDialog = function(button) {
        document.body.removeChild(button.parentElement.parentElement);
    };

    searchButton.addEventListener('click', searchCustomers);
    searchInput.addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            searchCustomers();
        }
    });

    // 초기 고객 목록 표시
    displayCustomers(customers);
});
