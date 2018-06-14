$("#submit").click(function() {

        $.ajax({
            url: '/api/article' ,
            contentType: "application/json",
            headers : {token:sessionStorage.getItem("token")},
            data:JSON.stringify({
                title : $('#title').val(),
                text : $("#text").val(),
                author : JSON.parse(decodeData(sessionStorage.getItem("token"))).id,
                imageName : $("#image").val().split("\\").slice(-1)[0]
            }),
            type: 'put',
            success: function (data) {
                console.log(data)
                $.each(data, function (i, result) {
                })
            },
            error: function (data, textStatus, jqXHR) {
                alert(data.responseJSON.message)
            }
        })
        // if ($("#image").val() == null){
        // }else{
            $("#updateArticleForm").attr("action", "api/article/image")
        // }
    }
)
