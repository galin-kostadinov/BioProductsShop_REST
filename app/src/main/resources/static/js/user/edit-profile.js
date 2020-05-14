$(function () {
    loadUserProfile();
});

function loadUserProfile() {
    $.ajax({
        type: 'GET',
        url: '/api/user/profile/edit',
        data: 'json',
        success: function (user) {
            addUserProfileDOM(user);
        }
    });
}

function addUserProfileDOM({username, email}) {
    $('#edit-user-profile')
        .append($('<div>')
            .addClass('form-group')
            .append($('<label>')
                .addClass('mb-1')
                .attr('for', 'username')
                .text('Username: ')
            )
            .append($('<input>')
                .addClass('form-control font-italic')
                .attr('type', 'text')
                .attr('name', 'username')
                .attr('id', 'username')
                .prop('readonly', true)
                .val(username)
            )
        )
        .append($('<div>')
            .addClass('form-group')
            .append($('<label>')
                .addClass('mb-1')
                .attr('for', 'email')
                .text('Email: ')
            )
            .append($('<input>')
                .addClass('form-control font-italic')
                .attr('type', 'email')
                .attr('name', 'email')
                .attr('id', 'email')
                .prop('readonly', true)
                .val(email)
            )
        )
}