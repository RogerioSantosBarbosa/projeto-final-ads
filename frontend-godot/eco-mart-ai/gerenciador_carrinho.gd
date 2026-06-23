extends Node

# Esta lista (Array) vai guardar os dicionários com os dados de cada produto
var itens_no_carrinho = []

func adicionar_produto(nome_produto, preco_produto):
	# Primeiro, checa se o produto já está no carrinho para apenas aumentar a quantidade
	for item in itens_no_carrinho:
		if item["nome"] == nome_produto:
			item["quantidade"] += 1
			print("Quantidade aumentada: ", nome_produto)
			return
			
	# Se não estava no carrinho, cria um novo registro
	var novo_item = {
		"nome": nome_produto,
		"preco": preco_produto,
		"quantidade": 1
	}
	itens_no_carrinho.append(novo_item)
	print("Novo produto adicionado: ", nome_produto)
	print("Total de itens no carrinho: ", itens_no_carrinho.size())
	
	
func obter_total_itens() -> int:
	var total = 0
	for item in itens_no_carrinho:
		total += item["quantidade"]
	return total
