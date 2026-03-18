package com.fpoly.springbootdemo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "DiaChi")
@NoArgsConstructor
@AllArgsConstructor
public class DiaChiModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Họ tên không được để trống")
	private String tenNguoiNhan;
	@NotBlank(message = "Số điện thoai không được để trống")
	@Size(min = 10, max = 10, message = "Số điện thoại không hợp lệ")
	private String soDienThoaiNhan;
	@NotBlank(message = "Tỉnh thành không được để trống")
	private String tinhThanh;
	@NotBlank(message = "Quận huyện không được để trống")
	private String quanHuyen;
	@NotBlank(message = "Phường Xã không được để trống")
	private String phuongXa;
	@NotBlank(message = "Địa chỉ cụ thể không được để trống")
	private String diaChiCuThe;
	@Column(name = "macDinh", nullable = false)

	private Boolean macDinh;
	@Column(name = "ngayTao", nullable = false)

	private LocalDateTime ngayTao;
	@Column(name = "ngayCapNhat", nullable = false)

	private LocalDateTime ngayCapNhat;
	@ManyToOne
	@JoinColumn(name =  "nguoiDungId", nullable = false)
	private NguoiDungModel nguoiDung;
}
