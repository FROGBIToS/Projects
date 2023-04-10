const db = require('../../config/mysql2/db');
const authorBookSchema = require("../../model/joi/AuthorBook");

exports.getAuthorBooks = () => {
    return db.promise().query('SELECT a.LastName, b.Name, ab.Id_Author_Book, ab.Publisher, ab.Amount_Copy,ab.Is_Sold FROM Author_Book ab JOIN Author a ON a.Id_Author = ab.Id_Author INNER JOIN Book b ON ab.Id_Book = b.Id_Book')
        .then((results, fields) => {
            return results[0];
    })
        .catch(err => {
           console.log(err);
           throw err;
        });
};
exports.getAuthorBookbyId = (authorBookId) => {
    const query = 'SELECT b.Id_Book, b.Name,b.Writting_date,b.Language,b.Is_Bestseller, a.Id_Author, a.FirstName,a.LastName,a.Email,ab.Publisher, ab.Amount_Copy,ab.Is_Sold FROM Author_Book ab LEFT JOIN Author a ON a.Id_Author = ab.Id_Author LEFT JOIN Book b ON ab.Id_Book = b.Id_Book WHERE Id_Author_Book = ?';
    return db.promise().query(query,[authorBookId])
        .then((results, fields) => {
            const row = results[0][0];
            if (!row){
                return {};
            }
            const authorBook = {
                Id_Author_Book: authorBookId,
                Id_Book: row.Id_Book,
                Id_Author: row.Id_Author,
                Name: row.Name,
                Writting_date: row.Writting_date,
                Language: row.Language,
                Is_Bestseller: row.Is_Bestseller,
                FirstName: row.FirstName,
                LastName: row.LastName,
                Email: row.Email,
                Publisher: row.Publisher,
                Amount_copy: row.Amount_Copy,
                Is_Sold: row.Is_Sold
            }
            return authorBook;
        }).catch(err => {
            console.log(err);
            throw err;
        });
};
exports.createAuthorBook = (dataAuthorBook) => {
    const vRes = authorBookSchema.validate(dataAuthorBook, {abortEarly:false} );
    if (vRes.error){
        return Promise.reject(vRes.error);
    }
    const sql = 'INSERT INTO Author_Book(publisher, amount_copy, is_sold, id_author, id_book) VALUE(?,?,?,?,?)'
    return db.promise().execute(sql, [dataAuthorBook.Publisher,dataAuthorBook.Amount_copy,dataAuthorBook.Is_Sold,dataAuthorBook.Id_Author,dataAuthorBook.Id_Book]);
};
exports.updateAuthorBook = (authorBookId, dataAuthorBook) => {
    const vRes = authorBookSchema.validate(dataAuthorBook, {abortEarly:false} );
    if (vRes.error){
        return Promise.reject(vRes.error);
    }
    const sql = 'UPDATE Author_Book SET Publisher = ?, Amount_Copy = ?, Is_Sold = ?, Id_Author = ?, Id_Book = ? WHERE Id_Author_Book = ?';
    return db.promise().execute(sql, [dataAuthorBook.Publisher,dataAuthorBook.Amount_copy,dataAuthorBook.Is_Sold,dataAuthorBook.Id_Author,dataAuthorBook.Id_Book,authorBookId]);
};
exports.deleteAuthorBook = (authorBookId) => {
    const sql = 'DELETE FROM Author_Book WHERE Id_Author_Book = ?';
    return db.promise().execute(sql,[authorBookId]);
};