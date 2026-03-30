package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.SanPhamModel;
import com.fpoly.springbootdemo.service.DanhMucService;
import com.fpoly.springbootdemo.service.SanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

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
    public String addSanPham(Model model, @Valid @ModelAttribute("model") SanPhamModel sanPham, BindingResult result, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                System.out.println("VALIDATION ERROR");
                result.getAllErrors().forEach(e -> System.out.println(e.toString()));
                model.addAttribute("list", danhMucSer.getAllDanhMuc());
                model.addAttribute("content", "viewAdmin/SanPham/addSanPham");
                System.out.println("FILE NULL? " + (file == null));
                System.out.println("FILE NAME: " + file.getOriginalFilename());
                return "viewAdmin/indexAdmin";
            }
            sanPhamSer.addSanPham(sanPham,file);
            return "redirect:/LegoShop/Admin/SanPham";
        } catch (Exception e) {
            e.printStackTrace(); // in lỗi ra console
            System.out.println("có lỗi ở:" + e.getMessage());

            model.addAttribute("list", danhMucSer.getAllDanhMuc());
            model.addAttribute("content", "viewAdmin/SanPham/addSanPham");

            return "viewAdmin/indexAdmin";
        }


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
