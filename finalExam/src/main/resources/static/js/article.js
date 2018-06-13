


$(document).ready(function () {
    console.log("asdasd");
    $.ajax({
        url: './api/article/list',
        contentType: 'application/json',
        type: 'get',
        success: function (data) {
            console.log("성공");
            console.log(data);
            $("#articleArea").html();
            var articles = ''
            $.each(data, function (i, article) {
                articles += '<div class = "article">\
                    <div class = "textbox" id = "title">' + article.title + '</div>\
                    <div class = "textbox" id = "author">' + article.author + '</div>\
                    <div class = "textbox" id = "timeCreated">' + article.timeCreated + '</div>\
                    <img src = "/api/article/image/' +  article.imageName + '">\
                    <div class = "textbox" id = "text">' + article.text + '</div>\
                    </div>'
                $('#articleArea').val()
            })
            $("#articleArea").html(articles);

        },
        error: function (data, textStatus, jqXHR) {
            console.log("에러");
            console.log(data)
            console.log(textStatus)
            console.log(jqXHR)
        }
    })
})