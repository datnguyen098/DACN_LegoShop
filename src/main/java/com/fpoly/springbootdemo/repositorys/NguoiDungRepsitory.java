package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.NguoiDungModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepsitory extends JpaRepository<NguoiDungModel, Long> {

    Optional<NguoiDungModel> findByEmailOrSoDienThoai(String email, String soDienThoai);

    long countByVaiTro_MaVaiTro(String maVaiTro);

}