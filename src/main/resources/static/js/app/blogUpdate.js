function categorySave(categoryType, upperCategoryNo) {
    const category = (categoryType === 'upper-category') ?
        $(`#${categoryType}-list`) : $(`#${categoryType}-list-${upperCategoryNo}`);

    category.append(
        `<div class="form-group">
            <input type="text" class="form-control" id="${categoryType}-category-add">
                <div class="form-group">
                    <button type="button" class="btn btn-primary" id="add-category" onclick="categorySaveComplete('${categoryType}', ${upperCategoryNo})">완료</button>
                    <button type="button" class="btn btn-secondary" id="lower-delete" onclick="categorySaveCansel()">취소</button>
                </div>
            </div>`);
}

function categorySaveComplete(categoryType, upperCategoryNo) {
    if (confirm("카테고리를 추가하시겠습니까?") === true) {
        const name = $(`#${categoryType}-category-add`).val();

        let dto;
        let url;
        if (categoryType === 'upper-category') {
            url = '/category/upper';
            dto =  {
                userNo: $('input[name=user-no]').val(),
                name: name
            }
        } else {
            url = '/category/lower';
            dto = {
                upperCategoryNo: upperCategoryNo,
                name: name
            }
        }

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(dto),
            contentType: 'application/json; charset=UTF-8'
        }).done(function (result) {
            alert("카테고리가 추가되었습니다.");
            const target = $(`#${categoryType}-category-add`).parent();
            target.children().remove();

            target.append(`<li id="li-${categoryType}-${result}">
                <div class="form-group">
                    <input type="text" class="form-control" id="${categoryType}-${result}" value=${name} onkeyup="">
                        <div class="form-group" id="${categoryType}-${result}-btn-selector">
                            <button type="button" class="btn btn-primary" id="${categoryType}-update" onClick="categoryUpdate('${categoryType}', ${result})">수정</button>
                            <button type="button" class="btn btn-secondary" id="${categoryType}-delete" onClick="categoryDelete('${categoryType}', ${result})">삭제</button>
                        </div>
                </div>
            </li>`);

            if (categoryType === 'upper-category') {
                const upperCategoryLi = $(`#li-upper-category-${result}`);
                upperCategoryLi.append(
                    `<ul id="lower-category-list-${result}"></ul>
                    <ul>
                        <div id="lower-category-save" class="form-group">
                            <button type="button" class="btn btn-primary" id="add-upper-category" onclick="categorySave('lower-category', ${result})">카테고리 추가</button>
                        </div>
                    </ul>`);
            }

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

function categorySaveCansel(categoryType, upperCategoryNo) {
    const target = (categoryType === 'upper-category') ?
        $(`#${categoryType}-list`) : $(`#${categoryType}-list-${upperCategoryNo} :last`);
    console.log(target);

    if (confirm("카테고리 생성을 취소하시겠습니까?") === true) {
        target.remove();
    }
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
    }).done(function () {
        alert("변경 완료되었습니다.");
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function addUpdateCanselButton(categoryType, categoryNo) {
    const target = $(`#${categoryType}-${categoryNo}-btn-selector`);
    target.append(
        `<button type="button" class="btn btn-secondary" id="${categoryType}-cansel-${categoryNo}" onClick="${categoryUpdateCansel(categoryType, categoryNo)}">취소</button>`);
}

function categoryUpdateCansel(categoryType, categoryNo) {
    if (confirm("수정 작업을 취소하시겠습니까?") === true) {
        const url = (categoryType === 'upper-category') ?
            `/category/upper?no=` : `/category/lower?no=`;

        $.ajax({
            type: 'GET',
            url: url + categoryNo,
            contentType: 'application/json; charset=UTF-8'
        }).done(function(result) {
            $(`#${categoryType}-${categoryNo}`).text(result);
            const canselButton = $(`#${categoryType}-cansel-${categoryNo}`);
            canselButton.remove();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
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
        }).done(function () {
            const target = $(`#li-${categoryType}-${categoryNo}`);
            target.remove();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

function userUpdate(userNo) {
    if (confirm("수정을 완료하시겠습니까?") === true) {
        console.log(`userNo: ${userNo}\nname: ${$('#name').val()}`)

        const dto = {
            userNo: userNo,
            name: $('#name').val(),
            blogName: $('#blog-name').val()
        }

        $.ajax({
            type: 'PUT',
            url: `/user`,
            contentType: 'application/json; charset=UTF-8',
            data: JSON.stringify(dto)
        }).done(function (result) {
            alert("수정 완료했습니다.");
            window.location.herf = `/user-page?no=${result}`;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}