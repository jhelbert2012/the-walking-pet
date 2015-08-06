var frisby = require('frisby');
var config = require('./config.js');
var url = config.apiUrlNoAuth;

// Global setup for all tests
frisby.globalSetup({
	request: {
		headers:{'Accept': 'application/json'}
	}
});

frisby.create('Get Root')
.get(url)
.expectStatus(200)
.expectHeaderContains('content-type', 'application/json')
.expectJSONTypes({
	_links: Object
})
.toss();
