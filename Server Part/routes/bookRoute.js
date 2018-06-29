const express       = require('express');
const controller    = require('../controllers/controller');

const router        = express.Router();

router.get('/', controller.bookGetAll);
router.get('/:id', controller.bookGetOne);
router.post('/', controller.bookPost);
router.patch('/:id', controller.bookUpdate);
router.delete('/:id', controller.bookDelete);

module.exports = router;