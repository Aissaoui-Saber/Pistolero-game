package sample;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar{
    private Rectangle frontRectangle;
    private Rectangle backRectangle;
    private Group g;

    public HealthBar(int width){
        g = new Group();
        backRectangle = new Rectangle(width, 5);
        frontRectangle = new Rectangle(width, 5);
        g.getChildren().addAll(backRectangle,frontRectangle);
        frontRectangle.xProperty().bind(backRectangle.xProperty());
        frontRectangle.yProperty().bind(backRectangle.yProperty());
        backRectangle.setFill(Color.GRAY);
        frontRectangle.setFill(Color.TOMATO);
    }
    public void setHeight(int v){
        this.frontRectangle.setHeight(v);
        this.backRectangle.setHeight(v);
    }
    public void setWidth(int v){
        this.frontRectangle.setWidth(v);
        this.backRectangle.setWidth(v);
    }
    public void setValue(double value){
        if (value >= 100){
            this.frontRectangle.setWidth(this.backRectangle.getWidth());
        }else if (value <= 0){
            this.frontRectangle.setWidth(0);
        }else {
            this.frontRectangle.setWidth((value * this.backRectangle.getWidth()) / 100);
        }
    }
    public double getValue(){
        return (this.frontRectangle.getWidth() * 100) / this.backRectangle.getWidth();
    }
    public Group getHealthBar(){
        return this.g;
    }
    public void hide(){
        this.g.setVisible(false);
    }
    //---------------------------------------------------------------------------------------------




}
