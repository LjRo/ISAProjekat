$(function() {
    $.get({
        url : '/api/user/profile',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                fillData(data);
            } else {

            }
        }
    });

    $('#registrationForm').on('submit', function(e) {
        e.preventDefault();
        var email = $('input[id="email"]').val();
        var firstName = $('input[id="name"]').val();
        var lastName = $('input[id="surname"]').val();
        var adr = $('input[id="address"]').val();
        var city = $('input[id="city"]').val();
        var phoneNumber = $('input[id="phoneNumber"]').val();
        $.ajax({
            url: "/api/user/updateInfo",
            type: 'PUT',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({email:email,firstName:firstName,lastName:lastName,address:adr,city:city,phoneNumber:phoneNumber}),
            contentType: 'application/json',
            success: function(article) {
                window.location.replace("/profile.html");
            },
            error: function(article) {
            },

        });
    });
});

function fillData(data) {
    $('input[id="email"]').val(data.email);
    $('input[id="name"]').val(data.firstName);
    $('input[id="surname"]').val(data.lastName);
    $('input[id="address"]').val(data.address);
    $('input[id="city"]').val(data.city);
    $('input[id="phoneNumber"]').val(data.phoneNumber);
    $('#nameDisplay').text(data.firstName + " " + data.lastName);
}