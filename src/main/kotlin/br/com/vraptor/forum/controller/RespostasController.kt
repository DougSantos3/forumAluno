//package br.com.vraptor.forum.controller
//
//import br.com.vraptor.forum.dto.NovaRespostaForm
//import br.com.vraptor.forum.model.Resposta
//import br.com.vraptor.forum.service.RespostaService
//import jakarta.validation.Valid
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping("/topicos/{id}/respostas")
//class RespostasController(private val service: RespostaService) {
//
//    @GetMapping
//    fun listar(@PathVariable id: Long) : List<Resposta> {
//        return service.listar(id)
//    }
//
//    @PostMapping
//    fun cadastrar(@PathVariable id: Long, @RequestBody @Valid dto: NovaRespostaForm) {
//        service.cadastrar(dto, id)
//    }
//}
