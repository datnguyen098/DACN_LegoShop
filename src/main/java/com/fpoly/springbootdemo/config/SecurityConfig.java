package com.fpoly.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth


                        // 1. Các quyền chỉ ADMIN được dùng
                        .requestMatchers("/legoshop/admin/nguoidung/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/vaitro/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/magiamgia/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/baocao/**").hasRole("ADMIN")
                        .requestMatchers("/legoshop/admin/caidat/**").hasRole("ADMIN")
                        // 2. ADMIN và STAFF đều được vào khu quản trị hiện tại
                        .requestMatchers("/legoshop/admin").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/legoshop/admin/").hasAnyRole("ADMIN", "STAFF")
                        // Quản lý sản phẩm, ảnh sản phẩm, tồn kho
                        .requestMatchers("/legoshop/admin/sanpham/**").hasAnyRole("ADMIN", "STAFF")
                        // Quản lý danh mục
                        .requestMatchers("/legoshop/admin/danhmuc/**").hasAnyRole("ADMIN", "STAFF")
                        // Chặn chung toàn bộ khu admin
                        .requestMatchers("/legoshop/admin/**").hasAnyRole("ADMIN", "STAFF")
                        // 3. Trang login và file tĩnh được truy cập tự do
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
                        // chỉ customer mới được truy cập
                        .requestMatchers("/legoshop/cart", "/legoshop/cart/**").permitAll()
                        .requestMatchers("/legoshop/**").permitAll()
                        // Các đường dẫn khác bắt buộc đăng nhập
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        // check đăng nhập nếu chưa đăng nhập thì đưa về login
                        .loginPage("/login")
                        // khi điền form đăng nhập và bấm đăng nhập dữ liệu sẽ được gửi đến do-login
                        .loginProcessingUrl("/do-login")

                     // hệ thống lấy tài khoản người dùng so sánh với database
                        // ng dùng nhập mail hay sdt đều có thể đăng nhập được
                        .usernameParameter("username")
                        // hệ thống sẽ so sánh password với BCrypt vì pass đã được has trong DB
                        .passwordParameter("password")
                        // nếu đăng nhập đúng mật khẩu ng dùng sẽ vào được trang admin
                        .defaultSuccessUrl("/legoshop/admin", true)
                        // nếu sai thì sẽ bị chuyển hướng trang về form đăng nhập
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
        return new BCryptPasswordEncoder();
    }
}