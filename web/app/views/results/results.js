'use strict';

angular.module('myApp.Results', [])
        //directive used in search.html to show results 
        .directive('results', function(){
            return {
                templateUrl: 'app/views/results/results.html'
            };
        });