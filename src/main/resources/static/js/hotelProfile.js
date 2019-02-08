var longitude, latitude, description, name, address;

function SortByFloor(a, b){
    var aFloor = a.floorNumber;
    var bFloor = b.floorNumber;
    return ((aFloor < bFloor) ? -1 : ((aFloor > bFloor) ? 1 : 0));
}
function SortByName(a, b){
    var aFloor = a.name;
    var bFloor = b.name;
    return ((aFloor < bFloor) ? -1 : ((aFloor > bFloor) ? 1 : 0));
}

$(document).ready(function () {

    var pId = getUrlParameter('id');
    var search = getUrlParameter('search');
    var hotelPrices = undefined;


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
                checkUserType();
                hotelPrices = hotel.hotelPriceList;

                var floorPlansUS = hotel.floorPlans;
                floorPlansUS.sort(SortByFloor);
                var i = 1;
                floorPlansUS.forEach(function(entry) {
                    if(i==1)
                    {
                        $('#loadPlan').append(entry.configuration);
                        i=0;
                    }
                    $( "#floors" ).append( '<option value= "' + entry.id + '">'+entry.floorNumber+'</option>' );
                });

                var idOfFloorPlan = $('#floors').children(0).val();
                $('select[name="chooseFloor"]').val(idOfFloorPlan);

                $("#nameOfCompany").text(name);
                $("#Address").text(address);
                $("#Description").text(description);
                ymaps.ready(makeMap);

                var roomTypesForSearch = hotel.roomTypes;
                roomTypesForSearch.sort(SortByName);
                roomTypesForSearch.forEach(function(entry) {
                    $( "#hotelOptions" ).append( '<option value= "' + entry.id + '">'+entry.name+'</option>' );
                });



                $.get({
                    url: '/api/hotel/' + pId + '/HotelServicesForHotel',
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    success: function (arrivedHotelServices) {
                        addService(arrivedHotelServices);
                    }

                });

                if(search == undefined || search =="") {
                    $.get({
                        url: '/api/rooms/findByIdAll?id=' + pId + '&page=' + getUrlParameter('page') + '&pagelimit=10',
                        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                        success: function (data) {
                            if (data != null && data.numberOfElements > 0) {
                                var selRoomType;
                                for (var i = 0; i < data.numberOfElements; i++) {
                                    if (hotelPrices != undefined && hotelPrices != null) {
                                        for (var j = 0; j < hotelPrices.length; ++j) {
                                            if (data.content[i].roomType.id == hotelPrices[j].roomType.id) {
                                                selRoomType = hotelPrices[j].price;
                                            }
                                        }
                                    }
                                    addRoom(data.content[i], pId, selRoomType,false);
                                }
                                setPagingButtons(data.totalPages, data.totalElements);
                                checkUserType();
                            }


                        }
                    });
                }
                else {
                    var minPrice =  getUrlParameter('minPrice');
                    var maxPrice =   getUrlParameter('maxPrice');
                    var type = getUrlParameter('type');
                    var arrival = getUrlParameter('arrival');
                    var departure = getUrlParameter('departure');
                    var people = getUrlParameter('people');
                    var Rooms = getUrlParameter('rooms');
                    var Beds = getUrlParameter('beds');
                    var page = getUrlParameter('page');

                    $('#search-minPrice').val(minPrice);
                    $('#search-maxPrice').val(maxPrice);
                    $('#checkIn').val(arrival);
                    $('#checkOut').val(departure);
                    $('#search-People').val(people);
                    $('#search-Rooms').val(Rooms);
                    $('#search-Beds').val(Beds);
                    $('#search-hotel').val(type);

                    $.post({
                        url: "api/rooms/findByIdAvailable",
                        data: JSON.stringify({
                            hotelId: pId,
                            minPrice: minPrice,
                            maxPrice: maxPrice,
                            arrivalDate: arrival,
                            departureDate : departure,
                            numberOfPeople : people,
                            numberOfBeds : Rooms,
                            numberOfRooms : Beds,
                            type : type,
                            page: page
                        }),
                        dataType: "json",
                        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                        contentType: 'application/json',
                        success: function (data) {
                            if (data != null && data.numberOfElements > 0) {
                                $('#addListings').html("");
                                var selRoomType;
                                for (var i = 0; i < data.numberOfElements; i++) {

                                    if (hotelPrices != undefined && hotelPrices != null) {
                                        for (var j = 0; j < hotelPrices.length; ++j) {
                                            if (data.content[i].roomType.id == hotelPrices[j].roomType.id) {
                                                selRoomType = hotelPrices[j].price;
                                            }
                                        }
                                    }

                                    var date1 = new Date(arrival);
                                    var date2 = new Date(departure);
                                    var timeDiff = Math.abs(date2.getTime() - date1.getTime());
                                    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
                                    diffDays = (diffDays == 0) ? 1 : diffDays;
                                    addRoom(data.content[i], pId, diffDays * selRoomType,false);
                                }
                                setPagingButtons(data.totalPages, data.totalElements);


                                checkUserType();
                            }
                        },
                        error: function (message) {
                            $('#error').text('Error occured' + message.status).fadeIn().delay(3000).fadeOut();
                        },

                    });
                }



            }

            if(shouldLoadStats(pId))
            {




                $.get({
                    url : '/api/airline/' + id + '/yearlyTickets',
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    success : function(data) {

                        if (data != null)
                        {
                            plotYearly(data,"#chartContainerYearly", "Yearly Ticket Sales");
                        } else {

                        }
                    }
                });

                $.get({
                    url : '/api/airline/' + id + '/monthlyTickets',
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    success : function(data) {

                        if (data != null)
                        {
                            plotYearly(data,"#chartContainerMonthly", "Monthly Ticket Sales");
                        } else {

                        }
                    }
                });


                $.get({
                    url : '/api/airline/' + id + '/weeklyTickets',
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    success : function(data) {

                        if (data != null)
                        {
                            plotYearly(data,"#chartContainerWeekly", "Weekly Ticket Sales");
                        } else {

                        }
                    }
                });

                $('#filterBtn').unbind('click').bind('click', function(event) {
                    event.preventDefault();

                    var fromDate = $("#fromDate").val();
                    var toDate = $("#toDate").val();

                    $.post({
                        url: '/api/airline/' + id + '/profitFromInterval',
                        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                        data: JSON.stringify({sDate: fromDate, eDate:toDate}),
                        contentType: 'application/json',
                        success: function (data) {

                            if (data != null) {
                                plotIncome(data, "#chartContainerSpecific", "Income");
                            } else {

                            }
                        }
                    });
                });
            }

            $.get({
                url: '/api/rooms/' + pId + '/quick',
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                success: function (data) {
                    if (data != null) {
                        if (data.length > 0)
                        {

                            data.forEach(function (entry) {
                                var price='Unknown';
                                var type = entry.room.roomType;
                                hotelPrices.forEach(function (pricesEntry) {
                                    if(pricesEntry.roomType.id == type.id)
                                        price = pricesEntry.price * entry.nightsStaying;
                                });
                                var newPrice = price * (1-hotel.fastDiscount/100).toFixed(2);
                                addRoom(entry.room,hotel.id,newPrice,true,entry.id,entry.arrivalDate.substring(0,10),entry.departureDate.substring(0,10),price)
                            });
                        }
                    }

                }
            });

        }
    });



    $.get({
        url: '/api/flight/allFutureOrders',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                if (data.length > 0)
                {
                    data.forEach(function (entry) {
                        fillOrders(entry);
                    });
                }
            }

        }
    });





    $('#addRoom').click(function () {
        window.location.href = "addRoom.html?id=" + pId;
    });

   $('#addRoomType').click(function () {
        window.location.href = "addRoomType.html?id=" + pId;
    });
    $('#manageFloors').click(function () {
        window.location.href = "manageFloors.html?id=" + pId;
    });
    $('#editHotel').click(function(){
        window.location.href="editHotel.html?id=" + pId;
    });

    $('#managePrices').click(function(){
        window.location.href="hotelPrices.html?id=" + pId;
    });

    $('#manageHotelServices').click(function(){
        window.location.href="hotelEditServices.html?id=" + pId;
    });



    $('#Search').on('submit',function (e) {
       e.preventDefault();
       $('.user').attr('display','inherit');
        //window.location.replace(addParameterToURL("search=true"));
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();

        var tomorrow = new Date();
        tomorrow.setDate(today.getDate()+1);
        var dd2 = tomorrow.getDate();
        var mm2 = tomorrow.getMonth() + 1; //January is 0!
        var yyyy2 = tomorrow.getFullYear();

        if (dd < 10) {
            dd = '0' + dd;
        }

        if (mm < 10) {
            mm = '0' + mm;
        }
        if (dd2 < 10) {
            dd2 = '0' + dd2;
        }

        if (mm2 < 10) {
            mm2 = '0' + mm2;
        }
         var a = yyyy  + '-' + mm + '-' + dd ;
         var d =  yyyy2 + '-' + mm2 +'-' + dd2 ;


       var minPrice = ($('#search-minPrice').val() == '' )? 0 : $('#search-minPrice').val() ;
       var maxPrice = ($('#search-maxPrice').val() == '')? 1000000000: $('#search-maxPrice').val() ;
       var type = $('#search-hotel').val();
       var arrival = ($('#checkIn').val()=='')?a:$('#checkIn').val();
       var departure = ($('#checkOut').val()=='')?d:$('#checkOut').val();
       var people = ($('#search-People').val()=='')?1:$('#search-People').val();
       var Rooms = ($('#search-Rooms').val()=='')?0:$('#search-Rooms').val();
       var Beds = ($('#search-Beds').val()=='')?0:$('#search-Beds').val();
       var page = (getUrlParameter('page'));

        var start_url = window.location.href.match(/^.*\//) + 'hotelprofile.html?id=' + pId + '&page=' + page;
        var url = addParameterToURL("minPrice=" + minPrice,start_url);
        url = addParameterToURL("maxPrice=" + maxPrice,url);
        url = addParameterToURL("departure=" + departure,url);
        url = addParameterToURL("arrival=" + arrival,url);
        url = addParameterToURL("people=" + people,url);
        url = addParameterToURL("rooms=" + Rooms,url);
        url = addParameterToURL("beds=" + Beds,url);
        url = addParameterToURL('type='+ type,url);
        url = addParameterToURL("search=true" ,url);

        window.location.replace(url);



    });

    $('select[name="chooseFloor"]').on('change', function() {
        $('#loadPlan').html("");

        $.get({
            url: '/api/floor/findById?id=' + this.value,
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (floorPlan) {
                $('#loadPlan').append(floorPlan.configuration);
            }
        });
    });


});

