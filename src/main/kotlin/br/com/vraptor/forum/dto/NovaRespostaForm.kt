package br.com.vraptor.forum.dto

import jakarta.validation.constraints.NotEmpty

data class NovaRespostaForm (
    @field:NotEmpty
    val mensagem: String,
    val idAutor: Long
)
