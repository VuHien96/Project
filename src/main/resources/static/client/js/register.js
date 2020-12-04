$(document).ready(function() {

    $("#btn-register").on("click", function () {
        if ($('#register-name').val().trim() === '' || $('#register-email').val().trim() === '' || $('#register-password').val().trim() === '' || $('#register-password-confirmation').val().trim() === '' || $('#register-address').val().trim() === '' || $('#register-phone').val().trim() === '') {
            swal(
                'Error',
                'Bạn cần điền vào tất cả các giá trị !',
                'error'
            );
            return ;
        }

        if (!isEmail($('#register-email').val().trim())) {
            swal(
                'Error',
                'Chưa đúng định dạng mail !',
                'error'
            );
            return ;
        }

        if ($('#register-password').val().trim() !== $('#register-password-confirmation').val().trim()) {
            swal(
                'Error',
                'Password không trung nhau !',
                'error'
            );
            return ;
        }

        var data = {
            name: $('#register-name').val().trim(),
            email: $('#register-email').val().trim(),
            password: $('#register-password').val().trim(),
            address: $('#register-address').val().trim(),
            phone: $('#register-phone').val().trim()
        }

        axios.post("/api/users", data).then(function(res){
            if(res.data.success) {
                swal(
                    'Good job!',
                    res.data.message,
                    'success'
                ).then(function () {
                    var dataSigin = {
                        email: $('#register-email').val().trim(),
                        password: $('#register-password').val().trim(),
                    }
                    axios.post("/api/authenticate", dataSigin).then(function(res){
                        setTimeout(() => location.replace("/client/home"), 500);
                    });
                });
            } else  {
                swal(
                    'Error',
                    res.data.message,
                    'error'
                );
            }
        });
    });

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }
});