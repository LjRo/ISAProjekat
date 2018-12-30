$(function() {
    $.get({
		url : '/api/user/profile',
        headers: {"accessToken": localStorage.getItem('accessToken'), "expiresIn": localStorage.getItem('expiresIn')},
		success : function(data) {

			if (data != null) {
				fillData(data);
			} else {

			}
		}
	});
});

function fillData(data) {
	$('input[name="email"]').val(data.email);
	$('input[name="username"]').val(data.username);
	$('input[name="name"]').val(data.firstName);
	$('input[name="surname"]').val(data.lastName);
	$('input[name="address"]').val(data.address);
	$('input[name="city"]').val(data.city);
	$('input[name="phoneNumber"]').val(data.phoneNumber);
}