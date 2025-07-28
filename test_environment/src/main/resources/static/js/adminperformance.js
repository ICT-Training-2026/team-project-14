document.addEventListener('DOMContentLoaded', () => {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  //テキストに何か入力されたら
  // document.querySelectorAll('.performance-correctReason-input').forEach((el) => {
  //   el.addEventListener('change', () => {
  //     const row = el.closest('tr');
  //     if (row) sendUpdate(row);
  //   });
  // });
  const inputClasses = ['.performance-start-input', '.performance-end-input', '.performance-break-input', '.performance-correctReason-input', '.work-select', '.approval-select'];

  // 各クラスのinput要素にchangeイベントを登録
  inputClasses.forEach((className) => {
    document.querySelectorAll(className).forEach((el) => {
      el.addEventListener('change', () => {
        const row = el.closest('tr');
        if (row) sendUpdate(row);
      });
    });
  });

  document.getElementById('confirm-button').addEventListener('click', function () {
    // テーブルのtbody内の各行を取得
    const table = document.querySelector('.adperformance-database');
    const rows = table.querySelectorAll('tbody tr');
    console.log('A');
	const updatePromises = [];

    rows.forEach(function (row) {
      // 各行のapproval-selectの値を取得
      const approvalSelect = row.querySelector('.approval-select');
      if (!approvalSelect) return;

      const approvalValue = approvalSelect.value;

      // ステータスのselectとhiddenを取得
      const statusSelect = row.querySelector('.adminperformance-status');

      console.log(approvalValue);

      // 承認(0)なら確定済みに、訂正(1)なら差し戻しに
      if (approvalValue == '0') {
        if (statusSelect) {
          statusSelect.value = '確定済み';
        }
      } else if (approvalValue == '1') {
        if (statusSelect) {
          statusSelect.value = '差し戻し';
        }
      }

      //sendUpdate(row);
	  updatePromises.push(sendUpdate(row));

    });

	Promise.all(updatePromises).then(() => {
	      location.reload();
	    }).catch((e) => {
	      alert('サーバー更新時にエラーが発生しました');
	      console.error(e);
	    });

    // 必要に応じてここでサーバーへ送信する処理（Ajaxやフォームsubmitなど）を追加
    // 例：alert('状態を更新しました');
  });

  //サーバーサイドの更新
  function sendUpdate(row) {
    const data = gatherData(row);
    console.log(data);
    return fetch('/adminperformance-update', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
      body: JSON.stringify(data),
    });
  }

  //表面上のデータの吸い上げ
  function gatherData(row) {
    const startTimeValue = row.querySelector('.performance-start-input').value;
    const endTimeValue = row.querySelector('.performance-end-input').value;
    // console.log(row.querySelector('.performance-reason-input').value);
    console.log('approval');
    console.log(row.querySelector('.approval-select').value);
    console.log(row.querySelector('.adminperformance-status').value);
    return {
      id: row.querySelector('.adminperformance-id').value,
      dayOfWeek: row.querySelector('.performance-dayofweek-input').value,
      date: row.querySelector('.performance-date-input').value,
      startTime: startTimeValue ? startTimeValue : null, // 空ならnull
      approval: row.querySelector('.approval-select').value,
      endTime: endTimeValue ? endTimeValue : null, // 空ならnull
      breakTime: parseInt(row.querySelector('.performance-break-input').value, 10) || 1,
      atClassification: row.querySelector('.work-select').value,
      reason: row.querySelector('.performance-reason-input').value,
      diffReason: row.querySelector('.performance-diffReason-input').value,
      correctReason: row.querySelector('.performance-correctReason-input').value,
      status: row.querySelector('.adminperformance-status').value,
    };
  }
});
