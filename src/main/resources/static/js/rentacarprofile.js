$(document).ready(function () {
    $("#carsList").load("/carList.html");

    var pId = getUrlParameter('id');
    $.get({
        url : '/api/rentacar/findById=' + pId,
        success : function(hotel) {
            if (hotel != null) {
                $("#nameOfCompany").text(hotel.name);
                $("#Address").text(hotel.address.addressName);
                $("#Description").text(hotel.description);
            }
        }
    });
    $.get({
        url : '/api/cars/findAll?page='+getUrlParameter('page')+'&pagelimit=10',
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

});


function setPagingButtons(MaxPages, MaxElements) {
    var previous = (parseInt(getUrlParameter('page'))-1);
    var next = (parseInt(getUrlParameter('page'))+1);
    var previousElement = $('#previous');
    var nextElement = $('#next');

    $('#TotalPages').html("Page " +(parseInt(getUrlParameter('page'))+1) + "/"+(MaxPages) );
    $('#TotalListings').html("Found "+ MaxElements + " cars");


    var pId = getUrlParameter('id');

    var adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+next;
    nextElement.attr("href", adr);
    adr = window.location.href.match(/^.*\//) + 'rentacarprofile.html?id='+pId+'&page='+previous;
    previousElement.attr("href",adr);

    if (previous < 0)
        $('#previous').attr('href', "#");
    if (next >= MaxPages)
        $('#next').attr('href',"#");
}

function addCar(car) {
    var div = $('#addListings');

  /*  var tr = $('<tr></tr>');
    var name = $('<td>' + user.name + '</td>');
    var mark = $('<td>' + user.mark + '</td>');
    tr.append(name).append(mark);
    table.append(tr);*/

    var tr = $('<div class="card mb-1"></div>');
   // var d1 = $('<div class="card mb-1">');
    var d2 = $(' <div class="card-body"></div>');
    var d3 = $(' <div class="row"></div>');
    var d4 = $('<div class="col-md-3"></div>');
    var d5 = $(' <img class ="card-img" src="assets/img/carlisting.png">');

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


   // $('#pager>li.previous').href= window.location.href.match(/^.*\//);



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