var totalpoint = 0;
var userpoint = 0;
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
    userpoint = $(userpointstotal).text();
    console.log(userpoint);
});
var populateitems = function (x) {
    var sipper = $.ajax({
        url: "/items",
        method: "GET",
        data: {
            itemId: x
        }
    });
    sipper.done(function (data) {
        if (data.itemValue <= userpoint) {
            userpoint = userpoint - data.itemValue;
            var s = "<option value='1'>1</option>";
            for (var i = 2; i <= data.quantity; i++) {
                s += "<option value='" + i + "'>" + i + "</option>";
            }
            $(".populate").append("<div class='row'>"
                + "<div class='col-sm-2'><img class='itemimg' src='" + data.imageSource + "'/></div>"
                + "<div class='col-sm-4'>" + data.itemName + "</div>"
                // +"<div class='col-sm-2'><select class='allitem'>"
                // +s
                // +"</select></div>"
                + "<div class='col-sm-2 itemvalue' >" + data.itemValue + "</div>"
                + "<span class='col-sm-2 close' " + " id='" + data.id + "'" + ">&times;</span></div>");

            totalpoint += data.itemValue;
            $(".subtotal").text(totalpoint);
        } else alert("not enough points!!!!")
    });
    sipper.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching item");
    });
};
var deleteitems = function (y) {
    var iteem = $.ajax({
        url: "/items",
        method: "GET",
        data: {
            itemId: y
        }
    });
    iteem.done(function (datay) {
        var cur = document.getElementById(datay.id);
        cur.parentElement.remove();
        userpoint = userpoint + datay.itemValue;
        console.log("userpoint::" + userpoint);
        totalpoint = totalpoint - datay.itemValue;
        console.log("totalpoint::" + totalpoint);
        $(".subtotal").text(totalpoint);
    });
    iteem.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching item");
    });
};
