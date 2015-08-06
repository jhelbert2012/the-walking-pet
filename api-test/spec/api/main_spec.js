var frisby = require('frisby');
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
	_links: Array
})
.toss();
