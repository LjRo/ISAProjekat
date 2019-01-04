

$(function () {
    $('#hideThis').hide();
    $('#success-message').hide();

   // $('#submitButton').html("Update");

    var idrent = getUrlParameter('id');

    $.get({
        url : '/api/rentacar/findById=' + idrent,
        success : function(data) {
            if (data != null) {
                fillData(data);

                $('#addForm').on('submit', function(e) {
                    e.preventDefault();
                    var name = $("input[name='rentName']").val();
                    var description = $("#description").val();

                    var addressName = $("input[name='addressName']").val();
                    var country = $("input[name='country']").val();
                    var city = $("input[name='city']").val();
                    var latitude = $("input[name='latitude']").val();
                    var longitude = $("input[name='longitude']").val();

                    var rentId = getUrlParameter('id');
                    // Already exists

                    var fastDiscount = $( "input[name='discountFast']" ).val();
                    var bronzeDiscount = $( "input[name='discountBronze']" ).val();
                    var silverDiscount = $( "input[name='discountSilver']" ).val();
                    var goldDiscount = $( "input[name='discountGold']" ).val();

                    var idLocation = $("input[name='idLocation']").val();

                    $.post({
                        url: "api/rentacar/edit",
                        data: JSON.stringify({
                            id : rentId,
                            name: name,
                            description: description,
                            fastDiscount: fastDiscount,
                            bronzeDiscount: bronzeDiscount,
                            silverDiscount: silverDiscount,
                            goldDiscount: goldDiscount,
                            address: {
                                id : idLocation,
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
                            $('#success-message').fadeIn(500).delay(1500).fadeOut(500);

                            //  window.location.replace("http://localhost:8080/index.html");

                            setTimeout(function () {

                                window.open("index.html", "_self");
                            }, 2000);
                        },
                        error: function () {
                            console.log("Error");
                            $('#error').css("visibility", "visible");

                        },

                    });

                });


            }

        },
        error: function(){
            //window.location.href = window.location.href.match(/^.*\//) + "index.html";
        }
    });






});









function fillData(data) {

    $( "input[name='id']" ).val(data.id); // idrentacar-a
    $( "input[name='idLocation']" ).val(data.address.id);
    $( "input[name='rentName']" ).val(data.name);
    $( "input[name='addressName']" ).val(data.address.addressName);
    $( "input[name='country']" ).val(data.address.country);
    $( "input[name='city']" ).val(data.address.city);
    $( "input[name='latitude']" ).val(data.address.latitude);
    $( "input[name='longitude']" ).val(data.address.longitude);

    /* Rent a car specific fields*/

    $( "#description" ).val(data.description);
    $( "input[name='discountFast']" ).val(data.discountFast);
    $( "input[name='discountBronze']" ).val(data.discountBronze);
    $( "input[name='discountSilver']" ).val(data.discountSilver);
    $( "input[name='discountGold']" ).val(data.discountGold);


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