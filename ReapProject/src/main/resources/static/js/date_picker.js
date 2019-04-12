$(function() {
    $('img[name="dates"]').daterangepicker({
        opens: 'left'
    }, function(start, end, label) {
        $.ajax({
            url: "/setSessionDates",
            type: "POST",
            data: {
                start : start.format('YYYY-MM-DD'),
                end : end.format('YYYY-MM-DD')
            },
            success: function (data) {
                location.reload();
            }
        });
    });
});