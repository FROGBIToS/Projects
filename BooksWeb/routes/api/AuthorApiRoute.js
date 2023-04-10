const express = require('express');
const router = express.Router();

const authorApiController = require('../../api/AuthorAPI');

router.get('/', authorApiController.getAuthors);
router.get('/:authorId',authorApiController.getAuthorById);
router.post('/',authorApiController.createAuthor);
router.put('/:authorId',authorApiController.updateAuthor);
router.delete('/:authorId',authorApiController.deleteAuthor);


module.exports = router;