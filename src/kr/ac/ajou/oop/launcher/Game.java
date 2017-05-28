package kr.ac.ajou.oop.launcher;

import kr.ac.ajou.oop.managers.FileManager;
import kr.ac.ajou.oop.panels.Code;
import kr.ac.ajou.oop.panels.Guidance;
import kr.ac.ajou.oop.panels.Input;
import kr.ac.ajou.oop.panels.Suggestion;
import kr.ac.ajou.oop.state.GameState;
import kr.ac.ajou.oop.state.State;
import kr.ac.ajou.oop.user.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game extends GameState {

    private JLabel lblName, lblScore, lblLevel;

    private User user;

    private Code code;
    private Suggestion suggestion;
    private Input input;
    private Guidance guidance;


    public Game() {
        JFrame frame;

        frame = new JFrame("OOP Education Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10, 10, 5,5));

        init();

        frame.add(lblName);
        frame.add(lblScore);
        frame.add(lblLevel);

        frame.add(code);
        frame.add(suggestion);
        frame.add(input);
        frame.add(guidance);

        frame.setSize(1280, 960);
        frame.setVisible(true);
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {
        switch(getID()) {
            case State.STATE_GAME_PLAY:

                break;
            case State.STATE_ANSWER_CORRECT:

                setID(State.STATE_NEXT_LEVEL);
                break;
            case State.STATE_ANSWER_INCORRECT:

                break;
            case State.STATE_HIGH_SCORE:

                setID(State.STATE_GAME_OVER);
                break;
            case State.STATE_NEXT_LEVEL:
                try {
                    prepareLevel();
                }catch(IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case State.STATE_GAME_OVER:
                gameOver();
                setID(State.STATE_EXIT);
                break;
            case State.STATE_EXIT:
                System.exit(1);
                break;
        }
    }

    @Override
    public void resetContent() {

    }

    private void init() {
        code = new Code();
        suggestion = new Suggestion();
        input = new Input();
        guidance = new Guidance();

        setUser(new User());

        lblName = new JLabel();
        lblScore = new JLabel();
        lblLevel = new JLabel();

        lblName.setText("Name: " + getUser().getName());
        lblScore.setText("Score: " + getUser().getScore());
        lblLevel.setText("Level: " + getUser().getLevel());

        try {
            FileManager.saveUser(getUser());
            prepareLevel();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        setID(State.STATE_GAME_INITIALIZE);

    }

    private void gameOver() {
        getUser().setGameOver(true);
    }

    private void prepareLevel() throws IOException, ClassNotFoundException {
        getCode().setCode(FileManager.loadAnswerCode(getUser().getLevel()));
        getGuidance().setHint(FileManager.loadGuidance(getUser().getLevel()));
    }

    private User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private Code getCode() { return code; }

    private Guidance getGuidance() { return guidance; }

    public static void main(String args[]) {
        new Game();
    }
//    Cannot call instance variable in a static method.
}
