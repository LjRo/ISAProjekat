$(function () {

    var id = getUrlParameter("id");

    if (id == null) {

        $.get({
            url: '/api/user/profile',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (data) {

                if (data != null) {
                    fillData(data);
                } else {

                }
            }
        });
        $.get({
            url : '/api/user/findAllFriendRequests',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success : function(data) {

                if (data != null) {
                    for ( var us in data) {
                        fillRequests(data[us]);
                    }
                } else {

                }
            }
        });

        $.get({
            url : '/api/user/findAllFriends',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success : function(data) {

                if (data != null) {
                    for ( var us in data) {
                        fillFriends(data[us]);
                    }
                } else {

                }
            }
        });

    } else {
        $.get({
            url: '/api/user/'+id+'/profile',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success: function (data) {
                if (data != null) {
                    fillOtherUserData(data);
                } else {

                }
            }
        });

        $.get({
            url: '/api/user/'+id+'/friendStatus',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            dataType: "text",
            success: function (data) {
                if (data != null) {
                    friendButtons(data);
                } else {

                }
            }
        });

        $.get({
            url: '/api/user/'+id+'/friends',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            success : function(data) {

                if (data != null) {
                    for ( var us in data) {
                        fillNonLoggedFriends(data[us]);
                    }
                } else {

                }
            }
        });

    }



    $('#registrationForm').on('submit', function (e) {
        e.preventDefault();
        var email = $('input[id="email"]').val();
        var firstName = $('input[id="name"]').val();
        var lastName = $('input[id="surname"]').val();
        var adr = $('input[id="address"]').val();
        var city = $('input[id="city"]').val();
        var phoneNumber = $('input[id="phoneNumber"]').val();
        var password = $('input[id="password"]').val();
        var password2 = $('input[id="password2"]').val();

        if (password != password2) {
            //TODO: Error
            return;
        }

        $.ajax({
            url: "/api/user/updateInfo",
            type: 'PUT',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                email: email,
                firstName: firstName,
                lastName: lastName,
                address: adr,
                city: city,
                phoneNumber: phoneNumber,
                password: password
            }),
            contentType: 'application/json',
            success: function (article) {
                window.location.replace("/profile.html");
            },
            error: function (article) {
            },

        });
    });

    $('#addBtn').on('click', function(event) {
        event.preventDefault();
        $.ajax({
            url: "api/user/sendFriendRequest",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#addBtn").css("display", "none");
                $("#rqsBtn").css("display", "block");

            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });

    $('#rmvBtn').on('click', function(event) {
        event.preventDefault();
        $.ajax({
            url: "api/user/removeFriend",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#rmvBtn").css("display", "none");
                $("#addBtn").css("display", "block");

            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });

    $('#accBtn').on('click', function(event) {
        event.preventDefault();
        $.ajax({
            url: "api/user/acceptFriendRequest",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#accBtn").css("display", "none");
                $("#decBtn").css("display", "none");
                $("#rmvBtn").css("display", "block");
            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });

    $('#decBtn').on('click', function(event) {
        event.preventDefault();
        $.ajax({
            url: "api/user/denyFriendRequest",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#accBtn").css("display", "none");
                $("#decBtn").css("display", "none");
                $("#addBtn").css("display", "block");
            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });

});

function fillNonLoggedFriends(data) {
    var table = $('#friendsTable').DataTable();

    var tr = $('<tr></tr>');
    var firstN = $('<td>' + data.firstName + '</td>');
    var lastN = $('<td>' + data.lastName + '</td>');
    tr.append(firstN).append(lastN);
    table.row.add(tr).draw();
}

function fillFriends(data) {
    var table = $('#friendsTable').DataTable();

    var tr = $('<tr id="'+data.id+'"></tr>');
    var firstN = $('<td>' + data.firstName + '</td>');
    var lastN = $('<td>' + data.lastName + '</td>');
    var removeBtn = $('<td>' + '<button type="button" class="btn btn-danger fRmvBtn" name = "'+ data.id + '"style="display:block">Remove</button>' + '</td>');
    tr.append(firstN).append(lastN).append(removeBtn);
    table.row.add(tr).draw();

    $('.fRmvBtn').on('click', function(event) {
        event.preventDefault();
        var id = parseInt(event.delegateTarget.name);
        $.ajax({
            url: "api/user/removeFriend",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#"+id).css("display", "none");
            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });
}

function fillRequests(data) {
    var table = $('#requestsTable').DataTable();

    var tr = $('<tr id="'+data.id+'"></tr>');
    var firstN = $('<td>' + data.firstName + '</td>');
    var lastN = $('<td>' + data.lastName + '</td>');
    var accBtn = $('<td>' + '<button type="button" class="btn btn-success rAccBtn" name = "'+ data.id + '"style="display:block">Accept</button>' + '</td>');
    var decBtn = $('<td>' + '<button type="button" class="btn btn-danger rDecBtn" name = "'+ data.id + '"style="display:block">Decline</button>' + '</td>');
    tr.append(firstN).append(lastN).append(accBtn).append(decBtn);
    table.row.add(tr).draw();


    $('.rDecBtn').on('click', function(event) {
        event.preventDefault();
        var id = parseInt(event.delegateTarget.name);
        $.ajax({
            url: "api/user/denyFriendRequest",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#"+id).css("display", "none");
            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });


    $('.rAccBtn').on('click', function(event) {
        event.preventDefault();
        var id = parseInt(event.delegateTarget.name);
        $.ajax({
            url: "api/user/acceptFriendRequest",
            type: 'POST',
            headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
            data: JSON.stringify({
                id: id,
            }),
            contentType: 'application/json',
            success: function (data) {
                $("#"+id).css("display", "none");
            },
            error: function (data) {
                alert("Something went wrong...");
            },
        });

    });
}

function fillData(data) {

    $("#requestPage").css("display", "block");
    $("#editPage").css("display", "block");

    $('input[id="email"]').val(data.email);
    $('input[id="name"]').val(data.firstName);
    $('input[id="surname"]').val(data.lastName);
    $('input[id="address"]').val(data.address);
    $('input[id="city"]').val(data.city);
    $('input[id="phoneNumber"]').val(data.phoneNumber);
    $('#nameDisplay').text(data.firstName + " " + data.lastName);
}

function fillOtherUserData(data) {
    $('#nameDisplay').text(data.firstName + " " + data.lastName);
}

function friendButtons(data) {
    if(data == "NOT_FRIEND") {
        $("#addBtn").css("display", "block");
    }
    if(data == "FRIEND") {
        $("#rmvBtn").css("display", "block");
    }
    if(data == "REQUEST_SENT") {
        $("#rqsBtn").css("display", "block");
    }
    if (data == "REQUEST_RECEIVED") {
        $("#accBtn").css("display", "block");
        $("#decBtn").css("display", "block");
    }
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