function postSave() {
    const userNo = $('#user-id').val();
    const openState = ($('#open-state').is(':checked') === true) ? `CLOSE` : `OPEN`;

    const dto = {
        title: $('#title').val(),
        content: $('#content').val(),
        userNo: userNo,
        upperCategoryNo: $('#upper-category option:selected').val(),
        lowerCategoryNo: $('#lower-category option:selected').val(),
        openState: openState
    };
    const blob = new Blob([JSON.stringify(dto)], {type: 'application/json'})
    const file =$('#files').get(0).files[0];

    const formData =new FormData();
    formData.append("dto", blob);
    formData.append("files", file);

    $.ajax({
        type: 'POST',
        url: '/post',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        data: formData
    }).done(function (result) {
        alert("게시글이 저장되었습니다.");
        window.location.href = `/blog-page?no=${userNo}&post=${result}`;
    }).fail(function (error) {
        alert(JSON.stringify(error));
        history.back();
    });
}

function saveCansel() {
    history.back();
}

function categorySelect() {
    const upperCategoryNo = $(`#upper-category option:selected`).val();

    $.ajax({
        type: 'GET',
        datatype: 'json',
        url: `/category/lower/list/upper?no=${upperCategoryNo}`,
        contentType: 'application/json; charset=UTF-8'
    }).done(function (result) {
        const lowerCategory = $('#lower-category');
        lowerCategory.children().remove();

        lowerCategory.append('<option>--</option>');
        result.forEach(function (element) {
            lowerCategory.append(`<option value=${element.lowerCategoryNo}>${element.name}</option>`);
        });
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}