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

    $.get({
        url : '/api/airline/findAll',
        success : function(data) {
            if (data != null) {
                addArticle(data,'#listAirlines');
            }
        }
    });

    $.get({
        url: '/api/hotel/findAll',
        success: function (data) {
            if (data != null) {
                addArticle(data,'#listHotels');
            }
        }
    });

    $.get({
        url : '/api/rentacar/findAll',
        success : function(data) {
            if (data != null) {
                addArticle(data,'#listRents');
            }
        }
    });


    $('#first_toggle').click(function () {
        $('select[name="selectAirline"]').hide();
        $('select[name="selectHotel"]').hide();
        $('select[name="selectRents"]').hide();
    });
    $('#second_toggle').click(function () {
        $('select[name="selectHotel"]').hide();
        $('select[name="selectRents"]').hide();
        $('select[name="selectAirline"]').fadeIn();
    });
    $('#third_toggle').click(function () {
        $('select[name="selectAirline"]').hide();
        $('select[name="selectRents"]').hide();
        $('select[name="selectHotel"]').fadeIn();
    });

    $('#forth_toggle').click(function () {
        $('select[name="selectAirline"]').hide();
        $('select[name="selectHotel"]').hide();
        $('select[name="selectRents"]').fadeIn();
    });

    $('#confirmRegistration').click(function () {
        var admin = $('#first_toggle').prop('checked');
        var airlineAdmin = $('#second_toggle').prop('checked');
        var hotelAdmin = $('#third_toggle').prop('checked');
        var rentAdmin = $('#forth_toggle').prop('checked');

        if(admin){
            register(1);
        }else if(airlineAdmin){
            checkIfSelected("selectAirline",2);
        }else if(hotelAdmin){
            checkIfSelected("selectHotel",3);
        }else if(rentAdmin){
            checkIfSelected("selectRents",4);
        }

    });
    
});

function checkIfSelected(what,type){
    var company = $('select[name="' + what+ '"]').val();
    if(company != undefined || company != null)
        register(type,company);
    else
        $('#error').html('Please select to which you want to add the administrator above').fadeIn(500).delay(1500).fadeOut(500);

}

/*0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin */
function addArticle(data,where) {
    data.forEach(function (entry) {
        $(where).append('<option value= "' + entry.id + '">' + entry.name + ' at ' + entry.address.city + '</option>');
    });
}

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

function register(typeIn,companyId) {
    var type= typeIn;
    var password = $('input[name="password"]').val();
    var email = $('input[name="email"]').val();
    var firstName = $('input[name="name"]').val();
    var lastName = $('input[name="surname"]').val();
    var address = $('input[name="address"]').val();
    var city = $('input[name="city"]').val();
    var phoneNumber = $('input[name="phoneNumber"]').val();

    if (checkPassword(true)) {

        $.post({
            url: window.location.href.match(/^.*\//) + "auth/registerAdmin",
            data: JSON.stringify({
                type:type,
                username: email,
                password: password,
                firstName: firstName,
                lastName: lastName,
                address: address,
                city: city,
                phoneNumber: phoneNumber
            }),
            contentType: 'application/json',
            success: function () {
                $('#success').html('Successfully added').fadeIn(500).delay(1500).fadeOut(500);


            },
            error: function (message) {
                $('#error').html('Error happend:' + message.result).fadeIn(500).delay(1500).fadeOut(500);
            },

        });
    } else {
        window.alert("Passwords must match!")
    }

});