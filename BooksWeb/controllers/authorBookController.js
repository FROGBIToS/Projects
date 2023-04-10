const AuthorBookRepository = require('../repository/mysql2/AuthorBookRepository');
const AuthorRepository = require('../repository/mysql2/AuthorRepository');
const BookRepository = require('../repository/mysql2/BookRepository');


exports.showAuthorBookList = (req,res,next) => {
    AuthorBookRepository.getAuthorBooks()
        .then(authorBooks => {
            res.render('pages/Author_Book/list-authorbook.ejs', {
                authorBooks: authorBooks,
                navLocation: 'authorBook'
            });
        });
}

exports.showAddAuthorBookForm = (req,res,next) => {
    let allbooks, allauthors;
    AuthorRepository.getAuthors()
        .then(authors => {
            allauthors = authors;
            return BookRepository.getBooks();
        })
        .then(books => {
            allbooks = books;
                res.render('pages/Author_Book/form.ejs',{
                    authorBook: {},
                    pageTitle: 'Nowe powiązanie',
                    formMode: 'add',
                    btnLabel: 'Dodaj powiązanie',
                    formAction: '/authorsBooks/add',
                    navLocation: 'authorBook',
                    allbooks: allbooks,
                    allauthors: allauthors,
                    validationErrors: []
                });
        });
}

exports.showAuthorBookInfo = (req,res,next) => {
    let allbooks, allauthors;
    const authorBookId = req.params.authorBookId;
    AuthorRepository.getAuthors()
        .then(authors => {
            allauthors = authors;
            return BookRepository.getBooks();
        })
        .then(books => {
            allbooks = books;
            return AuthorBookRepository.getAuthorBookbyId(authorBookId);
        })
        .then(authorBook => {
            res.render('pages/Author_Book/form.ejs',{
                authorBook: authorBook,
                formMode: 'info',
                pageTitle: 'Szczgóły powiązania',
                formAction: '/authorsBooks/details',
                navLocation: 'authorBook',
                allbooks: allbooks,
                allauthors: allauthors,
                validationErrors: []
            });
        }
        );
}

exports.showEditAuthorBookForm = (req,res,next) => {
    let allbooks, allauthors;
    const authorBookId = req.params.authorBookId;
    AuthorRepository.getAuthors()
        .then(authors => {
            allauthors = authors;
            return BookRepository.getBooks();
        })
        .then(books => {
            allbooks = books;
            return AuthorBookRepository.getAuthorBookbyId(authorBookId);
        })
        .then(authorBook => {
            res.render('pages/Author_Book/form.ejs',{
                authorBook: authorBook,
                formMode: 'edit',
                pageTitle: 'Edycja powiązania',
                btnLabel: 'Edycja powiązania',
                formAction: '/authorsBooks/edit',
                navLocation: 'authorBook',
                allbooks: allbooks,
                allauthors: allauthors,
                validationErrors: []
            });
            }
        );
}

exports.addAuthorBook = (res,req,next) => {
    const authorBookData = { ...res.body };
    if (authorBookData.Is_Sold === 'on'){
        authorBookData.Is_Sold = 1;
    }else{
        authorBookData.Is_Sold = 0;
    }

    AuthorBookRepository.createAuthorBook(authorBookData)
        .then(result => {
            req.redirect('/authorsBooks');
        }).catch(errMain => {
        AuthorRepository.getAuthors()
            .then(authors => {
                allauthors = authors;
                return BookRepository.getBooks();
            })
            .then(books => {
                allbooks = books;
            }).then(e => {
            req.render('pages/Author_Book/form.ejs',{
                authorBook: authorBookData,
                pageTitle: 'Nowe powiązanie',
                formMode: 'add',
                btnLabel: 'Dodaj powiązanie',
                formAction: '/authorsBooks/add',
                navLocation: 'authorBook',
                allbooks: allbooks,
                allauthors: allauthors,
                validationErrors: errMain.details
            });
        });
    });
}

exports.updateAuthorBook = (res,req,next) => {
    const authorBookData = { ...res.body };
    const authorBookId = res.body.Id_Author_Book;
    if (authorBookData.Is_Sold == 'on'){
        authorBookData.Is_Sold = 1;
    }else{
        authorBookData.Is_Sold = 0;
    }
    AuthorBookRepository.updateAuthorBook(authorBookId, authorBookData)
        .then(result => {
            req.redirect('/authorsBooks');
        })
        .catch(errMain => {
                AuthorRepository.getAuthors()
                    .then(authors => {
                        allauthors = authors;
                        return BookRepository.getBooks();
                    })
                    .then(books => {
                        allbooks = books;
                    }).then(e => {
                        req.render('pages/Author_Book/form.ejs',{
                            authorBook: authorBookData,
                            formMode: 'edit',
                            pageTitle: 'Edycja powiązania',
                            btnLabel: 'Edycja powiązania',
                            formAction: '/authorsBooks/edit',
                            navLocation: 'authorBook',
                            allbooks: allbooks,
                            allauthors: allauthors,
                            validationErrors: errMain.details
                        });
                    });
        });
}

exports.deleteAuthorBook = (req,res,next) => {
    const authorBookId = req.params.authorBookId;
    AuthorBookRepository.deleteAuthorBook(authorBookId)
        .then(() => {
            res.redirect('/authorsBooks');
        });
}