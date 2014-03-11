//Control System
var isLogin = false;
var isInited = false;
var currentQuestionnaireId = undefined;
var isFinished = false;
var isBusy = false;
var initTop = 0;

//Display Control
var isShowingLoginFailInfo = false;
var isShowingSubmitFailInfo = false;

$(document).ready(function () {
    $('#survey-container').css('min-height', $(window).height());
    $('#survey-userinfo').scrollToFixed();
    $('#survey-menu').scrollToFixed({ marginTop: 65 });
    $.pnotify.defaults.styling = "jqueryui";
    $(".after-login").hide();
    $.get("http://localhost:8088/current-user", function (data) {
        if (data.status == 0) {
            userLogin(data.email);
        }
    });
    $('body,html').animate({ scrollTop: 0 }, function () {
        isInited = true;
    });
});
$(window).resize(function () {
    $('#survey-container').css('min-height', $(window).height());
});

$(window).scroll(function () {
    if (isInited && !isFinished && !isBusy 
        && $(document).scrollTop() + $(window).height() > $(document).height() - 1 
        && $(document).scrollTop() > initTop) {
        if (!isLogin) {
            if (!isShowingLoginFailInfo) {
                isShowingLoginFailInfo = true;
                $.pnotify({
                    title: '请先登录...',
                    text: '您必须先登录才能开始答题...',
                    icon: false,
                    type: 'error',
                    after_close: function () {
                        isShowingLoginFailInfo = false;
                    }
                });
            }
            return;
        }
        if (currentQuestionnaireId == undefined) {
            getNewQuestionnaire();
            return;
        }
        //Update the anchor information
        submitCurrentQuestionnaire();
    }
    initTop = $(document).scrollTop();
});

$('#survey-login').click(function () {
    var email = $('#survey-email').val();
    var password = $('#survey-password').val();
    if (email == "") {
        $.pnotify({
            title: '登录失败...',
            text: '请填写您的邮箱地址...',
            icon: false,
            type: 'error'
        });
        return;
    } else if (password == "") {
        $.pnotify({
            title: '登录失败...',
            text: '请填写您的密码...',
            icon: false,
            type: 'error'
        });
        return;
    }
    $.get("http://localhost:8088/sign-in", { email: email, password: password }, function (data) {
        if (data.status == 0) {
            userLogin(data.email);
        } else {
            $.pnotify({
                title: '登录失败...',
                text: '请填写正确的邮箱地址以及密码...',
                icon: false,
                type: 'error'
            });
        }
    }, "json");
});

$('#survey-logout').click(function () {
    $.get("http://localhost:8088/log-out", function (data) {
        if (data.status == 0) {
            isLogin = false;
            $(".after-login").hide();
            $('.before-login').show();
            $('#survey-password').val('');
            location.reload();
        }
    });
});

function userLogin(email) {
    isLogin = true;
    updateMenu();
    $('#survey-userlogin-info').html("当前用户：" + email);
    $('.before-login').hide();
    $(".after-login").show();
}

function getNewQuestionnaire() {
    isBusy = true;
    $.get("http://localhost:8088/next-page", function (data) {
        if (data.status == 0) {
            currentQuestionnaireId = data.questionnaireId;
            var nextPage = "<div id= \"" + currentQuestionnaireId + "\"class=\"survey-questionnaire\">" + data.page + "</div>";
            $('#survey-content').append(nextPage);
            updateMenu();
        } else if (data.status == 1) {
            //Finish Event

        } else {
            //Exception
        }
        isBusy = false;
    });
}

function updateMenu() {
    isBusy = true;
    $.get("http://localhost:8088/get-menu", function (data) {
        if (data.status == 0) {
            var menuData = data.menu;
            $('#survey-menu').html(menuData);
        } else {
            //Exception
        }
        isBusy = false;
    });
}

function submitCurrentQuestionnaire() {
    if (currentQuestionnaireId != undefined) {
        var submitFunc = "q_" + currentQuestionnaireId + "_getAnswer()";
        var result = eval(submitFunc);
        if (result.finished == true) {
            //submit ajax
            var answer = result.answer;
            isBusy = true;
            $.post("http://localhost:8088/submit-answer",
                { questionnaireId: currentQuestionnaireId, answer: answer },
                function (data) {
                    if (data.status == 0) {
                        //Submit success
                        getNewQuestionnaire();
                    } else {
                        //Exception
                    }
                    isBusy = false;
                });
        } else {
            if (!isShowingSubmitFailInfo) {
                isShowingSubmitFailInfo = true;
                $.pnotify({
                    title: '请先完成当前问卷...',
                    text: '您必须先完成当前问卷才能进行接下来的测试...',
                    icon: false,
                    type: 'error',
                    after_close: function () {
                        isShowingSubmitFailInfo = false;
                    }
                });
            }
        }
    }
}