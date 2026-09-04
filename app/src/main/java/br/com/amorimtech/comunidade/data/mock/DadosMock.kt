package br.com.amorimtech.comunidade.data.mock

import br.com.amorimtech.comunidade.data.model.AutorPublico
import br.com.amorimtech.comunidade.data.model.Comentario
import br.com.amorimtech.comunidade.data.model.Curso
import br.com.amorimtech.comunidade.data.model.MaterialItem
import br.com.amorimtech.comunidade.data.model.ModuloCurso
import br.com.amorimtech.comunidade.data.model.PerfilUsuario
import br.com.amorimtech.comunidade.data.model.Post
import br.com.amorimtech.comunidade.data.model.RespostaForum
import br.com.amorimtech.comunidade.data.model.TopicoForum

/**
 * DadosMock — Repositório de dados fixos em memória para a fase de casca visual.
 * NOTA: Todos os nomes, documentos e conteúdos técnicos são estritamente fictícios
 * e criados exclusivamente para demonstração da interface e navegação local.
 */
object DadosMock {

    val usuarioAtual = PerfilUsuario(
        id = "user_current_01",
        nomeCompleto = "Eng. Roberto Amorim",
        tituloProfissional = "Engenheiro Civil & Perito Judicial",
        creaCau = "CREA-SP 506.842/D",
        avatarUrl = null,
        nivelAtual = "Membro Specialist 4.0",
        pontosTotais = 3850
    )

    val autorRoberto = AutorPublico(
        id = usuarioAtual.id,
        nomeCompleto = usuarioAtual.nomeCompleto,
        tituloProfissional = usuarioAtual.tituloProfissional,
        avatarUrl = null
    )

    val autorMariana = AutorPublico(
        id = "autor_02",
        nomeCompleto = "Arqª. Mariana Vasconcelos",
        tituloProfissional = "Especialista em Desempenho NBR 15575",
        avatarUrl = null
    )

    val autorCarlos = AutorPublico(
        id = "autor_03",
        nomeCompleto = "Eng. Carlos Eduardo Ramos",
        tituloProfissional = "Inspetor de Estruturas de Concreto",
        avatarUrl = null
    )

    val autorBeatriz = AutorPublico(
        id = "autor_04",
        nomeCompleto = "Engª. Beatriz Albuquerque",
        tituloProfissional = "Consultora em Impermeabilização e Fachadas",
        avatarUrl = null
    )

    val autorFernando = AutorPublico(
        id = "autor_05",
        nomeCompleto = "Eng. Fernando Dias",
        tituloProfissional = "Avaliador Imobiliário & Membro IBAPE",
        avatarUrl = null
    )

