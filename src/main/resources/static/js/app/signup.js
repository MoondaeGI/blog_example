

function duplicateCheck (target) {
    let url;
    let checkTarget;

    switch(target) {
        case 'email':
            url = `/user/email?email=${$('#email').value}`;
            checkTarget = {
                button: $('#btn-email-check'),
                description: '이메일'
            }
            break;
        case 'name':
            url = `/user/email?email=${$('#name').value}`;
            checkTarget = {
                button: $('#btn-name-check'),
                description: '이름'
            }
            break;
        case 'blogName':
            url = `/user/blog?name=${$('#blog-name').value}`;
            checkTarget = {
                button: $('#btn-blog-name-check'),
                description: '블로그 이름'
            }
            break;
    }

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: url,
        contentType: 'application/json; charset=UTF-8'
    }).done(function (result) {
        if (result) {
            alert('확인되었습니다.');

            const button = checkTarget.button;
            button.innerText = "확인 완료";
            button.disabled = true;
        } else {
            alert(`중복된 ${checkTarget.description}이 존재합니다.`);
        }
    }).fail(function (error) {
        alert(JSON.stringify(error));
    })
}

function signup() {
    const dto = {
        email: $('#email'),
        password: $('#password'),
        name: $('#name'),
        blogName: $('#blog-name')
    };

    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: '/auth/signup',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(dto)
    }).done(function () {
        alert("회원 가입이 완료되었습니다.");
    }).fail(function (error) {
        alert(JSON.stringify(error));
    }).finally(function () {
        window.location.href = '/';
    });
}
