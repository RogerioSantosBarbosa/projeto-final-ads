extends Node

var itens_no_carrinho = []
var requisicao_pedido : HTTPRequest

func _ready():
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

# FUNÇÃO ADICIONADA PARA O CRUD (+ e -) FUNCIONAR:
func modificar_quantidade(nome_produto: String, delta: int):
	for i in range(itens_no_carrinho.size()):
		if itens_no_carrinho[i]["nome"] == nome_produto:
			itens_no_carrinho[i]["quantidade"] += delta
			if itens_no_carrinho[i]["quantidade"] <= 0:
				itens_no_carrinho.remove_at(i)
			break

# --- INTEGRAÇÃO COM O BACKEND DE PEDIDOS ---
func finalizar_compra():
	if itens_no_carrinho.is_empty():
		print("Carrinho vazio!")
		return

	var dados_pedido = JSON.stringify({
		"itens": itens_no_carrinho
	})

	var headers = ["Content-Type: application/json"]
	var url = "http://localhost:8082/pedidos"

	requisicao_pedido.request(url, headers, HTTPClient.METHOD_POST, dados_pedido)

func _on_pedido_enviado(result, response_code, headers, body):
	if response_code == 200 or response_code == 201:
		print("Pedido finalizado com sucesso! RabbitMQ foi acionado pelo Java.")
		itens_no_carrinho.clear() 
	else:
		print("Erro ao processar o pedido. Código do erro: ", response_code)
		
# Calcula o valor total em dinheiro do carrinho
func obter_valor_total() -> float:
	var total_dinheiro = 0.0
	for item in itens_no_carrinho:
		total_dinheiro += item["preco"] * item["quantidade"]
	return total_dinheiro
