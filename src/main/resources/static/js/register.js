$(document).ready(function () {
    $('#errorPassword').fadeOut(0);

    $('#registerForm').on('submit', function(e) {
        e.preventDefault();
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();
        var email = $('input[name="email"]').val();
        var firstName = $('input[name="name"]').val();
        var lastName = $('input[name="surname"]').val();
        var address = $('input[name="address"]').val();
        var city = $('input[name="city"]').val();
        var phoneNumber = $('input[name="phoneNumber"]').val();



        $.post({
            url: window.location.href.match(/^.*\//) + "auth/register",
            data: JSON.stringify({email: email, username: username, password: password, firstName: firstName, lastName:lastName, address:address, city:city, phoneNumber:phoneNumber }),
            contentType: 'application/json',
            success: function () {
                window.location.replace("http://localhost:8080/index.html");
            },
            error: function () {
                console.log("Error");
                $('#error').css("visibility", "visible");
            },

        });
    });


    $('#verify').focusout(function () {
        var pass = $('password').val();
        var pass2 = $('verify').val();

        if (pass === pass2){
            $('#errorPassword').fadeOut(50);


        }else {
            $('#errorPassword').fadeIn(50);

        }

    });


});


