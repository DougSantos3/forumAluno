package br.com.vraptor.forum.usecases.service

import br.com.vraptor.forum.entity.Curso
import br.com.vraptor.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(private val repository: CursoRepository) {

    fun buscarPorId(id: Long): Curso {
        return repository.getReferenceById(id)
    }
}
