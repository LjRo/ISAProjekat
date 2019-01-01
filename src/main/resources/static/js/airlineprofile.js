$(function() {

    var id = getUrlParameter("id")
    $.get({
        url : '/api/airline/' + id + '/profile',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                fillData(data);
            } else {

            }
        }
    });

    $.get({
        url : '/api/airline/' + id + '/destinations',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                for ( var us in data) {
                    fillDest(data[us]);
                }
            } else {

            }
        }
    });


});

function fillData(data) {
    $('#nameDisplay').text(data.name);
    $('#address').html("<span class=\"float-left\"><strong>Address:</strong></span>" + data.address);
    $('#desc').text(data.description);
    $('#pricing').text(data.pricing);
}


function fillDest(data) {
    var table = $('#destinations').DataTable();

    var tr = $('<tr></tr>');
    var country = $('<td>' + data.country + '</td>');
    var city = $('<td>' + data.city + '</td>');
    var address = $('<td>' + data.addressName + '</td>');

    tr.append(country).append(city).append(address);
    table.row.add(tr).draw();
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