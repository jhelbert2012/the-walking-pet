var frisby = require('frisby');
var config = require('./config.js');
var url = config.apiUrlNoAuth + 'alps';

frisby.create('API: List config')
  .get(url)
    .expectStatus(200)
    .expectHeaderContains('content-type', 'application/alps+json')
    .expectJSONTypes({
        version: String,
        descriptors: Array
    })
    .expectJSON({
    	version: "1.0"
    })
    .toss();