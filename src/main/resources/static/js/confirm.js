$(function () {

    $('#error').hide();
    $('#success-message').hide();

    //window.location.href.match(/^.*\//)
    var token = getUrlParameter('token');

    if (token) {

        $.post({
            url: "/auth/confirm",
            data: JSON.stringify({
                token: token
            }),
            contentType: 'application/json',
            success: function () {
                $('#success-message').fadeIn(500).delay(1500).fadeOut(500);

                  window.location.replace("http://localhost:8080/index.html");

                setTimeout(function () {

                   // window.open("login.html", "_self");
                }, 3000);
            },
            error: function () {
                console.log("Error");
                $('#error').fadeIn(500).delay(1500).fadeOut(500);

                setTimeout(function () {

                    window.open("index.html", "_self");
                }, 3000);

            },

        });
    }else {
        $('#error').fadeIn(500).delay(1500).fadeOut(500);

        setTimeout(function () {

            window.open("index.html", "_self");
        }, 2000);
    }


});

 var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)), sURLVariables = sPageURL
        .split('&'), sParameterName, i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true
                : decodeURIComponent(sParameterName[1].replace(/\+/g, ' '));
        }
    }
};