/*
只要在这个文件中到日header.html和footer.html就可以让所有的页面都有的header和footer

使用jQuert后台加载页面,在回调函数中将服务器返回的数据显示在div中

使用同步方式加载的好处:先加载完毕header.html之后,才加载footer.html
 */

//在页面加载完毕后执行
$(function () {
    //加载header.html
    $.get({
        url:"header.html",
        async:false,//表示同步加载
        success:function (html) {//返回的就是整个header的内容
            //将所有的HTML内容显示在#header在 div中
            $("#header").html(html);
        }
    });

    //加载footer.html
    $.get({
        url:"footer.html",
        async:false,//表示同步加载
        success:function (html) {//返回的就是整个footer的内容
            //将所有的HTML内容显示在#footer在 div中
            $("#footer").html(html);
        }
    });

})