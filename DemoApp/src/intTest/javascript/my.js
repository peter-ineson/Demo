

console.error("NodeJs ----------------- Hello World ----------------------");


var describe = require('mocha').describe;
var pre = require('mocha').before;
var assertions = require('mocha').it;
var assert = require('assert');

var assert = require("assert");
describe('Array', function(){
  describe('#indexOf()', function(){
  	console.error("NodeJs ----------------- Run test ----------------------");
    it('should return -1 when the value is not present', function(){
      assert.equal(-1, [1,2,3].indexOf(5));
      assert.equal(-1, [1,2,3].indexOf(0));
    })
  })
});

