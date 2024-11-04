package br.com.vraptor.forum.unit

import br.com.vraptor.forum.exception.NotFoundException
import br.com.vraptor.forum.model.Usuario
import br.com.vraptor.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val notFoundMessage: String = "Usuario não encontrado"
) : UserDetailsService {

    fun buscarPorId(id: Long): Usuario {
        return repository.findById(id).orElseThrow{ NotFoundException(message = notFoundMessage)  }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = repository.findByEmail(username) ?: throw RuntimeException()
        return UserDetail(usuario)
    }
}
