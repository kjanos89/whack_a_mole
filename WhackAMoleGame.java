import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WhackAMoleGame extends JFrame {
    private static final int NUM_MOLES = 9;
    private static final int GRID_SIZE = 3;
    private static final int MOLE_DURATION = 2000;

    private Mole[] cubes;
    private int points;

    private JLabel scoreLabel;

    public WhackAMoleGame() {
        setTitle("Whack-a-Mole");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        cubes = new Mole[NUM_MOLES];

        for (int i = 0; i < NUM_MOLES; i++) {
            cubes[i] = new Mole();
            gamePanel.add(cubes[i]);
        }

        add(gamePanel, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel(new FlowLayout());
        scoreLabel = new JLabel("Score: 0");
        scorePanel.add(scoreLabel);
        add(scorePanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
        startGame();
    }

    private void startGame() {
        points = 0;
        updateScoreLabel();

        Timer timer = new Timer(MOLE_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideMoles();
                showMole();
            }
        });
        timer.start();
    }

    private void hideMoles() {
        for (Mole mole : cubes) {
            mole.setVisible(false);
        }
    }

    private void showMole() {
        int randomIndex = (int) (Math.random() * NUM_MOLES);
        cubes[randomIndex].setVisible(true);

        Timer timer = new Timer(MOLE_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cubes[randomIndex].setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + points);
    }

    private class Mole extends JLabel {
        public Mole() {
            setOpaque(true);
            setBackground(Color.GRAY);
            setPreferredSize(new Dimension(100, 100));
            setVisible(false);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isVisible()) {
                        points++;
                        updateScoreLabel();
                        setVisible(false);
                    }
                }
            });
        }
    }
}
