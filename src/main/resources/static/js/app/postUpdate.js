function postUpdateCheck() {
    const openState = $('input[name=open-state]').val();

    const openStateCheckBox = $('#open-state-selector');
    if (openState === "OPEN") {
        openStateCheckBox.append(`<input type="checkbox" id="checkbox-open-state" name="open-state">`);
    } else {
        openStateCheckBox.append(`<input type="checkbox" id="checkbox-open-state" name="open-state" checked>`);
    }

    const upperCategoryNo = $('input[name=upper-category]').val();
    const lowerCategoryNo = $('input[name=lower-category]').val();

    $('#upper-category').val(upperCategoryNo).prop("selected", true);
    $('#lower-category').val(lowerCategoryNo).prop("selected", true);
}

function postUpdate() {
    const openState = ($('#open-state').is(':checked') === true) ? `CLOSE` : `OPEN`;
    const upperCategoryNo = selectResult('upper-category');
    const lowerCategoryNo = selectResult('lower-category');

    console.log(
        `post-no=${$('input[name=post-no]').val()}\n
        upper-category=${upperCategoryNo}\n
        lower-category=${lowerCategoryNo}\n
        title=${$('#title').val()}\n
        content=${$('#content').val()}\n
        open-state=${openState}`);

    const dto = {
        postNo: $('input[name=post-no]').val(),
        title: $('#title').val(),
        content: $('#content').val(),
        upperCategoryNo: upperCategoryNo,
        lowerCategoryNo: lowerCategoryNo,
        openState: openState
    };
    const blob = new Blob([JSON.stringify(dto)], {type: 'application/json'})
    const file =$('#files').get(0).files[0];

    const formData =new FormData();
    formData.append("dto", blob);
    formData.append("files", file);

    $.ajax({
        type: 'POST',
        url: '/post/update',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        data: formData
    }).done(function () {
        history.back();
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function updateCansel() {
    if(confirm("게시글 수정을 취소하시겠습니까?") === true) {
        history.back();
    }
}

function categorySelect() {
    const upperCategoryNo = $(`#upper-category option:selected`).val();
    const lowerCategory = $('#lower-category');

    if (upperCategoryNo === 'not-select') {
        lowerCategory.children().remove();
        lowerCategory.append('<option value="not-select">--</option>');
    } else {
        $.ajax({
            type: 'GET',
            datatype: 'json',
            url: `/category/lower/list/upper?no=${upperCategoryNo}`,
            contentType: 'application/json; charset=UTF-8'
        }).done(function (result) {
            lowerCategory.children().remove();

            lowerCategory.append('<option value="not-select">--</option>');
            result.forEach(function (element) {
                lowerCategory.append(`<option value=${element.lowerCategoryNo}>${element.name}</option>`);
            });
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

function selectResult(categoryType) {
    const categorySelect = $(`#${categoryType} option:selected`).val();

    return (categorySelect === 'not-select' || categorySelect === undefined) ?
        $(`input[name=${categoryType}]`).val() : categorySelect;
}