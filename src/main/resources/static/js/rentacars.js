$(document).ready(function () {


    if (getUrlParameter('search') != null){

        var start = getUrlParameter('start');
        var end = getUrlParameter('end');
        var type = getUrlParameter('type');
        var search = getUrlParameter('search');

        $('#search-options').val(type);
        $("#search-name").val(search);
        $('#checkIn').val(start);
        $('#checkOut').val(end);
        sendSearch();

    }else {
        $.get({
            url : '/api/rentacar/findAll',
            success : function(data) {
                if (data != null) {
                    for ( var i in data) {
                        addArticle(data[i]);
                    }
                }
            }
        });

        $("#checkIn").val(new Date().toISOString().split('T')[0]);
        $("#checkOut").val(new Date().toISOString().split('T')[0]);
    }
    $('#searchForm').on('submit',function (e) {
        e.preventDefault();

        sendSearch();

    })

});


function sendSearch(){
    var type = $("#search-options option:selected").val();
    var search = $("#search-name").val();

    var start = new Date($("#checkIn").val());
    var end = new Date($("#checkOut").val());

    start = start.toISOString().split('T')[0];
    end = end.toISOString().split('T')[0];

    $.get({
        url : '/api/rentacar/filtered?type='+ type+'&search='+ search + '&start=' + start + '&end=' + end,
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {
            if (data != null ) {
                $('#carsList').html("");
                for ( var i in data) {
                    addArticle(data[i]);
                }

            }
        }
    });
}


var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1].replace(/\+/g, ' '));
        }
    }
};

function addArticle(rentacar) {

    var link;

    var start = getUrlParameter('start');
    var end = getUrlParameter('end');

    if (start != undefined && end != undefined){
        link = '/rentacarprofile.html?id=' + rentacar.id + '&page=0&pageLocation=0&start='+ start + '&end='+ end + '&carTypeId=0&passengers=1&locStart=1&locEnd=1';
    }else {
        link = '/rentacarprofile.html?id=' + rentacar.id + '&page=0&pageLocation=0';
    }

    var icon = "assets/img/rent-a-car.svg";
    $('#carsList').append('<div class="col-sm-6 col-md-5 col-lg-4 item">' +
        '<div class="box">' + '<img src="' + icon + '" style="width:80px;height:80px"/>' +
        '<a href="'+link+'">' +
        '<h3 class="name">' +rentacar.name +'</h3>' +
        '</a>' +
        '<p class="description">Address: <span style = "color:black">'+ 'Click to see google maps' + '</span></p>' +
        '<p class="description">'+ rentacar.description +'</p>' +
        '<a class="edit-rentacar admin" href="editrentacar.html?id=' + rentacar.id +'&name='+ rentacar.name +'&description='+ rentacar.description + '"></a> ' +
        '<a id="' + rentacar.id + '" class="delete-rentacar admin" href="rentacars.html"></a> '+
        '</div>');
}