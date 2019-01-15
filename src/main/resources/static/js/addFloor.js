$(function () {
    var id = getUrlParameter("id");
    var floorNumber = getUrlParameter("number");
    var edit = getUrlParameter("edit");

    var idFloor = undefined;

    if (edit == 1) {
        $.get({
            url: '/api/floor/findById?id=' + floorNumber,
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (floorPlan) {
                $('#save').html("");
                $('#save').append(floorPlan.configuration);
                idFloor = floorPlan.id;

                $("#droppable").droppable({
                    drop: function (event, ui) {
                    }
                });
                $("#droppable").resizable();

                $('.floorplan').each(function (i, obj) {
                    $(this).droppable();
                    $(this).resizable();
                });
                $('.room').each(function (i, obj) {

                    var WhatisIt = $(this);
                    var whatobjectIsIt = obj;

                    $(this).draggable({containment: "parent"});
                    $(this).draggable({
                        start: function () {
                            var selectedID = $(this).attr("id").toString();
                            $('#selected').text(selectedID);
                            var textSelected = $('#' + selectedID + '>p').text();
                            $('#changeText').val(textSelected);
                        }
                    });
                    $(this).resizable();
                });

            }
        });

    } else {
        $("#droppable").droppable({
            drop: function (event, ui) {
            }
        });
        $("#droppable").resizable();
        addNewRoom("Drag and resize");

    }

    $('#saveFloor').click(function () {

        var saveBack = $('#save').html();
        $('.ui-resizable-e').remove();
        $('.ui-resizable-s').remove();
        $('.ui-resizable-se').remove();
        var floorPlanHtml = $('#save').append();
        var changed = $('#save').html().replaceAll('  ', '');
        changed = changed.replaceAll('\n', '');

        $('#save').html(saveBack);



        if (edit == undefined) {
            $.post({
                url: '/api/hotel/' + id + '/addNewFloor',
                data: JSON.stringify({
                    hotel: {id: id},
                    floorNumber: floorNumber,
                    configuration: changed,
                }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function () {
                    window.location.href = 'manageFloors.html?id=' + id;
                },
                error: function (message) {
                    //console.log("Error");
                    if (message.status == 401) {
                        $('#toSubmit').attr("disabled", "disabled");
                        $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                    } else
                        $('#error').text("Error in making room").fadeIn().delay(3000).fadeOut();
                },

            });
        } else if (edit == 1) {
            $.post({
                url: '/api/floor/' + id + '/editFloor',
                data: JSON.stringify({
                    hotel: {id: id},
                    id: idFloor,
                    floorNumber: floorNumber,
                    configuration: floorPlanHtml,
                }),
                headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
                contentType: 'application/json',
                success: function () {
                    window.location.href = 'manageFloors.html?id=' + id;
                },
                error: function () {
                    if (message.status == 401) {
                        $('#toSubmit').attr("disabled", "disabled");
                        $('#error').text("Unauthorized access").fadeIn().delay(4000).fadeOut();
                    } else
                        $('#error').text("Error in making room").fadeIn().delay(3000).fadeOut();
                },

            });

        }


    });


});

String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};


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