$(document).ready(function () {


});

function logout() {
    localStorage.setItem('accessToken', null);
    localStorage.setItem('expiresIn', null);
    var url = window.location.href.match(/^.*\//) + 'login.html';
    window.location.replace(url);
}
