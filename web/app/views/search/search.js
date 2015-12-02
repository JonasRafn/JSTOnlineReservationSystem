'use strict';

angular.module('myApp.Search', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/search', {
                    templateUrl: 'app/views/search/search.html',
                    controller: 'SearchCtrl'
                });
            }])

        .controller('SearchCtrl', ['SearchFactory', 'AirportFactory', function (SearchFactory, AirportFactory, $rootScope) {
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
                        SearchFactory.getAllFlightsFromOrigin(self.searchRequest)
                            .success(function (results) {
                                self.results = results;
                                self.ShowResults = true;
                            })
                            .error(function (data) {
                                $rootScope.error = data.message;
                            });
                    }
                    else {
                        SearchFactory.getAllFlightsFromOriginToDestination(self.searchRequest)
                            .success(function (results) {
                                self.results = results;
                                self.ShowResults = true;
                            })
                            .error(function (data) {
                                $rootScope.error = data.message;
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

            }]);
        