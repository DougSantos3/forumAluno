package br.com.vraptor.forum.integration

import br.com.vraptor.forum.config.DatabaseContainerConfig
import br.com.vraptor.forum.fixtures.TopicoTest
import br.com.vraptor.forum.dto.TopicoPorCategoriaDto
import br.com.vraptor.forum.repository.TopicoRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.data.domain.PageRequest
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
class TopicoRepositoryTest : DatabaseContainerConfig() {

    @Autowired
    private lateinit var repository: TopicoRepository
    private val topic = TopicoTest.build()

    @Test
    fun `deve gerar um relatorio`(){
        repository.save(topic)
        val report = repository.relatorio()

        assertThat(report).isNotNull
        assertThat(report.first()).isExactlyInstanceOf(TopicoPorCategoriaDto::class.java)
    }

    @Test
    fun `deve listar topico pelo nome do curso`(){
        repository.save(topic)
        val topico = repository.findByCursoNome(topic.curso.nome, PageRequest.of(0, 5))
        assertThat(topico).isNotNull
    }
}