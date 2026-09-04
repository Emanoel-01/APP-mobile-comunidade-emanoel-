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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.mock.DadosMock
import br.com.amorimtech.comunidade.data.model.CapituloGuia
import br.com.amorimtech.comunidade.ui.theme.Amber
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.SleekBorder
import br.com.amorimtech.comunidade.ui.theme.SleekBorderSubtle
import br.com.amorimtech.comunidade.ui.theme.SleekGray400
import br.com.amorimtech.comunidade.ui.theme.SleekGray500
import br.com.amorimtech.comunidade.ui.theme.SleekIconBg
import br.com.amorimtech.comunidade.ui.theme.SleekTextBody

@Composable
fun GuiaConsultaView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCapitulo by remember { mutableStateOf<CapituloGuia?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoria by remember { mutableStateOf("Todas") }

    if (selectedCapitulo != null) {
        // Detalhe do Capítulo Construtivo
        CapituloDetalheView(
            capitulo = selectedCapitulo!!,
            onBack = { selectedCapitulo = null }
        )
    } else {
        // Lista com busca e filtros
        val categorias = listOf("Todas", "Sistemas Estruturais", "Construção a Seco", "Vedações e Proteções", "Fachadas e Revestimentos", "Instalações", "Geotecnia e Fundações")

        val capitulosFiltrados = DadosMock.capitulosGuiaMock.filter { cap ->
            val matchQuery = searchQuery.isEmpty() ||
                    cap.titulo.contains(searchQuery, ignoreCase = true) ||
                    cap.resumo.contains(searchQuery, ignoreCase = true)
            val matchCategoria = selectedCategoria == "Todas" || cap.categoria.equals(selectedCategoria, ignoreCase = true)
            matchQuery && matchCategoria
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(IvoryBackground)
        ) {
            // Header do Guia
            Surface(
                color = NavyDark,
                modifier = Modifier.fillMaxWidth()
            ) {
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
                    Column {
                        Text(
                            text = "Guia de Consulta",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Bíblia da Edificação — 41 Sistemas Construtivos",
                            fontSize = 11.sp,
                            color = Amber
                        )
                    }
                }
            }

            // Barra de Busca
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar sistemas, normas (ex: NBR 6118)...", fontSize = 13.sp, color = SleekGray400) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = SleekGray400
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Copper,
                        unfocusedBorderColor = SleekBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("guia_search_input")
                )
            }

            // Categorias em Carrossel
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categorias) { cat ->
                    val isSelected = selectedCategoria == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) Copper else Copper.copy(alpha = 0.08f))
                            .border(
                                1.dp,
                                if (isSelected) Copper else Copper.copy(alpha = 0.22f),
                                RoundedCornerShape(50)
                            )
                            .clickable { selectedCategoria = cat }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSelected) Color.White else Copper
                        )
                    }
                }
            }

            // Lista de Capítulos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(capitulosFiltrados, key = { it.id }) { capitulo ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedCapitulo = capitulo }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(SleekIconBg),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = String.format("%02d", capitulo.numero),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Copper
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = capitulo.categoria.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp,
                                    color = SleekGray400
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = capitulo.titulo,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = NavyDark
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = capitulo.resumo,
                                    fontSize = 12.sp,
                                    color = SleekGray500,
                                    lineHeight = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Ver capítulo",
                                tint = SleekGray400,
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
fun CapituloDetalheView(
    capitulo: CapituloGuia,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        Surface(
            color = NavyDark,
            modifier = Modifier.fillMaxWidth()
        ) {
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
                Column {
                    Text(
                        text = "Capítulo ${capitulo.numero} • ${capitulo.categoria}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Amber
                    )
                    Text(
                        text = capitulo.titulo,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = capitulo.titulo,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = capitulo.resumo,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Copper,
                        lineHeight = 18.sp
                    )

                    HorizontalDivider(
                        color = SleekBorderSubtle,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    Text(
                        text = capitulo.conteudoCompleto,
                        fontSize = 14.sp,
                        color = SleekTextBody,
                        lineHeight = 22.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}
