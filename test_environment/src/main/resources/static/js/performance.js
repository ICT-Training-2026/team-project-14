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

  document.addEventListener('click', function (e) {
      // クリックされた要素が .submit-button かどうか判定
      if (e.target && e.target.classList.contains('remove-button')) {
        // ボタンの属する<tr>を取得
        var tr = e.target.closest('tr');
        if (!tr) return;
        // その<tr>内の.status-selectを取得
        var select = tr.querySelector('.status-select');
        if (select) {
          select.value = '未申請';
          const row = e.target.closest('tr');
          sendUpdate(row);
          // 状態変更を他の処理で使う場合はchangeイベントも発火
          select.dispatchEvent(new Event('change'));
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