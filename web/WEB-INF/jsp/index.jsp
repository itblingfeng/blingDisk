<%--c
  Created by IntelliJ IDEA.
  User: new
  Date: 2017/8/16
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/js/bootstrap-3.3.7/docs/favicon.ico">

    <title>bling网盘</title>
    <script src="/js/jquery-1.11.0.min.js" type="text/javascript"></script>
    <!-- Bootstrap core CSS -->
    <link href="/js/bootstrap-3.3.7/docs/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/js/bootstrap-3.3.7/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/js/bootstrap-3.3.7/docs/examples/dashboard/dashboard.css" rel="stylesheet">
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]>
    <script src="/js/bootstrap-3.3.7/docs/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/js/bootstrap-3.3.7/docs/assets/js/ie-emulation-modes-warning.js"></script>
    <script src="/js/fileinput.js" type="text/javascript"></script>
    <link href="/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <script src="/js/fileinput_locale_zh.js" type="text/javascript"/>
    <script src="/js/fileinput_locale_de.js" type="text/javascript"></script>


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <![endif]-->
    <script>
        var isEdit = 0;

        function changeValue(li) {
            fileid = li;
            li = $("#name_" + li)[0];
            if (isEdit++ != 0) {
                return false;
            }
            var oldValue = li.innerHTML;
            li.innerHTML = "<input onblur='confirmChange(this)' value='" + oldValue + "'/>";
        }

        function confirmChange(input) {
            var newValue = input.value;
            var li = input.parentNode;
            li.innerHTML = newValue;
            isEdit = 0;
            $.get("${pageContext.request.contextPath}/disk/edit?id=" + fileid + "&fileName=" + newValue, function (data) {
                if (data.status == 200) {
                    $('#modal_value').html("修改成功");
                    $('#myModal').modal('show');
                } else {
                    $('#modal_value').html(data.msg);
                    $('#myModal').modal('show');
                }

            })
        }
    </script>
    <script>
        $("#file-4").fileinput({
            uploadExtraData: {kvId: '10'}
        });
        $(".btn-warning").on('click', function () {
            if ($('#file-4').attr('disabled')) {
                $('#file-4').fileinput('enable');
            } else {
                $('#file-4').fileinput('disable');
            }
        });
        $(".btn-info").on('click', function () {
            $('#file-4').fileinput('refresh', {previewClass: 'bg-info'});
        });

    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>

    <script>window.jQuery || document.write('<script src="/js/bootstrap-3.3.7/docs/assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="/js/bootstrap-3.3.7/docs/dist/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="/js/bootstrap-3.3.7/docs/assets/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="/js/bootstrap-3.3.7/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
    <script>
        /*function FileUpload(){
            $.ajax({
                url: '/disk/upload',
                type: 'POST',
                cache: false,
                data: new FormData($('#FileInfo')[0]),
                processData: false,
                contentType: false
            }).done(function(res) {
            }).fail(function(res) {});
        };*/
        function uploadFile() {
            if ($(".file-caption-name").html() == null || $(".file-caption-name").html() == "") {
                $('#myModal2').modal('hide');
                $('#modal_value').html("请至少选择一个文件");
                $('#myModal').modal('show');
                return;
            }
            $('#myModal2').modal('hide');
            var $filename = $(".file-caption-name").html();
            $("#newUpload").append("<li><a href='#'>" + $filename + "</a><li/>");
            $.ajax({
                url: '/disk/upload',
                type: 'POST',
                cache: false,
                data: new FormData($('#uploadForm')[0]),
                processData: false,
                contentType: false
            }).done(function (res) {
                $('#modal_value').html("上传成功");
                $('#myModal').modal('show');
                setTimeout('refresh()', 2000);

            }).fail(function (res) {
            });
        };

        function downLoad(url) {
            location.href = "${pageContext.request.contextPath}/downloadFile?url=" + url;
        };

        function refresh() {
            location.reload();
        };
        $(function () {
            $("#allchecked").click(function () {
                if (this.checked) {
                    $("tbody :checkbox").prop("checked", true);
                } else {
                    $("tbody :checkbox").prop("checked", false);
                }
            });

        });

        function deleteFile(url) {
            /*发送一个ajax请求，然后刷新页面即可*/
            $.get("/deleteFile", {"url":url}, function (data) {
                if (data.status == 200) {
                    $('#modal_value').html("文件删除成功");
                    $('#myModal').modal('show');
                    setTimeout('refresh()', 2000);
                }else
                {
                    $('#modal_value').html("文件删除失败:"+data.msg);
                    $('#myModal').modal('show');
                }
            }
        )
            ;
        };

    </script>

</head>

<body>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel2" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel2">提示</h4>
            </div>
            <div class="modal-body" id="modal_value"></div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Bling网盘</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">个人中心 <span class="sr-only">(current)</span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">最近下载 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">最近上传 <span class="caret"></span></a>
                    <ul class="dropdown-menu" id="lastUpload">
                        <li><a href="#">Action</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">正在上传 <span class="caret"></span></a>
                    <ul class="dropdown-menu" id="newUpload">

                    </ul>
                </li>
            </ul>

            <div id="navbar" class="navbar-collapse collapse">

                <ul class="nav navbar-nav navbar-right">
                 <span class="navbar-brand"
                       style="font-size: 14px;"> ${pageContext.session.getAttribute("user").userName},欢迎您使用bling网盘~
           </span>

                </ul>
                <form class="navbar-form navbar-right">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="来找点文件吧ヾ|≧_≦|〃">
                    </div>
                    <button type="submit" class="btn btn-default">搜索</button>
                </form>
            </div>
        </div>
    </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->

</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="${pageContext.request.contextPath}/disk/list">全部文件 <span class="sr-only">(current)</span></a>
                </li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="${pageContext.request.contextPath}/disk/index?typeId=4">xml文件</a></li>
                <li><a href="/disk/index?typeId=5">jar包</a></li>
                <li><a href="/disk/index?typeId=1">zip包</a></li>
                <li><a href="/disk/index?typeId=4">文件夹</a></li>
                <li><a href="/disk/index?typeId=3">mp3</a></li>
                <li><a href="/disk/index?typeId=2">txt</a></li>
            </ul>
        </div>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal2">
                上传文件
            </button>
            &nbsp;
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal2">
                删除
            </button>
            &nbsp;
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal2">
                创建文件夹
            </button>
            <!-- Modal -->
            <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">上传文件</h4>
                        </div>
                        <div class="modal-body">
                            <form id="uploadForm" enctype="multipart/form-data" method="post">
                                <div class="form-group">
                                    <input id="file" type="file" name="file" class="file"/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="uploadFile()">上传</button>
                        </div>
                    </div>
                </div>
            </div>
            <h4 class="sub-header">文件列表</h4>
            <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th>
                            <input type="checkbox" id="allchecked">
                        </th>
                        <th>文件名</th>
                        <th>文件大小</th>
                        <th>文件类型</th>
                        <th>菜单</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${fileList}" var="file">
                        <tr>
                            <td><input type="checkbox"></td>
                            <td id="name_${file.id}">${file.file_name}</td>
                            <td><fmt:formatNumber groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"
                                                  value="${file.file_size / 1024 }"/>M
                            </td>
                            <td>${file.type_name}</td>
                            <td>
                                <div class="dropdown">
                                    <a id="dLabel" data-target="#" href="http://example.com" data-toggle="dropdown"
                                       role="button" aria-haspopup="true" aria-expanded="false">
                                        菜单
                                        <span class="caret"></span>
                                    </a>

                                    <ul class="dropdown-menu" aria-labelledby="dLabel">
                                        <li><a href="javascript:void(0)" onclick="downLoad('${file.file_url}')">下载</a>
                                        </li>
                                        <li onclick="changeValue('${file.id}')"><a href="javascript:void(0)">重命名</a>
                                        </li>
                                        <li><a href="javascript:void(0)" onclick="deleteFile('${file.file_url}')">删除</a>
                                        </li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <nav aria-label="...">
                    <ul class="pagination">
                        <li class="disabled"><a href="#" aria-label="Previous"><span
                                aria-hidden="true">&laquo;</span></a></li>
                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->


</body>
</html>
