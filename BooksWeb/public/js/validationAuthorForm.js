function validateForm(){
    const firstName = document.getElementById('FirstName');
    const lastName = document.getElementById('LastName');
    const email = document.getElementById('Email');

    const errorFirstName = document.getElementById('errorFirstName');
    const errorLastName = document.getElementById('errorLastName');
    const errorEmail = document.getElementById('errorEmail');
    const errorSummary = document.getElementById('errorSummary');


    resetErrors([firstName,lastName,email],[errorFirstName,errorLastName,errorEmail],errorSummary);

    let valid = true;

    if (!checkRequired(firstName.value)){
        valid = false;
        firstName.classList.add("error-input");
        errorFirstName.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(firstName.value, 2, 30)){
        valid = false;
        firstName.classList.add("error-input");
        errorFirstName.innerText = "Pole powinno zawierać od 2 do 30 znaków";
    }

    if (!checkRequired(lastName.value)){
        valid = false;
        lastName.classList.add("error-input");
        errorLastName.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(lastName.value, 2, 30)){
        valid = false;
        lastName.classList.add("error-input");
        errorLastName.innerText = "Pole powinno zawierać od 2 do 30 znaków";
    }

    if (!checkRequired(email.value)){
        valid = false;
        email.classList.add("error-input");
        errorEmail.innerText = "Pole jest wymagane";
    } else if(!checkEmail(email.value)){
        valid = false;
        email.classList.add("error-input");
        errorEmail.innerText = "Pole powinno zawierać prawidłowy adres email";
    } else if (!checkTextLengthRange(email.value, 5, 50)){
        valid = false;
        email.classList.add("error-input");
        errorEmail.innerText = "Pole powinno zawierać od 5 do 50 znaków";
    }

    if (!valid){
        errorSummary.innerText = "Formularz zawiera błędy";
    }

    return valid;
}