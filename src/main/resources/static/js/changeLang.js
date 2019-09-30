function langExchange(){
    var langExchangeLeft = document.querySelector(".box").querySelector(".lang_left");
    var langExchangeRight = document.querySelector(".box").querySelector(".lang_right");
    
    langExchangeLeft.className = "lang_right";
    langExchangeRight.className = "lang_left";
}

