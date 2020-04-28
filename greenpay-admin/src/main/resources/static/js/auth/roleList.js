/**
 * 权限列表
 */
!(function() {

    layui.use('table', function(){
        let $ = layui.jquery;
        let table = layui.table;


        table.render({
            elem: '#demo'
            ,url: '/admin/api/v1/system/roles' //数据接口
            ,page: true //开启分页
            ,parseData: function(res){ //res 即为原始返回的数据
                return {
                    "code": 0,
                    "msg": res.message,
                    "count": res.total,
                    "data": res.records
                };
            }
            ,request: {
                pageName: 'current'
                ,limitName: 'size'
            }
            ,cols: [[ //表头
                {field: 'id', title: 'ID',width:'5%', unresize:true , sort: true}
                ,{field: 'name', title: '用户名', width:'15%', unresize:true}
                ,{field: 'roleCode', title: '权限码', width:'25%', unresize:true}
                ,{field: 'createdAt', title: '创建时间', width:'20%', unresize:true}
                ,{field: 'updatedAt', title: '更新时间', width:'20%', unresize:true}
                ,{title: '操作', align: 'left', width:'15%', toolbar: '#barDemo', unresize:true}
            ]]
        });
        table.on('checkbox(demo)', function(obj){
            console.log(obj)
        });
        table.on('tool(demo)', function(obj){
            var data = obj.data;
            if(obj.event === 'edit'){
                updateRole(data.id);
            } else if(obj.event === 'del'){
                layer.confirm('您确定要删除'+data.name+'角色吗？', {
                    btn: ['确认','返回'] //按钮
                }, function(index){
                    delRole(data.id,obj);
                    // obj.del();
                    layer.close(index);
                });
            }
        });


        function updateRole(id) {
            //isNaN是数字返回false
            if(id!=null && !isNaN(id)){
                window.location.href="/admin/system/role/list/edit/"+id;
            }else{
                layer.alert("请求参数有误，请您稍后再试");
            }
        }
        function delRole(id,obj) {
            if(null!=id && !isNaN(id)){
                $.ajax({
                    url: "/admin/api/v1/system/roles/del",
                    data: {'id': id},
                    type: "Delete",
                    success: function (data) {
                        layer.msg(data.msg);
                        obj.del();
                        // location.reload();
                        layer.closeAll();
                    },
                    error: function (data) {
                        alert(data.responseJSON.message);
                        layer.closeAll();
                    }
                });

            }
        }
    });
}());