function shouldLoadStats(pId) {
    $.get({
        url: '/api/user/userType',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if(data!=3)
                $('#resultAvailable').html('No right to this function');

            $('#FindAvailable').on('click',function () {
                var start= ($('input[name="StartAvailable"]').val());
                var end= ($('input[name="EndAvailable"]').val());
                var what = $('select[name="what"]').val();
                var where;
                if(what == true)
                {
                    where = 'findAvailable';
                }
                else
                    where = 'findUnavailable';
                if(start !='' && end !='')
                    $.get({
                        url : '/api/rooms/' + where + '?hotelId=' + pId + '&start=' + start + '&end=' + end,
                        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                        success : function(data) {
                            if (data != null)
                            {
                                $('#resultAvailable').html(data).fadeIn().delay(3000).fadeOut();
                            } else {
                                $('#resultAvailable').html('No data found').fadeIn().delay(3000).fadeOut();
                            }
                        },
                        error: function () {
                            $('#resultAvailable').attr('color','red').html('Error occurd').fadeIn().delay(3000).fadeOut().removeAttr('color');
                        }
                    });
                else {
                    $('#resultAvailable').attr('color','red').html('Select dates..').fadeIn().delay(3000).fadeOut().removeAttr('color');
                }

            });

        },
    });
    return false;
}

