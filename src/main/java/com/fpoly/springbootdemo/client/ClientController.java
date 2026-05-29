package com.fpoly.springbootdemo.client;

import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import com.fpoly.springbootdemo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fpoly.springbootdemo.models.SanPhamModel;

@Controller
@RequestMapping("/legoshop")
public class ClientController {
    private static final int CLIENT_PAGE_SIZE = 10;

    @Autowired
    DanhMucRepository danhMucRe;
    @Autowired
    SanPhamService sanPhamSer;
    @GetMapping("")
    public String indexClient(Model model, @RequestParam(defaultValue = "0") int page){
        Page<SanPhamModel> sanPhamPage = sanPhamSer.getSanPhamDangBanPhanTrang(Math.max(page, 0), CLIENT_PAGE_SIZE);

        model.addAttribute("content", "viewClient/indexClient.html");
        model.addAttribute("listSanPham", sanPhamPage.getContent());
        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentPage", sanPhamPage.getNumber());
        model.addAttribute("totalPages", sanPhamPage.getTotalPages());
        model.addAttribute("baseUrl", "/legoshop");
        model.addAttribute("listDanhMuc", danhMucRe.findByTrangThaiTrue());
        model.addAttribute("list", danhMucRe.findAllTenDanhMuc());
        return "viewClient/BaseClient";
    }
    @GetMapping("/danhMuc/{duongDan}")
    public String getSanPhamByDanhMuc(Model model, @PathVariable("duongDan") String duongDan,
                                      @RequestParam(defaultValue = "0") int page){
        Page<SanPhamModel> sanPhamPage = sanPhamSer.getSanPhamByDanhMucDuongDan(duongDan, Math.max(page, 0), CLIENT_PAGE_SIZE);

        model.addAttribute("list", danhMucRe.findAllTenDanhMuc());
        model.addAttribute("listDanhMuc", danhMucRe.findByTrangThaiTrue());
        model.addAttribute("listSanPham", sanPhamPage.getContent());
        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentPage", sanPhamPage.getNumber());
        model.addAttribute("totalPages", sanPhamPage.getTotalPages());
        model.addAttribute("baseUrl", "/legoshop/danhMuc/" + duongDan);
        model.addAttribute("content", "viewClient/indexClient.html");

        return "viewClient/BaseClient";
    }
}
