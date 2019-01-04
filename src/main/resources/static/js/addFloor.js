$(function () {
    var id = getUrlParameter("id");
    var floorNumber= getUrlParameter("number");

    $('#saveFloor').click(function () {
        floorPlanHtml = $('#save').html();

        $.post({
            url : '/api/hotel/' + id + '/addNewFloor' ,
            data: JSON.stringify({
                hotel: {id:id},
                floorNumber:floorNumber,
                configuration: floorPlanHtml,
            }),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function () {
                window.location.href = 'manageFloors.html?id=' + id;
            },
            error: function () {
                console.log("Error");
                $('#error').text("Error in making room").fadeIn().delay(3000).fadeOut();
            },

        });



    });





});


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