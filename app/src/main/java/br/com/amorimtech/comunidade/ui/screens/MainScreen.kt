package br.com.amorimtech.comunidade.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.amorimtech.comunidade.data.mock.DadosMock
import br.com.amorimtech.comunidade.data.model.Comentario
import br.com.amorimtech.comunidade.data.model.Curso
import br.com.amorimtech.comunidade.data.model.MaterialItem
import br.com.amorimtech.comunidade.data.model.ModuloCurso
import br.com.amorimtech.comunidade.data.model.Post
import br.com.amorimtech.comunidade.data.model.RespostaForum
import br.com.amorimtech.comunidade.data.model.TopicoForum
import br.com.amorimtech.comunidade.ui.components.AppBottomNavigationBar
import br.com.amorimtech.comunidade.ui.components.AppTopBar
import br.com.amorimtech.comunidade.ui.components.NavigationTab
import br.com.amorimtech.comunidade.ui.screens.courses.CourseDetailScreen
import br.com.amorimtech.comunidade.ui.screens.courses.CoursesScreen
import br.com.amorimtech.comunidade.ui.screens.feed.FeedScreen
import br.com.amorimtech.comunidade.ui.screens.feed.PostDetailScreen
import br.com.amorimtech.comunidade.ui.screens.forum.ForumScreen
import br.com.amorimtech.comunidade.ui.screens.forum.ForumTopicDetailScreen
import br.com.amorimtech.comunidade.ui.screens.materials.MaterialsScreen
import br.com.amorimtech.comunidade.ui.screens.profile.ProfileScreen

sealed class ScreenDestination {
    object TabRoot : ScreenDestination()
    data class PostDetail(val postId: String) : ScreenDestination()
    data class ForumTopicDetail(val topicId: String) : ScreenDestination()
    data class CourseDetail(val courseId: String) : ScreenDestination()
}

