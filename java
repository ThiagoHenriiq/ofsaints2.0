// configurações do jogo
const config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    backgroundColor: '#000000',
    physics: {
        default: 'arcade',
        arcade: {
            gravity: { y: 0 },
            debug: false
        }
    },
    scene: {
        preload: preload,
        create: create,
        update: update
    }
};

// inicia o jogo
const game = new Phaser.Game(config);

// variáveis do jogo
let player;
let cursors;
let bullets;
let bulletTime = 0;
let score = 0;
let scoreText;

// função para carregar os recursos do jogo
function preload ()
{
    this.load.image('ship', 'assets/ship.png');
    this.load.image('bullet', 'assets/bullet.png');
    this.load.image('enemy', 'assets/enemy.png');
}

// função para criar os elementos do jogo
function create ()
{
    // cria o jogador
    player = this.physics.add.sprite(400, 550, 'ship');
    player.setCollideWorldBounds(true);

    // cria os controles do jogador
    cursors = this.input.keyboard.createCursorKeys();

    // cria o grupo de balas
    bullets = this.physics.add.group({
        defaultKey: 'bullet',
        maxSize: 20
    });

    // cria o grupo de inimigos
    enemies = this.physics.add.group({
        key: 'enemy',
        repeat: 11,
        setXY: { x: 40, y: 40, stepX: 70 }
    });

    // adiciona física aos inimigos
    enemies.children.iterate(function (enemy) {
        enemy.setCollideWorldBounds(true);
        enemy.setBounce(1);
        enemy.setVelocity(Phaser.Math.Between(-200, 200), Phaser.Math.Between(-200, 200));
    });

    // cria a colisão entre as balas e os inimigos
    this.physics.add.overlap(bullets, enemies, hitEnemy, null, this);

    // cria o texto de pontuação
    scoreText = this.add.text(16, 16, 'Pontos: 0', { fontSize: '32px', fill: '#FFFFFF' });
}

// função para atualizar os elementos do jogo
function update ()
{
    // movimenta o jogador
    if (cursors.left.isDown) {
        player.setVelocityX(-400);
    } else if (cursors.right.isDown) {
       
