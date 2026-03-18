package com.fpoly.springbootdemo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "MaGiamGia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaGiamGiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã giảm giá không được để trống")
    @Size(max = 50, message = "Mã giảm giá tối đa 50 ký tự")
    @Column(name = "ma", length = 50, nullable = false, unique = true)
    private String ma;

    @NotBlank(message = "Tên mã giảm giá không được để trống")
    @Size(max = 200, message = "Tên tối đa 200 ký tự")
    @Column(name = "ten", length = 200, nullable = false)
    private String ten;

    // PHAN_TRAM / TIEN
    @NotBlank(message = "Loại giảm không được để trống")
    @Size(max = 20, message = "Loại giảm tối đa 20 ký tự")
    @Column(name = "loaiGiam", length = 20, nullable = false)
    private String loaiGiam;

    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.01", inclusive = true, message = "Giá trị giảm phải > 0")
    @Digits(integer = 16, fraction = 2, message = "Giá trị giảm tối đa 18 chữ số, 2 chữ số thập phân")

    @Column(name = "giaTriGiam", precision = 18, scale = 2, nullable = false)
    private BigDecimal giaTriGiam;

    @DecimalMin(value = "0.00", inclusive = true, message = "Giảm tối đa phải >= 0")
    @Digits(integer = 16, fraction = 2, message = "Giảm tối đa tối đa 18 chữ số, 2 chữ số thập phân")
    @Column(name = "giamToiDa", precision = 18, scale = 2)
    private BigDecimal giamToiDa;

    @DecimalMin(value = "0.00", inclusive = true, message = "Đơn tối thiểu phải >= 0")
    @Digits(integer = 16, fraction = 2, message = "Đơn tối thiểu tối đa 18 chữ số, 2 chữ số thập phân")
    @Column(name = "donToiThieu", precision = 18, scale = 2)
    private BigDecimal donToiThieu;

    @Min(value = 0, message = "Giới hạn lượt dùng phải >= 0")
    @Column(name = "gioiHanLuotDung")
    
    private Integer gioiHanLuotDung;

    @Min(value = 0, message = "Giới hạn mỗi người phải >= 0")
    @Column(name = "gioiHanMoiNguoi")
    private Integer gioiHanMoiNguoi;

    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @Column(name = "batDauLuc", nullable = false, columnDefinition = "DATETIME2(0)")
    private LocalDateTime batDauLuc;

    @NotNull(message = "Thời gian kết thúc không được để trống")
    @Column(name = "ketThucLuc", nullable = false, columnDefinition = "DATETIME2(0)")
    private LocalDateTime ketThucLuc;

    @NotNull
    @Column(name = "trangThai", nullable = false)
    private Boolean trangThai = true;
}