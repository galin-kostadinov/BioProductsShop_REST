$(function () {
    loadProduct();
});

function loadProduct() {
    const productId = $('#promote-product').attr('label');

    $.ajax({
        type: 'GET',
        url: '/api/product/promote/' + productId,
        data: 'json',
        success: function (product, status, xhr) {
            const dataTimeNow = xhr.getResponseHeader("dataTimeNow");
            addProductDOM(product, dataTimeNow);
        }
    })
}

function addProductDOM({id, name, made, description, imgUrl, price}, dataTimeNow) {
    $('#promote-product')
        .append($('<div/>')
            .addClass('col col-md-4 text-right pr-5')
            .append($('<img/>', {src: imgUrl})
                .width('300px')
                .height('300px')
            )
        )
        .append($('<div/>')
            .addClass('col col-md-6 text-left pl-5 pt-0')
            .append($('<ul/>')
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
                .append($('<li/>')
                    .addClass('text-left')
                    .append($('<h5/>')
                        .text('Regular Price: ' + price + ' $')
                    )
                ).append($('<li/>')
                    .append($('<form/>', {action: '/api/product/promote/' + id, method: 'POST'})
                        .addClass('mx-auto')
                        .append($('<div/>')
                            .addClass('col col-md-5 h5 p-0')
                            .append($('<label/>')
                                .attr('for', 'discount')
                                .text('Discount: ')
                            )
                            .append($('<input/>')
                                .addClass('ml-2')
                                .attr('type', 'number')
                                .attr('size', '3px')
                                .attr('id', 'discount')
                                .attr('name', 'discount')
                                .attr('placeholder', '10%')
                                .attr('min', '1')
                                .attr('max', '90')
                                .attr('step', '1')
                                .prop('required', true)
                            )
                        )
                        .append($('<div/>')
                            .addClass('col col-md-5 h5 p-0')
                            .append($('<label/>')
                                .attr('for', 'fromDate')
                                .text('From Date: ')
                            )
                            .append($('<input/>')
                                .addClass('ml-2 font-italic')
                                .attr('type', 'datetime-local')
                                .attr('size', '15px')
                                .attr('id', 'fromDate')
                                .attr('name', 'fromDate')
                                .val(dataTimeNow)
                                .attr('pattern', '[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}')
                                .attr('title', 'yyyy-MM-dd\'T\'HH:mm')
                                .prop('readonly', true)
                            )
                        )
                        .append($('<div/>')
                            .addClass('col col-md-5 h5 p-0')
                            .append($('<label/>')
                                .attr('for', 'toDate')
                                .text('To Date: ')
                            )
                            .append($('<input/>')
                                .addClass('ml-2')
                                .attr('type', 'datetime-local')
                                .attr('size', '15px')
                                .attr('id', 'toDate')
                                .attr('name', 'toDate')
                                .attr('placeholder', '2020-05-22T08:30')
                                .attr('pattern', '[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}')
                                .attr('title', 'yyyy-MM-dd\'T\'HH:mm')
                                .prop('required', true)
                            )
                        )
                        .append($('<button/>')
                            .addClass('btn warning btn-primary low-button font-weight-bold text-white mt-2 ml-2')
                            .attr('type', 'submit')
                            .text('Promote')
                        )
                    )
                )
            )
        );
}







