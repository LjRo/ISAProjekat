var quickTickets;
var longitude, latitude, description, name, address;

$(function() {

    var id = getUrlParameter("id")
    $.get({
        url : '/api/airline/' + id + '/profile',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                fillData(data);
                address = data.address.addressName + ', ' + data.address.city;
                longitude = data.address.longitude;
                latitude = data.address.latitude;
                name = data.name;
                description = data.description;
                ymaps.ready(makeMap);
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

    $.get({
        url : '/api/airline/' + id + '/flights',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                for ( var us in data) {
                    fillFlight(data[us]);
                }
            } else {

            }
        }
    });

    $.get({
        url : '/api/airline/' + id + '/quickFlights',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                quickTickets= data;
                for ( var us in data) {
                    fillQuickFlight(data[us],us);
                }
            } else {

            }
        }
    });


});


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


function fillData(data) {
    $('#nameDisplay').text(data.name);
    $('#address').html("<span class=\"float-left\"><strong>Address:</strong></span>" + data.address.addressName + ', ' + data.address.city + ', ' + data.address.country);
    $('#desc').text(data.description);
    $('#pricing').text(data.pricing);
    $('#addFlight').attr("href","addFlight.html?id="+data.id);
    $('#editAirline').attr("href","editAirline.html?id="+data.id);
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

function fillFlight(data) {
    var table = $('#flights').DataTable();

    var sDate = new Date(data.startTime);
    var lDate = new Date(data.landTime);
    var tr = $('<tr></tr>');
    var flightData = $('<td><div class="card mb-1">\n' +
        '                        <div class="card-body">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-3">\n' +
        '                                    <img class ="card-img" src="assets/img/flight.png">\n' +
        '                                </div>\n' +
        '                                <div class="col-md-6 border-right">\n' +
        '                                    <h5 class="text-danger" id="Location-Start-Dest">' + data.start.city + ' ('+ data.start.country +') -> ' + data.finish.city + ' ('+ data.finish.country +') </h5>\n' +
        '                                    <a href="#" id="duration" class="badge badge-secondary">'+ data.duration +'h</a>\n' +
        '                                    <br>\n' +
        '                                    <div style="width: 50%;float:left;" style="width: auto">Start date:    <strong><div id="startDate"> ' + sDate + '</div></strong></div>\n' +
        '                                    <div style="width: 50%;float:right;" style="width: auto">Arrival date: <strong><div id="endDate">' + lDate + '</div></strong></div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3">\n' +
        '                                    <h5>' + data.price + '$</h5>\n' +
        '                                    <br>\n' +
        '                                    Number of stops: <strong><span id="NumberOfStops">'+ data.numberOfStops +'</span></strong>\n<br><br>' +
        '                                    <a href="/bookflight.html?id='+ data.id +'"><button type="button" class="btn btn-success">Book</button></a>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div></td>');
    var bookBtn = "";
    tr.append(flightData).append(bookBtn);
    table.row.add(tr).draw();
}

function fillQuickFlight(data,us) {
    var table = $('#quickFlights').DataTable();

    var sDate = new Date(data.startTime);
    var lDate = new Date(data.landTime);
    var tr = $('<tr></tr>');
    var flightData = $('<td><div class="card mb-1">\n' +
        '                        <div class="card-body">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-3">\n' +
        '                                    <img class ="card-img" src="assets/img/flight.png">\n' +
        '                                </div>\n' +
        '                                <div class="col-md-6 border-right">\n' +
        '                                    <h5 class="text-danger" id="Location-Start-Dest">' + data.start.city + ' ('+ data.start.country +') -> ' + data.finish.city + ' ('+ data.finish.country +') </h5>\n' +
        '                                    <a href="#" id="duration" class="badge badge-secondary">'+ data.duration +'h</a>\n' +
        '                                    <a href="#" id="duration" class="badge badge-secondary">'+'Row: '+ (data.c_row+1) + ', Column: '+ (data.c_column+1) +'</a>\n' +
        '                                    <br>\n' +
        '                                    <div style="width: 50%;float:left;" style="width: auto">Start date:    <strong><div id="startDate"> ' + sDate + '</div></strong></div>\n' +
        '                                    <div style="width: 50%;float:right;" style="width: auto">Arrival date: <strong><div id="endDate">' + lDate + '</div></strong></div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3">\n' +
        '                                    <h5><s>' + data.price + '$</s></h5>\n' +
        '                                    <h5 style="color:red">' + data.price*0.95 + '$</h5>\n' +
        '                                    <br>\n' +
        '                                    Number of stops: <strong><span id="NumberOfStops">'+ data.numberOfStops +'</span></strong>\n<br><br>' +
        '                                    <button name="'+ us +'" type="button" class="btn btn-success quickBtn">Quick Book</button>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div></td>');
    var bookBtn = "";
    tr.append(flightData).append(bookBtn);
    table.row.add(tr).draw();

    $('.quickBtn').unbind('click').bind('click', function(event) {
        event.preventDefault();
        var id = parseInt(event.delegateTarget.name);
        var passport = $('#userPassport').val();
        if(!passport) {
            alert("Please fill out your passport id.")
        } else {
            quickTickets[id].passport = passport;
            $.ajax({
                url: "/api/flight/quickBook",
                type: 'POST',
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                data: JSON.stringify(quickTickets[id]),
                contentType: 'application/json',
                success: function (data) {
                    window.location.replace("/index.html");
                },
                error: function (data) {
                    alert("Something went wrong...");
                },
            });
        }

    });
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