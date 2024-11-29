package br.com.vraptor.forum.controller

import br.com.vraptor.forum.config.DatabaseContainerConfig
import br.com.vraptor.forum.config.JWTUtil
import br.com.vraptor.forum.entity.Role
import br.com.vraptor.forum.fixtures.TopicoTest
import br.com.vraptor.forum.entity.Usuario
import br.com.vraptor.forum.repository.TopicoRepository
import br.com.vraptor.forum.repository.UsuarioRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest : DatabaseContainerConfig() {

    @Autowired
    private lateinit var webAppContext: WebApplicationContext

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    @Autowired
    private lateinit var repository: TopicoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    private lateinit var mockMvc: MockMvc
    private var token: String? = null

    companion object {
        private const val RESOURCE = "/topico"
        private const val RESOURCE_ID = RESOURCE.plus("%s")
    }

    private fun generateToken(): String? {
        val authorities = mutableListOf(Role(1, "ROLE_WRITE"))
        return jwtUtil.generateToken("ana.email.com", authorities)
    }

    @BeforeEach
    fun setup() {
        token = generateToken()
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
            .apply<DefaultMockMvcBuilder?>(
                SecurityMockMvcConfigurers.springSecurity()
            ).build()

        val usuario = Usuario(
            id = 1,
            nome = "Ana",
            email = "ana.email.com",
            password = "senha"
        )
        usuarioRepository.save(usuario)
        repository.save(TopicoTest.build())
    }

    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem token`() {
        mockMvc.get(RESOURCE).andExpect {
            status { is4xxClientError() }
        }
    }

    @Test
    fun `should return status code 200 when the valid token is provided`() {
        mockMvc.get(RESOURCE) {
            headers { token?.let { setBearerAuth(it) } }
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar tópico por id com token`() {
        mockMvc.get(RESOURCE_ID.format("1")) {
            headers { token?.let { setBearerAuth(it) } }
        }.andExpect { status { is2xxSuccessful() } }
    }
}