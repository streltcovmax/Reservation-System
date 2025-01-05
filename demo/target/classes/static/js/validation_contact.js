//Исправлено, улучшено, ЛУЧШЕ НЕ ТРОГАТЬ, РАБОТАЕТ

const form = document.forms["reservationForm"];
const button = form.elements["submitForm"];

const inputs = Array.from(form.querySelectorAll("[data-reg]"));
inputs.forEach(inputCheck);

buttonHandler();

form.addEventListener("input", inputHandler);
form.addEventListener("input", buttonHandler);

function inputHandler({ target }) {
  if (target.hasAttribute("data-reg")) {
    inputCheck(target);
  }
}

function inputCheck(el) {
  const inputValue = el.value;
  const inputReg = el.getAttribute("data-reg");
  const reg = new RegExp(inputReg);
  if (reg.test(inputValue)) {
    el.style.border = "2px solid rgb(0,196,0)";
    el.setAttribute("is-valid", "1");
  } else {
    el.style.border = "2px solid rgb(255,0,0)";
    el.setAttribute("is-valid", "0");
  }
}

function buttonHandler() {
  const allValid = inputs.every((input) => input.getAttribute("is-valid") === "1");
  button.disabled = !allValid;
}
