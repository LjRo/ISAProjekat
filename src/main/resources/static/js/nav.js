$(document).ready(function () {
    $("#navbar-frame").load("/navbar.html");


    $.get({
        url: '/api/user/user',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            $(".not-logged").css("display", "none");
            $(".logged").css("display", "inherit");
            $('a.logged').css("display", "inline-block")
        },
        error : function (e) {
            $(".not-logged").css("display", "inherit");
            $('a.not-logged').css("display", "inline-block")
            $(".logged").css("display", "none");
        }
    });
    $.get({
        url: '/api/user/hotelAdmin',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            $(".not-logged").css("display", "none");
            $(".logged").css("display", "inherit");
            $('a.logged').css("display", "inline-block")
        },
        error : function (e) {
            $(".hotel-admin").remove();
        }
    });

    $.get({
        url: 'api/user/airlineAdmin',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function (data) {
            $(".not-logged").css("display", "none");
            $(".logged").css("display", "inherit");
            $('a.logged').css("display", "inline-block")
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
            $(".not-logged").css("display", "none");
            $(".logged").css("display", "inherit");
            $('a.logged').css("display", "inline-block")
        },
        error : function (e) {
            $(".rentacar-admin").remove();
            $('.admin-rentacar').remove();
        }
    });



});
