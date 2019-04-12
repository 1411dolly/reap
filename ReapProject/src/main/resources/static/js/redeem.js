$(document).on('click','.sipperbutton',function () {
    populateitemsipper();
    console.log("button clicked!!!!");

});
var populateitemsipper=function () {
    var sipper= $.ajax({
        url:"/itemsipper",
        method:"GET"
    });
    sipper.done(function (data) {
        console.log(data);
        // var s="<option value='1'>1</option>";
        for(var i=1;i<=data.quantity;i++)
        {
            s+="<option value='"+i+"'>"+i+"</option>";
        }
        $(".populate").html("<div class='row listredeem'>"
            +"<div class='col-sm-2'><img class='itemimg' src='"+data.imageSource+"'/></div>"
            + "<div class='col-sm-4'>"+data.itemName+"</div>"
            +"<div class='col-sm-2'><select>"
            +s
            +"</select></div>"
            +"<div class='col-sm-3'>"+data.itemValue+"</div><span class=\"col-sm-2 close\">&times;</span></div>");
    });
    sipper.fail(function (jqXHR, textStatus) {
        console.log("Error in fetching item");
    })
};

$(document).on('click','.close',function () {
    $(".populate").remove();
    console.log("removed");
});
