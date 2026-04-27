package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.DanhMucModel;
import com.fpoly.springbootdemo.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/LegoShop/Admin/DanhMuc")
public class DanhMucAdminController {
	@Autowired
	DanhMucService danhMucSer;

	@GetMapping("")
	public String adminDanhMuc(Model model) {
		model.addAttribute("list", danhMucSer.getAllDanhMuc());
		model.addAttribute("content", "viewAdmin/DanhMuc/DanhMucAdmin.html");
		return "viewAdmin/indexAdmin";
	}

	@GetMapping("/Add")
	public String formThemDanhMuc(Model model) {
		model.addAttribute("content", "viewAdmin/DanhMuc/addDanhMuc.html");
		model.addAttribute("model", new DanhMucModel());
		return "viewAdmin/indexAdmin";
	}

	@PostMapping("/Store")
	public String addDanhMuc(Model model, @Valid @ModelAttribute("model") DanhMucModel danhMuc, BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("content", "viewAdmin/DanhMuc/addDanhMuc.html");
			return "viewAdmin/indexAdmin";
		}
		danhMucSer.addDanhMuc(danhMuc);
		return "redirect:/LegoShop/Admin/DanhMuc";
	}

	@GetMapping("/toggle/{id}")
	public String toggle(@PathVariable("id") Long id) {
		danhMucSer.doiTrangThai(id);
		return "redirect:/LegoShop/Admin/DanhMuc";
	}

	@GetMapping("/Edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("model", danhMucSer.showDanhMuc(id));
		model.addAttribute("content", "viewAdmin/DanhMuc/editDanhMuc.html");
		return "viewAdmin/indexAdmin";
	}

	@PostMapping("/Edit")
	public String upDateDanhMuc(Model model, @ModelAttribute("model") DanhMucModel danhMuc) {
		danhMucSer.UpdateDanhMuc(danhMuc);
		return "redirect:/LegoShop/Admin/DanhMuc";
	}
}