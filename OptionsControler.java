package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsControler implements Initializable {

    @FXML
    StackPane optionsPane;

    @FXML
    Slider pistolSpeedSlider;

    @FXML
    Slider demonSpeedSlider;

    @FXML
    Button upBtn;
    @FXML
    Button downBtn;
    @FXML
    Button leftBtn;
    @FXML
    Button rightBtn;
    @FXML
    Button fireBtn;

    int clickedBtn = -1;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pistolSpeedSlider.setMin(1);
        pistolSpeedSlider.setMax(13);
        pistolSpeedSlider.setValue(GameConfig.getInstance().getPistolSpeed());
        pistolSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GameConfig.getInstance().setPistolSpeed(newValue.doubleValue());
            }
        });

        demonSpeedSlider.setMin(0.17);
        demonSpeedSlider.setMax(2);
        demonSpeedSlider.setValue(GameConfig.getInstance().getDemonsSpeed());
        demonSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GameConfig.getInstance().setDemonsSpeed(newValue.doubleValue());
            }
        });

        upBtn.setText(GameConfig.getInstance().getUpKey().getCode().getName());
        downBtn.setText(GameConfig.getInstance().getDownKey().getCode().getName());
        leftBtn.setText(GameConfig.getInstance().getLeftKey().getCode().getName());
        rightBtn.setText(GameConfig.getInstance().getRightKey().getCode().getName());
        fireBtn.setText(GameConfig.getInstance().getFireKey().getCode().getName());

        optionsPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (clickedBtn){
                    case 0:
                        GameConfig.getInstance().getUpKey().setCode(event.getCode());
                        upBtn.setText(event.getCode().getName());
                        upBtn.setDisable(false);
                        break;
                    case 1:
                        GameConfig.getInstance().getDownKey().setCode(event.getCode());
                        downBtn.setText(event.getCode().getName());
                        downBtn.setDisable(false);
                        break;
                    case 2:
                        GameConfig.getInstance().getLeftKey().setCode(event.getCode());
                        leftBtn.setText(event.getCode().getName());
                        leftBtn.setDisable(false);
                        break;
                    case 3:
                        GameConfig.getInstance().getRightKey().setCode(event.getCode());
                        rightBtn.setText(event.getCode().getName());
                        rightBtn.setDisable(false);
                        break;
                    case 4:
                        GameConfig.getInstance().getFireKey().setCode(event.getCode());
                        fireBtn.setText(event.getCode().getName());
                        fireBtn.setDisable(false);
                        break;
                }
                clickedBtn = -1;
            }
        });
    }

    public void goToMenu(MouseEvent mouseEvent) {
        GameConfig.getInstance().saveChanges();
        Main.rootControler.goToMenuScene();
    }

    public void defineUpBtn(ActionEvent actionEvent) {
        clickedBtn = 0;
        upBtn.setDisable(true);
    }

    public void defineDownBtn(ActionEvent actionEvent) {
        clickedBtn = 1;
        downBtn.setDisable(true);
    }

    public void defineLeftBtn(ActionEvent actionEvent) {
        clickedBtn = 2;
        leftBtn.setDisable(true);
    }

    public void defineRightBtn(ActionEvent actionEvent) {
        clickedBtn = 3;
        rightBtn.setDisable(true);
    }

    public void defineFireBtn(ActionEvent actionEvent) {
        clickedBtn = 4;
        fireBtn.setDisable(true);
    }
}
