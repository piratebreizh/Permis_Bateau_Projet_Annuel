$(document).ready(function(){
    $('#btn_submit_form').on('click', function () {
        $('#add_an_other').val('0');
        validateForm();
    });
    $('#btn_submit_form_and_add').on('click', function () {
        $('#add_an_other').val('1');
        validateForm();
    });

    document.getElementById('image').addEventListener('change', handleFileSelect, false);
});

function validateForm(){
    if(isNbResponsesCorrect() && isCheckboxCorrect()) {
        $('#form_question_creation').validator().submit();
    }
}


function isCheckboxCorrect(){
    var is_correct_A = $('#is_correct_A')[0].checked;
    var is_correct_B = $('#is_correct_B')[0].checked;
    var is_correct_C = $('#is_correct_C')[0].checked;
    var is_correct_D = $('#is_correct_D')[0].checked;
    if (
        (is_correct_A == false && is_correct_B == false && is_correct_C == false && is_correct_D == false)
        || (isTwoResponses() && ((is_correct_A == false && is_correct_B == false) || is_correct_C == true && is_correct_D == true))
    ) {
        $('#error_checkbox').show();
        return false;
    }
    else {
        $('#error_checkbox').hide();
        return true;
    }
}

function isNbResponsesCorrect(){
    if(!isTwoResponses() && !isFourResponses()){
        $('#error_nb_responses').show();
        return false;
    }else{
        $('#error_nb_responses').hide();
        return true;
    }
}

function isTwoResponses() {
    var $enonce_A = $('#enonce_A');
    var $enonce_B = $('#enonce_B');
    var $enonce_C = $('#enonce_C');
    var $enonce_D = $('#enonce_D');
    return $enonce_A.val() != ""
        && $enonce_B.val() != ""
        && $enonce_C.val() == ""
        && $enonce_D.val() == "";
}

function isNbResponsesIncorrect() {
    var $enonce_C = $('#enonce_C');
    var $enonce_D = $('#enonce_D');
    return

    ($enonce_C.val() != "" && $enonce_D.val() == "") || ($enonce_C.val() == "" && $enonce_D.val() != "") ;
}

function isFourResponses() {
    var $enonce_A = $('#enonce_A');
    var $enonce_B = $('#enonce_B');
    var $enonce_C = $('#enonce_C');
    var $enonce_D = $('#enonce_D');
    return $enonce_A.val() != ""
        && $enonce_B.val() != ""
        && $enonce_C.val() != ""
        && $enonce_D.val() != "";
}

function handleFileSelect(evt) {
    var file = evt.target.files[0];

    // Only process image files
    if (!file.type.match('image.*')) {
        return;
    }

    var reader = new FileReader();
    reader.onload = (function (theFile) {
        return function (e) {
            // Render thumbnail.
            var span = document.createElement('span');
            span.innerHTML = ['<img style="height: 75px;" src="', e.target.result, '"/>'].join('');
            $('#div_image').html(span);
        };
    })(file);

    // Read in the image file as a data URL
    reader.readAsDataURL(file);
}