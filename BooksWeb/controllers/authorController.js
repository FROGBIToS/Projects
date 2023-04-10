const AuthorRepository = require('../repository/mysql2/AuthorRepository');

exports.showAuthorList = (req,res,next) => {
    AuthorRepository.getAuthors()
        .then(authors => {
            res.render('pages/Author/list-author.ejs', {
                authors: authors,
                navLocation: 'author'
            });
        });
}

exports.showAddAuthorForm = (req,res,next) => {
    res.render('pages/Author/form.ejs',{
        author: {},
        pageTitle: 'Nowy autor',
        formMode: 'add',
        btnLabel: 'Dodaj autora',
        formAction: '/authors/add',
        navLocation: 'author',
        validationErrors: []
    });
}

exports.showAuthorInfo = (req,res,next) => {
    const authorId = req.params.authorId;
    AuthorRepository.getAuthorbyId(authorId)
        .then(author => {
            res.render('pages/Author/form.ejs',{
                author: author,
                formMode: 'info',
                pageTitle: 'Szczgóły autora',
                formAction: '/authors/details',
                navLocation: 'author',
                validationErrors: []
            });
        });
}

exports.showEditAuthorForm = (req,res,next) => {
    const authorId = req.params.authorId;
    AuthorRepository.getAuthorbyId(authorId)
        .then(author => {
            res.render('pages/Author/form.ejs',{
                author: author,
                formMode: 'edit',
                pageTitle: 'Edycja autora',
                btnLabel: 'Edycja autora',
                formAction: '/authors/edit',
                navLocation: 'author',
                validationErrors: []
            });
        });
}

exports.addAuthor = (res,req,next) => {
    const authorData = { ...res.body };
    AuthorRepository.createAuthor(authorData)
        .then(result => {
            req.redirect('/authors');
        })
        .catch(err => {
            req.render('pages/Author/form.ejs',{
                author: authorData,
                pageTitle: 'Nowy autor',
                formMode: 'add',
                btnLabel: 'Dodaj autora',
                formAction: '/authors/add',
                navLocation: 'author',
                validationErrors: err.details
            });
            console.log(err);
        });
}

exports.updateAuthor = (res,req,next) => {
    const authorData = { ...res.body };
    const authorId = res.body.Id_Author;
    AuthorRepository.updateAuthor(authorId, authorData)
        .then(result => {
            req.redirect('/authors');
        })
        .catch(err => {
            req.render('pages/Author/form.ejs',{
                author: authorData,
                formMode: 'edit',
                pageTitle: 'Edycja autora',
                btnLabel: 'Edycja autora',
                formAction: '/authors/edit',
                navLocation: 'author',
                validationErrors: err.details
            });
        });
}

exports.deleteAuthor = (req,res,next) => {
    const authorId = req.params.authorId;
    AuthorRepository.deleteAuthor(authorId)
        .then(() => {
            res.redirect('/authors');
        });
}