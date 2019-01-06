$(function() {
    var f = function() {
        //$(this).before().before().text($(this).is(':checked') ? 'Fast Reserved' : 'Not fast reserved');
    }
    $("input[type='checkbox']").change(f).trigger('change');
});