@Composable
fun MainScreen() {
    var currentTab by remember { mutableStateOf(NavigationTab.FEED) }
    var currentDestination by remember { mutableStateOf<ScreenDestination>(ScreenDestination.TabRoot) }

    // In-memory reactive state initialized from DadosMock
    val posts = remember { mutableStateListOf(*DadosMock.posts.toTypedArray()) }
    val topicos = remember { mutableStateListOf(*DadosMock.topicosForum.toTypedArray()) }
    val respostasMap = remember {
        mutableStateMapOf<String, MutableList<RespostaForum>>().apply {
            DadosMock.respostasForum.forEach { (key, value) ->
                put(key, value.toMutableList())
            }
        }
    }
    val cursos = remember { mutableStateListOf(*DadosMock.cursos.toTypedArray()) }
    val materiais = remember { mutableStateListOf(*DadosMock.materiais.toTypedArray()) }

    // Intercept back presses
    BackHandler(enabled = currentDestination !is ScreenDestination.TabRoot || currentTab != NavigationTab.FEED) {
        if (currentDestination !is ScreenDestination.TabRoot) {
            currentDestination = ScreenDestination.TabRoot
        } else if (currentTab != NavigationTab.FEED) {
            currentTab = NavigationTab.FEED
        }
    }

    Scaffold(
        topBar = {
            if (currentDestination is ScreenDestination.TabRoot) {
                when (currentTab) {
                    NavigationTab.FEED -> AppTopBar(
                        title = "Comunidade Business 4.0",
                        subtitle = "Engenharia & Perícias AmorimTech",
                        onSearchClick = {},
                        onNotificationsClick = {}
                    )
                    NavigationTab.FORUM -> AppTopBar(
                        title = "Fórum Técnico",
                        subtitle = "Discussões e Casos Práticos",
                        onSearchClick = {}
                    )
                    NavigationTab.CURSOS -> AppTopBar(
                        title = "Cursos Executivos",
                        subtitle = "Capacitação Contínua",
                        onNotificationsClick = {}
                    )
                    NavigationTab.MATERIAIS -> AppTopBar(
                        title = "Acervo de Materiais",
                        subtitle = "Planilhas, Laudos e Normas"
                    )
                    NavigationTab.PERFIL -> AppTopBar(
                        title = "Meu Perfil",
                        subtitle = "Credenciais e Conquistas"
                    )
                }
            }
        },
        bottomBar = {
            if (currentDestination is ScreenDestination.TabRoot) {
                AppBottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        currentDestination = ScreenDestination.TabRoot
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "ScreenTransition"
            ) { dest ->
                when (dest) {
                    is ScreenDestination.PostDetail -> {
                        val post = posts.find { it.id == dest.postId }
                        if (post != null) {
                            PostDetailScreen(
                                post = post,
                                onBackClick = { currentDestination = ScreenDestination.TabRoot },
                                onToggleLike = { pId ->
                                    val idx = posts.indexOfFirst { it.id == pId }
                                    if (idx != -1) {
                                        val p = posts[idx]
                                        val nowLiked = !p.curtidoPeloUsuario
                                        val newCurtidas = if (nowLiked) p.curtidas + 1 else p.curtidas - 1
                                        posts[idx] = p.copy(
                                            curtidoPeloUsuario = nowLiked,
                                            curtidas = maxOf(0, newCurtidas)
                                        )
                                    }
                                },
                                onAddComment = { pId, newComment ->
                                    val idx = posts.indexOfFirst { it.id == pId }
                                    if (idx != -1) {
                                        val p = posts[idx]
                                        posts[idx] = p.copy(
                                            comentarios = p.comentarios + newComment
                                        )
                                    }
                                }
                            )
                        } else {
                            currentDestination = ScreenDestination.TabRoot
                        }
                    }

                    is ScreenDestination.ForumTopicDetail -> {
                        val topico = topicos.find { it.id == dest.topicId }
                        if (topico != null) {
                            val respostas = respostasMap[dest.topicId] ?: emptyList()
                            ForumTopicDetailScreen(
                                topico = topico,
                                respostas = respostas,
                                onBackClick = { currentDestination = ScreenDestination.TabRoot },
                                onAddResposta = { tId, novaResposta ->
                                    val list = respostasMap.getOrPut(tId) { mutableListOf() }
                                    list.add(novaResposta)
                                    respostasMap[tId] = list.toMutableList()

                                    // Update total answers in topic
                                    val topicoIdx = topicos.indexOfFirst { it.id == tId }
                                    if (topicoIdx != -1) {
                                        val t = topicos[topicoIdx]
                                        topicos[topicoIdx] = t.copy(totalRespostas = t.totalRespostas + 1)
                                    }
                                }
                            )
                        } else {
                            currentDestination = ScreenDestination.TabRoot
                        }
                    }

                    is ScreenDestination.CourseDetail -> {
                        val curso = cursos.find { it.id == dest.courseId }
                        if (curso != null) {
                            CourseDetailScreen(
                                curso = curso,
                                onBackClick = { currentDestination = ScreenDestination.TabRoot },
                                onToggleModuleCompletion = { cId, mId ->
                                    val cIdx = cursos.indexOfFirst { it.id == cId }
                                    if (cIdx != -1) {
                                        val c = cursos[cIdx]
                                        val updatedModules = c.modulos.map { mod ->
                                            if (mod.id == mId) {
                                                mod.copy(
                                                    videoConcluido = !mod.videoConcluido,
                                                    avaliacaoAprovada = if (!mod.videoConcluido) true else mod.avaliacaoAprovada
                                                )
                                            } else mod
                                        }
                                        cursos[cIdx] = c.copy(modulos = updatedModules)
                                    }
                                }
                            )
                        } else {
                            currentDestination = ScreenDestination.TabRoot
                        }
                    }

                    ScreenDestination.TabRoot -> {
                        when (currentTab) {
                            NavigationTab.FEED -> FeedScreen(
                                postsList = posts,
                                onToggleLike = { pId ->
                                    val idx = posts.indexOfFirst { it.id == pId }
                                    if (idx != -1) {
                                        val p = posts[idx]
                                        val nowLiked = !p.curtidoPeloUsuario
                                        val newCurtidas = if (nowLiked) p.curtidas + 1 else p.curtidas - 1
                                        posts[idx] = p.copy(
                                            curtidoPeloUsuario = nowLiked,
                                            curtidas = maxOf(0, newCurtidas)
                                        )
                                    }
                                },
                                onOpenPostDetail = { post ->
                                    currentDestination = ScreenDestination.PostDetail(post.id)
                                },
                                onAddNewPost = { newPost ->
                                    posts.add(0, newPost)
                                }
                            )

                            NavigationTab.FORUM -> ForumScreen(
                                topicos = topicos,
                                onOpenTopicDetail = { topico ->
                                    currentDestination = ScreenDestination.ForumTopicDetail(topico.id)
                                },
                                onAddNewTopic = { newTopic ->
                                    topicos.add(0, newTopic)
                                    respostasMap[newTopic.id] = mutableListOf()
                                }
                            )

                            NavigationTab.CURSOS -> CoursesScreen(
                                cursos = cursos,
                                onOpenCourseDetail = { curso ->
                                    currentDestination = ScreenDestination.CourseDetail(curso.id)
                                }
                            )

                            NavigationTab.MATERIAIS -> MaterialsScreen(
                                materiais = materiais,
                                onUnlockAccess = { matId ->
                                    val idx = materiais.indexOfFirst { it.id == matId }
                                    if (idx != -1) {
                                        materiais[idx] = materiais[idx].copy(temAcesso = true)
                                    }
                                }
                            )

                            NavigationTab.PERFIL -> ProfileScreen(
                                usuario = DadosMock.usuarioAtual
                            )
                        }
                    }
                }
            }
        }
    }
}
