﻿//var isInited = false;
//var isLogin = false;
//var isShowLoginFailInfo = false;
//var isFinished = false;
//var questionnaireId = undefined;
//var isShouldGetNextQuestionnaire = true;
//var isShowNotFinishedInfo = false;
//var isSubmittingAnswer = false;

var isLogin = false;
var currentQuestionnaireId = undefined;

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
        //isInited = true;
    });
});
$(window).resize(function () {
    $('#survey-container').css('min-height', $(window).height());
});
//$(window).scroll(function () {
//    var isFetchingData = false
//    if (isInited && !isFinished && !isFetchingData && $(document).scrollTop() + $(window).height() > $(document).height() - 5) {
//        if (isLogin) {
//            //Here try to submit the current questionnaire
//            if (questionnaireId != undefined && !isShouldGetNextQuestionnaire) {
//                //submit the questionnaire
//                var submitFunc = "q_" + questionnaireId + "_getAnswer()";
//                var result = eval(submitFunc);
//                if (result.finished == true && !isSubmittingAnswer) {
//                    isSubmittingAnswer = true;
//                    //submit ajax
//                    var answer = result.answer;
//                    $.post("http://localhost:8088/submit-answer",
//                        { questionnaireId: questionnaireId, answer: answer },
//                        function (data) {
//                            if (data.status == 0) {
//                                isShouldGetNextQuestionnaire = true;
//                            } else {
//                                //Exception
//                            }
//                            isSubmittingAnswer = false;
//                        });
//                }
//            }
//            if (isShouldGetNextQuestionnaire) {
//                isShouldGetNextQuestionnaire = false;
//                isFetchingData = true;
//                $.get("http://localhost:8088/next-page", function (data) {
//                    if (data.status == 0) {
//                        questionnaireId = data.questionnaireId;
//                        var questionnaireUrl = "http://localhost:8088/static/questionnaire/" + data.pageName;
//                        getQuestionnaire(questionnaireUrl);
//                    } else if (data.status == 1) {
//                        isFinished = true;
//                    } else {
//                        //Exception
//                        isShouldGetNextQuestionnaire = true;
//                    }
//                });
//            } else {
//                if (!isShowNotFinishedInfo && questionnaireId != undefined) {
//                    isShowNotFinishedInfo = true;
//                    $.pnotify({
//                        title: '请先完成当前问卷...',
//                        text: '您必须先完成当前问卷才能进行接下来的测试...',
//                        icon: false,
//                        type: 'error',
//                        after_close: function () {
//                            isShowNotFinishedInfo = false;
//                        }
//                    });
//                }
//            }
//        } else { 
//            if(!isShowLoginFailInfo) {
//                isShowLoginFailInfo = true;
//                $.pnotify({
//                    title: '清先登录...',
//                    text: '您必须先登录才能开始答题...',
//                    icon: false,
//                    type: 'error',
//                    after_close:function() {
//                        isShowLoginFailInfo = false;
//                    }
//                });
//            }
//        }
//    }
//});
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
    $('#survey-userlogin-info').html("当前用户：" + email);
    $('.before-login').hide();
    $(".after-login").show();
    updateMenu();
}
function getNewQuestionnaire() {
    $.get("http://localhost:8088/next-page", function (data) {
        if (data.status == 0) {
            questionnaireId = data.questionnaireId;
            var questionnaireUrl = "http://localhost:8088/static/questionnaire/" + data.pageName;
            getQuestionnaire(questionnaireUrl);
        } else if (data.status == 1) {
            //Finish Event

        } else {
            //Exception
        }
    });
}
function getQuestionnaire(url) {
    $.get(url, function (data) {
        var nextPage = "<div id= \"" + questionnaireId + "\"class=\"survey-questionnaire\">" + data + "</div>";
        $('#survey-content').append(nextPage);
        //isFetchingData = false;
    });
}
function updateMenu() {
    $.get("http://localhost:8088/get-menu", function (data) {
        if (data.status == 0) {
            var menuData = data.menu;
            $('#survey-menu').html(menuData);
        } else {
            //Exception
        }
    });
}

function submitCurrentQuestionnaire() {
    if (currentQuestionnaireId != undefined) {
        var submitFunc = "q_" + questionnaireId + "_getAnswer()";
        var result = eval(submitFunc);
        if (result.finished == true && !isSubmittingAnswer) {
            isSubmittingAnswer = true;
            //submit ajax
            var answer = result.answer;
            $.post("http://localhost:8088/submit-answer",
                { questionnaireId: questionnaireId, answer: answer },
                function (data) {
                    if (data.status == 0) {
                        isShouldGetNextQuestionnaire = true;
                    } else {
                        //Exception
                    }
                    isSubmittingAnswer = false;
                });
        }
    }
}