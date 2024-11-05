package br.com.vraptor.forum.repository

import br.com.vraptor.forum.fixtures.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository: JpaRepository<Usuario, Long> {

    fun findByEmail(username: String?): Usuario?
}
