package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.SanPhamModel;
import com.fpoly.springbootdemo.service.DanhMucService;
import com.fpoly.springbootdemo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



import jakarta.validation.Valid;

@Controller
@RequestMapping("/LegoShop/Admin/SanPham")
public class SanPhamAdminController {
	@Autowired
	SanPhamService sanPhamSer;
	@Autowired
	DanhMucService danhMucSer;

	@GetMapping("")
	public String adminSanPham(Model model) {
		model.addAttribute("content", "viewAdmin/SanPham/SanPhamAdmin");
		model.addAttribute("list", sanPhamSer.getAllSanPham());
		return "viewAdmin/indexAdmin";
	}

	@GetMapping("/toggle/{id}")
	public String toggle(@PathVariable("id") Long id) {
		sanPhamSer.doiTrangThai(id);
		return "redirect:/LegoShop/Admin/SanPham";
	}

	@GetMapping("/Add")
	public String formAddSanPham(Model model) {
		model.addAttribute("model", new SanPhamModel());
		model.addAttribute("list", danhMucSer.getAllDanhMuc());
		model.addAttribute("content", "viewAdmin/SanPham/addSanPham");
		return "viewAdmin/indexAdmin";
	}

	@PostMapping("/Store")
	public String addSanPham(Model model, @Valid @ModelAttribute("model") SanPhamModel sanPham, BindingResult result) {
	    System.out.println("== HIT POST /Store ==");
	    System.out.println("HasErrors=" + result.hasErrors());
	    result.getFieldErrors().forEach(e -> System.out.println(e.getField() + " : " + e.getDefaultMessage()));
	    System.out.println("DanhMuc=" + (sanPham.getDanhMuc()==null ? null : sanPham.getDanhMuc().getId()));

	    if (result.hasErrors()) {
	        model.addAttribute("list", danhMucSer.getAllDanhMuc());
	        model.addAttribute("content", "viewAdmin/SanPham/addSanPham"); // bỏ .html cho đồng bộ
	        return "viewAdmin/indexAdmin";
	    }

	    sanPhamSer.addSanPham(sanPham);
	    return "redirect:/LegoShop/Admin/SanPham";
	}
	@GetMapping("/Edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("model", sanPhamSer.showSanPham(id));
		model.addAttribute("list", danhMucSer.getAllDanhMuc());
		model.addAttribute("content", "viewAdmin/SanPham/editSanPham.html");
		return "viewAdmin/indexAdmin";
	}
	@PostMapping("/Edit")
	public String updateSanPham(Model model, @ModelAttribute("model") SanPhamModel sanPham) {
	
		sanPhamSer.updateSanPham(sanPham);
		return "redirect:/LegoShop/Admin/SanPham";
	}
}