    // LISTA DE POSTS (FEED)
    val posts = mutableListOf(
        Post(
            id = "post_01",
            autor = autorCarlos,
            tipo = "texto",
            tag = "Patologia",
            conteudo = "Caso clássico de lixiviação avançada com formação de estalagmites de carbonato de cálcio em subsolo de edifício residencial construído em 2012. O teste de esclerometria apontou fck remanescente de 18 MPa na face inferior da viga V-12. Alguém já utilizou cristalizantes por migração capilar nessa intensidade?",
            fotosUrls = listOf("mock_img_subsolo_concreto"),
            criadoEm = "2026-09-02T14:30:00Z",
            curtidas = 28,
            curtidoPeloUsuario = true,
            comentarios = listOf(
                Comentario(
                    id = "c_01",
                    autor = autorBeatriz,
                    texto = "Excelente registro, Carlos! Sim, recomendo usar argamassa polimérica osmótica após apicoamento mecânico e saturação sem empoçamento. Dá um resultado de estanqueidade excelente.",
                    criadoEm = "2026-09-02T15:10:00Z"
                ),
                Comentario(
                    id = "c_02",
                    autor = autorRoberto,
                    texto = "Importante verificar se há recalque diferencial da fundação adjacente ao reservatório inferior antes de fechar o diagnóstico.",
                    criadoEm = "2026-09-02T16:45:00Z"
                )
            )
        ),
        Post(
            id = "post_02",
            autor = autorMariana,
            tipo = "compartilhamento",
            tag = "NBR 15575/16747",
            conteudo = "Acabo de finalizar uma perícia de entrega de obra com base nos novos anexos de desempenho acústico entre unidades autônomas. Notamos divergência de mais de 7 dB em relação ao cálculo de projeto simplificado devido a condução por flancos nas tubulações hidrossanitárias sem isolamento resiliente.",
            fotosUrls = emptyList(),
            criadoEm = "2026-09-01T09:15:00Z",
            curtidas = 45,
            curtidoPeloUsuario = false,
            comentarios = listOf(
                Comentario(
                    id = "c_03",
                    autor = autorFernando,
                    texto = "Ponto crucial, Mariana. Os construtores ainda negligenciam as passagens de prumada no shaft. Isso quase sempre vira quesito em juízo.",
                    criadoEm = "2026-09-01T10:00:00Z"
                )
            )
        ),
        Post(
            id = "post_03",
            autor = autorRoberto,
            tipo = "texto",
            tag = "Negócios",
            conteudo = "Dica de honorários periciais: nunca envie uma proposta de honorários sem discriminar as horas estimadas para exame dos autos, vistoria presencial (in loco), diligências complementares e resposta aos quesitos das partes. Isso reduz as contestações pelo patrono em 70%.",
            fotosUrls = emptyList(),
            criadoEm = "2026-08-30T18:00:00Z",
            curtidas = 89,
            curtidoPeloUsuario = true,
            comentarios = listOf(
                Comentario(
                    id = "c_04",
                    autor = autorCarlos,
                    texto = "Uso exatamente a tabela referencial do IBAPE estruturada por horas técnicas. A aceitação pelos magistrados tem sido quase imediata.",
                    criadoEm = "2026-08-30T19:20:00Z"
                )
            )
        ),
        Post(
            id = "post_04",
            autor = autorBeatriz,
            tipo = "enquete",
            tag = "Perícias",
            conteudo = "Enquete rápida entre os colegas peritos e assistentes técnicos: Na avaliação de desplacamento cerâmico de fachada aerada, qual ensaio complementar vocês mais solicitam no laudo prévio?\n\n1. Ensaio de arrancamento com dinamômetro (NBR 13528)\n2. Termografia infravermelha com câmera calibrada\n3. Percussão acústica manual com haste telescópica\n4. Ensaio de permeabilidade de Karsten",
            fotosUrls = listOf("mock_img_fachada_inspecao"),
            criadoEm = "2026-08-28T11:40:00Z",
            curtidas = 63,
            curtidoPeloUsuario = false,
            comentarios = listOf(
                Comentario(
                    id = "c_05",
                    autor = autorMariana,
                    texto = "Termografia primeiro para mapeamento global térmico de descolamento, seguido de percussão e arrancamento nos pontos críticos.",
                    criadoEm = "2026-08-28T12:05:00Z"
                )
            )
        ),
        Post(
            id = "post_05",
            autor = autorFernando,
            tipo = "texto",
            tag = "Perícias",
            conteudo = "Compartilhando vitória jurídica: laudo pericial contundente com correlação fotográfica 360º acolhido integralmente pela 3ª Vara Cível em ação de nunciação de obra nova. A fundamentação no método comparativo direto de dados com saneamento amostral foi o diferencial.",
            fotosUrls = emptyList(),
            criadoEm = "2026-08-27T16:10:00Z",
            curtidas = 52,
            curtidoPeloUsuario = false,
            comentarios = emptyList()
        ),
        Post(
            id = "post_06",
            autor = autorRoberto,
            tipo = "texto",
            tag = "Patologia",
            conteudo = "Infiltração por ascensão capilar em rodapés de alvenaria estrutural: por que a simples pintura com manta líquida superficial é um dos maiores erros cometidos em manutenção predial? Veja os detalhes na seção de tópicos do nosso Fórum.",
            fotosUrls = emptyList(),
            criadoEm = "2026-08-25T14:00:00Z",
            curtidas = 34,
            curtidoPeloUsuario = true,
            comentarios = listOf(
                Comentario(
                    id = "c_06",
                    autor = autorBeatriz,
                    texto = "Confinar a umidade só faz ela subir ainda mais pelas fiadas superiores!",
                    criadoEm = "2026-08-25T14:35:00Z"
                )
            )
        )
    )

