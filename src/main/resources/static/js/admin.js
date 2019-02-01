$(document).ready(function () {

    $('#nav-hotel form').on('submit', function (e) {
        e.preventDefault();
        addNew('#hotelTabForm',"api/hotel/addHotel");
    });
    $('#nav-airline form').on('submit', function (e) {
        e.preventDefault();
        addNew('#airlineTabForm',"api/airline/add");
    });
     $('#nav-rentcar form').on('submit', function (e) {
            e.preventDefault();
            addNew('#rentTabForm',"api/rentacar/add");
     });

});


function addNew(id, url) {
    var name = $(id + " input[name='name']").val();
    var description = $(id + ' textarea[name="description"]').val();
    var addressName = $(id + " input[name='addressName']").val();
    var country = $(id + " input[name='country']").val();
    var city = $(id + " input[name='city']").val();
    var latitude = $(id + " input[name='latitude']").val();
    var longitude = $(id + " input[name='longitude']").val();
    var fastDiscount = $(id + " input[name='discountFast']").val();

    $.post({
        url: url,
        data: JSON.stringify({
            name: name,
            description: description,
            fastDiscount: fastDiscount,
            address: {
                addressName: addressName,
                country: country,
                city: city,
                latitude: latitude,
                longitude: longitude
            }
        }),
        dataType: "json",
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        contentType: 'application/json',
        success: function () {
            $(id +' div[name="success"]').fadeIn(500).delay(1500).fadeOut(500);
        },
        error: function (message) {
            $(id + ' div[name="success"]').text('Error occured' + message.status).fadeIn().delay(3000).fadeOut();
        },

    });
}