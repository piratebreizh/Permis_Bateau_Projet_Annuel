function initTooltip(){
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function(){
    initTooltip();

    $('.publication').on('click', function(){
        var span = $(this);
        $.ajax({
            url: '/theme/publier',
            data:{
                id_theme: $(this).data("theme")
            },
            type: 'POST',
            complete: function(response){
                $(span).removeClass('glyphicon-eye-close');
                $(span).addClass('glyphicon-eye-open');
                $(span).attr("title", "Publié");
                $(span).tooltip('destroy')
                $(span).unbind("click");
                initTooltip();
            }
        })
    });

    $('.delete').on('click', function(){
        $.ajax({
            url: '/theme/supprimer',
            data:{
                id_theme: $(this).data("theme")
            },
            dataType: "json",
            type: 'POST',
            complete: function(response){
                console.log(response.responseJSON.URL);
                if(response.responseJSON.URL)
                    window.location.href = response.responseJSON.URL;
            }
        })
    });
});