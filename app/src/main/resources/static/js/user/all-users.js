$(function () {
    loadAllUsers();
});

function loadAllUsers() {
    $.ajax({
        type: 'GET',
        url: '/api/user/all-users',
        data: 'json',
        success: function (users) {
            $.each(users, function (i, user) {
                addUserDOM(user, i);
            });
        }
    })
}

function addUserDOM({username, email, authorities}, i) {
    $('#all-users-table')
        .append($('<tr/>')
            .addClass('row mx-auto col-md-12')
            .append($('<td/>')
                .addClass('col-md-1 text-center')
                .text(i + 1)
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .text(username))
            .append($('<td/>')
                .addClass('col-md-3 text-center')
                .text(email)
            )
            .append($('<td/>')
                .addClass('col-md-4 text-center')
                .append($('<block/>')
                    .attr('id', 'authorities-' + username)
                )
            )
            .append($('<td/>')
                .addClass('col-md-2 text-center')
                .attr('id', username)
            )
        );

    $.each(authorities, function (i, authority) {
        $('#authorities-' + username)
            .append($('<block/>')
                .append('<span/>')
                .addClass('pl-1 pr-1')
                .text(authority.authority)
            );
    });
}

