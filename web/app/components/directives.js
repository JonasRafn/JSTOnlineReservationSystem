'use strict';

/* Place you Global Directives in this file */

angular.module('myApp.directives', []).
        directive('angularLinks', function () {
            return {
                restrict: 'AE',
                replace: 'true',
                template: '<ul style="list-style-type: none">' +
                        '<li><a href="http://www.sitepoint.com/practical-guide-angularjs-directives/">A practical Guide</a></li>' +
                        '<li><a href="http://weblogs.asp.net/dwahlin/creating-custom-angularjs-directives-part-i-the-fundamentals">Creating Custom Directives</a></li>' +
                        '</ul>'
            };
        }).
        directive('jqdatepicker', function () {
            return {
                restrict: 'A',
                require: 'ngModel',
                link: function (scope, element, attrs, ngModelCtrl) {
                    $(function () {
                        element.datepicker({
                            dateFormat: 'dd/mm/yy',
                            onSelect: function (date) {
                                scope.$apply(function () {
                                    ngModelCtrl.$setViewValue(date);
                                });
                            }
                        });
                    });
                }
            };
        });
