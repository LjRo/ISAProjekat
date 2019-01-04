$(document).ready(function () {
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        var username = $('input[name="username"]').val();
        var password = $('input[name="password"]').val();


        $.post({
            url: window.location.href.match(/^.*\//) + "auth/login",
            data: JSON.stringify({username:username, password: password}),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function(data) {

                localStorage.setItem('accessToken', data['accessToken']);
                localStorage.setItem('expiresIn',data['expiresIn']);


                setTimeout(function () {

                    window.open("index.html", "_self");
                }, 500);

               // window.location.replace("http://localhost:8080/index.html");
            },
            error: function() {
                console.log("Error");
                $('#error').css("visibility", "visible");
            },

        });
    });

});
