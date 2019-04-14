var totalpoint = 0;
var userpoint = 0;
var globalcost = 0;
$(document).on('click', '.sipperbutton', function () {
    var item = this.id;
    populateitems(item);
    // $(this).attr("disabled", true);
});
$(function () {
    $('body').on('click', '.close', function () {
        var id = this.id;
        deleteitems(id);
    });
});
$(function () {
    $('body').on('click', '.redeempointsbutton', function () {
        console.log("clicked");
        if (totalpoint == 0)
            alert("Nothing in bag!!! Add some items");
        else alert("Purchase successful!!!")
    })
});
$(function () {
    userpoint = $(userpointstotal).text();
    console.log(userpoint);
});
var populateitems = function (x) {
    var sipper = $.ajax({
        url: "/additems",
        method: "GET",
        data: {
            itemId: x
        }
    });
    sipper.done(function (data) {
        if (data.itemValue <= userpoint) {
            userpoint = userpoint - data.itemValue;
            var selectedval, cost;
            var s = "<option value='1' class='optionval'>1</option>";
            for (var i = 2; i <= data.quantity; i++) {
                s += "<option value='" + i + "' class='optionval'>" + i + "</option>";
            }
            $(".populate").append("<div class='row '>"
                + "<div class='col-sm-2'><img class='itemimg' src='" + data.imageSource + "'/></div>"
                + "<div class='col-sm-4'>" + data.itemName + "</div>"
                // +"<div class='col-sm-2 dropdownval'><select class='allitem'>"
                // +s
                // +"</select></div>"
                + "<div class='col-sm-2 itemvalue quantityval' " + " id='q" + data.id + "'" + ">" + data.itemValue + "</div>"
                + "<span class='col-sm-2 close' " + " id='" + data.id + "'" + ">&times;</span></div>");

            // $('.allitem').on('change', function () {
            //     selectedval = $(this).val();
            //     cost = selectedval * data.itemValue;
            //     ($(this).parent('.dropdownval').parent('.row').children('.quantityval')).each(function (elem) {
            //         $(this).html(cost);
            //     });
            // });
            totalpoint = totalpoint + data.itemValue;
            $(".subtotal").text(totalpoint);

        } else alert("Not enough points!!!!")
    });
    sipper.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching item");
    });
};
var deleteitems = function (y) {
    var iteem = $.ajax({
        url: "/deleteitems",
        method: "GET",
        data: {
            itemId: y
        }
    });
    iteem.done(function (datay) {
        var cur = document.getElementById(datay.id);
        cur.parentElement.remove();
        userpoint = userpoint + datay.itemValue;
        totalpoint = totalpoint - datay.itemValue;
        $(".subtotal").text(totalpoint);
    });
    iteem.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching item");
    });
};
