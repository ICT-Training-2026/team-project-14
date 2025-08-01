const API_BASE = '/api/holidays';
		let allHolidays = [];

		function setupYearMonth() {
			const yearSel = document.getElementById('year-select');
			const monthSel = document.getElementById('month-select');
			const now = new Date();
			yearSel.innerHTML = '';
			monthSel.innerHTML = '';
			for (let y = now.getFullYear() - 1; y <= now.getFullYear() + 2; y++) {
				let opt = document.createElement('option');
				opt.value = y;
				opt.text = y;
				if (y === now.getFullYear()) opt.selected = true;
				yearSel.appendChild(opt);
			}
			for (let m = 1; m <= 12; m++) {
				let opt = document.createElement('option');
				opt.value = m;
				opt.text = m;
				if (m === now.getMonth() + 1) opt.selected = true;
				monthSel.appendChild(opt);
			}
		}

		async function fetchAllHolidays() {
			const res = await fetch(API_BASE);
			if (res.ok) {
				allHolidays = await res.json();
			} else {
				allHolidays = [];
			}
		}

		function loadHolidayTable() {
			const year = parseInt(document.getElementById('year-select').value);
			const month = parseInt(document.getElementById('month-select').value);
			const tbody = document.getElementById('holiday-tbody');
			tbody.innerHTML = '';
			const firstDay = new Date(year, month - 1, 1);
			const lastDay = new Date(year, month, 0);
			const weekDays = ['日', '月', '火', '水', '木', '金', '土'];

			for (let d = 1; d <= lastDay.getDate(); d++) {
				const dateStr = `${year}-${String(month).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
				const dateObj = new Date(year, month - 1, d);
				const week = weekDays[dateObj.getDay()];
				const holiday = allHolidays.find(h => h.holidayDate === dateStr);

				// 土日か？
				const isSunday = dateObj.getDay() === 0;
				const isSaturday = dateObj.getDay() === 6;

				// 行の種別・名称
				let typeLabel = "";
				let nameLabel = "";
				let opButtons = "";

				if (holiday) {
					if (holiday.holidayType === "祝日") {
						typeLabel = "祝日";
						nameLabel = holiday.holidayName && holiday.holidayName.trim() !== "" ? holiday.holidayName : "祝日";
						// 祝日も編集・削除できるように
						opButtons = `
							<button onclick="openEditDialog(${holiday.holidayId})">編集</button>
							<button onclick="deleteHoliday(${holiday.holidayId})">削除</button>
						`;
					} else if (holiday.holidayType === "休日") {
						typeLabel = "会社休日";
						nameLabel = "";
						opButtons = `
							<button onclick="openEditDialog(${holiday.holidayId})">編集</button>
							<button onclick="deleteHoliday(${holiday.holidayId})">削除</button>
						`;
					}
				} else if (!(isSunday || isSaturday)) {
					// 土日以外で会社休日でも祝日でもない日だけ追加ボタンを表示
					opButtons = `<button onclick="openCreateDialog('${dateStr}')">追加</button>`;
				}

				const tr = document.createElement('tr');
				if (holiday) tr.classList.add('holiday-cell');
				if (isSunday) tr.classList.add('sunday');
				if (isSaturday) tr.classList.add('saturday');
				tr.innerHTML = `
					<td>${dateStr}</td>
					<td${isSunday ? ' class="sunday"' : ''}${isSaturday ? ' class="saturday"' : ''}>${week}</td>
					<td>${typeLabel}</td>
					<td>${nameLabel}</td>
					<td>${holiday && holiday.holidayNote ? holiday.holidayNote : ""}</td>
					<td>${opButtons}</td>
				`;
				tbody.appendChild(tr);
			}
		}

		function openEditDialog(id) {
			const h = allHolidays.find(x => x.holidayId === id);
			document.getElementById('dialog-title').textContent = '休日編集';
			document.getElementById('dialog-id').value = h.holidayId;
			document.getElementById('dialog-date').value = h.holidayDate;
			document.getElementById('dialog-type').value = h.holidayType;
			document.getElementById('dialog-note').value = h.holidayNote || '';
			document.getElementById('dialog-name').value = h.holidayType === "祝日" ? (h.holidayName || "") : "";
			toggleNameInput();
			document.getElementById('holiday-dialog').style.display = 'flex';
		}
		function openCreateDialog(dateStr) {
			document.getElementById('dialog-title').textContent = '休日追加';
			document.getElementById('dialog-id').value = '';
			document.getElementById('dialog-date').value = dateStr || '';
			document.getElementById('dialog-type').value = '休日';
			document.getElementById('dialog-note').value = '';
			document.getElementById('dialog-name').value = '';
			toggleNameInput();
			document.getElementById('holiday-dialog').style.display = 'flex';
		}
		function closeDialog() {
			document.getElementById('holiday-dialog').style.display = 'none';
		}

		function toggleNameInput() {
			const type = document.getElementById('dialog-type').value;
			document.getElementById('div-holiday-name').style.display = (type === "祝日") ? "" : "none";
		}

		async function saveHoliday() {
			const id = document.getElementById('dialog-id').value;
			const type = document.getElementById('dialog-type').value;
			let data = {
				holidayDate: document.getElementById('dialog-date').value,
				holidayType: type,
				holidayName: type === "祝日" ? document.getElementById('dialog-name').value : "",
				holidayNote: document.getElementById('dialog-note').value
			};
			let res;
			if (id) {
				res = await fetch(`${API_BASE}/${id}`, {
					method: 'PUT',
					headers: {'Content-Type': 'application/json'},
					body: JSON.stringify(data)
				});
			} else {
				res = await fetch(API_BASE, {
					method: 'POST',
					headers: {'Content-Type': 'application/json'},
					body: JSON.stringify(data)
				});
			}
			if (res.ok) {
				await fetchAllHolidays();
				loadHolidayTable();
				renderCalendar();
				closeDialog();
			} else {
				let msg = '保存に失敗しました';
				try {
					msg += '\n' + await res.text();
				} catch (e) { }
				alert(msg);
			}
		}

		async function deleteHoliday(id) {
			if (!confirm('本当に削除しますか？')) return;
			const res = await fetch(`${API_BASE}/${id}`, {method: 'DELETE'});
			if (res.ok) {
				await fetchAllHolidays();
				loadHolidayTable();
				renderCalendar();
			} else {
				alert('削除に失敗しました');
			}
		}

		function renderCalendar() {
			const container = document.getElementById('calendar-container');
			container.innerHTML = '';
			const weekDays = ['日', '月', '火', '水', '木', '金', '土'];
			const year = parseInt(document.getElementById('year-select').value);
			for (let row = 0; row < 3; row++) {
				const rowDiv = document.createElement('div');
				rowDiv.style.display = 'flex';
				for (let col = 1; col <= 4; col++) {
					let m = row * 4 + col;
					const tbl = document.createElement('table');
					tbl.className = 'calendar-table';
					tbl.innerHTML = `<caption>${m}月</caption><tr>${weekDays.map(w => `<th>${w}</th>`).join('')}</tr>`;
					const firstDate = new Date(year, m - 1, 1);
					let rowHtml = '<tr>' + '<td></td>'.repeat(firstDate.getDay());
					let d = 1;
					for (let i = firstDate.getDay(); d <= new Date(year, m, 0).getDate(); i = (i + 1) % 7) {
					    const dateStr = `${year}-${String(m).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
					    const holiday = allHolidays.find(h => h.holidayDate === dateStr);
					    const isHoliday = !!holiday;
					    const isSunday = (i % 7) === 0;
					    const isSaturday = (i % 7) === 6;
					    let tdClass = [];
					    if (isHoliday) tdClass.push("holiday-cell");
					    if (isSunday) tdClass.push("sunday");
					    if (isSaturday) tdClass.push("saturday");
					    rowHtml += `<td class="${tdClass.join(' ')}">${d}</td>`;
					    if (i === 6) {
					        tbl.innerHTML += rowHtml + '</tr>';
					        rowHtml = '<tr>';
					    }
					    d++;
					}
					if (rowHtml !== '<tr>') tbl.innerHTML += rowHtml + '</tr>';
					rowDiv.appendChild(tbl);
				}
				container.appendChild(rowDiv);
			}
		}

		window.addEventListener('DOMContentLoaded', async function () {
			setupYearMonth();
			await fetchAllHolidays();
			loadHolidayTable();
			renderCalendar();
			document.getElementById('year-select').addEventListener('change', () => {loadHolidayTable(); renderCalendar();});
			document.getElementById('month-select').addEventListener('change', () => {loadHolidayTable();});
		});