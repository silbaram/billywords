function isWordQuestionCorrect(obj) {

    console.log(obj.getAttribute("value"));
    var jsonData = {
        chooseExampleId : obj.getAttribute("value")
    };

    $.ajax({
        type: "PATCH",
        url: "/words-test/exam-question",
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
    console.log("successFunction", data);
    nextExample();
}

function failFunction(data){
    console.log("failFunction", data);
}

function nextExample() {
    console.log("nextExample");
    var frm = document.nextExample;

    frm.action = "/words-test/next/example";
    frm.method = "get";
    frm.submit();
}
