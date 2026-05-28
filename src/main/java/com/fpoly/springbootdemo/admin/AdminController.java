package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legoshop/admin")
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
public class AdminController {

    @Autowired
    private AdminDashboardService adminDashboardService;

    @GetMapping("")
    public String adminDashBoard(Model model) {
        model.addAttribute("stats", adminDashboardService.thongKe());
        model.addAttribute("content", "viewAdmin/DashBoard.html");
        return "viewAdmin/indexAdmin";
    }

    @GetMapping("/nhanvien")
    public String nhanVien(Model model) {
        model.addAttribute("content", "viewAdmin/NhanVien/NhanVienAdmin.html");
        return "viewAdmin/indexAdmin";
    }
}
