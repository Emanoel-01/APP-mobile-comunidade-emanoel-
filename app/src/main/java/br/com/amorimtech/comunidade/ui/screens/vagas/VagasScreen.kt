package br.com.amorimtech.comunidade.ui.screens.vagas

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.mock.DadosMock
import br.com.amorimtech.comunidade.data.model.Vaga
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.SleekBorder
import br.com.amorimtech.comunidade.ui.theme.SleekBorderSubtle
import br.com.amorimtech.comunidade.ui.theme.SleekGray500
import br.com.amorimtech.comunidade.ui.theme.SleekIconBg
import br.com.amorimtech.comunidade.ui.theme.SleekTextBody

@Composable
fun VagasScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val vagasList = remember { mutableStateListOf(*DadosMock.vagasMock.toTypedArray()) }
    var selectedTipo by remember { mutableStateOf("Todos") }

    val tipos = listOf("Todos", "PJ", "CLT", "Freelance", "Estágio")
    val filteredVagas = if (selectedTipo == "Todos") {
        vagasList
    } else {
        vagasList.filter { it.tipo.equals(selectedTipo, ignoreCase = true) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Banner Informativo
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
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Copper),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Mural de Oportunidades",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Parcerias e vagas exclusivas para membros da Comunidade",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    }
                }
            }
        }

        // Filtros de Tipo
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tipos) { tipo ->
                    val isSelected = selectedTipo == tipo
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) Copper else Copper.copy(alpha = 0.08f))
                            .border(
                                1.dp,
                                if (isSelected) Copper else Copper.copy(alpha = 0.22f),
                                RoundedCornerShape(50)
                            )
                            .clickable { selectedTipo = tipo }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = tipo,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else Copper
                        )
                    }
                }
            }
        }

        // Lista de Vagas
        items(filteredVagas, key = { it.id }) { vaga ->
            VagaItemCard(
                vaga = vaga,
                onCandidatar = {
                    val idx = vagasList.indexOfFirst { it.id == vaga.id }
                    if (idx != -1) {
                        val novoStatus = !vagasList[idx].jaCandidatado
                        vagasList[idx] = vagasList[idx].copy(jaCandidatado = novoStatus)
                        val msg = if (novoStatus) {
                            "Candidatura enviada para ${vaga.empresa}! Seu perfil profissional foi compartilhado."
                        } else {
                            "Candidatura cancelada."
                        }
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
fun VagaItemCard(
    vaga: Vaga,
    onCandidatar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = vaga.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = null,
                            tint = SleekGray500,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = vaga.empresa,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = SleekGray500
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = SleekGray500,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = vaga.local,
                            fontSize = 12.sp,
                            color = SleekGray500
                        )
                    }
                }

                // Badge Tipo (PJ, CLT etc.)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Copper.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = vaga.tipo,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Copper
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = vaga.descricao,
                fontSize = 13.sp,
                color = SleekTextBody,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Publicado: ${vaga.publicadoEm}",
                    fontSize = 11.sp,
                    color = SleekGray500
                )

                if (vaga.jaCandidatado) {
                    OutlinedButton(
                        onClick = onCandidatar,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF059669),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Candidatado",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF059669)
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = onCandidatar,
                        colors = ButtonDefaults.buttonColors(containerColor = Copper),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(
                            text = "Candidatar-se",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
