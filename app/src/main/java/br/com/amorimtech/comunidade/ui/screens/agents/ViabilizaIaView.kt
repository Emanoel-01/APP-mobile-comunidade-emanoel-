package br.com.amorimtech.comunidade.ui.screens.agents

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.amorimtech.comunidade.data.mock.DadosMock
import br.com.amorimtech.comunidade.data.model.ProjetoCredito
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

private enum class ViabilizaStep {
    MEUS_PROJETOS,
    NOVO_PROJETO,
    DADOS_CALCULO,
    RESULTADO
}

@Composable
fun ViabilizaIaView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(ViabilizaStep.MEUS_PROJETOS) }
    val projetosSalvos = remember { mutableStateListOf(*DadosMock.projetosCreditoMock.toTypedArray()) }

    // Formulário do novo projeto
    var nomeProjeto by remember { mutableStateOf("") }
    var nomeCliente by remember { mutableStateOf("") }
    var tipoOperacao by remember { mutableStateOf("terreno_construcao") }
    var uf by remember { mutableStateOf("SP") }
    var cidade by remember { mutableStateOf("São Paulo") }

    // Formulário de dados de cálculo
    // NOTA: Estes campos são mockados localmente para posterior persistência no Supabase.
    var idadeCliente by remember { mutableStateOf("38") }
    var rendaMensal by remember { mutableStateOf("16500") }
    var valorTerreno by remember { mutableStateOf("250000") }
    var areaConstruida by remember { mutableStateOf("180") }
    var padraoCUB by remember { mutableStateOf("Normal") } // Baixo, Normal, Alto
    var valorFinanciar by remember { mutableStateOf("520000") }

    // Resultado calculado
    var resultadosCalculados by remember {
        mutableStateOf<List<DadosMock.ResultadoElegibilidadeBanco>>(emptyList())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        // TopBar do Viabiliza IA
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
                IconButton(
                    onClick = {
                        when (currentStep) {
                            ViabilizaStep.MEUS_PROJETOS -> onBack()
                            ViabilizaStep.NOVO_PROJETO -> currentStep = ViabilizaStep.MEUS_PROJETOS
                            ViabilizaStep.DADOS_CALCULO -> currentStep = ViabilizaStep.NOVO_PROJETO
                            ViabilizaStep.RESULTADO -> currentStep = ViabilizaStep.DADOS_CALCULO
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Viabiliza IA",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = when (currentStep) {
                            ViabilizaStep.MEUS_PROJETOS -> "Meus Projetos de Crédito"
                            ViabilizaStep.NOVO_PROJETO -> "Passo 1 de 3 • Identificação"
                            ViabilizaStep.DADOS_CALCULO -> "Passo 2 de 3 • Parâmetros Financeiros"
                            ViabilizaStep.RESULTADO -> "Passo 3 de 3 • Análise de Viabilidade"
                        },
                        fontSize = 11.sp,
                        color = Amber
                    )
                }
            }
        }

        when (currentStep) {
            ViabilizaStep.MEUS_PROJETOS -> {
                // TELA 1: Meus Projetos
                TelaMeusProjetos(
                    projetos = projetosSalvos,
                    onNovoProjeto = {
                        nomeProjeto = ""
                        nomeCliente = ""
                        tipoOperacao = "terreno_construcao"
                        currentStep = ViabilizaStep.NOVO_PROJETO
                    },
                    onAbrirProjeto = { proj ->
                        nomeProjeto = proj.nomeProjeto
                        nomeCliente = proj.nomeCliente
                        tipoOperacao = proj.tipoOperacao
                        uf = proj.uf
                        cidade = proj.cidade
                        valorFinanciar = proj.valorFinanciavel.toInt().toString()
                        // Recalcula para visualização
                        resultadosCalculados = DadosMock.calcularElegibilidade(
                            idade = 38,
                            rendaMensal = 16500.0,
                            custoTotal = proj.custoTotal,
                            valorFinanciarDesejado = proj.valorFinanciavel
                        )
                        currentStep = ViabilizaStep.RESULTADO
                    }
                )
            }

            ViabilizaStep.NOVO_PROJETO -> {
                // TELA 2: Novo Projeto (Identificação)
                TelaNovoProjeto(
                    nomeProjeto = nomeProjeto,
                    onNomeProjetoChange = { nomeProjeto = it },
                    nomeCliente = nomeCliente,
                    onNomeClienteChange = { nomeCliente = it },
                    tipoOperacao = tipoOperacao,
                    onTipoOperacaoChange = { tipoOperacao = it },
                    uf = uf,
                    onUfChange = { uf = it },
                    cidade = cidade,
                    onCidadeChange = { cidade = it },
                    onAvancar = {
                        if (nomeProjeto.isBlank() || nomeCliente.isBlank()) {
                            Toast.makeText(context, "Preencha o nome do projeto e cliente", Toast.LENGTH_SHORT).show()
                        } else {
                            currentStep = ViabilizaStep.DADOS_CALCULO
                        }
                    }
                )
            }

            ViabilizaStep.DADOS_CALCULO -> {
                // TELA 3: Dados para Cálculo
                TelaDadosCalculo(
                    tipoOperacao = tipoOperacao,
                    idadeCliente = idadeCliente,
                    onIdadeChange = { idadeCliente = it },
                    rendaMensal = rendaMensal,
                    onRendaChange = { rendaMensal = it },
                    valorTerreno = valorTerreno,
                    onValorTerrenoChange = { valorTerreno = it },
                    areaConstruida = areaConstruida,
                    onAreaChange = { areaConstruida = it },
                    padraoCUB = padraoCUB,
                    onPadraoCUBChange = { padraoCUB = it },
                    valorFinanciar = valorFinanciar,
                    onValorFinanciarChange = { valorFinanciar = it },
                    onCalcular = {
                        val idade = idadeCliente.toIntOrNull() ?: 38
                        val renda = rendaMensal.toDoubleOrNull() ?: 15000.0
                        val terreno = valorTerreno.toDoubleOrNull() ?: 200000.0
                        val area = areaConstruida.toDoubleOrNull() ?: 150.0
                        val custoM2 = when (padraoCUB) {
                            "Baixo" -> 2200.0
                            "Alto" -> 4100.0
                            else -> 3100.0 // Normal
                        }
                        val custoObra = area * custoM2
                        val custoTotal = if (tipoOperacao.contains("terreno")) terreno + custoObra else custoObra
                        val financiar = valorFinanciar.toDoubleOrNull() ?: (custoTotal * 0.8)

                        resultadosCalculados = DadosMock.calcularElegibilidade(
                            idade = idade,
                            rendaMensal = renda,
                            custoTotal = custoTotal,
                            valorFinanciarDesejado = financiar
                        )

                        currentStep = ViabilizaStep.RESULTADO
                    }
                )
            }

            ViabilizaStep.RESULTADO -> {
                // TELA 4: Resultado Bancário
                TelaResultadoViabilidade(
                    nomeProjeto = nomeProjeto.ifBlank { "Estudo de Viabilidade" },
                    nomeCliente = nomeCliente.ifBlank { "Cliente Homologado" },
                    resultados = resultadosCalculados,
                    onSalvarVoltar = {
                        val custoObra = (areaConstruida.toDoubleOrNull() ?: 150.0) * 3100.0
                        val custoTotal = (valorTerreno.toDoubleOrNull() ?: 0.0) + custoObra
                        val financiado = valorFinanciar.toDoubleOrNull() ?: (custoTotal * 0.8)
                        val primeiroBanco = resultadosCalculados.firstOrNull { it.elegivel }

                        val novoProj = ProjetoCredito(
                            id = "proj_${System.currentTimeMillis()}",
                            nomeProjeto = nomeProjeto.ifBlank { "Novo Projeto" },
                            nomeCliente = nomeCliente.ifBlank { "Cliente" },
                            tipoOperacao = tipoOperacao,
                            uf = uf,
                            cidade = cidade,
                            status = "concluido",
                            custoTotal = custoTotal,
                            valorFinanciavel = financiado,
                            parcelaEstimada = primeiroBanco?.parcelaEstimada
                        )
                        projetosSalvos.add(0, novoProj)
                        DadosMock.projetosCreditoMock.add(0, novoProj)
                        Toast.makeText(context, "Estudo salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        currentStep = ViabilizaStep.MEUS_PROJETOS
                    }
                )
            }
        }
    }
}

