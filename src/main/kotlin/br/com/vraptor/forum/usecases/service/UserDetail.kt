package br.com.vraptor.forum.usecases.service

import br.com.vraptor.forum.entity.Usuario
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
        private val usuario: Usuario
) : UserDetails {
    override fun getAuthorities() = usuario.role

    override fun getPassword() = usuario.password

    override fun getUsername() = usuario.email

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
