package br.com.vraptor.forum.config

import br.com.vraptor.forum.usecases.service.UsuarioService
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil(
    private val expiration: Long = 60000,
    private val usuarioService: UsuarioService
) {
    @Value("\${jwt.secret}")
    private var secret: String = "secret"
    private val key = Algorithm.HMAC256(secret.toByteArray())


    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String? {
        val authoritiesList = authorities.toString()
        return JWT.create()
            .withSubject(username)
            .withClaim("role", authoritiesList)
            .withExpiresAt(Date(System.currentTimeMillis() + expiration))
            .sign(key)

    }

    fun isValid(jwt: String?): Boolean {
        return try {
            JWT.require(key).build().verify(jwt)
            true
        } catch (e: Exception) {
            false
        }
    }

   fun getAuthentication(jwt: String?) : Authentication {
        val username = JWT.require(key)
            .build()
            .verify(jwt)
            .subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}