const params = new URLSearchParams(window.location.search);

const msg     = params.get("msg");
const devType = params.get("type");
const serNum  = params.get("serNum");
const prcName = params.get("prcName");
const ramAmo  = params.get("ram");
const manf    = params.get("manf");

// Load stored computers from cookie (JSON string)
let computers = JSON.parse(getCookie("computers") || "[]");

// Add the current one only if unique
if (msg && devType && serNum && prcName && ramAmo && manf) {
  const newDevice = { msg, devType, serNum, prcName, ramAmo, manf };

  // Add only if serial number isn't already in list
  if (!computers.some(c => c.serNum === serNum)) {
    computers.push(newDevice);
    setCookie("computers", JSON.stringify(computers));
  }
}

// Update topbar session and online status
if (msg) {
  document.getElementById("000001").textContent = "Session : " + msg;
  document.title = "SES " + msg;
  document.getElementById("000002").style.backgroundColor = "yellow";
  document.getElementById("000003").textContent = "online";
}
document.getElementById("000004").textContent = devType ? " | " + devType : " | DevNotFound";
document.getElementById("000005").textContent = serNum ? " | SN: " + serNum + " | " : " | SerNumNotFound |";

// Reference to container where loaders will be shown
const container = document.getElementById("loader-container");

// Loop through each saved computer and render loader block
computers.forEach((data) => {
  const loader = document.createElement("div");
  loader.className = "loader";

  // Format manufacturer name
  const manfFormatted = data.manf.charAt(0).toUpperCase() + data.manf.slice(1).toLowerCase();

  // Determine correct logos
  let logoPath = "./IMG/desktop-icon.png"; // Default icon

const manufacturer = data.manf.toUpperCase();

switch (manufacturer) {
  case "HP":
  case "HEWLETT-PACKARD":
    logoPath = "./IMG/hp-logo.png";
    break;
  case "DELL":
    logoPath = "./IMG/dell-logo.png";
    break;
  // You can add more like this:
  case "LENOVO":
    logoPath = "./IMG/lenovo-logo.png";
    break;
  case "ASUS":
    logoPath = "./IMG/asus-logo.png";
    break;
  case "ACER":
     logoPath = "./IMG/acer-logo.png";
     break;
  case "APPLE":
     logoPath = "./IMG/apple-logo.png";
     break;
}

const logo = logoPath;



  const icon = data.devType === "LAPTOP" ? "./IMG/laptop-icon.png" : "./IMG/desktop-icon.png";
  const label = data.devType === "LAPTOP" ? "Laptop" : "Desktop";

  // Final loader HTML content
  loader.innerHTML = `
    <img src="${icon}" style="width: 80px; height: 80px;">
    <div style="flex: 1; margin: 0 10px;">
      <h1>${label}</h1>
      <p>${data.serNum}</p>
      <p>${data.prcName}</p>
      <p>${data.ramAmo}GB Ram</p>
      <p>Manufacturer: ${manfFormatted}</p>
    </div>
    <img src="${logo}" style="width: 64px; height: 64px;">
  `;

  container.appendChild(loader);
});

// ========== Cookie Helpers ==========
function setCookie(name, value, days = 1) {
  const expires = new Date(Date.now() + days * 864e5).toUTCString();
  document.cookie = `${name}=${encodeURIComponent(value)}; expires=${expires}; path=/`;
}

function getCookie(name) {
  return document.cookie.split("; ").reduce((r, v) => {
    const parts = v.split("=");
    return parts[0] === name ? decodeURIComponent(parts[1]) : r;
  }, null);
}
