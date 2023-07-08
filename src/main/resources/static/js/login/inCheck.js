
//** ID
// 영문, 숫자를 어떻게 찾을 것인가 => 정규식으로 해결
// id.replace(/[a-z.0-9]/,'').length>0
// 대소문자 안가리면 i =>  '/[a-z.0-9]/i'
// replace는 첫 검색된 1개만 바뀌므로 ( 이전에 js 에서 그래서 반복문 돌렸음)
// 정규식 사용해서 g(global?) 사용 => id.replace(/[a-z.0-9]/gi,'').length>0

// ** ID
function idCheck() {
	let id=document.getElementById('id').value;
	if(id.length<4 || id.length >10) {
		document.getElementById('iMessage').innerHTML='아이디 의 길이는 4~10 입니다.';
     // document.getElementById('id').focus();
		return false;
	} else if (id.replace(/[a-z.0-9]/gi,'').length>0) {
		document.getElementById('iMessage').innerHTML='아이디 는 영문과 숫자로만 입력하세요.';
		return false;
	} else {
		document.getElementById('iMessage').innerHTML='';
		return true;
	}
} // idCheck

// ** Password : 길이4이상 10 이하, 영문, 숫자와 특수문자는 반드시 1개 이상 포함할것
// => input Tag의 type password 의 경우에는 기본적으로 영문자판으로 입력됨
// => 특수문자 : HTML 특수문자 리스트표 참고 (http://kor.pe.kr/util/4/charmap2.htm)
function pwCheck() {
   let password = document.getElementById('password').value;

	if(password.length<4 || password.length >15) {
		document.getElementById('pwMessage').innerHTML='비밀번호 의 길이는 4~15 입니다!! ';
		return false;
   } else if (password.replace(/[!-*.@]/gi, '').length>= password.length) {  //특수문자 반드시 포함 ( 보통 33 - 42 ,@ 는 64라 따로 추가해야함)
		document.getElementById('pwMessage').innerHTML='비밀번호 는 특수문자를 반드시 1개 이상 포함해야 합니다.';
      return false;
   } else if (password.replace(/[a-z.0-9.!-*.@]/gi,'').length > 0) {
		document.getElementById('pwMessage').innerHTML='비밀번호 는 영문과 숫자로만 입력하세요.';
		return false;
	} else {
		document.getElementById('pwdMessage').innerHTML='';
		return true;
   }
} //pwCheck

// ** password2 재입력 확인 : password 와 동일성 확인
function pw2Check() {
   let password = document.getElementById('password').value;
   let password2 = document.getElementById('pwCheck').value;

	if( password != password2) {
      document.getElementById('p2Message').innerHTML = '비밀번호 가 일치하지 않습니다.';
      return false;
   } else {
      document.getElementById('p2Message').innerHTML = '';
      return true;
   }
} //pw2Check

// ** Name : 길이(2이상), 영문 또는 한글로 만 입력
function nmCheck() {
	let name=document.getElementById('name').value;
	if(name.length<2) {
		document.getElementById('nMessage').innerHTML='이름 의 길이는 2글자 이상입니다.';
		return false;
	} else if (name.replace(/[a-z.가-힣]/gi,'').length>0) {
		document.getElementById('nMessage').innerHTML='이름 은 한글과 영문만 입력하세요.';
		return false;
	} else {
		document.getElementById('nMessage').innerHTML='';
		return true;
	}
} // nmCheck

//** phone : 10자~11자
function phmCheck() {
    let phone=document.getElementById('phone').value;
    if(phone.replace(/[0-9]/gi, '').length>0) {
          document.getElementById('phMessage').innerHTML='전화번호 는 숫자만 입력하세요.';
          return false;
    } else if(phone.length<10){
        document.getElementById('phMessage').innerHTML='전화번호 를 재확인하세요.';
        return false;
    } else {
        document.getElementById('phMessage').innerHTML='';
        return true;
    }
}

// ** email : @가 있어야하고 .com / .net / .co.kr 등 .~이 있어야함
function emCheck() {
   let email = document.getElementById('email').value;
   if(email.replace(/[@]/gi,'').length >= email.length) {
      document.getElementById('emMessage').innerHTML='이메일 을 입력하세요.';
      return false;
   }  else if(email.replace(/[.]/gi,'').length >= email.length) {
      document.getElementById('emMessage').innerHTML='이메일 을 입력하세요.';
      return false;
   } else {
      document.getElementById('emMessage').innerHTML='';
      return true;
   }
 }
