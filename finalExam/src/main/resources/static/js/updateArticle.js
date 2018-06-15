$("#submit").click(function() {
    var imageName = $("#image").val().split("\\").slice(-1)[0]
    if($("#image").val().split("\\").slice(-1)[0] == ''){
        imageName = $("#image").attr("value")
    }
    console.log(imageName)
    $.ajax({
            url: '/api/article' ,
            contentType: "application/json",
            headers : {token:sessionStorage.getItem("token")},
            data:JSON.stringify({
                id : self.articleId,
                title : $('#title').val(),
                text : $("#text").val(),
                author : JSON.parse(decodeData(sessionStorage.getItem("token"))).id,
                imageName : imageName
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
        if ($("#image").length < 3){
            document.close()
        }else{
            $("#updateArticleForm").attr("action", "api/article/image")
            $("#updateArticleForm").submit()
        }
    }
)


$("#cancel").click(function () {
    self.close()
})






function decodeData(token) {

    str = token.split(".")[1];
    var enc64List, dec64List;

    enc64List = new Array();
    dec64List = new Array();
    var i;
    for (i = 0; i < 26; i++) {
        enc64List[enc64List.length] = String.fromCharCode(65 + i);
    }
    for (i = 0; i < 26; i++) {
        enc64List[enc64List.length] = String.fromCharCode(97 + i);
    }
    for (i = 0; i < 10; i++) {
        enc64List[enc64List.length] = String.fromCharCode(48 + i);
    }
    enc64List[enc64List.length] = "+";
    enc64List[enc64List.length] = "/";
    for (i = 0; i < 128; i++) {
        dec64List[dec64List.length] = -1;
    }
    for (i = 0; i < 64; i++) {
        dec64List[enc64List[i].charCodeAt(0)] = i;
    }

    var c=0, d=0, e=0, f=0, i=0, n=0;
    var input = str.split("");
    var output = "";
    var ptr = 0;
    do {
        f = input[ptr++].charCodeAt(0);
        i = dec64List[f];
        if ( f >= 0 && f < 128 && i != -1 ) {
            if ( n % 4 == 0 ) {
                c = i << 2;
            } else if ( n % 4 == 1 ) {
                c = c | ( i >> 4 );
                d = ( i & 0x0000000F ) << 4;
            } else if ( n % 4 == 2 ) {
                d = d | ( i >> 2 );
                e = ( i & 0x00000003 ) << 6;
            } else {
                e = e | i;
            }
            n++;
            if ( n % 4 == 0 ) {
                output += String.fromCharCode(c) +
                    String.fromCharCode(d) +
                    String.fromCharCode(e);
            }
        }
    }
    while (typeof input[ptr] != "undefined");
    output += (n % 4 == 3) ? String.fromCharCode(c) + String.fromCharCode(d) :
        ((n % 4 == 2) ? String.fromCharCode(c) : "");
    return output;
}

