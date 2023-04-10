const Joi = require('joi');

const errMessage = (errors) => {
    errors.forEach(err => {
        switch (err.code){
            case "string.empty":
                err.message = "Pole jest wymagane";
                break;
            case "date.base":
                err.message = "Pole jest wymagane";
                break;
            case "string.min":
                err.message = `Pole musi zawierać co najmniej ${err.local.limit} znaki`;
                break;
            case "string.max":
                err.message = `Pole musi zawierać co najwyżej ${err.local.limit} znaki`;
                break;
            case "date.min":
                err.message = `Pole musi zawierać date wyższą niż ${err.local.limit.toISOString().split('T') [0]}`;
                break;
            case "date.max":
                err.message = `Pole musi zawierać date niższą niż ${err.local.limit.toISOString().split('T') [0]}`;
                break;
            default:
                break;
        }
    });
    return errors;
}

const bookSchema = Joi.object({
    Id_Book: Joi.number().optional().allow(""),
    Name: Joi.string().min(2).max(25).required().error(errMessage),
    Writting_date: Joi.date().min('1200-01-01').max('2040-01-01').required().error(errMessage),
    Language: Joi.string().min(2).max(30).required().error(errMessage),
    Is_Bestseller: Joi.number().optional().allow('')
});

module.exports = bookSchema;