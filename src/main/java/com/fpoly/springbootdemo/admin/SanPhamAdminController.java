package com.fpoly.springbootdemo.admin;

import com.fpoly.springbootdemo.models.SanPhamModel;
import com.fpoly.springbootdemo.service.DanhMucService;
import com.fpoly.springbootdemo.service.SanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/legoshop/admin/sanpham")
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

    @GetMapping("/toggle/{id}/ajax")
    @ResponseBody
    public ResponseEntity<?> toggle(@PathVariable("id") Long id) {
        sanPhamSer.doiTrangThai(id);
       SanPhamModel sanPham = sanPhamSer.findById(id);
      return ResponseEntity.ok(Map.of("trangThai", sanPham.getTrangThai()));
    }

    @GetMapping("/add")
    public String formAddSanPham(Model model) {
        model.addAttribute("model", new SanPhamModel());
        model.addAttribute("list", danhMucSer.getAllDanhMuc());
        model.addAttribute("content", "viewAdmin/SanPham/addSanPham");
        return "viewAdmin/indexAdmin";
    }

    @PostMapping("/store")
    public String addSanPham(Model model,
                             @Valid @ModelAttribute("model") SanPhamModel sanPham,
                             BindingResult result,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("list", danhMucSer.getAllDanhMuc());
                model.addAttribute("content", "viewAdmin/SanPham/addSanPham");
                return "viewAdmin/indexAdmin";
            }
            sanPhamSer.addSanPham(sanPham, file);
            return "redirect:/legoshop/admin/sanpham";
        } catch (Exception e) {
            model.addAttribute("list", danhMucSer.getAllDanhMuc());
            model.addAttribute("content", "viewAdmin/SanPham/addSanPham");
            model.addAttribute("errorMessage", e.getMessage());
            return "viewAdmin/indexAdmin";
        }
    }

    @PostMapping("/store/ajax")
    @ResponseBody
    public ResponseEntity<?> addSanPhamAjax(@Valid @ModelAttribute("model") SanPhamModel sanPham,
                                            BindingResult result,
                                            @RequestParam(value = "file", required = false) MultipartFile file) {
        if (result.hasErrors()) {
            return AdminAjaxHelper.validationError(result);
        }
        try {
            sanPhamSer.addSanPham(sanPham, file);
            return AdminAjaxHelper.ok("Thêm sản phẩm thành công", "/legoshop/admin/sanpham");
        } catch (Exception e) {
            return AdminAjaxHelper.fail(e.getMessage() != null ? e.getMessage() : "Không thể lưu sản phẩm");
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("model", sanPhamSer.showSanPham(id));
        model.addAttribute("list", danhMucSer.getAllDanhMuc());
        model.addAttribute("content", "viewAdmin/SanPham/editSanPham.html");
        return "viewAdmin/indexAdmin";
    }

    @PostMapping("/edit")
    public String updateSanPham(Model model,
                                @Valid @ModelAttribute("model") SanPhamModel sanPham,
                                BindingResult result,
                                @RequestParam(value = "fileAnh", required = false) MultipartFile file) {
        if (result.hasErrors()) {
            model.addAttribute("list", danhMucSer.getAllDanhMuc());
            model.addAttribute("content", "viewAdmin/SanPham/editSanPham.html");
            return "viewAdmin/indexAdmin";
        }
        sanPhamSer.updateSanPham(sanPham, file);
        return "redirect:/legoshop/admin/sanpham";
    }

    @PostMapping("/edit/ajax")
    @ResponseBody
    public ResponseEntity<?> updateSanPhamAjax(@Valid @ModelAttribute("model") SanPhamModel sanPham,
                                               BindingResult result,
                                               @RequestParam(value = "fileAnh", required = false) MultipartFile file) {
        if (result.hasErrors()) {
            return AdminAjaxHelper.validationError(result);
        }
        try {
            sanPhamSer.updateSanPham(sanPham, file);
            return AdminAjaxHelper.ok("Cập nhật sản phẩm thành công", "/legoshop/admin/sanpham");
        } catch (Exception e) {
            return AdminAjaxHelper.fail(e.getMessage() != null ? e.getMessage() : "Không thể cập nhật sản phẩm");
        }
    }
}