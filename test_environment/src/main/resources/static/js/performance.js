document.addEventListener('DOMContentLoaded', () => {
  // 初期年月
  let currentYear = 2025;
  let currentMonth = 7;

  // 年月を2桁に整形する関数
  function pad(num) {
    return num.toString().padStart(2, '0');
  }

  document.addEventListener('click', function (e) {
    // クリックされた要素が .submit-button かどうか判定
    if (e.target && e.target.classList.contains('submit-button')) {
      // ボタンの属する<tr>を取得
      var tr = e.target.closest('tr');
      if (!tr) return;
      // その<tr>内の.status-selectを取得
      var select = tr.querySelector('.status-select');
      if (select) {
        select.value = '申請済み';
        const row = e.target.closest('tr');
        sendUpdate(row);
        // 状態変更を他の処理で使う場合はchangeイベントも発火
        select.dispatchEvent(new Event('change'));
      }
    }
  });

  // 表示更新関数＆プッシュして、java側も更新する
  function updateYearMonth() {
    document.getElementById('current-year-month').textContent = `${currentYear}/${pad(currentMonth)}`;

    // console.log(currentYear);
    // // 送信するデータ例
    // const data = {
    //   year: currentYear,
    //   month: currentMonth,
    //   // 他に必要なデータがあれば追加
    // };

    // // employeeIdがどこから来るかに注意（例: グローバル変数 or 別途取得）
    // fetch(`/${employeeId}/top/jisseki_user`, {
    //   method: 'GET',
    //   headers: { 'Content-Type': 'application/json' },
    //   body: JSON.stringify(data),
    // });
  }

  // 前月へ
  document.getElementById('prev-month').addEventListener('click', function () {
    currentMonth--;
    if (currentMonth < 1) {
      currentMonth = 12;
      currentYear--;
    }
    updateYearMonth();
    // 必要に応じてここで他のデータの更新処理を呼ぶ
  });

  // 次月へ
  document.getElementById('next-month').addEventListener('click', function () {
    currentMonth++;
    if (currentMonth > 12) {
      currentMonth = 1;
      currentYear++;
    }
    updateYearMonth();
    // 必要に応じてここで他のデータの更新処理を呼ぶ
  });
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  //サーバーサイドの更新
  function sendUpdate(row) {
    const data = gatherData(row);
    return fetch('/performance-update', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
      body: JSON.stringify(data),
    });
  }

  //表面上のデータの吸い上げ
  function gatherData(row) {
    return {
      id: parseInt(row.dataset.id, 10), // data-id 属性
      dayOfWeek: row.querySelector('.performance-dayofweek-input').value,
      date: row.querySelector('.performance-date-input').value, // yyyy-MM-dd 形式
      startTime: row.querySelector('.performance-start-input').value, // HH:mm
      endTime: row.querySelector('.performance-end-input').value, // HH:mm
      breakTime: parseInt(row.querySelector('.performance-break-input').value, 10), // 休憩時間（数値）
      status: row.querySelector('.status-select').value, // セレクトボックス
      reason: row.querySelector('.performance-reason-input').value, // 事由
    };
  }

  // 初期表示
  updateYearMonth();
});