    // TÓPICOS DO FÓRUM
    val topicosForum = mutableListOf(
        TopicoForum(
            id = "topico_01",
            autor = autorCarlos,
            titulo = "Critérios de aceitação para fissuras térmicas em platibandas expostas",
            categoria = "Patologia",
            conteudo = "Prezados colegas, venho me deparando com frequência em inspeções com trincas a 45º nos cantos superiores de platibandas executadas em alvenaria convencional sem junta de dilatação vertical com a laje de cobertura. De acordo com a NBR 9575 e NBR 6118, qual a tolerância de abertura (w) que vocês consideram como limiar entre estado limite de serviço (ELS) e risco potencial de desplacamento com infiltração?",
            criadoEm = "2026-09-02T10:00:00Z",
            totalRespostas = 3
        ),
        TopicoForum(
            id = "topico_02",
            autor = autorMariana,
            titulo = "Aplicação prática da NBR 16747 na Inspeção Predial de condomínios antigos",
            categoria = "NBR 15575/16747",
            conteudo = "Como vocês têm estruturado a Matriz GUT (Gravidade, Urgência, Tendência) exigida no laudo de inspeção predial segundo a NBR 16747 em edifícios residenciais com mais de 30 anos sem projeto de 'as built' disponível? Quais parâmetros objetivos usam para justificar prioridade máxima?",
            criadoEm = "2026-09-01T14:20:00Z",
            totalRespostas = 3
        ),
        TopicoForum(
            id = "topico_03",
            autor = autorRoberto,
            titulo = "Fixação de honorários do perito judicial: petição de adiantamento de 50%",
            categoria = "Perícias",
            conteudo = "Colegas, em comarcas do interior do estado, alguns magistrados têm relutado em liberar o adiantamento de 50% dos honorários previsto no Art. 465, § 4º do CPC antes do início dos trabalhos técnicos. Gostaria de saber quais modelos de fundamentação com jurisprudência do STJ vocês têm utilizado para destravar o levantamento das custas periciais de diligência.",
            criadoEm = "2026-08-29T16:40:00Z",
            totalRespostas = 2
        ),
        TopicoForum(
            id = "topico_04",
            autor = autorFernando,
            titulo = "Estratégias de captação de clientes B2B para perícias preventivas",
            categoria = "Negócios",
            conteudo = "A maioria dos peritos só atua no contencioso judicial (após o litígio instaurado). No entanto, o mercado de laudos cautelares de vizinhança e vistorias de entrega de obra para construtoras tem ticket médio de R$ 8.000 a R$ 25.000 com ciclo de recebimento muito mais rápido. Quais canais de prospecção comercial ativa têm funcionado melhor para os seus escritórios?",
            criadoEm = "2026-08-26T11:00:00Z",
            totalRespostas = 2
        ),
        TopicoForum(
            id = "topico_05",
            autor = autorBeatriz,
            titulo = "Infiltração em poço de elevador: injeção de poliuretano flexível vs. resina acrílica",
            categoria = "Patologia",
            conteudo = "Temos um caso de lençol freático sazonal com pressão hidrostática negativa constante no fundo de poço de elevador de um edifício comercial. A injeção pontual de espuma rígida de poliuretano vedou temporariamente, mas a água migrou para a junta de concretagem da parede lateral. Qual sistema com resina estrutural ou acrílica hidroexpansiva vocês consideram com maior durabilidade para estanqueidade definitiva?",
            criadoEm = "2026-08-24T09:30:00Z",
            totalRespostas = 3
        )
    )

