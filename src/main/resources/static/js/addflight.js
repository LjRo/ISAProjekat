$(function() {

    var id = getUrlParameter("id");

    $.get({
        url : '/api/airline/' + id + '/destinations',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {
                for ( var us in data) {
                    fillDest(data[us]);
                }
            } else {

            }
        }
    });

    $("#stopSelect").change(function() {
        var val = $("#stopSelect").val();
        if(val == 0) {
            $("#stop_div1").css("display", "none");
            $("#stop_div2").css("display", "none");
            $("#stop_div3").css("display", "none");
            $("#stop_div4").css("display", "none");
        }

        if(val == 1) {
            $("#stop_div1").css("display", "block");
            $("#stop_div2").css("display", "none");
            $("#stop_div3").css("display", "none");
            $("#stop_div4").css("display", "none");
        }

        if(val == 2) {
            $("#stop_div1").css("display", "block");
            $("#stop_div2").css("display", "block");
            $("#stop_div3").css("display", "none");
            $("#stop_div4").css("display", "none");
        }

        if(val == 3) {
            $("#stop_div1").css("display", "block");
            $("#stop_div2").css("display", "block");
            $("#stop_div3").css("display", "block");
            $("#stop_div4").css("display", "none");
        }

        if(val == 4) {
            $("#stop_div1").css("display", "block");
            $("#stop_div2").css("display", "block");
            $("#stop_div3").css("display", "block");
            $("#stop_div4").css("display", "block");
        }
    });

    $('#addform').on('submit', function(e) {
        e.preventDefault();
        var start = $('select[name="start"]').val();
        var dest = $('select[name="dest"]').val();
        var startDT = $('input[name="startDT"]').val();
        var finishDT = $('input[name="finishDT"]').val();
        var length = $('input[name="length"]').val();
        var distance = $('input[name="distance"]').val();
        var price = $('input[name="price"]').val();
        var segments = $('input[name="segments"]').val();
        var columns = $('input[name="columns"]').val();
        var rows = $('input[name="rows"]').val();

        var stop = $('select[name="numberOfStops"]').val();

        var stop1 = $('select[name="stop1"]').val();
        var stop2 = $('select[name="stop2"]').val();
        var stop3 = $('select[name="stop3"]').val();
        var stop4 = $('select[name="stop4"]').val();

        var stopCodes;

        if(stop == 0) {
            stopCodes = [];
        }
        if(stop == 1) {
            stopCodes = [stop1];
        }
        if(stop == 2) {
            stopCodes = [stop1,stop2];
        }
        if(stop == 3) {
            stopCodes = [stop1,stop2,stop3];
        }
        if(stop == 4) {
            stopCodes = [stop1,stop2,stop3,stop4];
        }

        $.post({
            url: '/api/airline/' + id + '/addFlight',
            data: JSON.stringify({startID:start,destID:dest,startDate:startDT,finishDate:finishDT,length:length,distance:distance,price:price,segments:segments,columns:columns,rows:rows,stopCount:stop,stopIDs: stopCodes}),
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            contentType: 'application/json',
            success: function(article) {
                window.location.replace("airline.html?id="+id);
            },
            error: function(article) {
                //$('#error').css("visibility", "visible");
            },

        });
    });
});


function fillDest(data) {
    $( "#startGrp" ).append( '<option value= "' + data.id + '">'+data.addressName+'</option>' );
    $( "#destGrp" ).append( '<option value= "' + data.id + '">'+data.addressName+'</option>' );
    $( "#stopGrp1" ).append( '<option value= "' + data.id + '">'+data.addressName+'</option>' );
    $( "#stopGrp2" ).append( '<option value= "' + data.id + '">'+data.addressName+'</option>' );
    $( "#stopGrp3" ).append( '<option value= "' + data.id + '">'+data.addressName+'</option>' );
    $( "#stopGrp4" ).append( '<option value= "' + data.id + '">'+data.addressName+'</option>' );
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

