package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.service.DanhMucService;
import com.fpoly.springbootdemo.service.TonKhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/legoshop/admin/sanpham/tonkho")
public class TonKhoAdminController {

	@Autowired
	TonKhoService tonKhoSer;

	@Autowired
	DanhMucService danhMucSer;

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
}