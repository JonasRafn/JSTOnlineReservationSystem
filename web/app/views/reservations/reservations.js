'use strict';

angular.module('myApp.Reservations', [])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reservations', {
                    templateUrl: 'app/views/reservations/reservations.html',
                    controller: 'ReservationsCtrl'
                });
            }])

        .controller('ReservationsCtrl', ['ReservationsFactory', '$scope', '$rootScope', function (ReservationsFactory, $scope, $rootScope) {
                var self = this;

                self.reservations = [];

                self.getReservations = function (username) {
                    ReservationsFactory.getReservations(username)
                            .then(function (success) {
                                self.reservations = success.data;
                            }, function (error) {
                                $rootScope.error = error.data.message;
                            });
                };

                $scope.hiddenDiv = false;


            }]).factory('ReservationsFactory', ['$http', function ($http) {
        var reservations = [
            {
                airline: 'MyCoolAirline',
                flightID: 'MCA2345',
                Origin: 'Copenhagen (CPH)',
                Destination: 'Barcelona (BCN)',
                Date: '2016-02-25T11:30:00.000Z',
                destinationDate: '2016-02-25T14:30:00.000Z',
                FlightTime: 190,
                numberOfSeats: 2,
                totalPrice: 400,
                pricePerson: 200,
                ReserveeName: 'Peter Hansen',
                Passengers: [
                    {firstName: 'Peter', lastName: 'Peterson'},
                    {firstName: 'Jane', lastName: 'Peterson'}
                ]
            },
            {
                airline: 'AnotherCoolAirline',
                flightID: 'MCA2345',
                Origin: 'Barcelona (BCN)',
                Destination: 'Copenhagen (CPH)',
                Date: '2016-03-05T07:00:00.000Z',
                destinationDate: '2016-02-25T14:30:00.000Z',
                FlightTime: 190,
                numberOfSeats: 2,
                totalPrice: 800,
                pricePerson: 400,
                ReserveeName: 'Peter Hansen',
                Passengers: [
                    {firstName: 'Peter', lastName: 'Peterson'},
                    {firstName: 'Jane', lastName: 'Peterson'}
                ]}
        ];

        var getReservations = function (username) {
            var url = "api/reservation/" + username;
            return $http.get(url);
        };

        return {
            getReservations: getReservations
        };
    }]);