package gameComponents.scenario;

import gameComponents.character.Player;
import gameComponents.essential.GameObject;
import graphics.GameLib;
import graphics.Util;

import java.awt.*;

public class Message extends GameObject {

    private final String message;
    private static final double width = 200;
    private static final double height = 100;

    public Message(String message) {
        super((double)Util.WIDTH / 2, (double) Util.HEIGHT / 2, Color.darkGray);
        this.message = message;
    }

    public void update(Player player) {
        if (player.getLife() > 0) {
            return;
        }
        //desenha o quadrado de fundo
        GameLib.setColor(this.getColor());
        GameLib.fillRect(this.getCoordinateX(), this.getCoordinateY(), width, height);

        //escreve a mensagem
        GameLib.setColor(Color.black);
        GameLib.setFont(new Font("Arial", Font.BOLD, 16));

        FontMetrics metrics = GameLib.getFontMetrics(GameLib.getFont());
        int textWidth = metrics.stringWidth(message);
        int textHeight = metrics.getHeight();

        int textX = (int) getCoordinateX() - textWidth / 2;
        int textY = (int) getCoordinateY() - textHeight / 2 + metrics.getAscent();

        GameLib.drawString(message, textX, textY);
    }

}
