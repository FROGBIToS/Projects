const Joi = require('joi');

const errMessage = (errors) => {
    errors.forEach(err => {
        switch (err.code){
            case "string.empty":
                err.message = "Pole jest wymagane";
                break;
            case "number.base":
                err.message = "Pole jest wymagane";
                break;
            case "string.min":
                err.message = `Pole musi zawierać co najmniej ${err.local.limit} znaki`;
                break;
            case "string.max":
                err.message = `Pole musi zawierać co najwyżej ${err.local.limit} znaki`;
                break;
            case "number.min":
                err.message = `Pole musi zawierać co najmniej znaczenie ${err.local.limit}`;
                break;
            case "number.max":
                err.message = `Pole musi zawierać co najwyżej znaczenie ${err.local.limit}`;
                break;
            default:
                break;
        }
    });
    return errors;
}

const authorBookSchema = Joi.object({
    Id_Author_Book: Joi.number().optional().allow(""),
    Id_Author: Joi.number().required().error(errMessage),
    Id_Book: Joi.number().required().error(errMessage),
    Publisher: Joi.string().min(2).max(40).required().error(errMessage),
    Amount_copy: Joi.number().min(0).max(1000000).required().error(errMessage),
    Is_Sold: Joi.number().required().error(errMessage)
});

module.exports = authorBookSchema;