$(document).ready(function () {

    var pId = getUrlParameter('id');
    $.get({
        url : '/api/airline/findById=' + pId,
        success : function(airline) {
            if (airline != null) {
                $("#nameOfCompany").text(airline.name);
                $("#Address").text(airline.address);
                $("#Description").text(airline.description);
            }
        }
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