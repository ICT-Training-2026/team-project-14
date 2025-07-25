document.addEventListener('DOMContentLoaded', () => {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  //テキストに何か入力されたら
  document.querySelectorAll('.performance-diffReason-input').forEach((el) => {
    el.addEventListener('change', () => {
      const row = el.closest('tr');
      if (row) sendUpdate(row);
    });
  });

  //サーバーサイドの更新
  function sendUpdate(row) {
    const data = gatherData(row);
    console.log(data);
    return fetch('/reperformance-update', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', [csrfHeader]: csrfToken },
      body: JSON.stringify(data),
    });
  }

  const resubmitButton = document.querySelector('.resubmit-button-on');
  if (!resubmitButton) return;

  resubmitButton.addEventListener('click', function (event) {
    event.preventDefault(); // まずデフォルトのフォーム送信を止める

    const rows = document.querySelectorAll('.reperformance-database tbody tr.performance-row');

    rows.forEach((row) => {
      const statusSelect = row.querySelector('.status-select');
      if (statusSelect) {
        statusSelect.value = '再申請済み';
      }

      const hiddenStatusInput = row.querySelector('input[type="hidden"][name="status"]');
      if (hiddenStatusInput) {
        hiddenStatusInput.value = '再申請済み';
      }
    });

    const updatePromises = Array.from(rows).map((row) => sendUpdate(row));

    Promise.all(updatePromises)
      .then(() => {
        // 全てのAjax更新が完了したらフォーム送信を行う
        // もしフォームがボタンの親フォームなら以下で送信可能
        event.target.form.submit();

        // または、明示的にlocation.hrefで遷移させる場合はここで行う
        // 例: location.href = event.target.form.action;
      })
      .catch((error) => {
        console.error('更新処理でエラーが発生しました:', error);
        alert('更新処理中にエラーが発生しました。もう一度お試しください。');
        // フォーム送信は中止済みなのでここで特にしなくてよい
      });
  });

  //表面上のデータの吸い上げ
  function gatherData(row) {
    const startTimeValue = row.querySelector('.performance-start-input').value;
    const endTimeValue = row.querySelector('.performance-end-input').value;
    console.log(row.querySelector('.status-select').value);
    // console.log(row.querySelector('.performance-reason-input').value);
    return {
      id: row.querySelector('.reperformance-id').value,
      dayOfWeek: row.querySelector('.performance-dayofweek-input').value,
      date: row.querySelector('.performance-date-input').value,
      startTime: startTimeValue ? startTimeValue : null, // 空ならnull
      endTime: endTimeValue ? endTimeValue : null, // 空ならnull
      breakTime: parseInt(row.querySelector('.performance-break-input').value, 10) || 1,
      status: row.querySelector('.status-select').value,
      reason: row.querySelector('.performance-reason-input').value,
      diffReason: row.querySelector('.performance-diffReason-input').value,
    };
  }
});
