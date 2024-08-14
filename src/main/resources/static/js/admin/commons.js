document.addEventListener('DOMContentLoaded', function() {
	// Get the select-all checkbox
	const selectAllCheckbox = document.getElementById('select-all');
	// Get all individual item checkboxes
	const itemCheckboxes = document.querySelectorAll('.select-item');

	// Add an event listener to the select-all checkbox
	selectAllCheckbox.addEventListener('change', function() {
		// Update all item checkboxes based on the select-all checkbox status
		itemCheckboxes.forEach(checkbox => {
			checkbox.checked = selectAllCheckbox.checked;
		});
	});
});