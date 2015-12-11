'use strict';
angular.module('myApp.Admin', ['ngDialog'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/admin', {
                    templateUrl: 'app/views/admin/admin.html',
                    controller: 'AdminCtrl'
                });
            }])
        .controller('AdminCtrl', ['AdminFactory', 'AdminService', '$rootScope', 'ngDialog', function (AdminFactory, AdminService, $rootScope, ngDialog) {
                var self = this;
        
                self.reservations = AdminService.getReservations();
                self.reservation = AdminService.getReservation();
                
                self.getReservations = function (username) {
                    AdminFactory.getReservations(username)
                            .then(function (success) {
                                if (success.status === 204) {
                                    $rootScope.error = "No reservations in database";
                                } else {
                                    self.reservations = success.data;
                                    AdminService.setReservations(self.reservations);
                                }
                            }, function (error) {
                                $rootScope.error = error.data.message;
                            });
                };
                
                self.deleteReservation = function (reservation) {
                    AdminFactory.deleteReservation(reservation)
                            .then(function (success) {
                                $rootScope.success = success.data.message;
                            }, function (error) {
                                $rootScope.error = error.data.message;
                            });
                            self.reservations = AdminService.deleteReservation(reservation.reservationID);
                };

                self.popUp = function (id) {
                    AdminService.setReservation(id);
                    ngDialog.open({
                        template: 'app/views/admin/details.html',
                        controller: 'AdminCtrl'
                    });
                };
            }])
        .factory('AdminFactory', ['$http', function ($http) {

                var getReservations = function (username) {
                    var url = "api/reservation/" + username;
                    return $http.get(url);
                };

                var deleteReservation = function (reservation) {
                    var url = "api/reservation/";
                    return $http.delete(url + reservation.reservationID);
                };

                return {
                    getReservations: getReservations,
                    deleteReservation: deleteReservation
                };
            }]);

