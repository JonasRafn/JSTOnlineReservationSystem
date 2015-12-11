'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngDialog',
    'ngRoute',
    'ngAnimate',
    'ui.bootstrap',
    'myApp.services',
    'myApp.security',
    'myApp.Reserve',
    'myApp.Results',
    'myApp.Search',
    'myApp.CreateUser',
    'myApp.Documentation',
    'myApp.Reservations',
    'myApp.Admin',
    'myApp.Dashboard'
]).
        config(['$routeProvider', function ($routeProvider) {
                $routeProvider.otherwise({redirectTo: '/search'});
            }])
        .config(function ($httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
        });

