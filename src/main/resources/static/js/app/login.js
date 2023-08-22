function login() {
    const dto = {
        email: $('#emil').val(),
        password: $('#password').val()
    };

    $.ajax({
        type: 'POST',
        url: '/auth/login',
        data: JSON.stringify(dto),
        contentType: 'application/json; charset=UTF-8'
    }).done(function (result) {
        alert("로그인이 완료되었습니다");
        location.href = "/";

        console.log(result);
    })
}