$("#signUpForm").click(function() {
    $.ajax({
        url: './api/user' ,
        contentType: "application/json",
        data:JSON.stringify({
            id : $('#id').val(),
            password : $("#password").val(),
            nickname : $("#nickname").val()
        }),
        type: 'post',
        success: function (data) {
            $.each(data, function (i, result) {
            })
        },
        error: function (data, textStatus, jqXHR) {
        }
    })
        $("#signUpForm").attr("action", "api/user/" + $("#id").val() + "/image")

        var fileValue = $("#image").val().split("\\");
        var fileName = fileValue[fileValue.length - 1];


    }
)