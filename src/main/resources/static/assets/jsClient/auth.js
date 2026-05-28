(function () {
  const overlay = document.getElementById('authOverlay');
  const toast = document.getElementById('authToast');
  const btnShowAuth = document.getElementById('btnShowAuth');
  const btnLogout = document.getElementById('btnLogout');
  const btnCloseAuth = document.getElementById('btnCloseAuth');

  if (!overlay) return;

  function showToast(msg) {
    if (!toast) return;
    toast.textContent = msg;
    toast.classList.add('show');
    setTimeout(() => toast.classList.remove('show'), 3000);
  }

  function switchTab(tab) {
    const isLogin = tab === 'login';
    document.getElementById('authBtnLogin').classList.toggle('active', isLogin);
    document.getElementById('authBtnReg').classList.toggle('active', !isLogin);
    document.getElementById('authPanelLogin').classList.toggle('active', isLogin);
    document.getElementById('authPanelReg').classList.toggle('active', !isLogin);
  }

  window.authSwitchTab = switchTab;

  window.authToggleEye = function (id, btn) {
    const inp = document.getElementById(id);
    const icon = btn.querySelector('i');
    if (inp.type === 'password') {
      inp.type = 'text';
      icon.className = 'ti ti-eye-off';
      btn.style.color = '#ff6b00';
    } else {
      inp.type = 'password';
      icon.className = 'ti ti-eye';
      btn.style.color = '';
    }
  };

  function openModal(tab) {
    switchTab(tab || 'login');
    overlay.classList.add('show');
    document.body.style.overflow = 'hidden';
  }

  function closeModal() {
    overlay.classList.remove('show');
    document.body.style.overflow = '';
  }

  function setLoggedIn(user) {
    if (btnShowAuth) btnShowAuth.classList.add('d-none');
    if (btnLogout) {
      btnLogout.classList.remove('d-none');
      const label = btnLogout.querySelector('span');
      if (label && user && user.hoTen) {
        label.textContent = user.hoTen;
      } else if (label) {
        label.textContent = 'Đăng Xuất';
      }
    }
  }

  function setLoggedOut() {
    if (btnShowAuth) btnShowAuth.classList.remove('d-none');
    if (btnLogout) {
      btnLogout.classList.add('d-none');
      const label = btnLogout.querySelector('span');
      if (label) label.textContent = 'Đăng Xuất';
    }
  }

  function checkSession() {
    fetch('/legoshop/api/auth/me')
      .then((res) => res.json())
      .then((data) => {
        if (data.loggedIn) setLoggedIn(data.user);
        else setLoggedOut();
      })
      .catch(() => setLoggedOut());
  }

  function postJson(url, body) {
    return fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    }).then((res) =>
      res.json().then((data) => ({ ok: res.ok, data }))
    );
  }

  window.authHandleLogin = function () {
    const email = document.getElementById('authLoginEmail').value.trim();
    const matKhau = document.getElementById('authLoginPass').value;
    if (!email) { showToast('Vui lòng nhập email'); return; }
    if (!matKhau) { showToast('Vui lòng nhập mật khẩu'); return; }

    const btn = document.getElementById('authSubmitLogin');
    btn.disabled = true;

    postJson('/legoshop/api/auth/login', { email, matKhau })
      .then(({ ok, data }) => {
        if (ok && data.success) {
          showToast(data.message);
          setLoggedIn(data.user);
          closeModal();
        } else {
          showToast(data.message || 'Đăng nhập thất bại');
        }
      })
      .catch(() => showToast('Lỗi kết nối máy chủ'))
      .finally(() => { btn.disabled = false; });
  };

  window.authHandleRegister = function () {
    const ho = document.getElementById('authRegLast').value.trim();
    const ten = document.getElementById('authRegFirst').value.trim();
    const email = document.getElementById('authRegEmail').value.trim();
    const soDienThoai = document.getElementById('authRegPhone').value.trim();
    const matKhau = document.getElementById('authRegPass').value;
    const xacNhanMatKhau = document.getElementById('authRegConfirm').value;

    if (!ho || !ten) { showToast('Vui lòng nhập họ và tên'); return; }
    if (!email || !email.includes('@')) { showToast('Email không hợp lệ'); return; }
    if (!soDienThoai) { showToast('Vui lòng nhập số điện thoại'); return; }
    if (matKhau.length < 8) { showToast('Mật khẩu phải có ít nhất 8 ký tự'); return; }
    if (matKhau !== xacNhanMatKhau) { showToast('Mật khẩu xác nhận không khớp'); return; }

    const btn = document.getElementById('authSubmitReg');
    btn.disabled = true;

    postJson('/legoshop/api/auth/register', { ho, ten, email, soDienThoai, matKhau, xacNhanMatKhau })
      .then(({ ok, data }) => {
        if (ok && data.success) {
          showToast(data.message);
          setLoggedIn(data.user);
          closeModal();
        } else {
          showToast(data.message || 'Đăng ký thất bại');
        }
      })
      .catch(() => showToast('Lỗi kết nối máy chủ'))
      .finally(() => { btn.disabled = false; });
  };

  if (btnShowAuth) {
    btnShowAuth.addEventListener('click', function (e) {
      e.preventDefault();
      openModal('login');
    });
  }

  if (btnLogout) {
    btnLogout.addEventListener('click', function (e) {
      e.preventDefault();
      postJson('/legoshop/api/auth/logout', {})
        .then(({ data }) => {
          showToast(data.message || 'Đã đăng xuất');
          setLoggedOut();
        })
        .catch(() => setLoggedOut());
    });
  }

  if (btnCloseAuth) btnCloseAuth.addEventListener('click', closeModal);

  overlay.addEventListener('click', function (e) {
    if (e.target === overlay) closeModal();
  });

  document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape' && overlay.classList.contains('show')) closeModal();
  });

  checkSession();
})();
