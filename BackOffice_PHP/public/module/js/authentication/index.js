$(document).ready(function () {
    $('#btn-login').on("click", login);
    $(document).keypress(function(e) {
        if(e.which == 13) {
            login();
        }else{
            $("#login-alert").parent().fadeOut();
        }
    });
});

function login() {

    var $btn = $('#btn-login').button('loading');
    $.ajax({
        url: '/authentication/login',
        data: {
            username: $("#login-username").val(),
            password: $("#login-password").val()
        },
        dataType: "json",
        type: 'POST',
        success: function (response) {
            if (response.is_connected){
                window.location = response.url_redirect;
            }else if(response.msgError != ""){
                $("#login-alert").html(response.msgError)
                    .parent().fadeIn()
                    .find("button.close").on("click", function(){$(this).parent().fadeOut()});
                $btn.button('reset');
            }
        }
    });
}