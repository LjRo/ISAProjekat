$(document).ready(function () {

    $.get({
        url : '/api/airline/findAll',
        success : function(data) {
            if (data != null) {
                for ( var i in data) {
                    addArticle(data[i]);
                }
            }
        }
    });

});


function addArticle(airline) {
    var icon = "assets/img/travel.png";
    $('#airlineList').append('<div class="col-sm-6 col-md-5 col-lg-4 item">' +
        '<div class="box">' + '<img src="' + icon + '" style="width:80px;height:80px"/>' +
        '<a href="/airlineprofile.html?id=' + airline.id + '">' +
        '<h3 class="name">' +airline.name +'</h3>' +
        '</a>' +
        '<p class="description">Address: <span style = "color:black">'+ airline.address + '</span></p>' +
        '<p class="description">'+ airline.description +'</p>' +
        '<a class="edit-airline admin" href="editairlines.html?id=' + airline.id +'&name='+ airline.name +'&description='+ airline.description + '"><img src="/../assets/img/edit.png" style="height:16px;width16px;"></a> ' +
        '<a id="' + airline.id + '" class="delete-airline admin" href="airlines.html"><img src="assets/img/delete.png" style="height:16px;width16px;"></a> '+
        '</div>');
}