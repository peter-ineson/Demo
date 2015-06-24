

console.error("myTest ----------------- Hello World - Int Test ----------------------");

var testCase = require('mocha').describe
var pre = require('mocha').before
var assertions = require('mocha').it
var assert = require('assert')

console.error("testCase = ", testCase);

testCase('js.myTest', function(){
  pre(function(){
    // ...
  });

  testCase('run3', function(){
    assertions('should return -1 when not present ------', function(){
      assert.equal([1,2,3].indexOf(4), -1);
    });
  });

  testCase('run4', function(){
	    assertions('should return -1 when not present ---- ', function(){
	      assert.equal([1,2,3].indexOf(3), -1);
	    });
	  });

});
