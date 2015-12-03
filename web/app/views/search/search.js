'use strict';

angular.module('myApp.Search', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/search', {
                    templateUrl: 'app/views/search/search.html',
                    controller: 'SearchCtrl'
                });
            }])

        .controller('SearchCtrl', ['$rootScope','SearchFactory', 'AirportFactory', function ($rootScope, SearchFactory, AirportFactory) {
                var self = this;
                self.airports = {};
                self.getAirports = function(){
                    AirportFactory.getAirports()
                        .success(function (airports){
                            self.airports = airports;
                        });
                };
                self.results = {};
                self.ShowResults = false;
                self.searchRequest = {};
                self.search = function () {
                    if (self.searchRequest.destination === undefined || self.searchRequest.destination === "") {
                        SearchFactory.getAllFlightsFromOrigin(self.searchRequest).then(function (response){
                            var status = response.status;
                            var data = response.data;
                            if(status === 204){
                                $rootScope.error = "Oops! No flights were found...\n We weren't able to find any flights matching your request. Please try again, perhaps with alternative dates or airports.";
                            } else {
                                self.ShowResults = true;
                                self.results = data;
                            }
                        }, function (response) {
                            $rootScope.error = response.data.message;
                        });
                    }
                    else {
                        SearchFactory.getAllFlightsFromOriginToDestination(self.searchRequest).then(function (response){
                            var status = response.status;
                            var data = response.data;
                            if(status === 204){
                                $rootScope.error = "Oops! No flights were found...\n We weren't able to find any flights matching your request. Please try again, perhaps with alternative dates or airports.";
                            } else {
                                self.ShowResults = true;
                                self.results = data;
                            }
                        }, function (response) {
                            $rootScope.error = response.data.message;
                        });
                    }
                };

            }])

        .factory('AirportFactory', ['$http', function ($http) {
                var getAirports = function () {
                   var url = "api/airports";
                   var airports = $http.get(url);
                   return $http.get(url);
                };
                
                return {
                    getAirports: getAirports  
                };
            }])

        .factory('SearchFactory', ['$http', function ($http, $rootScope) {
                var getAllFlightsFromOrigin = function (searchRequest) {
                    var origin = parseIATACode(searchRequest.origin);
                    var url = "api/flightinfo/" + origin + "/" + searchRequest.departing.toISOString() + "/" + searchRequest.numberOfTickets;
                    return $http.get(url);
                };

                var getAllFlightsFromOriginToDestination = function (searchRequest) {
                    var origin = parseIATACode(searchRequest.origin);
                    var destination = parseIATACode(searchRequest.destination);
                    var url = "api/flightinfo/" + origin + "/" + destination + "/" + searchRequest.departing.toISOString() + "/" + searchRequest.numberOfTickets;
                    return $http.get(url);
                };    

                var parseIATACode = function (Airport) {
                    var partsArray = Airport.split(' ');
                    return partsArray[1].substring(1, 4);
                };
                
                return {
                    getAllFlightsFromOrigin: getAllFlightsFromOrigin,
                    getAllFlightsFromOriginToDestination: getAllFlightsFromOriginToDestination
                };
            }])
        
        .filter('formatTimer', function () {
                return function (hours) {
                    var seconds =  hours * 60;
                    var d = new Date(0,0,0,0,0,0,0);
                    d.setSeconds(seconds);
                    return d;
                };  
        }); 
        
