const express       = require('express');
const controller    = require('../controllers/controller');

const router        = express.Router();

router.get('/', controller.studentGetAll);
router.get('/:id', controller.studentGetOne);
router.post('/signup', controller.studentSignup);
router.post('/login', controller.studentLogin)
router.patch('/:id', controller.studentUpdate);
router.delete('/:id', controller.studentDelete);

module.exports = router;