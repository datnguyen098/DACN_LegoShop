package com.fpoly.springbootdemo.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/LegoShop/Admin")
public class AdminController {
@GetMapping("")
public String adminDashBoard(Model model) {
	model.addAttribute("content","viewAdmin/DashBoard.html");
	return "viewAdmin/indexAdmin";
}
}
