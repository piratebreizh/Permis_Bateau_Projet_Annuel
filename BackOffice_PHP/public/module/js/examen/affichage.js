function initTooltip(){
    $('[data-toggle="tooltip"]').tooltip()
}

$(document).ready(function(){
    initTooltip();

    $('.question_link').on("click", function () {
        var tr_question = $(this).closest('tr');
        var id_question = $(tr_question).data("id");

        window.location='/question/afficher?id='+id_question;
    })

    $('.question_modify').on("click", function () {
        var tr_question = $(this).closest('tr');
        var id_question = $(tr_question).data("id");

        window.location='/question/modifier?id='+id_question;
    });

    $('#deleteModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var id_question = button.data('id');
        var modal = $(this);
        modal.find('.question_delete').data("id", id_question);
    })

    $('.question_delete').on("click", function () {
        var id_question = $(this).data("id");

        $.ajax({
            url: '/question/supprimer',
            data: {
                id_question: id_question
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