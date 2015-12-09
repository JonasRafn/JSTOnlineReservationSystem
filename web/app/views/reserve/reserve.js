'use strict';

angular.module('myApp.Reserve', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reserve', {
                    templateUrl: 'app/views/reserve/reserve.html',
                    controller: 'ReserveCtrl'
                });
            }])

        .controller('ReserveCtrl', ['ReserveFactory', 'ReserveService', function (ReserveFactory, ReserveService) {
                var self = this;
                self.numberOfPassengers = 1;
                self.flightID = ReserveService.getFlightID();

                self.setFlightID = function () {
                    ReserveService.setFlightID("MCA2345");
                    self.flightID = ReserveService.getFlightID();
                };
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



