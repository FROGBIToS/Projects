function resetErrors(inputs, errorTexts, errorInfo){
    for (let i=0; i<inputs.length; i++){
        inputs[i].classList.remove("error-input");
    }
    for (let i = 0; i < errorTexts.length; i++) {
        errorTexts[i].innerText = "";
    }
    errorInfo.innerText = "";
}

function checkRequired(value){
    if (!value){
        return false;
    }
    value = value.toString().trim();

    if (value === ""){
        return false;
    }
    return true;
}

function checkTextLengthRange(value,min,max){
    if (!value){
        return false;
    }
    value = value.toString().trim();
    const  length = value.length;
    if (max && length > max){
        return false;
    }
    if (min && length < min){
        return false;
    }
    return true;
}

function checkEmail(value){
    if (!value){
        return false;
    }
    value = value.toString().trim();
    const  reg = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;;
    return reg.test(value);
}

function checkDate(value){
    if (!value){
        return false;
    }
    const patternDate = /(\d{4})-(\d{2})-(\d{2})/;
    return patternDate.test(value)
}

function checkYear(value){
    if (!value){
        return false;
    }
    const minYear = 1200;
    const maxYear = 2040;
    const valueDate = new Date(value);
    console.log(valueDate.getFullYear());

    if (valueDate.getFullYear() >= maxYear){
        return false;
    }
    if (valueDate.getFullYear() <= minYear){
        return false;
    }

    return true;

}

function checkRangeNumber(value,min,max){
    if (!value){
        return false;
    }
    if (value >= max){
        return false;
    }
    if (value <= min){
        return false;
    }
    return true;
}

function checkNumber(value) {
    if (!value){
        return false;
    }
    if (isNaN(value)){
        return false;
    }
    return true;
}