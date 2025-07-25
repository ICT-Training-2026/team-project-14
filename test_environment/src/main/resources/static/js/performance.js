document.addEventListener('DOMContentLoaded', () => {
  // 初期年月
  let currentYear = 2025;
  let currentMonth = 7;

  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  // 年月を2桁に整形する関数
  function pad(num) {
    return num.toString().padStart(2, '0');
  }

  document.addEventListener('click', function (e) {
    if (e.target && e.target.classList.contains('submit-button')) {
      e.preventDefault(); // デフォルトの送信を止める

      var tr = e.target.closest('tr');
      if (!tr) return;

      var select = tr.querySelector('.status-select');
      if (select) {
        select.value = '申請済み';

        var reasonInput = tr.querySelector('.performance-reason-input');
        if (reasonInput) {
          reasonInput.disabled = true;
        }

        e.target.disabled = true; // ここでdisabled化

        // 同じ行の取消ボタンを有効化
        var removeButton = tr.querySelector('.remove-button');
        if (removeButton) {
          removeButton.disabled = false;
        }

        sendUpdate(tr)
          .then(() => {
            // Ajax更新が終わったらフォーム送信やページ遷移を行う
            // 例えば、フォームがボタンの親要素の場合：
            var form = e.target.closest('form');
            if (form) {
              form.submit();
            } else {
              // フォームがない場合は必要に応じて遷移処理を記述
              // location.href = '遷移先URL';
            }
          })
          .catch(() => {
            alert('更新に失敗しました。');
            e.target.disabled = false; // 失敗したらボタンを有効に戻す
          });
      }
    }
  });

  document.addEventListener('click', function (e) {
    if (e.target && e.target.classList.contains('remove-button')) {
      e.preventDefault(); // ページ遷移やフォーム送信を一旦止める

      var tr = e.target.closest('tr');
      if (!tr) return;

      var select = tr.querySelector('.status-select');
      if (select) {
        select.value = '未申請';

        var reasonInput = tr.querySelector('.performance-reason-input');
        if (reasonInput) {
          reasonInput.disabled = false;
        }

        // 取消ボタン自身をdisabledにする
        e.target.disabled = true;

        var submitButton = tr.querySelector('.submit-button');
        if (submitButton) {
          submitButton.disabled = false;
        }

        // サーバーに更新を送信。sendUpdateがPromiseを返す想定
        sendUpdate(tr)
          .then(() => {
            var form = e.target.closest('form');
            if (form) {
              form.submit();
            } else {
              // フォームがない場合は必要に応じて遷移処理を記述
              // location.href = '遷移先URL';
            }
            // 更新成功後にフォーム送信やページ遷移を行う場合はここで実行
            // 例: e.target.form.submit(); または location.href = '遷移先URL';
            // もし遷移がないならここは不要
          })
          .catch(() => {
            alert('更新に失敗しました。');
            e.target.disabled = false; // 失敗したらボタンを有効に戻す
          });
      }
    }
  });

  //テキストに何か入力されたら
  document.querySelectorAll('.performance-reason-input').forEach((el) => {
    el.addEventListener('change', () => {
      const row = el.closest('tr');
      if (row) sendUpdate(row);
    });
  });

  //サーバーサイドの更新
  function sendUpdate(row) {
    const data = gatherData(row);
    console.log(data);
    return fetch('/performance-update', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
      body: JSON.stringify(data),
    });
  }

  //表面上のデータの吸い上げ
  function gatherData(row) {
    const startTimeValue = row.querySelector('.performance-start-input').value;
    const endTimeValue = row.querySelector('.performance-end-input').value;
    console.log(row.querySelector('.status-select').value);
    console.log(row.querySelector('.performance-id').value);
    console.log(row.querySelector('.performance-reason-input').value);
    return {
      id: row.querySelector('.performance-id').value,
      dayOfWeek: row.querySelector('.performance-dayofweek-input').value,
      date: row.querySelector('.performance-date-input').value,
      startTime: startTimeValue ? startTimeValue : null, // 空ならnull
      endTime: endTimeValue ? endTimeValue : null, // 空ならnull
      breakTime: parseInt(row.querySelector('.performance-break-input').value, 10) || 1,
      status: row.querySelector('.status-select').value,
      reason: row.querySelector('.performance-reason-input').value,
    };
  }
});
