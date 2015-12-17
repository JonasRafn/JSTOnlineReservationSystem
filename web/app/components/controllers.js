/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', ["ui.bootstrap"]).
        controller("HeroCtrl", function ($scope, $location) {
            $scope.showPageHero = $location.path() === "/search";
        })
        .controller('DatepickerDemoCtrl', [function () {

                var vm = this;

                vm.valuationDate = new Date();
                vm.valuationDatePickerIsOpen = false;

                vm.valuationDatePickerOpen = function () {

                    this.valuationDatePickerIsOpen = true;
                };
            }]);



