package br.com.vraptor.forum.unit

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
import io.mockk.verify
import java.util.Optional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows


class TopicoServiceTest {

    /* Para retornar page de topicos igual é do repository usa a implementação PageImpl, passar uma lista listOf que me
    retornam uma lista de topico, e usa o meu objeto de representação de tópico(TopicoTest.build) */
    val topicos = PageImpl(listOf(TopicoTest.build()))

    val paginacao: Pageable = mockk()

    /* Quando meu teste for validar a regra de negócio que chama o TopicoRepository com o findByCursoNome(), ele vai
     retorna um topico que é um tipo PageImpl que é do tipo Page, sendo esta uma implementação específica de página, e
     nós já ajustamos os valores para isso. */
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

    /* Além disso, existe outra situação no metodo listar() de TopicoService. Quando ele chama um metodo ou outro do
     repository, ambos vão devolver um Page<Topico>. Na verdade, o findByCursoNome() devolve um Page<Topico>, enquanto
      o findAll() devolve um Page<T>, que é genérico, mas como estamos trabalhando com repository de tópicos, ele vai
       devolver um Page<Topico>. Porém, o metodo pega o Page de topicos, faz um map, chama esse topicoViewMapper, que é
      um recurso da nossa aplicação, e faz um mapa para o TopicoView. É por isso que retornamos um TopicoView ao invés
      de um Page<Topico>. Então, precisamos simular esse comportamento do metodo em nosso teste.
       Teste: Criamos o deve listar topicos a partir do nome do curso, que ocorre quando a pessoa usuária informa o
       nome do curso e deseja verificar os tópicos. Dessa forma, passamos uma string com o nome do curso e chamamos um
        metodo específico do repository. No entanto, quando o nome do curso não é informado pela pessoa usuária, ou
        seja, quando o nome do curso está como nulo, o metodo de listar() retorna todos os tópicos existentes na nossa
         plataforma. Sendo assim, ele chama outro metodo, o findAll(). Essa é a única diferença. Se o nome do curso
        existir, chama o findByCursoNome, se não, chama o findAll. O comportamento de conversão de Topico para
        TopicoView funciona da mesma forma para os dois métodos. */
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

        val atual = assertThrows< NotFoundException> {
            topicoService.buscarPorId(1)
        }

        assertEquals("Topico nao encontrado!", atual.message)
    }
}