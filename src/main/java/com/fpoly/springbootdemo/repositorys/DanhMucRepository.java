package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.DanhMucModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface DanhMucRepository extends JpaRepository<DanhMucModel, Long> {

    @Modifying
    @Query("update DanhMucModel d set d.trangThai = :status where d.id = :id")
    int updateTrangThai(@Param("id") Long id, @Param("status") boolean status);
}
