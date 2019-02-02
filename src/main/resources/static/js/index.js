$(document).ready(function () {


    $.get({
        url: 'api/flight/reservations"',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                if(data.length > 0)
                {
                    data.forEach(function (entry) {
                        fillAirlineReservations(entry);
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




});

function fillAirlineReservations(data) {
    $("#listReservations").append('<option value= "' + data.id + '"> For ' + data.flight.finish.city + ' at ' + data.flight.startTime.substring(0,10) + '</option>');
}