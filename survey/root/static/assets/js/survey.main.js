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
    $('.popbox').popbox();
    $('#survey-container').css('min-height', $(window).height());
    $('#survey-userinfo').scrollToFixed();
    $('#survey-menu').scrollToFixed({ marginTop: 65 });
    $.pnotify.defaults.styling = "jqueryui";
    $(".after-login").hide();
    $.get(contextPath+"/current-user", function (data) {
        if (data.status == 0) {
            userLogin(data.email);
        }
    });
    $('body,html').animate({ scrollTop: 0 }, function () {
        isInited = true;
    });
	
	$(document).on("click","#survey-menu-list li p", function(){
		$(this).next(".survey-menu-inner-list").slideToggle();
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
            renderQuestionnaire();
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
    $.get(contextPath+"/sign-in", { email: email, password: password }, function (data) {
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
    $.get(contextPath+"/log-out", function (data) {
        if (data.status == 0) {
            isLogin = false;
            $(".after-login").hide();
            $('.before-login').show();
            $('#survey-password').val('');
            location.reload();
        }
    });
});

$('#survey-signup').click(function(){
	var nickname = $("#r-name").val();
	var email = $("#r-mail").val();
	var pwd1 = $("#r-pwd1").val();
	var pwd2 = $("#r-pwd2").val();
	if((nickname == "")||(email == "")||(pwd1 == "")||(pwd2 == "")) {
		$.pnotify({
                title: '注册失败...',
                text: '请填写完整信息...',
                icon: false,
                type: 'error'
        });
		return;
	}
	if(!checkMail(email)) {
		$.pnotify({
                title: '注册失败...',
                text: '请填写正确的邮箱信息...',
                icon: false,
                type: 'error'
        });
		return;
	}
	if(pwd1 != pwd2) {
		$.pnotify({
                title: '注册失败...',
                text: '请保证两次输入的密码一致...',
                icon: false,
                type: 'error'
        });
		return;
	}
	$.post(contextPath+"/sign-up", { nickname: nickname, email: email, password: pwd1 }, function (data) {
		if(data.status == 0) {
            location.reload();
		} else {
			$.pnotify({
                title: '注册失败...',
                text: '服务器异常，请稍候...',
                icon: false,
                type: 'error'
        });
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
    $.get(contextPath+"/next-page", function (data) {
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

function renderQuestionnaire() {
    /*
	isBusy = true;
	$.get(contextPath+"/get-answer", function(data){
		if(data.status == 0) {
			//success
			var renderQuestionnaireId = data.questionnaireId;
			var jsonString = data.content;
			$("#form-"+renderQuestionnaireId).unSerializeObjectFromJson(jsonString);
		}else if (data.status == 1) {
			//not submit yet
		}else if (data.status == 2) {
			//need to sign in
		}else if (data.status == 3) {
			//user does not exist
		}
	});
	*/
}

function updateMenu() {
    isBusy = true;
    $.get(contextPath+"/get-menu", function (data) {
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
            $.post(contextPath+"/submit-answer",
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

function checkMail(mailToCheck) {
	var reg = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/; 
	return reg.test(mailToCheck);
}