    // RESPOSTAS DO FÓRUM (Mapeadas por topicoId)
    val respostasForum = mutableMapOf(
        "topico_01" to mutableListOf(
            RespostaForum(
                id = "resp_01_01",
                topicoId = "topico_01",
                autor = autorMariana,
                texto = "Carlos, na minha prática adoto w <= 0,2 mm para ambientes externos de agressividade ambiental classe II. Acima de 0,3 mm já classifico como anomalia de gravidade moderada a alta, pois há penetração de CO2 acelerando a despassivação da armadura da cinta de amarração.",
                criadoEm = "2026-09-02T11:15:00Z"
            ),
            RespostaForum(
                id = "resp_01_02",
                topicoId = "topico_01",
                autor = autorBeatriz,
                texto = "Concordo plenamente com a Mariana. Além disso, se a platibanda não tiver pingadeira com bisel adequado, a umidade corre pela fissura por efeito Coanda. Sempre exijo junta elástica de poliuretano com mastique.",
                criadoEm = "2026-09-02T13:40:00Z"
            ),
            RespostaForum(
                id = "resp_01_03",
                topicoId = "topico_01",
                autor = autorRoberto,
                texto = "Nos laudos periciais para síndicos, coloco como recomendação técnica a criação de juntas de dilatação de alívio a cada 6 metros na alvenaria da platibanda para evitar reincidência cíclica.",
                criadoEm = "2026-09-02T15:20:00Z"
            )
        ),
        "topico_02" to mutableListOf(
            RespostaForum(
                id = "resp_02_01",
                topicoId = "topico_02",
                autor = autorCarlos,
                texto = "Quando não há as-built, faço vistoria visual sistemática por prumadas e subsolos. Para o 'G' (Gravidade), tudo que envolve risco iminente à saúde ou estabilidade (ex: laje de garagem com armadura exposta e corrosão com perda de seção) pontuo 5 automaticamente.",
                criadoEm = "2026-09-01T15:30:00Z"
            ),
            RespostaForum(
                id = "resp_02_02",
                topicoId = "topico_02",
                autor = autorFernando,
                texto = "Para a Tendência (T), considero o histórico de manutenção: se o condomínio não tem plano de manutenção documentado (NBR 5674), a tendência de degradação é exponencial, justificando nota 4 ou 5.",
                criadoEm = "2026-09-01T17:00:00Z"
            ),
            RespostaForum(
                id = "resp_02_03",
                topicoId = "topico_02",
                autor = autorRoberto,
                texto = "Temos uma planilha padronizada no acervo de Materiais aqui da comunidade exatamente com essa matriz calibrada para edifícios antigos. Vale muito a pena baixar!",
                criadoEm = "2026-09-01T18:40:00Z"
            )
        ),
        "topico_03" to mutableListOf(
            RespostaForum(
                id = "resp_03_01",
                topicoId = "topico_03",
                autor = autorFernando,
                texto = "Roberto, tenho anexado acórdãos do STJ (REsp 1.705.894) demonstrando que os 50% não constituem remuneração adiantada de mérito, mas sim adiantamento necessário para despesas operacionais da prova pericial. Tem funcionado em 95% dos despachos.",
                criadoEm = "2026-08-29T17:30:00Z"
            ),
            RespostaForum(
                id = "resp_03_02",
                topicoId = "topico_03",
                autor = autorMariana,
                texto = "Outra dica excelente: explicitar na proposta o custo de locação de equipamentos certificados (termohigrômetro, esclerômetro, decibelímetro homologado pelo Inmetro). Quando o juiz vê nota de locação ou certificação, ele defere imediatamente o depósito prévio.",
                criadoEm = "2026-08-29T19:00:00Z"
            )
        ),
        "topico_04" to mutableListOf(
            RespostaForum(
                id = "resp_04_01",
                topicoId = "topico_04",
                autor = autorRoberto,
                texto = "Para laudo cautelar de vizinhança, nossa melhor fonte são as construtoras médias que estão iniciando escavação e fundação. Entramos em contato diretamente com a diretoria de obras 30 dias antes do início do bate-estacas.",
                criadoEm = "2026-08-26T14:15:00Z"
            ),
            RespostaForum(
                id = "resp_04_02",
                topicoId = "topico_04",
                autor = autorCarlos,
                texto = "Também temos parceria com escritórios de advocacia imobiliária. Eles oferecem a vistoria prévia aos clientes investidores para resguardar patrimônio antes de reformas estruturais.",
                criadoEm = "2026-08-26T16:50:00Z"
            )
        ),
        "topico_05" to mutableListOf(
            RespostaForum(
                id = "resp_05_01",
                topicoId = "topico_05",
                autor = autorBeatriz,
                texto = "A resina de poliuretano pura só para o fluxo inicial de água corrente. A solução perene para junta fria é a injeção em duas fases: 1º poliuretano hidrofóbico flexível com 200% de elongação e 2º gel acrílico bicomponente para vedação micrométrica dos vazios capilares.",
                criadoEm = "2026-08-24T11:00:00Z"
            ),
            RespostaForum(
                id = "resp_05_02",
                topicoId = "topico_05",
                autor = autorCarlos,
                texto = "Perfeito, Beatriz. Já acompanhei obras em que fizeram apenas cristalização superficial e, com a pressão de 3 metros de coluna d'água no verão, a água estourou no piso ao lado das bombas de recalque.",
                criadoEm = "2026-08-24T14:20:00Z"
            ),
            RespostaForum(
                id = "resp_05_03",
                topicoId = "topico_05",
                autor = autorRoberto,
                texto = "Fundamental também instalar drenos de alívio temporários durante o processo de injeção dos bicos graxeiros para não sobrecarregar as paredes adjacentes.",
                criadoEm = "2026-08-24T17:10:00Z"
            )
        )
    )

