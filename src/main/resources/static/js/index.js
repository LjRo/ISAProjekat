$(document).ready(function () {

    checkUserType();



    $.get({
        url: 'api/flight/AllInvites',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {

            if (data != null) {
                if(data.length > 0)
                {
                    data.forEach(function (entry) {
                        guiInvites(entry.flight, "#airlineListings", entry.id) //fillInfo(entry);
                    });
                    $('#airlineListings').append("<h3> Your are invited on :!</h3>")
                }
                else
                {
                    $('#airlineListings').append('<h3> You will see flights on which you are invited here!</h3>')
                }
            }
        },
        error:function (message) {
            //alert(message.status);
        }

    });


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

        var sel = '#orderListings';
        $(sel).html('');

        var id = $('select[name="selectAirlineReservations"]').val();

        $.get({
            url: 'api/flight/'+ id +'/order',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (data) {

                $(sel).append('<h6 style="display: inline"> Order id: ' + data.id+ ' </h6>');


                $(sel).append('<button type="button" id="CancelOrder' + data.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Cancel order</button>');
                if (!data.finished)
                    $(sel).append('<button type="button" id="ConfirmOrder' + data.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Confirm order</button>')

                $('#CancelOrder'+data.id).click(function () {

                    $.get({
                        url: 'api/flight/cancelOrder?id=' + data.id,
                        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                        success: function (data) {
                            if (data == true) {
                                window.location.reload(true);
                            }else {
                                alert("Failed to delete order");
                            }
                        },
                        error: function (e) {

                        }
                    });

                });
                if (!data.finished){
                    $('#ConfirmOrder' + data.id).click(function () {
                        $.post({
                            url: 'api/order/'+data.id+'/confirm',
                            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                            success: function (data) {
                                alert('Thank you for the purchase')
                                setTimeout(function () {
                                    window.location.reload();
                                }, 2000);
                            },
                            error: function (e) {

                            }
                        });
                    });
                }


                if (data.reservations != undefined) {

                    data.reservations.forEach(function (entry) {
                        guiAirline(entry.flight,sel, entry.name, entry.lastName, entry.confirmed,entry.id);
                    });
                }
                if (data.rentReservation != undefined){
                    guiRent(data.rentReservation, sel, data.id, data.rentReservation.id);
                }
                if (data.reservationHotel != undefined){
                    guiHotel(data.reservationHotel, sel, data.id, data.reservationHotel.id);
                }

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

function guiRent(rentReservation, selectedElement, orderId,qinfo) {
        var car = rentReservation.rentedCar;
        var pricing =  rentReservation.price;
        var tr = $(
            '<div class="card mb-1">' +
            '                                                <div class="card-body">' +
            '                                                    <div class="row">' +
            '                                                        <div class="col-md-3" style="max-height:150px">' +
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
            '                                                            <div id="addStarsRR'+ qinfo+'"></div>                           ' +
            '                                                            <button type="button" id="cancelRent' + rentReservation.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Cancel</button>' +
            '                                                        </div>' +
            '                                                    </div>' +
            '                                                </div>' +
            '                                            </div>');
        //$('#addListings').append(tr);
         $(selectedElement).append(tr)


    $.get({
        url: '/rating/check?id='+qinfo+'&type=1',
        success: function (rating) {
            generateStars(rating,"#addStarsRR" + qinfo,qinfo, 1);
        },
        error : function (e) {

        }
    });

        $('#cancelRent'+ rentReservation.id).click(function () {
            $.get({
                url: 'api/flight/cancelRent?id=' + orderId,
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                success: function (data) {
                    if (data == true){
                        alert("Sucess");
                        window.location.reload(true);
                    }else {
                        alert("Can't cancel. already late");
                    }

                },
                error: function (e) {
                    alert('Failed to carry out operation');
                }
            });
        });
}

var guiHotel = function (hotel, selectedElement, orderId,qinfo) {
    var room = hotel.room;
    var price = hotel.price;
    var cena = price;

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
        '                                                            <div id="addStarsR'+ qinfo+'"></div>                           ' +
        '                                                            Floor: <strong><span id="Floor">' + room.floor + '</span></strong>' +
        '                                                            <br>' +
        '                                                            <button type="button" id="cancelRoom' + room.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Cancel</button>' +'                                                        </div>' +
        '                                                    </div>' +
        '                                                </div>' +
        '                                            </div>');
        $(selectedElement).append(tr)

    $.get({
        url: '/rating/check?id='+qinfo+'&type=3',
        success: function (rating) {
            generateStars(rating,"#addStarsR" +qinfo,qinfo, 3);
        },
        error : function (e) {

        }
    });


        $('#cancelRoom'+room.id).click(function () {

            $.get({
                url: 'api/flight/cancelHotel?id='+ orderId,
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                success: function (data) {
                    window.location.reload(true);
                },
                error: function (e) {
                    alert('Failed to carry out operation');
                }
            });

        });

}




function guiAirline(data, selectedElement, firstName, lastName, confirmed,qinfo) {
    var sDate = new Date(data.startTime);
    var lDate = new Date(data.landTime);
    var nesto = '';
    if (confirmed && false){
        nesto =   '                                                            ' +
            '<button type="button" id="cancelAir' + data.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Revoke invite</button>';
    }else {
        nesto = '';
    }


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
        '                                                            <div id="addStarsA'+ qinfo+'"></div>                           ' +
        '                                    <h5>' + data.price + '$</h5>\n' +
        '                                    <br>\n' +
        '                                    Number of stops: <strong><span id="NumberOfStops">'+ data.numberOfStops +'</span></strong>\n<br><br>' +
        nesto +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>');
    $(selectedElement).append(flightData)

    $.get({
        url: '/rating/check?id='+qinfo+'&type=2',
        success: function (rating) {
            generateStars(rating,"#addStarsA" + qinfo,qinfo, 2);
        },
        error : function (e) {

        }
    });


    if (!confirmed && false){
        $("#cancelAir"+data.id).click(function () {

            $.get({
                url: 'api/flight/decline?id=' + data.id,
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                success: function (data) {
                    window.location.reload(true);
                },
                error: function (e) {
                    alert('Failed to carry out operation');
                }
            });


        });
    }


}


function guiInvites(data, selectedElement, qinfo) {
    var sDate = new Date(data.startTime);
    var lDate = new Date(data.landTime);

    var flightData = $(
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
        '                                                            <div id="addStarsI'+ qinfo+'"></div>                           ' +
        '                                    <h5>' + data.price + '$</h5>\n' +
        '                                    <br>\n' +
        '                                    Number of stops: <strong><span id="NumberOfStops">'+ data.numberOfStops +'</span></strong>\n<br><br>' +
        '<button type="button" id="accept' + qinfo +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Accept invite</button>' +
        '<button type="button" id="decline' + qinfo +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Decline invite</button>'+
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>');
    $(selectedElement).append(flightData)



    $.get({
        url: '/rating/check?id='+qinfo+'&type=2',
        success: function (rating) {
            generateStars(rating,"#addStarsI" + qinfo,qinfo, 2);
        },
        error : function (e) {

        }
    });


        $("#decline"+qinfo).click(function () {

            $.get({
                url: 'api/flight/decline?id=' + qinfo,
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                success: function (data) {
                    alert("Done!")
                    setTimeout(function () {
                        window.location.reload(true);
                    },2000);


                },
                error: function (e) {
                    alert('Failed to carry out operation');
                }
            });


        });

    $("#accept"+qinfo).click(function () {

        $.get({
            url: 'api/flight/confirm?id=' +qinfo,
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (data) {

                alert("Done!")
                setTimeout(function () {
                    window.location.reload(true);
                },2000);
            },
            error: function (e) {
                alert('Failed to carry out operation');
            }
        });


    });



}
//1- cars , 2 - flight , 3- rooms <- types, id for those reservations
function generateStars(average, selector, id, type){
    //  $('#addStars' + id).append(res);
    var res;

    for (var i = 0; i < 5; i++){
        if (i > average){
            if (average >= i/2){
                res =  $('<a href="#" id="star'+id+'Link' + i +'"><i id="star'+id+'Q'+ i +' " class="fa fa-star-half-o"></i></a>');
            }else {
                res =  $('<a href="#" id="star'+id+'Link' + i +'"><i id="star'+id+'Q'+ i +' " class="fa fa-star-o"></i></a>');
            }
        } else {
            if (i == average){
                res =  $('<a href="#" id="star'+id+'Link' + i +'"><i id="star'+id+'Q'+ i +' " class="fa fa-star-o"></i></a>');
            }else
                res =  $('<a href="#" id="star'+id+'Link' + i +'"><i id="star'+id+'Q'+ i +' " class="fa fa-star"></i></a>');
        }


        $(selector).append(res);
        $('#star'+id+'Link'+i).click(function () {


        if (type == 1){
            $.post({
                url: window.location.href.match(/^.*\//) + "rating/add",
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                data: JSON.stringify({
                    userRating: i,
                    user: null,
                    type: type,
                    flightReservation: null,
                    hotelReservation : null,
                    rentReservation: {
                        id : id
                    }
                }),
                contentType: 'application/json',
                success: function () {

                    alert("Voted!");
                },
                error: function () {
                    alert("You don't meet the requirements for rating");
                },

            });
        }else if (type == 2){
            $.post({
                url: window.location.href.match(/^.*\//) + "rating/add",
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                data: JSON.stringify({
                    userRating: i,
                    user: null,
                    type: type,
                    flightReservation: {
                        id: id
                    },
                    hotelReservation : null,
                    rentReservation: null
                }),
                contentType: 'application/json',
                success: function () {

                    alert("Voted!");
                },
                error: function () {
                    alert("You don't meet the requirements for rating");
                },

            });
        }else if (type == 3){
            $.post({
                url: window.location.href.match(/^.*\//) + "rating/add",
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                data: JSON.stringify({
                    userRating: i,
                    user: null,
                    type: type,
                    flightReservation: null,
                    hotelReservation : {
                        id : id
                    },
                    rentReservation: null
                }),
                contentType: 'application/json',
                success: function () {
                    alert("Voted!");
                },
                error: function () {
                    alert("You don't meet the requirements for rating");
                },

            });
        }



        });

    }
    res = $('<br>');
    $(selector).append(res);
}