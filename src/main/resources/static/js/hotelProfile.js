var longitude, latitude, description, name, address;

$(document).ready(function () {


    var pId = getUrlParameter('id');
    $.get({
        url: '/api/hotel/findById=' + pId,
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (hotel) {
            if (hotel != null) {

                address = hotel.address.addressName + ', ' + hotel.address.city;
                longitude = hotel.address.longitude;
                latitude = hotel.address.latitude;
                name = hotel.name;
                description = hotel.description;
                $("#nameOfCompany").text(name);
                $("#Address").text(address);
                $("#Description").text(description);
                ymaps.ready(makeMap);

            }
        }
    });


    $.get({
        url: '/api/rooms/findAll?page=' + getUrlParameter('page') + '&pagelimit=10',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null && data.numberOfElements > 0) {
                for (var i = 0; i < data.numberOfElements; i++) {
                    //for ( var us in data.content) {
                    addRoom(data.content[i]);
                }
                setPagingButtons(data.totalPages, data.totalElements);
            }
        }
    });


});

function addRoom(room) {




    name = "";
    if(room.name != null)
        name = room.name;
    else
    {
        name = room.roomType.name + ' ' + room.roomNumber;
    }
    var tr = $(
        '<div class="card mb-1">' +
        '                                                <div class="card-body">' +
        '                                                    <div class="row">' +
        '                                                        <div class="col-md-3">' +
        '                                                            <img class="card-img" src="assets/img/room.png">' +
        '                                                        </div>' +
        '                                                        <div class="col-md-6 border-right">' +
        '                                                            <h5 class="text-danger" id="name">' +name +    '</h5>' +
        '                                                            <div style="max-width:100%" class="badge badge-secondary" href="#">Room Type:<span id="RoomType">' + room.roomType.name +   '</span></div>' +
        '                                                            <br>' +
        '                                                            <div style="height: 33%;width:25%;float:left;">People:<strong></br><div id="numberPeople" style="width:auto;float: left">' + room.numberOfPeople + '</div><img src="assets/img/people.png" style="margin-top: 5px"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">Rooms: <strong></br><div id="numberRooms"  style="width:auto;float: left">' + room.numberOfRooms + '</div><img height="16" src="assets/img/room.png" style="margin-top: 5px" width="16"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">Beds: <strong></br><div id="numberBeds"  style="width:auto;float: left">' + room.numberOfBeds  +  '</div> <img src="assets/img/bed.png" style="margin-top: 5px"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">No.: <strong></br><div id="roomNumber"  style="width:auto;float: left">' + room.roomNumber + '</div><img src="assets/img/roomkey.png" style="margin-top: 5px"></strong></div>' +
        '                                                        </div>' +
        '                                                        <div class="col-md-3">' +
        '                                                            <h5>$' + "DUMMY PRICE"   +'</h5>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <br>' +
        '                                                            Floor: <strong><span id="Floor">' + room.floor + '</span></strong>' +
        '                                                            <br>' +
        '                                                            <button class="btn btn-primary admin-hotel btn-outline-secondary rounded-0 mb-1" type="button"> Edit </button>' +
        '                                                            <button class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" type="button"> Reserve  </button>' +
        '                                                        </div>' +
        '                                                    </div>' +
        '                                                </div>' +
        '                                            </div>');
    $('#addListings').append(tr);


}


function setPagingButtons(MaxPages, MaxElements) {
    var previous = (parseInt(getUrlParameter('page')) - 1);
    var next = (parseInt(getUrlParameter('page')) + 1);
    var previousElement = $('#previous');
    var nextElement = $('#next');

    $('#TotalPages').html("Page " + (parseInt(getUrlParameter('page')) + 1) + "/" + (MaxPages) +" ");
    $('#TotalListings').html("Found " + MaxElements + " rooms");


    var pId = getUrlParameter('id');

    var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id=' + pId + '&page=' + next;
    nextElement.attr("href", adr);
    adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id=' + pId + '&page=' + previous;
    previousElement.attr("href", adr);

    if (previous < 0)
        $('#previous').attr('href', "#");
    if (next >= MaxPages)
        $('#next').attr('href', "#");
}


function makeMap() {
    var myMap = new ymaps.Map('map', {
            center: [latitude, longitude],
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

function makeStars(Rating) {
    var FullStars = Rating.toFixed(0);
    for (var i = 1; i < FullStars; i++) {

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
