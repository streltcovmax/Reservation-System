const dateTimeField = document.querySelector('#dateTime');
const peopleField = document.querySelector('#numberOfPeople');
const submitButton = document.querySelector("#submitForm");
const messageDiv = document.querySelector('#messageDiv');


callTableFetch();
dateTimeField.addEventListener('input', callTableFetch);
peopleField.addEventListener('input', callTableFetch);

function callTableFetch(){
    fetchTableInfo(submitButton, dateTimeField, dateTimeField.value, peopleField, messageDiv);
}
