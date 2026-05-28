package com.fpoly.springbootdemo.config;

import com.fpoly.springbootdemo.models.NguoiDungModel;
import com.fpoly.springbootdemo.repositorys.NguoiDungRepsitory;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String RAW_SEED_PASSWORD = "123456";
    private static final String LEGACY_SEED_HASH =
            "$2a$10$Dow1KjXnK3VYB2nY7R2fOu7RrV7q4N1R8m8RzGmQnTqXW7xC6QbS2";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, NguoiDungRepsitory nguoiDungRepsitory) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/legoshop/admin/nguoidung/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/vaitro/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/magiamgia/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/baocao/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/caidat/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/legoshop/admin/").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/legoshop/admin/sanpham/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/legoshop/admin/danhmuc/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/legoshop/admin/nhanvien/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/legoshop/admin/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/admin", "/admin/").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(
                                "/login",
                                "/do-login",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/assets/**",
                                "/uploads/**"
                        ).permitAll()
                        .requestMatchers("/legoshop/cart", "/legoshop/cart/**").permitAll()
                        .requestMatchers("/legoshop/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/do-login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            String username = request.getParameter("username");
                            nguoiDungRepsitory.findByEmailOrSoDienThoai(username, username)
                                    .or(() -> nguoiDungRepsitory.findByEmailOrSoDienThoai(authentication.getName(), authentication.getName()))
                                    .ifPresent(user -> {
                                        suaHashSeedCuSauKhiDangNhap(user, request.getParameter("password"), nguoiDungRepsitory);
                                        saveAdminUserToSession(request.getSession(), user);
                                    });

                            response.sendRedirect("/legoshop/admin");
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bcrypt.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String hash = encodedPassword == null ? "" : encodedPassword.trim();

                if (RAW_SEED_PASSWORD.contentEquals(rawPassword) && LEGACY_SEED_HASH.equals(hash)) {
                    return true;
                }

                try {
                    return bcrypt.matches(rawPassword, hash);
                } catch (IllegalArgumentException ex) {
                    return false;
                }
            }
        };
    }

    private void suaHashSeedCuSauKhiDangNhap(NguoiDungModel user, String rawPassword, NguoiDungRepsitory repository) {
        String hash = user.getMatKhauHash() == null ? "" : user.getMatKhauHash().trim();
        if (!RAW_SEED_PASSWORD.equals(rawPassword) || !LEGACY_SEED_HASH.equals(hash)) {
            return;
        }

        user.setMatKhauHash(new BCryptPasswordEncoder().encode(RAW_SEED_PASSWORD));
        user.setNgayCapNhat(LocalDateTime.now());
        repository.save(user);
    }

    private void saveAdminUserToSession(HttpSession session, NguoiDungModel user) {
        session.setAttribute("adminUserId", user.getId());
        session.setAttribute("userId", user.getId());

        if (user.getVaiTro() != null) {
            session.setAttribute("adminVaiTroId", user.getVaiTro().getId());
            session.setAttribute("adminMaVaiTro", user.getVaiTro().getMaVaiTro());
            session.setAttribute("vaiTroId", user.getVaiTro().getId());
            session.setAttribute("maVaiTro", user.getVaiTro().getMaVaiTro());
        }
    }
}
