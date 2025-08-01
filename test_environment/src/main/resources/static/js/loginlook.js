document.querySelectorAll('.toggle-password').forEach(function(btn) {
    btn.addEventListener('click', function() {
        const targetId = btn.getAttribute('data-target');
        const input = document.getElementById(targetId);
        if (input.type === 'password') {
            input.type = 'text';
            btn.textContent = '🙈'; // 表示時は目を閉じるアイコンに
        } else {
            input.type = 'password';
            btn.textContent = '👁️'; // 非表示時は目のアイコン
        }
    });
});