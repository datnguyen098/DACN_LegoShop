package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.service.AnhSanPhamService;
import com.fpoly.springbootdemo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/legoshop/admin/sanpham/anhsanpham")
public class AnhSanPhamAdminController {

    @Autowired
    AnhSanPhamService anhSanPhamSer;

    @Autowired
    SanPhamService sanPhamSer;

    @GetMapping("")
    public String getAllAnhSanPham(Model model) {
        model.addAttribute("list", sanPhamSer.getAllSanPham());
        model.addAttribute("content", "viewAdmin/SanPham/AnhSanPham");
        return "viewAdmin/indexAdmin";
    }

    @PostMapping("/uploadanh")
    public String uploadAnh(
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam("file") MultipartFile file) {
        try {
            anhSanPhamSer.uploadAnh(sanPhamId, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/legoshop/admin/sanpham/anhsanpham/khoanh/" + sanPhamId;
    }

    @PostMapping("/uploadanh/ajax")
    @ResponseBody
    public ResponseEntity<?> uploadAnhAjax(
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return AdminAjaxHelper.fail("Vui lòng chọn file ảnh");
            }
            anhSanPhamSer.uploadAnh(sanPhamId, file);
            return AdminAjaxHelper.ok("Thêm ảnh phụ thành công",
                    "/legoshop/admin/sanpham/anhsanpham/khoanh/" + sanPhamId);
        } catch (Exception e) {
            return AdminAjaxHelper.fail(e.getMessage() != null ? e.getMessage() : "Không thể tải ảnh lên");
        }
    }

    @GetMapping("/khoanh/{id}")
    public String khoAnh(Model model, @PathVariable Long id) {
        model.addAttribute("content", "viewAdmin/SanPham/KhoAnhPhu");
        model.addAttribute("list", anhSanPhamSer.getBySanPhamId(id));
        model.addAttribute("sanPhamId", id);
        return "viewAdmin/indexAdmin";
    }

    @PostMapping("/deleteanh")
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

    @PostMapping("/deleteanh/ajax")
    @ResponseBody
    public ResponseEntity<?> deleteAnhAjax(
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam("anhIds") List<Long> anhIds) {
        try {
            if (anhIds == null || anhIds.isEmpty()) {
                return AdminAjaxHelper.fail("Vui lòng chọn ít nhất một ảnh");
            }
            anhSanPhamSer.deleteAnhByIds(anhIds);
            return AdminAjaxHelper.ok("Đã xóa ảnh",
                    "/legoshop/admin/sanpham/anhsanpham/khoanh/" + sanPhamId);
        } catch (Exception e) {
            return AdminAjaxHelper.fail(e.getMessage() != null ? e.getMessage() : "Không thể xóa ảnh");
        }
    }
}
