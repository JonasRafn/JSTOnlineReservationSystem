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
                
                //Method to fetch airports and used to populate the datalist in search.html
                self.getAirports = function(){
                    AirportFactory.getAirports()
                        .success(function (airports){
                            self.airports = airports;
                        });
                };
                
                self.results = {};
                
                //Boolean used to handle the result view.
                self.ShowResults = false;
                
                //Two way binding object for the users input parameters 
                self.searchRequest = {};
                
                // Method to handle the two different search request and fetch results
                self.search = function () {
                    
                    // This part handles the search request for all departures from origin
                    if (self.searchRequest.destination === undefined || self.searchRequest.destination === "") {
                        SearchFactory.getAllFlightsFromOrigin(self.searchRequest)
                            
                            //Success
                            .then(function (response){
                                var status = response.status;
                                var data = response.data;
                                //Check for no response status 
                                if(status === 204){
                                    self.ShowResults = false;
                                    $rootScope.error = "Oops! No flights were found...\n We weren't able to find any flights matching your request. Please try again, perhaps with alternative dates or airports.";
                                } else {
                                    self.ShowResults = true;
                                    self.results = data;
                                }
                            }
                            //Error
                            , function (response) {
                                self.ShowResults = false;
                                $rootScope.error = response.data.message;
                            });
                    }
                    // This part handles the search request for all departures from origin to destination
                    else {
                        SearchFactory.getAllFlightsFromOriginToDestination(self.searchRequest)
                            //Success
                            .then(function (response){
                                var status = response.status;
                                var data = response.data;
                                //Check for no response status 
                                if(status === 204){
                                    self.ShowResults = false;
                                    $rootScope.error = "Oops! No flights were found...\n We weren't able to find any flights matching your request. Please try again, perhaps with alternative dates or airports.";
                                } else {
                                    self.ShowResults = true;
                                    self.results = data;
                                }
                            }
                            //Error
                            , function (response) {
                            self.ShowResults = false;
                            $rootScope.error = response.data.message;
                        });
                    }
                };

            }])
        
        // Factory to fetch airports from the db.
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
        
        //  Factory to fetch search results from a search request
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
                
                // Method uses to parse the IATACode fx. CPH 
                var parseIATACode = function (Airport) {
                    var partsArray = Airport.split(' ');
                    return partsArray[1].substring(1, 4);
                };
                
                return {
                    getAllFlightsFromOrigin: getAllFlightsFromOrigin,
                    getAllFlightsFromOriginToDestination: getAllFlightsFromOriginToDestination
                };
            }])
        
        //Filter used to format the traveltime
        .filter('formatTimer', function () {
                return function (hours) {
                    var seconds =  hours * 60;
                    var d = new Date(0,0,0,0,0,0,0);
                    d.setSeconds(seconds);
                    return d;
                };  
        }); 
        
