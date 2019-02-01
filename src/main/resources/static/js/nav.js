$(document).ready(function () {


    $("#navbar-frame").load("/navbar.html",function () {
    $(".not-logged").css("display", "none");
    $(".logged").css("display", "none");
    setTimeout(function () {
        checkLogged();
    },2);

});



});

function logout() {
    localStorage.setItem('accessToken', null);
    localStorage.setItem('expiresIn', null);
    var url = window.location.href.match(/^.*\//) + 'login.html';
    window.location.replace(url);
}

//0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin
function checkLogged() {
    $.get({
        url: '/api/user/userType',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            if (data == -1) {
                hideLogged();
                removeAdmin();
            } else if (data == 0) {
                hideNotLogged();
            } else if (data == 1)            {
                hideNotLogged();
                $('.admin').show();
            } else if (data == 3) {
                hideNotLogged();
                $(".hotel-admin").show();
            } else if (data == 2){
                hideNotLogged();
                $(".admin-airline").show();
                $('.airline-admin').show();
            } else  if (data == 4){
                hideNotLogged();
                $(".rentacar-admin").show();
                $('.admin-rentacar').show();
            };

            if(data!= 1){
                $('.admin').remove();
            }
            if (data != 3) {
                $(".hotel-admin").remove();
            }
            if(data != 2){
                $(".admin-airline").remove();
                $('.airline-admin').remove();
            }
            if(data != 4){
               $(".rentacar-admin").remove();
               $('.admin-rentacar').remove();
            }


        },
        error: function (e) {

        }
    });
}
function removeAdmin() {
    $('.admin').remove();
    $(".rentacar-admin").remove();
    $('.admin-rentacar').remove();
    $(".admin-airline").remove();
    $('.airline-admin').remove();
    $(".hotel-admin").remove();
}

function hideLogged() {
    $(".logged").css("display", "none");
    $(".not-logged").css("display", "inherit");
    $('a.not-logged').css("display", "inline-block")
}

function hideNotLogged() {
    $(".not-logged").css("display", "none");
    $(".logged").css("display", "inherit");
    $('a.logged').css("display", "inline-block")
}