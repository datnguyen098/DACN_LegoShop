document.addEventListener('DOMContentLoaded', function () {

    const tbody     = document.querySelector('#table1 tbody');
    const headerRow = document.getElementById('headerRow');
    const addColTh  = document.getElementById('addColTh');
    const rows      = Array.from(tbody.querySelectorAll('tr[data-id]'));

    // ── 1. Parse data-anhs thành mảng {anhId, fileName} ──
    rows.forEach(tr => {
        const raw = tr.dataset.anhs || '';
        tr._anhs = raw.split(',')
            .map(s => s.trim())
            .filter(s => s && s.includes(':') && s.split(':')[1])
            .map(s => {
                const [id, ...rest] = s.split(':');
                return { anhId: id, fileName: rest.join(':') };
            });
    });

    // ── 2. Tính số cột ảnh phụ nhiều nhất ──
    let maxCols = Math.max(0, ...rows.map(tr => tr._anhs.length));

    // ── 3. Render header cột ảnh phụ ──
    function renderHeaders(n) {
        headerRow.querySelectorAll('.col-anh-phu').forEach(th => th.remove());
        for (let i = 1; i <= n; i++) {
            const th = document.createElement('th');
            th.textContent = 'Ảnh ' + i;
            th.className = 'col-anh-phu';
            headerRow.insertBefore(th, addColTh);
        }
    }

    // ── 4. Render td ảnh phụ cho mỗi row ──
    function renderImageCells(tr, totalCols) {
        tr.querySelectorAll('.td-anh-phu').forEach(td => td.remove());

        const uploadTd = tr.querySelector('.upload-cell');

        for (let i = 0; i < totalCols; i++) {
            const td = document.createElement('td');
            td.className = 'td-anh-phu';

            const item = tr._anhs[i];
            if (item) {
                td.innerHTML = `
                    <form action="/LegoShop/Admin/SanPham/AnhSanPham/UpdateAnh"
                          method="post" enctype="multipart/form-data">
                        <input type="hidden" name="anhId" value="${item.anhId}">
                        <label class="img-click-wrap" title="Click để đổi ảnh">
                            <img src="/uploads/${item.fileName}" alt="ảnh">
                            <span class="edit-hint">✎</span>
                            <input type="file" name="file" accept="image/*"
                                   class="d-none" onchange="this.form.submit()">
                        </label>
                    </form>`;
            } else {
                td.innerHTML = `<span class="text-muted small">—</span>`;
            }

            tr.insertBefore(td, uploadTd);
        }
    }

    // ── 5. Render toàn bộ ──
    function renderAll() {
        renderHeaders(maxCols);
        rows.forEach(tr => renderImageCells(tr, maxCols));
    }

    renderAll();
});