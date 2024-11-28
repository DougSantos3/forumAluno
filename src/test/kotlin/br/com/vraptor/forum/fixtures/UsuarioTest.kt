package br.com.vraptor.forum.fixtures

object UsuarioTest {
    fun build() = Usuario(
        id = 1,
        nome = "Brunelli",
        email = "brunelli@email.com",
        password = "123456",
        role = mutableListOf(Role(id = 1, nome = "READ_WRITE"))
    )
}