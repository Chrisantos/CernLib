const mongoose      = require('mongoose');
const schema        = new mongoose.Schema({
    _id:        mongoose.Schema.Types.ObjectId,
    name:       {type: String, required: true},
    regno:      {type: String, required: true, unique: true},
    password:   {type: String, required: true},
    department: String,
    level:      String
});

module.exports = mongoose.model('Students', schema);