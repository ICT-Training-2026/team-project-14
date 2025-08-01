
document.addEventListener('DOMContentLoaded', function () {
			const exportBtn = document.getElementById('export-btn');
			const exportMessage = document.getElementById('export-message');
			exportBtn.addEventListener('click', function () {
				if (confirm('外部出力しますか？')) {
					const downloadUrl = /*[[@{/admin/company-info/export}]]*/ '/admin/export-attendance-zip';
					const a = document.createElement('a');
					a.href = downloadUrl;
					a.download = '';
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					exportMessage.style.display = 'block';
				}
			});
		});