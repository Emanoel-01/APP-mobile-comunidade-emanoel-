package br.com.amorimtech.comunidade.ui.screens.more

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.SleekBorder
import br.com.amorimtech.comunidade.ui.theme.SleekBorderSubtle
import br.com.amorimtech.comunidade.ui.theme.SleekGray400
import br.com.amorimtech.comunidade.ui.theme.SleekGray500
import br.com.amorimtech.comunidade.ui.theme.SleekIconBg

@Composable
fun MoreScreen(
    onNavigateToFeed: () -> Unit,
    onNavigateToForum: () -> Unit,
    onNavigateToMateriais: () -> Unit,
    onNavigateToVagas: () -> Unit,
    onNavigateToEventos: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Cabeçalho da Seção Mais
        item {
            Text(
                text = "CENTRAL DA COMUNIDADE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = Copper
            )
        }

        // Card com as opções de navegação
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    MoreMenuItem(
                        icon = Icons.Default.Feed,
                        title = "Feed de Notícias",
                        subtitle = "Publicações, artigos técnicos e novidades",
                        onClick = onNavigateToFeed,
                        testTag = "more_nav_feed"
                    )

                    HorizontalDivider(
                        color = SleekBorderSubtle,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    MoreMenuItem(
                        icon = Icons.Default.Forum,
                        title = "Fórum Técnico",
                        subtitle = "Debates sobre patologias, laudos e normas",
                        onClick = onNavigateToForum,
                        testTag = "more_nav_forum"
                    )

                    HorizontalDivider(
                        color = SleekBorderSubtle,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    MoreMenuItem(
                        icon = Icons.Default.Folder,
                        title = "Acervo de Materiais",
                        subtitle = "Planilhas, modelos de laudo e checklists",
                        onClick = onNavigateToMateriais,
                        testTag = "more_nav_materiais"
                    )

                    HorizontalDivider(
                        color = SleekBorderSubtle,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    MoreMenuItem(
                        icon = Icons.Default.Work,
                        title = "Mural de Vagas",
                        subtitle = "Oportunidades profissionais e parcerias",
                        onClick = onNavigateToVagas,
                        testTag = "more_nav_vagas"
                    )

                    HorizontalDivider(
                        color = SleekBorderSubtle,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    MoreMenuItem(
                        icon = Icons.Default.CalendarMonth,
                        title = "Agenda de Eventos",
                        subtitle = "Masterclasses e encontros ao vivo",
                        onClick = onNavigateToEventos,
                        testTag = "more_nav_eventos"
                    )
                }
            }
        }

        // Ações da Conta / Sair
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onLogout)
                        .padding(16.dp)
                        .testTag("more_btn_logout"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEE2E2)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sair",
                            tint = Color(0xFFDC2626),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Sair da conta",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDC2626)
                        )
                        Text(
                            text = "Encerrar sessão no aplicativo",
                            fontSize = 11.sp,
                            color = SleekGray500
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MoreMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(SleekIconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NavyDark,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = SleekGray500
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = SleekGray400,
            modifier = Modifier.size(14.dp)
        )
    }
}
