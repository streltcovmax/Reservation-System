const searchName = document.querySelector('#searchName');
const searchRole = document.querySelector('#searchRole');
const searchAmount = document.querySelector('#searchAmount');
const addButton = document.querySelector('#addButton');
const delButton = document.querySelector('#delButton');
const dataDiv = document.querySelector('.data-table');
searchName.addEventListener('input', validate);
searchRole.addEventListener('input', validate);
searchAmount.addEventListener('input', validate);

validate();

function validate(){
    addButton.disabled = !searchName.value || !searchAmount.value || searchAmount.value < 0 || !searchRole.value;
    delButton.disabled = addButton.disabled;
}

function add() {
    const inputData = {
        name: searchName.value.trim(),
        password: parseInt(searchAmount.value, 10),
        role: searchRole.value.trim()
    };
    console.log(inputData.name, inputData.password);
    fetch('/admin/stuff.add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(inputData)
    }).then(r => {
        updateWindow();
    });
}

function del() {
    const inputData = {
        name: searchName.value.trim(),
        password: parseInt(searchAmount.value, 10),
        role: searchRole.value.trim()
    };
    console.log(inputData.name, inputData.password);
    fetch('/admin/stuff.delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(inputData)
    }).then(r => {
        updateWindow();
    });
}

function updateWindow(){
    searchAmount.value = '';
    searchName.value = '';
    searchRole.value = '';
    validate();
    window.location.replace("/admin/stuff.search");
}