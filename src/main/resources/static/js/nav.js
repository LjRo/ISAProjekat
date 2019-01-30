$(document).ready(function () {


    $("#navbar-frame").load("/navbar.html",function () {
    $(".not-logged").css("display", "none");
    $(".logged").css("display", "none");
    setTimeout(function () {
        checkLogged();
    },10);

});



});

function checkAdmins() {
    $.get({
        url: '/api/user/hotelAdmin',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            hideNotLogged();
        },
        error : function (e) {
            $(".hotel-admin").remove();
        }
    });

    $.get({
        url: 'api/user/airlineAdmin',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            hideNotLogged();
        },
        error : function (e) {
            $(".admin-airline").remove();
            $('.airline-admin').remove();
        }
    });

    $.get({
        url: 'api/user/rentAdmin',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            hideNotLogged();
        },
        error : function (e) {
            $(".rentacar-admin").remove();
            $('.admin-rentacar').remove();

        }
    });
}

function checkLogged (){
    $.get({
        url: '/api/user/user',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            hideNotLogged();
            $(".rentacar-admin").remove();
            $('.admin-rentacar').remove();
            $(".admin-airline").remove();
            $('.airline-admin').remove();
            $(".hotel-admin").remove();
        },
        error : function (e) {
            hideLogged();
            checkAdmins();
        }
    });


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