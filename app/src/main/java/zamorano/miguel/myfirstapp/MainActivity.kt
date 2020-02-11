package zamorano.miguel.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var guess: Int = 0
    var limInf: Int = 0
    var limSup: Int = 100
    var contador: Int = 0;
    var generar: Button? = null
    var numero: TextView? = null
    var info: TextView? = null
    var restart: TextView? = null
    var up: Button? = null
    var down: Button? = null
    var enJuego: Boolean = false

    // @Override layout -> xml
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generar = findViewById(R.id.generar)
        numero = findViewById(R.id.numero)
        info = findViewById(R.id.info)
        restart = findViewById(R.id.restart)

        up = findViewById(R.id.up)
        down = findViewById(R.id.down)

        up?.setOnClickListener(View.OnClickListener {
            // if para evitar que se presione up que en el límite superior
            if(limSup != guess && enJuego) {
                // se establece que el límite inferior será uno más que el intento de adivinar
                limInf = guess + 1
                // se actualiza el contador de intentos
                contador++
                update()
                // si nomás queda una posibillidad, se termina el juego
                if (limSup == limInf) terminar()

            }
        })

        down?.setOnClickListener(View.OnClickListener {
            // if para evitar que se presione down que en el límite inferior
            if(limInf != guess && enJuego) {
                // se establece que el límite inferior será uno menos que el intento de adivinar
                limSup = guess - 1
                // se actualiza el contador de intentos
                contador++
                update()
                // si nomás queda una posibilidad, se termina el juego
                if (limSup == limInf) terminar()
            }
        })

        // se le asigna el setup como listener
        restart?.setOnClickListener(View.OnClickListener {

            setup()

        })

        // le establecemos el listener de generar al botón
        generar?.setOnClickListener(listenerGenerar)
        // colocamos la preconfiguración
        setup()
    }

    /**
     * Método que actualiza el número que intenta adivinar la app y los textos (límites)
     *
     */
    fun update(): Unit {
        // se genera un nuevo valor, un intento de adivinar
        guess = (Math.random()*(limSup - limInf)).toInt() + limInf;
        // se cambia el número
        numero?.setText(guess.toString())
        // se actualizan los límites mostrados
        info?.setText("Entre " + limInf + " y " + limSup)

    }

    /**
     * Método que establece la preconfiguración del juego
     */
    fun setup(): Unit {
        // límite inferior
        limInf = 0
        // límite superior
        limSup = 100
        // se establece el contador de intentos en 1
        contador = 1

        // se cambia el texto a donde se muestra el número
        numero?.setText("presiona generar")
        // se establece el texto de los límites
        info?.setText("Entre " + limInf + " y " + limSup)
        // se le cambia el texto al botón
        generar?.setText("Generar")
        // se le establece el listener de generar al botón generar
        generar?.setOnClickListener(listenerGenerar)
        // aún no se inicia el juego
        enJuego = false
    }

    /**
     * Método que establece el fin del juego
     */
    fun terminar(): Unit {
        // muestra el mensaje de final de juego
        numero?.setText("Número adivinado: " + guess.toString() + " en " + contador + " intentos")
        // se le establece el texto a generar
        generar?.setText("Generar")
        // se le coloca el listener de generar número al botón
        generar?.setOnClickListener(listenerGenerar)

    }

    // lístener de generar: reinicia el juego, se genera un número y comienza el juego
    val listenerGenerar: View.OnClickListener? = View.OnClickListener {
        setup()
        // llamamos a update para generar un número
        update()
        // cambiamos la funcionalidad del botón generar, para parar cuando la app haya adivinado
        generar?.setText("Has adivinado")
        // se cambia de listener al que es in-game
        generar?.setOnClickListener(listenerJuego)
        // se arranca el juego
        enJuego = true

    }

    // listener de juego: establece el fin del juego (la app adivinó el número)
    val listenerJuego: View.OnClickListener? = View.OnClickListener {
        // finalizamos el juego
        terminar()
        enJuego = false
    }

}
