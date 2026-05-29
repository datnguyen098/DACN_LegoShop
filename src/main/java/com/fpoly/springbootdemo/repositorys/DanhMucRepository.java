package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.DanhMucModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DanhMucRepository extends JpaRepository<DanhMucModel, Long> {

    long countByTrangThaiTrue();

    java.util.Optional<DanhMucModel> findByDuongDan(String duongDan);

    @Query("select dm.tenDanhMuc from DanhMucModel dm where dm.trangThai = true")
    List<String> findAllTenDanhMuc();
 List<DanhMucModel> findByTrangThaiTrue();
}
