'use strict';

angular.module('myApp.Dashboard', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/dashboard', {
                    templateUrl: 'app/views/dashboard/dashboard.html',
                    controller: 'DashboardCtrl'
                });
            }])

        .controller('DashboardCtrl', ['HistoryFactory', function (HistoryFactory) {
                var self = this;
                self.history = {};
                self.getHistory = function () {
                    HistoryFactory.getHistory()
                            .success(function (history) {
                                self.history = history;
                            });
                };



            }
        ])

        .factory('HistoryFactory', ['$http', function ($http) {
                var getHistory = function () {
                    var url = "api/history";
                    return $http.get(url);
                };

                return {
                    getHistory: getHistory
                };
            }]);