$(function () {
    loadUserProfile();
});

function loadUserProfile() {
    $.ajax({
        type: 'GET',
        url: URLS.userProfile,
        data: 'json',
        success: function (user) {
            addUserProfileDOM(user);
        }
    });
}

function addUserProfileDOM({username, email}) {
    $('#user-profile')
        .append($('<div>')
            .addClass('text-left pt-2')
            .append($('<h4>')
                .text('Username: ' + username)
            )
        )
        .append($('<div>')
            .addClass('text-left')
            .append($('<h4>')
                .addClass('pb-2')
                .text('Email: ' + email)
            )
        )
        .append($('<div>')
            .addClass('button-holder d-flex justify-content-center')
            .append($('<block/>')
                .append($('<a/>', {href: '/user/profile/edit'})
                    .addClass('btn warning btn-warning low-button font-weight-bold text-white')
                    .text('Edit Profile')
                )
            )
        )
}