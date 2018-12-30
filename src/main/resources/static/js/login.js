$(document).ready(function () {
    $("#navbar-frame").load("/navbar.html");

    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();


        $.post({
            url: window.location.href.match(/^.*\//) + "auth/login",
            data: JSON.stringify({username:username, password: password}),
            contentType: 'application/json',
            success: function() {
                window.location.replace("http://localhost:8080/index.html");
            },
            error: function() {
                console.log("Error");
                $('#error').css("visibility", "visible");
            },

        });
    });

});
