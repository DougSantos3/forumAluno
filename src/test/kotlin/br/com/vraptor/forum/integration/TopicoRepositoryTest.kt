package br.com.vraptor.forum.integration

import br.com.vraptor.forum.fixtures.TopicoTest
import br.com.vraptor.forum.dto.TopicoPorCategoriaDto
import br.com.vraptor.forum.repository.TopicoRepository
import net.bytebuddy.utility.dispatcher.JavaDispatcher.Container
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.assertj.core.api.Assertions.assertThat

@DataJpaTest/*conseguimos ver, no momento em que executamos nossos testes, qual query SQL está sendo utilizada para realizar aquele teste de integração.*/
@Testcontainers/* é uma anotação para o nosso teste que o JUnit 5 possui, habilitando, de fato, o teste de container.*/
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)/*estamos dizendo para o framework Spring Test, que não queremos substituir nosso banco de dados por esse banco de dados que estamos utilizando no teste.*/
class TopicoRepositoryTest {

    /*Aqui, podemos utilizar o Autowired, que é uma anotação do Spring para a injeção de dependência, e vamos injetar o nosso repository.*/
    @Autowired
    private lateinit var topicoRepository: TopicoRepository

    private val topico = TopicoTest.build()

    /*Em seguida, vamos criar um companion object, que é como se fosse o static do Java, para fazermos algumas configurações do nosso teste.
    Uma recomendação de alguns artigos sobre o teste de containers é não passarmos aqui o usuário root, porque o MySQL
    já possui um usuário root e tudo mais, e como é para teste aqui, não seria muito interessante usá-lo. Criamos, o trecho de código que vai
    criar uma instância do nosso container do MySQL, que vai criar, na verdade, o nosso container MySQL.*/
    companion object {
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.3.0").apply {
            withDatabaseName("testedb")
            withUsername("joao")
            withPassword("123456")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
        }
    }

    @Test
    fun `deve gerar um relatorio`(){
        topicoRepository.save(topico)
        /*como é lateinit agora que será iniciado*/
        val relatorio = topicoRepository.relatorio()

        assertThat(relatorio).isNotNull
        assertThat(relatorio.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve listar topico pelo nome do curso`(){
        topicoRepository.save(topico)
        val topico = topicoRepository.findByCursoNome(topico.curso.nome, PageRequest.of(0, 5))
        assertThat(topico).isNotNull
    }
}