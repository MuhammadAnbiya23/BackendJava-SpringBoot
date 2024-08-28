package com.domain.models.entities;

import java.util.Collection;
import java.util.Collections;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "tbl_users")
public class AppUser implements UserDetails { // AppUser mengimplementasikan UserDetails, yang merupakan bagian dari Spring Security. 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 150, nullable = false)
    private String fullname;
    @Column(length = 150, nullable = false, unique = true)
    private String email;
    @Column(length = 200, nullable = false) // kalau error muncul status 500 karena belum di custom
    private String password;
    @Enumerated((EnumType.STRING)) // tipe data khusus yang berisi sekumpulan konstanta yang telah ditentukan sebelumnya
    private AppUserRole appUserRole;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {  // implemented method dari UserDetails
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority); // Intinya dia ngecek kalau yang masuk itu admin atau user dari appUserRole
    }

    @Override
    public String getPassword() { // untuk keperluan authentikasi untuk mengembalikan kata sandi pengguna
        return password;
    }

    @Override
    public String getUsername() { // untuk keperluan authentikasi untuk mengembalikan username pengguna
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { // class yang digunakan oleh Spring Security untuk mengecek apakah akun kadaluarsa atau tidak
        return  true; // karena return nya true maka akun tidak akan pernah kadaluarsa
    }

    @Override
    public boolean isAccountNonLocked() { // class yang digunakan oleh Spring Security untuk mengecek apakah akun di terkunci
        return true; // karena return nya true maka akun tidak akan pernah terkunci
    }

    @Override 
    public boolean isCredentialsNonExpired() { // class yang digunakan oleh Spring Security untuk mengecek apakah kredensial (misal sandi) kadaluarsa atau tidak
        return true; // karena return nya true maka kredensial nya nya tidak akan kadaluarsa
    }

    @Override 
    public boolean isEnabled() { // class yang digunakan oleh Spring Security untuk mengecek apakah akun pengguna aktif
        return true; // karena return nya true maka akun pengguna akan selalu di aktifkan
    }

    //Setter Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AppUserRole getAppUserRole() {
        return appUserRole;
    }

    public void setAppUserRole(AppUserRole appUserRole) {
        this.appUserRole = appUserRole;
    }    
}
