function cansel() {
    history.back();
}

function duplicateCheck (target) {
    let url;
    let checkTarget;

    switch(target) {
        case 'email':
            url = `/user/email?email=${$('#email').val()}`;
            checkTarget = {
                button: $('#btn-email-check'),
                description: '이메일'
            }
            break;
        case 'name':
            url = `/user/name?name=${$('#name').val()}`;
            checkTarget = {
                button: $('#btn-name-check'),
                description: '이름'
            }
            break;
        case 'blogName':
            url = `/user/blog?name=${$('#blog-name').val()}`;
            checkTarget = {
                button: $('#btn-blog-name-check'),
                description: '블로그 이름'
            }
            break;
    }

    console.log(url);
    console.log(checkTarget);

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: url,
        contentType: 'application/json; charset=UTF-8'
    }).done(function (result) {
        if (!result) {
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

function search() {
    const searchType = $('#keyword-select option:selected').val();
    const keyword = $('#keyword').val();

    const url = (searchType === 'title') ? '/search?title=' : '/search?content=';
    location.href = url + keyword;
}