$(function() {

    var id = getUrlParameter("id");

    $('#addform').on('submit', function(e) {
        e.preventDefault();
        var addressName = $('input[name="address"]').val();
        var city = $('input[name="city"]').val();
        var country = $('input[name="country"]').val();
        var latitude = $('input[name="lat"]').val();
        var longitude = $('input[name="long"]').val();


        $.post({
            url: '/api/airline/' + id + '/addLocation',
            data: JSON.stringify({addressName: addressName, country: country, city: city, latitude: latitude, longitude:longitude}),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function(article) {
                window.location.replace("airline.html?id="+id);
            },
            error: function(article) {
                //$('#error').css("visibility", "visible");
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

