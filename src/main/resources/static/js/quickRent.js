$(document).ready(function () {

    $.get({
        url: '/api/flight/reservationRent',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                if(data.length > 0)
                {
                    data.forEach(function (entry) {
                        fillInfo(entry);
                    });
                            $.get({
                                url : 'api/cars/'+ getUrlParameter('id') +'/quick',
                                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                                success : function(data) {
                                    if (data != null) {
                                        for ( var i in data) {
                                            addRoom(data[i]);
                                        }
                                    }
                                },
                                error: function () {
                                    setTimeout(function () {
                                        window.location.replace('/index.html');
                                    },10000);
                                }
                            });
                }
                else
                {
                    $('#confirmReservation').remove();
                    $('#error2').html("Please make a airline reservation first to reserve a hotel").fadeIn().delay(10000).fadeOut();
                    setTimeout(function () {
                        // window.location.replace("hotelprofile.html?id=" + pId +"&page=0");
                    },10000);
                }
            }
        },
        error:function (message) {
            alert(message.status);
        }

    });




});
//function fillAirlineReservations(data) {
//    $("#listReservations").append('<option value= "' + data.id + '"> For ' + data.flight.finish.city + ' at ' + data.flight.startTime.substring(0,10) + '</option>');
//}

function fillInfo(data){
    $('#listReservations').append('<option value= "' + data.id + '"> For order id:' + data.id + '</option>')
}


function addRoom(rentReservation) {
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
    $('#addListings').append(tr);
    $('#' + 'buyButtonCar' +  rentReservation.id).click(function () {

        var airlineReservationId =  $('select[name="selectAirlineReservations"]').val();

        if(airlineReservationId =="" || airlineReservationId == undefined)
        {
            $('#error').html("Please select flight reservation to be able to confirm").fadeIn().delay(3000).fadeOut();
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
    });

}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1].replace(/\+/g, ' '));
        }
    }
};

function treatAsUTC(date) {
    var result = new Date(date);
    result.setMinutes(result.getMinutes() - result.getTimezoneOffset());
    return result;
}

function daysBetween(startDate, endDate) {
    var millisecondsPerDay = 24 * 60 * 60 * 1000;
    return (treatAsUTC(endDate) - treatAsUTC(startDate)) / millisecondsPerDay;
}