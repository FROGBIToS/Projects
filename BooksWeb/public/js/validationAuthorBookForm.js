function validateForm(){
    const authorLastName = document.getElementById('AuthorLastName');
    const bookName = document.getElementById('BookName');
    const publisher = document.getElementById('Publisher');
    const amountCopy = document.getElementById('Amount_copy');

    const errorAuthorLastName = document.getElementById('errorAuthorLastName');
    const errorBookName = document.getElementById('errorBookName');
    const errorPublisher = document.getElementById('errorPublisher');
    const errorAmountCopy = document.getElementById('errorAmount_copy');
    const errorSummary = document.getElementById('errorSummary');


    resetErrors([authorLastName,bookName,publisher,amountCopy],[errorAuthorLastName,errorBookName,errorPublisher,errorAmountCopy],errorSummary);

    let valid = true;

    if (!checkRequired(authorLastName.value)){
        valid = false;
        authorLastName.classList.add("error-input");
        errorAuthorLastName.innerText = "Pole jest wymagane";
    }


    if (!checkRequired(bookName.value)){
        valid = false;
        bookName.classList.add("error-input");
        errorBookName.innerText = "Pole jest wymagane";
    }

    if (!checkRequired(publisher.value)){
        valid = false;
        publisher.classList.add("error-input");
        errorPublisher.innerText = "Pole jest wymagane";
    } else if(!checkTextLengthRange(publisher.value,2,40)){
        valid = false;
        publisher.classList.add("error-input");
        errorPublisher.innerText = "Pole powinno zawierać od 2 do 40 znaków";
    }

    if (!checkRequired(amountCopy.value)){

        valid = false;
        amountCopy.classList.add("error-input");
        errorAmountCopy.innerText = "Pole jest wymagane";
    } else if (!checkNumber(amountCopy.value)){
        valid = false;
        amountCopy.classList.add("error-input");
        errorAmountCopy.innerText = "Pole musi zawierać tylko liczbę całkowitą";
    } else if (!checkRangeNumber(amountCopy.value,0,1000000)){
        console.log(amountCopy.value.className);
        console.log("fsdfasfasdf");
        valid = false;
        amountCopy.classList.add("error-input");
        errorAmountCopy.innerText = "Liczba musi być większa równa 0 i mniejsza niż 1000000";
    }

    if (!valid){
        errorSummary.innerText = "Formularz zawiera błędy";
    }

    return valid;
}