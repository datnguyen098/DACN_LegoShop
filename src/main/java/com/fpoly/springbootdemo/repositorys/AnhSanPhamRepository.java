package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPhamModel, Long> {
    @Query("SELECT MAX(a.thuTu) FROM AnhSanPhamModel a WHERE a.sanPham.id = :id")
    Integer getMaxThuTuBySanPhamId(@Param("id") Integer id);

    List<AnhSanPhamModel> findBySanPham_IdOrderByThuTuAsc(Long sanPhamId);
}
