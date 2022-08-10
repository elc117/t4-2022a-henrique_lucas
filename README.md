# Status: Trabalho em andamento

# Versões de Teste

	O arquivo desktop-1.0-Versao-Teste.jar, contém uma versão de teste para Desktop.
	O arquivo dist-html-Versao-Teste.zip, contém uma versão de teste para Navegador.
	
	
# Explicação do jogo

Neste jogo você controlará um arqueiro que precisa resgatar a princesa que foi levada para a torre pelo vilão.
Você precisa detruir a barreira que o impede você de chegar a torre. Para isto você pode utilizar seu arco e flecha, para disparar flechas contra a barricada precionando a tecla SETA PARA BAIXO. Além disso, você dispõe de 3 vidas e precisa desviar dos ataques do inimigo pulando, precionando a tecla SETA PARA CIMA, ou andando para os lados com as teclas SENTA PARA ESQUERDA ou DIREITA, cada ataque do inimigo, quando acertado em você,  subtrairá 1 de vida.
A barreira é composta por um status de vida de 100 que é exibido na parte superior da tela, cada flecha disparada contra a barreira subtrai o valor 5 do status da barreira. Você vence o jogo ao subtrair todos os 100 de status da barreia.
Você perde ao zerar o total de vidas disponível.

# Explicação de algumas Funções do jogo

  O Ataque do inimigo é executado à partir da função fire(), que cria uma coleção com a interação de ataques que serão executados. Os ataques serão disparados baseado na posição atual do personagem no momento em que o ataque é criado.
  xi = posição x de onde será disparado o ataque;
  yi = posição y de onde será disparado o ataque;
  xf = posição x de onde o personagem se encontra no inicio do ataque;
  yf = posição y de onde o personagem se encontra no inicio do ataque;
  
  ```
  private void fire() {
		fireState = false;
		Rectangle fireball = new Rectangle();
		fireball.x = chefe.x;
		fireball.y = chefe.y;
		fireballs.add(fireball);
		xi = chefe.x;
		yi = chefe.y;
		xf = personagem.x;
		yf = personagem.y;
		fireTime = TimeUtils.nanoTime();
}

  ```
  Uma vez tendo os parametros de posição inicial do ataque e posição final do ataque, foi implementado uma equação da reta que descreve esta trajetória, implementada em:
  
```
Iterator<Rectangle> iter = fireballs.iterator();
		while(iter.hasNext()) {
			Rectangle fireball = iter.next();
			fireball.y -= (yf-yi)/(xf-xi)*200 * Gdx.graphics.getDeltaTime();
			fireball.x -= 200 * Gdx.graphics.getDeltaTime();
			if(fireball.y < 54) {
				iter.remove();
				fogoSound.play();
				fireState = true;
			}
			if(fireball.overlaps(personagem)) {
				vida -= 1;
				iter.remove();
				fogoSound.play();
				fireState = true;
			}
		}
    
```
  
  Hà também um status chamado de fireState que é alternado entre true e false de modo que um novo ataque só aconteça após o ataques anterior ser finalizado.
  
A função de disparar flechas, muito semelhante a função do ataque do inimigo porém desta vez com deslocamente somente horizontal, utiliza de dois status, flechaState e flechaOk, de modo a o flechaState identifica que foi precionado o botão de disparar uma flecha e o flechaOk parmitir que uma proxima flecha só seja disparada após a flecha anterior atingir o alvo ou sair do cenário. Além disso hà um verificador baseado da posição horizontal do personagem que utiliza o status flechaPos para orientar o sentido da flecha baseado na posição horizontal que personagem estava no momento em que disparou a flecha.

```
if (Gdx.input.isKeyPressed(Keys.DOWN)) {
  flechaState = true;
}

if(flechaState == true) {
	flechaState = false;
	if (TimeUtils.nanoTime() - flechaTime > 10000000)
		atirar();
}

private void atirar() {
	if(flechaOk == true) {
		flechaOk = false;
		flechaState = false;
		Rectangle flecha = new  Rectangle();
		flecha.x = personagem.x;
		flecha.y = personagem.y + 40;
		flechas.add(flecha);
		flechaPos = ladoState;
		flechaTime = TimeUtils.nanoTime();
  }
  
  Iterator<Rectangle> iter1 = flechas.iterator();
	while(iter1.hasNext()) {
			Rectangle flecha = iter1.next();
			
			
			if(flechaPos == true){
				fogoSound.play(); //MUDAR PARA SOM da FLecha sendo lancada
				flechaImg = flechaRImg;
				flecha.x += 400 * Gdx.graphics.getDeltaTime();
			}else {
				fogoSound.play(); //MUDAR PARA SOM da FLecha sendo lancada
				flechaImg = flechaLImg;
				flecha.x -= 400 * Gdx.graphics.getDeltaTime();
			}
			if(flecha.overlaps(barreira)) {
				barreiraCount -= 5;
				iter1.remove();
				flechaOk = true;
				fogoSound.play(); //MUDAR PARA SOM de FLecha acertanodo alvo
			}
			if(flecha.x >= 800) {
				iter1.remove();
				fogoSound.play(); //MUDAR PARA SOM de FLecha acertanodo alvo
				flechaOk = true;
			}
			if(flecha.x <= 0) {
				iter1.remove();
				fogoSound.play(); //MUDAR PARA SOM de FLecha acertanodo alvo
				flechaOk = true;
			}
	}		
			
  
```

Por Fim a função jump() implementa uma espécie de pulo simplificado, com alcance máximo pré definido de 108 pixels que utiliza de um diagrama de estados implementado utilizando o status ascendente e a posição do personagem. Quando o status ascendente é igual a true significa que o desolamento vertical precisa ser adicionado quando false o deslocamento vertical precisa ser subtraido. Existem condições que impedem que o deslocamento ultrapasse 108px (limite máximo do pulo) e condições de parada da subtração da posição vertical quando atingido a linha do horizonte:

```
private void jump() {
		if(personagem.y < 108 && ascendente == true)
			personagem.y += 200 * Gdx.graphics.getDeltaTime();
		if(personagem.y >= 108 || ascendente == false) {
			ascendente = false;
			personagem.y -= 200 * Gdx.graphics.getDeltaTime();
		}
		if(personagem.y <= 54) {
			jumpState = false;
			ascendente = true;
		}
		jumpTime = TimeUtils.nanoTime();
}
```
  
  
# Fontes

###### Texturas:

[Fireball](https://pngset.com/download-free-png-cuntq)
[Elementos do Cenário](https://cainos.itch.io/pixel-art-platformer-village-props)
[Ataque de flecha](https://www.myinstants.com/pt/instant/flecha-2-98133/)
[Dano de flecha](https://www.myinstants.com/pt/instant/flecha-29117/)
[Ataque de fogo](https://www.myinstants.com/pt/instant/small-fireball-11092/)
[Dano de fogo](https://www.myinstants.com/pt/instant/fireballswoosh/)
[Música](https://www.youtube.com/watch?v=osXHdy2W25g&feature=youtu.be&ab_channel=MusiCat%E2%80%A2M%C3%9ASICASSEMCOPYRIGHT)
[Mago e arqueiro](http://pixelartmaker.com/offshoot/5680d58ebafdf15)
###### Sons:
