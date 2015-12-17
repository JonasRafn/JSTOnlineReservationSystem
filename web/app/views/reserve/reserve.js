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

                for (var i = 0; i < ReserveService.getNumberOfPassengers(); i++) {
                    self.reservation.passengers.push({});
                }

                self.bookFlight = function () {
                    ReserveFactory.bookTickets(self.reservation);
                };


            }])
        .factory('ReserveFactory', ['$http', '$rootScope', '$location', '$timeout', function ($http, $rootScope, $location, $timeout) {
                var bookTickets = function (reservation) {
                    $http.post("api/reservation", reservation)
                            .then(function (response) {
                                $location.path("/reservations");
                                $timeout(function () {
                                    $rootScope.success = response.data.message;
                                }, 3000);
                                
                            }, function (response) {
                                $rootScope.error = response.data.message;
                            });
                };

                return {
                    bookTickets: bookTickets
                };
            }])
        .filter('passenger', function () {
            return function (input) {
                if (input === 1) {
                    return input + " Passenger";
                } else {
                    return input + " Passengers";
                }
            };
        });



