$(document).ready(function() {
	var gift;
	var present = [1, 2, 3, 4, 5, 6];
	var rotationPos = [60, 120, 180, 240, 300, 360];

	function iniGame(num) {
		gift = num;
		gsap.to($(".board_on"), { duration: 0, rotation: rotationPos[gift] });
		gsap.from($(".board_on"), { duration: 5, rotation: -3000, onComplete: endGame, ease: "sine.out" });
		console.log("gift 숫자 : " + (gift + 1) + " rotationPos: " + rotationPos[gift]);
	}

	function endGame() {
		var copImg = "img/cupon/cupon" + (gift + 1) + ".png";
		console.log("이미지 : " + copImg);

		$("#popup_gift .lottery_present").html(function() {
			return "<br>축하드립니다.<br>" +
				present[gift] + " 룰렛 상품<br>" +
				(gift + 1) + "번 당첨 되셨습니다.";
		});

		$('<img src="' + copImg + '" />').prependTo("#popup_gift .lottery_present");
		setTimeout(function() { openPopup("popup_gift"); }, 1000);
	}

	$(".popup .btn").on("click", function() {
		location.reload();
	});

	function openPopup(id) {
		closePopup();
		$('.popup').slideUp(0);
		var popupid = id;
		$('body').append('<div id="fade"></div>');
		$('#fade').css({
			'filter': 'alpha(opacity=60)'
		}).fadeIn(300);
		var popuptopmargin = ($('#' + popupid).height()) / 2;
		var popupleftmargin = ($('#' + popupid).width()) / 2;
		$('#' + popupid).css({
			'margin-left': -popupleftmargin
		});
		$('#' + popupid).slideDown(500);
	}

	function closePopup() {
		$('#fade').fadeOut(300, function() {
			// $(".player").html('');
		});
		$('.popup').slideUp(400);
		return false;
	}

	$(".close").click(closePopup);

	var clicked = 0;
	$(".join").on("mousedown", function() {
		if (clicked <= 0) {
			iniGame(Math.floor(Math.random() * 6));
		}
		else if (clicked >= 1) {
			event.preventDefault();
			alert("이벤트 참여 하셨습니다.");
		}
		clicked++;
	});
});