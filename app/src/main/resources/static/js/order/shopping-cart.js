$(function () {
    loadShoppingCart();
});

function loadShoppingCart() {
    $.ajax({
        type: 'GET',
        url: '/api/order/cart',
        data: 'json',
        success: function (cart, status, xhr) {
            const cartTotalPrice = xhr.getResponseHeader("cartTotalPrice");
            $.each(cart, function (i, orderProduct) {
                addProductDOM(orderProduct);

            })
            addTotalPrice(cartTotalPrice);
            addBuyingForm();
            loader.hide();
        }
    })
}

function addProductDOM({product, quantity}) {
    const id = product.id;
    const name = product.name;
    const imgUrl = product.imgUrl;
    const price = product.price;
    const promotionalPrice = product.promotionalPrice;

    $('#shopping-cart')
        .append($('<output/>')
            .addClass('col col-md-3 text-center pb-md-4')
            .append(
                $('<h3/>')
                    .text(name)
            )
            .append(
                $('<img/>', {src: imgUrl})
                    .addClass('mb-md-3 mt-md-3')
                    .width('100px')
                    .height('100px')
            )
        );

    if (promotionalPrice == null) {
        $('#shopping-cart output:last-child')
            .append($('<h5/>')
                .addClass('text-left pl-4')
                .text('Price: ' + price.toFixed(2) + ' $')
            );
    } else {
        $('#shopping-cart output:last-child')
            .append($('<h5/>')
                .addClass('text-left pl-4')
                .text('Price: ' + promotionalPrice.toFixed(2) + ' $')
            );
    }

    $('#shopping-cart output:last-child')
        .append($('<h5/>')
            .addClass('text-left pl-4')
            .text('Quantity: ' + quantity + 'pcs.')
        )
        .append($('<form/>', {action: '/api/order/remove-from-cart/' + id, method: 'POST'})
            .addClass('mx-auto')
            .append($('<button/>')
                .addClass('btn warning btn-primary low-button font-weight-bold text-white')
                .attr('type', 'submit')
                .text('Remove from Cart')
            )
        );
}

function addTotalPrice(cartTotalPrice) {
    $('#shopping-cart')
        .append($('<hr/>')
            .addClass('hr-2 bg-dark')
        )
        .append($('<h4/>')
            .addClass('text-center pl-4')
            .text('Total price: ' + cartTotalPrice + ' $')
        );
}

function addBuyingForm() {
    $('#shopping-cart')
        .append($('<form/>', {action: '/api/order/buy', method: 'POST'})
            .addClass('mt-md-3 mb-md-5')
            .append($('<button/>')
                .addClass('btn warning btn-warning low-button font-weight-bold text-white')
                .attr('type', 'submit')
                .text('Buy Products')
            )
        );
}