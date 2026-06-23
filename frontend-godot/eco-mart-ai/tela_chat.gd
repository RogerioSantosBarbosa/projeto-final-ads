extends Control

# Carrega o "molde" do balão que criamos
var cena_balao = preload("res://balao_mensagem.tscn")

func _ready():
	# Configura o botão de voltar
	var btn_voltar = $VBoxContainer/PanelContainer/HBoxContainer/Voltar
	btn_voltar.pressed.connect(_on_btn_voltar_pressed)
	
	# Conecta o clique no botão enviar e a tecla 'Enter' do teclado
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
		return # Se estiver vazio, não faz nada
		
	%LineEdit.text = "" # Limpa a caixa de texto
	
	# 1. Cria a mensagem do usuário (verde, na direita)
	criar_balao(texto_digitado, true)
	
	# 2. Faz o Godot esperar 1 segundo para fingir que a IA está "pensando"
	await get_tree().create_timer(1.0).timeout
	
	# 3. Cria a resposta da IA (cinza, na esquerda)
	criar_balao("Entendido! Como uma IA do EcoMart, vou te ajudar a encontrar opções mais sustentáveis para isso.", false)

# Função que fabrica os balões na tela
func criar_balao(texto: String, e_usuario: bool):
	var novo_balao = cena_balao.instantiate()
	%ListaMensagens.add_child(novo_balao)
	novo_balao.configurar(texto, e_usuario)
