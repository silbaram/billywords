var solvedProblemCount = 0;
var solvedProblemChallengeCount = 4;

function isWordQuestionCorrect(obj) {

    var jsonData = {
        chooseExampleId : obj.getAttribute("value"),
        learningWordsGroupEntityId : $("#learningWordsGroupEntityId").val()
    };

    $.ajax({
        type: "PATCH",
        url: "/words-test/exam-question",
        contentType: "application/json",
        data: JSON.stringify(jsonData),
        success: function(data) {
            console.log(data);
            successFunction(JSON.parse(data));
        },
        error: function(xhr, status, error) {
            failFunction(error);
        }
    });
}

function successFunction(data){

    // 한문제에서 풀수 있는 횟수보다 작고 정답이 아니면 문제 풀기 시도를 +1 하고 한번더 문제를 풀 기회룰 준다.
    if(data.status !== "200" && solvedProblemCount < solvedProblemChallengeCount) {
        solvedProblemCount++;
    // 문제풀 기회를 넘었거나 정답이면
    } else {
        // 다음 문제 요청
        nextExample();
        // //정답이면
        // if(data.status === "200") {
        //     if(data.nextExample === "true") {
        //         // 새로운 학습문제 요청
        //         nextExamQuestion();
        //     } else {
        //         // 다음 문제 요청
        //         nextExample();
        //     }
        // } else {
        //     // 다음 문제 요청
        //     nextExample();
        // }
    }
}

function failFunction(data){
    console.log("failFunction", data);
}

function nextExample() {
    console.log("nextExample");
    var frm = document.nextExample;
alert("nextExample");
    frm.action = "/words-test/next/guest-example";
    frm.method = "post";
    frm.submit();
}

function nextExamQuestion() {
    console.log("nextExamQuestion");
    var frm = document.nextExample;

    frm.action = "/words-test/next/exam-question";
    frm.method = "post";
    frm.submit();
}