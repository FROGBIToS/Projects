const BookRepository = require('../repository/mysql2/BookRepository');

exports.showBookList = (req,res,next) => {
    BookRepository.getBooks()
        .then(books => {
            res.render('pages/Book/list-book.ejs', {
                books: books,
                navLocation: 'book'
            });
        });
}

exports.showAddBookForm = (req,res,next) => {
    res.render('pages/Book/form.ejs',{
        book: {},
        pageTitle: 'Nowa książka',
        formMode: 'add',
        btnLabel: 'Dodaj książkę',
        formAction: '/books/add',
        navLocation: 'book',
        validationErrors: []
    });
}

exports.showBookInfo = (req,res,next) => {
    const bookId = req.params.bookId;
    BookRepository.getBookbyId(bookId)
        .then(book => {
            res.render('pages/Book/form.ejs',{
                book: book,
                formMode: 'info',
                pageTitle: 'Szczgóły książki',
                formAction: '/books/details',
                navLocation: 'book',
                validationErrors: []
            });
        });
}

exports.showEditBookForm = (req,res,next) => {
    const bookId = req.params.bookId;
    BookRepository.getBookbyId(bookId)
        .then(book => {
            res.render('pages/Book/form.ejs',{
                book: book,
                formMode: 'edit',
                pageTitle: 'Edycja książki',
                btnLabel: 'Edycja książki',
                formAction: '/books/edit',
                navLocation: 'book',
                validationErrors:[]
            });
        });
}

exports.addBook = (res,req,next) => {
    const bookData = { ...res.body };

    BookRepository.createBook(bookData)
        .then(result => {
            req.redirect('/books');
        })
        .catch(err => {
                req.render('pages/Book/form.ejs', {
                    book: bookData,
                    pageTitle: 'Nowa książka',
                    formMode: 'add',
                    btnLabel: 'Dodaj książkę',
                    formAction: '/books/add',
                    navLocation: 'book',
                    validationErrors: err.details
                });
        });
}

exports.updateBook = (res,req,next) => {
    const bookData = { ...res.body };
    const bookId = res.body.Id_Book;
    BookRepository.updateBook(bookId, bookData)
        .then(result => {
            req.redirect('/books');
        })
        .catch(err => {
            req.render('pages/Book/form.ejs', {
                book: bookData,
                formMode: 'edit',
                pageTitle: 'Edycja książki',
                btnLabel: 'Edycja książki',
                formAction: '/books/edit',
                navLocation: 'book',
                validationErrors: err.details
            });
        });
}

exports.deleteBook = (req,res,next) => {
    const bookId = req.params.bookId;
    BookRepository.deleteBook(bookId)
        .then(() => {
            res.redirect('/books');
        });
}

