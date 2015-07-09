function initTooltip() {
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function () {
    initTooltip();
    $('.examen_link').on('click', function(){
        var tr_examen = $(this).closest(".tr_examen");
        var id_examen = $(tr_examen).data("id");

        window.location.href = '/examen/afficher?id='+id_examen;
    });

    $('#publishExamenModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var id = button.data('id');
        var modal = $(this);
        modal.find('.examen_publish').data("id", id);
        var nom_examen = $(".tr_examen[data-id='"+id+"'] .nom_examen").html();
        modal.find('.nom_examen').html(nom_examen);
    });

    $('#deleteExamenModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var id = button.data('id');
        var modal = $(this);
        modal.find('.examen_delete').data("id", id);
        var nom_examen = $(".tr_examen[data-id='"+id+"'] .nom_examen").html();
        modal.find('.nom_examen').html(nom_examen);
    });

    $('.examen_publish').on('click', function () {
        var id_examen = $(this).data("id");
        var tr_examen = $('.tr_examen[data-id='+id_examen+']');
        var span = $(tr_examen).find(".is_published > span");
        var publish_link = $(tr_examen).find('.publish_link');
        $.ajax({
            url: '/examen/publier',
            data: {
                id_examen: id_examen
            },
            type: 'POST',
            success: function () {
                $(span).removeClass('glyphicon-eye-close');
                $(span).addClass('glyphicon-eye-open');
                $(span).attr("title", "Publié");
                $(span).tooltip('destroy');
                $(publish_link).remove();
                initTooltip();
                $('#publishExamenModal').modal('hide');
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
            success: function (response) {
                if (response.is_deleted)
                    $('.tr_examen[data-id='+id_examen+']').remove();
                $('#deleteExamenModal').modal('hide');
            }
        })
    });

    $('.cours_link').on('click', function(){
        var tr_cours = $(this).closest(".tr_cours");
        var id_cours = $(tr_cours).data("id");

        window.location.href = '/cours/afficher?id='+id_cours;
    });

    $('#deleteCoursModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var id = button.data('id');
        var modal = $(this);
        modal.find('.cours_delete').data("id", id);
    });


    $('.cours_delete').on('click', function () {
        var id_cours = $(this).data("id");
        $.ajax({
            url: '/cours/supprimer',
            data: {
                id_cours: id_cours
            },
            dataType: "json",
            type: 'POST',
            success: function (response) {
                if (response.is_deleted)
                    $('.tr_cours[data-id='+id_cours+']').remove();
                $('#deleteCoursModal').modal('hide');
            }
        })
    });

    $('.add_examen').on("click", function () {
        $('#addExamenModal form').submit();
    });

    $('#addExamenModal').on('hidden.bs.modal', function (e) {
        $('#addExamenModal form')[0].reset();
    });

    $('#addExamenModal').on('shown.bs.modal', function (e) {
        $('#addExamenModal input.form-control')[0].focus();
    });
});