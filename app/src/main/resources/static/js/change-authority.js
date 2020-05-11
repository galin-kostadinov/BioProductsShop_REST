$(function () {
    loadUsers();
});

function loadUsers() {
    $.ajax({
        type: 'GET',
        url: URLS.users,
        data: 'json',
        success: function (users) {
            $.each(users, function (i, user) {
                addChangeAuthorityButton(user);
            })
            loader.hide();
        }
    })
}

function addChangeAuthorityButton({id, authorities, username}) {
    const selectorIdUser = '#' + username;

    if (authorities.length === 2) {
        $(selectorIdUser)
            .append($('<block/>')
                .append($('<form/>', {action: '/user/set-user/' + id, method: 'POST'})
                    .append($('<button/>')
                        .addClass('btn btn-secondary glow-button font-weight-bold')
                        .attr('type', 'submit')
                        .text('MAKE USER')
                    )
                )
            );
    } else if (authorities.length === 1) {
        $(selectorIdUser)
            .append($('<block/>')
                .append($('<form/>', {action: '/user/set-admin/' + id, method: 'POST'})
                    .append($('<button/>')
                        .addClass('btn btn-secondary glow-button font-weight-bold')
                        .attr('type', 'submit')
                        .text('MAKE ADMIN')
                    )
                )
            );
    }
}

