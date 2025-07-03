const params = new URLSearchParams(window.location.search);
const msg = params.get("msg");
const title = document.getElementById("000001");
const serverDot = document.getElementById("000002");
const sText = document.getElementById("000003")

if (msg) {
     serverDot.style.backgroundColor = "yellow";
     sText.textContent = "online"
     title.textContent = "Session : " + msg;
     document.title = "SES " + msg;
}
