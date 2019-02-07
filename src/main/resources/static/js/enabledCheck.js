$(document).ready(function () {


    $.get({
        url : '/api/user/isEnabled',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {
            if(data == false) {
                window.open("changePassword.html", "_self");
            }
        }
    });


});
