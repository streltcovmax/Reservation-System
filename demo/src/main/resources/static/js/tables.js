const dateField = document.querySelector('#dateTime');
const peopleField = document.querySelector('#numberOfPeople');
const submitButton = document.querySelector("#submitForm");
const message = document.querySelector("#message");

fetchTableInfo();
dateField.addEventListener('input', fetchTableInfo);
peopleField.addEventListener('input', fetchTableInfo);


//Изменение состояния кнопки "далее" и вывод сообщения об отсутствии столов
function fetchTableInfo(){
    const dateValue = dateField.value;
    const peopleValue = peopleField.value;
    submitButton.disabled = true;
    message.classList.add('hidden');
    if(dateValue && peopleValue && peopleValue > 0){
        const orderData = {
            dateTime: dateValue,
            numberOfPeople: peopleValue
        }
        fetch(
            '/tables/find',  {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(orderData)
            }
        ).then(
            response => response.json()
        ).then(data =>
        {
            let tableId = data;
            message.classList.remove('hidden');
            if(tableId !== -1){
                submitButton.disabled = false;
                message.textContent = "Нашли для вас стол!"
            }
            else{
                message.textContent = "Подходящих столов нет...((("
            }
            console.log("ur table is probably " + tableId);
        });
    }
}

//TODO можно оптимизировать