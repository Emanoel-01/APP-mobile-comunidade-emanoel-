package br.com.amorimtech.comunidade.ui.screens.agents

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.mock.DadosMock
import br.com.amorimtech.comunidade.data.model.AgenteCatalogo
import br.com.amorimtech.comunidade.ui.theme.Amber
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.SleekBorder
import br.com.amorimtech.comunidade.ui.theme.SleekGray400
import br.com.amorimtech.comunidade.ui.theme.SleekGray500
import br.com.amorimtech.comunidade.ui.theme.SleekIconBg

@Composable
fun AgentsScreen(
    modifier: Modifier = Modifier
) {
    var activeAgentId by remember { mutableStateOf<String?>(null) }
    var showBlockedNotice by remember { mutableStateOf<AgenteCatalogo?>(null) }
    var showWebOnlyNotice by remember { mutableStateOf<AgenteCatalogo?>(null) }

    when {
        activeAgentId == "guia-tipologias" -> {
            GuiaConsultaView(onBack = { activeAgentId = null })
        }
        activeAgentId == "viabiliza-ia" -> {
            ViabilizaIaView(onBack = { activeAgentId = null })
        }
        showBlockedNotice != null -> {
            AgentBlockedScreen(
                agente = showBlockedNotice!!,
                onBack = { showBlockedNotice = null }
            )
        }
        showWebOnlyNotice != null -> {
            AgentWebOnlyScreen(
                agente = showWebOnlyNotice!!,
                onBack = { showWebOnlyNotice = null }
            )
        }
        else -> {
            // Catálogo Principal com 10 Agentes
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(IvoryBackground),
                contentPadding = PaddingValues(bottom = 96.dp)
            ) {
                // Banner
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = NavyDark),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(Copper),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Agentes de IA de Engenharia",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Automação técnica, viabilidade e dimensionamento",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.75f)
                                )
                            }
                        }
                    }
                }

                // Grid/Lista dos 10 Agentes
                items(DadosMock.agentesCatalogo, key = { it.id }) { agente ->
                    val liberado = DadosMock.permissoesUsuarioMock[agente.id] == true || agente.gratuito

                    AgenteItemCard(
                        agente = agente,
                        liberado = liberado,
                        onClick = {
                            if (!liberado) {
                                showBlockedNotice = agente
                            } else {
                                when (agente.id) {
                                    "guia-tipologias" -> activeAgentId = "guia-tipologias"
                                    "viabiliza-ia" -> activeAgentId = "viabiliza-ia"
                                    else -> showWebOnlyNotice = agente
                                }
                            }
                        },
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AgenteItemCard(
    agente: AgenteCatalogo,
    liberado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (liberado) SleekBorder else SleekBorder.copy(alpha = 0.6f)
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("agente_card_${agente.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(if (liberado) 1.0f else 0.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone do Agente
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (liberado) SleekIconBg else Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getAgentIcon(agente.icone),
                    contentDescription = agente.nome,
                    tint = if (liberado) Copper else SleekGray500,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = agente.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    if (agente.gratuito) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE0E7FF))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "GRÁTIS",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4338CA)
                            )
                        }
                    } else if (liberado) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFDEF7EC))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "LIBERADO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF03543F)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = agente.descricao,
                    fontSize = 12.sp,
                    color = SleekGray500,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (!liberado) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Bloqueado",
                    tint = SleekGray400,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Acessar",
                    tint = SleekGray400,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

private fun getAgentIcon(iconName: String): ImageVector {
    return when (iconName) {
        "book" -> Icons.Default.MenuBook
        "cash" -> Icons.Default.MonetizationOn
        "chart" -> Icons.Default.ShowChart
        "ruler" -> Icons.Default.SquareFoot
        "list" -> Icons.Default.FormatListBulleted
        "building" -> Icons.Default.Build
        "money" -> Icons.Default.MonetizationOn
        "site" -> Icons.Default.Handyman
        "prompt" -> Icons.Default.Description
        "code" -> Icons.Default.Code
        else -> Icons.Default.Psychology
    }
}

@Composable
fun AgentBlockedScreen(
    agente: AgenteCatalogo,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        Surface(color = NavyDark, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = agente.nome,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFEF2F2))
                    .border(2.dp, Color(0xFFFCA5A5), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color(0xFFDC2626),
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Ferramenta Bloqueada",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "O agente '${agente.nome}' não está incluso no seu plano atual.",
                fontSize = 14.sp,
                color = SleekGray500,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Solicite a liberação da sua licença profissional pelo site amorimtech.com.br",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Copper,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = NavyDark),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(46.dp)
            ) {
                Text(text = "Voltar ao Catálogo", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AgentWebOnlyScreen(
    agente: AgenteCatalogo,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        Surface(color = NavyDark, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = agente.nome,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(SleekIconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getAgentIcon(agente.icone),
                    contentDescription = null,
                    tint = Copper,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = agente.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Em breve nesta versão mobile do app.",
                fontSize = 14.sp,
                color = SleekGray500,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Esta ferramenta está totalmente operacional na plataforma web em amorimtech.com.br.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = SleekGray500,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = Copper),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(46.dp)
            ) {
                Text(text = "Voltar ao Catálogo", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
