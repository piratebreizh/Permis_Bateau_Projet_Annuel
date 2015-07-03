function initTooltip() {
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function () {
    initTooltip();

    $('.theme_link').on('click', function () {
        var tr_theme = $(this).closest(".tr_theme");
        var id_theme = $(tr_theme).data("id");

        window.location = '/theme/afficher?id='+id_theme;
    });

    $('.examens_blancs_link').on('click', function () {
        window.location.href = '/theme/listerExamensBlanc';
    });

    $('.theme_publication').on('click', function () {
        var tr_theme = $(this).closest(".tr_theme");
        var id_theme = $(tr_theme).data("id");
        var span = $(tr_theme).find(".is_published > span");
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
        var nom_theme = $(".tr_theme[data-id='"+id+"'] .nom_theme").html();
        modal.find('.nom_theme').html(nom_theme);
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

    $('.add_theme').on("click", function () {
        $('#addThemeModal form').submit();
    });

    $('#addThemeModal').on('hidden.bs.modal', function (e) {
        $('#addThemeModal form')[0].reset();
    });

    $('#addThemeModal').on('shown.bs.modal', function (e) {
        $('#addThemeModal input.form-control')[0].focus();
    });
});