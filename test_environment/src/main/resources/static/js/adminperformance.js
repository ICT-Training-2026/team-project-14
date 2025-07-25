document.addEventListener('DOMContentLoaded', () => {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  //テキストに何か入力されたら
  document.querySelectorAll('.performance-correctReason-input').forEach((el) => {
    el.addEventListener('change', () => {
      const row = el.closest('tr');
      if (row) sendUpdate(row);
    });
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
