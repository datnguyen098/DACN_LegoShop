package com.fpoly.springbootdemo.service;



import com.fpoly.springbootdemo.models.DanhMucModel;
import com.fpoly.springbootdemo.repositorys.DanhMucRepository;
import com.fpoly.springbootdemo.util.SlugUtil;
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
	danhMuc.setDuongDan(SlugUtil.uniqueSlug(danhMuc.getTenDanhMuc(),
			slug -> danhMucRe.findByDuongDan(slug).isPresent()));
	danhMucRe.save(danhMuc);
}
// đổi trạng thái nút
	@Transactional
	public void doiTrangThai(Long id) {

		DanhMucModel dm = danhMucRe.findById(id)
				.orElseThrow(() ->
						new RuntimeException("Không tìm thấy"));

		dm.setTrangThai(!dm.getTrangThai());

		danhMucRe.save(dm);
	}
public Optional<DanhMucModel> showDanhMuc(Long id){
	return danhMucRe.findById(id);
}
public void UpdateDanhMuc(DanhMucModel danhMuc) {
	Optional<DanhMucModel> model = danhMucRe.findById(danhMuc.getId());
	if (model.isPresent()) {
		DanhMucModel danhMucModel = model.get();
		danhMucModel.setTenDanhMuc(danhMuc.getTenDanhMuc());
		String newSlug = SlugUtil.toSlug(danhMuc.getTenDanhMuc());
		if (!newSlug.equals(danhMucModel.getDuongDan())) {
			final Long id = danhMucModel.getId();
			newSlug = SlugUtil.uniqueSlug(danhMuc.getTenDanhMuc(),
					slug -> danhMucRe.findByDuongDan(slug)
							.filter(dm -> !dm.getId().equals(id))
							.isPresent());
			danhMucModel.setDuongDan(newSlug);
		}
		danhMucRe.save(danhMucModel);
	}
}
public  DanhMucModel findById(Long id){
	return danhMucRe.findById(id).orElse(null);
}
}
