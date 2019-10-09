function isWordQuestionCorrect(obj) {

    console.log(obj.getAttribute("value"));
    var jsonData = {
        objId : obj.getAttribute("value")
    };

    $.ajax({
        type: "PATCH",
        url: "/words-test/problem",
        dataType: "application/json",
        traditional: false, //배열 객체 보낼때
        data: jsonData,
        success: function(data){
            successFunction(data);
        },
        error: function(xhr, status, error) {
            failFunction(error);
        }
    });
}

function successFunction(data){
    if(data.result ==='success') {
        var a = "함수호출 success";
    }

    alert(a);
}

function failFunction(data){
    if(data.result !='success') {
        var a = "함수호출 fail";
    }

    alert(a);
}
