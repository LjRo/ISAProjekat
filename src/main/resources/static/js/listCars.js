$(function() {
    $.get({
        url : '/api/cars/findAll',
        success : function(data) {
            if (data != null) {
                for ( var us in data) {
                    addCar(data[us]);
                }
            }
        }
    });
});
function addCar(user) {
    var table = $('#tableCars > tbody');

    var tr = $('<tr></tr>');
    var name = $('<td>' + user.name + '</td>');
    var mark = $('<td>' + user.mark + '</td>');
    tr.append(name).append(mark);
    table.append(tr);
}