

$(function () {
    $('#hideThis').hide();
    $('#error').hide();
    //  $( "input[name*='id']").hide();
    var changing = getUrlParameter("edit");
    $('#success-message').hide();
    $('#deleteButton').hide();

    $.get({
        url: 'api/cartypes/',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success: function(data) {
            if (data != null)
                data.forEach(function(entry){
                    $( "#types" ).append( '<option value= "' + entry.id + '">'+entry.name+'</option>' );
            });
        }
    });
    
    
    
    var id = getUrlParameter('id');
    var idrent = getUrlParameter('idrent');
    if (changing !== undefined && changing === "true" ) {

        $('#submitButton').html("Update");
        $('#title').html("Update Car");


            $.get({
                url: '/api/cars/' + id,
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                success: function (data) {

                    if (data != null)
                        fillData(data);
                }
            });

    }
    if (changing !== undefined && changing === "true") {
        $('#deleteButton').show();
        $.get({
            url: 'api/cars/'+ getUrlParameter('idrent')+'/check?id='+ getUrlParameter('id'),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function(data) {
                if (data === true){ // EDITABLE
                    $('#deleteButton').removeAttr("disabled");
                    $('#error').hide();
                    $('#submitButton').removeAttr("disabled");

                    $('#deleteButton').click(function (e) {
                        e.preventDefault();

                        var idrent = getUrlParameter('idrent');
                        $.post({
                            url: "api/cars/" + idrent + "/remove?id=" + id,
                            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                            contentType: 'application/json',
                            success: function () {
                                $('#success-message').fadeIn(500).delay(1500).fadeOut(500);

                                //  window.location.replace("http://localhost:8080/index.html");

                                setTimeout(function () {

                                    window.open("rentacarprofile.html?id=" + idrent + "&page=0&pageLocation=0", "_self");
                                }, 2000);
                            },
                            error: function (message) {
                                console.log("Error");
                                $('#error').css("visibility", "visible");
                            },

                        });
                    });
                }else {
                    $('#deleteButton').attr("disabled", "disabled");
                    $('#error').show();
                    $('#submitButton').attr('disabled',"disabled");
                }
            }
        });
    }
    });


    $('#addForm').on('submit', function(e) {
        e.preventDefault();

        // $("input[name='id']").val();

        var mark  =   $("input[name='carMark']").val();
        var model =  $("input[name='carModel']").val();
        var name =    $("input[name='carName']").val();
        var regNumber =   $("input[name='regNumber']").val();
        var numDoors =   $("input[name='numDoors']").val();
        var numBags =   $("input[name='numBags']").val();
        var max =  $("input[name='max']").val();
        var dailyPrice =   $("input[name='dailyPrice']").val();
       // var fastReserved =  $("input[name='fastReserved']").val() === "on" ? true : false;
        var fastReserved =  $("input[name='fastReserved']").is(':checked') == true;

        var selectedType=  $("select[name='type']").val();


        // Already exists
        if (changing !== undefined && changing === "true") {
            var id = $("input[name='id']").val();


            $.post({
                url: "api/cars/edit",
                data: JSON.stringify({
                    id : id,
                    mark: mark,
                    model: model,
                    name: name,
                    registrationNumber: regNumber,
                    numberOfDoors : numDoors,
                    numberOfBags : numBags,
                    maxPassengers: max,
                    dailyPrice : dailyPrice,
                    fastReserved : fastReserved,
                    type: {
                        id : selectedType
                    }
                }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function () {
                    $('#success-message').fadeIn(500).delay(1500).fadeOut(500);

                    //  window.location.replace("http://localhost:8080/index.html");

                    setTimeout(function () {

                        window.open("rentacarprofile.html?id="+ idrent+"&page=0&pageLocation=0", "_self");
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
                url: "api/cars/"+ idrent+"/add",
                data: JSON.stringify({
                    mark: mark,
                    model: model,
                    name: name,
                    registrationNumber: regNumber,
                    numberOfDoors : numDoors,
                    numberOfBags : numBags,
                    maxPassengers: max,
                    dailyPrice : dailyPrice,
                    fastReserved : fastReserved,
                    type: {
                        id : selectedType
                    }
                }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function () {
                    $('#success-message').fadeIn(500).delay(1500).fadeOut(500);

                    //  window.location.replace("http://localhost:8080/index.html");

                    setTimeout(function () {

                        window.open("rentacarprofile.html?id="+ idrent+"&page=0&pageLocation=0", "_self");
                    }, 2000);
                },
                error: function () {
                    console.log("Error");
                    $('#error').css("visibility", "visible");
                },

            });


        }




});

function fillData(data) {

    $("input[name='id']").val(data.id);

   $("input[name='carMark']").val(data.mark);
   $("input[name='carModel']").val(data.model);
   $("input[name='carName']").val(data.name);
   $("input[name='regNumber']").val(data.registrationNumber);
   $("input[name='numDoors']").val(data.numberOfDoors);
   $("input[name='numBags']").val(data.numberOfBags);
   $("input[name='max']").val(data.maxPassengers);
   $("input[name='dailyPrice']").val(data.dailyPrice);
   $("input[name='fastReserved']").val(data.fastReserved);

   $("select[name='type']").val(data.type.id);


}

var isPossibleToEdit = function possible(param){





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