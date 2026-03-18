package com.fpoly.springbootdemo.service;



import com.fpoly.springbootdemo.models.DanhMucModel;
import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class DanhMucService {
@Autowired
DanhMucRepository danhMucRe;
public List<DanhMucModel> getAllDanhMuc(){
	return danhMucRe.findAll();
}
public void addDanhMuc(DanhMucModel danhMuc) {
	danhMucRe.save(danhMuc);
}
@Transactional
public void doiTrangThai(Long id) {
    DanhMucModel dm = danhMucRe.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));

    boolean newStatus = !dm.getTrangThai();   // đảo trạng thái
    danhMucRe.updateTrangThai(id, newStatus);
}
public Optional<DanhMucModel> showDanhMuc(Long id){
	return danhMucRe.findById(id);
}
public void UpdateDanhMuc(DanhMucModel danhMuc) {
	Optional<DanhMucModel> model = danhMucRe.findById(danhMuc.getId());
	if (model.isPresent()) {
		DanhMucModel danhMucModel = model.get();
		danhMucModel.setTenDanhMuc(danhMuc.getTenDanhMuc());
		danhMucModel.setDuongDan(danhMuc.getDuongDan());
		danhMucRe.save(danhMucModel);
	}
}
}
