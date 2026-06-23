extends PanelContainer

# Criamos um sinal para avisar a tela principal que o carrinho mudou
signal quantidade_alterada

@onready var lbl_nome = $HBoxContainer/LabelNome
@onready var lbl_preco = $HBoxContainer/LabelPreco
@onready var lbl_qtd = $HBoxContainer/LabelQtd
@onready var btn_menos = $HBoxContainer/BtnMenos
@onready var btn_mais = $HBoxContainer/BtnMais

var nome_produto: String = ""

func _ready():
	# Conecta os cliques dos botões internos
	btn_menos.pressed.connect(_on_btn_menos_pressed)
	btn_mais.pressed.connect(_on_btn_mais_pressed)

func configurar(nome: String, preco: float, quantidade: int):
	nome_produto = nome
	lbl_nome.text = nome
	lbl_preco.text = "R$ %.2f" % preco
	lbl_qtd.text = "x" + str(quantidade)

func _on_btn_menos_pressed():
	GerenciadorCarrinho.modificar_quantidade(nome_produto, -1)
	quantidade_alterada.emit() # Avisa a tela do carrinho para atualizar

func _on_btn_mais_pressed():
	GerenciadorCarrinho.modificar_quantidade(nome_produto, 1)
	quantidade_alterada.emit() # Avisa a tela do carrinho para atualizar
