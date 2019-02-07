var userData;
var friendsSelected = 0;
var seatsSelected = 0;
var friendIds = [];
var friendData;
var airlineReservations = [];
var seatDict = new Object();
var selectedSeats = [];

$(function () {

    var fid = getUrlParameter("id");

    $.get({
        url: '/api/flight/' + fid + '/seatData',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {

            if (data != null) {

                fillSeats(data);

            } else {

            }
        }
    });

    $.get({
        url: '/api/user/findAllFriends',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {

            if (data != null) {
                friendData = data;
                for (var us in data) {
                    fillFriends(data[us], us);
                }
            } else {

            }
        }
    });

    $.get({
        url: '/api/user/profile',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {

            if (data != null) {
                fillData(data);
                userData = data;
            } else {

            }
        }
    });



});


function fillSeats(data) {

    var segments = data.segments;
    var columns = data.columns;
    var rows = data.rows;
    var seatData = data.seats;
    var priceData = seatData[0].price;

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
                    price: priceData,
                    classes: 'economy-class', //your custom CSS class
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
                    //let's create a new <li> which we'll add to the cart items
                    $('<li>' + this.data().category + ' Seat # ' + this.settings.label + ': <b>$' + this.data().price + '</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
                        .attr('id', 'cart-item-' + this.settings.id)
                        .data('seatId', this.settings.id)
                        .appendTo($cart);

                    /*
                     * Lets update the counter and total
                     *
                     * .find function will not find the current seat, because it will change its stauts only after return
                     * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
                     */
                    $counter.text(sc.find('selected').length + 1);
                    seatsSelected = sc.find('selected').length + 1;
                    $total.text(recalculateTotal(sc) + this.data().price);
                    selectedSeats.push(this.settings.id);

                    return 'selected';
                } else if (this.status() == 'selected') {
                    //update the counter
                    $counter.text(sc.find('selected').length - 1);
                    seatsSelected = sc.find('selected').length - 1;
                    //and total
                    $total.text(recalculateTotal(sc) - this.data().price);

                    //remove the item from our cart
                    $('#cart-item-' + this.settings.id).remove();
                    var index = selectedSeats.indexOf(this.settings.id);
                    if (index !== -1) selectedSeats.splice(index, 1);

                    //seat has been vacated
                    return 'available';
                } else if (this.status() == 'unavailable') {
                    //seat has been already booked
                    return 'unavailable';
                } else {
                    return this.style();
                }
            }
        });

    //this will handle "[cancel]" link clicks
    $('#selected-seats').on('click', '.cancel-cart-item', function () {
        //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
        sc.get($(this).parents('li:first').data('seatId')).click();
    });

    //let's pretend some seats have already been booked
    //sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');
    for (var k in seatData) {
        var segOffset = Math.floor(seatData[k].c_column / columns)
        if (seatData[k].taken == true || seatData[k].quick == true) {
            sc.get([seatData[k].c_row + 1 + "_" + (seatData[k].c_column + 1 + segOffset)]).status('unavailable');
        }
        var sId = (seatData[k].c_row + 1 + "_" + (seatData[k].c_column + 1 + segOffset));
        seatDict[sId] = seatData[k];
    }


    $('#confirm1').on('click', function (event) {
        event.preventDefault();
        if (seatsSelected >= 1) {
            if (seatsSelected == 1) {
                $("#seatChooser").css("display", "none");
                $("#dataInput").css("display", "block");

            } else {
                $("#seatChooser").css("display", "none");
                $("#friendChooser").css("display", "block");

            }
        } else {
            alert("Please select at least one seat.")
        }
    });

    $('#confirm2').on('click', function (event) {
        event.preventDefault();
        $("#friendChooser").css("display", "none");
        $("#dataInput").css("display", "block");
        generateInputFields();
    });

    $('#confirm3').on('click', function (event) {
        event.preventDefault();

        var valid = true;
        for (var i = 0; i < seatsSelected; i++) {
            var sel = '#user' + (i + 1) + 'Name';
            var sel2 = '#user' + (i + 1) + 'LastName';
            var sel3 = '#user' + (i + 1) + 'Passport';
            if (!$(sel).val() || !$(sel2).val() || !$(sel3).val()) {
                valid = false;
                break
            }
        }
        if (!valid) {
            alert("Please fill out all fields");
        } else {
            confirmInput();
            $("#dataInput").css("display", "none");
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
}

function recalculateTotal(sc) {
    var total = 0;

    //basically find every selected seat and sum its price
    sc.find('selected').each(function () {
        total += this.data().price;
    });

    return total;
}

function fillFriends(data, us) {
    var table = $('#friendsTable').DataTable();

    var tr = $('<tr id="' + data.id + '"></tr>');
    var firstN = $('<td>' + data.firstName + '</td>');
    var lastN = $('<td>' + data.lastName + '</td>');
    var checkbox = $('<td>' + '<input type="checkbox" class="friendCheckBox" name = "' + us + '">' + '</td>');
    tr.append(firstN).append(lastN).append(checkbox);
    table.row.add(tr).draw();

    $('.friendCheckBox').change(function (event) {
        friendsSelected = $('.friendCheckBox:checkbox:checked').length;
        if (friendsSelected == (seatsSelected - 1)) {
            $('.friendCheckBox:checkbox:not(:checked)').attr('disabled', true);
        }

        if (friendsSelected < (seatsSelected - 1)) {
            $('.friendCheckBox:checkbox:not(:checked)').attr('disabled', false);
        }

    });
}

function fillData(data) {
    $('#user1Name').val(data.firstName);
    $('#user1Name').attr('disabled', 'true');
    $('#user1LastName').val(data.lastName);
    $('#user1LastName').attr('disabled', 'true');
    $('#user1PointsUsed').attr('placeholder', 'Use bonus points | Max:10, (Available: ' + data.points + ')');
}

function generateInputFields() {
    var heightVal = seatsSelected * 200 + 100;
    $('#mainBG').css("height", heightVal);

    var checkedBoxes = $('.friendCheckBox:checkbox:checked');
    for (var k in checkedBoxes) {
        friendIds.push(parseInt(checkedBoxes[k].name));
    }

    var dis = "";

    for (var i = 2; i <= seatsSelected; i++) {
        var row1 = '<br><hr>';
        var row2 = '<input type="text" class="form-control" id="user' + i + 'Name" placeholder="Name" required>';
        var row3 = '<input type="text" class="form-control" id="user' + i + 'LastName" placeholder="Surname" required>';
        var row4 = '<input type="text" class="form-control" id="user' + i + 'Passport" placeholder="Passport" required>';
        $('#userData').append(row1).append(row2).append(row3).append(row4);
    }
    for (var i = 1; i <= friendsSelected; i++) {
        var sel = '#user' + (i + 1) + 'Name';
        var sel2 = '#user' + (i + 1) + 'LastName';
        $(sel).val(friendData[friendIds[i - 1]].firstName);
        $(sel).attr('disabled', 'true');
        $(sel2).val(friendData[friendIds[i - 1]].lastName);
        $(sel2).attr('disabled', 'true');
    }
}

function confirmInput() {

    var idFlight = getUrlParameter("id");
    var userRes = {
        userId: userData.id,
        flight: idFlight ,
        passport: $('#user1Passport').val(),
        pointsUsed: $('#user1PointsUsed').val(),
        firstName: "",
        lastName: "",
        seatId: seatDict[selectedSeats[0]].id,
        totalCost: 0
    };


    if (!userRes.pointsUsed) {
        userRes.pointsUsed = 0;
    }

    airlineReservations.push(userRes);

    for (var k = 0; k < friendsSelected; k++) {
        var sel = '#user' + (k + 2) + 'Passport';
        var res = {
            userId: friendData[friendIds[k]].id,
            flight: idFlight ,
            passport: $(sel).val(),
            seatId: seatDict[selectedSeats[k+1]].id,
            firstName: "",
            lastName: "",
            pointsUsed: 0,
            totalCost: 0

        };
        airlineReservations.push(res);
    }

    for (var j = friendsSelected+1; j < seatsSelected; j++) {
        var selF = '#user' + (j+1) + 'Name';
        var selL = '#user' + (j+1) + 'LastName';
        var selP = '#user' + (j+1) + 'Passport';
        var res = {
            userId: null,
            flight: idFlight ,
            passport: $(selP).val(),
            seatId: seatDict[selectedSeats[j]].id,
            firstName: $(selF).val(),
            lastName: $(selL).val(),
            pointsUsed: 0,
            totalCost: 0
        };
        airlineReservations.push(res);
    }


    $.ajax({
        url: "api/flight/book",
        type: 'POST',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        data: JSON.stringify({
            airlineReservations: airlineReservations
        }),
        contentType: 'application/json',
        success: function (data) {

                    $.get({
                        url: '/api/flight/'+idFlight+'/',
                        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                        success: function (data) {
                            if (data != null) {
                                var today = new Date(data.landTime);
                                var tomorrow = new Date();
                                tomorrow.setDate(today.getDate()+1);
                                var a = getStringFromDate(today);
                                var d = getStringFromDate(tomorrow);

                                $('#addRoom').click(function () {
                                    var url = window.location.href.match(/^.*\//) + 'hotels.html?name=&location=' + data.finish.city +   '&arrival=' + a + '&departure=' + d + '&search=true';
                                    window.location.replace(url);
                                });

                                $('#addCar').click(function () {
                                    var url = window.location.href.match(/^.*\//) + 'rentacars.html?type=1&search='+data.finish.city + '&start=' + a + '&end='+d;
                                    window.location.replace(url);
                                });
                                $('#finishReservation').click(function () {
                                    var url = window.location.href.match(/^.*\//) + 'checkout.html';
                                    window.location.replace('');
                                });
                            }
                        }
                    });


                $("#chooseMore").css("display", "block");
        },
        error: function (data) {
            alert("Something went wrong...");
        },
    });

}

function getStringFromDate(date) {
    var dd = date.getDate();
    var mm = date.getMonth() + 1; //January is 0!
    var yyyy = date.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    return yyyy  + '-' + mm + '-' + dd ;
}
