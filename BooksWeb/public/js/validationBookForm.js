function validateForm(){
    const nazwaBookInput = document.getElementById('Name');
    const writingDateBookInput = document.getElementById('Writting_date');
    const languageBookInput = document.getElementById('Language');

    const errorNazwaBook = document.getElementById('errorName');
    const errorwritingDateBook = document.getElementById('errorWritting_date');
    const errorlanguageBook = document.getElementById('errorLanguage');
    const errorSummary = document.getElementById('errorSummary');


    resetErrors([nazwaBookInput,writingDateBookInput,languageBookInput],[errorNazwaBook,errorwritingDateBook,errorlanguageBook],errorSummary);

    let valid = true;

    if (!checkRequired(nazwaBookInput.value)){
        valid = false;
        nazwaBookInput.classList.add("error-input");
        errorNazwaBook.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(nazwaBookInput.value, 2, 25)){
        valid = false;
        nazwaBookInput.classList.add("error-input");
        errorNazwaBook.innerText = "Pole powinno zawierać od 2 do 25 znaków";
    }

    if (!checkRequired(writingDateBookInput.value)){
        valid = false;
        writingDateBookInput.classList.add("error-input");
        errorwritingDateBook.innerText = "Pole jest wymagane";
    } else if (!checkDate(writingDateBookInput.value)){
        valid = false;
        writingDateBookInput.classList.add("error-input");
        errorwritingDateBook.innerText = "Pole powinno zawierać date w formacie dd.MM.yyyy";
    } else if (!checkYear(writingDateBookInput.value)){
        valid = false;
        writingDateBookInput.classList.add("error-input");
        errorwritingDateBook.innerText = "Rok musi być wyższy niż 1200 i niższy niż 2040";
    }

    if (!checkRequired(languageBookInput.value)){
        valid = false;
        languageBookInput.classList.add("error-input");
        errorlanguageBook.innerText = "Pole jest wymagane";
    } else if (!checkTextLengthRange(languageBookInput.value, 2, 25)){
        valid = false;
        languageBookInput.classList.add("error-input");
        errorlanguageBook.innerText = "Pole powinno zawierać od 2 do 30 znaków";
    }

    if (!valid){
        errorSummary.innerText = "Formularz zawiera błędy";
    }

    return valid;
}