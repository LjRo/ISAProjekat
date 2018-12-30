$(document).ready(function () {
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();


        $.post({
            url: window.location.href.match(/^.*\//) + "auth/login",
            data: JSON.stringify({username:username, password: password}),
            headers: {"accessToken": localStorage.getItem('accessToken'), "expiresIn": localStorage.getItem('expiresIn')},
            contentType: 'application/json',
            success: function(data) {

                localStorage.setItem('accessToken', data['accessToken']);
                localStorage.setItem('expiresIn',data['expiresIn']);


                window.location.replace("http://localhost:8080/index.html");
            },
            error: function() {
                console.log("Error");
                $('#error').css("visibility", "visible");
            },

        });
    });

});
