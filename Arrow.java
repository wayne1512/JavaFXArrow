package wayne1512.arrow;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Arrow extends Path{


    private final SimpleDoubleProperty startX;
    private final SimpleDoubleProperty startY;
    private final SimpleDoubleProperty tailWidth;
    private final SimpleDoubleProperty tailLength;
    private final SimpleDoubleProperty shoulderWidth;
    private final SimpleDoubleProperty shoulderBackLength;
    private final SimpleDoubleProperty shoulderLength;
    private final SimpleDoubleProperty endX;
    private final SimpleDoubleProperty endY;

    //used for cloning to not have to recalculate the path
    private Arrow(double startX, double startY, double tailWidth, double tailLength, double shoulderWidth, double shoulderBackLength, double shoulderLength, double endX, double endY,ObservableList<PathElement> path){
        super();

        this.startX = new PathUpdatingProperty(this, "startX", startX);
        this.startY = new PathUpdatingProperty(this, "startY", startY);
        this.tailWidth = new PathUpdatingProperty(this, "tailWidth", tailWidth);
        this.tailLength = new PathUpdatingProperty(this, "tailLength", tailLength);
        this.shoulderWidth = new PathUpdatingProperty(this, "shoulderWidth", shoulderWidth);
        this.shoulderBackLength = new PathUpdatingProperty(this, "shoulderBackLength", shoulderBackLength);
        this.shoulderLength = new PathUpdatingProperty(this, "shoulderLength", shoulderLength);
        this.endX = new PathUpdatingProperty(this, "endX", endX);
        this.endY = new PathUpdatingProperty(this, "endY", endY);

        getElements().addAll(path);
    }

    public Arrow(double startX, double startY, double tailWidth, double tailLength, double shoulderWidth, double shoulderBackLength, double shoulderLength, double endX, double endY){
        super();

        this.startX = new PathUpdatingProperty(this, "startX", startX);
        this.startY = new PathUpdatingProperty(this, "startY", startY);
        this.tailWidth = new PathUpdatingProperty(this, "tailWidth", tailWidth);
        this.tailLength = new PathUpdatingProperty(this, "tailLength", tailLength);
        this.shoulderWidth = new PathUpdatingProperty(this, "shoulderWidth", shoulderWidth);
        this.shoulderBackLength = new PathUpdatingProperty(this, "shoulderBackLength", shoulderBackLength);
        this.shoulderLength = new PathUpdatingProperty(this, "shoulderLength", shoulderLength);
        this.endX = new PathUpdatingProperty(this, "endX", endX);
        this.endY = new PathUpdatingProperty(this, "endY", endY);

        updatePath();
    }


    public void updatePath(){

        getElements().clear();

        double dX = endX.get() - startX.get();
        double dY = endY.get() - startY.get();

        double angle = Math.atan2(dY,dX);

        List<Point2D> points = new ArrayList<>(10);

        Point2D startPoint = new Point2D(startX.getValue(), startY.getValue());

        Translate translate1 = new Translate(0, ((double) tailWidth.get() / 2));
        Point2D tailBottomPoint = translate1.transform(startPoint);
        points.add(tailBottomPoint);

        Translate translate2 = new Translate(tailLength.getValue(), 0);
        Point2D shoulderStartBottomPoint = translate2.transform(tailBottomPoint);
        points.add(shoulderStartBottomPoint);

        Translate translate3 = new Translate(-shoulderBackLength.getValue(),shoulderWidth.getValue());
        Point2D shoulderBackBottomPoint = translate3.transform(shoulderStartBottomPoint);
        points.add(shoulderBackBottomPoint);

        Translate translate4 = new Translate(shoulderLength.getValue(),0);
        Point2D shoulderEndBottomPoint = translate4.transform(shoulderBackBottomPoint);
        points.add(shoulderEndBottomPoint);

        List<Point2D> topHalf = new ArrayList<>(points.size());

        Scale flipper = new Scale(1,-1,startPoint.getX(),startPoint.getY());

        for (int i = points.size() - 1; i >= 0; i--){
            Point2D point = points.get(i);
            topHalf.add(flipper.transform(point));
        }

        Rotate masterRotation = new Rotate(Math.toDegrees(angle),startPoint.getX(),startPoint.getY());
        points = points.stream().map(masterRotation::transform).collect(Collectors.toList());

        points.add(new Point2D(endX.getValue(),endY.getValue()));

        points.addAll(topHalf.stream().map(masterRotation::transform).collect(Collectors.toList()));

        getElements().add(new MoveTo(startPoint.getX(), startPoint.getY()));
        for (Point2D point : points){
            getElements().add(new LineTo(point.getX(), point.getY()));
        }

        getElements().add(new ClosePath());
    }

    public double getStartX(){
        return startX.get();
    }

    public void setStartX(double startX){
        this.startX.set(startX);
    }

    public SimpleDoubleProperty startXProperty(){
        return startX;
    }

    public double getStartY(){
        return startY.get();
    }

    public void setStartY(double startY){
        this.startY.set(startY);
    }

    public SimpleDoubleProperty startYProperty(){
        return startY;
    }

    public double getTailWidth(){
        return tailWidth.get();
    }

    public void setTailWidth(double tailWidth){
        this.tailWidth.set(tailWidth);
    }

    public SimpleDoubleProperty tailWidthProperty(){
        return tailWidth;
    }

    public double getTailLength(){
        return tailLength.get();
    }

    public void setTailLength(double tailLength){
        this.tailLength.set(tailLength);
    }

    public SimpleDoubleProperty tailLengthProperty(){
        return tailLength;
    }

    public double getShoulderLength(){
        return shoulderLength.get();
    }

    public void setShoulderLength(double shoulderLength){
        this.shoulderLength.set(shoulderLength);
    }

    public SimpleDoubleProperty shoulderLengthProperty(){
        return shoulderLength;
    }

    public double getShoulderBackLength(){
        return shoulderBackLength.get();
    }

    public void setShoulderBackLength(double shoulderBackLength){
        this.shoulderBackLength.set(shoulderBackLength);
    }

    public SimpleDoubleProperty shoulderBackLengthProperty(){
        return shoulderBackLength;
    }

    public double getShoulderWidth(){
        return shoulderWidth.get();
    }

    public void setShoulderWidth(double shoulderWidth){
        this.shoulderWidth.set(shoulderWidth);
    }

    public SimpleDoubleProperty shoulderWidthProperty(){
        return shoulderWidth;
    }

    public double getEndX(){
        return endX.get();
    }

    public void setEndX(double endX){
        this.endX.set(endX);
    }

    public SimpleDoubleProperty endXProperty(){
        return endX;
    }

    public double getEndY(){
        return endY.get();
    }

    public void setEndY(double endY){
        this.endY.set(endY);
    }

    public SimpleDoubleProperty endYProperty(){
        return endY;
    }



    @Override
    @SuppressWarnings("all")
    public Object clone(){
        return new Arrow(
                getStartX(),
                getStartY(),
                getTailWidth(),
                getTailLength(),
                getShoulderWidth(),
                getShoulderBackLength(),
                getShoulderLength(),
                getEndX(),
                getEndY(),
                getElements()
        );
    }

    class PathUpdatingProperty extends SimpleDoubleProperty{

        {
            addListener(n -> updatePath());
        }

        public PathUpdatingProperty(){
        }

        public PathUpdatingProperty(double initialValue){
            super(initialValue);
        }

        public PathUpdatingProperty(Object bean, String name){
            super(bean, name);
        }

        public PathUpdatingProperty(Object bean, String name, double initialValue){
            super(bean, name, initialValue);
        }
    }


}