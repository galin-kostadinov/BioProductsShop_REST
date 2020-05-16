$(function () {
    loadProduct();
});

const PRODUCT_NOT_FOUND = "Product not found!";

function loadProduct() {
    const productId = $('#delete-product').attr('label');

    $.ajax({
        type: 'GET',
        url: '/api/product/delete/' + productId,
        data: 'json',
        success: function (product) {
            addProductDOM(product, productId);
        },
        error: function (request, status, error) {
            alert(PRODUCT_NOT_FOUND);
        }
    })
}

function addProductDOM({name, description, imgUrl, price}, productId) {
    $('#delete-product')
        .append($('<div/>')
            .addClass('col col-md-4 text-right pr-5')
            .append($('<img/>', {src: imgUrl})
                .width('300px')
                .height('300px')
            )
        )
        .append($('<div/>')
            .addClass('col col-md-6 text-left pl-5')
            .append($('<ul/>')
                .attr('id', 'product-delete-ul')
                .append($('<li/>')
                    .append($('<h3/>')
                        .text(name)
                    )
                )
                .append($('<li/>')
                    .addClass('text-left pb-3 pt-3')
                    .append($('<h6/>')
                        .text(description)
                    )
                )
                .append($('<li/>')
                    .addClass('text-left pb-3')
                    .append($('<h5/>')
                        .text('Price: ' + price + ' $')
                    )
                )
                .append($('<li/>')
                    .append($('<form/>', {action: '/api/product/delete/' + productId, method: 'POST'})
                        .append($('<div/>')
                            .addClass('button-holder d-flex text-left')
                        )
                        .append($('<button/>')
                            .addClass('btn btn-danger font-weight-bold text-white')
                            .attr('type', 'submit')
                            .text('Delete')
                        )
                    )
                )
            )
        );
}



