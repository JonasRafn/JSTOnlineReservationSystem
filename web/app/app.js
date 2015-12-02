'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'ui.bootstrap',
  'myApp.security',
  'myApp.CreateUser'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/create_user'});
}]).
config(function ($httpProvider) {
   $httpProvider.interceptors.push('authInterceptor');
});


