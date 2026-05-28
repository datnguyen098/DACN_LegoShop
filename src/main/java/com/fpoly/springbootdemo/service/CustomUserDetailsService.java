package com.fpoly.springbootdemo.service;

import com.fpoly.springbootdemo.models.NguoiDungModel;
import com.fpoly.springbootdemo.repositorys.NguoiDungRepsitory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepsitory nguoiDungRepsitory;

    public CustomUserDetailsService(NguoiDungRepsitory nguoiDungRepsitory) {
        this.nguoiDungRepsitory = nguoiDungRepsitory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NguoiDungModel nguoiDung = nguoiDungRepsitory
                .findByEmailOrSoDienThoai(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));

        if (nguoiDung.getVaiTro() == null) {
            throw new UsernameNotFoundException("Tài khoản chưa có vai trò");
        }

        String maVaiTro = resolveRoleCode(nguoiDung);

        if (maVaiTro == null || maVaiTro.isBlank()) {
            throw new UsernameNotFoundException("Vai trò không hợp lệ");
        }

        return new User(
                nguoiDung.getEmail(),
                nguoiDung.getMatKhauHash(),
                Boolean.TRUE.equals(nguoiDung.getTrangThai()),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority("ROLE_" + maVaiTro.trim().toUpperCase()))
        );
    }

    private String resolveRoleCode(NguoiDungModel nguoiDung) {
        Byte vaiTroId = nguoiDung.getVaiTro().getId();
        if (vaiTroId != null) {
            if (vaiTroId == 1) return "ADMIN";
            if (vaiTroId == 2) return "STAFF";
            if (vaiTroId == 3) return "CUSTOMER";
        }

        return nguoiDung.getVaiTro().getMaVaiTro();
    }
}
