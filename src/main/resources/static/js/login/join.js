
// --------- ▷▶ 중복 검사 --------------------------------------------------------
$(document).ready(function() {
    var errorMessage = [[${errorMessage}]];
    if(errorMessage != null) {
    alert(errorMessage);
    }
});



function idDupCheck() {
    let url="idDupCheck?id="+document.getElementById('id').value;
    console.log("여기여기");
    window.open(url, '_blank','width=400,height=300,resizable=yes,scrollbars=yes,toolbar=no');
}
/* 안먹힘....
window.onload = function() {

    document.getElementById('id').addEventListener("keydown",
        (e) => {if(e.switch == 13) {
                e.preventDefault();
                document.getElementById('idCheck').focus();
                }
        });

   document.getElementById('idCheck').addEventListener("keydown",
        (e) => {if(e.switch == 13) {
                e.preventDefault();
                document.getElementById('password').focus();
                }
        });

   document.getElementById('password').addEventListener("keydown",
        (e) => {if(e.switch == 13) {
                e.preventDefault();
                document.getElementById('pwCheck').focus();
                }
        });
   let pw1 =  document.getElementById('password').value;

   document.getElementById('pwCheck').addEventListener("keydown",
        (e) => {if(e.switch == 13) {
               e.preventDefault();
               document.getElementById('name').focus();
               }
        });
   let pw2 =  document.getElementById('pwcheck').value;
   document.getElementById('pwCheck').addEventListener("focusout",
   		() => {if(pw1 == pw2) {
                document.getElementById('p2Message').innerHTML= '사용가능합니다.';
   		}});

   document.getElementById('name').addEventListener("keydown",
          (e) => {if(e.switch == 13) {
                 e.preventDefault();
                 document.getElementById('phone').focus();
                 }
          });
   document.getElementById('phone').addEventListener("keydown",
            (e) => {if(e.switch == 13) {
                   e.preventDefault();
                   document.getElementById('email').focus();
                   }
            });*/

}
