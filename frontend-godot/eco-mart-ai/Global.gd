extends Node

# ---------------------------------------------------------
# 1. MOCK DO SERVIÇO DE USUÁRIOS (REST)
# ---------------------------------------------------------
var usuario_logado : bool = true
var preferencias_dieta : Array = ["hipercalórico"] 

# ---------------------------------------------------------
# 2. MOCK DO SERVIÇO DE CATÁLOGO E PEDIDOS (REST)
# ---------------------------------------------------------
var itens_no_carrinho : int = 0

# Simula uma requisição rápida (Síncrona)
func adicionar_ao_carrinho():
	itens_no_carrinho += 1
	print("REST OK: Produto adicionado. Total: ", itens_no_carrinho)

# ---------------------------------------------------------
# 3. MOCK DO SERVIÇO DE IA E RABBITMQ (ASSÍNCRONO)
# ---------------------------------------------------------
# Esta função simula a lentidão de uma fila de mensagens e da LLM
func enviar_mensagem_ia(mensagem: String) -> String:
	print("RABBITMQ: Mensagem enviada para a fila: ", mensagem)
	
	# O comando 'await' congela apenas esta função e cria um temporizador.
	# Isso simula perfeitamente o tempo (ex: 3 segundos) que o servidor 
	# real levaria para processar o ChatGPT/Gemini.
	await get_tree().create_timer(3.0).timeout
	
	print("RABBITMQ: Resposta da IA processada e recebida!")
	
	# Retorna a resposta falsa da IA para a tela de chat
	return "Com certeza! Aqui estão ótimas opções focadas em energia e sabor, com os melhores preços."
