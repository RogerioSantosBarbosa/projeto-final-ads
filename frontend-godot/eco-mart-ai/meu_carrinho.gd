extends Control

func _ready():
	# 1. COLE OS CAMINHOS AQUI 
	var btn_voltar = $COLE_AQUI_O_CAMINHO_DO_BOTAO_VOLTAR
	var btn_limpar = $COLE_AQUI_O_CAMINHO_DO_BOTAO_LIMPAR
	var btn_finalizar = $COLE_AQUI_O_CAMINHO_DO_BOTAO_FINALIZAR
	
	# 2. O código abaixo conecta os botões às funções
	if btn_voltar:
		btn_voltar.pressed.connect(_on_btn_voltar_pressed)
		
	if btn_limpar:
		btn_limpar.pressed.connect(_on_btn_limpar_pressed)
		
	if btn_finalizar:
		btn_finalizar.pressed.connect(_on_btn_finalizar_pressed)

# --- FUNÇÕES DOS BOTÕES ---

func _on_btn_voltar_pressed():
	get_tree().change_scene_to_file("res://tela_inicial.tscn")

func _on_btn_limpar_pressed():
	# Pede para o gerenciador esvaziar a lista
	GerenciadorCarrinho.itens_no_carrinho.clear()
	print("Carrinho limpo!")

func _on_btn_finalizar_pressed():
	# Pede para o gerenciador disparar a requisição pro Java
	GerenciadorCarrinho.finalizar_compra()
	print("Enviando pedido para o servidor...")
	
	# Faz o Godot esperar 1.5 segundos e volta pra tela inicial
	await get_tree().create_timer(1.5).timeout
	get_tree().change_scene_to_file("res://tela_inicial.tscn")
