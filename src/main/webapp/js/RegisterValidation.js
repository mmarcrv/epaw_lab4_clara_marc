window.App = window.App || {};
App.initRegisterValidation = function (serverErrors)  {

	const form = document.getElementById('registerForm');
	const password = document.getElementById('password');
	const confirmPassword = document.getElementById('confirmPassword');
	const dateOfBirth = document.getElementById('dateOfBirth');
	const email = document.getElementById('email');
	const firstName = document.getElementById('firstName');
	const lastName = document.getElementById('lastName');
	const comarca = document.getElementById('comarca');

	// check passwords are equal
	confirmPassword.addEventListener('input', () => {
	  confirmPassword.setCustomValidity(
	    confirmPassword.value !== password.value ? "Passwords do not match." : ""
	  );
	});


	/**
	 * Validació de data de naixement.
	 * Comprova que la data no sia en el futur.
	 */
	dateOfBirth.addEventListener('change', () => {
	if (dateOfBirth.value) {
		const birthDate = new Date(dateOfBirth.value);
		const today = new Date();
		today.setHours(0, 0, 0, 0);

		if (birthDate > today) {
		dateOfBirth.setCustomValidity("La data de naixement no pot ser en el futur.");
		} else {
		dateOfBirth.setCustomValidity("");
		}
	}
	});

	/**
	 * Validació de format de correu electrònic.
	 * Afegim una validació extra a més de type="email".
	 */
	email.addEventListener('input', () => {
	const emailRegex = /^[A-Za-z0-9+_.-]+@(.+)$/;
	if (email.value && !emailRegex.test(email.value)) {
		email.setCustomValidity("Format de correu electrònic invàlid.");
	} else {
		email.setCustomValidity("");
	}
	});

	/**
	 * Validació de nombres (únicament lletres i espais).
	 */
	function validateNameField(field) {
	field.addEventListener('input', () => {
		const value = field.value;
		if (value && /[^a-zA-ZàáäâèéëêìíïîòóöôùúüûñçÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛÑÇ\s'-]/.test(value)) {
		field.setCustomValidity("El nom només pot contenir lletres, espais, apòstrofs i guions.");
		} else {
		field.setCustomValidity("");
		}
	});
	}

	validateNameField(firstName);
	validateNameField(lastName);



	Object.entries(serverErrors).forEach(([field, message]) => {
	  const input = document.getElementsByName(field)[0];
	  if (input) {
	    input.setCustomValidity(message);
	    input.reportValidity();
		input.addEventListener('input', () => {
		  input.setCustomValidity('');
		  input.reportValidity();
		});
	  }
	});

	// check before submit
	form.addEventListener('submit', event => {

	  confirmPassword.setCustomValidity(
		    confirmPassword.value != password.value ? "Passwords do not match." : ""
	  );
	  password.setCustomValidity(
		    password.value != confirmPassword.value ? "Passwords do not match." : ""
	  );

	  if (!form.checkValidity()) {
	    event.preventDefault();
	    form.reportValidity();
	  }
	});

}
