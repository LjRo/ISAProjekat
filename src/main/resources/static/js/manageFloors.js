
$(function () {

    var id = getUrlParameter("id");

    $('#error').hide();
    $.get({
        url : 'api/hotel/findById=' + id,
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(hotel) {
            if (hotel != null) {
                var plans = hotel.floorPlans;
                if(plans != null)
                {
                    fillFloors(plans);
                }
            }
        }
    });

    $('#deleteSelectedFloor').click(function () {
        var floorSelected = $('select[name="floor"]').val();
        if(floorSelected === "")
        {
            return ;
        }
        $.post({
            url : 'api/hotel/' +id +'/removeFloor?id=' + floorSelected,
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success : function(message) {
                $.get({
                    url : 'api/hotel/findById=' + id,
                    headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                    success : function(hotel) {
                        if (hotel != null) {
                            var plans = hotel.floorPlans;
                            if(plans != null && plans != undefined)
                            {
                                $('#floors').html("");
                                fillFloors(plans);
                            }
                        }
                    },
                    error: function(message){
                         var a  = message;
                        alert("FWTF");
                    }
                });
            },
            error: function(message) {
                if(message.status == 401)
                {
                    $('#toSubmit').attr("disabled", "disabled")
                    $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                }
            },
        });

        
        
    });

    $('#addNewFloor').click(function () {
        number = $('input[name="number"]').val();
        if(number === "")
        {
            $('#error').text("Must input floor number for floor plan").fadeIn().delay(4000).fadeOut();
        }
        else {
            window.location.href="addFloor.html?id=" + id +"&number=" + number ;
        }
    });

});

function fillFloors(plans) {

    plans.forEach(function(entry) {
        $( "#floors" ).append( '<option value= "' + entry.id + '">'+entry.floorNumber+'</option>' );
    });

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