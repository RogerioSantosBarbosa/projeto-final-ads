extends Control

func _ready():
	
	# Faz o Godot esperar 3 segundos olhando para a tela verde
	await get_tree().create_timer(3.0).timeout
	
	# Volta para a tela principal (o carrinho já estará vazio graças ao Gerenciador)
	get_tree().change_scene_to_file("res://tela_inicial.tscn")
