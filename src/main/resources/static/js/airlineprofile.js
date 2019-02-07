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
        url : '/api/airline/' + id + '/lastSeatData',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {
            fillSeats(data);
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

    $.get({
        url : '/api/airline/' + id + '/editData',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {

                    fillEditData(data);

            } else {

            }
        }
    });

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





    $('#editForm').on('submit', function (e) {
        e.preventDefault();
        var aName = $('input[id="aName"]').val();
        var description = $('input[id="aDesc"]').val();
        var country = $('input[id="country"]').val();
        var address = $('input[id="eAddress"]').val();
        var city = $('input[id="city"]').val();
        var latitude = $('input[id="lat"]').val();
        var longitude = $('input[id="long"]').val();

        var hasFood = false;
        var hasLuggage = false;
        var hasOther = false;

        if(document.getElementById('food').checked) {
            hasFood=true;
        }
        if(document.getElementById('luggage').checked) {
            hasLuggage=true;
        }
        if(document.getElementById('other').checked) {
            hasOther=true;
        }

        var luggagePrice = $('input[id="lugP"]').val();
        var foodPrice = $('input[id="foodP"]').val();

        $.ajax({
            url: "api/airline/" + id + "/edit",
            type: 'PUT',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                name: aName,
                description: description,
                country: country,
                address: address,
                city: city,
                latitude: latitude,
                longitude: longitude,
                hasFood: hasFood,
                hasLuggage: hasLuggage,
                hasOther: hasOther,
                luggagePrice: luggagePrice,
                foodPrice: foodPrice
            }),
            contentType: 'application/json',
            success: function () {
                window.location.replace("/airline.html?id=" + id);
            },
            error: function () {
            },

        });
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
    $('#addLocation').attr("href","addLocation.html?id="+data.id);

    var foodIcon = '';
    var luggageIcon = '';
    var otherIcon = '';

    if(data.hasFood) {
        foodIcon = '<img style="width: 24px; height = 24px" src="assets/img/p_food.png\">'
    }

    if(data.hasExtraLuggage) {
        luggageIcon = '<img style="width: 24px; height = 24px" src="assets/img/p_luggage.png\">'
    }

    if(data.hasOtherServices) {
        otherIcon = '<img style="width: 24px; height = 24px" src="assets/img/p_other.png\">'
    }

    $('#serv').html(foodIcon + luggageIcon + otherIcon);
    $('#lPrice').text(data.luggagePrice);
    $('#fPrice').text(data.foodPrice);


}


