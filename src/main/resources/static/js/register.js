$(document).ready(function () {
    $('#errorPassword').fadeOut(0);

    $('#success-message').fadeOut(0);


    $('#registerForm').on('submit', function(e) {
        e.preventDefault();
      //  var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();
        var email = $('input[name="email"]').val();
        var firstName = $('input[name="name"]').val();
        var lastName = $('input[name="surname"]').val();
        var address = $('input[name="address"]').val();
        var city = $('input[name="city"]').val();
        var phoneNumber = $('input[name="phoneNumber"]').val();

        if (checkPassword(true)) {

            $.post({
                url: window.location.href.match(/^.*\//) + "auth/register",
                data: JSON.stringify({
                    email: email,
                    password: password,
                    firstName: firstName,
                    lastName: lastName,
                    address: address,
                    city: city,
                    phoneNumber: phoneNumber
                }),
                contentType: 'application/json',
                success: function () {
                    $('#success-message').fadeIn(500).delay(1500).fadeOut(500);

                    //  window.location.replace("http://localhost:8080/index.html");

                    setTimeout(function () {

                        window.open("index.html", "_self");
                    }, 2000);
                },
                error: function () {
                    console.log("Error");
                    $('#error').css("visibility", "visible");
                },

            });
        } else {
            window.alert("Passwords must match!")
        }

    });


    $('#verify').focusout(function () {
        checkPassword(false);

    });


});

function checkPassword(from){
    var pass = $('#password').val();
    var pass2 = $('#verify').val();

    if (pass === pass2){
        $('#errorPassword').fadeOut(50);

        return true;
    }else {
        $('#errorPassword').fadeIn(50);

        return false;
    }
}
