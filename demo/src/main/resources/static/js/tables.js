const dateField = document.querySelector('#dateTime');
const peopleField = document.querySelector('#numberOfPeople');
const submitButton = document.querySelector("#submitForm");
const messageDiv = document.querySelector('#messageDiv');

fetchTableInfo();
dateField.addEventListener('input', fetchTableInfo);
peopleField.addEventListener('input', fetchTableInfo);


//Изменение состояния кнопки "далее" и вывод сообщения об отсутствии столов
//Здесь же валидация
//То что закоментил как будто не нужно
function fetchTableInfo(){
    const dateValue = dateField.value;
    const peopleValue = peopleField.value;
    const currentDate = new Date();
    submitButton.disabled = true;
    messageDiv.textContent = ""
    messageDiv.style.marginTop = "5px";
    peopleField.classList.remove("incorrect");
    dateField.classList.remove("incorrect");
    let incorrect = false;

    if(dateValue && peopleValue){
        if(!/^\d+$/.test(peopleValue) || peopleValue <= 0){
            // messageDiv.textContent = "Количество человек должно быть больше нуля.";
            // messageDiv.style.color = "red";
            peopleField.classList.add("incorrect");
            incorrect = true;
        }
        if(new Date(dateValue) < currentDate){
            // messageDiv.textContent = "Вы не можете выбрать дату раньше текущей!";
            // messageDiv.style.color = "red";
            dateField.classList.add("incorrect");
            incorrect = true;
        }

        if(!incorrect){
            peopleField.classList.remove("incorrect");
            dateField.classList.remove("incorrect");
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

//TODO можно оптимизировать