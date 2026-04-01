package com.fpoly.springbootdemo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "SanPham")
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Mã sản phẩm không được để trống")
	private String maSanPham;
	@NotBlank(message = "Tên sản phẩm không được để trống")
	private String tenSanPham;
	@Pattern (regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Đường dẫn chỉ gồm chữ thường, số và dấu -")
	@NotBlank(message = "Đường dẫn không được để trống")
	private String duongDan;
	@NotBlank(message = "Hãy điền thêm mô tả")
	private String moTa;
	@NotNull(message = "Giá không được để trống")
	@DecimalMin(value = "0.0", inclusive = false, message = "Giá phải > 0")
	private BigDecimal gia;
	private BigDecimal giaGoc;
	private String chuDe;
	@NotNull(message = "Độ tuổi tối thiểu không được để trống")
	@Min(value = 3, message = "Độ tuổi tối thiểu phải trên 3 tuổi")
	private Byte doTuoiToiThieu;
	@Min(value = 0, message = "Số mảnh phải lớn hơn 0")
	@NotNull(message = "Số mảnh không được để trống")
	private Integer soManh;
	@NotNull(message = "Năm phát hành không được để trống")
	@Min(value = 1950, message = "Năm phát hành không hợp lệ")
	@Max(value = 2100, message = "Năm phát hành không hợp lệ")
	private Short namPhatHanh;
	@Column(name = "trangThai", nullable = false)
	private String trangThai = "DANG_BAN";
	@Column(nullable = false, updatable = false)
	private LocalDateTime ngayTao;
	@Column(nullable = false, updatable = false)
	private LocalDateTime ngayCapNhat;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danhMucId", nullable = false)
    private DanhMucModel danhMuc;
	@OneToOne(mappedBy = "sanPham", fetch = FetchType.LAZY)
	private TonKhoModel tonKho;


	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnhSanPhamModel> danhSachAnh;
	private String AnhChinh;
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
}
