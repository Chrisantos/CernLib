const express           = require('express');
const mongoose          = require('mongoose');
const bodyParser        = require('body-parser');
const morgan            = require('morgan');

const bookRoute         = require('./routes/bookRoute');
const studentRoute      = require('./routes/studentRoute');
const lendRoute         = require('./routes/lendRoute');

const app               = express();
mongoose.connect(process.env.mongoUri);
mongoose.Promise = global.Promise;

app.use(morgan('dev'));
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

app.use('/books', bookRoute);
app.use('/students', studentRoute);
app.use('/lends', lendRoute);

app.set('port', process.env.PORT || 5000);

app.listen(app.get('port'), () =>{
    console.log(`Server up and running at port ${app.get('port')}`);
});