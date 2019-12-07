var solvedProblemCount = 0;
var solvedProblemChallengeCount = 4;

function isWordQuestionCorrect(obj) {

    var jsonData = {
        chooseExampleId : obj.getAttribute("value"),
        learningWordsGroupEntityId : $("#learningWordsGroupEntityId").val()
    };
    console.log("Number($(\"#learningWordsPosition\").val()) === 1", Number($("#learningWordsPosition").val()) === 1);
    console.log("umber($(\"#learningWordsPosition\").val())", Number($("#learningWordsPosition").val()));
    if(Number($("#learningWordsPosition").val()) === 1) {
        pleaseSignupUp();
        return;
    }
    return;
    $.ajax({
        type: "PATCH",
        url: "/words-test/exam-question",
        contentType: "application/json",
        data: JSON.stringify(jsonData),
        success: function(data) {
            successFunction(JSON.parse(data), obj);
        },
        error: function(xhr, status, error) {
            failFunction(error);
        }
    });
}

function successFunction(data, obj){

    // 한문제에서 풀수 있는 횟수보다 작고 정답이 아니면 문제 풀기 시도를 +1 하고 한번더 문제를 풀 기회룰 준다.
    if(data.status !== "200" && solvedProblemCount < solvedProblemChallengeCount) {
        solvedProblemCount++;
        anserWorngChange(obj.className);
    // 문제풀 기회를 넘었거나 정답이면
    } else {
        // 다음 문제 요청
        nextExample();
    }
}

function failFunction(data){
    console.log("failFunction", data);
}

function nextExample() {
    var frm = document.nextExample;
    frm.action = "/words-test/next/guest-example";
    frm.method = "post";
    frm.submit();
}

