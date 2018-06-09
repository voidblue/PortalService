$("#loginSubmit").click(function() {
    console.log("click")
        $.ajax({
            url: './api/user/' + $("id") , //TODO 나중에 변수로 받아올것!
            dataType: 'application/json',
            type: 'get',
            success: function (data) {
                $.each(data, function (i, user) {

                    console.log(user.nickname)
                })
            }
        })
    }
)