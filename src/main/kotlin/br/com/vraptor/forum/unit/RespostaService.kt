package br.com.vraptor.forum.unit

//import br.com.vraptor.forum.mapper.RespostaFormMapper
import br.com.vraptor.forum.model.Curso
import br.com.vraptor.forum.model.Resposta
import br.com.vraptor.forum.model.Topico
import br.com.vraptor.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

/*Preciso modificar para buscar dados ou inserir na base de dados*/
@Service
class RespostaService(
    private var respostas : List<Resposta>,
    //private var respostaFormMapper : RespostaFormMapper
    ) {

    init {
        val curso = Curso(
            id = 1,
            nome = "Kotlin",
            categoria = "Programacao"
        )

        val autor = Usuario(
            id = 1,
            nome = "Ana da Silva",
            email = "ana@email.com",
            password = "123456"
        )
        val topico = Topico(
            id = 1,
            titulo = "Duvidas Kotlin",
            mensagem = "Variaveis no Kotlin",
            curso = curso,
            autor = autor
        )

        val resposta = Resposta(
            id = 1,
            mensagem = "Resposta 1",
            autor = autor,
            topico = topico,
            solucao = false
        )

        val resposta2 = Resposta(
            id = 2,
            mensagem = "Resposta 2",
            autor = autor,
            topico = topico,
            solucao = false
        )

        respostas = Arrays.asList(resposta, resposta2)
    }

    fun listar(idTopico: Long): List<Resposta> {
        return respostas.stream().filter{ r ->
            r.topico.id == idTopico
        }.collect(Collectors.toList())
    }

//    fun cadastrar(form: NovaRespostaForm, idTopico: Long) {
//        val resposta = respostaFormMapper.map(form)
//        resposta.id = respostas.size.toLong() + 1
//        resposta.topico = topicoService.buscarPorId(idTopico)
//        respostas = respostas.plus(resposta)
//    }
}
