extends Control

# Carrega o molde do card individual
var cena_card = preload("res://card_carrinho.tscn")

# Pega a referência da sua lista vertical onde os cards vão brotar
@onready var lista_cards = $VBoxContainer/ScrollContainer/MarginContainer/ListaCards

@onready var btn_voltar = $VBoxContainer/PanelContainer/HBoxContainer/Voltar
@onready var btn_limpar = $VBoxContainer/MarginContainer3/BtnLimpar
@onready var btn_finalizar = $VBoxContainer/MarginContainer2/BtnFinalizar
@onready var lbl_total = $VBoxContainer/MarginContainer4/LabelTotal

func _ready():
	# 1. Desenha os produtos na tela assim que ela abre
	exibir_itens_do_carrinho()
	
	# 2. Conecta os botões principais da tela
	if btn_voltar: btn_voltar.pressed.connect(_on_btn_voltar_pressed)
	if btn_limpar: btn_limpar.pressed.connect(_on_btn_limpar_pressed)
	if btn_finalizar: btn_finalizar.pressed.connect(_on_btn_finalizar_pressed)

func exibir_itens_do_carrinho():
	# Limpa resquícios visuais antigos
	for filho in lista_cards.get_children():
		filho.queue_free()
		
	# Se o carrinho estiver vazio, esconde/desativa os botões e o total
	if GerenciadorCarrinho.itens_no_carrinho.size() == 0:
		btn_limpar.disabled = true
		btn_limpar.visible = false
		btn_finalizar.disabled = true   # <-- Trava o botão de finalizar
		btn_finalizar.visible = false   # <-- Esconde o botão de finalizar
		lbl_total.visible = false
	else:
		btn_limpar.disabled = false
		btn_limpar.visible = true
		btn_finalizar.disabled = false  # <-- Destrava o botão
		btn_finalizar.visible = true    # <-- Mostra o botão
		lbl_total.visible = true
		
	# Cria os novos cards atualizados
	for item in GerenciadorCarrinho.itens_no_carrinho:
		var novo_card = cena_card.instantiate()
		lista_cards.add_child(novo_card)
		novo_card.configurar(item["nome"], item["preco"], item["quantidade"])
		novo_card.quantidade_alterada.connect(exibir_itens_do_carrinho)

	# --- NOVIDADE AQUI: Atualiza o texto do Total ---
	var valor_final = GerenciadorCarrinho.obter_valor_total()
	lbl_total.text = "Total: R$ %.2f" % valor_final

# --- FUNÇÕES DOS BOTÕES ---

func _on_btn_voltar_pressed():
	get_tree().change_scene_to_file("res://tela_inicial.tscn")

func _on_btn_limpar_pressed():
	GerenciadorCarrinho.itens_no_carrinho.clear()
	print("Carrinho limpo!")
	exibir_itens_do_carrinho() # Atualiza a tela para apagar os cards visualmente

func _on_btn_finalizar_pressed():
	# Pede para o gerenciador disparar a requisição pro Java
	GerenciadorCarrinho.finalizar_compra()
	print("Enviando pedido para o servidor...")
	
	# Em vez de voltar pra tela inicial, vamos para a tela de Sucesso!
	get_tree().change_scene_to_file("res://tela_sucesso.tscn")
