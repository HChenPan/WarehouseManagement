$(function () {
    $('#departlist').treegrid({
        url: 'department/search',
        idField: 'id',
        treeField: 'name',
        lines: true,
        rownumbers: true,
        toolbar: toolbar,
        singleSelect: true,
        striped: true,
        columns: [[
            {field: 'id', hidden: true},
            {field: 'name', title: '组织机构名称', width: 300, sortable: true},
            {field: 'deptnumber', title: '组织机构编号', width: 150, sortable: true},
            {field: 'tel', title: '办公电话', width: 150, sortable: true},
            {field: '_parentId', hidden: true}
        ]],
        onLoadSuccess: function () {
            $('#departlist').treegrid('collapseAll');
            var rootnode = $('#departlist').treegrid('getRoot');
            setTimeout(function () {
                $('#departlist').treegrid('expand', rootnode.id);
            }, 500);
        }
    });
});

var url;

/* 定义数据表格的按钮及事件 */
var toolbar = [{
    text: '新增',
    iconCls: 'icon-add',
    handler: function () {
        getselectnode();
        url = 'department/create';
    }
}, '-', {
    text: '修改',
    iconCls: 'icon-edit',
    handler: function () {
        getSelecteddepart();
        url = 'department/update';
    }
}, '-', {
    text: '删除',
    iconCls: 'icon-cut',
    handler: function () {
        deleteSelecteddepart();
    }
}];

/* 为新增操作取得选中节点 */
function getselectnode() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $('#departadd').form('clear');
        $('#parentid').val(row.id);
        $('#w').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择欲添加单位的上级');
    }
}

/* 数据表单提交 */
function submitdepartForm() {
    $('#departadd').form(
        'submit',
        {
            url: url,
            onSubmit: function () {
                return $('#departadd').form('validate');
            },
            success: function (data) {
                var data = eval('(' + data + ')');
                if (url.indexOf('update') > 0) {
                    $.messager.alert('提示信息', '成功修改组织机构');
                    $('#departadd').form('clear');
                    $('#departlist').treegrid('reload');
                    $('#w').window('close');
                    setTimeout(function () {
                        $('#departlist').treegrid('expandTo', data.id).treegrid('select', data._parentId);
                    }, 500);
                } else {
                    $.messager.alert('提示信息', '成功添加组织机构');
                    $('#departadd').form('clear');
                    $('#departlist').treegrid('reload');
                    setTimeout(function () {
                        $('#parentid').val(data._parentId);
                        $('#departlist').treegrid('expandTo', data.id).treegrid('select', data._parentId);
                    }, 500);
                }
            }
        });
}

/* 删除所选数据 */
function deleteSelecteddepart() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $.messager.confirm('提示信息', '确定删除此数据?', function (r) {
            if (r) {
                $.ajax({
                    async: false,
                    url: 'department/delete',
                    data: 'id=' + row.id,
                    success: function (data) {
                        $.messager.alert('提示信息', '成功删除组织机构');
                        $('#departlist').treegrid('reload');
                        setTimeout(function () {
                            $('#departlist').treegrid('expandTo', data);
                        }, 500);
                    }
                });
            }
        });
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}

/* 取得选中行数据并打开修改表单 */
function getSelecteddepart() {
    var row = $('#departlist').treegrid('getSelected');
    if (row) {
        $('#departadd').form('clear');
        $('#departadd').form('load', row);
        $('#w').window('open');
    } else {
        $.messager.alert('提示信息', '请首先选择一行');
    }
}
