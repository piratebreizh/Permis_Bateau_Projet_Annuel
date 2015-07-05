$(document).ready(function () {
    $('#btn-login').on("click", login);
});

function login() {
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
            }
        }
    });
}