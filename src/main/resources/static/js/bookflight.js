$(function() {

    var fid = getUrlParameter("id");

    $.get({
        url : '/api/flight/' + fid + '/seatData',
        headers: {"Authorization": "Bearer " + localStorage.getItem('accessToken')},
        success : function(data) {

            if (data != null) {

                fillSeats(data);

            } else {

            }
        }
    });

});


function fillSeats(data) {

    var segments = data.segments;
    var columns = data.columns;
    var rows  = data.rows;
    var seatData = data.seats;
    var priceData = seatData[0].price;

    var mapData = [];

    for(i = 0; i < rows; i++) {
        var rowTemplate = "";
        for(k = 0; k < segments;k++) {
            if(rowTemplate != "") {
                rowTemplate+="_"
            }
            for (j = 0; j < columns; j++){
                rowTemplate+="e"
            }
        }
        mapData.push(rowTemplate);
    }

    var firstSeatLabel = 1;
        var $cart = $('#selected-seats'),
            $counter = $('#counter'),
            $total = $('#total'),
            sc = $('#seat-map').seatCharts({
                map: mapData,
                seats: {
                    e: {
                        price   : priceData,
                        classes : 'economy-class', //your custom CSS class
                        category: 'Economy Class'
                    }

                },
                naming : {
                    top : false,
                    getLabel : function (character, row, column) {
                        return firstSeatLabel++;
                    },
                },
                legend : {
                    node : $('#legend'),
                    items : [
                        [ 'e', 'available',   'Economy Class'],
                        [ 'f', 'unavailable', 'Booked']
                    ]
                },
                click: function () {
                    if (this.status() == 'available') {
                        //let's create a new <li> which we'll add to the cart items
                        $('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
                            .attr('id', 'cart-item-'+this.settings.id)
                            .data('seatId', this.settings.id)
                            .appendTo($cart);

                        /*
                         * Lets update the counter and total
                         *
                         * .find function will not find the current seat, because it will change its stauts only after return
                         * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
                         */
                        $counter.text(sc.find('selected').length+1);
                        $total.text(recalculateTotal(sc)+this.data().price);

                        return 'selected';
                    } else if (this.status() == 'selected') {
                        //update the counter
                        $counter.text(sc.find('selected').length-1);
                        //and total
                        $total.text(recalculateTotal(sc)-this.data().price);

                        //remove the item from our cart
                        $('#cart-item-'+this.settings.id).remove();

                        //seat has been vacated
                        return 'available';
                    } else if (this.status() == 'unavailable') {
                        //seat has been already booked
                        return 'unavailable';
                    } else {
                        return this.style();
                    }
                }
            });

        //this will handle "[cancel]" link clicks
        $('#selected-seats').on('click', '.cancel-cart-item', function () {
            //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
            sc.get($(this).parents('li:first').data('seatId')).click();
        });

        //let's pretend some seats have already been booked
        //sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');
        for(var k in seatData) {
            if(seatData[k].taken == true || seatData[k].quick == true) {
                var segOffset = Math.floor(seatData[k].c_column/columns)
                sc.get([seatData[k].c_row+1+"_"+(seatData[k].c_column+1+segOffset)]).status('unavailable');
            }
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
}

function recalculateTotal(sc) {
    var total = 0;

    //basically find every selected seat and sum its price
    sc.find('selected').each(function () {
        total += this.data().price;
    });

    return total;
}

