$(function () {

    var id = getUrlParameter("id");

    $('#error').hide();

    $.get({
        url: '/api/hotel/' + id + '/roomTypes',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                for (var us in data) {
                    fillRoomType(data[us]);
                }
            } else {

            }
        }
    });

    $('addForm').on('submit',function () {

    });

});


function fillRoomType(data) {
    $("#roomType").append('<option value= "' + data.id + '">' + data.name + '</option>');
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
