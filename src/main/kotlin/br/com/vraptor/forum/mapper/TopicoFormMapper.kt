package br.com.vraptor.forum.mapper

import br.com.vraptor.forum.dto.NovoTopicoForm
import br.com.vraptor.forum.fixtures.Topico
import br.com.vraptor.forum.unit.CursoService
import br.com.vraptor.forum.unit.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService,
) : Mapper<NovoTopicoForm, Topico>{
    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor)
        )
    }

}
