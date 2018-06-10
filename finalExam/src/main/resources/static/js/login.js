console.log(sessionStorage.getItem("token"));

if (sessionStorage.getItem("token") === null) {
    $("#loginArea").html(
        "<div id=\"signUpForm\">\
            <h1 class=\"loginTitle\">로그인</h1>\
            <input id = \"id\" type=\"text\" class=\"loginInput\" placeholder=\"아이디\" autofocus required>\
            <input id = \"password\" type=\"password\" class=\"loginInput\" placeholder=\"비밀번호\" required>\
            <div id = \"loginSubmit\"  class=\"submitButton\">로그인</div>\
            <a href=\"signUp.html\">\
            <div id = \"signUp\" class=\"submitButton\" href=\"signUp.html\">회원가입</div>\
            </a>\
            </div>")
}
else {
    var tokenvalues = getTokenValueDic(sessionStorage.getItem("token"))
    $("#loginArea").html("<div id=\"signUpForm\">\
    <img src = \"/api/user/" + tokenvalues['id'] + "image.jpg\">\
    <h3>" + tokenvalues['nickname'] + "님 환영합니다.</h3>\
    <div id = \"logout\"  class=\"submitButton\">로그아웃</div>")
}


$("#loginSubmit").click(function() {
        $.ajax({
            url: './api/auth/login',
            contentType: 'application/json',
            data:JSON.stringify({
                id : $("#id").val(),
                password : $("#password").val()
            }),
            type: 'post',
            success: function (data) {
                sessionStorage.setItem("token", data)
                window.location.reload();
                dic = getTokenValueDic(data);
                console.log(dic['nickname']);
            },
            error:function (data, textStatus, jqXHR) {
                console.log("click")
                console.log(data);
                console.log(textStatus);
                console.log(jqXHR);
            }

        })

    }

)

$("#logout").click(function() {
    sessionStorage.clear();
    window.location.reload();
}
)




function getTokenValueDic(token)
{
    input = token.split('.')[1];
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    var output = "";
    var chr1, chr2, chr3;
    var enc1, enc2, enc3, enc4;
    var i = 0;

    input = input.replace(/[^A-Za-z0-9+/=]/g, "");

    while (i < input.length)
    {
        enc1 = this._keyStr.indexOf(input.charAt(i++));
        enc2 = this._keyStr.indexOf(input.charAt(i++));
        enc3 = this._keyStr.indexOf(input.charAt(i++));
        enc4 = this._keyStr.indexOf(input.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);

        if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
        }
    }
    console.log(output)
    temp = output.split(":")[1];
    temp = temp.split("}")[0];
    temp = temp.split("\"")[1];
    list = temp.split(",");
    dic = {};
    for(var i = 0; i < list.length ; i++)
    {
        var keyAndValue = list[i].split('=');
        dic[keyAndValue[0]] = keyAndValue[1]
    }
    return dic;
}

