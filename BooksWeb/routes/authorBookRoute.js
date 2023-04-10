const express = require('express');
const router = express.Router();

const authorBookController = require('../controllers/authorBookController');


router.get('/', authorBookController.showAuthorBookList);
router.get('/add', authorBookController.showAddAuthorBookForm);
router.get('/details/:authorBookId', authorBookController.showAuthorBookInfo);
router.get('/edit/:authorBookId', authorBookController.showEditAuthorBookForm);

router.post('/add', authorBookController.addAuthorBook);
router.post('/edit', authorBookController.updateAuthorBook);
router.get('/delete/:authorBookId', authorBookController.deleteAuthorBook);

module.exports = router;