const dateTimeField = document.querySelector('#dateTime');
const peopleField = document.querySelector('#numberOfPeople');
const messageDiv = document.querySelector('#messageDiv');


callTableFetch();
dateTimeField.addEventListener('input', callTableFetch);
peopleField.addEventListener('input', callTableFetch);

function callTableFetch(){
    fetchTableInfo(dateTimeField, dateTimeField.value, peopleField, messageDiv);
}
