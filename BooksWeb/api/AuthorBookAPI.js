const AuthorBookRepository = require('../repository/mysql2/AuthorBookRepository');

exports.getAuthorBooks = (req,res,next) => {
    AuthorBookRepository.getAuthorBooks()
        .then(authorBooks => {
            res.status(200).json(authorBooks);
        }).catch(err => {
            console.log(err);
    });
};

exports.getAuthorBookById = (req, res, next) => {
    const authorBookId = req.params.authorBookId;
    AuthorBookRepository.getAuthorBookbyId(authorBookId)
        .then(authorBook => {
            if(!authorBook){
                res.status(404).json({message: 'authorBook with id: ' + authorBookId + 'not found'});
            }else{
                res.status(200).json(authorBook);
            }
        });
};

exports.createAuthorBook = (req, res, next) => {
    AuthorBookRepository.createAuthorBook(req.body)
        .then(newObj => {
            res.status(201).json(newObj);
        }).catch(err => {
            if (!err.statusCode){
                err.statusCode = 500;
            }
            next(err);
    });
};

exports.updateAuthorBook = (req,res,next) => {
    const authorBookId = req.params.authorBookId;
    AuthorBookRepository.updateAuthorBook(authorBookId, req.body)
        .then( result => {
            res.status(200).json({message: 'AuthorBook updated', authorBook: result});
        }).catch(err => {
        if (!err.statusCode){
            err.statusCode = 500;
        }
        next(err);
    });
};

exports.deleteAuthorBook = (req,res,next) => {
    const authorBookId = req.params.authorBookId;
    AuthorBookRepository.deleteAuthorBook(authorBookId)
        .then(result => {
            res.status(200).json({message: 'AuthorBook was deleted', authorBook: result});
        }).catch(err => {
        if (!err.statusCode){
            err.statusCode = 500;
        }
        next(err);
    });
};