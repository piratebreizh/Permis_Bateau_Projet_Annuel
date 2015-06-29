function initTooltip() {
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function () {
    initTooltip();
    $('#deleteExamenModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id = button.data('id');
        var modal = $(this);
        modal.find('.examen_delete').data("id", id);
    })

    $('.examen_publication').on('click', function () {
        var div_examen = $(this).parent().parent().parent().parent();
        var id_examen = $(this).data("id");
        var span = $(div_examen).find(".is_published > span");
        var link_menu = $(this).parent();
        $.ajax({
            url: '/examen/publier',
            data: {
                id_examen: id_examen
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

    $('.examen_delete').on('click', function () {
        var id_examen = $(this).data("id");
        $.ajax({
            url: '/examen/supprimer',
            data: {
                id_examen: id_examen
            },
            dataType: "json",
            type: 'POST',
            complete: function (response) {
                if (response.responseJSON.is_deleted)
                    $('.div_examen[data-id='+id_examen+']').remove();
                $('#deleteExamenModal').modal('hide');
            }
        })
    });
});