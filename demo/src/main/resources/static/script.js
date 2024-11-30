let i = 0;
const tablesInfo = document.querySelector('#tablesInfo')
const submitFormButton = document.querySelector('#submitForm')
const dateTime = document.querySelector("#dateTime");
console.log(dateTime.value);


function updateTablesInfo(){
    i++;
    tablesInfo.textContent = `//Если бы столов для таких данных не было, то здесь вывелось бы сообщение и 
    кнопка "отправить" была бы не активна. Запрос к бд происходил бы при каждом 
    изменении данных и произошел бы уже ` + i + "-й раз";
    submitFormButton.removeAttribute("disabled");
    console.log(dateTime.value);
}

function createCookies(){
    console.log('create cookis');
}

document.querySelector("#dateTime")
    .addEventListener("change", updateTablesInfo)
document.querySelector("#numberOfPeople")
    .addEventListener("change", updateTablesInfo)
submitFormButton.addEventListener('onclick', createCookies)