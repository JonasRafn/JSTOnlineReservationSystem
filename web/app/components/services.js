'use strict';

/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
        .service('ReserveService', [function () {
                var flightID = "";

                this.getFlightID = function () {
                    return flightID;
                };

                this.setFlightID = function (id) {
                    flightID = id;
                };
            }]);