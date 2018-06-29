const mongoose      = require('mongoose');
const schema        = new mongoose.Schema({
    _id:        mongoose.Schema.Types.ObjectId,
    ISBN:       {type: String, required: true, unique: true},
    title:      {type: String, required: true},
    author:     String,
    publisher:  String,
    field:      String,
    quantity:   {type: String, default: 1}
});

module.exports = mongoose.model('Books', schema);