function categorySave(categoryType) {
    const category = $(`#${categoryType}-list`);
    category.appendChild(
        `<div class="form-group">
                <input type="text" class="form-control" id="${categoryType}">
                <button type="button" class="btn btn-primary" id="upper-save" onClick="saveComplete('${categoryType}')">수정</button>
                </div>`
    );
}

function saveComplete(categoryType) {

}

function categoryUpdate(categoryType, categoryNo) {
    let dto;
    let url;
    if (categoryType === 'upper-category') {
        dto = {
            upperCategoryNo: categoryNo,
            name: $(`#upper-category-${categoryNo}`).val()
        }
        url = `/category/upper`;
    } else {
        dto = {
            lowerCategoryNo: categoryNo,
            name: $(`#lower-category-${categoryNo}`).val()
        }
        url = `/category/lower`;
    }

    $.ajax({
        type: 'PUT',
        url: url,
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(dto)
    }).done(function (result) {
        alert("변경 완료되었습니다.");
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}
function categoryDelete(categoryType, categoryNo) {
    const url = (categoryType === 'upper-category') ?
        `/category/upper?no=${categoryNo}` : `/category/lower?no=${categoryNo}`;

    if(confirm("정말 삭제하시겠습니까?") === true) {
        $.ajax({
            type: 'DELETE',
            dataType: 'json',
            url: url,
            contentType: 'application/json; charset=UTF-8'
        }).done(function (result) {

        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
    }
}

function userUpdate() {}

function updateCansel() {
    history.back();
}