package com.fpoly.springbootdemo.service;

import java.util.List;
import java.util.Optional;

import com.fpoly.springbootdemo.models.DanhMucModel;
import com.fpoly.springbootdemo.models.SanPhamModel;
import com.fpoly.springbootdemo.models.TonKhoModel;
import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import com.fpoly.springbootdemo.repositorys.SanPhamRepository;
import com.fpoly.springbootdemo.repositorys.TonKhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import jakarta.transaction.Transactional;

@Service
public class SanPhamService {
	@Autowired
	SanPhamRepository sanPhamRe;
	@Autowired
	DanhMucRepository danhMucRe;
	@Autowired
	TonKhoRepository tonKhoRe;

	public List<SanPhamModel> getAllSanPham() {
		return sanPhamRe.findAll();
	}

	@Transactional
	public void doiTrangThai(Long id) {
		SanPhamModel sp = sanPhamRe.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

		String newStatus = sp.getTrangThai().equals("DANG_BAN") ? "NGUNG_BAN" : "DANG_BAN";

		sanPhamRe.updateTrangThai(id, newStatus);
	}
	@Transactional
	public void addSanPham(SanPhamModel sp) {
		Long dmId = sp.getDanhMuc().getId();
		DanhMucModel dm = danhMucRe.findById(dmId).orElseThrow();
		sp.setDanhMuc(dm);
		SanPhamModel saved = sanPhamRe.save(sp);
		// tạo tồn kho
		TonKhoModel tk = new TonKhoModel();
		tk.setSanPham(saved);
		tk.setSoLuongTon(0);
		tk.setSoLuongGiu(0);
		tonKhoRe.save(tk);
		// tạo ảnh sản phẩm 
	}

	public Optional<SanPhamModel> showSanPham(Long id) {
		return sanPhamRe.findById(id);
	}

	public void updateSanPham(SanPhamModel sanPham) {

		SanPhamModel sp = sanPhamRe.findById(sanPham.getId()).orElseThrow();

		// ✅ rút gọn: lấy proxy reference theo id (không transient)
		Long dmId = sanPham.getDanhMuc().getId();
		sp.setDanhMuc(danhMucRe.getReferenceById(dmId));

		// Danh mục
		sp.setDanhMuc(sanPham.getDanhMuc());

		// Thông tin cơ bản
		sp.setMaSanPham(sanPham.getMaSanPham());
		sp.setTenSanPham(sanPham.getTenSanPham());
		sp.setDuongDan(sanPham.getDuongDan());
		sp.setMoTa(sanPham.getMoTa());

		// Giá
		sp.setGiaGoc(sanPham.getGiaGoc());

		// Thông số
		sp.setDoTuoiToiThieu(sanPham.getDoTuoiToiThieu());
		sp.setSoManh(sanPham.getSoManh());
		sp.setNamPhatHanh(sanPham.getNamPhatHanh());
		sanPhamRe.save(sp);
	}
}
