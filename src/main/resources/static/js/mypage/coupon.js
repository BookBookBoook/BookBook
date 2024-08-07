function openTab(evt, tabName) {
	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tab");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	document.getElementById(tabName).style.display = "block";
	evt.currentTarget.className += " active";
}

// Show the first tab by default
document.addEventListener("DOMContentLoaded", function() {
	document.querySelector(".tab").click();
});

document.addEventListener('DOMContentLoaded', (event) => {
    const allBtn = document.getElementById('all-btn');
    const couponIcons = document.querySelectorAll('.coupon .discount i');

    allBtn.addEventListener('click', () => {
        couponIcons.forEach(icon => {
            icon.classList.toggle('selected');
        });
    });

    couponIcons.forEach(icon => {
        icon.addEventListener('click', () => {
            icon.classList.toggle('selected');
        });
    });
});