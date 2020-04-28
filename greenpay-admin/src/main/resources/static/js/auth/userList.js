/**
 * 权限列表
 */
var $ = layui.$
!(function() {

    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form,
            layer = layui.layer;

        tableIns=table.render({
            elem: '#uesrList'
            ,url:'/admin/api/v1/system/users'
            ,cellMinWidth: 80
            ,page: true
            ,parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": 0,//解析接口状态
                "msg": res.message,//解析提示文本
                "count": res.total,//解析提示文本
                "records": res.records//解析数据列表
            };
            },
            request: {
                pageName: 'current' //页码的参数名称，默认：current
                ,limitName: 'size' //每页数据量的参数名，默认：size
            },response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 0 //成功的状态码，默认：0
                ,countName: 'total' //数据总数的字段名称，默认：count
                ,dataName: 'records' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'numbers'}//{type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID',width:80, unresize: true, sort: true}
                ,{field:'username', title:'用户名'}
                // ,{field:'mobile', title:'手机号'}
                ,{field:'email', title: '邮箱'}
                ,{field:'roleNames', title: '角色名称', minWidth:80}
                ,{field: 'createdAt', title: '创建时间', width:180, unresize:true}
                ,{field: 'updatedAt', title: '更新时间', width:180, unresize:true}
                ,{fixed:'right', title:'操作',width:140,align:'center', toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听工具条
        table.on('tool(userTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                delUser(obj,layer,data);
            } else if(obj.event === 'edit'){
                //编辑

            } else if(obj.event === 'recover'){
                //恢复
                recoverUser(data,data.id);
            }
        });
        //监听提交
        form.on('submit(userSubmit)', function(data){
            formSubmit(data);
            return false;
        });

    });
    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#insertTimeStart'
        });
        laydate.render({
            elem: '#insertTimeEnd'
        });
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
}());

//提交表单
function formSubmit(obj){
    //应该做一个更新自己的下线操作
    // var currentUser=$("#currentUser").html();
    if(checkRole()){
        submitAjax(obj);
    }
}
function submitAjax(obj){
    $.ajax({
        type: "POST",
        data: $("#userForm").serialize(),
        url: "/admin/api/v1/system/users",
        success: function (data) {
            if (data ) {
                layer.alert("操作成功",function(){
                    layer.closeAll();
                    cleanUser();
                    //$("#id").val("");
                    //加载页面
                    load(obj);
                });
            } else {
                layer.alert(data,function(){
                    layer.closeAll();
                    //加载load方法
                    load(obj);//自定义
                });
            }
        },
        error: function (data) {
            var er = $.parseJSON(data.responseText);
            var sr ;
            if (er.length > 1) {
                $.each(er.errors, function(i, item) {
                    sr = item.message;
                    return false;
                });
            }else {
                sr = er.message;
            }

            layer.alert(sr);

        }
    });
}
function checkRole(){
    //选中的角色
    var array = new Array();
    var roleCheckd=$(".layui-form-checked");
    //获取选中的权限id
    for(var i=0;i<roleCheckd.length;i++){
        array.push($(roleCheckd.get(i)).prev().val());
    }
    //校验是否授权
    var roleIds = array.join(",");
    if(roleIds==null || roleIds==''){
        layer.alert("请您给该用户添加对应的角色！")
        return false;
    }
    $("#roleIds").val(roleIds);
    return true;
}
//开通用户
function addUser(){
    $.get("/admin/api/v1/system/roles",function(data){
        if(data!=null){
            //显示角色数据
            $("#roleDiv").empty();
            $.each(data.records, function (index, item) {
                // <input type="checkbox" name="roleId" title="发呆" lay-skin="primary"/>
                var roleInput=$("<input type='checkbox' name='roleId' value="+item.id+" title="+item.name+" lay-skin='primary'/>");
                //未选中
                /*<div class="layui-unselect layui-form-checkbox" lay-skin="primary">
                    <span>发呆</span><i class="layui-icon">&#xe626;</i>
                    </div>*/
                //选中
                // <div class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="primary">
                // <span>写作</span><i class="layui-icon">&#xe627;</i></div>
                var div=$("<div class='layui-unselect layui-form-checkbox' lay-skin='primary'>" +
                    "<span>"+item.name+"</span><i class='layui-icon'>&#xe626;</i>" +
                    "</div>");
                $("#roleDiv").append(roleInput).append(div);
            })
            openUser(null,"开通用户");
            //重新渲染下form表单 否则复选框无效
            layui.form.render('checkbox');
        }else{
            //弹出错误提示
            layer.alert("获取角色数据有误，请您稍后再试",function () {
                layer.closeAll();
            });
        }
    });
}
function openUser(id,title){
    if(id==null || id==""){
        $("#id").val("");
    }
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setUser'),
        end:function(){
            cleanUser();
        }
    });
}

function delUser(obj,layer,data) {
    //需要判断下是否删除自己
    // var currentUser=$("#currentUser").html();
    var version=obj.version;
    if(null!=data.id){
        // if(currentUser==id){
        //     layer.alert("对不起，您不能执行删除自己的操作！");
        // }else{
        // }
        layer.confirm('您确定要删除'+name+'用户吗？', {
            btn: ['确认','返回'] //按钮
        }, function(index){
            obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            layer.close(index);

            //向服务端发送删除指令
            $.ajax({
                url:"/admin/api/v1/system/users/del",
                data:{'id':data.id},
                type:"DELETE",
                // dataType:"json",
                success:function(data){
                    layer.msg(data.msg);
                    load(obj);//自定义
                },
                error:function(data){
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        //加载load方法
                        load(obj);//自定义
                    });
                }
            });

        }, function(){
            layer.closeAll();
        });
    }
}
function recoverUser(obj,id) {
    //console.log("需要恢复的用户id="+id);
    var version=obj.version;
    //console.log("delUser版本:"+version);
    if(null!=id){
        layer.confirm('您确定要恢复'+name+'用户吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/user/recoverUser",{"id":id,"version":version},function(data){
                if(isLogin(data)){
                    if(data=="ok"){
                        //回调弹框
                        layer.alert("恢复成功！",function(){
                            layer.closeAll();
                            //加载load方法
                            load(obj);//自定义
                        });
                    }else{
                        layer.alert(data,function(){
                            layer.closeAll();
                            //加载load方法
                            load(obj);//自定义
                        });
                    }
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}


function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

function cleanUser(){
    //$("#id").val("");
    $("#username").val("");
    $("#mobile").val("");
    $("#email").val("");
    $("#password").val("");
}