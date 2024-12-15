import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Nim_Game extends JFrame {
    JLabel[] palos; // Array de JLabels para los palos
    boolean[] seleccionados; // Array para verificar si un palo está seleccionado
    int jugadorActual; // 1 para jugador 1, 2 para jugador 2
    JButton botonRecoger; // Botón para recoger los palos
    JButton botonReiniciar; // Botón para reiniciar el juego
    JButton botonModo; // Botón para cambiar entre modos de juego
    int lineaSeleccionada; // Línea actualmente seleccionada (1, 2 o 3)
    JLabel lblTurno; // JLabel para mostrar el turno del jugador
    boolean modoDosJugadores; // true para modo dos jugadores, false para contra la máquina
    boolean esTurnoMaquina; // true si es el turno de la máquina, false si es el turno del jugador
    boolean recogido = false; // true si la maquina ha recogido palos, false si no ha recogido los palos
    int delay = 2000; // Tiempo de espera de la maquina en milisegundos

    public Nim_Game() {
        // Configuración de la ventana
        setTitle("Nim Game");
        setSize(800, 800); // Tamaño de la ventana
        setResizable(false); // No redimensionable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar al cerrar la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Establecer el fondo con una imagen
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //ImageIcon fondo = new ImageIcon("C:/Users/David/eclipse-workspace/JuegoDeLos15Palos/imagenes/fondo.jpg");
                //ImageIcon fondo = new ImageIcon("/home/alumn0/eclipse-workspace/Juego15Palos/imagenes/fondo.jpg");
                ImageIcon fondo = new ImageIcon(getClass().getResource("/imagenes/fondo.jpg"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Configurar el panelFondo
        panelFondo.setLayout(null); // Usar null layout para setBounds
        setContentPane(panelFondo);

        // Cargar imagen de informacion
        //ImageIcon iconoOriginal = new ImageIcon("C:/Users/David/eclipse-workspace/JuegoDeLos15Palos/imagenes/info.png");
        //ImageIcon iconoOriginal = new ImageIcon("/home/alumn0/eclipse-workspace/Juego15Palos/imagenes/info.png");
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/info.png"));
        Image imagenOriginal = iconoOriginal.getImage(); 
        Image imagenReescalada = imagenOriginal.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Reescalar la imagen al tamaño deseado
        ImageIcon iconoReescalado = new ImageIcon(imagenReescalada); // Crear un nuevo ImageIcon con la imagen reescalada
        // Crear un JLabel para mostrar la imagen de informacion
        JLabel ir = new JLabel(iconoReescalado);
        ir.setBounds(650, 60, 60, 60);
        panelFondo.add(ir);
        // Agregar un MouseListener al JLabel para detectar clics
        ir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mostrar ventana de información al hacer clic en la imagen
                JOptionPane.showMessageDialog(null, "- Juego por turnos, primero Jugador 1 y despues Jugador 2.\n- En tu turno puedes recoger la cantidad de palos que quieras\nde una linea, despues le toca el turno al otro jugador.\n- El que quite el ultimo palo de todos pierde.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Cargar la imagen del palo y aumentar su tamaño
        //ImageIcon imagenPalo = new ImageIcon("C:/Users/David/eclipse-workspace/JuegoDeLos15Palos/imagenes/palo.png");
        //ImageIcon imagenPalo = new ImageIcon("/home/alumn0/eclipse-workspace/Juego15Palos/imagenes/palo.png");
        ImageIcon imagenPalo = new ImageIcon(getClass().getResource("/imagenes/palo.png"));
        Image img = imagenPalo.getImage();
        img = img.getScaledInstance(imagenPalo.getIconWidth() * 2, imagenPalo.getIconHeight() * 2, Image.SCALE_SMOOTH);
        imagenPalo = new ImageIcon(img);

        // Inicializar el array de JLabel para los palos y los seleccionados
        palos = new JLabel[15];
        seleccionados = new boolean[15];

        // Añadir los palos
        int index = 0;

        // Posicionar los 3 palos en la parte superior (Línea 1)
        for (int i = 0; i < 3; i++) {
            palos[index] = crearPalo(imagenPalo, index, 1);
            palos[index].setBounds(300 + (i * 100), 50, imagenPalo.getIconWidth(), imagenPalo.getIconHeight());
            panelFondo.add(palos[index]);
            index++;
        }

        // Posicionar los 5 palos en la parte central (Línea 2)
        for (int i = 0; i < 5; i++) {
            palos[index] = crearPalo(imagenPalo, index, 2);
            palos[index].setBounds(200 + (i * 100), 250, imagenPalo.getIconWidth(), imagenPalo.getIconHeight());
            panelFondo.add(palos[index]);
            index++;
        }

        // Posicionar los 7 palos en la parte inferior (Línea 3)
        for (int i = 0; i < 7; i++) {
            palos[index] = crearPalo(imagenPalo, index, 3);
            palos[index].setBounds(100 + (i * 100), 450, imagenPalo.getIconWidth(), imagenPalo.getIconHeight());
            panelFondo.add(palos[index]);
            index++;
        }

        // Inicializar el JLabel para mostrar el turno
        lblTurno = new JLabel("Jugador 1");
        lblTurno.setBounds(350, 5, 300, 40); // Ajustar posición y tamaño
        lblTurno.setFont(new Font("Arial", Font.BOLD, 24)); // Configurar la fuente a un tamaño más grande
        lblTurno.setForeground(Color.BLACK); // Cambiar el color del texto si es necesario
        panelFondo.add(lblTurno); // Añadir el JLabel al panel

        // Inicializar el botón "Recoger"
        botonRecoger = new JButton("Recoger");
        botonRecoger.setBounds(350, 650, 100, 30);
        botonRecoger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recogerPalos();
            }
        });
        panelFondo.add(botonRecoger);

        // Inicializar el botón "Reiniciar"
        botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.setBounds(50, 50, 100, 30); // Colocar al lado derecho del botón "Recoger"
        botonReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
        panelFondo.add(botonReiniciar);

        // Inicializar el botón para cambiar modo
        botonModo = new JButton("Modo: 2 Jugadores");
        botonModo.setBounds(50, 100, 200, 30); // Debajo del botón de reiniciar
        botonModo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarModo();
            }
        });
        panelFondo.add(botonModo);

        // Comenzar con el jugador 1 en modo dos jugadores
        jugadorActual = 1;
        lineaSeleccionada = 0; // Ninguna línea seleccionada al principio
        modoDosJugadores = true; // Comenzar en modo dos jugadores
        esTurnoMaquina = false; // Comienza en el turno del jugador
    }

    private JLabel crearPalo(ImageIcon imagenPalo, int index, int linea) {
        JLabel palo = new JLabel(imagenPalo);
        palo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarPalo(index, linea);
            }
        });
        return palo;
    }

    private void seleccionarPalo(int index, int linea) {
        // Si es el turno de la máquina, no permitir la selección
        if (esTurnoMaquina) {
            return;
        }

        // Si el palo ya está seleccionado, deseleccionarlo
        if (seleccionados[index]) {
            seleccionados[index] = false; // Marcar como no seleccionado
            palos[index].setBorder(null); // Quitar el borde de selección
        } else {
            // Si no hay línea seleccionada o se selecciona una línea diferente
            if (lineaSeleccionada == 0) {
                lineaSeleccionada = linea; // Establecer la línea seleccionada
            } else if (lineaSeleccionada != linea) {
                // Desmarcar los palos de la línea anterior
                for (int i = 0; i < seleccionados.length; i++) {
                    if (seleccionados[i]) {
                        seleccionados[i] = false;
                        palos[i].setBorder(null); // Quitar el borde de selección
                    }
                }
                lineaSeleccionada = linea; // Cambiar a la nueva línea seleccionada
            }

            // Marcar el palo como seleccionado
            seleccionados[index] = true;
            palos[index].setBorder(BorderFactory.createLineBorder(Color.RED, 3)); // Cambiar el borde para indicar selección solo si es el jugador
        }
    }

    private void recogerPalos() {
        // Contar cuántos palos están seleccionados
        int count = 0; // Contador de palos seleccionados
        for (boolean seleccionado : seleccionados) {
            if (seleccionado) {
                count++;
            }
        }
    
        // Verificar que se recogieron almenos 1 palo
        if (count < 1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar almenos 1 palo.");
            return; // Salir del método
        }
    
        // Verificar si se recoge el último palo
        int totalPalos = 0; // Contador de todos los palos visibles
        for (JLabel palo : palos) {
            if (palo.isVisible()) {
                totalPalos++;
            }
        }
        
        if (totalPalos - count == 0) {
            JOptionPane.showMessageDialog(this, "¡Jugador " + jugadorActual + ", has perdido al quitar el último palo!");
        }
    
        // Eliminar los palos seleccionados
        for (int i = 0; i < seleccionados.length; i++) {
            if (seleccionados[i]) {
                palos[i].setVisible(false);
                seleccionados[i] = false; // Reiniciar la selección
                palos[i].setBorder(null); // Quitar borde de selección
            }
        }
    
        // Cambiar de turno
        if (modoDosJugadores) {
            jugadorActual = (jugadorActual == 1) ? 2 : 1; // Alternar entre los jugadores
            lblTurno.setText("Jugador " + jugadorActual); // Actualizar el JLabel con el turno
        } else {
            // Lógica para el modo contra la máquina
            if (jugadorActual == 1) {
                jugadorActual = 2; // Cambiar al turno de la máquina
                esTurnoMaquina = true; // Indicar que es el turno de la máquina
                lblTurno.setText("Máquina"); // Mostrar que es el turno de la máquina
                habilitarControles(false); // Deshabilitar controles
    
                // Esperar 1 segundo antes de que la máquina haga su movimiento
                Timer timer = new Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        realizarMovimientoMaquina(); // Llama a un método para que la máquina haga su movimiento
                    }
                });
                timer.setRepeats(false); // Solo ejecutar una vez
                timer.start(); // Iniciar el temporizador
            } else {
                jugadorActual = 1; // Cambiar de vuelta al jugador
                esTurnoMaquina = false; // Indicar que es el turno del jugador
                lblTurno.setText("Jugador 1"); // Mostrar que es el turno del jugador
                habilitarControles(true); // Habilitar controles
            }
        }
    }
    
    private void habilitarControles(boolean habilitar) {
        botonRecoger.setEnabled(habilitar); // Habilitar o deshabilitar el botón "Recoger"
        botonReiniciar.setEnabled(habilitar); // Habilitar o deshabilitar el botón "Reiniciar"
        botonModo.setEnabled(habilitar); // Habilitar o deshabilitar el botón de modo
    }

    private void cambiarModo() {
        modoDosJugadores = !modoDosJugadores; // Alternar el modo
        if (modoDosJugadores) {
            botonModo.setText("Modo: 2 Jugadores");
        } else {
            botonModo.setText("Modo: Máquina");
        }
        reiniciarJuego(); // Reiniciar el juego al cambiar de modo
    }

    private void reiniciarJuego() {
        // Reiniciar el estado del juego
        for (int i = 0; i < palos.length; i++) {
            palos[i].setVisible(true); // Hacer visibles todos los palos
            seleccionados[i] = false; // Reiniciar selección
            palos[i].setBorder(null); // Quitar borde de selección
        }
        recogido = false; // Reiniciar el estado de recogido
        jugadorActual = 1; // Reiniciar el jugador actual
        lineaSeleccionada = 0; // Reiniciar línea seleccionada
        lblTurno.setText("Jugador " + jugadorActual); // Actualizar el JLabel con el turno
    }

    public static void main(String[] args) {
        // Crear la ventana y hacerla visible
        SwingUtilities.invokeLater(() -> {
            Nim_Game ventana = new Nim_Game();
            ventana.setVisible(true);
        });
    }

    private void realizarMovimientoMaquina() {

        int fila1 = 0, fila2 = 0, fila3 = 0;
    
        // Contar palos visibles por fila
        for (int i = 0; i < palos.length; i++) {
            if (palos[i].isVisible()) {
                if (i < 3) fila1++;
                else if (i < 8) fila2++;
                else fila3++;
            }
        }

        // ---------------------------------------------------------------------------------------------------------------- //
        // Calcular el XOR de los tres números
        int resultado = fila1 ^ fila2 ^ fila3;

        // Mostrar el resultado del XOR
        //System.out.println("El resultado del XOR es: " + resultado);

        // Si el resultado no es 0, modificar uno de los números para que el XOR sea 0
        if (resultado != 0) {
            // Intentar modificar el primer número
            int nuevoFila1 = fila1 ^ resultado;
            if (nuevoFila1 < fila1 && nuevoFila1 >= 0 && nuevoFila1 <= 3) {
                int recoger = fila1 - nuevoFila1;
                recogerPalosMaquina(1, recoger, false);
                return;
            }

            // Intentar modificar el segundo número
            int nuevoFila2 = fila2 ^ resultado;
            if (nuevoFila2 < fila2 && nuevoFila2 >= 0 && nuevoFila2 <= 5) {
                int recoger = fila2 - nuevoFila2;
                recogerPalosMaquina(2, recoger, false);
                return;
            }

            // Intentar modificar el tercer número
            int nuevoFila3 = fila3 ^ resultado;
            if (nuevoFila3 < fila3 && nuevoFila3 >= 0 && nuevoFila3 <= 7) {
                int recoger = fila3 - nuevoFila3;
                recogerPalosMaquina(3, recoger, false);
                return;
            }
        } else {
            // Si el resultado es 0, la maquina selecciona una fila al azar
            Random random = new Random();
            int fila = random.nextInt(3) + 1;

            while (!((fila == 1 && fila1 >= 1) || (fila == 2 && fila2 >= 1) || (fila == 3 && fila3 >= 1))) {
                fila = random.nextInt(3) + 1; // Genera un nuevo random hasta encontrar una fila válida
            }
            recogerPalosMaquina(fila, 1, true); // Recoger 1 palo de la fila seleccionada
        }
        // ---------------------------------------------------------------------------------------------------------------- //

        cambiarTurnoJugador();
    }

    public void recogerPalosMaquina(int fila, int recoger, boolean esRandom) {
        int palosRecogidos = 0; // Contador de palos recogidos
        int x = 0, y = 0; // Palos de fila X
        if (fila == 1) { x = 0; y = 3; } else if (fila == 2) { x = 3; y = 8; } else if (fila == 3) { x = 8; y = 15; }
        for (int i = x; i < y; i++) { // Verificar la fila X (índices x a y)
            if (palos[i].isVisible()) { // Si el palo está visible
                palos[i].setVisible(false); // Recoger el palo
                seleccionados[i] = false; // Reiniciar la selección
                palosRecogidos++; // Aumentar el contador
                if (palosRecogidos == recoger){
                    if (!esRandom) {
                        recogido = true;
                    }
                    break;
                }// Dejar de recoger si se recogieron los necesarios
            }
        }
        cambiarTurnoJugador();
    }

    public void cambiarTurnoJugador() {
        // Cambiar de turno al jugador
        jugadorActual = 1; // Cambiar de vuelta al jugador
        esTurnoMaquina = false; // Indicar que es el turno del jugador
        lblTurno.setText("Jugador 1"); // Actualizar el JLabel
        // Habilitar controles después del movimiento de la máquina
        habilitarControles(true);
    }
}

