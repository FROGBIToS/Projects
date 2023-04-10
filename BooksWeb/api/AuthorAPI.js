const AuthorRepository = require('../repository/mysql2/AuthorRepository');

exports.getAuthors = (req,res,next) => {
    AuthorRepository.getAuthors()
        .then(authors => {
            res.status(200).json(authors);
        }).catch(err => {
            console.log(err);
    });
};

exports.getAuthorById = (req, res, next) => {
    const authorId = req.params.authorId;
    AuthorRepository.getAuthorbyId(authorId)
        .then(author => {
            if(!author){
                res.status(404).json({message: 'author with id: ' + authorId + 'not found'});
            }else{
                res.status(200).json(author);
            }
        });
};

exports.createAuthor = (req, res, next) => {
    AuthorRepository.createAuthor(req.body)
        .then(newObj => {
            res.status(201).json(newObj);
        }).catch(err => {
            if (!err.statusCode){
                err.statusCode = 500;
            }
            next(err);
    });
};

exports.updateAuthor = (req,res,next) => {
    const authorId = req.params.authorId;
    AuthorRepository.updateAuthor(authorId, req.body)
        .then( result => {
            res.status(200).json({message: 'Author updated', author: result});
        }).catch(err => {
        if (!err.statusCode){
            err.statusCode = 500;
        }
        next(err);
    });
};

exports.deleteAuthor = (req,res,next) => {
    const authorId = req.params.authorId;
    AuthorRepository.deleteAuthor(authorId)
        .then(result => {
            res.status(200).json({message: 'Author was deleted', author: result});
        }).catch(err => {
        if (!err.statusCode){
            err.statusCode = 500;
        }
        next(err);
    });
};