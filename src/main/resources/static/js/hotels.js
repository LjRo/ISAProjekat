$(document).ready(function () {

    $.get({
        url : '/api/hotel/findAll',
        success : function(data) {
            if (data != null) {
                for ( var i in data) {
                    addArticle(data[i]);
                }
            }
        }
    });

});


function addArticle(hotel) {
    var icon = "assets/img/hotel.png";
    $('#hotelList').append(

        '<div class="col-sm-6 col-md-5 col-lg-4 item">' +
        '<div class="box">' + '<img src="' + icon + '" style="width:80px;height:80px"/>' +
        '<br>' +
        '<a href="/hotelprofile.html?id=' + hotel.id + '">' +
        '<h3 class="name">' +hotel.name +'</h3>' +
        '</a>' +
        '<p class="description">Address: <span style = "color:black">'+ hotel.address.addressName  + ',' + hotel.address.city + '</span></p>' +
        '<p class="description">'+ hotel.description +'</p>' +
        '<a class="edit-hotel admin" href="edithotel.html?id=' + hotel.id +'&name='+ hotel.name +'&description='+ hotel.description + '"><img src="/../assets/img/edit.png" style="height:16px;width16px;"></a> ' +
        '<a id="' + hotel.id + '" class="delete-hotel admin" href="hotels.html"><img src="assets/img/delete.png" style="height:16px;width16px;"></a> '+
        '</div>');
}