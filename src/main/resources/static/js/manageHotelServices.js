$(function () {

    var id = getUrlParameter("id");

    $('#error').hide();
    $('#success').hide();
    $('input[name="editId"]').hide();
     $.get({
     url: '/api/hotel/' + id + '/HotelServicesForHotel',
     headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
     success: function (arrivedHotelServices) {
         fillHotelServices(arrivedHotelServices.sort(SortBy));

         var idOfHotelService = $('#listServices').children(0).val();
         $('select[name="selectServices"]').val(idOfHotelService);
         }

    });

    $('#addNewService').click(function () {
        var price = $('input[name="price"]').val();
        var name = $('input[name="name"]').val();
        var editId =  $('input[name="editId"]').val();
        if (price === "" || name ==="") {
            $('#error').text("Must input price number for service and name").fadeIn().delay(4000).fadeOut();
        } else {

            if(editId == "")
            {
                $.post({
                    url: '/api/hotel/'+ id  + '/addHotelServices',
                    data: JSON.stringify(
                        {
                            name: name,
                            price: price,
                            hotel: {
                                id:id
                            }
                        }),
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    contentType: 'application/json',
                    success: function (message) {
                        $('#success').text("Added new service " + name +" to your hotel").fadeIn().delay(4000).fadeOut();
                        $.get({
                            url: '/api/hotel/' + id + '/HotelServicesForHotel',
                            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                            success: function (arrivedHotelServices) {
                                $("#listServices").html("");
                                fillHotelServices(arrivedHotelServices.sort(SortBy));

                                var idOfHotelService = $('#listServices').children(0).val();
                                $('select[name="selectServices"]').val(idOfHotelService);
                            }

                        });
                    },
                    error: function (message) {
                        if (message.status == 401) {
                            $('#toSubmit').attr("disabled", "disabled");
                            $('#delete').attr("disabled","disabled");
                            $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                        }
                        else if(message.status == 500) {
                            $('#error').text("Server error").fadeIn().delay(4000).fadeOut();
                        }
                    },

                });
            }
            else {
                $('input[name="editId"]').val("");
                $('input[name="price"]').val("");
                $('input[name="name"]').val("");
                $('#addNewService').val("Add new Service");
                $.post({
                    url: '/api/hotel/editHotelServices',
                    data: JSON.stringify(
                        {
                            id:editId,
                            name: name,
                            price: price,
                            hotel: {
                                id:id
                            }
                        }),
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    contentType: 'application/json',
                    success: function (message) {
                        $('#success').text("Changed service " + name +" in your hotel").fadeIn().delay(4000).fadeOut();
                        $.get({
                            url: '/api/hotel/' + id + '/HotelServicesForHotel',
                            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                            success: function (arrivedHotelServices) {
                               //  fillHotelServices(arrivedHotelServices.sort(SortBy));
                            }

                        });
                    },
                    error: function (message) {
                        if (message.status == 401) {
                            $('#toSubmit').attr("disabled", "disabled");
                            $('#delete').attr("disabled","disabled");
                            $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                        }
                        else if(message.status == 500) {
                            $('#error').text("Server error").fadeIn().delay(4000).fadeOut();
                        }
                    },

                });
            }
        }
    });

    $('#editSelectedService').click(function () {
        var hotelServiceId = $('select[name="selectServices"]').val();
        $.get({
            url: '/api/hotel/' + hotelServiceId + '/HotelServices',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (arrivedHotelServices) {
                $('input[name="name"]').val(arrivedHotelServices.name);
                $('input[name="price"]').val(arrivedHotelServices.price);
                $('input[name="editId"]').val(arrivedHotelServices.id);

                $('#addNewService').val("Change");
            }

        });

    });

   // api/hotel/removeHotelServices
    $('#deleteSelectedService').click(function () {
        var selectedService = $('select[name="selectServices"]').val();
        if (selectedService === "") {
            return;
        }
        $.post({
            url: 'api/hotel/removeHotelServices',
            contentType: 'application/json',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify(
                {
                    id:selectedService,
                    hotel:{
                        id:id
                    }
                }),
            success: function (message) {
                $.get({
                    url: '/api/hotel/' + id + '/HotelServicesForHotel',
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    success: function (arrivedHotelServices) {
                        $("#listServices").html("");
                        fillHotelServices(arrivedHotelServices.sort(SortBy));

                        var idOfHotelService = $('#listServices').children(0).val();
                        $('select[name="selectServices"]').val(idOfHotelService);
                    }

                });
            },
            error: function (message) {
                if (message.status == 401) {
                    $('#toSubmit').attr("disabled", "disabled")
                    $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                }
            },
        });

    });


});

function fillHotelServices(services) {
    services.forEach(function (entry) {
        $("#listServices").append('<option value= "' + entry.id + '">' + entry.name + '</option>');
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

function SortBy(a, b) {
    return ('' + a.name).localeCompare(b.name);
}

