'use strict';

angular.module('myApp.CreateUser', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/create_user', {
                    templateUrl: 'app/views/create_user/create_user.html',
                    controller: 'CreateUserCtrl'
                });
            }])

        .controller('CreateUserCtrl', function ($http, $rootScope) {
            var self = this;
            self.user = {};
            self.createUser = function () {
                if (self.user.userName === undefined) {
                    $rootScope.error = "You must input a username!";
                } else if (self.user.password === undefined) {
                    $rootScope.error = "You must input a password!";
                } else {
                    $http.post("api/create", self.user)
                            .then(function (response) {
                                $rootScope.success = "User " + response.data.userName + " succesfullly created!";
                            }, function (response) {
                                $rootScope.error = response.data.message;
                            });
                }
            };
        });
