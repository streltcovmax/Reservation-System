document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.delete-btn')
        .forEach(button => {
            button.addEventListener('click', () => deleteOrder(button, false));
        });
    document.querySelectorAll('.edit-btn')
        .forEach(button => {
            button.addEventListener("click", () => editOrder(button));
        });
});
let tableId = null;
let deleted = false;
function deleteOrder(button, quiet){
    const row = button.closest('tr');
    const orderId = row.querySelector('td:first-child').textContent.trim(); // Предполагается, что ID заказа — это первый <td>
    let isConfirmed = true;
    if(!quiet) isConfirmed = confirm(`Вы уверены, что хотите удалить заказ №${orderId}?`);
    if (isConfirmed) {
        try{
            fetch('/admin/orders/delete/' + orderId, {
                    method: 'DELETE',
                }
            ).then(response => {
                if(!quiet){
                    if(response.ok){
                        row.classList.add('strikethrough');
                        row.querySelector('.edit-btn').classList.add('hidden');
                        row.querySelector('.delete-btn').classList.add('hidden');
                        console.log('Заказ ' + orderId + ' удален');
                    }
                    else{
                        alert('Не удалось удалить заказ');
                    }
                }
            })
        }
        catch (error){
            alert('Ошибка при удалении/редактировании: ' + error.message());
        }

    }
}

