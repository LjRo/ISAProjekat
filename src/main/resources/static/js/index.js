$(document).ready(function () {

    checkUserType();

    $.get({
        url: 'api/flight/allReservations',
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