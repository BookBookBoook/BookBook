function editInfo() {
	const fields = ['representativeName', 'managerName', 'contactNumber', 'email','faxNum'];
	fields.forEach(id => {
		const element = document.getElementById(id);
		const value = element.innerText;
		element.outerHTML = `<input type="text" name="${id}" id="${id}" value="${value}" />`;
	});
	document.querySelector('.account-delete button').style.display = 'none';
	document.querySelector('.account-delete + .account-delete').style.display = 'flex';
}

function submitForm(event) {
	event.preventDefault();
	const formData = new FormData(document.getElementById('publisherForm'));
	fetch('/updatePublisherInfo', {
		method: 'POST',
		body: formData
	})
		.then(response => response.json())
		.then(data => {
			alert('정보가 성공적으로 수정되었습니다.');
			location.reload();
		})
		.catch(error => {
			console.error('Error:', error);
		});
}