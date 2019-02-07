$(document).ready(function () {

    var pId = getUrlParameter('idrent');


    var start = getUrlParameter('start');
    var end = getUrlParameter('end');

    var car = JSON.parse(localStorage.getItem('car'));

    start = (start==undefined||start=='')?'':start;
    end = (end==undefined||end=='')?'':end;

    if(start != '' && end != '')
    {
        $('#checkIn').val(start);
        $('#checkOut').val(end);
        var priceTotal = daysBetween(start,end)* car.dailyPrice * 0.95;
        $('#price').html("Total price for renting a car :" + priceTotal+ "$");
        $('#disc').html("Price may differ (decrease) if you reserve a hotel room, check the final price when finishing reservation");
        $('#info').html("Car :" + car.mark+" " +  car.model + " " + car.name );
        $('#info2').html("Type :" + car.type.name);
    }


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
                    $('#confirmReservation').on('click', function(event) {
                        event.preventDefault();
                        var airlineReservationId =  $('select[name="selectAirlineReservations"]').val();

                        if(airlineReservationId =="" || airlineReservationId == undefined)
                        {
                            $('#error').html("Please select flight reservation to be able to confirm").fadeIn().delay(3000).fadeOut();
                        }
                        else
                        {

                            $.post({
                                url:  "api/cars/" + pId + "/" + airlineReservationId + "/reserve",
                                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                                data: JSON.stringify(
                                    {

                                            airlineReservation : {
                                                id : airlineReservationId
                                            },
                                            user: null,
                                            startDate : start,
                                            endDate : end,
                                            startLocation: {
                                                id: getUrlParameter('locStart')
                                            },
                                            endLocation: {
                                                id: getUrlParameter('locEnd')
                                            },
                                            numberOfPeople:  getUrlParameter('passengers'),
                                            rentedCar : car,
                                            fastReservation: false

                                    }),
                                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                                contentType: 'application/json',
                                success: function () {
                                        $('#success').html("Succesfuly added this car to your reservation").fadeIn().delay(3000).fadeOut();
                                        $('#confirmReservation').attr('disable','disable');
                                        setTimeout(function () {
                                            window.location.replace("index.html");
                                        },3000);
                                },
                                error : function () {

                                        $('#error').html("Alredy reserved").fadeIn().delay(3000).fadeOut();

                                }
                            });
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

function fillInfo(data){
    $('#listReservations').append('<option value= "' + data.id + '"> For order id:' + data.id + '</option>')
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