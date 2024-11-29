package br.com.vraptor.forum.fixtures

import br.com.vraptor.forum.entity.Role
import br.com.vraptor.forum.entity.Usuario

object UsuarioTest {
    fun build() = Usuario(
        id = 1,
        nome = "Brunelli",
        email = "brunelli@email.com",
        password = "123456",
        role = mutableListOf(Role(id = 1, nome = "READ_WRITE"))
    )
}