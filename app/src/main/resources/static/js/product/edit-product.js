$(function () {
    loadProduct();
});

function loadProduct() {
    const productId = $('#edit-product').attr('label');

    $.ajax({
        type: 'GET',
        url: '/api/product/edit/' + productId,
        data: 'json',
        success: function (product) {
            addProductDOM(product, productId);
        }
    })
}

function addProductDOM({name, description, imgUrl, price}, productId) {
    $('#edit-product')
        .append($('<form/>', {action: '/api/product/edit/' + productId, method: 'POST'})
            .attr('id', 'edit-product-form')
            .addClass('mx-auto w-25 mb-5')
            .append($('<div/>')
                .addClass('form-group')
                .append($('<div/>')
                    .addClass('label-holder textCol d-flex justify-content-center')
                    .append($('<label/>')
                        .addClass('h4 mb-2 text-blue')
                        .attr('for', 'name')
                        .text('Name')
                    )
                )
                .append($('<input/>')
                    .addClass('form-control')
                    .attr('type', 'text')
                    .attr('id', 'name')
                    .attr('name', 'name')
                    .val(name)
                )
            )
            .append($('<div/>')
                .addClass('form-group')
                .append($('<div/>')
                    .addClass('label-holder textCol d-flex justify-content-center')
                    .append($('<label/>')
                        .addClass('h4 mb-2')
                        .attr('for', 'description')
                        .text('Description')
                    )
                )
                .append($('<textarea/>')
                    .addClass('form-control')
                    .attr('id', 'description')
                    .attr('name', 'description')
                    .attr('style', 'height:150px;')
                    .text(description)
                )
            )
            .append($('<div/>')
                .addClass('form-group')
                .append($('<div/>')
                    .addClass('label-holder textCol d-flex justify-content-center')
                    .append($('<label/>')
                        .addClass('h4 mb-2')
                        .attr('for', 'imgUrl')
                        .val('Image url')
                    )
                )
                .append($('<input/>')
                    .addClass('form-control')
                    .attr('type', 'url')
                    .attr('id', 'imgUrl')
                    .attr('name', 'imgUrl')
                    .val(imgUrl)
                )
            )
            .append($('<div/>')
                .addClass('form-group')
                .append($('<div/>')
                    .addClass('label-holder textCol d-flex justify-content-center')
                    .append($('<label/>')
                        .addClass('h4 mb-2')
                        .attr('for', 'price')
                        .text('Regular Price')
                    )
                )
                .append($('<input/>')
                    .addClass('form-control')
                    .attr('type', 'number')
                    .attr('step', '0.01')
                    .attr('id', 'price')
                    .attr('name', 'price')
                    .val(price)
                )
            )
            .append($('<div/>')
                .addClass('button-holder d-flex justify-content-center')
                .append($('<button/>')
                    .addClass('btn btn-danger font-weight-bold text-white')
                    .attr('type', 'submit')
                    .text('Edit')
                )
            )
        )
}



