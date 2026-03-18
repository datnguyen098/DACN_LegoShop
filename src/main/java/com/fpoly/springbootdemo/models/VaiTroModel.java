package com.fpoly.springbootdemo.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "VaiTro")
@AllArgsConstructor
@NoArgsConstructor
public class VaiTroModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Byte id;
	@Column(name = "maVaiTro", nullable = false)
	private String maVaiTro;
	@Column(name = "tenVaiTro", nullable = false)
	private String tenVaiTro;
	@Column(updatable = false, insertable = false)
	@OneToMany(mappedBy = "vaiTro")
	@JsonIgnore
	private List<NguoiDungModel> nguoiDungs;
}
