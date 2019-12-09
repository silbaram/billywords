// window.onload = function clickAnser(taget){
//     document.querySelector("." + taget).addEventListener("click", wrong_iconEvent);
// }


function anserWorngChange(clicked_class){
    var anserWorngBut = document.querySelector("." + clicked_class);
    anserWorngBut.className = "anserWorng";
}

function iconEventAgain(){
    document.querySelector(".wrong_icon").style.animationName = "none";
    document.querySelector(".wrong_icon").style.animationDuration = "0s";
}

function wrong_iconEvent(){
    document.querySelector(".wrong_icon").style.animationName = "fadeout";
    document.querySelector(".wrong_icon").style.animationDuration = "1s";


    window.setTimeout(iconEventAgain, 1000);
}

function pleaseSignupUp(){
    document.querySelector(".pleaseSignup").style.animationName = "up";
    document.querySelector(".pleaseSignup").style.animationDuration = "1s";
    document.querySelector(".pleaseSignup").style.animationFillMode = "both";
}

//pleaseSignupUp();
