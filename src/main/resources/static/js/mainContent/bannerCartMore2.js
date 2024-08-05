
$(document).ready(function() {
    const $slider = $('.slider');
    const $slides = $('.slide');
    const slideCount = $slides.length;
    let currentIndex = 0;

    function goToSlide(index) {
        if (index < 0) {
            index = slideCount - 1;
        } else if (index >= slideCount) {
            index = 0;
        }
        currentIndex = index;
        const offset = -index * 100;
        $slider.css('transform', `translateX(${offset}%)`);
    }

    function nextSlide() {
        goToSlide(currentIndex + 1);
    }

    function prevSlide() {
        goToSlide(currentIndex - 1);
    }

    $('.next').on('click', nextSlide);
    $('.prev').on('click', prevSlide);

    // 자동 슬라이드 설정 (5초마다)
    setInterval(nextSlide, 5000);
});