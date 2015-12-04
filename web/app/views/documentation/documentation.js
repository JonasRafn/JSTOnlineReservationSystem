'use strict';

angular.module('myApp.Documentation', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/documentation', {
              templateUrl: 'app/views/documentation/documentation.html',
              controller: 'DocumentationCtrl'
            });
          }])

        .controller('DocumentationCtrl', function () {

        });