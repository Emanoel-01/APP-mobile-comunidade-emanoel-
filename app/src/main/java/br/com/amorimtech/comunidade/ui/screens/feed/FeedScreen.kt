package br.com.amorimtech.comunidade.ui.screens.feed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.mock.DadosMock
import br.com.amorimtech.comunidade.data.model.Post
import br.com.amorimtech.comunidade.ui.components.AuthorAvatar
import br.com.amorimtech.comunidade.ui.components.CategoryChip
import br.com.amorimtech.comunidade.ui.components.TagBadge
import br.com.amorimtech.comunidade.ui.theme.SleekBorder
import br.com.amorimtech.comunidade.ui.theme.SleekBorderSubtle
import br.com.amorimtech.comunidade.ui.theme.SleekGray400
import br.com.amorimtech.comunidade.ui.theme.SleekGray500
import br.com.amorimtech.comunidade.ui.theme.SleekIconBg
import br.com.amorimtech.comunidade.ui.theme.SleekTextBody
import br.com.amorimtech.comunidade.ui.theme.SleekWhite
import br.com.amorimtech.comunidade.ui.theme.Amber
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.CopperDark
import br.com.amorimtech.comunidade.ui.theme.CopperLight
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.IvoryBorder
import br.com.amorimtech.comunidade.ui.theme.IvorySurface
import br.com.amorimtech.comunidade.ui.theme.IvorySurfaceVariant
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.NavyLight
import br.com.amorimtech.comunidade.ui.theme.NavySurface
import br.com.amorimtech.comunidade.ui.theme.TextMuted
import br.com.amorimtech.comunidade.ui.theme.TextPrimary
import br.com.amorimtech.comunidade.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun FeedScreen(
    postsList: List<Post>,
    onToggleLike: (String) -> Unit,
    onOpenPostDetail: (Post) -> Unit,
    onAddNewPost: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTag by remember { mutableStateOf("Todos") }
    var showCreateDialog by remember { mutableStateOf(false) }
    val tags = listOf("Todos", "Patologia", "Perícias", "NBR 15575/16747", "Negócios")

    val filteredPosts = remember(selectedTag, postsList) {
        if (selectedTag == "Todos") postsList
        else postsList.filter { it.tag.equals(selectedTag, ignoreCase = true) }
    }

    Box(modifier = modifier.fillMaxSize().background(IvoryBackground)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 88.dp)
        ) {
            // Live Session Spotlight Banner from Sleek Interface design
            item {
                LiveSessionSpotlightCard(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                )
            }

            // Create Post Trigger Header
            item {
                CreatePostHeader(
                    onTriggerClick = { showCreateDialog = true },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            // Section Header: Feed da Comunidade & "Ver tudo"
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Feed da Comunidade",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Copper.copy(alpha = 0.10f))
                            .clickable { selectedTag = "Todos" }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Ver tudo",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Copper
                        )
                    }
                }
            }

            // Tag Filters
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tags) { tag ->
                        CategoryChip(
                            text = tag,
                            isSelected = selectedTag == tag,
                            onClick = { selectedTag = tag },
                            modifier = Modifier.testTag("feed_tag_$tag")
                        )
                    }
                }
            }

            // Posts List
            items(filteredPosts, key = { it.id }) { post ->
                PostCard(
                    post = post,
                    onToggleLike = { onToggleLike(post.id) },
                    onCardClick = { onOpenPostDetail(post) },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            if (filteredPosts.isEmpty()) {
                item {
                    EmptyFeedState(tag = selectedTag)
                }
            }
        }

        // New Post Dialog
        if (showCreateDialog) {
            CreatePostDialog(
                onDismiss = { showCreateDialog = false },
                onPostCreated = { newPost ->
                    onAddNewPost(newPost)
                    showCreateDialog = false
                }
            )
        }
    }
}

