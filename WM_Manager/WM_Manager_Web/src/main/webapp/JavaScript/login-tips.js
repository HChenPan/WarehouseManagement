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
        //�ж��ı����ֵ�Ƿ�Ϊ�գ���ֵ�������������ʾ�û��ֵ����ʾ
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
                required: "�������û���"
            },
            password: {
                required: "����������"
            }
        },
        submitHandler: function (form) {
            $('#password').val($.md5($('#password').val()));
            $(form).ajaxSubmit();
        }
    });
    $("#focus .input_txt").each(function () {
        var thisVal = $(this).val();
        //�ж��ı����ֵ�Ƿ�Ϊ�գ���ֵ�������������ʾ�û��ֵ����ʾ
        if (thisVal !== "") {
            $(this).siblings("span").hide();
        } else {
            $(this).siblings("span").show();
        }
        //�۽����������֤
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
        //�ж��ı����ֵ�Ƿ�Ϊ�գ���ֵ�������������ʾ�û��ֵ����ʾ
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
