$(document).ready(function () { //TODO modifikovati za nase korisnike i skinuti komentare u  styles.css onda
    /*$.get({
        url: 'http://localhost:8080/Project/rest/user',
        success: function (data) {

            if (data != null) {
                $("#home-page").html(data.username);
                $(".not-logged").remove();
                $(".logged").css("visibility", "visible");
                if (data.type == "ADMIN") {
                    $(".admin").css("visibility", "visible");
                    $(".distributor").remove();
                    $(".customer").remove();
                } else {
                    $(".admin").remove();
                    if (data.type == "DISTRIBUTOR") {
                        $(".customer").remove();
                        $(".distributor").css("visibility", "visible");
                    } else {
                        $(".distributor").remove();
                        $(".customer").css("visibility", "visible");
                    }
                }
            } else {
                $(".not-logged").css("visibility", "visible");
            }
        }
    }); */
});
