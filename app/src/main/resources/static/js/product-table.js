const URLS = {
    products: '/api/product/product-table'
};

$(function () {
    loadAllProduct();
});

function loadAllProduct() {
    $.ajax({
        type: 'GET',
        url: URLS.products,
        data: 'json',
        success: function (products) {
            $.each(products, function (i, product) {
                addProductDOM(product, i);
            })
            loader.hide();
        }
    })
}

function addProductDOM({id, name, made, imgUrl, price, promotionalPrice}, i) {
    $('#product-table')
        .append($('<tr/>')
            .addClass('row mx-auto')
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(i + 1)
            )
            .append($('<td/>')
                .addClass('col-md-2')
                .append($('<div/>')
                    .addClass('text-center')
                    .append($('<img/>', {src: imgUrl, alt: 'img'})
                        .width('40px')
                        .height('40px')
                    )
                )
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(name))
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(made)
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(price.toFixed(2) + ' $')
            )
        )
    ;

    if (promotionalPrice == null) {
        $('#product-table tr:last-child')
            .append($('<td/>')
                .addClass('col-md-3 text-center font-italic')
                .text('Without current promotion')
            );
    } else {
        $('#product-table tr:last-child')
            .append($('<td/>')
                .addClass('col-md-3 text-center font-italic')
                .text(promotionalPrice.toFixed(2) + ' $')
            );

    }

    $('#product-table tr:last-child')
        .append($('<td/>')
            .addClass('col-md-12 text-left')
            .append($('<block/>')
                .append($('<a/>', {href: '/product/details/' + id})
                    .addClass('btn btn-secondary glow-button font-weight-bold text-white mr-2')
                    .text('Details')
                )
            )
            .append($('<block/>')
                .append($('<a/>', {href: '/product/edit/' + id})
                    .addClass('btn btn-success glow-button font-weight-bold text-white mr-2')
                    .text('Edit')
                )
            )
            .append($('<block/>')
                .append($('<a/>', {href: '/product/delete/' + id})
                    .addClass('btn btn-danger glow-button font-weight-bold text-white mr-2')
                    .text('Delete')
                )
            )
        );

    if (promotionalPrice == null) {
        $('#product-table tr:last-child td:last-child')
            .append($('<block/>')
                .append($('<a/>', {href: '/product/promote/' + id})
                    .addClass('btn btn-primary glow-button font-weight-bold text-white mr-2')
                    .text('Promote')
                )
            )
    }
}

