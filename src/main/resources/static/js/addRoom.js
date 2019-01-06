$(function () {

    var id = getUrlParameter("id");
    var edit = getUrlParameter("edit");
    var roomId = getUrlParameter("room");
    $('#error').hide();
    $('input[name="id"]').hide();
    $('#delete').hide();
    $.get({
        url: '/api/hotel/' + id + '/roomTypes',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data != null) {
                for (var us in data) {
                    fillRoomType(data[us]);
                }
            } else {

            }
        }
    });

    if (edit == 1) {

        $.get({
            url: '/api/rooms/findById?id=' + roomId,
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (data) {
                if (data != null) {
                    $('select[name="roomTypeSelected"]').val(data.roomType.id);
                    //$('select[name="roomTypeSelected"] :selected').text();
                    $('input[name="id"]').val(data.id);
                    $('input[name="name"]').val(data.name);
                    $('input[name="numberOfPeople"]').val(data.numberOfPeople);
                    $('input[name="numberOfBeds"]').val(data.numberOfBeds);
                    $('input[name="numberOfRooms"]').val(data.numberOfRooms);
                    $('input[name="roomNumber"]').val(data.roomNumber);
                    $('input[name="floorNumber"]').val(data.floor);
                } else {

                }
            }
        });

        $('#delete').show();

        $('#delete').click(function () {
            var idField = $('input[name="id"]').val();
            $.post({
                url: '/api/rooms/'+ id  + '/deleteRoom',
                data: JSON.stringify(
                    {
                        id: idField
                    }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function (article) {
                    window.location.replace("hotelProfile.html?id=" + id +"&page=0");
                },
                error: function (message) {
                    if (message.status == 401) {
                        $('#toSubmit').attr("disabled", "disabled");
                        $('#delete').attr("disabled","disabled");
                        $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                    }
                    else if(message.status == 500) {
                        $('#error').text("Server error cause dad identifier for room or hotel in request").fadeIn().delay(4000).fadeOut();
                    }
                },

            });

        });

    }

    $('#addForm').on('submit', function (e) {
        e.preventDefault();
        var roomType = $('select[name="roomTypeSelected"]').val();
        var roomName = $('select[name="roomTypeSelected"] :selected').text();
        var name = $('input[name="name"]').val();
        var numberOfPeople = $('input[name="numberOfPeople"]').val();
        var numberOfBeds = $('input[name="numberOfBeds"]').val();
        var numberOfRooms = $('input[name="numberOfRooms"]').val();
        var roomNumber = $('input[name="roomNumber"]').val();
        var floor = $('input[name="floorNumber"]').val();
        var idField = $('input[name="id"]').val();

        if (roomType === "" || numberOfPeople === "" || numberOfBeds === "" || numberOfRooms === "" || roomNumber === "" || floor === "") {
            $('#error').text("Please fill all the necessary fields").fadeIn().delay(4000).fadeOut();
            return;
        }

        if (name === "")
            name = null;
        if (edit == undefined) {
            $.post({
                url: '/api/hotel/' + id + '/addRoom',
                data: JSON.stringify(
                    {
                        name: name,
                        roomType: {id: roomType, name: roomName},
                        numberOfPeople: numberOfPeople,
                        numberOfBeds: numberOfBeds,
                        numberOfRooms: numberOfRooms,
                        roomNumber: roomNumber,
                        floor: floor
                    }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function (article) {
                    window.location.replace("hotelProfile.html?id=" + id + "&page=0");
                },
                error: function (message) {
                    if (message.status == 401) {
                        $('#toSubmit').attr("disabled", "disabled");
                        $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                    }
                },

            });
        } else if (edit == 1) {
            $.post({
                url: '/api/rooms/'+ id  + '/editRoom',
                data: JSON.stringify(
                    {
                        id: idField,
                        name: name,
                        roomType: {id: roomType, name: roomName},
                        numberOfPeople: numberOfPeople,
                        numberOfBeds: numberOfBeds,
                        numberOfRooms: numberOfRooms,
                        roomNumber: roomNumber,
                        floor: floor
                    }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function (article) {
                    window.location.replace("hotelProfile.html?id=" + id +"&page=0");
                },
                error: function (message) {
                    if (message.status == 401) {
                        $('#toSubmit').attr("disabled", "disabled");
                        $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                    }
                    else {
                        $('#error').text("Bad identifier for room or hotel in request").fadeIn().delay(4000).fadeOut();
                    }
                },

            });
        }

    });
});


function fillRoomType(data) {
    $("#roomType").append('<option value= "' + data.id + '">' + data.name + '</option>');
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
