package br.com.vraptor.forum.mapper

interface Mapper<T, U> {
    fun map(t: T): U
}
