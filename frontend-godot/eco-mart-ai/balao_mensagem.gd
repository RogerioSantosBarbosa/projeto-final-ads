extends MarginContainer

@onready var label_texto = $PanelContainer/MarginContainer/Label
@onready var painel = $PanelContainer

func configurar(texto: String, e_usuario: bool):
	label_texto.text = texto
	
	# Criar um estilo com bordas arredondadas
	var estilo = StyleBoxFlat.new()
	estilo.corner_radius_top_left = 15
	estilo.corner_radius_top_right = 15
	estilo.corner_radius_bottom_left = 15
	estilo.corner_radius_bottom_right = 15
	
	if e_usuario:
		estilo.bg_color = Color("85c88a") # Verde do seu app
		# Empurra o balão para a direita
		size_flags_horizontal = SIZE_SHRINK_END 
	else:
		estilo.bg_color = Color("e0e0e0") # Cinza clarinho
		label_texto.add_theme_color_override("font_color", Color("333333"))
		# Empurra o balão para a esquerda
		size_flags_horizontal = SIZE_SHRINK_BEGIN 
		
	# Aplica o estilo ao fundo do balão
	painel.add_theme_stylebox_override("panel", estilo)
