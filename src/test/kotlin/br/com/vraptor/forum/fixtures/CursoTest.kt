package br.com.vraptor.forum.fixtures

import br.com.vraptor.forum.entity.Curso

object CursoTest {
    fun build() = Curso(
        id = 1,
        nome = "Aprendendo Kotlin",
        categoria = "Programação"
    )
}