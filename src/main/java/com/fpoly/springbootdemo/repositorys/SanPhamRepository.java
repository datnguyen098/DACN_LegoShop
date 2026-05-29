package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.SanPhamModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPhamModel, Long> {

    long countByTrangThai(String trangThai);

    java.util.Optional<SanPhamModel> findByDuongDan(String duongDan);
    List<SanPhamModel> findByDanhMuc_DuongDanAndTrangThai(String duongDan, String trangThai);
    Page<SanPhamModel> findByTrangThai(String trangThai, Pageable pageable);
    Page<SanPhamModel> findByDanhMuc_DuongDanAndTrangThai(String duongDan, String trangThai, Pageable pageable);
}
