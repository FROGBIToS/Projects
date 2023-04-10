const express = require('express');
const router = express.Router();

const authorController = require('../controllers/authorController');


router.get('/', authorController.showAuthorList);
router.get('/add', authorController.showAddAuthorForm);
router.get('/details/:authorId', authorController.showAuthorInfo);
router.get('/edit/:authorId', authorController.showEditAuthorForm);

router.post('/add', authorController.addAuthor);
router.post('/edit', authorController.updateAuthor);
router.get('/delete/:authorId', authorController.deleteAuthor);

module.exports = router;