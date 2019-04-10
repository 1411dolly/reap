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
        this.value = ui.item.label;
        $(this).next("input").val(ui.item.value);
        event.preventDefault();
    }
});

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