/**
 * 权限列表
 */
let $ = layui.jquery;
!(function () {

    //初始化treegrid 页面表格
    layui.config({
        base: '../../../treegrid/'
    }).use(['laytpl', 'treegrid'], function () {
        var laytpl = layui.laytpl,
            treegrid = layui.treegrid;
        treegrid.config.render = function (viewid, data) {
            var view = document.getElementById(viewid).innerHTML;
            return laytpl(view).render(data) || '';
        };

        var treeForm=treegrid.createNew({
            elem: 'permTable',
            view: 'view',
            data: { rows: permList },
            parentid: 'parentId',
            singleSelect: false
        });
        treeForm.build();

    });
    //操作
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(permSubmit)', function(data){
            let style = data.field.style;
            switch (style) {
                case "0":
                    $.ajax({
                        type: "PUT",
                        data: $("#permForm").serialize(),
                        url: "/admin/api/v1/system/menus",
                        success: function (data) {
                            if (data == "ok") {
                                layer.alert("操作成功",function(){
                                    layer.closeAll();
                                });
                            } else {
                                layer.alert(data);
                            }
                        },
                        error: function (data) {
                            layer.alert("操作请求错误，请您稍后再试");
                        }
                    });
                    break;
                case "1":
                    $.ajax({
                        type: "POST",
                        data: $("#permForm").serialize(),
                        url: "/admin/api/v1/system/menus",
                        success: function (data) {
                            if (data == "ok") {
                                layer.alert("操作成功",function(){
                                    layer.closeAll();
                                });
                            } else {
                                layer.alert(data);
                            }
                        },
                        error: function (data) {
                            layer.alert("操作请求错误，请您稍后再试");
                        }
                    });
                    break;
                default:
                    break;
            }

            return false;
        });
        form.render();
    });




}());
function edit(id,type){
    if(null!=id){
        $("#style").val(type);
        $("#id").val(id);
        $.get("/admin/api/v1/system/menus/"+id,function(data) {
            // console.log(data);
            if(null!=data){
                $("input[name='title']").val(data.title);
                $("input[name='mark']").val(data.mark);
                $("input[name='path']").val(data.path);
                $("input[name='sorts']").val(data.sorts);
                $("textarea[name='extra']").text(data.extra);
                $("#parentId").val(data.parentId);
                data.type==0?$("input[name='type']").val(0).checked:$("input[name='type']").val(1).checked;
                layer.open({
                    type:1,
                    title: "更新权限",
                    fixed:false,
                    resize :false,
                    shadeClose: true,
                    area: ['500px', '580px'],
                    content:$('#updatePerm'),
                    end:function(){
                        location.reload();
                    }
                });
            }else{
                layer.alert("获取权限数据出错，请您稍后再试");
            }
        });
    }
}
//开通权限
function addPerm(parentId,flag){
    if(null!=parentId){
        //flag[0:开通权限；1：新增子节点权限]
        //style[0:编辑；1：新增]
        if(flag==0){
            $("#type").val(1);
            $("#style").val(1);
            $("#parentId").val(0);
        }else{
            $("#type").val(2);
            //设置父id
            $("#style").val(1);
            $("#parentId").val(parentId);
        }
        layer.open({
            type:1,
            title: "添加权限",
            fixed:false,
            resize :false,
            shadeClose: true,
            area: ['500px', '580px'],
            content:$('#updatePerm'),  //页面自定义的div，样式自定义
            end:function(){
                location.reload();
            }
        });
    }
}

function del(id,name){
    // console.log("===删除id："+id);
    if(null!=id){
        layer.confirm('您确定要删除'+name+'权限吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/auth/del",{"id":id},function(data){
                if(data=="ok"){
                    //回调弹框
                    layer.alert("删除成功！",function(){
                        layer.closeAll();
                        //加载load方法
                        location.reload();;//自定义
                    });
                }else{
                    layer.alert(data);//弹出错误提示
                }
            });
        }, function(){
            layer.closeAll();
        });
    }

}

//关闭弹框
function close(){
    layer.closeAll();
}