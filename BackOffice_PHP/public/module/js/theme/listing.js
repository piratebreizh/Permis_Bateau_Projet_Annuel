function initTooltip() {
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function () {
    initTooltip();

    $('.theme_publication').on('click', function () {
        var div_theme = $(this).parent().parent().parent().parent();
        var id_theme = $(div_theme).data("id");
        var span = $(div_theme).find(".is_published > span");
        var link_menu = $(this).parent();
        $.ajax({
            url: '/theme/publier',
            data: {
                id_theme: id_theme
            },
            type: 'POST',
            success: function (response) {
                $(span).removeClass('glyphicon-eye-close');
                $(span).addClass('glyphicon-eye-open');
                $(span).attr("title", "Publié");
                $(span).tooltip('destroy');
                $(link_menu).remove();
                initTooltip();
            }
        })
    });

    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id = button.data('id');
        var modal = $(this);
        modal.find('.theme_delete').data("id", id);
    })

    $('.theme_delete').on('click', function () {
        var id_theme = $(this).data("id");
        $.ajax({
            url: '/theme/supprimer',
            data: {
                id_theme: id_theme
            },
            dataType: "json",
            type: 'POST',
            complete: function (response) {
                if (response.responseJSON.is_deleted){
                    window.location.reload();
                }
                $('#deleteModal').modal('hide');
            }
        })
    });
});