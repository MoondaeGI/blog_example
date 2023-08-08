function userCheck() {
    const bloggerNo = $('#blogger-no').val();
    const userNo = $('#user-no').val();
    const postNo = $('#post-no').val();

    if (bloggerNo === userNo) {
        const saveButton = $('#btn-post-save');
        saveButton.append(`<a href="/post-save?no=${bloggerNo}" role="button" class="btn btn-primary">글 등록</a>`);

        const postSelectorButton = $('#post-button-selector');
        postSelectorButton.append(`<a href="/post-update?no=${bloggerNo}" role="button" class="btn btn-primary">글 수정</a>`);
        postSelectorButton.append(`<button type="button" class="btn btn-secondary" id="post-delete" onclick="postDelete(${postNo})">삭제</button>`);
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

        const postLiked = $('#post-liked');
        $.ajax({
            type: 'GET',
            url: `/post/liked?post=${postNo}&user=${userNo}`,
            contentType: 'application/json; charset=UTF-8'
        }).done(function (result) {
            if (result === true) {
                postLiked.append(`<button type="button" class="btn btn-secondary" id="btn-post-liked" onclick="postLiked(${userNo}, ${postNo})">싫어요</button>`);
            } else {
                postLiked.append(`<button type="button" class="btn btn-primary" id="btn-post-liked" onclick="postLiked(${userNo}, ${postNo})">좋아요</button>`);
            }
        })
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
        console.log($('#comment-' + commentNo + '-content').text());

        const commentButtonSelector = $('#btn-comment-selector-' + commentNo);
        commentButtonSelector.append(`<button type="button" class="btn btn-primary" id="btn-comment-update" onclick="commentUpdate(${commentNo})">수정</button>`);
        commentButtonSelector.append(`<button type="button" class="btn btn-secondary" id="btn-comment-delete" onclick="commentDelete(${commentNo})">삭제</button>`);
    })

    const button = $('#btn-post-save');
    button.append(`<a href="/post-save?no=${bloggerNo}" role="button" class="btn btn-primary">글 등록</a>`);

    const postSelectorButton = $('#post-button-selector');
    postSelectorButton.append(`<a href="/post-update?user=${bloggerNo}&post=${postNo}" role="button" class="btn btn-primary">글 수정</a>`);
    postSelectorButton.append(`<button type="button" class="btn btn-secondary" id="post-delete" onclick="postDelete(${postNo})">삭제</button>`);

    const testUserNo = 26;

    const postLiked = $('#post-liked');
    $.ajax({
        type: 'GET',
        url: `/post/liked?post=${postNo}&user=${testUserNo}`,
        contentType: 'application/json; charset=UTF-8'
    }).done(function (result) {
        if (result === true) {
            postLiked.append(`<button type="button" class="btn btn-secondary" id="btn-post-liked" onclick="postLiked(${testUserNo}, ${postNo})">싫어요</button>`);
        } else {
            postLiked.append(`<button type="button" class="btn btn-primary" id="btn-post-liked" onclick="postLiked(${testUserNo}, ${postNo})">좋아요</button>`);
        }
    })
}

function postLiked(userNo, postNo) {
    $.ajax({
        type: 'GET',
        url: `/post/state/liked?post=${postNo}&user=${userNo}`,
        contentType: 'application/json; charset=UTF-8'
    }).done(function (result) {
        if (result === "LIKED") {
            alert("게시글에 좋아요 표시했습니다.");
        } else {
            alert("게시글 좋아요 표시를 취소했습니다.");
        }
    }).fail(function (error) {
        alert(JSON.stringify(error))
    }).always(function () {
        window.location.reload();
    })
}

function postDelete(postNo) {
    if (confirm("게시글을 삭제하시겠습니까?") === true) {
        $.ajax({
            type: 'DELETE',
            dataType: 'json',
            url: `/post?no=${postNo}`,
            contentType: 'application/json; charset=UTF-8',
        }).done(function () {
            alert("게시글이 삭제되었습니다.");
        }).fail(function (error) {
            alert(JSON.stringify(error));
        }).always(function () {
            window.location.reload();
        });
    } else {
        return false;
    }
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
    const text = commentContent.text();

    commentContent.empty();
    commentContent.append(`<textarea type="text" className="form-control" id="content">${text}</textarea>`);

    const commentButtonSelector = $('#btn-comment-selector-' + commentNo);
    commentButtonSelector.empty();
    commentButtonSelector.append(`<button type="button" class="btn btn-primary" id="btn-update-complete" onclick="updateComplete(${commentNo})">완료</button>`);
    commentButtonSelector.append(`<button type="button" class="btn btn-secondary" id="btn-update-cancel" onclick="window.location.reload()">취소</button>`);
}

function updateComplete(commentNo) {
    const content = $('#content').val();

    const dto = {
        commentNo: commentNo,
        content: content
    };

    $.ajax({
        type: 'PUT',
        url: '/comment',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(dto)
    }).done(function () {
        alert("댓글이 수정되었습니다.");
    }).fail(function (error) {
        alert(JSON.stringify(error));
    }).always(function () {
        window.location.reload();
    })
}

function commentDelete(commentNo) {
    if (confirm("댓글을 삭제하시겠습니까?") === true) {
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
    } else {
        return false;
    }
}