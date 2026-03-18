package com.fpoly.springbootdemo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "GioHangChiTiet")
@NoArgsConstructor
@AllArgsConstructor
public class GioHangChiTietModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message = "Số lượng không được để trống")
	@Min(value = 0, message = "Số lượng không được nhỏ hơn 0")
	@Column(name = "soLuong", nullable = false)
	private int soLuong;
	@Column(name = "ngayTao", nullable = false, updatable = false)
	private LocalDateTime ngayTao;
	@Column(name = "ngayCapNhat", nullable = false)
	private LocalDateTime ngayCapNhat;
	@ManyToOne
	@JoinColumn(name = "gioHangId", nullable = false)
	private GioHangModel gioHang;

	@ManyToOne
	@JoinColumn(name = "sanPhamId", nullable = false)
	private SanPhamModel sanPham;

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
