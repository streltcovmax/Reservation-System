const button = document.querySelector('#submitButton');
const nameInput = document.querySelector('#name');
const passInput = document.querySelector('#password');
const messageDiv = document.querySelector('#messageDiv');


function checkAndLogin(){
    messageDiv.textContent = ""
    const inputData = {
        name: nameInput.value,
        password: passInput.value
    }
    console.log(inputData.name);

    fetch('/admin.check',
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(inputData)
        }
    ).then(
        response => response.json()
    ).then( isAdmin => {
        if(isAdmin){
            console.log('U R Admin');
            window.location.replace("../admin");

        }else {
            console.log('U R not Admin');
            messageDiv.textContent = "Неверный логин или пароль!1!"
            messageDiv.style.color = "red";
        }
    })
}