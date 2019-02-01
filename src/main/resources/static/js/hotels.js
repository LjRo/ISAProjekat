$(document).ready(function () {


    var search = getUrlParameter('search');
    if(search == undefined || search == '') {
        $.get({
            url: '/api/hotel/findAll',
            success: function (data) {
                if (data != null) {
                    for (var i in data) {
                        addArticle(data[i]);
                    }
                }
            }
        });
    }
    else
    {
        var nameFilter = getUrlParameter('name');
        var location = getUrlParameter('location');
        var arrival = getUrlParameter('arrival');
        var departure  = getUrlParameter('departure');

        $('#search-name').val(nameFilter);
        $('#search-location').val(location);
        $('#checkIn').val(arrival);
        $('#checkOut').val(departure);

        $.get({
            url: '/api/hotel/findAvailable?name=' + nameFilter +'&arrival=' + arrival +'&location=' + location +'&departure=' + departure,
            success: function (data) {
                if (data != null) {
                    for (var i in data) {
                            addArticle(data[i]);
                    }
                }
            }
        });
    }

    $('#search_hotel').on('submit' , function (e) {
        e.preventDefault();

        var name =  $('#search-name').val();
        var location =  $('#search-location').val();
        var arrival =  $('#checkIn').val();
        var departure =  $('#checkOut').val();
        var start_url = window.location.href.match(/^.*\//) + 'hotels.html?name=' + name + '&location='+location+'&arrival=' + arrival + '&departure='+departure + '&search=true';
        window.location.replace(start_url);
    });

});


function addArticle(hotel) {
    var icon = "assets/img/hotel.svg";
    var arrival = getUrlParameter('arrival');
    var departure = getUrlParameter('departure');
    arrival = (arrival=="" || arrival==undefined)?"":'&arrival=' +arrival;
    departure = (departure=="" || departure==undefined)?"":'&departure=' +departure;
    var rest='';
    if(arrival != "" && departure !="")
    {
        rest = arrival + departure + '&minPrice=0&maxPrice=1000000000&people=1&rooms=0&beds=0&type=0&search=true';
    }
    var str = 'hotelprofile.html?id=' + hotel.id + '&page=0' + rest;
    $('#hotelList').append(

        '<div class="col-sm-6 col-md-5 col-lg-4 item">' +
        '<div class="box">' + '<img onclick="changeTo(\'' + str + '\')" src="' + icon + '" style="width:80px;height:80px"/>' +
        '<br>' +
        '<a href="' + str + '">' +
        '<h3 class="name"><strong>' +hotel.name +'</strong></h3>' +
        '</a>' +
        '<p class="description">Address: <span style = "color:black">'+ hotel.address.addressName  + ',' + hotel.address.city + '</span></p>' +
        '<p class="description">'+ hotel.description +'</p>' +
        '<a class="edit-hotel hotel-admin" href="edithotel.html?id=' + hotel.id +'&name='+ hotel.name + '"><img src="/../assets/img/edit.png" style="height:16px;width16px;"></a> ' +
        '<a id="' + hotel.id + '" class="delete-hotel" href="hotels.html"><img src="assets/img/delete.png" style="display:none;height:16px;width16px;"></a> '+
        '</div>');
}

function changeTo(to) {
    window.location.replace(to)
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
