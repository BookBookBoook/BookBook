// 리뷰 데이터 배열
let reviews = [
    { id: 1, title: "좋은 제품", content: "매우 만족스러운 제품입니다.", rating: 5, likes: 10, isPurchased: true },
    { id: 2, title: "그저 그러네요", content: "기대만큼은 아니었습니다.", rating: 3, likes: 2, isPurchased: false }
];

// DOM 요소 선택
const reviewForm = document.getElementById('reviewForm');
const reviewsContainer = document.getElementById('reviewsContainer');
const openReviewFormButton = document.getElementById('openReviewForm');
const reviewFormContainer = document.querySelector('.review-form');

// 리뷰 표시 함수
function displayReviews(filter = 'all') {
    reviewsContainer.innerHTML = '';
    const filteredReviews = filter === 'all' ? reviews : reviews.filter(review => review.isPurchased);

    filteredReviews.forEach(review => {
        const reviewElement = document.createElement('div');
        reviewElement.className = 'review-item';
        reviewElement.innerHTML = `
            <div class="review-header">
                <h3 class="review-item-title">${review.title}</h3>
                <span class="review-item-rating">${'★'.repeat(review.rating)}${'☆'.repeat(5-review.rating)}</span>
            </div>
            <p class="review-item-content">${review.content}</p>
            <button class="review-like-button" data-id="${review.id}">👍 ${review.likes}</button>
        `;
        reviewsContainer.appendChild(reviewElement);
    });
}

// 리뷰 폼 열기 버튼 이벤트 리스너

openReviewFormButton.addEventListener('click', function() {
    reviewFormContainer.style.display = 
        reviewFormContainer.style.display === 'none' ? 'block' : 'none';
    this.textContent = 
        reviewFormContainer.style.display === 'none' ? '리뷰 작성' : '작성 취소';
});

// 리뷰 제출 이벤트 리스너
reviewForm.addEventListener('submit', function(e) {
    e.preventDefault();
    const title = document.getElementById('reviewTitle').value;
    const content = document.getElementById('reviewContent').value;
    const rating = document.querySelector('input[name="rating"]:checked');

    if (!rating) {
        alert('별점을 선택해주세요.');
        return;
    }

    const newReview = {
        id: reviews.length + 1,
        title,
        content,
        rating: parseInt(rating.value),
        likes: 0,
        isPurchased: Math.random() < 0.5 // 임의로 구매 여부 설정
    };

    reviews.push(newReview);
    displayReviews();
    this.reset();
    reviewFormContainer.style.display = 'none';
    openReviewFormButton.textContent = '리뷰 작성';
});

// 탭 전환 이벤트 리스너
document.querySelector('.review-tabs').addEventListener('click', function(e) {
    if (e.target.classList.contains('review-tab')) {
        document.querySelectorAll('.review-tab').forEach(tab => tab.classList.remove('active'));
        e.target.classList.add('active');
        displayReviews(e.target.dataset.tab);
    }
});

// 좋아요 버튼 이벤트 리스너
reviewsContainer.addEventListener('click', function(e) {
    if (e.target.classList.contains('review-like-button')) {
        const reviewId = Number(e.target.dataset.id);
        const review = reviews.find(r => r.id === reviewId);
        if (review) {
            review.likes++;
            e.target.textContent = `👍 ${review.likes}`;
        }
    }
});

// 초기 리뷰 표시
displayReviews();