package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import com.fpoly.springbootdemo.service.AnhSanPhamService;
import com.fpoly.springbootdemo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/legoshop/admin/sanpham/anhsanpham")
public class AnhSanPhamAdminController {

    @Autowired
    AnhSanPhamService anhSanPhamSer;

    @Autowired
    SanPhamService sanPhamSer;

    // Danh sách
    @GetMapping("")
    public String getAllAnhSanPham(Model model) {
        model.addAttribute("list", sanPhamSer.getAllSanPham());
        model.addAttribute("content", "viewAdmin/SanPham/AnhSanPham");
        System.out.println("SIZE = " + anhSanPhamSer.getAll().size());
        return "viewAdmin/indexAdmin";
    }

    // Upload ảnh
    @PostMapping("/upload")
    public String uploadAnh(
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam("file") MultipartFile file) {

        try {
            anhSanPhamSer.uploadAnh(sanPhamId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/legoshop/admin/sanpham/anhsanpham";
    }



    // 🔹 Kho ảnh
    @GetMapping("/khoanh/{id}")
    public String khoAnh(Model model, @PathVariable Long id) {
        model.addAttribute("content", "viewAdmin/SanPham/KhoAnhPhu");
        model.addAttribute("list", anhSanPhamSer.getBySanPhamId(id));
        model.addAttribute("sanPhamId", id);
        return "viewAdmin/indexAdmin";
    }

    // Delete ảnh
    @PostMapping("/delete")
    public String deleteAnh(
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam("anhIds") List<Long> anhIds) {

        try {
            anhSanPhamSer.deleteAnhByIds(anhIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/legoshop/admin/sanpham/anhsanpham/khoanh/" + sanPhamId;
    }
}