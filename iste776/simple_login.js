exports.login = function(request, response, next){

    "use strict";

    var assert = require('assert');
    assert.notEqual(request.body.user, null);
    var _user = request.body.user;
    var _onDisk = require('./simple_login_info.json');
    var _checkCredentials = (_user.username == _onDisk.user.username && _user.password == _onDisk.user.password);
    response.send(JSON.stringify({"code": 200, "message": _checkCredentials}));
};

exports.update = function(request, response, next){

    "use strict";
    var assert = require('assert');
    var _file = require('fs');
    assert.notEqual(request.body.user, null);
    assert.notEqual(request.body.user.username, "");
    assert.notEqual(request.body.user.password, "");
    var _data = JSON.stringify(request.body);
    _file.writeFile('./simple_login_info.json', _data, function(error){
        assert.equal(error, null);
    });

    response.send(JSON.stringify({"code": 200, "message": "200"}));
};