function fillOrders(data) {
    $("#listReservations").append('<option value= "' + data.id + '"> Order ' + data.id + '</option>');
}

//0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin
function checkUserType() {
    $.get({
        url: '/api/user/userType',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != 0) {
                $(".user").remove();
            }
            if (data != 3) {
                $(".hotel-admin").remove();
            } else if (data == 3) {
                $(".hotel-admin").show();
            }
        },
        error: function (e) {

        }
    });
}

function addService(services) {
    services.forEach(function (entry) {
        $("#hotelServicesList").append('<li value= "' + entry.id + '">'  + entry.name + ' ' + entry.price +  '$</li>');
    });
}


function addRoom(room,hotelId,price,quick,reservationId,startDate,EndDate,oldPrice) {
    var arrival = getUrlParameter('arrival');
    var departure = getUrlParameter('departure');
    arrival = (arrival==undefined||arrival=='')?'':'&arrival='+arrival;
    departure = (departure==undefined||departure=='')?'':'&departure='+departure;

    var display = 'none';
    var newPrice = '';
    var addingToId = '';
    var advice = 'Click search to check if available';
    var datesText = '';
    var strike1 = '',strike2 = '';
    if(arrival != '' && departure !='')
    {
        advice='';
        display='inherit';
    }
    if(quick){
        newPrice = '<h5>$'  + price  + '</h5><br/>';
        price = oldPrice;
        strike1 = '<strike>'
        strike2 = '</strike>'
        advice ='';
        addingToId='Quick';
        display='inherit';
        datesText = 'Start date:<strong>'+ startDate + '</strong>  End Date:<strong>' +  EndDate +'</strong>';
    }



    var pricing = (price==undefined)?"Unknown":price;
    var name = (room.name !=null)? room.name : room.roomType.name + ' ' + room.roomNumber ;
    var tr = $(
        '<div class="card mb-1">' +
        '                                                <div class="card-body">' +
        '                                                    <div class="row">' +
        '                                                        <div class="col-md-3" style="max-height: 150px">' +
        '                                                            <img style="height: 100%" class="card-img" src="assets/img/room.svg">' +
        '                                                        </div>' +
        '                                                        <div class="col-md-6 border-right">' +
        '                                                            <h5 class="text-danger" id="name">' +name +    '</h5>' +
        '                                                            <div style="max-width:100%" class="badge badge-secondary" href="#">Room Type: <span id="RoomType">' + room.roomType.name +   '</span></div>' +
        '                                                            <br>' +
        '                                                            <div style="height: 33%;width:25%;float:left;">People:<strong></br><div id="numberPeople" style="width:auto;float: left">' + room.numberOfPeople + '</div><img src="assets/img/people.png" style="margin-top: 5px"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">Rooms: <strong></br><div id="numberRooms"  style="width:auto;float: left">' + room.numberOfRooms + '</div><img height="16" src="assets/img/room.svg" style="margin-top: 5px" width="16"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">Beds: <strong></br><div id="numberBeds"  style="width:auto;float: left">' + room.numberOfBeds  +  '</div> <img src="assets/img/bed.png" style="margin-top: 5px"></strong></div>' +
        '                                                            <div style="height: 33%;width:25%;float:right;">No.: <strong></br><div id="roomNumber"  style="width:auto;float: left">' + room.roomNumber + '</div><img src="assets/img/roomkey.png" style="margin-top: 5px"></strong></div>' +
        datesText +
        '                                                        </div>' +
        '                                                        <div class="col-md-3">' +
        '                                                            <h5>$'+ strike1 + pricing  + strike2 +'</h5>' +
                                                                           newPrice      +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <i class="fa fa-star"></i>' +
        '                                                            <br>' +
        '                                                            Floor: <strong><span id="Floor">' + room.floor + '</span></strong>' +
        '                                                            <div>' + advice + '</div>' +
        '                                                            <br>' +
        '                                                            <button id="editRoom' + room.id +'" class="btn btn-primary hotel-admin btn-outline-secondary rounded-0 mb-1" style="display:none" type="button"> Edit </button>' +
        '                                                            <button id="reserveRoom' + addingToId + room.id +'" class="btn btn-primary user btn-outline-secondary rounded-0 mb-1" style="display:' + display +  '" type="button"> Reserve  </button>' +
        '                                                        </div>' +
        '                                                    </div>' +
        '                                                </div>' +
        '                                            </div>');
    if(quick)
    {
        $('#quickListings').append(tr);

        $('#' + 'reserveRoomQuick' +  room.id).click(function () {
            var orderId = $('select[name="selectorder"]').val();
            if(orderId == undefined)
            {
                $('#qerror').html('Select order to which you want to add').fadeIn().delay(3000).fadeOut();
                return;
            }
            $.post({
                url: "api/rooms/quickReserve",
                data: JSON.stringify({
                    roomId : room.id,
                    orderId: orderId,
                    reservationId: reservationId,
                }),
                dataType: "json",
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function (message) {
                    if(message.status == '200')
                    {
                        $('#qsuccess').html("Succesfuly added this room to your reservation").fadeIn().delay(3000).fadeOut();
                        $('#confirmReservation').attr('disable','disable');
                    }
                    else
                    {
                        $('#qerror').html(message.body).fadeIn().delay(3000).fadeOut();
                    }
                },
                error: function (data) {
                    if(data.status == undefined)
                    {
                        data.status = 'Unknown';
                    }
                    if(data.status == '500')
                    {
                        $('#error').html("Alredy reserved").fadeIn().delay(3000).fadeOut();
                    } else
                        $('#error').html("Error happened while reserving this(" + data.status +')' ).fadeIn().delay(3000).fadeOut();
                }
            });
        });
    }
    else
    {
        $('#addListings').append(tr);
        $('#' + 'editRoom' +  room.id).click(function () {
            window.location.href = "addRoom.html?id=" + hotelId +"&room="+ room.id + "&edit=1";
        });
        $('#' + 'reserveRoom' +  room.id).click(function () {
            window.location.href = "hotelReservation.html?id=" + hotelId +"&room="+ room.id +arrival + departure; // + "&edit=1";
        });
    }




}




