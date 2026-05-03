package com.fpoly.springbootdemo.client;

import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legoshop")
public class ClientController {
    @Autowired
    DanhMucRepository danhMucRe;
    @GetMapping("")
    public String indexClient(Model model){
        model.addAttribute("content", "viewClient/indexClient.html");
        model.addAttribute("list", danhMucRe.findAllTenDanhMuc());
        return "viewClient/BaseClient";
    }
}
