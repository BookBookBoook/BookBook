
document.addEventListener('DOMContentLoaded', function() {
	const selectAllCheckbox = document.getElementById('selectAllCheckbox');
	const bookCheckboxes = document.querySelectorAll('.bookCheckbox');

	selectAllCheckbox.addEventListener('change', function() {
		bookCheckboxes.forEach(checkbox => {
			checkbox.checked = this.checked;
		});
	});

	bookCheckboxes.forEach(checkbox => {
		checkbox.addEventListener('change', function() {
			const allChecked = Array.from(bookCheckboxes).every(cb => cb.checked);
			selectAllCheckbox.checked = allChecked;
		});
	});
});
