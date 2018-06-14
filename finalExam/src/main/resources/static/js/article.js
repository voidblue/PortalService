$(document).ready(function () {
    console.log("asdasd");
    $.ajax({
        url: '/api/article/list?page=1',
        contentType: 'application/json',
        type: 'get',
        success: function (data) {
            console.log("성공");
            console.log(data);
            $("#articleArea").html();
            var articles = ''
            $.each(data.content, function (i, article) {
                articles += '<div class = "article">\
                    <div class = "textbox" id = "title">' + article.title + '</div>\
                    <div class = "textbox" id = "author">' + article.author + '</div>\
                    <div class = "textbox" id = "timeCreated">' + article.timeCreated + '</div>\
                    <button class = "rightbtn" id = "update" onclick = "updateArticle('+article.id+')">수정</button>\
                    <button class =     "rightbtn" id = "delete" onclick = "deleteArticle('+article.id+')">삭제</button>\
                    <div class = "floatPadding"></div>\
                    <img class="articleImg center" src = "/api/article/image/' +  article.imageName + '">\
                    <div class = "textbox" id = "text">' + article.text + '</div>\
                    <div class = "CommentArea">\
                        <input type="textArea" class ="newComment" id = "commentText' + i + '">\
                        <input type="hidden" value="' + article.id + '">\
                        <button class = "rightbtn"  onclick = "newComment('+article.id +', '+ i +')">댓글쓰기</button>\
                                            <div class = "floatPadding"></div>\
                        <div id = "commentWrapper'+i+'">\
                            <button class = "centerbtn center" onclick = "showComment('+article.id+', '+ i +')">댓글보기</button>\
                        </div>\
                    </div>\
                    </div>'
            })
            $("#articleArea").html(articles);

        },
        error: function (data, textStatus, jqXHR) {
            alert(data.responseJSON.message)
        }
    })
})


function  newComment(articleId, textNum){
    console.log(articleId)
    if(sessionStorage.getItem("token") != null) {
        $.ajax({
            url: '/api/comment',
            contentType: 'application/json',
            type: 'post',
            headers: {token: sessionStorage.getItem("token")},
            data: JSON.stringify({
                article: articleId,
                text: $("#commentText"+textNum).val(),
                author: JSON.parse(decodeData(sessionStorage.getItem("token"))).id
            }),

            success: function (data) {
                alert("등록되었습니다.")

            },
            error: function (data, textStatus, jqXHR) {
                alert(data.responseJSON.message)
            }
        })
    }
}






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



function  showComment(articleNum, i){
    var comments = '';
    $.ajax({
        url: '/api/comment/list?article='+articleNum,
        contentType: 'application/json',
        type: 'get',
        header: {token: sessionStorage.getItem("token")},
        success: function (data) {

            $.each(data, function (j, comment) {
                    $.ajax({
                        url: '/api/user/' + comment.author,
                        contentType: 'application/json',
                        type: 'get',
                        success:function(author) {
                            console.log(author)
                            comments += '<img class ="commentImg" src ="api/user/image/' + author.imageName + '">\
                            <div class="profile">\
                                <div class="nickname">'+author.nickname+'</div>\
                                <div class="commentText"> ' + comment.text + '</div>\
                             </div>\
                            <button class = "rightbtn" id = "update" onclick = "updateComment('+comment.id+')">수정</button>\
                            <button class = "rightbtn" id = "delete" onclick = "deleteComment('+comment.id+')">삭제</button>\
                            <p class="floatPadding"></p>'

                            console.log(comments)
                            $("#commentWrapper" + i).html(comments)
                        }
                    })


            })
        },
        error: function (data, textStatus, jqXHR) {
            alert(data.responseJSON.message)
        }
    })
}





function deleteArticle(articleId) {
    $.ajax({
        url: '/api/article/' + articleId,
        contentType: 'application/json',
        type: 'delete',
        headers: {token: sessionStorage.getItem("token")},

        success: function (data) {
            console.log(data)
            // window.location.reload()
        },
        error: function (data, textStatus, jqXHR) {
            alert(data.responseJSON.message)
        }
    })

}



function updateArticle(articleId) {
    window.name = "parentForm";
    // window.open("open할 window", "자식창 이름", "팝업창 옵션");
    updateArticle = window.open("updateArticle.html",
        "childForm", "width=570, height=600 , resizable = no, scrollbars = no");

    $.ajax({
        url: '/api/article/' + articleId,
        contentType: 'application/json',
        type: 'get',
        success: function (data) {
            $(updateArticle.document).find("#title").val(data.title);
            $(updateArticle.document).find("#text").val(data.text);

        },
        error: function (data, textStatus, jqXHR) {
            alert(data.responseJSON.message)
        }
    })

    $(updateArticle.document).find("#actionButton").click(function () {
        updateArticle.close();
    })
}


function deleteComment(commentId) {
    $.ajax({
        url: '/api/comment/' + commentId,
        contentType: 'application/json',
        type: 'delete',
        headers: {token: sessionStorage.getItem("token")},

        success: function (data) {
            console.log(data)
            window.location.reload()
        },
        error: function (data, textStatus, jqXHR) {
            alert(data.responseJSON.message)
        }
    })

}



function updateComment(commentId) {
    $.ajax({
        url: '/api/comment/' + commentId,
        contentType: 'application/json',
        type: 'delete',
        headers: {token: sessionStorage.getItem("token")},

        success: function (data) {
            console.log(data)
        },
        error: function (data, textStatus, jqXHR) {
            alert(data.responseJSON.message)
        }
    })

}
