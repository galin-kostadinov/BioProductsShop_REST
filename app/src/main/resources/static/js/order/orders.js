$(function () {
    loadAllOrders();
});

function loadAllOrders() {
    $.ajax({
        type: 'GET',
        url: '/api/order/orders',
        data: 'json',
        success: function (orders) {
            $.each(orders, function (i, order) {
                addOrderMainInfoDOM(order);
                addOrderTableDOM(order);
                addOrderTotalPriceDOM(order);
            })
            loader.hide();
        }
    });
}

function addOrderMainInfoDOM(order) {
    const blockOrderSelectorId = 'block-order-' + order.id
    $('#all-orders')
        .append($('<block>')
            .attr('id', blockOrderSelectorId)
            .addClass('col col-md-12')
            .append($('<div/>')
                .addClass('order-main-info col col-md-12 text-left pt-3 pl-5')
                .append($('<ul/>')
                    .append($('<li/>')
                        .append($('<h5/>')
                            .text('Order ID: ' + order.id)
                        )
                    )
                    .append($('<li/>')
                        .append($('<h5/>')
                            .text('Date: ' + order.dateCreated)
                        )
                    )
                    .append($('<li/>')
                        .append($('<h5/>')
                            .text('Status: ' + order.status)
                        )
                    )
                )
            )
        );
}

function addOrderTableDOM({orderProducts, id}) {
    const orderSelectorId = 'tbody-id-' + id;

    $('#all-orders block:last-child')
        .append($('<div/>')
            .addClass('col col-md-12 text-left pt-3')
            .append($('<table/>')
                .addClass('table table-hover table-bordered mx-auto')
                .append($('<thead/>')
                    .append($('<tr/>')
                        .addClass('row mx-auto')
                        .append($('<th/>')
                            .addClass('col-md-1 text-center')
                            .text('#')
                        )
                        .append($('<th/>')
                            .addClass('col-md-1 text-center')
                            .text('Image')
                        )
                        .append($('<th/>')
                            .addClass('col-md-4 text-center')
                            .text('Name')
                        )
                        .append($('<th/>')
                            .addClass('col-md-2 text-center')
                            .text('Quantity')
                        )
                        .append($('<th/>')
                            .addClass('col-md-2 text-center')
                            .text('Price')
                        )
                        .append($('<th/>')
                            .addClass('col-md-2 text-center')
                            .text('Total Price')
                        )
                    )
                )
                .append($('<tbody/>')
                    .attr('id', orderSelectorId)
                )
            )
        );

    $.each(orderProducts, function (i, orderProduct) {
        addOrderTableMainInfoDOM(orderProduct, i, orderSelectorId);
    });
}

function addOrderTableMainInfoDOM(orderProduct, rowIndex, orderSelectorId) {
    $('#' + orderSelectorId)
        .append($('<tr/>')
            .addClass('row mx-auto')
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(rowIndex + 1)
            )
            .append($('<td/>')
                .addClass('col-md-1')
                .append($('<div/>')
                    .addClass('text-center')
                    .append($('<img/>', {src: orderProduct.product.imgUrl, alt: 'img'})
                        .width('40px')
                        .height('40px')
                    )
                )
            )
            .append($('<td/>')
                .addClass('col-md-4 text-center')
                .text(orderProduct.product.name)
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(orderProduct.quantity)
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(orderProduct.product.price.toFixed(2) + ' $')
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(orderProduct.totalPrice.toFixed(2) + ' $')
            )
        );
}

function addOrderTotalPriceDOM(order) {
    const blockOrderSelectorId = 'block-order-' + order.id;

    $('#' + blockOrderSelectorId)
        .append($('<div/>')
            .addClass('order-total-price col col-md-12 text-right')
            .append($('<h5/>')
                .addClass('pr-4')
                .text('Total price: ' + order.totalPrice.toFixed(2) + ' $')
            )
            .append($('<hr/>')
                .addClass('hr-2 bg-dark')
            )
        );
}