    // CURSOS DISPONÍVEIS
    val cursos = mutableListOf(
        Curso(
            id = "curso_01",
            titulo = "Perícia Judicial e Assistência Técnica 4.0",
            descricao = "Formação executiva completa para engenheiros e arquitetos atuarem como Peritos do Juízo e Assistentes Técnicos em disputas judiciais de alta complexidade. Do cadastro nos tribunais até a elaboração e defesa do laudo pericial.",
            categoria = "Perícias",
            instrutorNome = "Prof. Eng. Roberto Amorim",
            instrutorQualificacao = "Mestre em Engenharia de Construção, Perito Judicial TJSP/TRF3",
            imagemCapaUrl = "cover_pericia_judicial",
            cargaHorariaCertificado = "40 horas",
            modulos = listOf(
                ModuloCurso(
                    id = "mod_01_01",
                    cursoId = "curso_01",
                    titulo = "1. Fundamentos Processuais do CPC para o Perito",
                    descricao = "Nomeação, escusa, impedimentos, fixação de honorários e prazos peremptórios segundo os Arts. 464 a 480 do Código de Processo Civil.",
                    duracaoEstimada = "1h 45min",
                    ordem = 1,
                    exigeAvaliacao = true,
                    travaProximoModulo = false,
                    videoConcluido = true,
                    avaliacaoAprovada = true
                ),
                ModuloCurso(
                    id = "mod_01_02",
                    cursoId = "curso_01",
                    titulo = "2. Planejamento da Diligência e Vistoria Técnica",
                    descricao = "Notificação prévia das partes, checklist de equipamentos, registro termográfico, amostragem e cadeia de custódia das evidências.",
                    duracaoEstimada = "2h 10min",
                    ordem = 2,
                    exigeAvaliacao = true,
                    travaProximoModulo = true,
                    videoConcluido = true,
                    avaliacaoAprovada = true
                ),
                ModuloCurso(
                    id = "mod_01_03",
                    cursoId = "curso_01",
                    titulo = "3. Redação do Laudo e Resposta Conclusiva aos Quesitos",
                    descricao = "Estrutura do laudo conforme NBR 13752. Como blindar sua conclusão técnica contra impugnações dos assistentes técnicos.",
                    duracaoEstimada = "3h 00min",
                    ordem = 3,
                    exigeAvaliacao = true,
                    travaProximoModulo = true,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                ),
                ModuloCurso(
                    id = "mod_01_04",
                    cursoId = "curso_01",
                    titulo = "4. Gestão Comercial de Honorários e Contratos",
                    descricao = "Técnicas de negociação, manifestação sobre impugnação de honorários e execução judicial de valores devidos.",
                    duracaoEstimada = "1h 30min",
                    ordem = 4,
                    exigeAvaliacao = false,
                    travaProximoModulo = false,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                )
            )
        ),
        Curso(
            id = "curso_02",
            titulo = "Diagnóstico Avançado de Patologias em Concreto e Alvenaria",
            descricao = "Métodos científicos e ensaios não destrutivos para identificar causas-raiz de fissuras, corrosão de armaduras, eflorescências e infiltrações graves em edificações.",
            categoria = "Patologia",
            instrutorNome = "Prof. Dr. Carlos Eduardo Ramos",
            instrutorQualificacao = "Doutor em Estruturas de Concreto (USP), Especialista em Ensaios Não Destrutivos",
            imagemCapaUrl = "cover_patologia_concreto",
            cargaHorariaCertificado = "32 horas",
            modulos = listOf(
                ModuloCurso(
                    id = "mod_02_01",
                    cursoId = "curso_02",
                    titulo = "1. Mecanismos de Degradação do Concreto Armado",
                    descricao = "Carbonatação, ataque por íons cloreto, reação álcali-agregado (RAA) e lixiviação: termodinâmica e velocidade de avanço.",
                    duracaoEstimada = "2h 15min",
                    ordem = 1,
                    exigeAvaliacao = true,
                    travaProximoModulo = false,
                    videoConcluido = true,
                    avaliacaoAprovada = true
                ),
                ModuloCurso(
                    id = "mod_02_02",
                    cursoId = "curso_02",
                    titulo = "2. Ensaios em Campo: Esclerometria, Ultrassom e Carbonatação",
                    descricao = "Procedimentos práticos com esclerômetro de Schmidt, ensaio de fenolftaleína e velocidade de pulso ultrassônico (VPU).",
                    duracaoEstimada = "2h 40min",
                    ordem = 2,
                    exigeAvaliacao = true,
                    travaProximoModulo = true,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                ),
                ModuloCurso(
                    id = "mod_02_03",
                    cursoId = "curso_02",
                    titulo = "3. Mapeamento de Fissuras e Diagnóstico Causal",
                    descricao = "Diferenciação entre fissuras térmicas, por retração plástica, sobrecarga estrutural e recalque diferencial de fundações.",
                    duracaoEstimada = "3h 10min",
                    ordem = 3,
                    exigeAvaliacao = true,
                    travaProximoModulo = true,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                ),
                ModuloCurso(
                    id = "mod_02_04",
                    cursoId = "curso_02",
                    titulo = "4. Projetos de Recuperação e Reforço Estrutural",
                    descricao = "Tratamento de armadura corroída, grauteamento, fibra de carbono e injeção estrutural de resina epóxi.",
                    duracaoEstimada = "2h 00min",
                    ordem = 4,
                    exigeAvaliacao = false,
                    travaProximoModulo = false,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                )
            )
        ),
        Curso(
            id = "curso_03",
            titulo = "Inspeção Predial na Prática com NBR 16747",
            descricao = "Como estruturar um negócio rentável de laudos de inspeção predial para condomínios residenciais e edifícios comerciais com conformidade total às normas vigentes.",
            categoria = "NBR 15575/16747",
            instrutorNome = "Arqª. Mariana Vasconcelos & Eng. Roberto Amorim",
            instrutorQualificacao = "Membros de comitês ABNT CB-002, Peritos de Desempenho",
            imagemCapaUrl = "cover_inspecao_nbr",
            cargaHorariaCertificado = "24 horas",
            modulos = listOf(
                ModuloCurso(
                    id = "mod_03_01",
                    cursoId = "curso_03",
                    titulo = "1. Escopo e Diretrizes da NBR 16747 e NBR 5674",
                    descricao = "Diferença entre vistoria, inspeção e perícia. Definição do nível de inspeção (1, 2 ou 3) e documentação inicial necessária.",
                    duracaoEstimada = "1h 50min",
                    ordem = 1,
                    exigeAvaliacao = true,
                    travaProximoModulo = false,
                    videoConcluido = true,
                    avaliacaoAprovada = true
                ),
                ModuloCurso(
                    id = "mod_03_02",
                    cursoId = "curso_03",
                    titulo = "2. Vistoria Sistemática por Subsistemas Construtivos",
                    descricao = "Checklist de estrutura, vedações, impermeabilização, instalações elétricas, hidrossanitárias, combate a incêndio e elevadores.",
                    duracaoEstimada = "2h 30min",
                    ordem = 2,
                    exigeAvaliacao = true,
                    travaProximoModulo = true,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                ),
                ModuloCurso(
                    id = "mod_03_03",
                    cursoId = "curso_03",
                    titulo = "3. Classificação das Anomalias e Elaboração da Matriz GUT",
                    descricao = "Anomalias endógenas, exógenas e funcionais. Ponderação da criticidade de risco e recomendações técnicas para o plano de manutenção.",
                    duracaoEstimada = "2h 15min",
                    ordem = 3,
                    exigeAvaliacao = true,
                    travaProximoModulo = true,
                    videoConcluido = false,
                    avaliacaoAprovada = false
                )
            )
        )
    )

