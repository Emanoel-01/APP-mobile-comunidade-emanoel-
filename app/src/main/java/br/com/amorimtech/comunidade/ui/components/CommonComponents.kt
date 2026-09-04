package br.com.amorimtech.comunidade.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.model.AutorPublico
import br.com.amorimtech.comunidade.ui.theme.Amber
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.CopperDark
import br.com.amorimtech.comunidade.ui.theme.CopperLight
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.IvoryBorder
import br.com.amorimtech.comunidade.ui.theme.IvorySurface
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.NavyLight
import br.com.amorimtech.comunidade.ui.theme.NavySurface
import br.com.amorimtech.comunidade.ui.theme.TextMuted
import br.com.amorimtech.comunidade.ui.theme.TextOnDark
import br.com.amorimtech.comunidade.ui.theme.TextPrimary
import br.com.amorimtech.comunidade.ui.theme.TextSecondary

@Composable
fun AppTopBar(
    title: String,
    subtitle: String? = null,
    onSearchClick: (() -> Unit)? = null,
    onNotificationsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shadowElevation = 2.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, br.com.amorimtech.comunidade.ui.theme.SleekBorderSubtle),
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(br.com.amorimtech.comunidade.ui.theme.SleekIconBg)
                        .border(1.dp, br.com.amorimtech.comunidade.ui.theme.SleekBorder, CircleShape)
                        .clickable(onClick = onBackClick)
                        .testTag("top_bar_back_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = NavyDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AMORIMTECH",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.8.sp,
                    color = Copper
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                    fontWeight = FontWeight.Bold,
                    color = NavyDark,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (onSearchClick != null) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(br.com.amorimtech.comunidade.ui.theme.SleekIconBg)
                            .border(1.dp, br.com.amorimtech.comunidade.ui.theme.SleekBorder, CircleShape)
                            .clickable(onClick = onSearchClick)
                            .testTag("top_bar_search_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = NavyDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (onNotificationsClick != null) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(br.com.amorimtech.comunidade.ui.theme.SleekIconBg)
                            .border(1.dp, br.com.amorimtech.comunidade.ui.theme.SleekBorder, CircleShape)
                            .clickable(onClick = onNotificationsClick)
                            .testTag("top_bar_notifications_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = Copper,
                                    contentColor = Color.White
                                ) {
                                    Text("2", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notificações",
                                tint = NavyDark,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AuthorAvatar(
    autor: AutorPublico,
    size: Dp = 44.dp,
    modifier: Modifier = Modifier
) {
    val initials = autor.nomeCompleto
        .split(" ")
        .filter { it.isNotBlank() && !it.startsWith("Eng") && !it.startsWith("Arq") && !it.startsWith("Prof") && !it.startsWith("Dr") }
        .take(2)
        .mapNotNull { it.firstOrNull()?.toString() }
        .joinToString("")
        .ifEmpty { autor.nomeCompleto.take(2).uppercase() }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(NavyDark),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = (size.value * 0.36f).sp
        )
    }
}

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) Copper else Copper.copy(alpha = 0.08f)
    val textColor = if (isSelected) Color.White else Copper
    val borderColor = if (isSelected) Copper else Copper.copy(alpha = 0.22f)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
    }
}

@Composable
fun TagBadge(
    tag: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor) = when (tag.lowercase()) {
        "patologia" -> Pair(Copper.copy(alpha = 0.12f), Copper)
        "perícias" -> Pair(NavyDark.copy(alpha = 0.10f), NavyDark)
        "nbr 15575/16747" -> Pair(Amber.copy(alpha = 0.12f), Amber)
        "negócios" -> Pair(Color(0xFF2E7D32).copy(alpha = 0.12f), Color(0xFF2E7D32))
        else -> Pair(Copper.copy(alpha = 0.08f), Copper)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = tag.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            color = textColor
        )
    }
}

enum class NavigationTab(
    val title: String,
    val icon: ImageVector,
    val testTag: String
) {
    FEED("Feed", Icons.Default.RssFeed, "nav_feed"),
    FORUM("Fórum", Icons.Default.Forum, "nav_forum"),
    CURSOS("Cursos", Icons.Default.School, "nav_cursos"),
    MATERIAIS("Materiais", Icons.Default.MenuBook, "nav_materiais"),
    PERFIL("Perfil", Icons.Default.Person, "nav_perfil")
}

@Composable
fun AppBottomNavigationBar(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, br.com.amorimtech.comunidade.ui.theme.SleekBorderSubtle),
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = NavyDark,
            tonalElevation = 0.dp,
            modifier = Modifier.height(76.dp)
        ) {
            NavigationTab.entries.forEach { tab ->
                val isSelected = currentTab == tab
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = tab.title,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Copper,
                        selectedTextColor = Copper,
                        indicatorColor = Copper.copy(alpha = 0.12f),
                        unselectedIconColor = br.com.amorimtech.comunidade.ui.theme.SleekGray400,
                        unselectedTextColor = br.com.amorimtech.comunidade.ui.theme.SleekGray400
                    ),
                    modifier = Modifier.testTag(tab.testTag)
                )
            }
        }
    }
}
