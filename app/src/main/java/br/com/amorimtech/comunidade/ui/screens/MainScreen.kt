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
import br.com.amorimtech.comunidade.data.model.Curso
import br.com.amorimtech.comunidade.data.model.MaterialItem
import br.com.amorimtech.comunidade.data.model.ModuloCurso
import br.com.amorimtech.comunidade.data.model.Post
import br.com.amorimtech.comunidade.data.model.RespostaForum
import br.com.amorimtech.comunidade.data.model.TopicoForum
import br.com.amorimtech.comunidade.ui.components.AppBottomNavigationBar
import br.com.amorimtech.comunidade.ui.components.AppTopBar
import br.com.amorimtech.comunidade.ui.components.NavigationTab
import br.com.amorimtech.comunidade.ui.screens.agents.AgentsScreen
import br.com.amorimtech.comunidade.ui.screens.courses.CourseDetailScreen
import br.com.amorimtech.comunidade.ui.screens.courses.CoursesScreen
import br.com.amorimtech.comunidade.ui.screens.eventos.EventosScreen
import br.com.amorimtech.comunidade.ui.screens.feed.FeedScreen
import br.com.amorimtech.comunidade.ui.screens.feed.PostDetailScreen
import br.com.amorimtech.comunidade.ui.screens.forum.ForumScreen
import br.com.amorimtech.comunidade.ui.screens.forum.ForumTopicDetailScreen
import br.com.amorimtech.comunidade.ui.screens.login.LoginScreen
import br.com.amorimtech.comunidade.ui.screens.materials.MaterialsScreen
import br.com.amorimtech.comunidade.ui.screens.more.MoreScreen
import br.com.amorimtech.comunidade.ui.screens.profile.ProfileScreen
import br.com.amorimtech.comunidade.ui.screens.vagas.VagasScreen

sealed class ScreenDestination {
    object TabRoot : ScreenDestination()
    data class PostDetail(val postId: String) : ScreenDestination()
    data class ForumTopicDetail(val topicId: String) : ScreenDestination()
    data class CourseDetail(val courseId: String) : ScreenDestination()
    object ForumRoot : ScreenDestination()
    object MaterialsRoot : ScreenDestination()
    object VagasRoot : ScreenDestination()
    object EventosRoot : ScreenDestination()
}

@Composable
fun MainScreen() {
    var isLoggedIn by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        LoginScreen(
            onLoginSuccess = {
                isLoggedIn = true
            }
        )
        return
    }

    // Default tab when entering the app is CURSOS
    var currentTab by remember { mutableStateOf(NavigationTab.CURSOS) }
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
    BackHandler(enabled = currentDestination !is ScreenDestination.TabRoot || currentTab != NavigationTab.CURSOS) {
        if (currentDestination !is ScreenDestination.TabRoot) {
            currentDestination = ScreenDestination.TabRoot
        } else if (currentTab != NavigationTab.CURSOS) {
            currentTab = NavigationTab.CURSOS
        }
    }

    Scaffold(
        topBar = {
            when (currentDestination) {
                is ScreenDestination.ForumRoot -> {
                    AppTopBar(
                        title = "Fórum Técnico",
                        subtitle = "Discussões e Casos Práticos",
                        onBackClick = { currentDestination = ScreenDestination.TabRoot }
                    )
                }
                is ScreenDestination.MaterialsRoot -> {
                    AppTopBar(
                        title = "Acervo de Materiais",
                        subtitle = "Planilhas, Laudos e Checklists",
                        onBackClick = { currentDestination = ScreenDestination.TabRoot }
                    )
                }
                is ScreenDestination.VagasRoot -> {
                    AppTopBar(
                        title = "Mural de Vagas",
                        subtitle = "Oportunidades na Engenharia",
                        onBackClick = { currentDestination = ScreenDestination.TabRoot }
                    )
                }
                is ScreenDestination.EventosRoot -> {
                    AppTopBar(
                        title = "Agenda de Eventos",
                        subtitle = "Masterclasses e Webinars",
                        onBackClick = { currentDestination = ScreenDestination.TabRoot }
                    )
                }
                ScreenDestination.TabRoot -> {
                    when (currentTab) {
                        NavigationTab.FEED -> AppTopBar(
                            title = "Comunidade Business 4.0",
                            subtitle = "Engenharia & Perícias AmorimTech",
                            onSearchClick = {},
                            onNotificationsClick = {}
                        )
                        NavigationTab.CURSOS -> AppTopBar(
                            title = "Cursos Executivos",
                            subtitle = "Capacitação Contínua",
                            onNotificationsClick = {}
                        )
                        NavigationTab.AGENTES -> AppTopBar(
                            title = "Agentes de Engenharia",
                            subtitle = "Automação e Diagnóstico com IA",
                            onNotificationsClick = {}
                        )
                        NavigationTab.PERFIL -> AppTopBar(
                            title = "Meu Perfil",
                            subtitle = "Credenciais e Conquistas"
                        )
                        NavigationTab.MAIS -> AppTopBar(
                            title = "Central de Recursos",
                            subtitle = "Fórum, Vagas, Downloads e Eventos"
                        )
                    }
                }
                else -> {
                    // Detalhes possuem suas próprias barras
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

                    ScreenDestination.ForumRoot -> {
                        ForumScreen(
                            topicos = topicos,
                            onOpenTopicDetail = { topico ->
                                currentDestination = ScreenDestination.ForumTopicDetail(topico.id)
                            },
                            onAddNewTopic = { newTopic ->
                                topicos.add(0, newTopic)
                                respostasMap[newTopic.id] = mutableListOf()
                            }
                        )
                    }

                    ScreenDestination.MaterialsRoot -> {
                        MaterialsScreen(
                            materiais = materiais,
                            onUnlockAccess = { matId ->
                                val idx = materiais.indexOfFirst { it.id == matId }
                                if (idx != -1) {
                                    materiais[idx] = materiais[idx].copy(temAcesso = true)
                                }
                            }
                        )
                    }

                    ScreenDestination.VagasRoot -> {
                        VagasScreen()
                    }

                    ScreenDestination.EventosRoot -> {
                        EventosScreen()
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

                            NavigationTab.CURSOS -> CoursesScreen(
                                cursos = cursos,
                                onOpenCourseDetail = { curso ->
                                    currentDestination = ScreenDestination.CourseDetail(curso.id)
                                }
                            )

                            NavigationTab.AGENTES -> AgentsScreen()

                            NavigationTab.PERFIL -> ProfileScreen(
                                usuario = DadosMock.usuarioAtual
                            )

                            NavigationTab.MAIS -> MoreScreen(
                                onNavigateToFeed = {
                                    currentTab = NavigationTab.FEED
                                    currentDestination = ScreenDestination.TabRoot
                                },
                                onNavigateToForum = {
                                    currentDestination = ScreenDestination.ForumRoot
                                },
                                onNavigateToMateriais = {
                                    currentDestination = ScreenDestination.MaterialsRoot
                                },
                                onNavigateToVagas = {
                                    currentDestination = ScreenDestination.VagasRoot
                                },
                                onNavigateToEventos = {
                                    currentDestination = ScreenDestination.EventosRoot
                                },
                                onLogout = {
                                    isLoggedIn = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