    // MATERIAIS DO ACERVO
    val materiais = mutableListOf(
        MaterialItem(
            id = "mat_01",
            titulo = "Planilha Automática de Cálculo de Honorários Periciais (CPC/IBAPE)",
            categoria = "Planilhas",
            descricao = "Planilha paramétrica completa que calcula horas técnicas de vistoria, elaboração de laudo e quesitos complementares de acordo com o valor da causa e complexidade técnica.",
            formato = "XLSX",
            tamanho = "1.8 MB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/planilha_honorarios.xlsx",
            pago = false,
            valor = null,
            temAcesso = true
        ),
        MaterialItem(
            id = "mat_02",
            titulo = "Modelo Editável de Laudo Pericial de Engenharia (NBR 13752)",
            categoria = "Modelos de Laudo",
            descricao = "Template profissional em Word (.docx) com formatação ABNT, índice analítico, metodologia pericial, transcrição de quesitos e assinatura digital qualificada.",
            formato = "DOCX",
            tamanho = "3.2 MB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/modelo_laudo_nbr13752.docx",
            pago = false,
            valor = null,
            temAcesso = true
        ),
        MaterialItem(
            id = "mat_03",
            titulo = "Checklist Master de Inspeção Predial Condominial (NBR 16747)",
            categoria = "Checklists",
            descricao = "Roteiro de vistoria campo a campo para tablet ou celular cobrindo mais de 180 itens críticos de subsolo, barrilete, cobertura, fachadas e áreas de uso comum.",
            formato = "PDF",
            tamanho = "950 KB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/checklist_nbr16747.pdf",
            pago = false,
            valor = null,
            temAcesso = true
        ),
        MaterialItem(
            id = "mat_04",
            titulo = "E-book: Guia Prático de Fissuras em Alvenarias e Concreto",
            categoria = "E-books",
            descricao = "Manual ilustrado de 94 páginas com fotos reais de casos em alta definição, diagnóstico de aberturas, inclinações características e mapas de risco.",
            formato = "PDF",
            tamanho = "14.5 MB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/ebook_fissuras_alvenaria.pdf",
            pago = true,
            valor = 67.0,
            temAcesso = true
        ),
        MaterialItem(
            id = "mat_05",
            titulo = "Planilha de Depreciação de Imóveis pelo Método Ross-Heidecke",
            categoria = "Planilhas",
            descricao = "Ferramenta de cálculo automático do coeficiente de depreciação física e funcional com base no estado de conservação e vida útil de edificações urbanas.",
            formato = "XLSX",
            tamanho = "2.1 MB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/depreciacao_ross_heidecke.xlsx",
            pago = true,
            valor = 49.90,
            temAcesso = false // Usuário não comprou ainda
        ),
        MaterialItem(
            id = "mat_06",
            titulo = "Skill / System Prompt Claude: Assistente de Formulação de Quesitos Periciais",
            categoria = "Skills Claude",
            descricao = "Prompt avançado calibrado para gerar quesitos periciais estratégicos e elucidativos para assistentes técnicos com base na petição inicial e contestação da parte adversa.",
            formato = "TXT / MD",
            tamanho = "45 KB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/skill_claude_quesitos.txt",
            pago = false,
            valor = null,
            temAcesso = true
        ),
        MaterialItem(
            id = "mat_07",
            titulo = "Masterclass: Sustentação Oral e Defesa do Laudo em Audiência de Instrução",
            categoria = "Vídeos",
            descricao = "Vídeo gravado em estúdio com 1h 40min de simulação real de audiência judicial com esclarecimentos orais a juízes e promotores.",
            formato = "MP4",
            tamanho = "480 MB",
            urlArquivo = "https://vimeo.com/mock_amorimtech_audiencia",
            pago = true,
            valor = 97.0,
            temAcesso = false, // Pago sem acesso
            plataformaVideo = "vimeo"
        ),
        MaterialItem(
            id = "mat_08",
            titulo = "Kit Minutas Jurídicas de Notificação Cautelar Extrajudicial",
            categoria = "Outros",
            descricao = "Conjunto de 5 notificações prontas para embargo de obra vizinha, solicitação de documentos a construtoras e termo de vistoria cautelar amigável.",
            formato = "ZIP",
            tamanho = "4.6 MB",
            urlArquivo = "https://mock.amorimtech.comunidade/files/kit_notificacoes_extrajudiciais.zip",
            pago = false,
            valor = null,
            temAcesso = true
        )
    )
}
