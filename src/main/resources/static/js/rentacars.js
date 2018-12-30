$(document).ready(function () {

    $.get({
        url : 'http://localhost:8080/api/rentcar/findAll',
        success : function(data) {
            if (data != null) {
                for ( var i in data) {
                    addArticle(data[i]);
                }
            }
        }
    });

});

function addArticle(rentacar) {
    var icon = "assets/img/car.png";
    $('#carsList').append('<div class="col-sm-6 col-md-5 col-lg-4 item">' +
        '<div class="box">' + '<img src="' + icon + '" style="width:80px;height:80px"/>' +
        '<h3 class="name">' +rentacar.name +'</h3>' +
        '<p class="description">Address: <span style = "color:black">'+ 'Click to see google maps' + '</span></p>' +
        '<p class="description">'+ rentacar.description +'</p>' +
        '<a class="edit-rentacar admin" href="editrentacar.html?id=' + rentacar.id +'&name='+ rentacar.name +'&description='+ rentacar.description + '"><img src="/../assets/img/edit.png" style="height:16px;width16px;"></a> ' +
        '<a id="' + rentacar.id + '" + class="delete-rentacar admin" href="rentacars.html"><img src="assets/img/delete.png" style="height:16px;width16px;"></a> '+
        '</div>');
}