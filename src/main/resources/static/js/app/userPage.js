function userPageCheck() {
    const userNo = $('#user-id').val();
    const blogger = $('#blogger-no').val();

    if (userNo !== blogger) {
        const postOpenStates = $('input[name=post-open-state]');
        postOpenStates.each(function (index) {
            const element = $(this).val();
            if (element === "CLOSE") {
                $('#posts-table td')[index * 5 + 1].innerHTML = "비공개입니다";
            }
        });

        const likedPostOpenStates = $('input[name=liked-post-open-state]');
        likedPostOpenStates.each(function (index) {
            const element = $(this).val();
            if (element === "CLOSE") {
                $('#liked-posts-table td')[index * 4 + 1].innerHTML = "비공개입니다";
            }
        });
    }
}