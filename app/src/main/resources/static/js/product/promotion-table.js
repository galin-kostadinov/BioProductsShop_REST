$(function () {
    loadAllPromotions();
});

function loadAllPromotions() {
    $.ajax({
        type: 'GET',
        url: '/api/product/promotion-table',
        data: 'json',
        success: function (products) {
            $.each(products, function (i, product) {
                addProductDOM(product, i);
            })
            loader.hide();
        }
    })
}

function addProductDOM({id, name, made, imgUrl, price, priceDiscount, promotionalPrice}, i) {
    $('#promotion-table')
        .append($('<tr/>')
            .addClass('row mx-auto')
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(i + 1)
            )
            .append($('<td/>')
                .addClass('col-md-1')
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
                .addClass('col-md-1 text-center')
                .text(made)
            )
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(price.toFixed(2) + ' $')
            )
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(priceDiscount.discount + ' %')
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(priceDiscount.fromDate)
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(priceDiscount.toDate)
            )
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(promotionalPrice.toFixed(2) + ' $')
            )
        );

    $('#promotion-table tr:last-child')
        .append($('<td/>')
            .addClass('col-md-12 text-left')
            .append($('<block/>')
                .append($('<form/>', {action: '/api/product/remove-promotion/' + id, method: 'POST'})
                    .append($('<button/>')
                        .addClass('btn btn-danger glow-button font-weight-bold text-white')
                        .attr('type', 'submit')
                        .text('Remove Promotion')
                    )
                )
            )
        );
}

