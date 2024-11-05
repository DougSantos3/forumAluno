package br.com.vraptor.forum.fixtures

import br.com.vraptor.forum.dto.TopicoView
import java.time.LocalDate
import java.time.LocalDateTime


object TopicoViewTest {
    fun build() = TopicoView(
        id = 1,
        titulo = "Kotlin Basico",
        mensagem = "Aprendendo Kotlin basico",
        status = StatusTopico.NAO_RESPONDIDO,
        dataCriacao = LocalDateTime.now(),
        dataAlteracao = LocalDate.now()
    )
}