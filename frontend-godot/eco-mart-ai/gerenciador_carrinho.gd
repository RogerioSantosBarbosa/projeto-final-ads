extends Node

var itens_no_carrinho = []
var requisicao_pedido : HTTPRequest

func _ready():
	# Cria o nó de requisição invisível via código
	requisicao_pedido = HTTPRequest.new()
	add_child(requisicao_pedido)
	requisicao_pedido.request_completed.connect(_on_pedido_enviado)

func adicionar_produto(nome_produto, preco_produto):
	for item in itens_no_carrinho:
		if item["nome"] == nome_produto:
			item["quantidade"] += 1
			return

	var novo_item = {
		"nome": nome_produto,
		"preco": preco_produto,
		"quantidade": 1
	}
	itens_no_carrinho.append(novo_item)

func obter_total_itens() -> int:
	var total = 0
	for item in itens_no_carrinho:
		total += item["quantidade"]
	return total

# --- INTEGRAÇÃO COM O BACKEND DE PEDIDOS ---
func finalizar_compra():
	if itens_no_carrinho.is_empty():
		print("Carrinho vazio!")
		return

	# Monta o JSON no exato formato que o Spring Boot espera
	var dados_pedido = JSON.stringify({
		"itens": itens_no_carrinho
	})

	var headers = ["Content-Type: application/json"]
	var url = "http://localhost:8082/pedidos"

	# Dispara o POST para o microsserviço de Pedidos
	requisicao_pedido.request(url, headers, HTTPClient.METHOD_POST, dados_pedido)

func _on_pedido_enviado(result, response_code, headers, body):
	# O Spring Boot geralmente retorna 200 (OK) ou 201 (Created)
	if response_code == 200 or response_code == 201:
		print("Pedido finalizado com sucesso! RabbitMQ foi acionado pelo Java.")
		itens_no_carrinho.clear() # Esvazia o carrinho no app
	else:
		print("Erro ao processar o pedido. Código do erro: ", response_code)
