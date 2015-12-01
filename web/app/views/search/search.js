'use strict';

angular.module('myApp.Search', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/search', {
                    templateUrl: 'app/views/search/search.html',
                    controller: 'SearchCtrl'
                });
            }])

        .controller('SearchCtrl', ['TravelFactory', function (TravelFactory) {
                var self = this;
                self.airports = TravelFactory.getAirports();
                self.ShowResults = false;
                self.searchRequest = {};
                self.search = function (){
                    if(self.searchRequest.destination === undefined){
                        TravelFactory.getAllFlightsFromOrigin(self.searchRequest);
                    }
                    else {
                        TravelFactory.getAllFlightsFromOriginToDestination(self.searchRequest);
                    }
                }

            }])

        .factory('TravelFactory', ['$http', function ($http) {
                var airports = [{IATACODE: "CPH", country: "Denmark", city: "Copenhagen"},
                    {IATACODE: "LHR", country: "England", city: "London"},
                    {IATACODE: "BCN", country: "Spain", city: "Barcelona"},
                    {IATACODE: "MIL", country: "Italy", city: "Milano"}
                ];
                
                var flights = [];
                
                var getAirports = function () {
                    return airports;
                };
                
                var getFlights = function () {
                    return flights;
                };  
                
                var getAllFlightsFromOrigin = function(searchRequest) {
                    var origin = parseIATACode(searchRequest.origin);
                    var url = "api/flightinfo/" + origin + "/" + searchRequest.departing.toISOString() + "/" + searchRequest.numberOfTickets;
                    $http.get(url)
                            .success(function (data) {
                                flights.length = 0;
                                console.log(url);
//                                data.forEach(function(flight) {
//                                      console.log(flight)
////                                    flights.push(flight);
//                                });                             
                            })
                            .error(function (data) {
                                $rootScope.error = data.error + " : " + data.message;
                            });
                };
                
                var getAllFlightsFromOriginToDestination = function(searchRequest) {
                    var origin = parseIATACode(searchRequest.origin);
                    var destination = parseIATACode(searchRequest.destination);
                    var url = "api/flightinfo/" + origin + "/" + destination + "/" + searchRequest.departing.toISOString() + "/" + searchRequest.numberOfTickets;
                    $http.get(url)
                            .success(function (data) {
                            })
                            .error(function (data) {
                                $rootScope.error = data.error + " : " + data.message;
                            });
                };
                
                var parseIATACode = function(Airport){
                    var partsArray = Airport.split(' ');
                    return partsArray[1].substring(1,4);
                }
                return {
                    getAirports: getAirports,
                    getAllFlightsFromOrigin: getAllFlightsFromOrigin,
                    getAllFlightsFromOriginToDestination: getAllFlightsFromOriginToDestination
                };
                
            }]);



