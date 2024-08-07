$(document).ready(function() {
    // 리뷰 데이터 (예시) - 좋아요 수 추가
    const reviews = [
        { id: 1, name: "홍길동", content: "좋은 제품입니다.", rating: 5, isBuyer: true, likes: 10 },
        { id: 2, name: "김철수", content: "배송이 빨라요.", rating: 4, isBuyer: true, likes: 5 },
        { id: 3, name: "이영희", content: "품질이 좋아 보입니다.", rating: 3, isBuyer: false, likes: 2 }
    ];

    // 별점 표시 함수
    function getStars(rating) {
        return '★'.repeat(rating) + '☆'.repeat(5 - rating);
    }

    // 리뷰 목록 표시 함수
    function displayReviews(reviewsToShow) {
        const $reviewList = $('#reviewList');
        $reviewList.empty();
        
        reviewsToShow.forEach(review => {
            $reviewList.append(`
                <div class="review-item" data-id="${review.id}">
                    <div class="review-header">
                        <h3>${review.name}</h3>
                        <div class="stars-display">${getStars(review.rating)}</div>
                    </div>
                    <p>${review.content}</p>
                    <button class="like-button">
                        <i class="fas fa-thumbs-up"></i>
                        <span class="like-count">${review.likes}</span>
                    </button>
                </div>
            `);
        });
    }

    // 초기 리뷰 목록 표시
    displayReviews(reviews);

    // 전체 리뷰 버튼 클릭 이벤트
    $('#allReviewsBtn').click(function() {
        $(this).addClass('active');
        $('#buyerReviewsBtn').removeClass('active');
        displayReviews(reviews);
    });

    // 구매자 리뷰 버튼 클릭 이벤트
    $('#buyerReviewsBtn').click(function() {
        $(this).addClass('active');
        $('#allReviewsBtn').removeClass('active');
        const buyerReviews = reviews.filter(review => review.isBuyer);
        displayReviews(buyerReviews);
    });

    // 리뷰 작성 버튼 클릭 이벤트
    $('#writeReviewBtn').click(function() {
        $('#reviewModal').css('display', 'block');
    });

    // 모달 닫기 버튼 클릭 이벤트
    $('.close').click(function() {
        $('#reviewModal').css('display', 'none');
    });

    // 좋아요 버튼 클릭 이벤트
    $(document).on('click', '.like-button', function() {
        const reviewId = $(this).closest('.review-item').data('id');
        const review = reviews.find(r => r.id === reviewId);
        if (review) {
            review.likes++;
            $(this).find('.like-count').text(review.likes);
        }
    });

    // 리뷰 제출 이벤트
    $('#reviewForm').submit(function(e) {
        e.preventDefault();
        const name = $('#name').val();
        const content = $('#content').val();
        const rating = $('input[name="rating"]:checked').val();

        // 새 리뷰 추가 (실제로는 서버에 전송해야 함)
        reviews.push({
            id: reviews.length + 1,
            name,
            content,
            rating: parseInt(rating),
            isBuyer: true,
            likes: 0  // 초기 좋아요 수는 0
        });

        // 폼 초기화 및 모달 닫기
        this.reset();
        $('#reviewModal').css('display', 'none');

        // 리뷰 목록 갱신
        displayReviews(reviews);
    });
});