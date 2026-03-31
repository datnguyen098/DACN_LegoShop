package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPhamModel, Integer> {
    @Query("SELECT MAX(a.thuTu) FROM AnhSanPhamModel a WHERE a.sanPham.id = :id")
    Integer getMaxThuTuBySanPhamId(@Param("id") Integer id);
}
