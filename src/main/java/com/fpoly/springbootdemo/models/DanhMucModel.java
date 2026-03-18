package com.fpoly.springbootdemo.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Table(name = "DanhMuc")
@NoArgsConstructor
@AllArgsConstructor
public class DanhMucModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String tenDanhMuc;

    @NotBlank(message = "Đường dẫn không được để trống")
    private String duongDan;

    @Column(nullable = false)
    private Boolean trangThai = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime ngayTao;

    @Column(nullable = false)
    private LocalDateTime ngayCapNhat;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.ngayTao = now;
        this.ngayCapNhat = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.ngayCapNhat = LocalDateTime.now();
    }

@OneToMany(mappedBy = "danhMuc", fetch = FetchType.LAZY)
private List<SanPhamModel> sanPhams;
}
