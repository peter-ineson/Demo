
console.error("myTest ----------------- Hello World ----------------------");

var testCase = require('mocha').describe
var pre = require('mocha').before
var assertions = require('mocha').it
var assert = require('assert')

console.error("testCase = ", testCase);
console.error("pre = ", pre);
console.error("assertions = ", assertions);
console.error("assert = ", assert);

describe('js.myTest', function() {

  describe('run1', function(){
    it('should return -1 when not present', function(){
      assert.equal([1,2,3].indexOf(4), -1);
    });
  });

  describe('run2', function(){
    it('should return -1 when not present', function(){
      assert.equal([1,2,3].indexOf(3), -1);
    });
  });

});
