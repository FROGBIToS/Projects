const db = require('../../config/mysql2/db');
const authorSchema = require("../../model/joi/Author");
const e = require("express");

exports.getAuthors = () => {
    return db.promise().query('SELECT * FROM Author')
        .then((results, fields) => {
            return results[0];
    })
        .catch(err => {
           console.log(err);
           throw err;
        });
};
exports.getAuthorbyId = (authorId) => {
    const query = '\n' +
        'SELECT a.FirstName, a.LastName, a.Email, b.Id_Book, b.Name, b.Writting_date,b.Language,b.Is_Bestseller FROM Author a LEFT JOIN Author_Book ab on a.Id_Author = ab.Id_Author LEFT JOIN Book b on b.Id_Book = ab.Id_Book WHERE a.Id_Author = ?;';
    return db.promise().query(query,[authorId])
        .then((results, fields) => {
            const row = results[0][0];
            if (!row){
                return {};
            }
            const author = {
                Id_Author: authorId,
                FirstName: row.FirstName,
                LastName: row.LastName,
                Email: row.Email,
                books: []
            }

            for (let i = 0; i < results[0].length; i++) {
                const row = results[0][i];
                book = {
                    Id_Book: row.Id_Book,
                    Name: row.Name,
                    Writting_date: row.Writting_date,
                    Language: row.Language,
                    Is_Bestseller: row.Is_Bestseller
                }

                author.books.push(book);
            }
            return author;
        }).catch(err => {
            console.log(err);
            throw err;
        });
};
exports.createAuthor = (dataAuthor) => {
    const vRes = authorSchema.validate(dataAuthor, {abortEarly:false} );
    if (vRes.error){
        return Promise.reject(vRes.error);
    }
    return checkEmailUnique(dataAuthor.Email)
        .then(emailErr => {
                if (emailErr.details != undefined){
                    return Promise.reject(emailErr);
                }else{
                    const sql = 'INSERT INTO Author(FirstName, LastName, Email) VALUE(?,?,?)'
                    return db.promise().execute(sql, [dataAuthor.FirstName,dataAuthor.LastName,dataAuthor.Email]);
                }
            }
        ).catch(err => {
            return Promise.reject(err);
            }
        );
};
exports.updateAuthor = (authorId, dataAuthor) => {
    const vRes = authorSchema.validate(dataAuthor, {abortEarly:false} );
    if (vRes.error){
        return Promise.reject(vRes.error);
    }
    return checkEmailUnique(dataAuthor.Email, authorId)
        .then(emailErr => {
                if (emailErr.details != undefined){
                    return Promise.reject(emailErr);
                }else{
                    const sql = 'UPDATE Author SET FirstName = ?, LastName = ?, Email = ? WHERE Id_Author = ?';
                    return db.promise().execute(sql, [dataAuthor.FirstName,dataAuthor.LastName,dataAuthor.Email,authorId]);
                }
            }
        ).catch(err => {
                return Promise.reject(err);
            }
        )
};
exports.deleteAuthor = (authorId) => {
    const sql_1 = 'DELETE FROM Author_Book WHERE Id_Author = ?';
    const sql_2 = 'DELETE FROM Author WHERE Id_Author = ?';
    db.promise().execute(sql_1,[authorId]);
    return db.promise().execute(sql_2,[authorId]);
};
checkEmailUnique = (email, Id_Author) => {
    let sql, promise;
    if (Id_Author){
        sql = `SELECT COUNT(1) as c FROM Author WHERE Id_Author != ? AND Email = ?`;
        promise = db.promise().query(sql, [Id_Author, email]);
    }else{
        sql = `SELECT COUNT(1) as c FROM Author WHERE Email = ?`;
        promise = db.promise().query(sql, [email]);
    }
    return promise.then((results, fields) => {
        const count = results[0][0].c;
        let err = {};
        if (count > 0){
            err = {
                details:[{
                    path: ['Email'],
                    message: 'Podany email jest już używany'
                }]
            };
        }
        return err;
    })
}