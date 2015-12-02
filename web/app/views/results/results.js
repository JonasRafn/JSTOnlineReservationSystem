'use strict';

angular.module('myApp.Results', [])
        .directive('results', function(){
            return {
                templateUrl: 'app/views/results/results.html'
            };
        });