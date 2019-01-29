$(document).ready(function () {

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

    $('#searchForm').on('submit',function (e) {
        e.preventDefault();

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

    })

});

function addArticle(rentacar) {
    var icon = "assets/img/rent-a-car.svg";
    $('#carsList').append('<div class="col-sm-6 col-md-5 col-lg-4 item">' +
        '<div class="box">' + '<img src="' + icon + '" style="width:80px;height:80px"/>' +
        '<a href="/rentacarprofile.html?id=' + rentacar.id + '&page=0&pageLocation=0">' +
        '<h3 class="name">' +rentacar.name +'</h3>' +
        '</a>' +
        '<p class="description">Address: <span style = "color:black">'+ 'Click to see google maps' + '</span></p>' +
        '<p class="description">'+ rentacar.description +'</p>' +
        '<a class="edit-rentacar admin" href="editrentacar.html?id=' + rentacar.id +'&name='+ rentacar.name +'&description='+ rentacar.description + '"><img src="/../assets/img/edit.png" style="height:16px;width16px;"></a> ' +
        '<a id="' + rentacar.id + '" class="delete-rentacar admin" href="rentacars.html"><img src="assets/img/delete.png" style="height:16px;width16px;"></a> '+
        '</div>');
}