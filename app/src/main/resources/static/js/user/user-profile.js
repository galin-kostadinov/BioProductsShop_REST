$(function () {
    loadUserProfile();
});

function loadUserProfile() {
    $.ajax({
        type: 'GET',
        url: '/api/user/profile',
        data: 'json',
        success: function (user) {
            addUserProfileDOM(user);
        }
    });
}

function addUserProfileDOM({username, email}) {
    $('#user-profile')
        .append($('<div>')
            .addClass('text-center pt-2')
            .append($('<h4>')
                .text('Username: ' + username)
            )
        )
        .append($('<div>')
            .addClass('text-center')
            .append($('<h4>')
                .addClass('pb-2')
                .text('Email: ' + email)
            )
        )
        .append($('<div>')
            .addClass('button-holder d-flex justify-content-center')
            .append($('<block/>')
                .addClass("pr-2")
                .append($('<a/>', {href: '/user/profile/edit'})
                    .addClass('btn btn-warning low-button font-weight-bold text-white')
                    .text('Edit Profile')
                )
            )
            .append($('<block/>')
                .append($('<a/>', {href: '/user/profile/delete'})
                    .addClass('btn btn-danger low-button font-weight-bold text-white')
                    .text('Delete Profile')
                )
            )
        )
}