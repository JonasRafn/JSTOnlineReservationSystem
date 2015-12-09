'use strict';

angular.module('myApp.Dashboard', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/dashboard', {
                    templateUrl: 'app/views/dashboard/dashboard.html',
                    controller: 'DashboardCtrl'
                });
            }])

        .controller('DashboardCtrl', ['HistoryFactory','AirlineFactory', function (HistoryFactory, AirlineFactory) {
                var self = this;
                self.airline = {};
                self.addAirline = function(){
                    AirlineFactory.addAirline(self.airline);
                }
                self.history = {};
                self.getHistory = function () {
                    HistoryFactory.getHistory()
                            .success(function (history) {
                                self.history = history;
                            });
                };

            }
        ])
        
        .factory('AirlineFactory', ['$http', '$rootScope', function($http, $rootScope){
               var addAirline = function(airline){
                   $http.post("api/dashboard", airline).then(function (response) {
                            $rootScope.success = "Airline succesfullly created!";
                        }, function (response) {
                            $rootScope.error = response.data.message;
                        });
               };
               return {
                    addAirline: addAirline
                };
        }])

        .factory('HistoryFactory', ['$http', function ($http) {
                var getHistory = function () {
                    var url = "api/dashboard";
                    return $http.get(url);
                };

                return {
                    getHistory: getHistory
                };
            }]);