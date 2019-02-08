$(document).ready(function () {

    checkUserType();

    $.get({
        url: 'api/flight/allOrders',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                if(data.length > 0)
                {
                    data.forEach(function (entry) {
                        fillInfo(entry);
                    });
                    $('#status').html("")
                }
                else
                {
                    $('#status').html("Make your first reservation today!!")
                }
            }
        },
        error:function (message) {
            //alert(message.status);
        }

    });


    $('.change-list').change(function () {
        var id = $('select[name="selectAirlineReservations"]').val();

        $.get({
            url: 'api/flight/'+ id +'/order',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (data) {
                var sel = '#orderListings';
                $(sel).append('<h6> Order id: ' + data.id+ ' </h6>');
               /* if (data.reservations != undefined) {
                    data.reservations.forEach(function (entry) {
                        guiAriline(entry.flight,sel, entry.name, entry.lastName, entry.confirmed);
                    });
                }
                if (data.rentReservation != undefined){
                    guiRent(data.rentReservation, sel);
                }
                if (data.reservationHotel != undefined){
                    guiHotel(data.reservationHotel.room);
                }*/

            },
            error:function (message) {
                //alert(message.status);
            }

        });


    });




});

function fillInfo(data){
    $('#listReservations').append('<option value= "' + data.id + '"> For order id:' + data.id + '</option>')
}

//0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin
function checkUserType() {
    $.get({
        url: '/api/user/userType',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != 0) {
                $(".user").remove();
            }else {
                $(".user").show();
            }
        },
        error: function (e) {

        }
    });
}

function guiRent(rentReservation, selectedElement) {


        var car = rentReservation.rentedCar;
        var pricing =  rentReservation.price;
        var normal = car.dailyPrice * daysBetween(rentReservation.startDate, rentReservation.endDate);
        var tr = $(
            '<div class="card mb-1">' +
            '                                                <div class="card-body">' +
            '                                                    <div class="row">' +
            '                                                        <div class="col-md-3" style="max-height: 150px">' +
            '                                                            <img class="card-img" src="assets/img/carlisting.svg" style="height: 100px;">' +
            '                                                        </div>' +
            '                                                        <div class="col-md-6 border-right">' +
            '                                                            <h5 class="text-danger" id="Mark-Model-Name">'+ car.mark+" " +  car.model + " " + car.name + '</h5>' +
            '                                                            <div style="max-width:100%" class="badge badge-secondary" href="#">Car Type: <span id="CarType">' +  car.type.name +   '</span></div>' +
            '                                                            <br>' +
            '                                                            <div style="height: 33%;width:25%;float:left;">People:<strong></br><div id="numberPeople" style="width:auto;float: left">' + car.maxPassengers  + '</div></strong></div>' +
            '                                                            <div style="height: 33%;width:25%;float:right;">Doors: <strong></br><div id="numberDoors"  style="width:auto;float: left">' +  car.numberOfDoors + '</div></strong></div>' +
            '                                                            <div style="height: 33%;width:25%;float:right;">Number of Bags: <strong></br><div id="numberBags"  style="width:auto;float: left">' + car.numberOfBags  +  '</div> </strong></div>' +
            '                                                            <div style="height: 33%;width:25%;float:right;">Dates: <strong></br><div id="dates"  style="width:auto;float: left">' + rentReservation.startDate.split('T')[0] +' to ' + rentReservation.endDate.split('T')[0] + '</div></strong></div>' +
            '                                                        </div>' +
            '                                                        <div class="col-md-3">' +
            '                                                            <h5>$' + pricing +'</h5>' +
            '                                                            <h6 style="text-decoration: line-through">$'+ normal+'</h6>                         ' +
            '                                                            <i class="fa fa-star"></i>' +
            '                                                            <i class="fa fa-star"></i>' +
            '                                                            <i class="fa fa-star"></i>' +
            '                                                            <i class="fa fa-star"></i>' +
            '                                                            <i class="fa fa-star"></i>' +
            '                                                            <button type="button" id="buyButtonCar' + rentReservation.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Reserve</button>' +
            '                                                        </div>' +
            '                                                    </div>' +
            '                                                </div>' +
            '                                            </div>');
        //$('#addListings').append(tr);
         $(selectedElement).append(tr)
        /*
        $('#' + 'buyButtonCar' +  rentReservation.id).click(function () {

            var airlineReservationId =  $('select[name="selectAirlineReservations"]').val();

            if(airlineReservationId =="" || airlineReservationId == undefined)
            {
                $('#error').html("Please select order reservation to be able to confirm").fadeIn().delay(3000).fadeOut();
            }
            else {

                $.post({
                    url: "api/cars/" + rentReservation.id + "/" + getUrlParameter('id') + "/quickReserve",
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    contentType: 'application/json',
                    success: function () {

                        $('#success').html("Your renting of a car is a success").fadeIn().delay(3000).fadeOut();

                        setTimeout(function () {
                            window.location.replace('index.html');
                        },2000)

                    },
                    error: function (data) {
                        if (data.status == undefined) {
                            data.status = 'Unknown';
                        }
                        if (data.status == '500') {
                            $('#error').html("Alredy reserved").fadeIn().delay(3000).fadeOut();
                        } else
                            $('#error').html("Error happened while reserving this(" + data.status + ')').fadeIn().delay(3000).fadeOut();

                    }
                });
            }
        }); */



}

