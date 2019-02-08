var longitude,latitude,description,name,address, mapName;

$(document).ready(function () {


    var pId = getUrlParameter('id');

    $('#addCarButton').append('<button style="flex:0.2" type="button" class="btn btn-primary admin-rentacar"><span class="fa fa-gear"></span> add Car</button>');
    $('#addOfficeButton').append('<button style="flex:0.2" type="button" class="btn btn-primary admin-rentacar"><span class="fa fa-gear"></span> add Office</button>');



    $('#addOfficeButton>button').click(function () {
       window.location.href = window.location.href.match(/^.*\//)+ "addOffice.html?idrent=" + pId;
    });

    $('#editRentACar').click(function () {
       window.location.href =  window.location.href.match(/^.*\//)+ "editRentACar.html?id=" + pId;
    });


    $('#quickReservations').click(function () {
        window.location.href =  window.location.href.match(/^.*\//)+ "quickRent.html?id=" + pId;
    });

    $('#addCarButton>button').click(function () {
        window.location.href = window.location.href.match(/^.*\//)+ "addCar.html?idrent=" + pId;
    });

    $.get({
        url: '/rating/check?id='+pId+'&type=4',
        success: function (rating) {
            generateStars(rating,"#addStarsRent");
        },
        error : function (e) {

        }
    });


    $.get({
        url : 'api/office/all?id='+ getUrlParameter('id'),
        success : function(data) {
            data.forEach(function (element) {
                fillLocation(element.location,'#locationOptionStart');
                fillLocation(element.location,'#locationOptionEnd');
            });


            $.get({
                url : 'api/cartypes',
                success : function(data) {
                    data.forEach(function (element) {
                        fillTypes(element);

                    });
                        // FILL Data

                        var exists = getUrlParameter("carTypeId");

                        if ( exists != undefined){

                            setFilter();

                            var locStart = $("#locationOptionStart option:selected").val();
                            var locEnd = $("#locationOptionEnd option:selected").val();

                            var minPrice = $("#search-minPrice").val();
                            var maxPrice = $("#search-maxPrice").val();

                            minPrice = minPrice == '' ? 0 : minPrice;
                            maxPrice = maxPrice == '' ? 0 : maxPrice;

                            var pass = $("#search-minPassengers").val();


                            var nesto = $("#search-options option:selected").val();
                            var start = new Date($("#checkIn").val());
                            var end = new Date($("#checkOut").val());

                            start = start.toISOString().split('T')[0];
                            end = end.toISOString().split('T')[0];
                            $.get({
                                url : window.location.href.match(/^.*\//) + 'api/cars/'+ getUrlParameter('id') +'/availablePrice?page='+getUrlParameter('page')+"&carTypeId="+nesto+"&start="+start+"&end="+ end+ "&passengers="+ pass+"&minPrice=" + minPrice + "&maxPrice=" + maxPrice,
                                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
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

                        }else {

                            $.get({
                                url : '/api/cars/findByIdAll?id='+ getUrlParameter('id') +'&page='+getUrlParameter('page')+'&pagelimit=10',
                                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
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
                        }

                        $.get({
                            url : '/api/office/findByIdAll?id='+ getUrlParameter('id') +'&page='+getUrlParameter('pageLocation')+'&pagelimit=10',
                            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
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


                }
            });


        }
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

    //$('#srcSubmit').on('click',function (e) {
    $('#formSearch').on('submit', function(e) {

        e.preventDefault();
        var locStart = $("#locationOptionStart option:selected").val();
        var locEnd = $("#locationOptionEnd option:selected").val();

        var minPrice = $("#search-minPrice").val()  ;
        var maxPrice = $("#search-maxPrice").val() ;

        minPrice = minPrice == '' ? 0 : minPrice;
        maxPrice = maxPrice == '' ? 0 : maxPrice;

        var pass = $("#search-minPassengers").val();


        var nesto = $("#carOptions option:selected").val();
        var start = new Date($("#checkIn").val());
        var end = new Date($("#checkOut").val());

        start = start.toISOString().split('T')[0];
        end = end.toISOString().split('T')[0];

        //window.location.href = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page=0' + '&pageLocation=' +  getUrlParameter('pageLocation')
       // + "&carTypeId ="+ nesto + "&start=" + start + "&end=" + end + "&minPrice=" + minPrice + "&maxPrice="+ maxPrice + "&locStart=" + locStart + "&locEnd=" + locEnd;

        window.location.replace(window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page=0' + '&pageLocation=' +  getUrlParameter('pageLocation')
            + "&carTypeId="+ nesto + "&start=" + start + "&end=" + end + "&minPrice=" + minPrice + "&maxPrice="+ maxPrice + "&locStart=" + locStart + "&locEnd=" + locEnd + "&passengers=" + pass);
        //  window.location.reload(true);

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

    if (getUrlParameter("carTypeId") != undefined){

        var locStart = $("#locationOptionStart option:selected").val();
        var locEnd = $("#locationOptionEnd option:selected").val();

        var minPrice = $("#search-minPrice").val()  ;
        var maxPrice = $("#search-maxPrice").val() ;

        minPrice = minPrice == '' ? 0 : minPrice;
        maxPrice = maxPrice == '' ? 0 : maxPrice;

        var pass = $("#search-minPassengers").val();


        var nesto = $("#search-options option:selected").val();
        var start = new Date($("#checkIn").val());
        var end = new Date($("#checkOut").val());

        start = start.toISOString().split('T')[0];
        end = end.toISOString().split('T')[0];


        var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page=' + next + '&pageLocation=' +  getUrlParameter('pageLocation')
            + "&carTypeId="+ nesto + "&start=" + start + "&end=" + end + "&minPrice=" + minPrice + "&maxPrice="+ maxPrice + "&locStart=" + locStart + "&locEnd=" + locEnd + "&passengers=" + pass;
        nextElement.attr("href", adr);
        adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page=' + previous + '&pageLocation=' +  getUrlParameter('pageLocation')
            + "&carTypeId="+ nesto + "&start=" + start + "&end=" + end + "&minPrice=" + minPrice + "&maxPrice="+ maxPrice + "&locStart=" + locStart + "&locEnd=" + locEnd + "&passengers=" + pass;
        previousElement.attr("href",adr);

    }else {
        var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+next + '&pageLocation=' +  getUrlParameter('pageLocation');
        nextElement.attr("href", adr);
        adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+previous +'&pageLocation=' + getUrlParameter('pageLocation');
        previousElement.attr("href",adr);
    }
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

    if (getUrlParameter("carTypeId") != undefined){


        var locStart = $("#locationOptionStart option:selected").val();
        var locEnd = $("#locationOptionEnd option:selected").val();

        var minPrice = $("#search-minPrice").val()  ;
        var maxPrice = $("#search-maxPrice").val() ;

        minPrice = minPrice == '' ? 0 : minPrice;
        maxPrice = maxPrice == '' ? 0 : maxPrice;

        var pass = $("#search-minPassengers").val();


        var nesto = $("#search-options option:selected").val();
        var start = new Date($("#checkIn").val());
        var end = new Date($("#checkOut").val());

        start = start.toISOString().split('T')[0];
        end = end.toISOString().split('T')[0];


        var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page=' + getUrlParameter('page') + '&pageLocation=' +  next
            + "&carTypeId="+ nesto + "&start=" + start + "&end=" + end + "&minPrice=" + minPrice + "&maxPrice="+ maxPrice + "&locStart=" + locStart + "&locEnd=" + locEnd + "&passengers=" + pass;
        nextElement.attr("href", adr);
        adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page=' + getUrlParameter('page') + '&pageLocation=' +  previous
            + "&carTypeId="+ nesto + "&start=" + start + "&end=" + end + "&minPrice=" + minPrice + "&maxPrice="+ maxPrice + "&locStart=" + locStart + "&locEnd=" + locEnd + "&passengers=" + pass;
        previousElement.attr("href",adr);

    }else {
        var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+getUrlParameter('page')+'&pageLocation=' + next;
        nextElement.attr("href", adr);
        adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+getUrlParameter('page')+'&pageLocation='+previous;
        previousElement.attr("href",adr);
    }
    if (previous < 0)
        previousElement.attr('href', "");
    if (next >= MaxPages)
        nextElement.attr('href',"");
}

function addLocation(location) {
    var div = $('#addListingsLocation');

    var tr = $('<div class="card mb-1"></div>');

    var d2 = $(' <div class="card-body"></div>');
    var d3 = $(' <div class="row"></div>');
    var d4 = $('<div class="col-md-3"></div>');
    var d5 = $(' <img class ="card-img" src="assets/img/map.svg">');


    var d7 = $(' <div class="col-md-6 border-right"></div>');
    var d8 = $(' <h5 class="text-danger" id="OfficeName'+location.id+'">'+ location.name + '</h5>');
    var d9 = $('<div style=" width: 33%;float:left;" style="width: auto">Address:<strong><div id="addressName'+location.id+'">' + location.location.addressName + '</div></strong></div>');
    var d10 = $('  <div style="width: 33%;float:right;" style="width: auto">City: <strong><div id="city'+location.id+'">' + location.location.city +'</div></strong></div>');
    var d11 = $(' <div style="width: 33%;float:right;" style="width: auto">Country: <strong><div id="country'+location.id+'">' +  location.location.country + '</div></strong></div>');

    var d13 = $('<div class="col-md-3"></div>');


    var d23 = $(' <button type="button" id="editButtonLocation'+ location.id +'" class="btn btn-primary admin-rentacar btn-outline-secondary rounded-0 mb-1">Edit</button>');
    d13.append(d23);

    //Picture
    d4.append(d5);
    d3.append(d4);

    //Stats
    d7.append(d8);
    d7.append(d9);
    d7.append(d10);
    d7.append(d11);
    d3.append(d7);


    d3.append(d13);

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

    var d55 = $('<div style="max-width:100%" class="badge badge-secondary" href="#">Car Type: <span id="CarType">'+ car.type.name + '</span></div><br>');



    var d8 = $(' <h5 class="text-danger" id="Mark-Model-Name">'+ car.mark+" " +  car.model + " " + car.name + '</h5>');
    var d9 = $('<div style=" width: 33%;float:left;" style="width: auto">People:<strong><div id="numberPeople">' + car.maxPassengers + '</div></strong></div>');
    var d10 = $('  <div style="width: 33%;float:right;" style="width: auto">Doors: <strong><div id="numberDoors">' + car.numberOfDoors +'</div></strong></div>');
    var d11 = $(' <div style="width: 33%;float:right;" style="width: auto">Bags: <strong><div id="numberBags">' +  car.numberOfBags + '</div></strong></div>');

    var d13 = $('<div class="col-md-3"></div>');
    var d14;
    var q1;

    if (getUrlParameter("carTypeId") != undefined){
        d14 = $(' <h5>$'+ car.dailyPrice * daysBetween(getUrlParameter('start'),getUrlParameter('end')) *0.95 +'</h5>');
        q1 = $(' <h5 style="text-decoration: line-through">$'+ car.dailyPrice * daysBetween(getUrlParameter('start'),getUrlParameter('end')) +'</h5>');
    }else {
        d14 =  $(' <h5>$'+ car.dailyPrice * 0.95  +'</h5>');
        q1 =  $(' <h5 style="text-decoration: line-through">$'+ car.dailyPrice  +'</h5>');
    }

    var dq1 = $('<div id="addStars'+ car.id+'"></div>')
    var d15 = $('<i id="star1" class="fa fa-star"></i>');
    var d16 = $('<i id="star2" class="fa fa-star"></i>');
    var d17 = $('<i id="star3" class="fa fa-star"></i>');
    var d18 = $('<i id="star4" class="fa fa-star"></i>');
    var d19 = $('<i id="star5" class="fa fa-star"></i>');
    var d20 = $('<br>');
   // var d21 = $('<a href=""><small>more info about car</small></a>');
    var d22 = $('<br>');



    var d23 = $(' <button type="button" id="editButtonCar'+ car.id +'" class="btn btn-primary admin-rent btn-outline-secondary rounded-0 mb-1 admin-rentacar">Edit</button>');
    var d24 = $(' <button type="button" id="buyButtonCar' + car.id +'" class="btn btn-primary  btn-outline-secondary rounded-0 mb-1" style="margin-left: 5px">Reserve</button>');
   // var d25 = $(' </div>'); same as d6 x4

    //Picture
    d4.append(d5);
    d3.append(d4);

    //Stats
    d7.append(d8);
    d7.append(d55);
    d7.append(d9);
    d7.append(d10);
    d7.append(d11);
    d3.append(d7);

    //Price, stars, more info, buttons
    d13.append(d14);
    d13.append(q1);
    //d13.append(d15);
    //d13.append(d16);
    //d13.append(d17);
    //d13.append(d18);
    //d13.append(d19);
    //d13.append(d20);

    d13.append(dq1);
    //d13.append(generateStars(stars))

   // d13.append(d21);
    d13.append(d22);
    d13.append(d23);


    d3.append(d13);

    // adding to div parent
    d2.append(d3);
    tr.append(d2);

    div.append(tr);

    var pId = getUrlParameter('id');
    $('#'+ "editButtonCar" + car.id).click(function () {
        window.location.href = window.location.href.match(/^.*\//)+ "addCar.html?idrent=" + pId + "&edit=true&id=" + car.id;
    });

    if (getUrlParameter("carTypeId") != undefined){
        d13.append(d24);

        $('#'+ "buyButtonCar" + car.id).click(function () {

            localStorage.setItem('car',JSON.stringify(car));
            window.location.href = window.location.href.match(/^.*\//)+ "rentReservation.html?idrent=" + pId + "&start=" + getUrlParameter('start')+'&end=' + getUrlParameter('end')+
            '&rented=' + car.id + '&locStart=' + getUrlParameter('locStart') + "&locEnd=" +getUrlParameter('locEnd') + "&passengers=" + getUrlParameter('passengers');
        });
    }


    $.get({
        url: '/rating/check?id='+car.id+'&type=1',
        success: function (rating) {
           generateStars(rating,"#addStars" + car.id);
        },
        error : function (e) {

        }
    });





    $.get({
        url: '/api/user/rentAdmin',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
        },
        error : function (e) {
            $(".admin-rentacar").remove();
        }
    });

}


function generateStars(average, selector){
    //  $('#addStars' + id).append(res);
    var res;

    for (var i = 0; i < 5; i++){
        if (i > average){
            if (average >= i/2){
                res =  $('<i id="star1" class="fa fa-star-half-o"></i>');
            }else {
                res =  $('<i id="star1" class="fa fa-star-o"></i>');
            }
        } else {
           if (i == average){
               res =  $('<i id="star1" class="fa fa-star-o"></i>');
           }else
            res =  $('<i id="star1" class="fa fa-star"></i>');
        }

        $(selector).append(res);
    }
    res = $('<br>');
    $(selector).append(res);
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


function setFilter(){
    var carTypeId =  getUrlParameter('carTypeId')
    var start = getUrlParameter('start');
    var end = getUrlParameter('end');
    var pass = getUrlParameter('passengers');
    var locStart = getUrlParameter('locStart');
    var locEnd = getUrlParameter('locEnd');

    $('#search-options').val(carTypeId);

    $('#search-location-start').val(locStart);
    $('#search-location-end').val(locEnd);


    $('#checkIn').val(start);
    $('#checkOut').val(end);

    $('#search-minPassengers').val(pass);

    $("#search-minPrice").val(getUrlParameter('minPrice'))  ;
    $("#search-maxPrice").val(getUrlParameter('maxPrice')) ;

}

function fillTypes(data) {
    $('#carOptions').append('<option value= "' + data.id + '">' + data.name + '</option>');
}

function fillLocation(data,selector) {
    $(selector).append('<option value= "' + data.id + '">' + data.addressName + '</option>');
}


function treatAsUTC(date) {
    var result = new Date(date);
    result.setMinutes(result.getMinutes() - result.getTimezoneOffset());
    return result;
}

function daysBetween(startDate, endDate) {
    var millisecondsPerDay = 24 * 60 * 60 * 1000;
    return (treatAsUTC(endDate) - treatAsUTC(startDate)) / millisecondsPerDay;
}