@Composable
fun LiveSessionSpotlightCard(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDark),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "PRÓXIMA AULA LIVE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.6.sp,
                    color = Amber
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Patologia de Estruturas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Hoje às 19:30 • via Zoom",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.70f)
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Copper)
                    .border(4.dp, Color.White.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Assistir Aula",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun CreatePostHeader(
    onTriggerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onTriggerClick)
            .testTag("create_post_trigger")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AuthorAvatar(
                    autor = DadosMock.autorRoberto,
                    size = 44.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(SleekIconBg)
                        .border(1.dp, SleekBorderSubtle, RoundedCornerShape(24.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Compartilhe um caso, dúvida ou insight...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = SleekGray500
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                QuickActionItem(
                    icon = Icons.Default.CameraAlt,
                    label = "Caso Técnico",
                    color = Copper,
                    onClick = onTriggerClick
                )
                QuickActionItem(
                    icon = Icons.Default.Poll,
                    label = "Enquete",
                    color = Amber,
                    onClick = onTriggerClick
                )
                QuickActionItem(
                    icon = Icons.Default.Image,
                    label = "Laudo/Foto",
                    color = NavyLight,
                    onClick = onTriggerClick
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextPrimary
        )
    }
}

@Composable
fun PostCard(
    post: Post,
    onToggleLike: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick)
            .testTag("post_card_${post.id}")
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header: Author & Tag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AuthorAvatar(autor = post.autor, size = 48.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.autor.nomeCompleto,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Text(
                        text = "${post.autor.tituloProfissional ?: "Especialista"} • Há 2 horas",
                        style = MaterialTheme.typography.bodySmall,
                        color = SleekGray500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (!post.tag.isNullOrBlank()) {
                    TagBadge(tag = post.tag)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Post Content
            Text(
                text = post.conteudo,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = SleekTextBody,
                lineHeight = 22.sp
            )

            // Technical Image Preview Box (if has photos)
            if (post.fotosUrls.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(NavyDark, NavyLight)
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Copper.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Foto da Inspeção",
                                tint = Amber,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Registro Fotográfico de Vistoria Pericial",
                            style = MaterialTheme.typography.titleSmall,
                            color = IvoryBackground
                        )
                        Text(
                            text = "Inspeção em campo • Evidência técnica catalogada",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Row: Like, Comment, Share (border-t border-gray-50)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.5.dp,
                        color = SleekBorderSubtle,
                        shape = RoundedCornerShape(0.dp)
                    )
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Like Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(onClick = onToggleLike)
                            .padding(vertical = 4.dp)
                            .testTag("post_like_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = if (post.curtidoPeloUsuario) Icons.Default.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "Curtir",
                            tint = if (post.curtidoPeloUsuario) Copper else SleekGray500,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${post.curtidas}",
                            fontSize = 12.sp,
                            fontWeight = if (post.curtidoPeloUsuario) FontWeight.Bold else FontWeight.Medium,
                            color = if (post.curtidoPeloUsuario) Copper else SleekGray500
                        )
                    }

                    // Comment Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(onClick = onCardClick)
                            .padding(vertical = 4.dp)
                            .testTag("post_comment_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Comentários",
                            tint = SleekGray500,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${post.comentarios.size}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = SleekGray500
                        )
                    }
                }

                // Share Button in Copper
                IconButton(
                    onClick = { /* simulated share */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartilhar",
                        tint = Copper,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onPostCreated: (Post) -> Unit
) {
    var contentText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Patologia") }
    val categories = listOf("Patologia", "Perícias", "NBR 15575/16747", "Negócios")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Nova Publicação Técnica",
                style = MaterialTheme.typography.titleLarge,
                color = NavyDark
            )
        },
        text = {
            Column {
                Text(
                    text = "Autor: ${DadosMock.usuarioAtual.nomeCompleto}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Tag selector
                Text(
                    text = "Categoria técnica:",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { cat ->
                        CategoryChip(
                            text = cat,
                            isSelected = selectedCategory == cat,
                            onClick = { selectedCategory = cat }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = contentText,
                    onValueChange = { contentText = it },
                    placeholder = { Text("Escreva o relato pericial, caso de obra ou pergunta...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("create_post_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Copper,
                        unfocusedBorderColor = IvoryBorder
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (contentText.isNotBlank()) {
                        val newPost = Post(
                            id = "post_${UUID.randomUUID().toString().take(8)}",
                            autor = DadosMock.autorRoberto,
                            tipo = "texto",
                            tag = selectedCategory,
                            conteudo = contentText.trim(),
                            criadoEm = "2026-09-03T18:00:00Z",
                            curtidas = 0,
                            curtidoPeloUsuario = false,
                            comentarios = emptyList()
                        )
                        onPostCreated(newPost)
                    }
                },
                enabled = contentText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Copper),
                modifier = Modifier.testTag("create_post_confirm_button")
            ) {
                Text("Publicar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = TextSecondary)
            }
        }
    )
}

@Composable
fun EmptyFeedState(tag: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Nenhuma publicação encontrada em '$tag'",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Seja o primeiro especialista a publicar sobre este tema!",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted
            )
        }
    }
}
