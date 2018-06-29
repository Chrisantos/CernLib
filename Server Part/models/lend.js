const mongoose      = require('mongoose');
const schema        = new mongoose.Schema({
    _id:        mongoose.Schema.Types.ObjectId,
    // book:       {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Books'},
    // student:    {type: mongoose.Schema.Types.ObjectId, required: true, ref: 'Students'}
    book:      {type: String, required: true},
    student:   {type: String, required: true},
    regno:     {type: String, required: true},
    isbn:      {type: String, required: true}
});

module.exports = mongoose.model('Lends', schema);