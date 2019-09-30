// Create a "close" button and append it to each list item
var myNodelist = document.querySelector("#myUL").getElementsByTagName("LI");
var i;
for (i = 0; i < myNodelist.length; i++) {
  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
}

// Click on a close button to hide the current list item
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
}

// Create a new list item when clicking on the "Add" button
function newTagElement() {
    var li = document.createElement("li");
    var inputValue = document.getElementById("myInput").value;
    var t = document.createTextNode(inputValue);



    li.appendChild(t);
    if (inputValue === '') {
        alert("You must write something!");
    } else {
        document.getElementById("myUL").appendChild(li);
    }
    document.getElementById("myInput").value = "";

    var span = document.createElement("SPAN");
    var txt = document.createTextNode("\u00D7");
    span.className = "close";
    span.appendChild(txt);
    li.appendChild(span);

    for (i = 0; i < close.length; i++) {
        close[i].onclick = function() {
        var div = this.parentElement;
        div.style.display = "none";
        }
    }

    li.addEventListener("click", function(event) {
        var value = this.innerText;
        var valueArr = value.substr(-0,value.length-1);
        document.querySelector("#graph_tag").innerHTML = valueArr+ " 정규분포";
   });

}

// window.onload = function(){
        // var myUlA = document.querySelector("#myUL").querySelector("li");
// console.log(myUlA);
        // myUlA.addEventListener("click", {
        //     ocument.querySelector("#graph_tag").innerHTML = "aaa";
        // });
// };

// function changGraphTag(){
//     document.querySelector("#graph_tag").innerHTML = "aaa";
// }