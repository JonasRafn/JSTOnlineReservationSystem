'use strict';

angular.module('myApp.Results', [])
        .directive('results', function(){
            return {
                templateUrl: 'app/views/results/results.html'
            };
        });

//        .controller('ResultsCtrl', ['ResultsFactory', function (ResultsFactory) {
//                var self = this;
//                self.getResults = ResultsFactory.getResults();
//            }])
//                
//
//        .factory('ResultsFactory', ['$http', function ($http) {
//                var results = [
//                    {date: '2016-02-25T11:30:00.000Z', numberOfSeats: 2, totalPrice: 400, pricePerson: 200, flightID: 'MCA2345', airline: 'RyanAir', travelTime: 190, arrivalTime:'16:20', destination: 'ACA', destinationAirport: 'Acapulco International Airport', destinationCity: 'Acapulco', origin: 'CPH', originAirport: 'Copenhagen Airport', originCity: 'Copenhagen'},
//                    {date: '2016-02-25T11:30:00.000Z', numberOfSeats: 2, totalPrice: 400, pricePerson: 200, flightID: 'MCA2345', airline: 'RyanAir', travelTime: 190, arrivalTime:'16:20', destination: 'ACA', destinationAirport: 'Acapulco International Airport', destinationCity: 'Acapulco', origin: 'CPH', originAirport: 'Copenhagen Airport', originCity: 'Copenhagen'},
//                    {date: '2016-02-25T11:30:00.000Z', numberOfSeats: 2, totalPrice: 400, pricePerson: 200, flightID: 'MCA2345', airline: 'RyanAir', travelTime: 190, arrivalTime:'16:20', destination: 'ACA', destinationAirport: 'Acapulco International Airport', destinationCity: 'Acapulco', origin: 'CPH', originAirport: 'Copenhagen Airport', originCity: 'Copenhagen'}
//                ];
//
//                var getResults = function () {
//                    return results;
//                };
//
//                return {
//                    getResults: getResults
//                };
//            }]);



