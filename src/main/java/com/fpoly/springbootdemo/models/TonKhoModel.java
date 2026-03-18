package com.fpoly.springbootdemo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "TonKho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TonKhoModel {

    @Id
    @Column(name = "sanPhamId")
    private Long sanPhamId;

    @OneToOne(fetch = FetchType.EAGER) 
    @MapsId   // dùng chung khóa chính với SanPham
    @JoinColumn(name = "sanPhamId")
    private SanPhamModel sanPham;

    @Min(value = 0, message = "Số lượng tồn không được âm")
    @Column(name = "soLuongTon", nullable = false)
    private Integer soLuongTon = 0;

    @Min(value = 0, message = "Số lượng giữ không được âm")
    @Column(name = "soLuongGiu", nullable = false)
    private Integer soLuongGiu = 0;
}
