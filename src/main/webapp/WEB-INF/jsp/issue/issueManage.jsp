<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-table/bootstrap-table.css">
<script src="${pageContext.request.contextPath}/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap3/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-table/bootstrap-table.js"></script>

<script src="${pageContext.request.contextPath}/js/issueManage.js" type="text/javascript"></script>
</head>
<body style="padding: 40px">
    <div>
      <!-- Nav tabs -->
      <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#classShow" aria-controls="classShow" role="tab" data-toggle="tab">班牌信息</a></li>
        <li role="presentation"><a href="#logInfo" aria-controls="logInfo" role="tab" data-toggle="tab">日志信息</a></li>
      </ul>

      <!-- Tab panes -->
      <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="classShow">
            <br/>
            <caption>>>班牌信息列表</caption>
            <div class="bs-example">
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="modal" data-target="#addClassShow" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增</button>

              <div class="modal fade" id="addClassShow" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                    <div class="modal-header">
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                      <h4 class="modal-title" id="exampleModalLabel">新增班牌信息</h4>
                    </div>
                    <div class="modal-body">
                      <form>
                        <div class="form-group">
                          <label for="recipient-name" class="control-label">班牌名称</label>
                          <input type="text" class="form-control" id="classBoardName">
                        </div>
                        <div class="form-group">
                          <label for="message-text" class="control-label">班牌IP</label>
                          <input type="text" class="form-control" id="classBoardIP">
                        </div>
                      </form>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                      <button type="button" class="btn btn-primary" id="addClassBoard">保存</button>
                    </div>
                  </div>
                </div>
              </div>

              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">天气</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">考试</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">课表</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">倒计时</button>
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">主页</button>
              <div class="btn-group">
                 <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">样式<span class="caret"></span></button>
                 <ul class="dropdown-menu">
                    <li><a href="#">样式一</a></li>
                    <li><a href="#">样式二</a></li>
                    <li><a href="#">样式三</a></li>
                    <li><a href="#">样式四</a></li>
                    <li><a href="#">样式五</a></li>
                    <li><a href="#">样式六</a></li>
                  </ul>
              </div>
              <div class="btn-group">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">通知<span class="caret"></span></button>
                <ul class="dropdown-menu">
                    <li><a href="#">文本通知</a></li>
                    <li><a href="#">图片通知</a></li>
                    <li><a href="#">视频通知</a></li>
                 </ul>
              </div>
          </div>
            <br/>
            <table class="table table-striped table-bordered table-hover" id="classShowTable">
              <thead>
                <tr>
                  <th  data-field="id">#</th>
                  <th  data-field="name">班牌名称</th>
                  <th  data-field="ip">班牌IP</th>
                  <th  data-field="createTime">创建时间</th>
                </tr>
              </thead>
            </table>
           <nav>
             <ul class="pager">
               <li><a href="#">上一页</a></li>
               <li><a href="#">下一页</a></li>
             </ul>
           </nav>

        </div>
        <div role="tabpanel" class="tab-pane" id="logInfo">
            ...
        </div>
      </div>

    </div>
    <div >
    </div>
</body>

</html>