// -------------------------------------------------------------
// TELA 1: LISTA DOS PROJETOS SALVOS
// -------------------------------------------------------------
@Composable
private fun TelaMeusProjetos(
    projetos: List<ProjetoCredito>,
    onNovoProjeto: () -> Unit,
    onAbrirProjeto: (ProjetoCredito) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Botão Novo Projeto
        item {
            Button(
                onClick = onNovoProjeto,
                colors = ButtonDefaults.buttonColors(containerColor = Copper),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_novo_projeto_credito")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Novo",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+ Novo projeto de crédito",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        item {
            Text(
                text = "ESTUDOS ANTERIORES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = SleekGray400,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(projetos, key = { it.id }) { projeto ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAbrirProjeto(projeto) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = projeto.nomeProjeto,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Cliente: ${projeto.nomeCliente}",
                                fontSize = 12.sp,
                                color = SleekGray500
                            )
                        }

                        // Badge de Status
                        val isConcluido = projeto.status == "concluido"
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isConcluido) Color(0xFFDEF7EC) else Color(0xFFFEF08A))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (isConcluido) "CONCLUÍDO" else "RASCUNHO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isConcluido) Color(0xFF03543F) else Color(0xFF713F12)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Custo Estimado", fontSize = 10.sp, color = SleekGray400)
                            Text(
                                text = "R$ ${String.format("%,.0f", projeto.custoTotal)}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        }
                        Column {
                            Text(text = "Financiamento", fontSize = 10.sp, color = SleekGray400)
                            Text(
                                text = "R$ ${String.format("%,.0f", projeto.valorFinanciavel)}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Copper
                            )
                        }
                        Column {
                            Text(text = "Local", fontSize = 10.sp, color = SleekGray400)
                            Text(
                                text = "${projeto.cidade}/${projeto.uf}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SleekTextBody
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TELA 2: NOVO PROJETO (IDENTIFICAÇÃO & TIPO)
// -------------------------------------------------------------
@Composable
private fun TelaNovoProjeto(
    nomeProjeto: String,
    onNomeProjetoChange: (String) -> Unit,
    nomeCliente: String,
    onNomeClienteChange: (String) -> Unit,
    tipoOperacao: String,
    onTipoOperacaoChange: (String) -> Unit,
    uf: String,
    onUfChange: (String) -> Unit,
    cidade: String,
    onCidadeChange: (String) -> Unit,
    onAvancar: () -> Unit
) {
    val tipos = listOf(
        Pair("terreno_construcao", "Terreno + Construção"),
        Pair("construcao", "Apenas Construção"),
        Pair("terreno", "Apenas Compra de Terreno"),
        Pair("reforma_pf", "Reforma / Ampliação PF"),
        Pair("condominio", "Crédito Condominial")
    )

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
                    text = "Identificação da Operação",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Nome do Projeto / Imóvel", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = nomeProjeto,
                    onValueChange = onNomeProjetoChange,
                    placeholder = { Text("Ex: Residência Morumbi", color = SleekGray400, fontSize = 13.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("input_nome_projeto")
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "Nome do Cliente / Mutuário", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = nomeCliente,
                    onValueChange = onNomeClienteChange,
                    placeholder = { Text("Ex: Carlos Eduardo de Oliveira", color = SleekGray400, fontSize = 13.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("input_nome_cliente")
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Tipo de Operação de Crédito", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                Spacer(modifier = Modifier.height(8.dp))

                // 5 Cards Selecionáveis
                tipos.forEach { (key, label) ->
                    val isSelected = tipoOperacao == key
                    Surface(
                        color = if (isSelected) Copper.copy(alpha = 0.08f) else Color.White,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) Copper else SleekBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onTipoOperacaoChange(key) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, if (isSelected) Copper else SleekGray400, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(Copper)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = label,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Copper else SleekTextBody
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "UF", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = uf,
                            onValueChange = onUfChange,
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(2.5f)) {
                        Text(text = "Cidade", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = cidade,
                            onValueChange = onCidadeChange,
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onAvancar,
                    colors = ButtonDefaults.buttonColors(containerColor = Copper),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_iniciar_projeto")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Iniciar projeto",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Avançar",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(96.dp))
    }
}

// -------------------------------------------------------------
// TELA 3: DADOS PARA CÁLCULO
// -------------------------------------------------------------
@Composable
private fun TelaDadosCalculo(
    tipoOperacao: String,
    idadeCliente: String,
    onIdadeChange: (String) -> Unit,
    rendaMensal: String,
    onRendaChange: (String) -> Unit,
    valorTerreno: String,
    onValorTerrenoChange: (String) -> Unit,
    areaConstruida: String,
    onAreaChange: (String) -> Unit,
    padraoCUB: String,
    onPadraoCUBChange: (String) -> Unit,
    valorFinanciar: String,
    onValorFinanciarChange: (String) -> Unit,
    onCalcular: () -> Unit
) {
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
                    text = "Parâmetros de Engenharia & Financiamento",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NavyDark
                )
                Text(
                    text = "Dados técnicos para enquadramento nos sistemas SFH e SFI",
                    fontSize = 11.sp,
                    color = SleekGray500
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Idade do Mutuário", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = idadeCliente,
                            onValueChange = onIdadeChange,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(2f)) {
                        Text(text = "Renda Familiar Bruta (R$)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = rendaMensal,
                            onValueChange = onRendaChange,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (tipoOperacao.contains("terreno")) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "Valor Estimado do Terreno (R$)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = valorTerreno,
                        onValueChange = onValorTerrenoChange,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Área Construída (m²)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = areaConstruida,
                            onValueChange = onAreaChange,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1.5f)) {
                        Text(text = "Padrão CUB", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("Baixo", "Normal", "Alto").forEach { padrao ->
                                val sel = padraoCUB == padrao
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (sel) Copper else SleekIconBg)
                                        .clickable { onPadraoCUBChange(padrao) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = padrao,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (sel) Color.White else SleekTextBody
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "Valor Desejado a Financiar (R$)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = NavyDark)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = valorFinanciar,
                    onValueChange = onValorFinanciarChange,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().testTag("input_valor_financiar")
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onCalcular,
                    colors = ButtonDefaults.buttonColors(containerColor = Copper),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_calcular_viabilidade")
                ) {
                    Text(
                        text = "Calcular Viabilidade",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(96.dp))
    }
}

// -------------------------------------------------------------
// TELA 4: RESULTADO DE VIABILIDADE BANCÁRIA
// -------------------------------------------------------------
@Composable
private fun TelaResultadoViabilidade(
    nomeProjeto: String,
    nomeCliente: String,
    resultados: List<DadosMock.ResultadoElegibilidadeBanco>,
    onSalvarVoltar: () -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Resumo do Estudo
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NavyDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "ESTUDO PRELIMINAR DE CRÉDITO",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Amber
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = nomeProjeto,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Proponente: $nomeCliente",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        item {
            Text(
                text = "ENQUADRAMENTO BANCÁRIO (6 INSTITUIÇÕES)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = SleekGray400
            )
        }

        // Lista de Bancos (ordenada: elegíveis primeiro)
        items(resultados) { item ->
            val banco = item.banco
            val isElegivel = item.elegivel

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isElegivel) Color(0xFF34D399) else SleekBorder
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = banco.banco,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = banco.produto,
                                fontSize = 11.sp,
                                color = SleekGray500
                            )
                        }

                        // Badge Elegível / Fora do Perfil
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isElegivel) Color(0xFFDEF7EC) else Color(0xFFFDE8E8))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isElegivel) Icons.Default.CheckCircle else Icons.Default.Error,
                                    contentDescription = null,
                                    tint = if (isElegivel) Color(0xFF03543F) else Color(0xFF9B1C1C),
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isElegivel) "Elegível" else "Fora do perfil",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isElegivel) Color(0xFF03543F) else Color(0xFF9B1C1C)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Taxa Estimada", fontSize = 10.sp, color = SleekGray400)
                            Text(
                                text = "${banco.taxaMin}% a ${banco.taxaMax}% a.a.",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        }
                        Column {
                            Text(text = "Prazo Máximo", fontSize = 10.sp, color = SleekGray400)
                            Text(
                                text = "${banco.prazoMaxAnos} anos (${banco.prazoMaxAnos * 12}x)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = NavyDark
                            )
                        }
                        Column {
                            Text(text = "Parcela Inicial", fontSize = 10.sp, color = SleekGray400)
                            Text(
                                text = "R$ ${String.format("%,.0f", item.parcelaEstimada)}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isElegivel) Copper else Color(0xFF9B1C1C)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Motivo / Parecer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isElegivel) Color(0xFFF0FDF4) else Color(0xFFFFF1F2))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = item.motivo,
                            fontSize = 11.sp,
                            color = if (isElegivel) Color(0xFF166534) else Color(0xFF991B1B),
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Aviso Legal Obrigatório
        item {
            Surface(
                color = Color(0xFFFEF3C7).copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Aviso Legal",
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Simulação meramente informativa sujeita a análise de crédito e engenharia pelo banco operador.",
                        fontSize = 11.sp,
                        color = Color(0xFF92400E),
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Ações: Baixar Estudo e Salvar
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Download do Estudo Completo de Viabilidade (PDF) iniciado.",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_baixar_estudo_pdf")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = NavyDark,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Baixar Estudo Completo (PDF)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = NavyDark
                        )
                    }
                }

                Button(
                    onClick = onSalvarVoltar,
                    colors = ButtonDefaults.buttonColors(containerColor = Copper),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_salvar_voltar_projetos")
                ) {
                    Text(
                        text = "Salvar e voltar aos projetos",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
