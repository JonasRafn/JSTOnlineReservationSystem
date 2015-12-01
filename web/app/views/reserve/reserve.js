'use strict';

angular.module('myApp.Reserve', ['ngRoute'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reserve', {
                    templateUrl: 'app/views/reserve/reserve.html',
                    controller: 'ReserveCtrl'
                });
            }])

        .controller('ReserveCtrl', ['ReserveFactory', function (ReserveFactory) {
                var self = this;
                self.numberOfPassengers = 1;
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



