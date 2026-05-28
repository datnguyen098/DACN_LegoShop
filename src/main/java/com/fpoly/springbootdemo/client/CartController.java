package com.fpoly.springbootdemo.client;

import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legoshop/cart")
public class CartController {
    @Autowired
    DanhMucRepository danhMucRe;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "viewClient/cart.html");
        model.addAttribute("list", danhMucRe.findAllTenDanhMuc());
        return "viewClient/BaseClient";
    }
}
