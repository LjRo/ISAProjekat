$(function() {
    $.get({
		url : '/api/user/profile',
		headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
		success : function(data) {

			if (data != null) {
				fillData(data);
			} else {

			}
		}
	});
});

function fillData(data) {
	$('input[name="email"]').val(data.username);
	$('input[name="name"]').val(data.firstName);
	$('input[name="surname"]').val(data.lastName);
	$('input[name="address"]').val(data.address);
	$('input[name="city"]').val(data.city);
	$('input[name="phoneNumber"]').val(data.phoneNumber);
}