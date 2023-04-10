const express = require('express');
const router = express.Router();

const bookController = require('../controllers/bookController');


router.get('/', bookController.showBookList);
router.get('/add', bookController.showAddBookForm);
router.get('/details/:bookId', bookController.showBookInfo);
router.get('/edit/:bookId', bookController.showEditBookForm);

router.post('/add', bookController.addBook);
router.post('/edit', bookController.updateBook);
router.get('/delete/:bookId', bookController.deleteBook);

module.exports = router;