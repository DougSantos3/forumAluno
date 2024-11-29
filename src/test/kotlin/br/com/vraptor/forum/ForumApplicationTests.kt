package br.com.vraptor.forum

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [ForumApplication::class])
@ActiveProfiles("test")
class ForumApplicationTests {

	@Test
	fun contextLoads() {}
}