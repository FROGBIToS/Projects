var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var bookRouter = require('./routes/bookRoute');
var authorRouter = require('./routes/authorRoute');
var authorBookRouter = require('./routes/authorBookRoute');

var app = express();
const bookApiRouter = require('./routes/api/BookApiRoute');
const authorApiRouter = require('./routes/api/AuthorApiRoute');
const authorBookApiRouter = require('./routes/api/AuthorBookApiRoute');

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/books', bookRouter);
app.use('/authors', authorRouter);
app.use('/authorsBooks', authorBookRouter);
app.use('/api/books', bookApiRouter);
app.use('/api/authors', authorApiRouter);
app.use('/api/authorBooks', authorBookApiRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
