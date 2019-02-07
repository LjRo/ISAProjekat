$(function () {


        $.get({
            url : '/api/user/findAllUsers',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success : function(data) {

                if (data != null) {
                    for ( var us in data) {
                        fillUsers(data[us]);
                    }
                } else {

                }
            }
        });

});

function fillUsers(data) {
    var table = $('#friendsTable').DataTable();

    var tr = $('<tr id="'+data.id+'"></tr>');
    var firstN = $('<td>' + data.firstName + '</td>');
    var lastN = $('<td>' + data.lastName + '</td>');
    var profileBtn = $('<td>' + '<a href="profile.html?id='+ data.id +'"><button type="button" class="btn btn-info" name = "'+ data.id + '"style="display:block">Profile</button></a>' + '</td>');
    tr.append(firstN).append(lastN).append(profileBtn);
    table.row.add(tr).draw();

}