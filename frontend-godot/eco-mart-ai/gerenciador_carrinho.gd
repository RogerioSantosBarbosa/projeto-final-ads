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
	
# Altera a quantidade de um item (+1 ou -1). Se chegar a 0, deleta o item!
func modificar_quantidade(nome_produto: String, delta: int):
	for i in range(itens_no_carrinho.size()):
		if itens_no_carrinho[i]["nome"] == nome_produto:
			itens_no_carrinho[i]["quantidade"] += delta
			
			# Se a quantidade for 0 ou menos, remove o item do CRUD
			if itens_no_carrinho[i]["quantidade"] <= 0:
				itens_no_carrinho.remove_at(i)
			break

# Limpa o carrinho por completo
func limpar_todo_o_carrinho():
	itens_no_carrinho.clear()
