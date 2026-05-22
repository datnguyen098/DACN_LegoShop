package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.NguoiDungModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepsitory extends JpaRepository<NguoiDungModel, Long> {
    // khi check trùng email

    Optional<NguoiDungModel> findByEmail(String email);
//check trùng SDT
    Optional<NguoiDungModel> findBySoDienThoai(String soDienThoai);
//khi đăng nhập có thể dùng sdt hay mail đều đăng nhập được
    Optional<NguoiDungModel> findByEmailOrSoDienThoai(String email, String soDienThoai);

    long countByVaiTro_MaVaiTro(String maVaiTro);

}