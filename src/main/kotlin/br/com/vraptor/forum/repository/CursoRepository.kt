package br.com.vraptor.forum.repository

import br.com.vraptor.forum.entity.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
}