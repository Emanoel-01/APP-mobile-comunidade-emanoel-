package br.com.amorimtech.comunidade

import br.com.amorimtech.comunidade.data.mock.DadosMock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testMockDataIntegrity() {
        // Assert mock requirements
        assertTrue("Deve ter pelo menos 5 posts", DadosMock.posts.size >= 5)
        assertTrue("Deve ter pelo menos 5 tópicos de fórum", DadosMock.topicosForum.size >= 5)
        assertTrue("Deve ter pelo menos 3 cursos", DadosMock.cursos.size >= 3)
        assertTrue("Deve ter pelo menos 8 materiais", DadosMock.materiais.size >= 8)
        assertNotNull("Deve ter usuário atual definido", DadosMock.usuarioAtual)

        DadosMock.topicosForum.forEach { topico ->
            val respostas = DadosMock.respostasForum[topico.id]
            assertNotNull("Cada tópico deve ter lista de respostas", respostas)
            assertTrue("Tópico ${topico.id} deve ter respostas", (respostas?.size ?: 0) >= 2)
        }

        DadosMock.cursos.forEach { curso ->
            assertTrue("Curso ${curso.id} deve ter pelo menos 3 módulos", curso.modulos.size >= 3)
        }
    }
}
