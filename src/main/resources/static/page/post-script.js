document.addEventListener('DOMContentLoaded', function() {
    // 인증 토큰 가져오기
    const token = localStorage.getItem('token');

    // 게시글 작성 이벤트 리스너
    const postForm = document.getElementById('post-form');
    if (postForm) {
        postForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const title = document.getElementById('title').value;
            const content = document.getElementById('content').value;
            const token = localStorage.getItem('jwtToken'); // 토큰 가져오기

            fetch('/api/posts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`  // 인증 토큰을 헤더에 추가
                },
                body: JSON.stringify({ title, content })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('게시글 작성 실패');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('게시글이 성공적으로 작성되었습니다.');
                    window.location.href = '/'; // 성공 시 메인 페이지로 리다이렉션
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('게시글 작성에 실패했습니다.');
                });
        });
    }

    // 게시글 수정 이벤트 리스너
    const editForm = document.getElementById('edit-post-form');
    if (editForm) {
        editForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const postId = document.getElementById('post-id').value;
            const title = document.getElementById('post-title').value;
            const content = document.getElementById('post-content').value;

            fetch(`/api/posts/${postId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`  // 인증 토큰을 헤더에 추가
                },
                body: JSON.stringify({ title, content })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('게시글 수정 실패');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(data);
                    window.location.href = '/'; // 수정 성공 시 메인 페이지로 리다이렉션
                })
                .catch(error => console.error('Error:', error));
        });
    }

    // 게시글 삭제 이벤트 리스너
    const deleteButton = document.getElementById('delete-post-button');
    if (deleteButton) {
        deleteButton.addEventListener('click', function() {
            const postId = document.getElementById('post-id').value;

            fetch(`/api/posts/${postId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`  // 인증 토큰을 헤더에 추가
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('게시글 삭제 실패');
                    }
                    console.log('Post deleted successfully');
                    window.location.href = '/'; // 삭제 성공 시 메인 페이지로 리다이렉션
                })
                .catch(error => console.error('Error:', error));
        });
    }
});
