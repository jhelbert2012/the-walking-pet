/**
 * frisby.js: Facebook usage example
 * (C) 2012, Vance Lucas
 */
 var frisby = require('frisby');

// Global setup for all tests
frisby.globalSetup({
	request: {
		headers:{'Accept': 'application/json'}
	}
});

frisby.create('Get Printers')
.get('http://api.zebra.com/v1/printers/105SL')
.expectStatus(200)
.expectHeaderContains('content-type', 'application/json')
.expectJSONTypes({
	id: String,
	name: String
})
.expectJSON({
	id: '105SL',
	name: '105SL'
})
.toss();
