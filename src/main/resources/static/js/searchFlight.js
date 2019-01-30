$(function() {

    document.getElementById("fromDate").valueAsDate = new Date();

    $('#searchBtn').unbind('click').bind('click', function(event) {
        event.preventDefault();
        runSearch()
    });

});

function runSearch() {

    var fromLoc = $("#from").val();
    var toLoc = $("#to").val();
    var fromDate = $("#fromDate").val();
    var toDate = $("#toDate").val();


    $.post({
        url : '/api/flight/search',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        data: JSON.stringify({cityFrom:fromLoc, cityTo: toLoc, startDate: fromDate, landDate: toDate}),
        contentType: 'application/json',
        success : function(data) {

            if (data != null) {
                for ( var us in data) {
                    fillFlight(data[us]);
                }
            } else {

            }
        }
    });

}


function fillFlight(data) {
    var table = $('#flights').DataTable();
    table.clear();

    var sDate = new Date(data.startTime);
    var lDate = new Date(data.landTime);
    var tr = $('<tr></tr>');
    var flightData = $('<td><div class="card mb-1">\n' +
        '                        <div class="card-body">\n' +
        '                            <div class="row">\n' +
        '                                <div class="col-md-3">\n' +
        '                                    <img class ="card-img" src="assets/img/flight.png">\n' +
        '                                </div>\n' +
        '                                <div class="col-md-6 border-right">\n' +
        '                                    <h5 class="text-danger" id="Location-Start-Dest">' + data.start.city + ' ('+ data.start.country +') -> ' + data.finish.city + ' ('+ data.finish.country +') </h5>\n' +
        '                                    <a href="#" id="duration" class="badge badge-secondary">'+ data.duration +'h</a>\n' +
        '                                    <br>\n' +
        '                                    <div style="width: 50%;float:left;" style="width: auto">Start date:    <strong><div id="startDate"> ' + sDate + '</div></strong></div>\n' +
        '                                    <div style="width: 50%;float:right;" style="width: auto">Arrival date: <strong><div id="endDate">' + lDate + '</div></strong></div>\n' +
        '                                </div>\n' +
        '                                <div class="col-md-3">\n' +
        '                                    <h5>' + data.price + '$</h5>\n' +
        '                                    <br>\n' +
        '                                    Number of stops: <strong><span id="NumberOfStops">'+ data.numberOfStops +'</span></strong>\n<br><br>' +
        '                                    <a href="/bookflight.html?id='+ data.id +'"><button type="button" class="btn btn-success">Book</button></a>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div></td>');
    var bookBtn = "";
    tr.append(flightData).append(bookBtn);
    table.row.add(tr).draw();
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