'use strict';

let toggle_container = document.getElementById('toggle_container'),
    toggle_Q = toggle_container.getElementsByClassName('toggle_Q'),
    toggle_A = toggle_container.getElementsByClassName('toggle_A');



for(let i = 0; i<toggle_Q.length; i++) {
    toggle_Q[i].addEventListener('click', ()=>click(i));
}

function click(i) {
    console.log(i);
    if(!toggle_A[i].classList.contains('open')){
        toggle_A[i].classList.add('open'); 
    } else {
        toggle_A[i].classList.remove('open'); 
    }
}