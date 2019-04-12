$("#searchNewer").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "http://localhost:8080/getUserListActive",
            type: "GET",
            data: {
                term : request.term,
                user_id : $("#user-id").val()
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
        this.value = ui.redeem.label;
        $(this).next("input").val(ui.redeem.value);
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









// var $form = $('#recognise_form');
// $form.on('submit', function(e) {
//     e.preventDefault();
//     $.ajax({
//         url: $form.attr('action'),
//         type: 'post',
//         data: $form.serialize(),
//         success: function(data)
//         {
//             alert("recognized");
//         }
//     });
//
//     return false;
// });


$("#searchNewer3").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "http://localhost:8080/getUserListActive",
            type: "GET",
            data: {
                term : request.term,
                user_id : $("#user-id").val()
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

//for admin management
/*
$("#searchNewer4").autocomplete({
    source: function (request, response) {
        $.ajax({
            url: "http://localhost:8080/getUserListActive",
            type: "GET",
            data: {
                term : request.term,
                user_id : $("#user-id").val()
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
*/
