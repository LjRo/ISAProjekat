$(document).ready(function () {
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        var password = $('input[name="password"]').val();
        var password2 = $('input[name="password2"]').val();

        if(password != password2) {
            alert("Passwords must match!");
        }

        $.post({
            url: "/api/user/changePassword",
            data: JSON.stringify({password:password}),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function(data) {

                setTimeout(function () {

                    window.open("index.html", "_self");
                }, 500);

            },
            error: function() {
                console.log("Error");
                $('#error').css("visibility", "visible");
            },

        });

    });

});
