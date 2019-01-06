$(function () {
    var id = getUrlParameter("id");


    $('#addForm').on('submit', function (e) {
        e.preventDefault();
        var name = $('input[name="name"]').val();

        if (name === "") {
            $('#error').text("Please fill all the necessary fields").fadeIn().delay(4000).fadeOut();
            return;
        }

        $.post({
            url: '/api/hotel/' + id + '/addRoomType',
            data: JSON.stringify({name: name}),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function (article) {
                window.location.replace("hotelProfile.html?id=" + id + "&page=0");
            },
            error: function (message) {
                if(message.status == 401)
                {
                    $('#toSubmit').attr("disabled", "disabled");
                    $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                }
            },

        });
    });
});


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