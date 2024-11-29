package br.com.vraptor.forum.service

import br.com.vraptor.forum.fixtures.TopicoTest;
import br.com.vraptor.forum.fixtures.TopicoViewTest;
import br.com.vraptor.forum.exception.NotFoundException;
import br.com.vraptor.forum.mapper.TopicoFormMapper;
import br.com.vraptor.forum.mapper.TopicoViewMapper;
import br.com.vraptor.forum.repository.TopicoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import io.mockk.verify
import java.util.Optional
import org.junit.jupiter.api.assertThrows


class TopicoServiceTest {

   private val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    val topicoRepository: TopicoRepository = mockk {
        every { findByCursoNome(any(), any()) } returns topicos
        every { findAll(paginacao) } returns topicos
    }

    val topicoViewMapper: TopicoViewMapper = mockk {
        every { map(any()) } returns TopicoViewTest.build()
    }

    val topicoFormMapper: TopicoFormMapper = mockk()

    val topicoService = TopicoService(
        topicoRepository, topicoViewMapper, topicoFormMapper
    )

    @Test
    fun `deve listar topicos a partir do nome do curso`() {
        topicoService.listar("Kotlin avançado", paginacao)

        verify(exactly = 1) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 0) { topicoRepository.findAll(paginacao) }
    }

    @Test
    fun `deve listar todos os topicos quando o nome do curso for nulo`() {
        topicoService.listar(null, paginacao)

        verify(exactly = 0) { topicoRepository.findByCursoNome(any(), any()) }
        verify(exactly = 1) { topicoViewMapper.map(any()) }
        verify(exactly = 1) { topicoRepository.findAll(paginacao) }
    }

    @Test
    fun `deve listar not found exception quando topicos nao for achado`() {
        every { topicoRepository.findById(any()) } returns Optional.empty()

        val exception = assertThrows< NotFoundException> {
            topicoService.buscarPorId(1)
        }

        assertThat(exception.message).isEqualTo("Topico nao encontrado!")
    }
}