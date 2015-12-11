'use strict';

/* Place your Global Services in this File */

// Demonstrate how to register services
angular.module('myApp.services', [])
        .service('ReserveService', [function () {

                var airline = "";
                var flightID = "";
                var numberOfPassengers;
                var date = "";
                var totalPrice = 0;
                var pricePerson = 0;
                var flightTime = 0;
                var origin = "";
                var originCity = "";
                var destination = "";
                var destinationCity = "";
                var destinationDate = "";

                this.getAirline = function () {
                    return airline;
                };

                this.setAirline = function (airLine) {
                    airline = airLine;
                };

                this.getFlightID = function () {
                    return flightID;
                };

                this.setFlightID = function (id) {
                    flightID = id;
                };

                this.getNumberOfPassengers = function () {
                    return numberOfPassengers;
                };

                this.setNumberOfPassengers = function (nOP) {
                    numberOfPassengers = nOP;
                    console.log("Service " + numberOfPassengers);
                };

                this.getDate = function () {
                    return date;
                };

                this.setDate = function (Date) {
                    date = Date;
                };

                this.getTotalPrice = function () {
                    return totalPrice;
                };

                this.setTotalPrice = function (price) {
                    totalPrice = price;
                };

                this.getPricePerson = function () {
                    return pricePerson;
                };

                this.setPricePerson = function (price) {
                    pricePerson = price;
                };

                this.getFlightTime = function () {
                    return flightTime;
                };

                this.setFlightTime = function (time) {
                    flightTime = time;
                };

                this.getOrigin = function () {
                    return origin;
                };

                this.setOrigin = function (Origin) {
                    origin = Origin;
                };

                this.getOriginCity = function () {
                    return originCity;
                };

                this.setOriginCity = function (city) {
                    originCity = city;
                };

                this.getDestination = function () {
                    return destination;
                };

                this.setDestination = function (dest) {
                    destination = dest;
                };

                this.getDestinationCity = function () {
                    return destinationCity;
                };

                this.setDestinationCity = function (dest) {
                    destinationCity = dest;
                };

                this.getDestinationDate = function () {
                    return destinationDate;
                };

                this.setDestinationDate = function (date) {
                    destinationDate = date;
                };
            }]).service('AdminService', [function () {

        var ID;
        var reservations = [];
        var reservation;

        this.setID = function (id) {
            ID = id;
        };

        this.getID = function () {
            return ID;
        };

        this.getReservation = function () {
            return reservation;
        };

        this.setReservation = function (ID) {
            for (var i = 0; i < reservations.length; i++) {
                if (reservations[i].reservationID === ID) {
                    reservation = reservations[i];
                }
            }
        };
        
        this.deleteReservation = function(ID) {
            for (var i = 0; i <reservations.length; i++) {
                if(reservations[i].reservationID === ID) {
                    reservations.splice(i, 1);
                }
            }
            return reservations;
        };

        this.getReservations = function () {
            return reservations;
        };

        this.setReservations = function (res) {
            reservations = res;
        };
    }]);