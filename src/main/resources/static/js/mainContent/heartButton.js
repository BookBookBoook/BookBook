$(document).ready(function() {
    $('.heart-button').on('click', function() {
        var $button = $(this);
        var $bookItem = $button.closest('li');
        var bookData = {
            isbn: $bookItem.find('a[name="title"]').attr('href').split('/').pop(),
            title: $bookItem.find('a[name="title"]').text(),
            author: $bookItem.find('a[name="authors"]').text(),
            price: $bookItem.find('h5[name="price"]').text(),
            description: $bookItem.find('h6[name="content"]').text(),
            image: $bookItem.find('img[name="thumbnail"]').attr('src')
        };

        $.ajax({
            url: '/api/favorites',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(bookData),
            success: function(response) {
                console.log('Success:', response);
                alert('책이 성공적으로 즐겨찾기에 추가되었습니다!');
                
                // 하트 버튼 스타일 변경
                $button.addClass('active');
                $button.find('i').removeClass('far').addClass('fas');
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                var errorMessage = xhr.responseText ? xhr.responseText : '알 수 없는 오류가 발생했습니다.';
                alert('즐겨찾기 추가 중 오류가 발생했습니다. ' + errorMessage);
            }
        });
    });
});