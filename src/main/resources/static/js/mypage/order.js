document.addEventListener("DOMContentLoaded", function() {
	const searchForm = document.getElementById("search-form2");
	const searchInput = document.getElementById("search");
	const orderList = document.querySelector(".order-list table tbody");

	if (!searchForm || !searchInput || !orderList) {
		console.error("필요한 DOM 요소를 찾을 수 없습니다.");
		return;
	}

	searchForm.addEventListener("submit", function(event) {
		event.preventDefault();
		const query = searchInput.value.toLowerCase();
		filterTable(query);
	});

	function filterTable(query) {
		const rows = orderList.querySelectorAll("tr");

		rows.forEach(row => {
			const bookName = row.querySelector(".order-info div")?.textContent.toLowerCase() || "";
			const orderDate = row.querySelector("td:first-child")?.textContent.toLowerCase() || "";
			const orderStatus = row.querySelector(".order-actions div")?.textContent.toLowerCase() || "";

			const shouldDisplay =
				bookName.includes(query) ||
				orderDate.includes(query) ||
				orderStatus.includes(query);

			row.style.display = shouldDisplay ? "" : "none";
		});
	}
	
	// 리뷰 작성 버튼 클릭 이벤트
    document.getElementById('writeReviewBtn').addEventListener('click', function() {
        console.log("리뷰 작성 버튼 클릭됨");
        document.getElementById('reviewModal').style.display = 'block';
    });

    // 모달 닫기 버튼 클릭 이벤트
    document.querySelector('.close2').addEventListener('click', function() {
        console.log("닫기 버튼 클릭됨");
        document.getElementById('reviewModal').style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function(event) {
        if (event.target == document.getElementById('reviewModal')) {
            document.getElementById('reviewModal').style.display = 'none';
        }
    });
    
    // 리뷰 폼 제출 이벤트
    document.getElementById('reviewForm').addEventListener('submit', function(e) {
        e.preventDefault();
        console.log("리뷰 폼 제출됨");

        const content = document.getElementById('reviewContent').value;
        const rate = document.querySelector('input[name="rate"]:checked').value;
        const isbn = document.getElementById('bookIsbn').value;

        console.log("폼에서 가져온 데이터:", { content, rate, isbn });

        if (!content || !rate) {
            console.error("리뷰 내용 또는 평점이 비어있습니다.");
            alert("리뷰 내용과 평점을 모두 입력해주세요.");
            return;
        }

        const reviewData = {
            reviewContent: content,
            rate: parseInt(rate)
        };

        console.log("서버로 전송할 데이터:", JSON.stringify(reviewData));

        fetch(`/detail/${isbn}/review`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify(reviewData)
        })
        .then(response => response.json())
        .then(data => {
            console.log('리뷰 제출 성공. 응답:', data);
            document.getElementById('reviewModal').style.display = 'none';
            addReviewToList(data);
            // 폼 리셋
            document.getElementById('reviewContent').value = '';
            document.querySelectorAll('input[name="rate"]').forEach(input => input.checked = false);
        })
        .catch(error => {
            console.error("리뷰 제출 실패. 오류:", error);
            alert("리뷰 작성에 실패했습니다. 다시 시도해 주세요.");
        });
    });
	
	// 평점 선택 시 시각적 피드백
    document.querySelectorAll('input[name="rate"]').forEach(input => {
        input.addEventListener('change', function() {
            const rate = this.value;
            document.querySelectorAll('.rating label').forEach(label => label.classList.remove('selected'));
            this.nextElementSibling.classList.add('selected');
            let prevSibling = this.nextElementSibling.previousElementSibling;
            while (prevSibling && prevSibling.tagName === 'LABEL') {
                prevSibling.classList.add('selected');
                prevSibling = prevSibling.previousElementSibling;
            }
        });
    });

    // 폼 입력 값 변경 시 실시간 유효성 검사
    document.getElementById('reviewContent').addEventListener('input', validateForm);
    document.querySelectorAll('input[name="rate"]').forEach(input => {
        input.addEventListener('change', validateForm);
    });

    function validateForm() {
        const content = document.getElementById('reviewContent').value;
        const rate = document.querySelector('input[name="rate"]:checked');
        document.getElementById('reviewSubmitBtn').disabled = !(content && rate);
    }

    // 리뷰를 목록에 추가하는 함수
    function addReviewToList(review) {
        const reviewHtml = `
            <div class="review-item">
                <div class="review-header">
                    <h3>${review.username}</h3>
                    <div class="stars-display">
                        <span class="star-rating" data-rating="${review.rate}"></span>
                    </div>
                </div>
                <p>${review.reviewContent}</p>
            </div>
        `;
        document.getElementById('reviewList').insertAdjacentHTML('afterbegin', reviewHtml);
        displayStars();
    }

    // 별점 표시 함수
    function displayStars() {
        document.querySelectorAll('.star-rating').forEach(span => {
            const rating = span.getAttribute('data-rating');
            span.textContent = '★'.repeat(rating) + '☆'.repeat(5 - rating);
        });
    }

    // 페이지 로드 시 초기 별점 표시
    displayStars();
    
});