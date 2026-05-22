package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.TonKhoModel;
import com.fpoly.springbootdemo.repositorys.TonKhoRepository;
import com.fpoly.springbootdemo.service.TonKhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/legoshop/admin/sanpham/tonkho")
public class TonKhoAdminController {

	@Autowired
	TonKhoService tonKhoSer;

	@Autowired
	TonKhoRepository tonKhoRepository;

	@GetMapping("")
	public String getAllTonKho(Model model) {
		model.addAttribute("content", "viewAdmin/SanPham/SanPhamTonKho");
		model.addAttribute("list", tonKhoSer.getAllTonKho());
		return "viewAdmin/indexAdmin";
	}

	@PostMapping("/nhap")
	public String nhapThemSoLuong(@RequestParam Long sanPhamId,
								  @RequestParam int soLuongNhap) {
		tonKhoSer.nhapHang(sanPhamId, soLuongNhap);
		return "redirect:/legoshop/admin/sanpham/tonkho";
	}

	@PostMapping("/nhap/ajax")
	@ResponseBody
	public ResponseEntity<?> nhapThemAjax(@RequestParam Long sanPhamId,
										  @RequestParam int soLuongNhap) {
		try {
			tonKhoSer.nhapHang(sanPhamId, soLuongNhap);
			TonKhoModel tk = tonKhoRepository.findById(sanPhamId).orElseThrow();
			return ResponseEntity.ok(Map.of(
					"success", true,
					"message", "Nhập kho thành công (+" + soLuongNhap + ")",
					"sanPhamId", sanPhamId,
					"soLuongTon", tk.getSoLuongTon()
			));
		} catch (Exception e) {
			return AdminAjaxHelper.fail(e.getMessage() != null ? e.getMessage() : "Nhập kho thất bại");
		}
	}
}