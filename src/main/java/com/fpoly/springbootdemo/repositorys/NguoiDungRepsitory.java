package com.fpoly.springbootdemo.repositorys;


import com.fpoly.springbootdemo.models.NguoiDungModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;

@Repository
public interface NguoiDungRepsitory extends JpaRepository<NguoiDungModel, Long> {

}
