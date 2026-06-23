extends Control

var cena_card = preload("res://card_carrinho.tscn")

@onready var lista_cards = $VBoxContainer/ScrollContainer/MarginContainer/ListaCards
@onready var btn_limpar = $VBoxContainer/MarginContainer3/BtnLimpar

func _ready():
	exibir_itens_do_carrinho()
	btn_limpar.pressed.connect(_on_btn_limpar_pressed)

func exibir_itens_do_carrinho():
	# Limpa a listagem visual antiga
	for filho in lista_cards.get_children():
		filho.queue_free()
		
	# Se o carrinho estiver vazio, desativa o botão de limpar
	if GerenciadorCarrinho.itens_no_carrinho.size() == 0:
		btn_limpar.disabled = true
	else:
		btn_limpar.disabled = false
		
	# Cria os novos cards atualizados
	for item in GerenciadorCarrinho.itens_no_carrinho:
		var novo_card = cena_card.instantiate()
		lista_cards.add_child(novo_card)
		novo_card.configurar(item["nome"], item["preco"], item["quantidade"])
		
		# IMPORTANTE: Escuta o sinal do card. Se o usuário clicar em + ou -, 
		# a tela inteira se redesenha sozinha!
		novo_card.quantidade_alterada.connect(exibir_itens_do_carrinho)

func _on_btn_limpar_pressed():
	GerenciadorCarrinho.limpar_todo_o_carrinho()
	exibir_itens_do_carrinho() # Atualiza a tela que agora vai ficar vazia
