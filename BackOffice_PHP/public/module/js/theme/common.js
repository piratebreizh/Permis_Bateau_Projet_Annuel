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

    $('.theme_delete').on('click', function () {
        var div_theme = $(this).parent().parent().parent().parent();
        var id_theme = $(div_theme).data("id");
        $.ajax({
            url: '/theme/supprimer',
            data: {
                id_theme: id_theme
            },
            dataType: "json",
            type: 'POST',
            complete: function (response) {
                if (response.responseJSON.is_deleted)
                    $(div_theme).remove();
            }
        })
    });

    $('.examen_publication').on('click', function () {
        var div_examen = $(this).parent().parent().parent().parent();
        var id_examen = $(div_examen).data("id");
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
        var div_examen = $(this).parent().parent().parent().parent();
        var id_examen = $(div_examen).data("id");
        $.ajax({
            url: '/examen/supprimer',
            data: {
                id_examen: id_examen
            },
            dataType: "json",
            type: 'POST',
            complete: function (response) {
                if (response.responseJSON.is_deleted)
                    $(div_examen).remove();
            }
        })
    });
});