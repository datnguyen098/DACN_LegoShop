package com.fpoly.springbootdemo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fpoly.springbootdemo.models.DanhMucModel;
import com.fpoly.springbootdemo.models.SanPhamModel;
import com.fpoly.springbootdemo.models.TonKhoModel;
import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import com.fpoly.springbootdemo.repositorys.SanPhamRepository;
import com.fpoly.springbootdemo.repositorys.TonKhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SanPhamService {
	@Autowired
	SanPhamRepository sanPhamRe;
	@Autowired
	DanhMucRepository danhMucRe;
	@Autowired
	TonKhoRepository tonKhoRe;

	public List<SanPhamModel> getAllSanPham() {
	List<SanPhamModel> lst =sanPhamRe.findAll(Sort.by(Sort.Direction.DESC, "id"));
return lst;
	}

	@Transactional
	public void doiTrangThai(Long id) {
		SanPhamModel sp = sanPhamRe.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

		String newStatus = sp.getTrangThai().equals("DANG_BAN") ? "NGUNG_BAN" : "DANG_BAN";

		sanPhamRe.updateTrangThai(id, newStatus);
	}
		@Transactional
		public void addSanPham(SanPhamModel sp, MultipartFile file) throws IOException {
			// thêm ảnh cho sản phẩm
	 if (!file.isEmpty()){
		 // Tạo Tên File duy nhất
		 String fileName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
		  Path uploadPath = Paths.get("src/main/resources/static/uploads");
		// nếu chưa có thư mục uploads thì tạo
		 if(!Files.exists(uploadPath)){
			 Files.createDirectories(uploadPath);
		 }
		 // lưu file vào thư mục uploads
		 Path filePath = uploadPath.resolve(fileName);
		 Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		 // set ten file vào Model
		 sp.setAnhChinh(fileName);

	 }
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
