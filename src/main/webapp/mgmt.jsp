<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Management Console</title>
    <link type="text/css" rel="stylesheet" media="all" href="css/manage.css"></link>
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/manage.js"></script>
</head>
<body>
    http://
    <select class="host">
        <option value="127.0.0.1">localhost</option>
        <option value="127.0.0.1">127.0.0.1</option>
    </select>
    <button id="btnEnv">查看环境</button>
    <button id="btnLog">日志下载</button>
    <button id="btnTailLog">日志监控</button>
    <button id="btnSql">SQL</button>
    <button id="btnJs">JS</button>
    <button id="btnHistory">收藏</button>
    <button id="btnSync">接口调用</button>
    <a class="button" href="?p=123">退出</a>
    <hr/>
    <div id="divLog">
        Dir:<input type="text" name="dir" id="dir" size="60" value="" /> （空白则显示根目录, <button id="btnGuessTomcatLogDir">tomcat路径</button>）
        <button id="btnList" >查询</button>
    </div>
    <div id="divTailLog">
        File:<input type="text" name="file" id="file" size="80" value="" />
        Offset: <input type="text" name="offset" id="offset" value="-50000" class="short"/>
        <!--Length:--> <input type="hidden" name="size" id="size" value="10000" class="short"/>
        <button id="btnDoTailFile" >开始监控</button>
    </div>
    <div id="divSql">
            <table style="width:850px;" >
                <tr><td width="50" align="right">收藏:</td>
                    <td><select id="sqlhistory" class="sqlhistory rect" style="width:700px;" >
                        <option>Select * from kd_person</option>
                        <option>Select * from kd_mgmt</option>
                    </select></td>
                    <td width="100" align="center" rowspan="3" valign="top"><button id="btnExecSql" >查询</button></td></tr>
                <tr><td align="right">备注:</td>
                    <td><input type="text" id="sqlRemark" class="remark" style="width:700px;" /></td>
                </tr>
                <tr><td align="right">SQL:</td><td><textarea id="sql" name="sql" rows="10" style="width:700px;"> </textarea></td></tr>
            </table>
        </div>
    <div id="outputWrapper">
        <hr/>
        <div id="outputDiv"></div>
    </div>

</body>
<html>