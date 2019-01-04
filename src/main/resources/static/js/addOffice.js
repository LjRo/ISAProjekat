

$(function () {
    $('#hideThis').hide();
  //  $( "input[name*='id']").hide();
    var changing = getUrlParameter("edit");
    $('#success-message').hide();



    var id = getUrlParameter('id');
    var idrent = getUrlParameter('idrent');
    if (changing !== undefined && changing === "true") {

        $('#submitButton').html("Update");

        $.get({
            url: '/api/office/' +idrent + '/' +id,
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function(data) {

               if (data != null)
                   fillData(data);
            }
        });
    }

    $('#addForm').on('submit', function(e) {
        e.preventDefault();

        var officeName = $("input[name='officeName']").val();
        var addressName = $("input[name='addressName']").val();
        var country = $("input[name='country']").val();
        var city = $("input[name='city']").val();
        var latitude = $("input[name='latitude']").val();
        var longitude = $("input[name='longitude']").val();
        var rentId = getUrlParameter('idrent');
        var changing = getUrlParameter("edit");
        // Already exists
        if (changing !== undefined && changing === "true") {
            var id = $("input[name='id']").val();
            var idLocation = $("input[name='idLocation']").val();

            $.post({
                url: "api/office/"+rentId+"/edit",
                data: JSON.stringify({
                    id : id,
                    name: officeName,
                    location: {
                        id : idLocation,
                        addressName: addressName,
                        country: country,
                        city: city,
                        latitude: latitude,
                        longitude: longitude
                    }
                }),
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


        } else { // Does not exist
            var idrent = getUrlParameter('idrent');
            $.post({
                url: "api/office/"+ idrent+"/add",
                data: JSON.stringify({
                    name: officeName,
                    location: {
                        addressName: addressName,
                        country: country,
                        city: city,
                        latitude: latitude,
                        longitude: longitude
                    }
                }),
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


        }
    });



});


function fillData(data) {
    $( "input[name*='id']" ).val(data.id);
    $( "input[name*='idLocation']" ).val(data.location.id);
    $( "input[name*='officeName']" ).val(data.name);
    $( "input[name*='addressName']" ).val(data.location.addressName);
    $( "input[name*='country']" ).val(data.location.country);
    $( "input[name*='city']" ).val(data.location.city);
    $( "input[name*='latitude']" ).val(data.location.latitude);
    $( "input[name*='longitude']" ).val(data.location.longitude);
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