var guiHotel = function (room, selectedElement) {
    var price = room.hotel.priceList;
    var hotelId = room.hotel.id;

    var cena = undefined;
    price.forEach(function (entry) {
       if (entry.roomType = room.roomType){
           cena = entry.price;
       }
    });

    var pricing = (cena==undefined)?"Unknown":price;
    var name = (room.name !=null)? room.name : room.roomType.name + ' ' + room.roomNumber ;
    var tr = $(
        '<div class="card mb-1">' +
        '                                                <div class="card-body">' +
        '                                                    <div class="row">' +
        '                                                        <div class="col-md-3" style="max-height: 150px">' +
        '                                                            <img style="height: 100%" class="card-img" src="assets/img/room.svg">' +
        '                                                        </div>' +
        '                                                        <div class="col-md-6 border-right">' +
        '                                                            <h5 class="text-danger" id="name">' +name +    '</h5>' +
        '                                                            <div style="max-width:100%" class="badge badge-secondary" href="#">Room Type: <span id="RoomType">' + room.roomType.name +   '</span></div>' +
        '                                                            <br>' +
        '                                                            <div style="height: 33%;width:25%;float:left;">People:<strong></br><div id="numberPeople" style="width:auto;float: left">' + room.numberOfPeople + '</div><img src="assets/img/people.png" style="margin-top: 5px"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">Rooms: <strong></br><div id="numberRooms"  style="width:auto;float: left">' + room.numberOfRooms + '</div><img height="16" src="assets/img/room.svg" style="margin-top: 5px" width="16"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">Beds: <strong></br><div id="numberBeds"  style="width:auto;float: left">' + room.numberOfBeds  +  '</div> <img src="assets/img/bed.png" style="margin-top: 5px"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">No.: <strong></br><div id="roomNumber"  style="width:auto;float: left">' + room.roomNumber + '</div><img src="assets/img/roomkey.png" style="margin-top: 5px"></strong></div>' +
        '                                                        </div>' +
        '                                                        <div class="col-md-3">' +
        '                                                            <h5>$' + pricing  +'</h5>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <br>' +
        '                                                            Floor: <strong><span id="Floor">' + room.floor + '</span></strong>' +
        '                                                            <br>' +
        '                                                            <button id="editRoom' + room.id +'" class="btn btn-primary hotel-admin btn-outline-secondary rounded-0 mb-1" style="display:none" type="button"> Edit </button>' +
        '                                                            <button id="reserveRoom' + room.id +'" class="btn btn-primary user btn-outline-secondary rounded-0 mb-1" style="display:' + display +  '" type="button"> Reserve  </button>' +
        '                                                        </div>' +
        '                                                    </div>' +
        '                                                </div>' +
        '                                            </div>');
        $(selectedElement).append(tr)

   /* $('#addListings').append(tr);
    $('#' + 'editRoom' +  room.id).click(function () {
        window.location.href = "addRoom.html?id=" + hotelId +"&room="+ room.id + "&edit=1";
    });
    $('#' + 'reserveRoom' +  room.id).click(function () {
        window.location.href = "hotelReservation.html?id=" + hotelId +"&room="+ room.id +arrival + departure; // + "&edit=1";
    });*/
}

function guiAriline(data, selectedElement, firstName, lastName, confirmed) {
    var sDate = new Date(data.startTime);
    var lDate = new Date(data.landTime);
    var flightData = $(
        '<h6>Booked for '+ firstName +' '+ lastName +' </h6>'+
        '<div class="card mb-1">\n' +
        '                        <div class="card-body">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-3" style="max-height: 150px">\n' +
        '                                    <img class ="card-img" style="height: 100%" src="assets/img/flight.png">\n' +
        '                                </div>\n' +
        '                                <div class="col-md-6 border-right">\n' +
        '                                    <h5 class="text-danger" id="Location-Start-Dest">' + data.start.city + ' ('+ data.start.country +') -> ' + data.finish.city + ' ('+ data.finish.country +') </h5>\n' +
        '                                    <a href="#" id="duration" class="badge badge-secondary">'+ data.duration +'h</a>\n' +
        '                                    <br>\n' +
        '                                    <div style="width: 50%;float:left;" style="width: auto">Start date:    <strong><div id="startDate"> ' + sDate + '</div></strong></div>\n' +
        '                                    <div style="width: 50%;float:right;" style="width: auto">Arrival date: <strong><div id="endDate">' + lDate + '</div></strong></div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3">\n' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                    <br>\n' +
        '                                    <h5>' + data.price + '$</h5>\n' +
        '                                    <br>\n' +
        '                                    Number of stops: <strong><span id="NumberOfStops">'+ data.numberOfStops +'</span></strong>\n<br><br>' +
        '                                    <a class="user" href="/bookflight.html?id='+ data.id +'"><button type="button" class="btn btn-success">Book</button></a>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>');
    $(selectedElement).append(flightData)
}