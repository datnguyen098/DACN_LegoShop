package com.fpoly.springbootdemo.service;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import com.fpoly.springbootdemo.repositorys.AnhSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnhSanPhamService {
 @Autowired
    AnhSanPhamRepository anhSanPhamRe;
    public List<AnhSanPhamModel> getAll(){
     return anhSanPhamRe.findAll(Sort.by(Sort.Direction.DESC, "id"));
 }
}
