'use strict';

angular.module('myApp.Reserve', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reserve', {
                    templateUrl: 'app/views/reserve/reserve.html',
                    controller: 'ReserveCtrl'
                });
            }])

        .controller('ReserveCtrl', ['ReserveFactory', 'ReserveService', '$rootScope', function (ReserveFactory, ReserveService, $rootScope) {
                var self = this;
                self.reservation = {};
                self.reservation.airline = ReserveService.getAirline();
                self.reservation.flightID = ReserveService.getFlightID();
                self.reservation.numberOfSeats = ReserveService.getNumberOfPassengers();
                self.reservation.date = ReserveService.getDate();
                self.reservation.totalPrice = ReserveService.getTotalPrice();
                self.reservation.pricePerson = ReserveService.getPricePerson();
                self.reservation.flightTime = ReserveService.getFlightTime();
                self.reservation.origin = ReserveService.getOrigin();
                self.reservation.originCity = ReserveService.getOriginCity();
                self.reservation.destination = ReserveService.getDestination();
                self.reservation.destinationCity = ReserveService.getDestinationCity();
                self.reservation.destinationDate = ReserveService.getDestinationDate();
                self.reservation.user = {userName: $rootScope.username};

                self.reservation.passengers = [];

                self.bookFlight = function () {
                    console.log(JSON.stringify(self.reservation));
                };

                self.getNumberOfPassengers = function () {
                    return new Array(self.reservation.numberOfSeats);
                };

//                self.setNumberOfPassengers = function (nOP) {
//                    console.log(nOP);
//                    ReserveService.setNumberOfPassengers(nOP);
//                };
            }])
        .factory('ReserveFactory', ['$http', function ($http) {
                return {};
            }])
        .filter('range', function () {
            return function (input, total) {
                total = parseInt(total);

                for (var i = 0; i < total; i++) {
                    input.push(i);
                }

                return input;
            };
        });



