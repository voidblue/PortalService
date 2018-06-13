$("#signUpSubmit").click(function() {
    console.log(JSON.parse(decodeData(sessionStorage.getItem("token"))).id);
    console.log($("#image").val().split("\\").slice(-1)[0]);
    $.ajax({
        url: './api/user' ,
        contentType: "application/json",

    data:JSON.stringify({
            id : $('#id').val(),
            password : $("#password").val(),
            nickname : $("#nickname").val(),
            imageName : $("#image").val().split("\\").slice(-1)[0]
        }),
        type: 'post',
        success: function (data) {
            $.each(data, function (i, result) {
            })
        },
        error: function (data, textStatus, jqXHR) {
        }
    })
        $("#signUpForm").attr("action", "api/user/image")

        var fileValue = $("#image").val().split("\\");
        var fileName = fileValue[fileValue.length - 1];


    }
)