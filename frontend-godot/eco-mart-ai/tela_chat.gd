extends Control

var cena_balao = preload("res://balao_mensagem.tscn")

# Criamos a variável vazia aqui
var requisicao_ia : HTTPRequest

func _ready():
	# 1. CRIANDO O NÓ VIA CÓDIGO
	requisicao_ia = HTTPRequest.new()
	add_child(requisicao_ia) # Adiciona o nó invisível na tela
	requisicao_ia.request_completed.connect(_on_requisicao_ia_completada)

	# 2. Configurações originais dos botões
	var btn_voltar = $VBoxContainer/PanelContainer/HBoxContainer/Voltar
	btn_voltar.pressed.connect(_on_btn_voltar_pressed)
	%Enviar.pressed.connect(_on_enviar_pressed)
	%LineEdit.text_submitted.connect(_on_texto_submetido)

func _on_btn_voltar_pressed():
	get_tree().change_scene_to_file("res://tela_inicial.tscn")

func _on_enviar_pressed():
	enviar_mensagem()

func _on_texto_submetido(novo_texto):
	enviar_mensagem()

func enviar_mensagem():
	var texto_digitado = %LineEdit.text.strip_edges()
	if texto_digitado == "":
		return
		
	%LineEdit.text = ""
	criar_balao(texto_digitado, true) # Cria o balão do usuário na tela

	# --- INÍCIO DA INTEGRAÇÃO ---
	
	# 1. Pegamos a dieta lá do arquivo Global da Duda
	var dieta_atual = ""
	if Global.preferencias_dieta.size() > 0:
		dieta_atual = Global.preferencias_dieta[0]
		
	# 2. Montamos o JSON com as chaves EXATAS do RecomendacaoRequestDTO do Java
	var dados_json = JSON.stringify({
		"produtosCarrinho": [texto_digitado],
		"objetivoDieta": dieta_atual
	})
	
	var headers = ["Content-Type: application/json"]
	var url_api = "http://localhost:8080/recomendacao" 
	
	requisicao_ia.request(url_api, headers, HTTPClient.METHOD_POST, dados_json)

func _on_requisicao_ia_completada(result, response_code, headers, body):
	if response_code == 200:
		var resposta_json = JSON.parse_string(body.get_string_from_utf8())
		
		# 3. Lemos a chave EXATA do RecomendacaoResponseDTO do Java
		if resposta_json and resposta_json.has("sugestaoReceita"):
			criar_balao(resposta_json["sugestaoReceita"], false)
		else:
			criar_balao("Recebi os dados, mas não encontrei a receita no formato esperado.", false)
	else:
		criar_balao("Servidor indisponível. Erro: " + str(response_code), false)

func criar_balao(texto: String, e_usuario: bool):
	var novo_balao = cena_balao.instantiate()
	%ListaMensagens.add_child(novo_balao)
	novo_balao.configurar(texto, e_usuario)
