const express = require('express');
const router = express.Router();

const bookApiController = require('../../api/BookAPI');

router.get('/', bookApiController.getBooks);
router.get('/:bookId',bookApiController.getBookById);
router.post('/',bookApiController.createBook);
router.put('/:bookId',bookApiController.updateBook);
router.delete('/:bookId',bookApiController.deleteBook);


module.exports = router;