const mongoose      = require('mongoose');
const studentModel  = require('../models/student');
const bookModel     = require('../models/book');
const lendModel     = require('../models/lend');

const jwt           = require('jsonwebtoken');
const bcrypt        = require('bcrypt');

module.exports = {
    studentGetAll: (req, res, next) =>{
        studentModel.find()
        .exec()
        .then(students =>{
            if(students.length < 1){
                return res.status(500).json({
                    message: "No students"
                })
            }
            res.status(200).json({
                count: students.length,
                students: students.map(student =>{
                    return {
                        name: student.name,
                        level: student.level,
                        department: student.department,
                        regno: student.regno,
                        _id:   student._id,
                        request: {
                            type: 'GET',
                            url: 'http://localhost:5000/students/' + student._id
                        }
                    }
                })
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            })
        });
    },
    studentGetOne: (req, res, next) =>{
        studentModel.findById(req.params.id)
        .exec()
        .then(student =>{
            res.status(200).json({
                student,
                request: {
                    type: 'GET',
                    url: 'http://localhost:5000/students'
                }
            });
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
    },
    studentSignup: (req, res, next) =>{
        const regno     = req.body.regno;
        const password  = req.body.password;
        const name      = req.body.name;
        const department= req.body.department;
        const level     = req.body.level;

        studentModel.find({regno: regno})
        .exec()
        .then(students =>{
            if(students.length >= 1){
                return res.status(409).json({
                    message: 'Student with same registration number already exists'
                });
            }

            bcrypt.hash(password, 10, (err, hash) =>{
                if(err){
                    return res.status(500).json({
                        error: err
                    })
                }
                const newStudent = new studentModel({
                    _id:  new mongoose.Types.ObjectId(),
                    name,
                    regno,
                    password: hash,
                    department,
                    level
                });
                newStudent.save()
                .then(result =>{
                    res.status(201).json({
                        student: result,
                        request: {
                            type: 'GET',
                            url: 'http://localhost:5000/students'
                        }
                    })
                })
                .catch(err =>{
                    console.log(err);
                    res.status(500).json({
                        error: err
                    });
                });
            })
        })
    } ,
    studentLogin : (req, res, next) =>{
        const regno     = req.body.regno;
        const password  = req.body.password;

        studentModel.findOne({regno: regno})
        .exec()
        .then(student =>{
            if(!student){
                return res.status(500).json({
                    message: "No student found"
                })
            }
            bcrypt.compare(password, student.password, (err, result) =>{
                if(err){
                    return res.status(401).json({
                        message: 'Auth failed'
                    })
                }
                if(result){
                    const token = jwt.sign(
                        {
                            name: student.name,
                            level: student.level
                        }, process.env.JWT_KEY,
                        {
                            expiresIn: '1h'
                        });
                        return res.status(200).json({
                            message: 'Auth successful',
                            student,
                            token
                        });
                }
                res.status(401).json({
                    message: 'Auth failed'
                })
            })
        })
    } ,
    studentUpdate: (req, res, next) =>{
        studentModel.update({_id: req.params.id}, 
            {$set: 
                {
                    name: req.body.name, 
                    level: req.body.level, 
                    regno: req.body.regno,
                    department: req.body.department
                }
            })
            .exec()
            .then(student =>{
                res.status(200).json({
                    message: 'Student record updated',
                    request: {
                        type: 'GET',
                        url: 'http://localhost:5000/students'
                    }
                })
            })
            .catch(err =>{
                console.log(err);
                res.status(500).json({
                    error: err
                });
            })
        
    }  ,
    studentDelete: (req, res, next) =>{
        studentModel.findById(req.params.id)
        .exec()
        .then(student =>{
            if(!student){
                return res.status(404).json({
                    message: 'No student with such ID exist',
                    bool: false
                });
            }
            studentModel.remove({_id: req.params.id})
            .exec()
            .then(result =>{
                res.status(200).json({
                    message: 'Student removed successfully',
                    bool: true,
                    request: {
                        type: 'GET',
                        url: 'http://localhost:5000/students'
                    }
                })
            })
            .catch(err =>{
                console.log(err);
                res.status(500).json({
                    error: err
                });
            })
        })
    } ,

    bookGetAll: (req, res, next) =>{
        bookModel.find()
        .exec()
        .then(books =>{
            if(books.length < 1){
                return res.status(500).json({
                    message: 'No books available'
                });
            }
            res.status(200).json({
                count: books.length,
                books: books.map(book =>{
                    return {
                        _id: book._id,
                        title: book.title,
                        author: book.author,
                        field: book.field,
                        publisher: book.publisher,
                        quantity: book.quantity,
                        isbn: book.ISBN,
                        request: {
                            type: 'GET',
                            url: 'http://localhost:5000/books/' + book._id
                        }
                    }
                })
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    bookGetOne: (req, res, next) =>{
        bookModel.findById(req.params.id)
        .exec()
        .then(book =>{
            if(!book){
                return res.status(404).json({
                    message: 'No book with such ID exist'
                });
            }
            res.status(200).json({
                book: book,
                request: {
                    type: 'GET',
                    url: 'http://localhost:5000/books'
                }
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    bookPost: (req, res, next) =>{
        const title     = req.body.title;
        const author    = req.body.author;
        const publisher = req.body.publisher;
        const field     = req.body.field;
        const ISBN      = req.body.ISBN;
        const quantity  = req.body.quantity;

        bookModel.find({ISBN: ISBN})
        .exec()
        .then(books =>{
            if(books.length >= 1){
                return res.status(500).json({
                    message: 'Incorrect credentials'
                });
            }
            const newBook   = new bookModel({
                _id: new mongoose.Types.ObjectId(),
                ISBN,
                title,
                author,
                publisher,
                field,
                quantity
            });
            newBook.save()
            .then(book =>{
                res.status(201).json({
                    message: 'New book added',
                    book: book,
                    request: {
                        type: 'GET',
                        url: 'http://localhost:5000/books'
                    }
                })
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    bookUpdate: (req, res, next)=>{
        bookModel.update({_id: req.params.id}, 
        {$set: {
            ISBN: req.body.ISBN,
            title: req.body.title,
            author: req.body.author,
            publisher: req.body.publisher,
            field: req.body.field,
            quantity: req.body.quantity
            }
        })
        .exec()
        .then(result =>{
            res.status(200).json({
                message: 'Book recorded updated successfully',
                request: {
                    type: 'GET',
                    url: 'http://localhost:5000/books'
                }
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    bookDelete: (req, res, next) =>{
        bookModel.findById(req.params.id)
        .exec()
        .then(book =>{
            if(!book){
                return res.status(404).json({
                    message: 'No book with such record found',
                    bool: false
                });
            }
            bookModel.remove({_id: req.params.id})
            .exec()
            .then(result =>{
                res.status(200).json({
                    message: 'Book deleted successfully',
                    bool: true,
                    request: {
                        type: 'GET',
                        url: 'http://localhost:5000/books'
                    }
                })
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,

    lendGetAll: (req, res, next) =>{
        lendModel.find()
        .exec()
        .then(lends =>{
            if(lends.length < 1){
                return res.status(500).json({
                    message: 'No book has been borrowed yet'
                })
            }
            res.status(200).json({
                count: lends.length,
                lends: lends.map(lend =>{
                    return {
                        _id: lend._id,
                        book: lend.book,
                        student_name: lend.student,
                        student_regno: lend.regno,
                        ISBN: lend.isbn,

                        request: {
                            type: "GET",
                            url: "http://localhost:5000/lends/" + lend.student
                        }
                    }
                })
            })
            
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    lendGetOne: (req, res, next) =>{
        // lendModel.findById(req.params.id)
        lendModel.find({student: req.params.id})
        .exec()
        .then(lend =>{
            if(lend.length < 1){
                return res.status(404).json({
                    message: 'No record found for such Student name'
                })
            }
            res.status(200).json({
                count: lends.length,
                lends: lends.map(lend =>{
                    return {
                        _id: lend._id,
                        book: lend.book,
                        student_name: lend.student,
                        student_regno: lend.regno,
                        isbn: lend.isbn,
                        request: {
                            type: "GET",
                            url: "http://localhost:5000/lends"
                        }
                    }
                })
            })
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    lendPost: (req, res, next) =>{
        const ISBN           = req.body.ISBN;
        let bookTitle        = req.body.book;  //No more ID's
        let studentName      = req.body.student;
        bookModel.findOne({ISBN})
        .exec()
        .then(book =>{
            if(!book){
                return res.status(404).json({
                    message: 'No book with such ISBN Number exist'
                });
            }
            else{
                studentModel.findOne({name: studentName})
                .exec()
                .then(student =>{
                    if(!student){
                        return res.status(404).json({
                            message: 'No student with such ID exist',
                            bool: false
                        });
                    }else{
                        bookModel.update({ISBN},
                            {$set: {
                                ISBN: book.ISBN,
                                title: book.title,
                                author: book.author,
                                publisher: book.publisher,
                                field: book.field,
                                quantity: (book.quantity - 1)
                                }
                            })
                            .exec()
                            .then(updatedBook =>{
                                const newLend   = new lendModel({
                                    _id: new mongoose.Types.ObjectId(),
                                    book: bookTitle,
                                    student: studentName,
                                    regno: student.regno,
                                    isbn: ISBN
                                })
                                newLend.save()
                                .then(result =>{
                                    res.status(201).json({
                                        message: 'Book borrowed successfully by ' + student.name,
                                        bool: true,
                                        request: {
                                            type: 'GET',
                                            url: 'http://localhost:5000/lends'
                                        } 
                                    })
                                })
                            })
                    }
                })
                
            }
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            });
        })
    } ,
    lendUpdate: (req, res, next) =>{
        
    } ,
    lendDelete: (req, res, next) =>{
        lendModel.findById(req.params.id)
        .exec()
        .then(lend =>{
            if(!lend){
                return res.status(404).json({
                    message: 'No such record found for ID'
                })
            }
            else{
                lendModel.remove({_id: req.params.id})
                .exec()
                .then(removeResult =>{
                    let book = lend.book;
                    book     = book.toLowerCase();
                    bookModel.findOne({title: book})
                    .exec()
                    .then(book =>{
                        bookModel.update({title: book},
                            {$set: {
                                ISBN: book.ISBN,
                                title: book.title,
                                author: book.author,
                                publisher: book.publisher,
                                field: book.field,
                                quantity: (book.quantity + 1) 
                                }
                            })
                            .exec()
                            .then(updateResult =>{
                                res.status(200).json({
                                    message: `The book ${lend.book} has been returned by ${lend.student}`,
                                    request: {
                                        type: 'GET',
                                        url: 'http://localhost:5000/books'
                                    } 
                                })
                                // studentModel.findById(lend.student)
                                // .exec()
                                // .then(student =>{
                                //     res.status(200).json({
                                //         message: `The book ${book.title} has been returned by ${student.name}`,
                                //         request: {
                                //             type: 'GET',
                                //             url: 'http://localhost:5000/books'
                                //         } 
                                //     })
                                // })
                            })
                    })
                    
                })
            }
        })
        .catch(err =>{
            console.log(err);
            res.status(500).json({
                error: err
            })
        })
    } ,

}