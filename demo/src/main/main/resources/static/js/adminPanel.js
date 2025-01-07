


document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.delete-btn')
        .forEach(button => {
            button.addEventListener('click', () => deleteOrder(button));
        });
    document.querySelectorAll('.edit-btn')
        .forEach(button => {
            button.addEventListener("click", () => editOrder(button));
        });
});

function deleteOrder(button){
    const row = button.closest('tr');
    const orderId = row.querySelector('td:first-child').textContent.trim(); // Предполагается, что ID заказа — это первый <td>
    const isConfirmed = confirm(`Вы уверены, что хотите удалить заказ №${orderId}?`);
    if (isConfirmed) {
        try{
            fetch('/admin/orders/delete/' + orderId, {
                    method: 'DELETE',
                }
            ).then(response => {
                if(response.ok){
                    row.classList.add('strikethrough');
                    //Здесь убирать кнопки удал и ред, добавить кнопку "скрыть"
                    row.querySelector('.edit-btn').classList.add('hidden');
                    row.querySelector('.delete-btn').classList.add('hidden');
                    console.log('Заказ ' + orderId + ' удален');
                }
                else{
                    alert('Не удалось удалить заказ');
                }
            })
        }
        catch (error){
            alert('Ошибка при удалении: ' + error.message());
        }

    }
}

function editOrder(button){
    const modal = document.getElementById("editModal");
    const closeBtn = document.querySelector(".close-btn");
    const saveBtn = document.getElementById("saveBtn");
    const form = document.getElementById("editForm");
    let currentRow = null;

    closeBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });
    window.addEventListener("click", event => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    currentRow = button.closest("tr"); // Получаем текущую строку
    const fullName = currentRow.querySelector(".order-name").textContent;
    const people = currentRow.querySelector(".order-people").textContent;
    const date = currentRow.querySelector(".order-date").textContent;
    const time = currentRow.querySelector(".order-time").textContent;
    const phoneNumber = currentRow.querySelector(".order-phone").textContent;
    const comment = currentRow.querySelector(".order-comment").textContent;

    // Преобразуем дату в формат yyyy-MM-dd
    const dateParts = date.split('.');
    const formattedDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`; // yyyy-MM-dd


    // Заполняем поля модального окна
    form.fullName.value = fullName;
    form.numberOfPeople.value = people;
    form.date.value = formattedDate;
    form.time.value = time;
    form.phoneNumber.value = phoneNumber;
    form.comment.value = comment;

    const oldData = {
        fullName: fullName,
        numberOfPeople: people,
        date: formattedDate,
        time: time,
        phoneNumber: phoneNumber,
        comment: comment
    }

    modal.style.display = "block"; // Показываем модальное окно

    saveBtn.addEventListener("click",  () => {
        const updatedData = {
            fullName: form.fullName.value,
            numberOfPeople: form.numberOfPeople.value,
            date: form.date.value,
            time: form.time.value,
            phoneNumber: form.phoneNumber.value,
            comment: form.comment.value
        };
        console.log(oldData)
        console.log(updatedData)

        if(oldData === updatedData){
            modal.style.display = "none";
        }
        else{
            const orderId = currentRow.querySelector(".order-id").textContent;
            // Отправка данных на сервер
            fetch('/admin/orders/update/'+ orderId, {
                method: "POST", // Или PUT
                headers: {"Content-Type": "application/json",},
                body: JSON.stringify(updatedData),
            }) .then(response => {
                if (response.ok) {
                    // Обновляем данные в таблице

                    // Закрываем модальное окно
                    modal.style.display = "none";
                    alert("Данные успешно обновлены!");
                }
                else {
                    alert("Ошибка при обновлении данных.");
                }
            })
                .catch(error => {
                    console.error("Ошибка:", error);
                    alert("Произошла ошибка. Попробуйте позже.");
                });
        }
    });
}

/*TODO неправильно работает сравнение, нужно поэлементное из гпт, потом доделать пост маппинг и обновление данных в бд,
   еще "валидация" поля даты, которую по идее надо просто приделать
*/