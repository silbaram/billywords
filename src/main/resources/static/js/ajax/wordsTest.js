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

    //TODO 현재 풀고 있는 문제의 보기를 몇개 틀렸을때 다음으로 넘어갈지 경정해야됨
    // nextExample();

    nextExamQuestion();
}

function failFunction(data){
    console.log("failFunction", data);
}

function nextExample() {
    console.log("nextExample");
    var frm = document.nextExample;

    frm.action = "/words-test/next/example";
    frm.method = "post";
    frm.submit();
}

function nextExamQuestion() {
    console.log("nextExample");
    var frm = document.nextExample;

    frm.action = "/words-test/next/exam-question";
    frm.method = "post";
    frm.submit();
}
