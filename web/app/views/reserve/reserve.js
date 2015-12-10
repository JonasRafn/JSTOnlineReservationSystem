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
                var count = 0;
                self.reservation = {};
                self.reservation.airline = ReserveService.getAirline();
                self.reservation.flightID = ReserveService.getFlightID();
//                self.reservation.numberOfSeats = ReserveService.getNumberOfPassengers();
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
                    ReserveFactory.bookTickets(self.reservation);
                };

                self.getNumberOfPassengers = function () {
                    count++;
                    console.log("getNumberOfPassengers " + ReserveService.getNumberOfPassengers() + " Count " + count);
                    var nOP = ReserveService.getNumberOfPassengers();
                    console.log("nOP " + nOP);
                    return new Array(nOP);

                };

                self.setNumberOfPassengers = function (nOP) {
                    ReserveService.setNumberOfPassengers(nOP);
                    console.log("Reserve " + ReserveService.getNumberOfPassengers());
                };

            }])
        .factory('ReserveFactory', ['$http', '$rootScope', function ($http, $rootScope) {
                var bookTickets = function (reservation) {
                    $http.post("api/reservation", reservation)
                            .then(function (response) {
                                $rootScope.success = response.data.message;
                            }, function (response) {
                                $rootScope.error = response.data.message;
                            });
                };

                return {
                    bookTickets: bookTickets
                };
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



