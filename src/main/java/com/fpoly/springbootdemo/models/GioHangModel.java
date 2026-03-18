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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "GioHang")
@NoArgsConstructor
@AllArgsConstructor
public class GioHangModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "ngayTao", nullable = false)

	private LocalDateTime ngayTao;
	@Column(name = "ngayCapNhat", nullable = false)

	private LocalDateTime ngayCapNhat;
	@ManyToOne
	@JoinColumn(name = "nguoiDungId", nullable = false)
	private NguoiDungModel nguoiDung;
}
