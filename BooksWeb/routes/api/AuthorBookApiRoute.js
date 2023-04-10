const express = require('express');
const router = express.Router();

const authorBookApiController = require('../../api/AuthorBookAPI');

router.get('/', authorBookApiController.getAuthorBooks);
router.get('/:authorBookId',authorBookApiController.getAuthorBookById);
router.post('/',authorBookApiController.createAuthorBook);
router.put('/:authorBookId',authorBookApiController.updateAuthorBook);
router.delete('/:authorBookId',authorBookApiController.deleteAuthorBook);


module.exports = router;