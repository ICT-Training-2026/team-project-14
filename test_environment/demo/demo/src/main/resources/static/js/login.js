document.addEventListener('DOMContentLoaded', () => {
  const signInTab = document.getElementById('signInTab');
  const signUpTab = document.getElementById('signUpTab');
  const signInForm = document.getElementById('signInForm');
  const signUpForm = document.getElementById('signUpForm');
  const container = document.querySelector('.container');

  // 一時的に表示状態を変えて高さを取得する関数
  function getVisibleHeight(element) {
    const originalDisplay = element.style.display;
    const originalVisibility = element.style.visibility;
    const originalPosition = element.style.position;

    element.style.display = 'block';
    element.style.visibility = 'hidden';
    element.style.position = 'absolute';

    const height = element.scrollHeight;

    element.style.display = originalDisplay;
    element.style.visibility = originalVisibility;
    element.style.position = originalPosition;

    return height;
  }

  const signInHeight = getVisibleHeight(signInForm);
  const signUpHeight = getVisibleHeight(signUpForm);

  const maxHeight = Math.max(signInHeight, signUpHeight);
  // 少し余裕を持たせて例えば+40px程度を加算すると良いです
  container.style.height = (maxHeight + 400) + 'px';

  // タブ切り替え処理
  signInTab.addEventListener('click', () => {
    signInTab.classList.add('active');
    signUpTab.classList.remove('active');
    signInForm.style.display = 'flex';
    signUpForm.style.display = 'none';
  });

  signUpTab.addEventListener('click', () => {
    signUpTab.classList.add('active');
    signInTab.classList.remove('active');
    signUpForm.style.display = 'flex';
    signInForm.style.display = 'none';
  });

  // パスワード確認チェック（登録フォーム）
  signUpForm.addEventListener('submit', function(e) {
    const pass = this.querySelector('#reg-password').value;
    const passConfirm = this.querySelector('#reg-password-confirm').value;
    if (pass !== passConfirm) {
      e.preventDefault();
      alert('パスワードとパスワード確認が一致しません。');
    }
  });
});