package br.com.amorimtech.comunidade.data.model

import java.util.UUID

// Autor de um post/tópico/comentário — dados públicos mínimos,
// nunca inclui e-mail nem dados documentais (CNPJ, CREA completo)
data class AutorPublico(
    val id: String,
    val nomeCompleto: String,
    val tituloProfissional: String?,
    val avatarUrl: String?
)

data class Post(
    val id: String,
    val autor: AutorPublico,
    val tipo: String,          // ex: "texto", "enquete", "compartilhamento"
    val tag: String?,
    val conteudo: String,
    val fotosUrls: List<String> = emptyList(),
    val criadoEm: String,      // ISO 8601, formatar na exibição
    val curtidas: Int = 0,
    val curtidoPeloUsuario: Boolean = false,
    val comentarios: List<Comentario> = emptyList()
)

data class Comentario(
    val id: String,
    val autor: AutorPublico,
    val texto: String,
    val criadoEm: String
)

// Tópico e respostas são entidades separadas no banco real —
// mantenha essa separação aqui também, não embuta respostas dentro do tópico
// por padrão; carregue as respostas só quando o tópico for aberto.
data class TopicoForum(
    val id: String,
    val autor: AutorPublico,
    val titulo: String,
    val categoria: String,     // ex: "Patologia", "Perícias", "NBR 15575/16747", "Negócios"
    val conteudo: String,
    val criadoEm: String,
    val totalRespostas: Int = 0
)

data class RespostaForum(
    val id: String,
    val topicoId: String,
    val autor: AutorPublico,
    val texto: String,
    val criadoEm: String
)

data class Curso(
    val id: String,
    val titulo: String,
    val descricao: String,
    val categoria: String?,
    val instrutorNome: String?,
    val instrutorQualificacao: String?,
    val imagemCapaUrl: String?,
    val cargaHorariaCertificado: String?,
    val modulos: List<ModuloCurso> = emptyList()
)

data class ModuloCurso(
    val id: String,
    val cursoId: String,
    val titulo: String,
    val descricao: String?,
    val duracaoEstimada: String?,
    val ordem: Int,
    val exigeAvaliacao: Boolean,
    val travaProximoModulo: Boolean,
    // Progresso do usuário atual neste módulo — vem de uma tabela
    // separada no banco real (cursos_progresso_modulo), mantenha
    // como campos opcionais aqui, preenchidos só quando há progresso
    val videoConcluido: Boolean = false,
    val avaliacaoAprovada: Boolean = false
)

// Material do acervo. Note: no banco real este modelo NUNCA é lido de
// uma tabela bruta — sempre de uma view que já filtra o campo
// urlArquivo para null quando o usuário não tem acesso. Mantenha o
// campo urlArquivo como nullable por esse motivo, mesmo em mock.
data class MaterialItem(
    val id: String,
    val titulo: String,
    val categoria: String,     // "Planilhas", "Modelos de Laudo", "Checklists", "E-books", "Vídeos", "Skills Claude", "Outros"
    val descricao: String?,
    val formato: String?,
    val tamanho: String?,
    val urlArquivo: String?,   // null = sem acesso ou não carregado
    val pago: Boolean,
    val valor: Double?,
    val temAcesso: Boolean,
    val plataformaVideo: String? = null // "vimeo" ou "youtube", só quando categoria = Vídeos
)

data class PerfilUsuario(
    val id: String,
    val nomeCompleto: String,
    val tituloProfissional: String?,
    val creaCau: String?,
    val avatarUrl: String?,
    val nivelAtual: String?,   // rótulo de gamificação/perfil de acesso
    val pontosTotais: Int = 0
)
