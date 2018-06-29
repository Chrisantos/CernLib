const express       = require('express');
const controller    = require('../controllers/controller');

const router        = express.Router();

router.get('/', controller.lendGetAll);
router.get('/:id', controller.lendGetOne);
router.post('/', controller.lendPost);   //borrow
router.patch('/:id', controller.lendUpdate);
router.delete('/:id', controller.lendDelete); //return

module.exports = router;