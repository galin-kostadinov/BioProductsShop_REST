$(function () {
    loadProduct();
});

function loadProduct() {
    const productId = $('#product-details').attr('label');

    $.ajax({
        type: 'GET',
        url: '/api/product/details/' + productId,
        data: 'json',
        success: function (product) {
            addProductDOM(product);
        }
    })
}

function addProductDOM({id, name, made, description, imgUrl, price, promotionalPrice}) {
    $('#product-details')
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
                .attr('id', 'product-details-ul')
                .append($('<li/>')
                    .append($('<h3/>')
                        .text(name)
                    )
                )
                .append($('<li/>')
                    .append($('<h5/>')
                        .text('Made: ' + made)
                    )
                )
                .append($('<li/>')
                    .addClass('text-left pb-3 pt-3')
                    .append($('<h6/>')
                        .text(description)
                    )
                )
            )
        );

    if (promotionalPrice == null) {
        $('#product-details-ul')
            .append($('<li/>')
                .addClass('text-left pb-3')
                .append($('<h5/>')
                    .text('Price: ' + price.toFixed(2) + ' $')
                )
            );
    } else {
        $('#product-details-ul')
            .append($('<li/>')
                .addClass('text-left')
                .append($('<h6/>')
                    .addClass('discount-price')
                    .text('Old Price: ' + price.toFixed(2) + ' $')
                )
            )
            .append($('<li/>')
                .addClass('text-left pb-3')
                .append($('<h5/>')
                    .text('Price: ' + promotionalPrice.toFixed(2) + ' $')
                )
            );

    }

    $('#product-details-ul')
        .append($('<li/>')
            .append($('<form/>', {action: '/order/add-to-cart/' + id, method: 'POST'})
                .append($('<div/>')
                    .addClass('col col-md-5 h5 p-0')
                    .append($('<label/>')
                        .attr('for', 'quantity')
                        .text('Quantity: ')
                    )
                    .append($('<input/>')
                        .addClass('ml-2')
                        .attr('type', 'number')
                        .attr('size', '3px')
                        .attr('id', 'quantity')
                        .attr('name', 'quantity')
                        .attr('placeholder', '0')
                        .attr('min', '1')
                        .prop('required', true)
                    )
                )
                .append($('<button/>')
                    .addClass('btn warning btn-warning low-button font-weight-bold text-white mt-2 ml-2')
                    .attr('type', 'submit')
                    .text('Add to Cart')
                )
            )
        );
}



