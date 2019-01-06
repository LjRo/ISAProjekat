$(function () {



    /* if (localStorage.getItem('expiresIn') < new Date()){
         $.post({
             url: window.location.href.match(/^.*\//) + "auth/refresh",
             headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
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
     }*/

    if (localStorage.getItem('accessToken') == null){
        $.post({
            url: window.location.href.match(/^.*\//) + "auth/loginToken",
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
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
    }



});