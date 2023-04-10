const Joi = require('joi');

const errMessage = (errors) => {
    errors.forEach(err => {
        switch (err.code){
            case "string.empty":
                err.message = "Pole jest wymagane";
                break;
            case "string.email":
                err.message = `Pole musi zawierać prawidłowy email`;
                break;
            case "string.min":
                err.message = `Pole musi zawierać co najmniej ${err.local.limit} znaki`;
                break;
            case "string.max":
                err.message = `Pole musi zawierać co najwyżej ${err.local.limit} znaki`;
                break;

            default:
                break;
        }
    });
    return errors;
}

const authorSchema = Joi.object({
    Id_Author: Joi.number().optional().allow(""),
    FirstName: Joi.string().min(2).max(30).required().error(errMessage),
    LastName: Joi.string().min(2).max(30).required().error(errMessage),
    Email: Joi.string().email().min(5).max(50).required().error(errMessage)
});

module.exports = authorSchema;