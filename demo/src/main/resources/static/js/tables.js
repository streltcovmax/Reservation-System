const dateTimeField = document.querySelector('#dateTime');
const peopleField = document.querySelector('#numberOfPeople');
const submitButton = document.querySelector("#submitForm");
const messageDiv = document.querySelector('#messageDiv');


callTableFetch();
dateTimeField.addEventListener('input', callTableFetch);
peopleField.addEventListener('input', callTableFetch);


//Изменение состояния кнопки "далее" и вывод сообщения об отсутствии столов
//Здесь же валидация
//Используется втч на адм панели

function callTableFetch(){
    fetchTableInfo(submitButton, dateTimeField, dateTimeField.value, peopleField, messageDiv);
}

//TODO можно оптимизировать