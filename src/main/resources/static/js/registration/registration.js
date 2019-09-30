function emailUniqueCheck() {
    $.ajax({
        url:"/email-unique-check?email=" + $("#username").val(),
        success:function(data){
            console.log(data);
        }
    })
}
