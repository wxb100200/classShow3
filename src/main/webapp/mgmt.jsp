<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Date,com.hz.school.controller.MgmtResource" %>


<%
    boolean allow = MgmtResource.checkToken( request.getParameter("p") );
    System.out.println("p:" + request.getParameter("p") + " -> " + allow );
    if(!allow){
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Management Console V2</title>
<script src="javascripts/plugins/jquery-1.4.2.min.js" type="text/javascript"></script>
<script>
    <%
    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmm");
    %>
    var now = <%=format.format(new java.util.Date())%>;

function auth(){
    $.ajax({type:"get",url:"https://local.sh.chinamobile.com:10086/encrypt?text=" + now, dataType:"jsonp",success:doAuth});
}

function doAuth(json) {
    var p = json.result;
    var token = now + "x" + p;
    document.cookie="_tk=" + token;
    console.log(token);
    $("#p").val(token);
    $("#form").submit();
}
</script>
</head>
<body>
Empty Page
<input type="button" onclick="auth()" value="JSONP测试" /><BR/><BR/><BR/>
<form id="form" action="mgmt.jsp" method="POST">
  <input type="hidden" name="p" id="p" value="" />
</form>
</body>
</html>
<%
        return;  // 短路。jsp会被编译成java的一个函数,return 就会被退出当前函数。下面的页面也就不会被输出了。
    }
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Management Console V2</title>
<script src="javascripts/plugins/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="javascripts/plugins/jquery.form.js" type="text/javascript"></script>
<style type="text/css">
    html,body{height:100%}
    html{overflow-y:auto}

    button {
        BACKGROUND: #fff;
        cursor: pointer;
        FONT-SIZE: 12pt;
        HEIGHT: 24px;

        border-top:1px solid #708090;
        border-right:1px solid #708090;
        border-bottom:1px solid #708090;
        border-left:1px solid #708090;
    }

    a {
        text-decoration:none;
    }

    a.button {
        BACKGROUND: #fff;
        cursor: pointer;
        FONT-SIZE: 12pt;
        HEIGHT: 24px;
        padding: 4px 8px 1px 8px;
        text-decoration:none;

        border-top:1px solid #708090;
        border-right:1px solid #708090;
        border-bottom:1px solid #708090;
        border-left:1px solid #708090;
    }

    select.rect {
       background: transparent;
       padding: 2px;
       font-size: 14px;
       border: 1px solid #ccc;
       -webkit-appearance: none; /*for chrome*/
    }

    span.host {
        cursor: pointer;
    }
    span.host:hover{
        color:blue;
    }

    button:hover {
        color:blue;
    }

    table{
        border-left: 1px solid #666; border-bottom: 1px solid #666;
        border-spacing: 0;
        width: 100%;
    }

    tr.hilight:hover, a:hover, tr.hilight:hover a {
        color:red;
    }

    tbody.center td {
        text-align:center;
    }
    th,td{ border-right:1px solid #666;border-top: 1px solid #666; padding: 3px; }

    input {
        border-top:1px solid #708090;
        border-right:1px solid #708090;
        border-bottom:1px solid #708090;
        border-left:1px solid #708090;
        height:20px;
        FONT-SIZE: 12pt;
    }

    input.short {
        width:100px;
    }
    textarea {
        border-top:1px solid #708090;
        border-right:1px solid #708090;
        border-bottom:1px solid #708090;
        border-left:1px solid #708090;
    }

    .history-table textarea {
        width:370px;
        height:52px;
    }

    #divSync ol li {
        line-height: 32px;
    }

    #divSync ol li button{
        margin: 0 20px 0 20px;
    }
    #divSync ol li div.name{
        width: 300px !important;
        display: inline-block;
    }

    hr { height:1px;border:none;border-top:1px dashed #0066CC; }

    span.jsBlock {
        display: none;
    }
</style>
<body>
    <button id="btnEnv">查看环境</button>
    <button id="btnLog">日志下载</button>
    <button id="btnTailLog">日志监控</button>
    <button id="btnSql">SQL</button>
    <button id="btnJs">JS</button>
    <button id="btnHistory">收藏</button>
    <button id="btnSync">接口调用</button>
    <button id="btnSms">短信平台</button>
    <a class="button" href="?p=123">退出</a>
    <hr/>

    <div id="divEnv"></div>

    <div id="divLog">
        Dir:<input type="text" name="dir" id="dir" size="60" value="" /> （空白则显示根目录, <button id="btnGuessTomcatLogDir">猜猜看</button>）
        <button id="btnList" >查询</button>
    </div>

    <div id="outputWrapper">
        <hr/>
        <div id="outputDiv"></div>
    </div>
</body>
<script language="JavaScript">
    <%
    java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmm");
    %>
    var now = <%=format.format(new java.util.Date())%>;
</script>
<script src="mgmt.js" type="text/javascript"></script>
</html>