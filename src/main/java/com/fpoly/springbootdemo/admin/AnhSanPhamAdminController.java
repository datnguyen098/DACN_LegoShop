package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import com.fpoly.springbootdemo.service.AnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/LegoShop/Admin/SanPham/AnhSanPham")
public class AnhSanPhamAdminController {
    @Autowired
    AnhSanPhamService anhSanPhamSer;

    @GetMapping("")
    public String getAllAnhSanPham(Model model) {
        model.addAttribute("list", anhSanPhamSer.getAll());
        model.addAttribute("content", "viewAdmin/SanPham/AnhSanPham");
        System.out.println("SIZE = " + anhSanPhamSer.getAll().size());
        return "viewAdmin/indexAdmin";
    }
}