function editOrder(button) {
    deleted = false;
    const modal = document.getElementById("editModal");
    const closeBtn = document.querySelector(".close-btn");
    const saveBtn = document.getElementById("saveBtn");
    const form = document.getElementById("editForm");
    let currentRow = button.closest("tr"); // Получаем текущую строку


    const fullName = currentRow.querySelector(".order-name").textContent;
    const people = currentRow.querySelector(".order-people").textContent;
    const date = currentRow.querySelector(".order-date").textContent;
    const time = currentRow.querySelector(".order-time").textContent;
    const phoneNumber = currentRow.querySelector(".order-phone").textContent;
    const comment = currentRow.querySelector(".order-comment").textContent;

    // Преобразуем дату в формат yyyy-MM-dd
    const dateParts = date.split('.');
    const formattedDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`; // yyyy-MM-dd
    const orderId = currentRow.querySelector(".order-id").textContent;

    // Заполняем поля модального окна
    form.fullName.value = fullName;
    form.numberOfPeople.value = people;
    form.date.value = formattedDate;
    form.time.value = time;
    form.phoneNumber.value = phoneNumber;
    form.comment.value = comment;

    form.date.addEventListener("input", () => deleteAndFetchTableInfo(orderId, form, saveBtn));
    form.numberOfPeople.addEventListener("input", () => deleteAndFetchTableInfo(orderId, form, saveBtn));

    let oldData = {
        fullName: fullName,
        numberOfPeople: people,
        date: formattedDate,
        time: time,
        phoneNumber: phoneNumber,
        comment: comment,
    };

    // Закрытие модального окна
    closeBtn.addEventListener("click", () => {
        if(deleted) resetChanges(orderId, oldData);
        modal.style.display = "none";
    });

    window.addEventListener("click", event => {
        if (event.target === modal) {
            if(deleted) resetChanges(orderId, oldData);
            modal.style.display = "none";
        }
    });

    console.log("OLD DATA:", oldData);

    modal.style.display = "block"; // Показываем модальное окно

    // Удаляем ранее установленный обработчик, чтобы избежать повторного вызова
    const newSaveHandler = () => {
        let updatedData = {
            fullName: form.fullName.value,
            numberOfPeople: form.numberOfPeople.value,
            date: form.date.value,
            time: form.time.value,
            phoneNumber: form.phoneNumber.value,
            comment: form.comment.value,
            tableNumber: tableId
        };

        form.fullName.classList.remove("incorrect");
        form.phoneNumber.classList.remove("incorrect");

        let valid = true;

        if(updatedData.fullName.trim().length === 0){
            form.fullName.classList.add("incorrect");
            valid = false;
        }
        if(updatedData.phoneNumber.trim().length === 0){
            form.phoneNumber.classList.add("incorrect");
            valid = false;
        }
        if(updatedData.time.trim().length === 0){
            form.time.classList.add("incorrect");
            valid = false;
        }

        console.log("UPDATED DATA:", updatedData);

        if (objectsDiffer(oldData, updatedData) && valid) {
            console.log("Найдены различия.");

            // Отправка данных на сервер
            fetch('/admin/orders/update/' + orderId, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(updatedData),
            })
                .then(response => {
                    if (response.ok) {
                        // Обновляем данные в таблице
                        currentRow.querySelector(".order-name").textContent = updatedData.fullName;
                        currentRow.querySelector(".order-people").textContent = updatedData.numberOfPeople;
                        currentRow.querySelector(".order-date").textContent = updatedData.date.split('-').reverse().join('.');
                        currentRow.querySelector(".order-time").textContent = updatedData.time;
                        currentRow.querySelector(".order-phone").textContent = updatedData.phoneNumber;
                        currentRow.querySelector(".order-comment").textContent = updatedData.comment;
                        currentRow.querySelector(".order-table").textContent = updatedData.tableNumber;

                        modal.style.display = "none"; // Закрываем окно
                        alert("Данные успешно обновлены!");
                        oldData = updatedData;
                    } else {
                        alert("Ошибка при обновлении данных.");
                    }
                })
                .catch(error => {
                    console.error("Ошибка:", error);
                    alert("Произошла ошибка. Попробуйте позже.");
                });
        } else {
            console.log("Объекты идентичны.");
            modal.style.display = "none"; // Закрываем окно
        }

    };

    // Удаляем старый обработчик, если он был установлен
    saveBtn.replaceWith(saveBtn.cloneNode(true));
    document.getElementById("saveBtn").addEventListener("click", newSaveHandler);
}

function objectsDiffer(obj1, obj2) {
    // Обходим ключи первого объекта
    for (const key in obj1) {
        if (obj1.hasOwnProperty(key)) {
            // Сравниваем значения (с учётом возможных различий типов)
            if (obj1[key] != obj2[key]) {
                return true;
            }
        }
    }
    return false;
}

function fetchTableInfo(form, saveButton) {
    //тихонечко удаляем заказ из бд

    console.log("FETCHIN CHEKCNI");
    const dateValue = form.date.value.trim();
    const peopleValue = form.numberOfPeople.value.trim();
    const messageDiv = document.querySelector('#messageDiv');
    messageDiv.textContent = ""
    saveButton.disabled = true;

    let valid = true;
    form.date.classList.remove("incorrect");
    form.numberOfPeople.classList.remove("incorrect");

    if (dateValue.length === 0) {
        valid = false;
        form.date.classList.add("incorrect");
    }
    if (peopleValue.length === 0 || peopleValue <= 0) {
        valid = false;
        form.numberOfPeople.classList.add("incorrect");
    }

    const orderData = {
        dateTime: convertToDateTimeLocal(dateValue, form.time.value),
        numberOfPeople: peopleValue
    }
    if (valid) {
        fetch(
            '/tables/find',
            {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(orderData)
            }
        ).then(
            response => response.json()
        ).then(data => {
            tableId = data;
            if (tableId !== -1) {
                saveButton.disabled = false;
                messageDiv.textContent = "Есть свободный стол"
                messageDiv.style.color = "green";
            } else {
                messageDiv.textContent = "Подходящих столов нет"
                messageDiv.style.color = "red";
            }
            console.log("ur table is probably " + tableId);
        });
    }

}
//НОМЕР СТОЛИКА ЫВБЫФВБФЫЖВ
function deleteAndFetchTableInfo(orderId, form, saveButton){
    if(!deleted) deleteOrderFromTable(orderId);
    deleted = true;
    fetchTableInfo(form, saveButton);
}

function resetChanges(id, oldData){
    fetch('/admin/orders/update/' + id, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(oldData),
    }).then(r => {
        if(r.ok) console.log("data restored")
        else console.error("error restoring data");
    } )
}

function deleteOrderFromTable(orderId){
    fetch('/admin/orders/tableDelete/' + orderId, {
            method: 'DELETE',
        }
    ).then(r => {
        if(r.ok) console.log("deleted order " + orderId + " from table");
        else console.error("error deleting order" + orderId + " from table");
    })
}


function convertToDateTimeLocal(dateValue, timeValue) {
    const date = new Date(dateValue);
    const [hours, minutes] = timeValue.split(":").map(Number);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Месяцы с 0, поэтому +1
    const day = String(date.getDate()).padStart(2, '0');
    const time = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}`;

    return `${year}-${month}-${day}T${time}`;
}