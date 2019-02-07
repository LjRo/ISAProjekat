$(document).ready(function () {

    var pId = getUrlParameter('id');
    var id = getUrlParameter('room');

    var arrival = getUrlParameter('arrival');
    var departure = getUrlParameter('departure');

    arrival = (arrival==undefined||arrival=='')?'':arrival;
    departure = (departure==undefined||departure=='')?'':departure;

    if(arrival != '' && departure != '')
    {
        $('#checkIn').val(arrival);
        $('#checkOut').val(departure);
    }


    $.get({
        url: '/api/flight/allFutureOrders',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                if(data.length > 0)
                {
                    data.forEach(function (entry) {
                        fillAirlineReservations(entry);
                    });
                    $('#confirmReservation').on('click', function(event) {
                        event.preventDefault();
                        var orderId =  $('select[name="selectAirlineReservations"]').val();

                        if(orderId =="" || orderId == undefined)
                        {
                            $('#error').html("Please select order for flight reservation to be able to confirm").fadeIn().delay(3000).fadeOut();
                        }
                        else
                        {
                            var checkedItems = {}, counter = 0;
                            $("#hotelServices li.active").each(function(idx, li) {
                                checkedItems[counter] = $(li).val();
                                counter++;
                            }); // JSON.stringify(checkedItems, null)
                            var services = '';

                            for(var i = 0;i< counter ; i++)
                            {
                                if(i==0)
                                    services += checkedItems[i];
                                else
                                    services += ','+checkedItems[i];
                            }

                            $.post({
                                url: "api/rooms/reserveRoom",
                                data: JSON.stringify({
                                    hotelId: pId,
                                    roomId : id,
                                    reservationId: orderId,
                                    services :services ,
                                    arrivalDate: arrival,
                                    departureDate: departure,
                                }),
                                dataType: "json",
                                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                                contentType: 'application/json',
                                success: function (message) {
                                    if(message.status == '200')
                                    {
                                        $('#success').html("Succesfuly added this room to your reservation").fadeIn().delay(3000).fadeOut();
                                        $('#confirmReservation').attr('disable','disable');
                                        setTimeout(function () {
                                            window.location.replace("hotelprofile.html?id=" + pId +"&page=0");
                                        },3000);
                                    }
                                    else
                                    {
                                        $('#error').html(message.body).fadeIn().delay(3000).fadeOut();
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
                        }

                        //$('#display-json').html(JSON.stringify(checkedItems, null, '\t'));
                    });

                }
                else
                {
                    $('#confirmReservation').remove();
                    $('#error2').html("Please make a airline reservation first to reserve a hotel").fadeIn().delay(10000).fadeOut();
                    setTimeout(function () {
                       // window.location.replace("hotelprofile.html?id=" + pId +"&page=0");
                    },10000);
                }
            }
        },
        error:function (message) {
            alert(message.status);
        }

    });

    $.get({
        url: '/api/hotel/' + pId + '/HotelServicesForHotel',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (arrivedHotelServices) {
            arrivedHotelServices.forEach(function (entry) {
                addService(entry);
            });
            checkboxPrepare();
        }

    });


});

function fillAirlineReservations(data) {

    $("#listReservations").append('<option value= "' + data.id + '"> Order ' + data.id + '</option>');
}

function addService(entry) {
        $("#hotelServices").append('<li value= "' + entry.id + '" class="list-group-item" data-style="button"  data-color="info">'  + entry.name + ' $' + entry.price +  '</li>');
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

function checkboxPrepare() {
    $('.list-group.checked-list-box .list-group-item').each(function () {

        // Settings
        var $widget = $(this),
            $checkbox = $('<input type="checkbox" class="hidden" />'),
            color = ($widget.data('color') ? $widget.data('color') : "primary"),
            style = ($widget.data('style') == "button" ? "btn-" : "list-group-item-"),
            settings = {
                on: {
                    icon: 'glyphicon glyphicon-check'
                },
                off: {
                    icon: 'glyphicon glyphicon-unchecked'
                }
            };

        $widget.css('cursor', 'pointer')
        $widget.append($checkbox);

        // Event Handlers
        $widget.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });


        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $widget.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $widget.find('.state-icon')
                .removeClass()
                .addClass('state-icon ' + settings[$widget.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $widget.addClass(style + color + ' active');
            } else {
                $widget.removeClass(style + color + ' active');
            }
        }

        // Initialization
        function init() {

            if ($widget.data('checked') == true) {
                $checkbox.prop('checked', !$checkbox.is(':checked'));
            }

            updateDisplay();

            // Inject the icon if applicable
            if ($widget.find('.state-icon').length == 0) {
                $widget.prepend('<span class="state-icon ' + settings[$widget.data('state')].icon + '"></span>');
            }
        }
        init();
    });


}