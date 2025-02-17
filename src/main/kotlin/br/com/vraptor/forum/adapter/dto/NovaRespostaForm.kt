package br.com.vraptor.forum.adapter.dto

import jakarta.validation.constraints.NotEmpty

data class NovaRespostaForm (
    @field:NotEmpty
    val mensagem: String,
    val idAutor: Long
)
