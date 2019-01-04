var longitude,latitude,description,name,address, mapName;

$(document).ready(function () {
    $("#carsList").load("/carList.html");

    var pId = getUrlParameter('id');

    $('#addCarButton').append('<button style="flex:0.2" type="button" class="btn btn-primary rentacar-admin"><span class="fa fa-gear"></span> add Car</button>');
    $('#addOfficeButton').append('<button style="flex:0.2" type="button" class="btn btn-primary rentacar-admin"><span class="fa fa-gear"></span> add office</button>');



    $('#addOfficeButton>button').click(function () {
       window.location.href = window.location.href.match(/^.*\//)+ "addOffice.html?idrent=" + pId;
    });

    $('#editRentACar').click(function () {
       window.location.href =  window.location.href.match(/^.*\//)+ "editRentACar.html?id=" + pId;
    });


    $.get({
        url : '/api/rentacar/findById=' + pId,
        success : function(data) {
            if (data != null) {
                address= data.address.addressName + ', ' + data.address.city;
                longitude= data.address.longitude;
                latitude=data.address.latitude;
                name = data.name;
                description = data.description;
                $("#nameOfCompany").text(name);
                $("#Address").text(address);
                $("#Description").text(description);
                mapName = "";
                ymaps.ready(makeMap);
            }
        }
    });
    $.get({
        url : '/api/cars/findByIdAll?id='+ getUrlParameter('id') +'&page='+getUrlParameter('page')+'&pagelimit=10',
        success : function(data) {
            if (data != null && data.numberOfElements > 0) {
                for( var i = 0; i < data.numberOfElements; i++) {
                    //for ( var us in data.content) {
                    addCar(data.content[i]);
                }
                setPagingButtons(data.totalPages,data.totalElements);
            }
        }
    });

    $.get({
        url : '/api/office/findByIdAll?id='+ getUrlParameter('id') +'&page='+getUrlParameter('pageLocation')+'&pagelimit=10',
        success : function(data) {
            if (data != null && data.numberOfElements > 0) {
                for( var i = 0; i < data.numberOfElements; i++) {
                    //for ( var us in data.content) {
                    addLocation(data.content[i],i);

                }
                setPagingButtonsLocation(data.totalPages,data.totalElements);
            }
        }
    });

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


function setPagingButtons(MaxPages, MaxElements) {
    var previous = (parseInt(getUrlParameter('page'))-1);
    var next = (parseInt(getUrlParameter('page'))+1);
    var previousElement = $('#previous');
    var nextElement = $('#next');

    $('#TotalPages').html("Page " +(parseInt(getUrlParameter('page'))+1) + "/"+(MaxPages) );
    $('#TotalListings').html("Found "+ MaxElements + " cars");


    var pId = getUrlParameter('id');

    var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+next + '&pageLocation=' +  getUrlParameter('pageLocation');
    nextElement.attr("href", adr);
    adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+previous +'&pageLocation=' + getUrlParameter('pageLocation');
    previousElement.attr("href",adr);

    if (previous < 0)
        previousElement.attr('href', "");
    if (next >= MaxPages)
        nextElement.attr('href',"");
}


function setPagingButtonsLocation(MaxPages, MaxElements) {
    var previous = (parseInt(getUrlParameter('pageLocation'))-1);
    var next = (parseInt(getUrlParameter('pageLocation'))+1);
    var previousElement = $('#previousLocation');
    var nextElement = $('#nextLocation');

    $('#TotalPagesLocation').html("Page " +(parseInt(getUrlParameter('pageLocation'))+1) + "/"+(MaxPages) );
    $('#TotalListingsLocation').html("Found "+ MaxElements + " locations");


    var pId = getUrlParameter('id');

    var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+getUrlParameter('page')+'&pageLocation=' + next;
    nextElement.attr("href", adr);
    adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+getUrlParameter('page')+'&pageLocation='+previous;
    previousElement.attr("href",adr);

    if (previous < 0)
        previousElement.attr('href', "");
    if (next >= MaxPages)
        nextElement.attr('href',"");
}

function addLocation(location) {
    var div = $('#addListingsLocation');

    var tr = $('<div class="card mb-1"></div>');
    // var d1 = $('<div class="card mb-1">');
    var d2 = $(' <div class="card-body"></div>');
    var d3 = $(' <div class="row"></div>');
    var d4 = $('<div class="col-md-3"></div>');
   var d5 = $(' <img class ="card-img" src="assets/img/map.svg">');
   // var d5 = $('<div id="map'+ location.id +'" class="d-none d-md-block iphone-mockup">\n' +
   //     '\n' +
   //     '</div>');

    var d7 = $(' <div class="col-md-6 border-right"></div>');
    var d8 = $(' <h5 class="text-danger" id="OfficeName'+location.id+'">'+ location.name + '</h5>');
    var d9 = $('<div style=" width: 33%;float:left;" style="width: auto">Address:<strong><div id="addressName'+location.id+'">' + location.location.addressName + '</div></strong></div>');
    var d10 = $('  <div style="width: 33%;float:right;" style="width: auto">City: <strong><div id="city'+location.id+'">' + location.location.city +'</div></strong></div>');
    var d11 = $(' <div style="width: 33%;float:right;" style="width: auto">Country: <strong><div id="country'+location.id+'">' +  location.location.country + '</div></strong></div>');

    var d13 = $('<div class="col-md-3"></div>');
    //var d14 = $(' <h5>$'+ car.dailyPrice +'</h5>');
    //var d15 = $('<i id="star1" class="fa fa-star"></i>');
    //var d16 = $('<i id="star2" class="fa fa-star"></i>');
    //var d17 = $('<i id="star3" class="fa fa-star"></i>');
    //var d18 = $('<i id="star4" class="fa fa-star"></i>');
    //var d19 = $('<i id="star5" class="fa fa-star"></i>');
    //var d20 = $('<br>');
    //var d21 = $('<a href=""><small>more info about car</small></a>');
    //var d22 = $('<br>');
    var d23 = $(' <button type="button" id="editButtonLocation'+ location.id +'" class="btn btn-primary admin-rent btn-outline-secondary rounded-0 mb-1">Edit</button>');


    //var d24 = $(' <button type="button" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">BUY</button>');
    // var d25 = $(' </div>'); same as d6 x4

    //Picture
    d4.append(d5);
    d3.append(d4);

    //Stats
    d7.append(d8);
    d7.append(d9);
    d7.append(d10);
    d7.append(d11);
    d3.append(d7);

    //Price, stars, more info, buttons
    /*d13.append(d14);
    d13.append(d15);
    d13.append(d16);
    d13.append(d17);
    d13.append(d18);
    d13.append(d19);
    d13.append(d20);
    d13.append(d21);
    d13.append(d22);*/
    d13.append(d23);
   // d13.append(d24);

    d3.append(d13);

    // adding to div parent
    d2.append(d3);
    tr.append(d2);

    div.append(tr);
    var pId = getUrlParameter('id');
    $('#'+ "editButtonLocation" + location.id).click(function () {
        window.location.href = window.location.href.match(/^.*\//)+ "addOffice.html?idrent=" + pId + "&edit=true&id=" + location.id;
    });



}


function addCar(car) {
    var div = $('#addListings');

    var tr = $('<div class="card mb-1"></div>');
   // var d1 = $('<div class="card mb-1">');
    var d2 = $(' <div class="card-body"></div>');
    var d3 = $(' <div class="row"></div>');
    var d4 = $('<div class="col-md-3"></div>');
    var d5 = $(' <img class ="card-img" src="assets/img/carlisting.svg">');

    var d7 = $(' <div class="col-md-6 border-right"></div>');
    var d8 = $(' <h5 class="text-danger" id="Mark-Model-Name">'+ car.mark+" " +  car.model + " " + car.name + '</h5>');
    var d9 = $('<div style=" width: 33%;float:left;" style="width: auto">People:<strong><div id="numberPeople">' + car.maxPassengers + '</div></strong></div>');
    var d10 = $('  <div style="width: 33%;float:right;" style="width: auto">Doors: <strong><div id="numberDoors">' + car.numberOfDoors +'</div></strong></div>');
    var d11 = $(' <div style="width: 33%;float:right;" style="width: auto">Bags: <strong><div id="numberBags">' +  car.numberOfBags + '</div></strong></div>');

    var d13 = $('<div class="col-md-3"></div>');
    var d14 = $(' <h5>$'+ car.dailyPrice +'</h5>');
    var d15 = $('<i id="star1" class="fa fa-star"></i>');
    var d16 = $('<i id="star2" class="fa fa-star"></i>');
    var d17 = $('<i id="star3" class="fa fa-star"></i>');
    var d18 = $('<i id="star4" class="fa fa-star"></i>');
    var d19 = $('<i id="star5" class="fa fa-star"></i>');
    var d20 = $('<br>');
    var d21 = $('<a href=""><small>more info about car</small></a>');
    var d22 = $('<br>');
    var d23 = $(' <button type="button" class="btn btn-primary admin-rent btn-outline-secondary rounded-0 mb-1">Edit</button>');
    var d24 = $(' <button type="button" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">BUY</button>');
   // var d25 = $(' </div>'); same as d6 x4

    //Picture
    d4.append(d5);
    d3.append(d4);

    //Stats
    d7.append(d8);
    d7.append(d9);
    d7.append(d10);
    d7.append(d11);
    d3.append(d7);

    //Price, stars, more info, buttons
    d13.append(d14);
    d13.append(d15);
    d13.append(d16);
    d13.append(d17);
    d13.append(d18);
    d13.append(d19);
    d13.append(d20);
    d13.append(d21);
    d13.append(d22);
    d13.append(d23);
    d13.append(d24);

    d3.append(d13);

    // adding to div parent
    d2.append(d3);
    tr.append(d2);

    div.append(tr);
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