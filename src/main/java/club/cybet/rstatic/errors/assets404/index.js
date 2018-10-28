(function(){

  // general
  var Game = {
    points: 0,
    medals: 0,
    time: 1000,
    hold: false,
    progress: progress,
    counter: document.querySelector('#record text'),
    colors: ['#573659', '#ad4375', '#fa7370', '#1d839c', '#2f506c']
  }

  Game.update = function(){
    switch (Game.points) {
      case Game.progress.max:
        spark(demo, 'game:win');
        break;
      case -1:
        spark(demo, 'game:lose');
        break;
      default:
        spark(demo, 'game:score');
                       }
    spark(demo, 'game:restart');
  }

  Game.restart = function(){
    clearTimeout(Game.timeout);
    Game.timeout = setTimeout(function(){
      spark(demo, 'game:ended');
    }, Game.time);

    Game.progress.value = Game.points;
    Game.counter.innerHTML = Game.medals;
  }

  Game.ended = function(){
    Game.hold = false;
    Game.time = 1000;
    Game.progress.value = Game.points = 0;
    demo.className = '';
    customstyles.innerHTML = '';
    Sound.stop();
  }

  Game.score = function(){
    demo.className = 'petting';
    Sound.play('purr');
  }

  Game.lose = function(){
    Game.hold = true;
    Game.time = Math.round( Sound.effects.sneeze.duration * 1000 );
    Game.points = 0;
    Game.medals = Game.medals > 0 ? Game.medals - 1: 0;
    demo.className = 'petting-head';
    Sound.stop();
    Sound.play('sneeze');
  }

  Game.win = function(){
    Game.hold = true;
    Game.time = Math.round( Sound.effects.happy.duration * 1000 );
    Game.medals++;
    demo.className = 'happy';
    customstyles.innerHTML = '#bg, #faces path { fill: '+ Game.colors[Math.floor(Math.random() * Game.colors.length)] +'; }';
    Sound.stop();
    Sound.play('happy');
  }

  Game.play = function(e){
    if ( Game.hold ) return;
    switch (e.detail) {
      case 'cface':
        Game.points = -1;
        break;
      default:
        Game.points++;
                    }
    spark(demo, 'game:update');
  }

  var Sound = {
    effects: {
      purr: new Audio('sounds/purr.mp3'),
      sneeze: new Audio('sounds/sneeze.mp3'),
      happy: new Audio('sounds/happy.mp3')
    }
  };

  Sound.play = function(item){
    Sound.effects[item].play();
  }

  Sound.stop = function(){
    for (var i in Sound.effects) {
      if (Sound.effects.hasOwnProperty(i)) {
        Sound.effects[i].pause();
        Sound.effects[i].currentTime = 0;
      }
    }
  }

  function Controller(){
    spark(demo, 'controller:pet', this.id);
  }

  cbody.addEventListener('click', Controller);
  cface.addEventListener('click', Controller);
  cbody.addEventListener('touchstart', Controller);
  cface.addEventListener('touchstart', Controller);

  demo.addEventListener('controller:pet', Game.play);

  demo.addEventListener('game:score', Game.score);
  demo.addEventListener('game:lose', Game.lose);
  demo.addEventListener('game:win', Game.win);
  demo.addEventListener('game:update', Game.update);
  demo.addEventListener('game:restart', Game.restart);
  demo.addEventListener('game:ended', Game.ended);

})();