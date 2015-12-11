'use strict';

angular.module('myApp.Reservations', [])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/reservations', {
                    templateUrl: 'app/views/reservations/reservations.html',
                    controller: 'ReservationsCtrl'
                });
            }])

        .controller('ReservationsCtrl', ['ReservationsFactory', 'AdminService', '$scope', '$rootScope', 'ngDialog', function (ReservationsFactory, AdminService, $scope, $rootScope, ngDialog) {
                var self = this;

                self.reservations = AdminService.getReservations();
                self.reservation = AdminService.getReservation();

                $scope.hiddenDiv = false;

                self.getReservations = function (username) {
                    ReservationsFactory.getReservations(username)
                            .then(function (success) {
                                if (success.status === 204) {
                                    $rootScope.error = "You have made no reservations yet...";
                                } else {
                                    self.reservations = success.data;
                                    AdminService.setReservations(self.reservations);
                                }
                            }, function (error) {
                                $rootScope.error = error.data.message;
                            });
                };

                self.popUp = function (id) {
                    AdminService.setReservation(id);
                    ngDialog.open({
                        template: 'app/views/reservations/details.html',
                        controller: 'ReservationsCtrl'
                    });
                };

            }]).factory('ReservationsFactory', ['$http', function ($http) {

        var getReservations = function (username) {
            var url = "api/reservation/" + username;
            return $http.get(url);
        };

        return {
            getReservations: getReservations
        };
    }]);