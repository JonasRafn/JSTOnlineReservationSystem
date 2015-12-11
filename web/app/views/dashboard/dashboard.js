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
                        //Success
                        .then(function (response) {
                            self.history = response.data;
                        }
                        
                        //Error
                        , function(response){
                            if(response.status === 401 || response.status === 403){
                                    $rootScope.error = response.data.error.message;
                                } else {
                                    $rootScope.error = response.data.message;
                                } 
                        });
                };
        }])
        
        .factory('HistoryFactory', ['$http', function ($http) {
                var getHistory = function () {
                    var url = "api/dashboard/history";
                    return $http.get(url);
                };

                return {
                    getHistory: getHistory
                };
            }]);