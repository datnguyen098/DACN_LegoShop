package com.fpoly.springbootdemo.service;

import com.fpoly.springbootdemo.models.AnhSanPhamModel;
import com.fpoly.springbootdemo.repositorys.AnhSanPhamRepository;
import com.fpoly.springbootdemo.repositorys.SanPhamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnhSanPhamService {
 @Autowired
    AnhSanPhamRepository anhSanPhamRe;
 @Autowired
    SanPhamRepository sanPhamRe;
    @Value("${upload.path}")
    private String uploadPath;
    public List<AnhSanPhamModel> getAll(){
     return anhSanPhamRe.findAll(Sort.by(Sort.Direction.DESC, "id"));
 }

    public List<AnhSanPhamModel> getBySanPhamId(Long sanPhamId) {
        return anhSanPhamRe.findBySanPham_IdOrderByThuTuAsc(sanPhamId);
    }
 // logic thêm ảnh sản phẩm mới
    @Transactional
    public void uploadAnh(Long sanPhamId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return;

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath();
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
        Files.copy(file.getInputStream(), uploadDir.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        Integer maxThuTu = anhSanPhamRe.getMaxThuTuBySanPhamId(sanPhamId.intValue());
        int thuTuMoi = (maxThuTu == null) ? 1 : maxThuTu + 1;

        AnhSanPhamModel anh = new AnhSanPhamModel();
        anh.setSanPham(sanPhamRe.getReferenceById(sanPhamId));
        anh.setDuongDanAnh(fileName);
        anh.setThuTu(thuTuMoi);
        anhSanPhamRe.save(anh);
    }

   // logic sửa ảnh sản phầm
    @Transactional
    public void updateAnh(Long anhId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return;

        AnhSanPhamModel anh = anhSanPhamRe.findById(anhId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ảnh ID: " + anhId));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadDir = Paths.get(uploadPath).toAbsolutePath();
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
        Files.copy(file.getInputStream(), uploadDir.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        anh.setDuongDanAnh(fileName);
        anhSanPhamRe.save(anh);
    }
    public Optional<AnhSanPhamModel> getOne( Long id){
        return anhSanPhamRe.findById(id);
    }

    @Transactional
    public void deleteAnhByIds(List<Long> anhIds) throws IOException {
        if (anhIds == null || anhIds.isEmpty()) return;

        Path uploadDir = Paths.get(uploadPath).toAbsolutePath();

        for (Long id : anhIds) {
            if (id == null) continue;
            Optional<AnhSanPhamModel> opt = anhSanPhamRe.findById(id);
            if (opt.isEmpty()) continue;

            AnhSanPhamModel anh = opt.get();
            String fileName = anh.getDuongDanAnh();

            // xóa DB trước/sau đều được; ưu tiên giữ DB sạch
            anhSanPhamRe.deleteById(id);

            if (fileName != null && !fileName.isBlank()) {
                Path filePath = uploadDir.resolve(fileName).normalize();
                // đảm bảo không thoát ra ngoài thư mục upload
                if (filePath.startsWith(uploadDir) && Files.exists(filePath)) {
                    Files.deleteIfExists(filePath);
                }
            }
        }
    }
}
