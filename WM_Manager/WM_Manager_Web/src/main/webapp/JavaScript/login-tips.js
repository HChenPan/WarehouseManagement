function KeyDown() {
    if (event.keyCode === 13) {
        event.returnValue = false;
        event.cancel = true;
        login_form.ibtn_Login.click();
    }
}


function f_input_reset() {
    $("#focus .input_txt").each(function () {
        var thisVal = $(this).val();
        //判断文本框的值是否为空，有值的情况就隐藏提示语，没有值就显示
        if (thisVal !== "") {
            $(this).siblings("span").hide();
        } else {
            $(this).siblings("span").show();
        }
    });
}

$(document).ready(function () {

    $("#focus").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: "请输入用户名"
            },
            password: {
                required: "请输入密码"
            }
        },
        submitHandler: function (form) {
            $('#password').val($.md5($('#password').val()));
            $(form).ajaxSubmit();
        }
    });
    $("#focus .input_txt").each(function () {
        var thisVal = $(this).val();
        //判断文本框的值是否为空，有值的情况就隐藏提示语，没有值就显示
        if (thisVal !== "") {
            $(this).siblings("span").hide();
        } else {
            $(this).siblings("span").show();
        }
        //聚焦型输入框验证
        $(this).focus(function () {
            $(this).siblings("span").hide();
        }).blur(function () {
            var val = $(this).val();
            if (val !== "") {
                $(this).siblings("span").hide();
            } else {
                $(this).siblings("span").show();
            }
        });
    });
    $("#keydown .input_txt").each(function () {
        var thisVal = $(this).val();
        //判断文本框的值是否为空，有值的情况就隐藏提示语，没有值就显示
        if (thisVal !== "") {
            $(this).siblings("span").hide();
        } else {
            $(this).siblings("span").show();
        }
        $(this).keyup(function () {
            $(this).siblings("span").hide();
        }).blur(function () {
            var val = $(this).val();
            if (val !== "") {
                $(this).siblings("span").hide();
            } else {
                $(this).siblings("span").show();
            }
        });
    });
});
