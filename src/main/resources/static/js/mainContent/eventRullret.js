$(document).ready(function() {
    var gift;
    var rotationPos = [60, 120, 180, 240, 300, 360];
    var couponIds = [
        "355810397512",//500
        "994960561891",//1000
        "945187642528", //2000
        "873114667385", //3000
        "972349263653",//5000
        "121662522193" //무료
    ];

    function iniGame(num) {
        gift = num;
        gsap.to($(".board_on"), { duration: 0, rotation: rotationPos[gift] });
        gsap.from($(".board_on"), { duration: 5, rotation: -3000, onComplete: endGame, ease: "sine.out" });
        console.log("gift 숫자 : " + (gift + 1) + " rotationPos: " + rotationPos[gift]);
    }

    function endGame() {
        var couponId = couponIds[gift];
        var copImg = "img/cupon/cupon" + (gift + 1) + ".png";
        console.log("쿠폰 ID : " + couponId + ", 이미지 : " + copImg);

        $.ajax({
            url: '/api/coupons/' + couponId,
            method: 'GET',
            success: function(coupon) {
                displayCouponInfo(coupon, copImg);
            },
            error: function(xhr, status, error) {
                console.error('쿠폰 정보를 가져오는데 실패했습니다.', error);
                alert('쿠폰 정보를 가져오는데 실패했습니다. 다시 시도해 주세요.');
            }
        });
    }

    function displayCouponInfo(coupon, copImg) {
        var html = `
            <br>축하드립니다!<br>
            <span>${coupon.couponName}</span> 쿠폰에 당첨되셨습니다.<br>
            <strong>할인율: ${coupon.couponRate}배</strong><br>
            유효기간: ${formatDate(coupon.startDate)} ~ ${formatDate(coupon.endDate)}<br>
            <em>${coupon.couponDetail}</em><br>
            지금 바로 사용해보세요!
        `;

        $("#popup_gift .lottery_present").html(html);
        $('<img src="' + copImg + '" />').prependTo("#popup_gift .lottery_present");
        openPopup("popup_gift");
    }

    function formatDate(dateString) {
        var date = new Date(dateString);
        return date.getFullYear() + '-' + 
               String(date.getMonth() + 1).padStart(2, '0') + '-' + 
               String(date.getDate()).padStart(2, '0');
    }

    function openPopup(id) {
        $('#' + id).fadeIn(300);
    }

    function closePopup() {
        $('.popup').fadeOut(300);
    }

    $(".close").click(closePopup);

    var clicked = 0;
    $(".join").on("click", function() {
        if (clicked === 0) {
            iniGame(Math.floor(Math.random() * 6));
            clicked++;
        } else {
            alert("이미 이벤트에 참여하셨습니다.");
        }
    });
});