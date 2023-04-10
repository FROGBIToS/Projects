const BookRepository = require('../repository/mysql2/BookRepository');

exports.getBooks = (req,res,next) => {
    BookRepository.getBooks()
        .then(books => {
            res.status(200).json(books);
        }).catch(err => {
            console.log(err);
    });
};

exports.getBookById = (req, res, next) => {
    const bookId = req.params.bookId;
    BookRepository.getBookbyId(bookId)
        .then(book => {
            if(!book){
                res.status(404).json({message: 'Book with id: ' + bookId + 'not found'});
            }else{
                res.status(200).json(book);
            }
        });
};

exports.createBook = (req, res, next) => {
    BookRepository.createBook(req.body)
        .then(newObj => {
            res.status(201).json(newObj);
        }).catch(err => {
            if (!err.statusCode){
                err.statusCode = 500;
            }
            next(err);
    });
};

exports.updateBook = (req,res,next) => {
    const  bookId = req.params.bookId;
    BookRepository.updateBook(bookId, req.body)
        .then( result => {
            res.status(200).json({message: 'Book updated', book: result});
        }).catch(err => {
        if (!err.statusCode){
            err.statusCode = 500;
        }
        next(err);
    });
};

exports.deleteBook = (req,res,next) => {
    const bookId = req.params.bookId;
    BookRepository.deleteBook(bookId)
        .then(result => {
            res.status(200).json({message: 'Book was deleted', book: result});
        }).catch(err => {
        if (!err.statusCode){
            err.statusCode = 500;
        }
        next(err);
    });
};