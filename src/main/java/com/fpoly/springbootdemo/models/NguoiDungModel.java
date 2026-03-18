package com.fpoly.springbootdemo.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NguoiDung")
public class NguoiDungModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Họ tên không được để trống")
	private String hoTen;
	@NotBlank(message = "Email không được để trống")
	private String email;
	@NotBlank(message = "Số điện thoại không được để trống")
	@Size(min = 10 , max = 10, message = "Số điện thoại không chính xác vui lòng nhập lại")
	private String soDienThoai;
	@Column(name = "matKhauHash", nullable = false)
	private String matKhauHash;
	@Column(name = "trangThai", nullable = false)
	private Boolean trangThai;
	@Column(name = "ngayTao", nullable = false)
	private LocalDateTime ngayTao;
	@Column(name = "ngayCapNhat", nullable = false)
	private LocalDateTime ngayCapNhat;
	@ManyToOne
	@JoinColumn(name = "vaiTroId", nullable = false)
	private VaiTroModel vaiTro;
	@OneToMany(mappedBy = "nguoiDung")
	@JsonIgnore
	private List<DiaChiModel> diaChis;
	@OneToMany(mappedBy = "nguoiDung")
	@JsonIgnore
	private List<GioHangModel> gioHangs;
}
