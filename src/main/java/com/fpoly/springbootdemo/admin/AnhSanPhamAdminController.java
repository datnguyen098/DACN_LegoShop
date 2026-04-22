package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import com.fpoly.springbootdemo.service.AnhSanPhamService;
import com.fpoly.springbootdemo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/LegoShop/Admin/SanPham/AnhSanPham")
public class AnhSanPhamAdminController {
    @Autowired
    AnhSanPhamService anhSanPhamSer;
    @Autowired
    SanPhamService sanPhamSer;

    @GetMapping("")
    public String getAllAnhSanPham(Model model) {
        model.addAttribute("list", sanPhamSer.getAllSanPham());

        model.addAttribute("content", "viewAdmin/SanPham/AnhSanPham");
        System.out.println("SIZE = " + anhSanPhamSer.getAll().size());
        return "viewAdmin/indexAdmin";
    }

    @PostMapping("/UploadAnh")
    public String uploadAnh(
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam("file") MultipartFile file) {
        try {
            anhSanPhamSer.uploadAnh(sanPhamId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/LegoShop/Admin/SanPham/AnhSanPham";
    }

    // ── SỬA ẢNH ──
    @PostMapping("/UpdateAnh")
    public String updateAnh(
            @RequestParam("anhId") Long anhId,
            @RequestParam("file") MultipartFile file) {
        try {
            anhSanPhamSer.updateAnh(anhId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/LegoShop/Admin/SanPham/AnhSanPham";
    }
    @GetMapping("/KhoAnh/{id}")
    public String KhoAnh(Model model, @PathVariable Long id){
        model.addAttribute("content", "viewAdmin/SanPham/KhoAnhPhu");
        // id ở đây là sanPhamId => lấy toàn bộ ảnh của sản phẩm
        model.addAttribute("list", anhSanPhamSer.getBySanPhamId(id));
        model.addAttribute("sanPhamId", id);
        return "viewAdmin/indexAdmin";
    }
}

