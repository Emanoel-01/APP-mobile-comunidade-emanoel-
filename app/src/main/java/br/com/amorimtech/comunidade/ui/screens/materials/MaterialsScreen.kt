package br.com.amorimtech.comunidade.ui.screens.materials

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.model.MaterialItem
import br.com.amorimtech.comunidade.ui.components.CategoryChip
import br.com.amorimtech.comunidade.ui.components.TagBadge
import br.com.amorimtech.comunidade.ui.theme.Amber
import br.com.amorimtech.comunidade.ui.theme.Copper
import br.com.amorimtech.comunidade.ui.theme.CopperDark
import br.com.amorimtech.comunidade.ui.theme.IvoryBackground
import br.com.amorimtech.comunidade.ui.theme.IvoryBorder
import br.com.amorimtech.comunidade.ui.theme.IvorySurface
import br.com.amorimtech.comunidade.ui.theme.IvorySurfaceVariant
import br.com.amorimtech.comunidade.ui.theme.NavyDark
import br.com.amorimtech.comunidade.ui.theme.NavyLight
import br.com.amorimtech.comunidade.ui.theme.SuccessGreen
import br.com.amorimtech.comunidade.ui.theme.TextMuted
import br.com.amorimtech.comunidade.ui.theme.TextPrimary
import br.com.amorimtech.comunidade.ui.theme.TextSecondary

@Composable
fun MaterialsScreen(
    materiais: List<MaterialItem>,
    onUnlockAccess: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("Todos") }
    var searchQuery by remember { mutableStateOf("") }
    var activeMaterialForDetail by remember { mutableStateOf<MaterialItem?>(null) }
    var downloadDialogMessage by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        "Todos",
        "Planilhas",
        "Modelos de Laudo",
        "Checklists",
        "E-books",
        "Vídeos",
        "Skills Claude",
        "Outros"
    )

    val filteredMateriais = remember(selectedCategory, searchQuery, materiais) {
        materiais.filter { item ->
            val matchCategory = selectedCategory == "Todos" || item.categoria.equals(selectedCategory, ignoreCase = true)
            val matchQuery = searchQuery.isBlank() ||
                    item.titulo.contains(searchQuery, ignoreCase = true) ||
                    (item.descricao?.contains(searchQuery, ignoreCase = true) ?: false)
            matchCategory && matchQuery
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header
            item {
                MaterialsHeaderBanner(modifier = Modifier.padding(16.dp))
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar planilhas, laudos, checklists...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = TextMuted
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .testTag("materials_search_input"),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Copper,
                        unfocusedBorderColor = IvoryBorder,
                        focusedContainerColor = IvorySurface,
                        unfocusedContainerColor = IvorySurface
                    ),
                    singleLine = true
                )
            }

            // Category Chips
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { cat ->
                        CategoryChip(
                            text = cat,
                            isSelected = selectedCategory == cat,
                            onClick = { selectedCategory = cat },
                            modifier = Modifier.testTag("materials_cat_$cat")
                        )
                    }
                }
            }

            // Items List
            items(filteredMateriais, key = { it.id }) { item ->
                MaterialCard(
                    material = item,
                    onClick = { activeMaterialForDetail = item },
                    onActionClick = {
                        if (item.temAcesso) {
                            downloadDialogMessage = "Download iniciado com sucesso: '${item.titulo}'. Arquivo salvo na pasta local de downloads."
                        } else {
                            // unlock access
                            onUnlockAccess(item.id)
                            downloadDialogMessage = "Acesso desbloqueado com sucesso para '${item.titulo}'!"
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            if (filteredMateriais.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum material encontrado com os filtros atuais.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        // Material Detail Dialog
        activeMaterialForDetail?.let { mat ->
            MaterialDetailDialog(
                material = mat,
                onDismiss = { activeMaterialForDetail = null },
                onAction = {
                    if (mat.temAcesso) {
                        downloadDialogMessage = "Download de '${mat.titulo}' iniciado."
                    } else {
                        onUnlockAccess(mat.id)
                        downloadDialogMessage = "Acesso ao material '${mat.titulo}' liberado!"
                    }
                    activeMaterialForDetail = null
                }
            )
        }

        // Feedback / Download Dialog
        downloadDialogMessage?.let { msg ->
            AlertDialog(
                onDismissRequest = { downloadDialogMessage = null },
                title = {
                    Text(
                        text = "Acervo de Materiais",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark
                    )
                },
                text = {
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { downloadDialogMessage = null },
                        colors = ButtonDefaults.buttonColors(containerColor = Copper)
                    ) {
                        Text("OK", color = Color.White)
                    }
                }
            )
        }
    }
}

@Composable
fun MaterialsHeaderBanner(modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = NavyDark),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Amber),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = NavyDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Acervo Técnico Especializado",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = IvoryBackground
                    )
                    Text(
                        text = "Planilhas automatizadas, minutas e laudos ABNT",
                        style = MaterialTheme.typography.bodySmall,
                        color = Amber
                    )
                }
            }
        }
    }
}

