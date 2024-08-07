
document.addEventListener('DOMContentLoaded', () => {
	const toggleSubmenu = (event) => {
		event.preventDefault();
		const submenu = event.target.nextElementSibling;
		if (submenu.style.display === 'none' || submenu.style.display === '') {
			submenu.style.display = 'block';
		} else {
			submenu.style.display = 'none';
		}
	};

	const menuItems = document.querySelectorAll('.menu');
	menuItems.forEach(item => {
		item.addEventListener('click', toggleSubmenu);
	});
});