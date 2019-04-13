$("#searchNewer").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "http://localhost:8080/getUserListActive",
            type: "GET",
            data: {
                term: request.term,
                user_id: $("#user-id").val()
            },
            success: function (data) {
                response($.map(data, function (el) {
                    return {
                        label: el.nameEmail,
                        value: el.email
                    };
                }));
            }
        });
    },
    select: function (event, ui) {
        this.value = ui.item.label;
        $(this).next("input").val(ui.item.value);
        event.preventDefault();
    }
});

// $(document).ready(function () {
//     $(".col-md-6").click(function (e) {
//         e.preventDefault();
//         $.ajax({
//             type: 'GET',
//             url: '/logout',
//             success: function (data) {
//                 window.location.reload();
//             },
//             error: function () {
//                 console.log("Logout failed")
//             }
//         })
//     })
// })

$(".table-dropdown-role").change(function(){
    var x = this.value;
    var y = $(this).parent().parent().attr('id');
    $.ajax({
        url: "/updateUserRole",
        type: "POST",
        data:{
            role : x,
            userId : y
        },
        success: function (data) {
            location.reload();
        }
    });
});

$(".isAdmin-checkbox").change(function(){
    var x;
    var y = $(this).parent().parent().parent().attr('id');
    if ($(this).is(":checked"))
    {
     x="true";
    }else{
        x="false";
    }
    $.ajax({
        url: "/updateAdminRole",
        type: "POST",
        data:{
            isAdmin : x,
            userId : y
        },
        success: function (data) {
            location.reload();
        }
    });
});