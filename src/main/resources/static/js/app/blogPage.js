function userCheck() {
    const bloggerNo = $('#blogger-no').val();
    const userNo = $('#user-no').val();

    if (bloggerNo === userNo) {
        const button = $('#btn-post-save');
        button.append(`<a href="/post-save?no=${bloggerNo}" role="button" class="btn btn-primary">글 등록</a>`);
    } else {
        const openStates = $('input[name=open-state]');
        openStates.each(function (index) {
            const element = $(this).val();
            console.log(element);
            console.log(index);
            if (element === "CLOSE") {
                $('#posts-table td')[index * 4 + 1].innerHTML = "비공개입니다";
            }
        });
    }

    const commentsUserNo = $('input[name=comments-user-no]');
    commentsUserNo.each(function (index) {
        const element = $(this).val();
        const commentButtonSelector = $('#btn-comment-selector');
        if (element === userNo) {
            const commentNo = $('input[name=comments-comment-no]').eq(index).val();

            commentButtonSelector.append(`<button type="button" class="btn btn-primary" id="comment-update" onclick="commentUpdate(${commentNo})">수정</button>`);
            commentButtonSelector.append(`<button type="button" class="btn btn-secondary" id="comment-delete" onclick="commentDelete(${commentNo})">삭제</button>`);
        }
    })

    commentsUserNo.each(function (index) {
        const element = $(this).val();

        console.log(element);
        const commentNo = $('input[name=comments-comment-no]').eq(index).val();
        console.log(commentNo);
        console.log($('#comment-' + commentNo + '-content'));

        const commentButtonSelector = $('#btn-comment-selector-' + commentNo);
        commentButtonSelector.append(`<button type="button" class="btn btn-primary" id="btn-comment-update" onclick="commentUpdate(${commentNo})">수정</button>`);
        commentButtonSelector.append(`<button type="button" class="btn btn-secondary" id="btn-comment-delete" onclick="commentDelete(${commentNo})">삭제</button>`);
    })

    const button = $('#btn-post-save');
    button.append(`<a href="/post-save?no=${bloggerNo}" role="button" class="btn btn-primary">글 등록</a>`);
}

function commentSave() {
    const userNo = $('#user-no').val();

    const dto = {
        userNo: userNo,
        postNo: $('#post-no').val(),
        content: $('#comment').val()
    };

    $.ajax({
        type: 'POST',
        dataType: 'json',
        url: '/comment',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(dto)
    }).done(function () {
        alert('댓글을 입력하셨습니다.');
    }).fail(function (error) {
        alert(JSON.stringify(error));
    }).always(function () {
        window.location.reload();
    });
}

function commentUpdate(commentNo) {
    const commentContent = $('#comment-' + commentNo + '-content');
    commentContent.innerHTML = `<textarea type="text" placeholder="수정할 댓글을 입력하십시오"></textarea>`;

    const commentButtonSelector = $('#btn-comment-selector-' + commentNo);
    commentButtonSelector.empty();
    commentButtonSelector.append(`<button type="button" class="btn btn-primary" id="btn-update-complete" onclick="commentUpdate(${commentNo})">완료</button>`);
    commentButtonSelector.append(`<button type="button" class="btn btn-secondary" id="btn-update-cancel" onclick="window.location.reload()">삭제</button>`);

    const dto = {
        commentNo: commentNo,
        content: $('#comment').val()
    };

    $.ajax({
        type: 'PUT',
        dataType: 'json',
        url: '/comment',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(dto)
    }).done()
}

function commentDelete(commentNo) {
    const commentsTable = $('#comments-table');

    $.ajax({
        type: 'DELETE',
        dataType: 'json',
        url: `/comment?no=${commentNo}`,
        contentType: 'application/json; charset=UTF-8',
    }).done(function () {
        alert("댓글이 삭제되었습니다.");
    }).fail(function (error) {
        alert(JSON.stringify(error));
    }).always(function () {
        window.location.reload();
    });
}