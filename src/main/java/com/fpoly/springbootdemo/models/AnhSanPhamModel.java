package com.fpoly.springbootdemo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "AnhSanPham")
@NoArgsConstructor
@AllArgsConstructor
public class AnhSanPhamModel {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	private String duongDanAnh;

	@Column(name = "thuTu", nullable = false)
	private Integer thuTu;
	@ManyToOne
	@JoinColumn(name = "sanPhamId", nullable = false)
	private SanPhamModel sanPham;
}
