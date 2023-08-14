function categorySave(categoryType) {
    const category = $(`#${categoryType}-list`);

    category.append(
        `<div class="form-group">
            <input type="text" class="form-control" id="${categoryType}-category-add">
                <div class="form-group">
                    <button type="button" class="btn btn-primary" id="add-category" onclick="categorySaveComplete('${categoryType}')">완료</button>
                    <button type="button" class="btn btn-secondary" id="lower-delete" onclick="categorySaveCansel()">취소</button>
                </div>
            </div>`);
}

function categorySaveComplete(categoryType) {
    if (confirm("카테고리를 추가하시겠습니까?") === true) {
        const name = $(`#${categoryType}-category-add`).val();
        const dto = {
            name: name
        }

        const url = (categoryType === 'upper-category') ? `/category/upper` : `/category/lower`;

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(dto),
            contentType: 'application/json; charset=UTF-8'
        }).done(function (result) {
            alert("카테고리가 추가되었습니다.");
            const target = $(`#${categoryType}-category-add`).parent();
            target.children().remove();

            target.append(`<div class="form-group">
                <input type="text" class="form-control" id="${categoryType}-${result}" value=${name}>
                    <div class="form-group">
                        <button type="button" class="btn btn-primary" id="${categoryType}-update" onClick="categoryUpdate('${categoryType}', ${result})">수정</button>
                        <button type="button" class="btn btn-secondary" id="${categoryType}-delete" onClick="categoryDelete('${categoryType}', ${result})">삭제</button>
                    </div>
            </div>`)

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

function categorySaveCansel() {}

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
    }).done(function () {
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