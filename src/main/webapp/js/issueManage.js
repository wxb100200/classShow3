$(document).ready(function () {
      //调用函数，初始化表格
      initTable();
      //新增班牌信息
      $('#addClassBoard').on('click',addClassBoard);
      //当点击查询按钮的时候执行
      $("#search").bind("click", initTable);
 });

function initTable() {
   $.ajax({
      type: "GET",
      url:"../issue/findClassBoard",
　　　success: function(msg){
//          console.log(msg);
　　　　　//这里的msg是json对象，不是json字符串。
            var obj=JSON.parse(msg);
            $('#classShowTable').bootstrapTable('destroy').bootstrapTable({
            data:obj
            });
　　　}
　　});
}
function addClassBoard(){
    console.log("---->>>>>");
    console.log($(this));
};

