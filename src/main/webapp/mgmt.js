Date.prototype.format = function(fmt){ //author: meizz
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "H+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

var localUrl = "https://local.sh.chinamobile.com:10086/";

var tailing = false;

function hideAll(){
    tailing = false;
    $("#divEnv").hide();
    $("#divLog").hide();
    $("#divTailLog").hide();
    $("#divSql").hide();
    $("#divJs").hide();
    $("#divHistory").hide();
    $("#divSync").hide();
    $("#divSms").hide();
    $("#outputWrapper").hide();
}
function show(name){
    hideAll();
    $('#' + name).show();
}

function output(content){
    $("#outputWrapper").show();
    $('#outputDiv').html("");
    $('#outputDiv').html(content);
}

function append(content){
    $("#outputWrapper").show();
    $('#outputDiv').html( $('#outputDiv').html() + content);
}

$("select.host").change(function(e){
    $('#host').val( $(e.target).val() ) ;
});

$("#btnEnv").click(function(){
    show('divEnv');

    $.ajax({
        type: "get",
        url: "mgmt/cluster/system",
        success: function(infos1){
            console.log(infos1);
            var infos=JSON.parse(infos1);
            console.log(infos);
            var result = [];
            if( !(infos instanceof Array) ){
                console.log("------11111------");
                return;
            }
            result.push("<table>");
            for(var i=0; i < infos.length; i++){
                var it = infos[i];
                console.log(it);
                result.push("<tr><td align='right'>"+i+"</td><td>" + it.key + "</td><td>" + it.value + "</td></tr>");
            }
            result.push("</table>");
            output(result.join(' '));
        }
    });
})

function arrayFind(arr, item){
    for(var i=0; i<arr.length; i++){
        var it = arr[i];
        if(it.key==item){
            return it.value;
        }
    }
    return false;
}
$("#btnGuessTomcatLogDir").click(function(){
    $("#dir").val("C:\\apache-tomcat-7.0.68");
});

$("#btnLog").click(function(){
    show('divLog');
    $('#dir').focus();
})

function list(dir){
    $('#dir').val(dir);
    $('#btnList').click();
}

$('#dir').keypress(function (e) { //这里给function一个事件参数命名为e，叫event也行，随意的，e就是IE窗口发生的事件。
    var key = e.which; //e.which是按键的值
    if (key == 13) {
        $('#btnList').click();
    }
});

$("#btnList").click(function(){
    var host = $('#host').val();
    var dir = $('#dir').val();
    $.ajax({
        type: "get",
        url: "mgmt/cluster/listFile",
        data:{
            host:host,
            dir:dir
        },
        success: function(files1){
            var files=JSON.parse(files1);
            var result = [];
            if(! (files instanceof Array) ){
                console.log('----111111----');
                return;
            }
            result.push("<table>");
            for(var i=0; i < files.length; i++){
                var f = files[i];

                if(f.directory){
                    result.push("<tr><td align='right'>" + i + "</td><td><a href='javascript:top.window.list(\"" + f.path.replace(/\\/g,"\\\\") + "\")'>" + (f.name || f.path) + "/</a></td><td></td><td></td><td></td></tr>");
                }else{
                    result.push("<tr><td align='right'>" + i + "</td><td><a target='_blank' href='mgmt/cluster/getFile?host=" + host + "&file=" + f.path + "'>" + (f.name ||
                            f.path) + "</a></td><td align='right'>" + f.length + "</td><td>"+new Date(f.date).format("yyyy-MM-dd HH:mm:ss")+"</td><td><button onclick='monitor(\""+ f.path.replace(/\\/g,"\\\\")+"\")'>监控文件</button></td></tr>");
                }
            }
            result.push("</table>");
            output(result.join(' '));
        }
    });
});

$("#btnSql").click(function(){
    show('divSql');
    $('#sql').focus();
    $.ajax({
        type:'get',
        url:"service/mgmt2/entity/Mgmt:sort(star desc)?where=[{n:'star',v:0,o:'>'},{n:'type',v:'SQL'}]",
        success:function(json){
            if(!json || !json.success){
                return;
            }
            var histories = json.records;
            var choice = $('#divSql select.sqlhistory');
            choice.html("<option></option>");
            for(var i=0; i<histories.length; i++){
                var his = histories[i];
                choice.append('<option class="id-'+his.id+'">' + (his.remark ? his.remark : '' + his.content.substr(0,80)) + '</option>');
                var opt = $('option.id-'+his.id,choice).get(0);
                opt.value = his.content;
                opt.title = his.content;
            }
        }
    });
})

$('#divSql select.sqlhistory').change(function(){
    $('#sql').val($('#divSql select.sqlhistory').get(0).value);
});

$("#sqlhistory").change(function(e){
    $('#sql').val( $(e.target).val() ) ;
});

$("#jshistory").change(function(e){
    $('#js').val( $(e.target).val() ) ;
});

$("#btnExecSql").click(function(){
    var host = $('#host').val();
    var sql = $('#sql').val();
    var remark = $('#sqlRemark').val();
    encrypt(sql,function(json){
        if(json && json.success){
            $.ajax({
                type: "post",
                url: "service/mgmt2/cluster/sql",
                data:{
                    host:host,
                    sql:json.result,
                    remark:remark
                },
                success: function(result){
                    output(result.value);
                }
            });
        }
    });
});

$("#btnJs").click(function(){
    show('divJs');
    $('#js').focus();
    $.ajax({
        type:'get',
        url:"service/mgmt2/entity/Mgmt:sort(star desc)?where=[{n:'star',v:0,o:'>'},{n:'type',v:'JS'}]",
        success:function(json){
            if(!json || !json.success){
                return;
            }
            var histories = json.records;
            var choice = $('#divJs select.jshistory');
            choice.html("<option></option>");
            for(var i=0; i<histories.length; i++){
                var his = histories[i];
                choice.append('<option class="id-'+his.id+'">' + (his.remark ? his.remark : '' + his.content.substr(0,80)) + '</option>');
                var opt = $('option.id-'+his.id,choice).get(0);
                opt.value = his.content;
                opt.title = his.content;
            }
        }
    });
})
$('#divJs select.jshistory').change(function(){
    $('#sql').val($('#divJs select.jshistory').get(0).value);
});

$("#btnExecJs").click(function(){
    var host = $('#host').val();
    var js = $('#js').val();
    var remark = $('#jsRemark').val();
    encrypt(js,function(json){
        if(json && json.success){
            $.ajax({
                type: "post",
                url: "service/mgmt2/cluster/js",
                data:{
                    host:host,
                    js:json.result,
                    remark:remark
                },
                success: function(result){
                    output(result.value);
                }
            });
        }
    });
});

$("#btnHistory").click(function(){
    show('divHistory');
})

$('#divHistory button.btnSearchHistory').click(function(){
    var pageTd = $('#divHistory td.page');
    $('input.offset',pageTd).val(0);
    doSearchHistory();
});

function doSearchHistory(){
    var table = $('#divHistory table.history-table tbody');
    var searchTd = $('#divHistory td.search');
    var pageTd = $('#divHistory td.page');

    $(table).html("");
    var type = $('select.type',searchTd).val();
    var remark = $('input.remark',searchTd).val();
    var content = $('input.content',searchTd).val();
    var q = [];
    if(type){
        q.push("{n:'type',v:'" + type + "'}");
    }
    if(remark){
        q.push("{n:'remark',v:'" + remark + "',o:'~'}");
    }
    if(content){
        q.push("{n:'content',v:'" + content + "',o:'~'}");
    }
    if(q && q.length>0){
        q = "[" + q.join(",") + "]";
    }
    var offset = $('input.offset',pageTd).val();
    $.ajax({
                type: "get",
                url: "service/mgmt2/entity/Mgmt:sort(star desc,createTime desc)",
                data:{
                    where:q,
                    limit:10,
                    start:offset
                },
                success: function(json){
                    if(!json || !json.success || json.total==0){
                        return;
                    }

                    $('span.total').html(json.total);
                    $('span.offset').html(offset);
                    $('span.end').html(json.records.length + offset*1);

                    var arr = [];
                    json = json.records
                    for(var i=0; i<json.length; i++){
                        var r = json[i];
                        arr.push('<tr class="id-'+r.id+'">');
                        arr.push('  <td>'+r.id+'</td>');
                        arr.push('  <td>'+r.type+'</td>');
                        arr.push('  <td><textarea>'+(r.remark ? r.remark : '')+'</textarea></td>');
                        arr.push('  <td><textarea>'+r.content+'</textarea></td>');
                        arr.push('  <td>'+ (new Date(r.createTime).format('yyyy-MM-dd'))+'</td>');
                        arr.push('  <td><select class="rect"><option '+ ( r.star==3 ? "selected='selected'" : "") +'>3</option><option '+ ( r.star==2 ? "selected='selected'" : "") +'>2</option><option '+ ( r.star==1 ? "selected='selected'" : "") +'>1</option><option '+ ( r.star==0 ? "selected='selected'" : "") +'>0</option></select></td>');
                        arr.push('  <td><button onclick="updateHistory('+r.id+')">修改</button></td>');
                        arr.push('</tr>');
                        $(table).append(arr.join("\n"));
                        var tr = $('tr.id-'+r.id,table);
                        tr.get(0)['raw-entity']=r;
                        arr=[];
                    }


                }
            });
}

$('#divHistory td.page a.first').click(function(e){
    var pageTd = $('#divHistory td.page');
    var offset = $('input.offset',pageTd).val();
    if(offset>0){
        $('input.offset',pageTd).val(0)
        doSearchHistory();
    }
});

$('#divHistory td.page a.previous').click(function(e){
    var pageTd = $('#divHistory td.page');
    var offset = $('input.offset',pageTd).val();
    if(offset>0){
        $('input.offset',pageTd).val(offset - 10)
        doSearchHistory();
    }
});

$('#divHistory td.page a.next').click(function(e){
    var pageTd = $('#divHistory td.page');
    var offset = $('input.offset',pageTd).val();
    var total = $('span.total',pageTd).html();
    if(offset*1+10<total*1){
        $('input.offset',pageTd).val(offset*1+10)
        doSearchHistory();
    }
});

$('#divHistory td.page a.last').click(function(e){
    var pageTd = $('#divHistory td.page');
    var offset = $('input.offset',pageTd).val();
    var total = $('span.total',pageTd).html();
    if(offset<total - total%10){
        $('input.offset',pageTd).val(total - total%10);
        doSearchHistory();
    }
});

function updateHistory(id){
    var tr = $('#divHistory table.history-table tr.id-' + id);
    var rawEntity = $(tr).get(0)['raw-entity'];
    console.log(rawEntity);
    var data = {
        remark: $('textarea',tr)[0].value,
        content: $('textarea',tr)[1].value,
        star: $('select',tr)[0].value
    };
    data = $.extend({},rawEntity,data);
    $.ajax({
        type: "put",
        url: "service/mgmt2/entity/Mgmt/"+id,
        data:JSON.stringify(data),
        contentType:'application/json',
        success:function(json){
            if(!json || !json.success){
                alert('更新失败!');
            }else{
                alert('更新成功!');
            }
        }
    });
}

$("#btnTailLog").click(function(){
    show('divTailLog');
    $('#file').focus();
});

function monitor(file){
    if(! confirm("确定要监控文件： " + file + " 吗？\n请务必确认该文件是文本文件！")){
        return;
    }
    $("#btnTailLog").click();
    $('#file').val(file);
    $('#offset').val('-50000');
    $('#size').val('10000');
    $("#btnDoTailFile").click();
}

function tail(host,file,offset,size){
    tailing = true;
    output("");
    function t(){
        $.ajax({
            type: "get",
            url: "service/mgmt2/cluster/fileBlock",
            data:{
                host:host,
                file:file,
                offset:offset,
                size:size
            },
            success: function(result){
                var txt = result.content;

                if(tailing && txt) append(txt.split("\n").join("<br/>"));

                if(result.error || result instanceof String){
                    tailing = false;
                }
                if(tailing){
                    offset = result.offset * 1 + result.size * 1;
                    //size = 1000;
                    setTimeout(t,3000);
                }
            }
        });

    }

    setTimeout(t,100);
}

$("#btnDoTailFile").click(function(){
    var host = $('#host').val();
    var file = $('#file').val();
    var offset = $('#offset').val();
    var size = $('#size').val();

    tail(host,file,offset,size);

});

$("#btnSync").click(function(){
    show('divSync');
});

$('#divSync ol li button').each(function(index,btn){
    $(btn).click(function(e){
        var cls = btn.dataset.cls,
            type = btn.dataset.type;

        if("cxf"==type){
            $.ajax({
                type : "GET",
                url : "service/mgmt2/sync/" + cls,
                success : function(result){
                    var span = $(btn).next();
                    span.hide();
                    span.html(result.value);
                    span.fadeIn( "slow" );
                }
            });
        }else if("js"==type){
            var host = $('#host').val();
            var js =btn.dataset.js;
            encrypt('load("nashorn:mozilla_compat.js");' + js,function(json){
                if(json && json.success){
                    $.ajax({
                        type: "post",
                        url: "service/mgmt2/js",
                        data:{
                            host:host,
                            js:json.result
                        },
                        success: function(result){
                            var span = $(btn).next();
                            span.hide();
                            span.html(result.value);
                            span.fadeIn( "slow" );
                        }
                    });
                }
            });
        }else if("jsBlock"==type){
            var host = $('#host').val();
            var js0 = $(btn).siblings('span.jsBlock').html();
            var js = eval(js0);
            encrypt('load("nashorn:mozilla_compat.js");' + js,function(json){
                if(json && json.success){
                    $.ajax({
                        type: "post",
                        url: "service/mgmt2/js",
                        data:{
                            host:host,
                            js:json.result
                        },
                        success: function(result){
                            var span = $(btn).next();
                            span.hide();
                            span.html(result.value);
                            span.fadeIn( "slow" );
                        }
                    });
                }
            });
        }
    });
});

$("#btnSms").click(function(){
    show('divSms');
    $('#sms_phone').focus();
});

function loadSmsConfig(){
    $.ajax({
        type : "GET",
        url : "service/entity/SMSConfig",
        success : function(result){
            $('#sms_xx1').html('');
            var html = '<tr><td>key</td><td>value</td></tr>';
            for(var i=0;i<result.length;i++){
                html += '<tr><td>'+result[i].key+'</td><td>'+result[i].value+'</td></tr>'
            }
            $('#sms_xx1').html(html);
        }
    });
}
function changeSmsConfig(){
    var key = $('#sms_key').val();
    var value = $('#sms_value').val();
    $.ajax({
        type : "GET",
        url : "service/smsconfig/add/"+key+"/"+value,
        success : function(result){
            alert('增加修改成功');
            loadSmsConfig();
        }
    });
}
function delSmsConfig(){
    var key = $('#sms_key').val();
    $.ajax({
        type : "GET",
        url : "service/smsconfig/del/"+key,
        success : function(result){
            alert('删除成功');
            loadSmsConfig();
        }
    });
}
////////////////////////////

function loadSmsBW(){
    $.ajax({
        type : "GET",
        url : "service/entity/SMSBlackWhiteList:sort(type)",
        success : function(result){
            $('#sms_xx2').html('');
            var html = '<tr><td>number</td><td>type</td></tr>';
            for(var i=0;i<result.length;i++){
                html += '<tr><td>'+result[i].phone+'</td><td>'+result[i].type+'</td></tr>'
            }
            $('#sms_xx2').html(html);
        }
    });
}
function addSmsBW(type){
    var number = $('#sms_number1').val();
    $.ajax({
        type : "GET",
        url : "service/smsbwlist/add/"+type+"/"+number,
        success : function(result){
            alert('增加成功');
            loadSmsBW();
        }
    });
}
function delSmsBW(){
    var number = $('#sms_number1').val();
    $.ajax({
        type : "GET",
        url : "service/smsbwlist/del/"+number,
        success : function(result){
            alert('删除成功');
            loadSmsBW();
        }
    });
}

///////////////////////////////////////////////
function sendSms(){
    var phone = $('#sms_phone').val();
    var content = $('#sms_content').val();
    var count = $('#sms_count').val();
    var priority = $('#sms_priority').val();
    $.ajax({
        type : "GET",
        url : "service/sms/send/"+phone+"/"+content+"/"+count+"/"+priority,
        success : function(result){
            alert('发送成功');
        }
    });
}

///////////////////////////////////////////////

function encrypt(text,callback){
    $.ajax({
        type:"get",
        url:localUrl + "encrypt",
        dataType:"jsonp",
        data:{text:text},
        success:callback
    });
}

function updateAuth() {
    $.ajax({
        type : "GET",
        url : "mgmt/time",
        success : function(json){
            if(!json || !json[1] || !json[1].value){
                return;
            }
            now = json[1].value;
            encrypt(now,function(json){
                var p = json.result;
//                console.log(p);
                var token = now + "x" + p;
//                console.log(token);
                document.cookie="_tk=" + token;
            });
        }
    });
    setTimeout(updateAuth,2*60*1000);
}

hideAll();
updateAuth();
$('#host').focus();