function setPagingButtons(MaxPages, MaxElements) {
    var previous = (parseInt(getUrlParameter('page')) - 1);
    var next = (parseInt(getUrlParameter('page')) + 1);
    var previousElement = $('#previous');
    var nextElement = $('#next');

    $('#TotalPages').html("Page " + (parseInt(getUrlParameter('page')) + 1) + "/" + (MaxPages) +" ");
    $('#TotalListings').html("| Found " + MaxElements + "<strong> Rooms</strong>");


    var pId = getUrlParameter('id');

    var search = getUrlParameter('search');
    var minPrice =  getUrlParameter('minPrice');
    var maxPrice =   getUrlParameter('maxPrice');
    var type = getUrlParameter('type');
    var arrival = getUrlParameter('arrival');
    var departure = getUrlParameter('departure');
    var people = getUrlParameter('people');
    var Rooms = getUrlParameter('rooms');
    var Beds = getUrlParameter('beds');
    var adr;
    if(search =="" || search == undefined)
    {
        adr = window.location.href.match(/^.*\//) + 'hotelProfile.html?id=' + pId + '&page=' + next;
        nextElement.attr("href", adr);
        adr = window.location.href.match(/^.*\//) + 'hotelProfile.html?id=' + pId + '&page=' + previous;
        previousElement.attr("href", adr);
    }
    else
    {
        adr = window.location.href.match(/^.*\//) + 'hotelProfile.html?id=' + pId + '&page=' + next + '&type=' + type + '&arrival=' + arrival + '&departure=' + departure +
        '&people='+people+'&rooms=' + Rooms + '&beds=' + Beds + '&minPrice=' + minPrice + '&maxPrice=' + maxPrice + '&search=true' ;
        nextElement.attr("href", adr);
        adr = window.location.href.match(/^.*\//) + 'hotelProfile.html?id=' + pId + '&page=' + previous + '&type=' + type + '&arrival=' + arrival + '&departure=' + departure +
            '&people='+people+'&rooms=' + Rooms + '&beds=' + Beds + '&minPrice=' + minPrice + '&maxPrice=' + maxPrice + '&search=true' ;
        previousElement.attr("href", adr);
    }

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
function addParameterToURL(param,start_url){
    var url = start_url;
    url += (url.split('?')[1] ? '&':'?') + param;
    return url;
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
