document.addEventListener("DOMContentLoaded", () => {
    const openCartBtn = document.getElementById("openCartBtn");
    const cartModal = document.getElementById("cartModal");
    const cartItems = document.getElementById("cartItems");
    const closeCartBtn = document.getElementById("closeCartBtn");
    const totalPrice = document.getElementById("totalPrice");
    const submitButton = document.getElementById('submitCart');

    // Открыть корзину
    openCartBtn.addEventListener("click", () => {
        fetch('menu/basket').then(r => r.json()).then(data =>{
            console.log(data);
            cartItems.innerHTML='';
            if(Object.keys(data.dishes).length === 0){
                cartItems.innerHTML = `<li class="cart-item"> 
                                            <span class="item-name">Кажется тут ничего...</span> 
                                        </li>`
            }
            else{
                const dishesMap = Object.entries(data.dishes);
                dishesMap.forEach(([key, value]) => {
                    let dishElement = document.createElement('li');
                    dishElement.innerHTML = `<li class="cart-item">
                    <span class="item-name"> ${key} </span>
                    <span class="item-quantity">${value} шт</span>
                    </li>`
                    cartItems.appendChild(dishElement);
                })
            }
            totalPrice.textContent = data.totalCost + ' ₽';
            cartModal.style.display = "block";
        })
    });

    // Закрыть корзину
    closeCartBtn.addEventListener("click", () => {
        cartModal.style.display = "none";
    });

    // Закрыть корзину при клике вне контента
    window.addEventListener("click", (event) => {
        if (event.target === cartModal) {
            cartModal.style.display = "none";
        }
    });

    submitButton.addEventListener("click", () => {
        window.location.replace("/processedOrder");
    })
});