const button = document.getElementById("submitForm");
const timeInput = document.getElementById("dateTime");
const numberInput = document.getElementById("numberOfPeople");

button.addEventListener("click", (e) => {
    const currentDate = new Date();

    // Сброс стилей и удаление старого сообщения об ошибке
    timeInput.style.border = "";
    const dateErrorMessage = document.querySelector(".dateError");
    if (dateErrorMessage) {
        dateErrorMessage.textContent = ""; // Очистить текст ошибки
    }

    const numberErrorMessage = document.querySelector(".numberError");
    if (numberErrorMessage) {
        numberErrorMessage.textContent = ""; // Очистить текст ошибки
    }

    if (!numberInput.value) {
        e.preventDefault();
        numberInput.style.border = "solid 2px red";
        const errorDiv = document.querySelector(".numberError");
        errorDiv.style.color = "red";
        errorDiv.style.marginTop = "5px";
        errorDiv.textContent = "Введите количество человек.";
        return;
    } else if (!/^\d+$/.test(numberInput.value)) {
        // Проверка, чтобы ввод содержал только цифры
        e.preventDefault();
        numberInput.style.border = "solid 2px red";
        const errorDiv = document.querySelector(".numberError");
        errorDiv.style.color = "red";
        errorDiv.style.marginTop = "5px";
        errorDiv.textContent = "Количество человек должно быть числом.";
        return;
    } else if (Number(numberInput.value) <= 0) {
        e.preventDefault();
        numberInput.style.border = "solid 2px red";
        const errorDiv = document.querySelector(".numberError");
        errorDiv.style.color = "red";
        errorDiv.style.marginTop = "5px";
        errorDiv.textContent = "Количество человек должно быть больше нуля.";
        return;
    } else {
        numberInput.style.border = "solid 2px green";
    }

    const inputDate = new Date(timeInput.value);
    if (!timeInput.value) {
        // Проверяем, заполнено ли поле
        e.preventDefault();
        timeInput.style.border = "solid 2px red";
        const errorDiv = document.querySelector(".dateError");
        errorDiv.style.color = "red";
        errorDiv.style.marginTop = "5px";
        errorDiv.textContent = "Пожалуйста, выберите дату и время.";
        return;
    } else if (inputDate < currentDate) {
        e.preventDefault();
        timeInput.style.border = "solid 2px red";

        const errorDiv = document.querySelector(".dateError");
        errorDiv.style.color = "red";
        errorDiv.style.marginTop = "5px";
        errorDiv.textContent = "Вы не можете выбрать дату раньше текущей!";
    } else {
        timeInput.style.border = "solid 2px green";
    }
});