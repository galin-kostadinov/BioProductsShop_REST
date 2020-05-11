$(function () {
    loadAllPromotedProducts();
});

function loadAllPromotedProducts() {
    $.ajax({
        type: 'GET',
        url: URLS.promotionTable,
        data: 'json',
        success: function (products) {
            addTitleDOM()
            $.each(products, function (i, product) {
                addProductDOM(product);
            })
            loader.hide();
        }
    })
}

function addTitleDOM() {
    $('#index-promoted-products')
        .append($('<h2/>')
            .addClass('text-center')
            .text('Our weekly offers:')
        )
        .append($('<hr/>')
            .addClass('hr-2 bg-dark')
        )
        .append($('<block/>')
            .append($('<div/>')
                .addClass('row')
                .append($('<div/>')
                    .addClass('col col-md-12 text-center p-0')
                    .attr('id', 'index-promoted-products-row')
                )
            )
        );
}

function addProductDOM({id, name, made, imgUrl, price, promotionalPrice}) {
    $('#index-promoted-products-row')
        .append($('<output/>')
            .addClass('col col-md-4 col-sm-12 text-center pb-md-5 ml-md-0 mr-md-0')
            .append($('<div/>')
                .addClass('col col-md-12 text-center')
                .append(
                    $('<h2/>')
                        .addClass('col col-md-12')
                        .text(name)
                )
                .append(
                    $('<h4/>')
                        .addClass('col col-md-12')
                        .text('Made: ' + made)
                )
                .append(
                    $('<img/>', {src: imgUrl})
                        .addClass('col col-md-12 mb-md-3 mt-md-3')
                        .width('300px')
                        .height('300px')
                )
                .append($('<div/>')
                    .addClass('col col-md-12 mb-md-2 h6 discount-price')
                    .text('Old Price: ' + price.toFixed(2) + ' $')
                )
                .append($('<div/>')
                    .addClass('col col-md-12 mb-md-2 h5')
                    .text('Price: ' + promotionalPrice.toFixed(2) + ' $')
                )
                .append($('<block/>')
                    .addClass('col col-md-12')
                    .append($('<a/>', {href: '/product/details/' + id})
                        .addClass('btn btn-secondary glow-button font-weight-bold text-white')
                        .text('Details')
                    )
                )
            )
        );
}



