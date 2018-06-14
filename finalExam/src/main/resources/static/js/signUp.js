$("#signUpSubmit").click(function() {
    if($("#password").val() === $("#passwordAcception").val()){
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
                alert(data.responseJSON.message)
            }
        })

            $("#signUpSubmit").attr("type", "submit")
            $("#signUpForm").attr("action", "api/user/image")
            window.location("/")
        }else{

        $("#signUpSubmit").attr("type", "button")
        $("#signUpForm").attr("onsubmit", "return false;")
        alert("비빌번호가 다릅니다.")
    }
    }
)