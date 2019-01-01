$(document).ready(function () {
    var longitude,latitude,description,name,address;

    var pId = getUrlParameter('id');
    $.get({
        url : '/api/hotel/findById=' + pId,
        success : function(hotel) {
            if (hotel != null) {

                address= hotel.address.addressName;
                longitude= hotel.address.longitude;
                latitude=hotel.address.latitude;
                name = hotel.name;
                description = hotel.description;
                $("#nameOfCompany").text(name);
                $("#Address").text(address);
                $("#Description").text(description);
                ymaps.ready(makeMap);

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



    function makeMap(){
        var myMap = new ymaps.Map('map', {
                center: [latitude, longitude ],
                zoom: 9
            }, {
                searchControlProvider: 'yandex#search'
            }),
            myPlacemark = new ymaps.Placemark(myMap.getCenter(), {
                hintContent: name,
                balloonContent: '<strong>' + name + '</strong>' + '<br>' + address + '<br>' + description
            }, {
                iconLayout: 'default#image',
                iconImageHref: 'https://image.flaticon.com/icons/svg/252/252025.svg',
                iconImageSize: [30, 42],
                iconImageOffset: [-5, -38]
            });
        myMap.controls.remove('fullscreenControl');
        myMap.geoObjects
            .add(myPlacemark);
    }



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
