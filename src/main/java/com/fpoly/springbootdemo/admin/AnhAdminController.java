package com.fpoly.springbootdemo.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/LegoShop/Admin/SanPham/AnhSanPham")
public class AnhAdminController {
	@GetMapping("")
public String index(Model model) {
	model.addAttribute("content", "viewAdmin/SanPham/AnhSanPham");
	return "viewAdmin/indexAdmin";
}
}
