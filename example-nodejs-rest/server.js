function respondToRequest(request, response) {
	console.log(request.method + " -> " + request.url);
    response.send(200);
};

var express = require('express');
var server = express();
server.configure(function() {
    server.use(express.bodyParser());
});

server.get('/ratings', function (request, response) {
	response.json({ratings: ratingRepository.findAll()});
});

server.get('/ratings/:id', function (request, response) {
    var rating = ratingRepository.find(request.params.id);
    if (rating != null) {
    	response.json(rating);
    }
    else {
    	response.send(404);
    }
});

server.post('/ratings', function (request, response) {
	var rating = request.body;
	ratingRepository.create(rating.label, rating.score);
	response.send(200);
 });

server.put('/ratings/:id', function (request, response) {
    var storedRating = ratingRepository.find(request.params.id);
    if (storedRating != null) {		
		var requestRating = request.body;
		ratingRepository.update(storedRating.id, requestRating.label, requestRating.score);
		response.send(200);
    }
    else {
    	response.send(404);
    }
});

server.delete('/ratings/:id', function (request, response) {
    var rating = ratingRepository.find(request.params.id);
    if (rating != null) {
		ratingRepository.delete(request.params.id);
		response.send(200);
 	} else {
		response.send(404);
    }
});

server.listen(4000);


// see: http://www.unleashed-technologies.com/blog/2010/12/09/introduction-javascript-module-design-pattern
var ratingRepository = (function() {

    var ratings = {};
    var nextId = 0;
     
    return {
        findAll: function() {
 			var ratingValues = new Array();
			for (var key in ratings) {
    			ratingValues.push(ratings[key]);
			}
			return ratingValues;
        },

        find: function(id) {
 			return ratings[id];
        },

        create: function(label, score) {
            var rating = { id: nextId, label: label, score: score };
        	ratings[rating.id] = rating;
        	nextId++;
        },

        update: function(id, label, score) {
        	rating = ratings[id];
        	rating.label = label;
        	rating.score = score;
        },

        delete: function(id) {
        	delete ratings[id];
        }
    }
})();

ratingRepository.create("The Dark Knight", 9);
ratingRepository.create("Machete", 6);
ratingRepository.create("Die Hard", 8);

console.log("Started server: http://127.0.0.1:4000");