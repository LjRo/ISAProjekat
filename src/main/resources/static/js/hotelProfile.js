$(document).ready(function () {

    var pId = getUrlParameter('id');
    $.get({
        url : '/api/hotel/findById=' + pId,
        success : function(hotel) {
            if (hotel != null) {
                $("#nameOfCompany").text(hotel.name);
                $("#Address").text(hotel.address);
                $("#Description").text(hotel.description);

               /*
                var ratings = hotel.ratings;
                var rating = 0;
                ratings.forEach(function(element) {
                    rating += element.userRating;
                });
                rating = rating/ ratings.size();
                */
            }
        }
    });

});


function makeStars(Rating) {
    var FullStars = Rating.toFixed(0);
    for (var i=1;i<FullStars; i++)
    {

    }

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
