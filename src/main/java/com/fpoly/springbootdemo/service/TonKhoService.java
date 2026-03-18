package com.fpoly.springbootdemo.service;

import java.util.List;

import com.fpoly.springbootdemo.models.TonKhoModel;
import com.fpoly.springbootdemo.repositorys.TonKhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import jakarta.transaction.Transactional;

@Service
public class TonKhoService {
@Autowired
TonKhoRepository tonKhoRe;
 public List<TonKhoModel> getAllTonKho(){
	 return tonKhoRe.findAll();
 }
 @Transactional
 public void nhapHang(Long sanPhamId, int soLuongNhap) {
     if (soLuongNhap <= 0) throw new RuntimeException("Số lượng nhập phải > 0");
     int updated = tonKhoRe.nhapThem(sanPhamId, soLuongNhap);
     if (updated == 0) throw new RuntimeException("Không tìm thấy tồn kho");
 }
}
