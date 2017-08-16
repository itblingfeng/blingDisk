<%--
  Created by IntelliJ IDEA.
  User: new
  Date: 2017/8/16
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../js/bootstrap-3.3.7/docs/favicon.ico">
    <script src="/js/jquery-1.5.1.min.js"></script>
    <title>登录</title>

    <!-- Bootstrap core CSS -->
    <link href="/js/bootstrap-3.3.7/docs/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/jquery.alerts.css" />
    <script type="text/javascript" src="/js/jquery.alerts.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/js/bootstrap-3.3.7/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="/js/bootstrap-3.3.7/docs/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="/js/bootstrap-3.3.7/docs/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">

    <form class="form-signin" id="formlogin" method="post">
        <h2 class="form-signin-heading">bling网盘登录</h2>
        <label for="inputText" class="sr-only">Username</label>
        <input type="text" id="inputText" class="form-control" name="username" placeholder="Username" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" class="form-control" name="password" placeholder="Password" required>
        <div class="checkbox">
            <label>
                <input type="checkbox" value="remember-me"> 记住我
            </label>
        </div>
        <a class="btn btn-lg btn-primary btn-block" id="login_sub">登录</a>
    </form>

</div> <!-- /container -->


<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/js/bootstrap-3.3.7/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
<script type="text/javascript">
    var redirectUrl = "${redirect}";
    var LOGIN={
        check:function () {
            if(!$("#inputText").val()){
                return false;
            }
            if(!$("#inputPassword").val()){
                return false;
            }
            return true;

        },
        login:function(){
            if(this.check()){
               this.doLogin();
            }
            else{

            }
        },
        doLogin:function() {
            $.post("/user/login", $("#formlogin").serialize(),function(data){
                if (data.status == 200) {
                    jAlert('登录成功！',"提示", function(){
                        if (redirectUrl == "") {
                            location.href = "http://localhost:8080/index";
                        } else {
                            location.href = redirectUrl;
                        }
                    });

                } else {
                    jAlert("登录失败，原因是：" + data.msg,"失败");
                }
            });
        }
    };
    $(function () {
        $("#login_sub").click(function () {
            LOGIN.login();
        });
    });
</script>
</body>
</html>

