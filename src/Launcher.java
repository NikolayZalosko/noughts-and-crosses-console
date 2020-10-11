public class Launcher {
    public static void main(String[] args) {
        Game game = new Game();
        if (game.getPlayer2() == Game.Player.HUMAN) {
            game.battleWithHuman();
        } else {
            game.battleWithCpu();
        }
        game.showResults();
    }
}
