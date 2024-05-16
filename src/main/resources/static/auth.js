document.addEventListener('DOMContentLoaded', function () {
    // 회원가입 폼 이벤트 리스너
    const signupForm = document.getElementById('signup-form');
    if (signupForm) {
        signupForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const email = document.getElementById('signup-email').value;
            const nickname = document.getElementById('signup-nickname').value;
            const password = document.getElementById('signup-password').value;
            // 로그를 출력하여 전송 데이터 확인
            console.log("Sending data:", { email, nickname, password });

            fetch('/api/auth/signup', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, nickname, password })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Signup failed');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('회원가입 성공!');
                    window.location.href = '/login.html';
                })
                .catch(error => {
                    console.error('회원가입 중 오류 발생:', error);
                    alert('회원가입 실패: ' + error.message);
                });
        });
    }

    // 로그인 폼 이벤트 리스너
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const email = document.getElementById('login-email').value;
            const password = document.getElementById('login-password').value;

            fetch('/api/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ username: email, password: password })
            })
                .then(response => { // response 객체를 여기서 처리
                    if (response.ok) {
                        return response.json().then(data => {
                            alert('로그인 성공!');
                            window.location.href = '/'; // 예: 홈 페이지
                        });
                    } else {
                        return response.json().then(data => {
                            alert('로그인 실패: ' + data.returnMessage); // 실패 시 서버에서 보낸 메시지 표시
                        });
                    }
                })
                .catch(error => {
                    console.error('로그인 중 오류 발생:', error);
                });
        });
    }
});
