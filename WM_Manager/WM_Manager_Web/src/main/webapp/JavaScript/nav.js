$(function () {
    var index = 0;
    $('.navlist li a').click(function () {
        $(".navlist li div").removeClass("selected");
        $(this).parent().addClass("selected");
    }).hover(function () {
        $(this).parent().addClass("hover");
    }, function () {
        $(this).parent().removeClass("hover");
    });
});

function addPanel(url, title) {
    if (!$('#tt').tabs('exists', title)) {
        $('#tt')
            .tabs(
                'add',
                {
                    title: title,
                    content: '<iframe src="' + url + '" frameBorder="0" border="0"  style="width: 100%; height: 100%;"/>',
                    closable: true
                });
    } else {
        $('#tt').tabs('select', title);
    }
}

function removePanel() {
    var tab = $('#tt').tabs('getSelected');
    if (tab) {
        var index = $('#tt').tabs('getTabIndex', tab);
        $('#tt').tabs('close', index);
    }
}
