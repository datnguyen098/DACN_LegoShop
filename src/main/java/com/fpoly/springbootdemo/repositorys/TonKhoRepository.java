package com.fpoly.springbootdemo.repositorys;

import com.fpoly.springbootdemo.models.TonKhoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import jakarta.transaction.Transactional;

@Repository
public interface TonKhoRepository extends JpaRepository<TonKhoModel, Long> {
	@Modifying
	@Transactional
	@Query("""
	UPDATE TonKhoModel t
	SET t.soLuongTon = t.soLuongTon + :soLuongNhap
	WHERE t.sanPhamId = :sanPhamId
	""")
	int nhapThem(@Param("sanPhamId") Long sanPhamId,
	             @Param("soLuongNhap") int soLuongNhap);
}
