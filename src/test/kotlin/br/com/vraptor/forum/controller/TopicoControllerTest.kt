package br.com.vraptor.forum.controller

import br.com.vraptor.forum.config.JWTUtil
import br.com.vraptor.forum.fixtures.Role
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    private lateinit var mockMvc: MockMvc

    private var token: String? = null

    /*Vamos fazer uma requisição para o recurso de tópicos, que é o "/topicos" na nossa aplicação, e realizaremos
   vários testes para "/topicos". Faremos um teste com autenticação, outro sem autenticação, e faremos a requisição
   para "/topicos" passando também o ID, para verificar se ele chamará corretamente com parâmetros ou não.

   Portanto, o ponto em comum que teremos é a string "/topicos" nos nossos testes. Para evitar escrever a string
   "/topicos" toda vez que formos fazer um teste, uma boa prática é criar um companion object no arquivo
   TopicoControllerTest.kt, onde vamos declarar uma private const val.

   Essa constante privada se chamará RECURSO, e feito isso, passaremos para ela a string que teremos em comum em todos
   os testes, ou seja, /topicos/.
    Pelos nossos padrões de segurança, "/topicos" é um recurso protegido. Ele precisa de um token válido para retornar
    as informações.*/
    companion object {
        private const val RECURSO = "/topico"
        private const val RECURSO_ID = RECURSO.plus("%s")
    }


    /*Qual é o contexto da nossa aplicação? Como é uma requisição real, precisamos fazer o trabalho com o contexto
    Spring e com servlet. O contexto da nossa aplicação é um WebApplicationContext, então precisamos passar uma
    instância do WebApplicationContext para webAppContextSetup().
    Agora, entre os parênteses de .apply<>(), precisamos configurar o Spring Security, pois quando formos fazer as
    requisições no mockMvc, passaremos recursos de autenticação para conseguirmos fazer uma requisição para o endpoint
    de tópicos.

    Primeiramente, chamaremos SecurityMockMvcConfigurers e faremos sua importação. Logo após sua chamada, vamos
    adicionar .springSecurity(), e após .apply<>(), colocaremos build().*/
    @BeforeEach
    fun setup() {
        token = gerarToken()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(
                SecurityMockMvcConfigurers.springSecurity()
            ).build()
    }




    /*O primeiro teste que vamos fazer é validar se, quando fizermos uma requisição para "/topicos" sem um token, será
    retornado um erro 400. Ele deve retornar isso, informando que não estamos autenticados e que não podemos acessar
    esse recurso sem um token.
    Esse é um teste relativamente simples, porque só precisamos fazer uma chamada para o endpoint e verificar o código
    de status do retorno. Como fazemos isso com o teste de API?
    Criamos uma função, como todos os outros testes, e damos um nome expressivo para ela, nesse caso, deve retornar
    codigo 400 quando chamar topicos sem token(), na linha 32 após a função setup().
    Uma vez criada a primeira função, que será um teste, precisamos simular e fazer com que o mockMvc faça a requisição GET para a nossa aplicação por recurso "/topicos".

    Para isso, chamamos mockMvc no escopo da função, seguido do metodo get(). É entre os parênteses de get() que
    precisamos informar qual recurso da aplicação será chamado.
    Como vamos utilizar várias vezes .get(), criamos uma constante. Então, essa é a primeira boa prática para o nosso
    teste. Após passar RECURSO para get(), o que acontece?

    Quando chamamos "/topicos" sem autenticação, devemos esperar um código 400. Podemos fazer isso com o mockMvc
    chamando o andExpect{}, que recebe o status declarado como is4xxClientError().

    Ao chamar esse recurso, como está sem autenticação, esperamos o status 400. Se realmente for retornado o status
    400, que é o comportamento correto, o @Test deve ficar verde.*/
    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem token`() {
        mockMvc.get(RECURSO).andExpect { status { is4xxClientError() } }
    }

    /*Quando fazemos a requisição para a nossa API, ela passa pelas camadas da aplicação até chegar no repositório.
    Esse teste vai utilizar as informações do nosso repositório. Podemos também criar um repositório falso com o MockK
   para configurar no nosso teste de API.
   Da mesma forma, poderíamos criar um container com o teste de container, assim como podemos bater no banco de dados
   da aplicação, que é um Docker criado no começo do curso, o qual tem um banco de dados MySQL em que executamos a
   nossa aplicação. Quando está no ar, usamos ele localmente.

   Existem essas três abordagens. Como já usamos o MockK e fizemos um teste de integração com o teste de container,
   dessa vez, vamos bater no banco de dados. Caso contrário, será necessário criar migrações para usuário, inserindo
   usuários para validar ao fazer uma requisição autenticada no teste de API.

   Precisaremos fazer uma série de outras configurações que já estão prontas no nosso banco de dados, onde estão as
   informações registradas.

   Dito isso, faremos o seguinte: vamos chamar o @Test que adicionamos, ele vai executar, e o mysql-container que
   criamos no começo do curso estará ativo no Docker.

   No vídeo do teste de container, ele estava down, ou seja, estava inativo, porque de fato ele subiu um container de
   teste para podermos utilizar.

   Como retorno, a API sem autenticação está funcionando 100%. Ela retorna um 400, conforme esperado.*/

    @Test
    fun 'deve retornar codigo 200 quando chamar topicos com token'() {
        mockMvc.get(RECURSO) { this: ModHttpServletRequestDst
            headers token

                ?.let { this.setBearerAuth(it) } }
        }.andExpect { status ( 1s2xxSuccessful() } }
    }

    /*Agora vamos criar uma função chamada 'deve retornar codigo 200 quando chamar tópico por id com token'. Vamos
    fazer a mesma coisa aqui. Vamos chamar mockMvc.get, mas agora vamos chamar o RECURSO_id e vamos dar .format
    passando o argumento 1, que substituirá esse "%s" e concatenará logo após a barra.
    Aqui também será a mesma coisa. Vamos abrir chaves, colocar os cabeçalhos, fazer a mesma coisa com o token, temos
    que fazer uma validação. Se ele não for nulo, definimos ele aqui no setBearerAuth, passando it, e verificamos se o
    status será um is2xxSuccessful. Vamos anotar com @Test e ver se o nosso novo teste será executado com sucesso.
    Com esses três testes, conseguimos validar a chamada para tópicos sem autenticação e vimos que ele realmente
    retorna 400. Passando um BearerToken, ele retorna 200. E quando passamos um parâmetro e existe o tópico com o ID
    que formamos no banco de dados, ele retorna 200 também. Desta forma, fizemos uma cobertura de testes bastante
    abrangente para a nossa API.*/
    @Test
    fun 'deve retornar codigo 200 quando chamar tópico por id com token'() {
        mockMvc.get(RECURSO_id.format("1")) {
            headers { token?.let { this.setBearerAuth(it) } }
        }.andExpect { status ( 1s2xxSuccessful() } }
    }


    private fun gerarToken(): String? {
            val authorities = mutableListOf(Role(1, "LEITURA_ESCRITA"))
            return jwtUtil.generateToken("ana.email.com", authorities)
        }



}