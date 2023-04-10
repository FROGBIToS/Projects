const db = require('../../config/mysql2/db');
const bookSchema = require('../../model/joi/Book');

exports.getBooks = () => {
    return db.promise().query('SELECT * FROM Book')
        .then((results, fields) => {
            return results[0];
    })
        .catch(err => {
           console.log(err);
           throw err;
        });
};
exports.getBookbyId = (bookId) => {
    const query = 'SELECT b.Name, b.Writting_date, b.Language, b.Is_Bestseller,a.LastName, a.FirstName, ab.Publisher, ab.Amount_Copy, ab.Is_Sold, a.Id_Author FROM Book b LEFT JOIN Author_Book ab ON b.Id_Book = ab.Id_Book LEFT JOIN Author a ON a.Id_Author = ab.Id_Author WHERE b.Id_Book = ?';
    return db.promise().query(query,[bookId])
        .then((results, fields) => {
            const row = results[0][0];
            if (!row){
                return {};
            }
            const book = {
                Id_Book: bookId,
                Name: row.Name,
                Writting_date: row.Writting_date,
                Language: row.Language,
                Is_Bestseller: row.Is_Bestseller,
                authorBooks: []
            }
            for (let i = 0; i < results[0].length; i++) {
                const row = results[0][i];

                const authorBook = {
                    Id_Author: row.Id_Author,
                    LastName: row.LastName,
                    FirstName: row.FirstName,
                    Publisher: row.Publisher,
                    Amount_Copy: row.Amount_Copy,
                    Is_Sold: row.Is_Sold
                }
                book.authorBooks.push(authorBook);
            }
            return book;
        }).catch(err => {
            console.log(err);
            throw err;
        });
};
exports.createBook = (dataBook) => {
    const vRes = bookSchema.validate(dataBook, {abortEarly:false} );
    if (vRes.error){
        return Promise.reject(vRes.error);
    }
    let sql = '';
    if (dataBook.Is_Bestseller == 'null' || dataBook.Is_Bestseller == '' || dataBook.Is_Bestseller == null){
        sql = 'INSERT INTO Book(Name, Writting_date, Language) VALUE(?,?+ INTERVAL 1 DAY,?)';
        return db.promise().execute(sql, [dataBook.Name,dataBook.Writting_date,dataBook.Language]);
    }else{
        sql = 'INSERT INTO Book(Name, Writting_date, Language,Is_Bestseller) VALUE(?,? + INTERVAL 1 DAY,?,?)';
        return db.promise().execute(sql, [dataBook.Name,dataBook.Writting_date,dataBook.Language,dataBook.Is_Bestseller]);
    }

};
exports.updateBook = (bookId, dataBook) => {
    const vRes = bookSchema.validate(dataBook, {abortEarly:false} );
    if (vRes.error){
        return Promise.reject(vRes.error);
    }
    console.log(dataBook);

    if (dataBook.Is_Bestseller == 'null' || dataBook.Is_Bestseller == '' || dataBook.Is_Bestseller == null){
        let sql = 'UPDATE Book SET Name = ?, writting_date = ?+ INTERVAL 1 DAY, language = ? WHERE Id_Book = ?';
        return db.promise().execute(sql, [dataBook.Name,dataBook.Writting_date,dataBook.Language,bookId]);
    }else{
        let sql = 'UPDATE Book SET Name = ?, writting_date = ?+ INTERVAL 1 DAY, language = ?, is_bestseller = ? WHERE Id_Book = ?';
        return db.promise().execute(sql, [dataBook.Name,dataBook.Writting_date,dataBook.Language,dataBook.Is_Bestseller,bookId]);
    }

};
exports.deleteBook = (bookId) => {
    const sql_1 = 'DELETE FROM Author_Book WHERE Id_Book = ?';
    const sql_2 = 'DELETE FROM Book WHERE Id_Book = ?';
    db.promise().execute(sql_1,[bookId]);
    return db.promise().execute(sql_2,[bookId]);
};