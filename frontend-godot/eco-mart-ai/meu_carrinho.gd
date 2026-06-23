extends Control

func _ready():
	# Configura o botão de voltar
	var btn_voltar = $VBoxContainer/PanelContainer/HBoxContainer/Voltar
	btn_voltar.pressed.connect(_on_btn_voltar_pressed)

func _on_btn_voltar_pressed():
	get_tree().change_scene_to_file("res://tela_inicial.tscn")
