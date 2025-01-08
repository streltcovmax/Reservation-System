let tableId = null;

//Изменение состояния кнопки "далее" и вывод сообщения об отсутствии столов
//Здесь же валидация
//Используется втч на адм панели
function fetchTableInfo(submitButton, dateTimeField, dateTimeValue, peopleField, messageDiv){
    const peopleValue = peopleField.value;
    const currentDate = new Date();
    submitButton.disabled = true;
    messageDiv.textContent = ""
    messageDiv.style.marginTop = "5px";
    peopleField.classList.remove("incorrect");
    dateTimeField.classList.remove("incorrect");
    let incorrect = false;

    if(dateTimeValue && peopleValue){
        if(!/^\d+$/.test(peopleValue) || peopleValue <= 0){
            peopleField.classList.add("incorrect");
            incorrect = true;
        }
        if(new Date(dateTimeValue) < currentDate){
            dateTimeField.classList.add("incorrect");
            incorrect = true;
        }

        if(!incorrect){
            peopleField.classList.remove("incorrect");
            dateTimeField.classList.remove("incorrect");
            const orderData = {
                dateTime: dateTimeValue,
                numberOfPeople: peopleValue
            }
            fetch(
                '/tables/find',
                {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(orderData)
                }
            ).then(
                response => response.json()
            ).then(data =>
            {
                tableId = data;
                if(tableId !== -1){
                    submitButton.disabled = false;
                    messageDiv.textContent = "Нашли для вас стол!"
                    messageDiv.style.color = "green";
                }
                else{
                    messageDiv.textContent = "Подходящих столов нет...((("
                    messageDiv.style.color = "red";
                }
                console.log("ur table is probably " + tableId);
            });
        }
    }
}
