<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="loginHead.jsp"%>
<div id="content">
    <p id="whereami">
    </p>
    <h1>
        请登录
    </h1>
    <form action="loginRequest" method="post">
        <table cellpadding="0" cellspacing="0" border="0"
            class="form_table">
            <tr>
                <td valign="middle" align="right">
                    用户名:
                </td>
                <td valign="middle" align="left">
                    <input type="text" class="inputgri" name="name" />
                </td>
            </tr>
            <tr>
                <td valign="middle" align="right">
                    密码:
                </td>
                <td valign="middle" align="left">
                    <input type="password" class="inputgri" name="password" />
                </td>
            </tr>
        </table>
        <p>
            <input type="submit" class="button" value="确认" />
        </p>
    </form>
</div>
<%@include file="loginFoot.jsp"%>
