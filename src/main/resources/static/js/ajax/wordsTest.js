function isWordQuestionCorrect(obj) {

    console.log(obj.getAttribute("value"));
    var jsonData = {
        chooseExampleId : obj.getAttribute("value")
    };

    $.ajax({
        type: "PATCH",
        url: "/words-test/problem",
        // dataType: "application/json",
        contentType: "application/json",
        data: JSON.stringify(jsonData),
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

    alert(data);
}

function failFunction(data){
    if(data.result !== 'success') {
        var a = "함수호출 fail";
    }

    alert(a);
}
