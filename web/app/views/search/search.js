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
                self.airports = AirportFactory.getAirports();
                self.results = {};
                self.ShowResults = false;
                self.searchRequest = {};
                self.search = function () {
                    if (self.searchRequest.destination === undefined) {
                        SearchFactory.getAllFlightsFromOrigin(self.searchRequest)
                            .success(function (results) {
                                self.results = results;
                                self.ShowResults = true;
                            })
                            .error(function (data) {
                                $rootScope.error = data.error + " : " + data.message;
                            });
                    }
                    else {
                        SearchFactory.getAllFlightsFromOriginToDestination(self.searchRequest)
                            .success(function (results) {
                                self.results = results;
                                self.ShowResults = true;
                            })
                            .error(function (data) {
                                $rootScope.error = data.error + " : " + data.message;
                            });
                    }
                };

            }])

        .factory('AirportFactory', ['$http', function ($http) {
                var airports = [{IATACODE: "CPH", country: "Denmark", city: "Copenhagen"},
                    {IATACODE: "STN", country: "England", city: "London"},
                    {IATACODE: "BCN", country: "Spain", city: "Barcelona"},
                    {IATACODE: "MIL", country: "Italy", city: "Milano"}
                ];

                var getAirports = function () {
                    return airports;
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
        