// ---------------------------------------------------------------------------------------------------------------- //
// Si es turno de la maquina y la mesa esta asi, la maquina gana con los de -
//       -              -    -              -         -
// 245, 246, 247, 256, 257, 312, 345, 346, 347, 354, 356, 357
// ---------------------------------------------------------------------------------------------------------------- //

// 110, 200, 223, 232, 313, 331, 122, 133, 144, 155, 303, 320, 111 // fila 1 (-1)
// 300, 210, 201, 323, 332, 222, 233, 244, 255, 211 // fila 1 (-2)
// 310, 301, 322, 333, 344, 355, 311 // fila 1 (-3)

// 020, 142, 241, 032, 043, 054, 212, 230, 340 // fila 2 (-1)
// 120, 021, 030, 143, 152, 251, 341, 042, 053, 240, 350, 121 // fila 2 (-2)
// 130, 031, 040, 153, 243, 342, 351, 052, 250, 131 // fila 2 (-3)
// 140, 041, 050, 253, 352, 242, 343, 141 // fila 2 (-4)
// 150, 051, 252, 353, 151 // fila 2 (-5)

// 101, 011, 002, 124, 214, 023, 034, 045, 056, 203, 304, 221, 146 // fila 3 (-1)
// 102, 012, 003, 125, 134, 215, 314, 024, 035, 046, 057, 204, 305, 112, 147, 156 // fila 3 (-2)
// 103, 013, 004, 126, 135, 216, 234, 315, 324, 025, 036, 047, 205, 306, 113, 157 // fila 3 (-3)
// 104, 014, 005, 127, 136, 217, 235, 316, 325, 026, 037, 206, 307, 224, 334, 114 // fila 3 (-4)
// 105, 015, 006, 137, 236, 317, 326, 027, 207, 225, 335, 115 // fila 3 (-5)
// 106, 016, 007, 237, 327, 226, 336, 116 // fila 3 (-6)
// 107, 017, 227, 337, 117 // fila 3 (-7)