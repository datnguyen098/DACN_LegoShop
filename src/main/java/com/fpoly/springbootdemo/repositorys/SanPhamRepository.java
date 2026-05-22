package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.SanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import jakarta.transaction.Transactional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPhamModel, Long> {

    long countByTrangThai(String trangThai);

    java.util.Optional<SanPhamModel> findByDuongDan(String duongDan);
}
