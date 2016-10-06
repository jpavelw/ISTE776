"use strict";

var express = require('express'),
    bodyParser = require('body-parser'),
    assert = require('assert'),
    simpleLogin = require('./simple_login');

var app = express();

app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());

app.post('/simplelogin/login', simpleLogin.login);
app.post('/simplelogin/update', simpleLogin.update);

app.use(function(resquest, response){
    response.sendStatus(404);
});

var server = app.listen(7760, function(){
    var port = server.address().port;
    console.log('Express server listening on port %s', port);
});
