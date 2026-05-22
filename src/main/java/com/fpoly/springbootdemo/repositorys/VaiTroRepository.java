package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.VaiTroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTroModel, Byte> {
    Optional<VaiTroModel> findByMaVaiTro(String maVaiTro);
}
