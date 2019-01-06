$(function () {
    $('#selected').hide();
    $('#iterator').hide();
});


function functionDel() {
    selectedID = $('#selected').html();
    $('#' + selectedID).remove();
}

function addNewRoom(name) {
    if (name === "") {
        name = Math.floor(Math.random() * 100) + 100;
    }
    idCount = 1 + parseInt($('#iterator').html());
    $('#iterator').html(idCount);
    $('#droppable').append(
        '<div id="draggable' + idCount + '" style="z-index:10;" class="room"><p>' + name + '</p>'
    );

    $("#draggable" + idCount).draggable({containment: "parent"});
    $("#draggable" + idCount).draggable({
        start: function () {
            selectedID = $(this).attr("id").toString();
            $('#selected').text(selectedID);
            textSelected = $('#' + selectedID + '>p').text();
            $('#changeText').val(textSelected);
        }
    });
    $("#draggable" + idCount).resizable();

}

function changeText(value) {
    selectedID = $('#selected').html();
    $('#' + selectedID + '>p').text(value);
}

function addNewStairs() {
    idCount = 1 + parseInt($('#iterator').html());
    $('#iterator').html(idCount);
    $('#droppable').append(
        '<div id="draggable' + idCount + '" style="z-index:10" class="room"><img style="z-index:15; width:100%;height: 100%;" src="assets/img/stairs.png"></div>');
    $("#draggable" + idCount).draggable({containment: "parent"}); //STAIRS
    $("#draggable" + idCount).draggable({
        start: function () {
            selectedID = $(this).attr("id").toString();
            $('#selected').text(selectedID);
        }
    });
    $("#draggable" + idCount).resizable();

}


jQuery.fn.rotate = function (degrees) {
    $(this).css({'transform': 'rotate(' + degrees + 'deg)'});
    return $(this);
};
/*
  rotation= 0;
function rotateElement(){
  selectedID = $('#selected').text();
  if(selectedID!="")
  {
      rotated = $('#'+selectedID).attr('style');
      found = rotated.lastIndexOf("rotate(");
      degFound = 	rotated.lastIndexOf("deg)");
      angle = rotated.substring(found+7, degFound);
      if(found != -1)
      {
       $('#'+selectedID).rotate(parseInt(angle) + 5);
      }
      else
      {
        $('#'+selectedID).rotate(5);
      }
  }


}
*/
