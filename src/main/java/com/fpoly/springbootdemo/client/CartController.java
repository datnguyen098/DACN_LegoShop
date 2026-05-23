package com.fpoly.springbootdemo.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/legoshop/gio-hang")
public class CartController {
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("content", "viewClient/cart.html");
        return "viewClient/BaseClient";
    }
}
