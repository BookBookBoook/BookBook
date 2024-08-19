$(document).ready(function() {
    const isbn = /*[[${book.isbn}]]*/ '';

    // 리뷰 작성 버튼 클릭 이벤트
    $('#writeReviewBtn').click(function() {
        $('#reviewModal').css('display', 'block');
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('.close').click(function() {
        $('#reviewModal').css('display', 'none');
    });

    // 리뷰 폼 제출 이벤트
    $('#reviewForm').submit(function(e) {
        e.preventDefault();
        const content = $('#reviewContent').val();
        const rate = $('input[name="rate"]:checked').val();
        
        $.ajax({
            url: `/detail/${isbn}/review`,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ content: content, rate: rate }),
            success: function(response) {
                $('#reviewModal').css('display', 'none');
                // 새 리뷰를 목록에 추가하는 로직
                addReviewToList(response);
            },
            error: function(xhr, status, error) {
                console.error("리뷰 작성에 실패했습니다:", error);
                alert("리뷰 작성에 실패했습니다. 다시 시도해 주세요.");
            }
        });
    });

    function addReviewToList(review) {
        // 새 리뷰를 목록에 추가하는 로직
        const reviewHtml = `
            <div class="review-item">
                <div class="review-header">
                    <h3>${review.userName}</h3>
                    <div class="stars-display">${'★'.repeat(review.rate)}${'☆'.repeat(5-review.rate)}</div>
                </div>
                <p>${review.content}</p>
                <button class="like-button" data-review-id="${review.id}">
                    <i class="fas fa-thumbs-up"></i>
                    <span class="like-count">${review.likeCount}</span>
                </button>
            </div>
        `;
        $('#reviewList').prepend(reviewHtml);
    }

    // 페이지 로드 시 콘솔에 로그 출력
    console.log("Review script loaded.");
});