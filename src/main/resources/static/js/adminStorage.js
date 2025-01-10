const searchName = document.querySelector('#searchName');
const searchAmount = document.querySelector('#searchAmount');
const editButton = document.querySelector('#editButton');
const dataDiv = document.querySelector('.data-table');
searchName.addEventListener('input', validate);
searchAmount.addEventListener('input', validate);

validate();

function validate(){
    const presentCheck = document.getElementById(searchName.value.trim());
    editButton.disabled = !searchName.value || !searchAmount.value || searchAmount.value < 0 || presentCheck==null;
}

function edit() {
    const inputData = {
        name: searchName.value.trim(),
        amount: parseInt(searchAmount.value, 10)
    };
    const el = document.querySelector('#' + searchName.value.trim());
    const nameElement = el.querySelector('.ingr-name');
    const amountElement = el.querySelector('.ingr-amount');

    const oldData = {
        name: nameElement.textContent.trim(),
        amount: parseInt(amountElement.textContent, 10)
    };

    if(inputData.name == oldData.name && inputData.amount==oldData.amount){
        searchAmount.value = '';
        searchName.value = '';
        validate();
    }
    else{
        fetch('/admin/storage.edit', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(inputData)
        }).then(r => {
            nameElement.textContent = inputData.name;
            amountElement.textContent = inputData.amount.toString();
            searchAmount.value = '';
            searchName.value = '';
            validate();

        });
    }
}