function fillDest(data) {
    var table = $('#destinations').DataTable();

    var tr = $('<tr id="'+data.id+'"></tr>');
    var country = $('<td>' + data.country + '</td>');
    var city = $('<td>' + data.city + '</td>');
    var address = $('<td>' + data.addressName + '</td>');
    var deleteBtn = $('<td class ="airline-admin">' + '<button type="button" class="btn btn-danger dBtn" name = "'+ data.id + '"style="display:block">Delete</button>' + '</td>');


    tr.append(country).append(city).append(address).append(deleteBtn);
    table.row.add(tr).draw();

    $('.dBtn').on('click', function(event) {
        event.preventDefault();
        var id = parseInt(event.delegateTarget.name);
        $.ajax({
            url: "api/location/"+id+"/delete",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function (data) {
                $("#"+id).css("display", "none");
            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });
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
        '                                    <a class="user" href="/bookflight.html?id='+ data.id +'"><button type="button" class="btn btn-success">Book</button></a>\n' +
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

function fillEditData(data) {

    $('input[id="aName"]').val(data.name);
    $('input[id="aDesc"]').val(data.description);
    $('input[id="country"]').val(data.country);
    $('input[id="eAddress"]').val(data.address);
    $('input[id="city"]').val(data.city);
    $('input[id="lat"]').val(data.latitude);
    $('input[id="long"]').val(data.longitude);

    if(data.hasFood) {
        $("#food").prop("checked", true );
    }
    if(data.hasLuggage) {
        $("#luggage").prop("checked", true );
    }
    if(data.hasOther) {
        $("#other").prop("checked", true );
    }

    $('input[id="lugP"]').val(data.luggagePrice);
    $('input[id="foodP"]').val(data.foodPrice);


}

function fillSeats(data) {

    if(data!=null) {
        var segments = data.segments;
        var columns = data.columns;
        var rows = data.rows;
        //var seatData = data.seats;
        //var priceData = seatData[0].price;
    } else {
        var segments = 3;
        var columns = 3;
        var rows = 10;
        //var seatData = data.seats;
        //var priceData = seatData[0].price;
    }

    var mapData = [];

    for (i = 0; i < rows; i++) {
        var rowTemplate = "";
        for (k = 0; k < segments; k++) {
            if (rowTemplate != "") {
                rowTemplate += "_"
            }
            for (j = 0; j < columns; j++) {
                rowTemplate += "e"
            }
        }
        mapData.push(rowTemplate);
    }

    var firstSeatLabel = 1;
    var $cart = $('#selected-seats'),
        $counter = $('#counter'),
        $total = $('#total'),
        sc = $('#seat-map').seatCharts({
            map: mapData,
            seats: {
                e: {
                    price: 0,
                    classes: 'economy-class',
                    category: 'Economy Class'
                }

            },
            naming: {
                top: false,
                getLabel: function (character, row, column) {
                    return firstSeatLabel++;
                },
            },
            legend: {
                node: $('#legend'),
                items: [
                    ['e', 'available', 'Economy Class'],
                    ['f', 'unavailable', 'Booked']
                ]
            },
            click: function () {
                if (this.status() == 'available') {
                    return 'available';
                }
            }

        });






}

function plotYearly(data,name,title) {

    var dKeys = [];
    var plotData = [];


    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            dKeys.push(key);
        }
    }
    dKeys.sort();
    for(var j in dKeys) {
        plotData.push({x: new Date(dKeys[j]),y: data[dKeys[j]]});
    }


    var options = {
        animationEnabled: true,
        title:{
            text: title
        },
        axisX:{
            valueFormatString: "DD MMM",
            crosshair: {
                enabled: true,
                snapToDataPoint: true
            }
        },
        axisY: {
            title: "Tickets Sold",
            includeZero: false,
            valueFormatString: "##0",
            crosshair: {
                enabled: true,
                snapToDataPoint: true,
                labelFormatter: function(e) {
                    return "" + CanvasJS.formatNumber(e.value, "##0")+ " T";
                }
            }
        },
        data: [{
            type: "area",
            xValueFormatString: "DD MMM",
            yValueFormatString: "##0",
            dataPoints: plotData
}]
};


    $(name).CanvasJSChart(options);

}

function plotIncome(data,name,title) {

    var dKeys = [];
    var plotData = [];


    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            dKeys.push(key);
        }
    }
    dKeys.sort();
    for(var j in dKeys) {
        plotData.push({x: new Date(dKeys[j]),y: data[dKeys[j]]});
    }


    var options = {
        animationEnabled: true,
        title:{
            text: title
        },
        axisX:{
            valueFormatString: "DD MMM",
            crosshair: {
                enabled: true,
                snapToDataPoint: true
            }
        },
        axisY: {
            title: "Profit (in USD)",
            includeZero: false,
            valueFormatString: "$##0.00",
            crosshair: {
                enabled: true,
                snapToDataPoint: true,
                labelFormatter: function(e) {
                    return "$" + CanvasJS.formatNumber(e.value, "##0.00");
                }
            }
        },
        data: [{
            type: "area",
            xValueFormatString: "DD MMM",
            yValueFormatString: "$##0.00",
            dataPoints: plotData
        }]
    };


    $(name).CanvasJSChart(options);

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

