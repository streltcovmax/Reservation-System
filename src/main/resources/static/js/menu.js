document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.add-to-cart')
        .forEach(button => {
            button.addEventListener('click', () => addToCart(button));
        });
    document.querySelectorAll('.remove-from-cart')
        .forEach(button => {
            button.disabled = button.closest('.dish-footer').querySelector('.dish-quantity').textContent <= 0
            button.addEventListener('click', () => {
                removeFromCart(button);
            });
        });
});

function addToCart(button){
    const name = button.closest('.dish-panel').querySelector('.dish-name').textContent;
    const quantityElement = button.closest('.dish-footer').querySelector('.dish-quantity');
    const minusButton = button.closest('.dish-footer').querySelector('.remove-from-cart');
    const quantity = quantityElement.textContent;
    console.log(name);

    fetch('/menu.addDish', {
        method:"POST",
        headers: {'Content-Type': 'application/json'},
        body: name
    }).then(r => {
        if(!r.ok) alert("ошибка добавления блюда");
        else{
            quantityElement.textContent = parseInt(quantity)+1;
            minusButton.disabled = quantityElement.textContent <= 0;
        }
    })
}
function removeFromCart(button){
    const name = button.closest('.dish-panel').querySelector('.dish-name').textContent;
    const quantityElement = button.closest('.dish-footer').querySelector('.dish-quantity');
    const quantity = quantityElement.textContent;
    console.log(name);

    fetch('/menu.removeDish', {
        method:"POST",
        headers: {'Content-Type': 'application/json'},
        body: name
    }).then(r => {
        if(!r.ok) alert("ошибка убавления блюда");
        else{
            quantityElement.textContent = quantity-1;
            button.disabled = quantityElement.textContent <= 0;
        }
    })
}