@Composable
fun MaterialCard(
    material: MaterialItem,
    onClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formatColor = when (material.formato?.uppercase()) {
        "XLSX" -> Color(0xFF1E7145)
        "PDF" -> Color(0xFFC4302B)
        "DOCX" -> Color(0xFF2B579A)
        "MP4" -> Color(0xFF6C3483)
        "TXT / MD" -> NavyLight
        else -> Copper
    }

    val formatIcon = when (material.formato?.uppercase()) {
        "XLSX" -> Icons.Default.TableChart
        "PDF" -> Icons.Default.Description
        "DOCX" -> Icons.Default.Description
        "MP4" -> Icons.Default.PlayCircle
        "TXT / MD" -> Icons.Default.SmartToy
        else -> Icons.Default.FolderZip
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IvorySurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, IvoryBorder),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("material_card_${material.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Format Icon Badge
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(formatColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = formatIcon,
                        contentDescription = material.formato,
                        tint = formatColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TagBadge(tag = material.categoria)

                        // Access status badge
                        if (!material.pago || material.temAcesso) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SuccessGreen.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = if (!material.pago) "Gratuito" else "Liberado",
                                    color = SuccessGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Amber.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "R$ ${"%.2f".format(material.valor ?: 0.0)}",
                                    color = Amber,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = material.titulo,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = NavyDark,
                        lineHeight = 20.sp
                    )

                    material.descricao?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${material.formato ?: "Arquivo"} • ${material.tamanho ?: ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMuted
                        )

                        Button(
                            onClick = onActionClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (material.temAcesso) NavyDark else Copper
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("material_action_${material.id}")
                        ) {
                            Icon(
                                imageVector = if (material.temAcesso) Icons.Default.Download else Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (material.temAcesso) "Baixar" else "Adquirir",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MaterialDetailDialog(
    material: MaterialItem,
    onDismiss: () -> Unit,
    onAction: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = material.titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NavyDark
            )
        },
        text = {
            Column {
                TagBadge(tag = material.categoria)
                Spacer(modifier = Modifier.height(10.dp))
                material.descricao?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary,
                        lineHeight = 22.sp
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(IvorySurfaceVariant)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Formato", style = MaterialTheme.typography.labelSmall)
                        Text(
                            material.formato ?: "N/D",
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    }
                    Column {
                        Text("Tamanho", style = MaterialTheme.typography.labelSmall)
                        Text(
                            material.tamanho ?: "N/D",
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    }
                    Column {
                        Text("Status", style = MaterialTheme.typography.labelSmall)
                        Text(
                            if (material.temAcesso) "Liberado" else "Pago",
                            fontWeight = FontWeight.Bold,
                            color = if (material.temAcesso) SuccessGreen else Amber
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onAction,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (material.temAcesso) NavyDark else Copper
                )
            ) {
                Text(
                    text = if (material.temAcesso) "Baixar Material" else "Desbloquear Acesso",
                    color = Color.White
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar", color = TextSecondary)
            }
        }
    )
}
