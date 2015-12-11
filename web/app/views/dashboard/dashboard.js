'use strict';

angular.module('myApp.Dashboard', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/dashboard', {
                    templateUrl: 'app/views/dashboard/dashboard.html',
                    controller: 'DashboardCtrl'
                });
            }])

        .controller('DashboardCtrl', ['$rootScope','HistoryFactory', function ($rootScope, HistoryFactory) {
                var self = this;
                self.history = {};
                self.getHistory = function () {
                    HistoryFactory.getHistory()
                            .success(function (response) {
                                self.history = response;
                            }).error(function(response){
                                $rootScope.error = response.error.message;
                            });
                };
            }
        ])
        
        .factory('HistoryFactory', ['$http', function ($http) {
                var getHistory = function () {
                    var url = "api/dashboard/history";
                    return $http.get(url);
                };

                return {
                    getHistory: getHistory
                };
            }]);