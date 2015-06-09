function initTooltip(){
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function(){
    initTooltip();

    $('.publication').on('click', function(){
        var span = $(this);
        $.ajax({
            url: '/examen/publier',
            data:{
                id_examen: $(this).data("examen")
            },
            type: 'POST',
            complete: function(response){
                $(span).removeClass('glyphicon-eye-close');
                $(span).addClass('glyphicon-eye-open');
                $(span).attr("title", "Publié");
                $(span).unbind("click");
                $(span).tooltip('destroy')
                initTooltip();
            }
        })
    });

    $('.delete').on('click', function(){
        $.ajax({
            url: '/examen/supprimer',
            data:{
                id_examen: $(this).data("examen")
            },
            dataType: "json",
            type: 'POST',
            complete: function(response){
                if(response.responseJSON.URL)
                    window.location.href = response.responseJSON.URL;
            }
        })
    });
});