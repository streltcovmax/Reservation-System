const searchName = document.querySelector('#searchName');
const searchAmount = document.querySelector('#searchAmount');
const addButton = document.querySelector('#addButton');
const dataDiv = document.querySelector('.data-table');
searchName.addEventListener('input', validate);
searchAmount.addEventListener('input', validate);

validate();

function validate(){
    addButton.disabled = !searchName.value || !searchAmount.value || searchAmount.value < 0;
}

function add() {
    const inputData = {
        name: searchName.value.trim(),
        amount: parseInt(searchAmount.value, 10)
    };

    console.log(inputData.name, inputData.amount);

    fetch('/admin/storage.add', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(inputData)
    }).then(r => {
        searchAmount.value = '';
        searchName.value = '';
        validate();
        let newElement = document.createElement('tr');
        newElement.innerHTML =
            `
                <tr>
                    <td class="order-id">${inputData.name}</td>
                    <td class="order-name"">${inputData.amount}</td>
                    <td class="text-view" ></td>
                </tr>
            `
        dataDiv.appendChild(newElement)
    });
}


//????
// document.addEventListener('DOMContentLoaded', () => {
//     document.querySelectorAll('.delete-btn')
//         .forEach(button => {
//             button.addEventListener('click', () => deleteOrder(button, false));
//         });
//     document.querySelectorAll('.edit-btn')
//         .forEach(button => {
//             button.addEventListener("click", () => editOrder(button));
//         });
// });
// let deleted = false;
// function deleteOrder(button, quiet){
//     const row = button.closest('tr');
//     const orderId = row.querySelector('td:first-child').textContent.trim(); // Предполагается, что ID заказа — это первый <td>
//     let isConfirmed = true;
//     if(!quiet) isConfirmed = confirm(`Вы уверены, что хотите удалить заказ №${orderId}?`);
//     if (isConfirmed) {
//         try{
//             fetch('/admin/ingredients/delete/' + orderId, {
//                     method: 'DELETE',
//                 }
//             ).then(response => {
//                 if(!quiet){
//                     if(response.ok){
//                         row.classList.add('strikethrough');
//                         row.querySelector('.edit-btn').classList.add('hidden');
//                         row.querySelector('.delete-btn').classList.add('hidden');
//                         console.log('Заказ ' + orderId + ' удален');
//                     }
//                     else{
//                         alert('Не удалось удалить заказ');
//                     }
//                 }
//             })
//         }
//         catch (error){
//             alert('Ошибка при удалении/редактировании: ' + error.message());
//         }
//
//     }
// }
