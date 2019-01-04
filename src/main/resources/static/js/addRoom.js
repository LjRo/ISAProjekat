$(function() {

    var id = getUrlParameter("id");

    $('#error').hide();
    $.get({
        url : '/api/hotel/' + id + '/roomTypes',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {
            if (data != null) {
                for ( var us in data) {
                    fillRoomType(data[us]);
                }
            } else {

            }
        }
    });


    $('#addForm').on('submit', function(e) {
        e.preventDefault();
        var roomType = $('select[name="roomTypeSelected"]').val();
        var roomName = $('select[name="roomTypeSelected"] :selected').text();
        var name = $('input[name="name"]').val();
        var numberOfPeople = $('input[name="numberOfPeople"]').val();
        var numberOfBeds = $('input[name="numberOfBeds"]').val();
        var numberOfRooms = $('input[name="numberOfRooms"]').val();
        var roomNumber = $('input[name="roomNumber"]').val();
        var floor = $('input[name="floorNumber"]').val();

        if(roomType === "" || numberOfPeople === "" || numberOfBeds === "" || numberOfRooms  === "" || roomNumber === "" || floor === "")
        {
            $('#error').text("Please fill all the necessary fields").fadeIn().delay(4000).fadeOut();
            return;
        }

        if(name === "")
            name=null;


        $.post({
            url: '/api/hotel/' + id + '/addRoom',
            data: JSON.stringify(
                {
                    name:name,
                    roomType : {id:roomType,name:roomName},
                    numberOfPeople:numberOfPeople,
                    numberOfBeds:numberOfBeds,
                    numberOfRooms: numberOfRooms ,
                    roomNumber: roomNumber ,
                    floor:floor}),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function(article) {
                window.location.replace("hotelProfile.html?id="+id);
            },
            error: function(message) {
                if(message.status == 401)
                {
                    $('#toSubmit').attr("disabled", "disabled")
                    $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                }
            },

        });
    });
});


function fillRoomType(data) {
    $( "#roomType" ).append( '<option value= "' + data.id + '">'+data.name+'</option>' );
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
