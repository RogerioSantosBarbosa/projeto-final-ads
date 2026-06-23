extends Control

# Essa função roda assim que a tela inicial carrega
func _ready():
	# 1. Botão do Microfone (IA)
	var btn_microfone = $MenuInferior/MarginContainer/HBoxContainer/IA
	btn_microfone.pressed.connect(_on_btn_microfone_pressed)
	
	# 2. Botão de adicionar produto (Carne Moída)
	$ContainerPrincipal/Recomendações/ScrollContainer/ListaProdutos/CarProduto/VBoxContainer/GrupoTextos/Button.pressed.connect(_on_botao_adicionar_carne_pressed)
	
	# 3. Botão do carrinho superior direito
	var btn_carrinho_topo = $ContainerPrincipal/PanelContainer/Cabecalho/TextureButton
	btn_carrinho_topo.pressed.connect(_on_btn_carrinho_pressed)
	
	# 4. Botão do carrinho do menu inferior
	var btn_carrinho_inferior = $MenuInferior/MarginContainer/HBoxContainer/Carrinho
	btn_carrinho_inferior.pressed.connect(_on_btn_carrinho_pressed)
	
	# Atualiza o número assim que o jogo inicia
	atualizar_contador_visual()

func _on_btn_microfone_pressed():
	print("Indo para o Chat com IA...")
	get_tree().change_scene_to_file("res://tela_chat.tscn")

func _on_botao_adicionar_carne_pressed():
	GerenciadorCarrinho.adicionar_produto("Carne Moída 1kg", 38.90)
	# IMPORTANTE: Atualiza o balão numérico após adicionar o item!
	atualizar_contador_visual()

func _on_btn_carrinho_pressed():
	print("Abrindo o carrinho de compras...")
	get_tree().change_scene_to_file("res://meu_carrinho.tscn")

# Nova função responsável por atualizar a bolinha do carrinho
func atualizar_contador_visual():
	var total_itens = GerenciadorCarrinho.obter_total_itens()
	var label_contador = $ContainerPrincipal/PanelContainer/Cabecalho/TextureButton/Contador
	
	if total_itens > 0:
		label_contador.text = str(total_itens)
		label_contador.show() # Mostra a bolinha se houver itens
	else:
		label_contador.hide() # Esconde a bolinha se o carrinho estiver vazio
