package edu.sce.tom.physotrack.Algorithm;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

public class LandmarksAnalyzer {
    private final FaceLandmarks face;


    private final char LEFT_SIDE='L';
    private final char RIGHT_SIDE='R';

    //metrics
    private Point leftEyeCenter;
    private Point rightEyeCenter;
    private float leftEyeArea;
    private float rightEyeArea;
    private Point rightBrowCenter;
    private Point leftBrowCenter;
    private float leftEyeToBrowDistance;
    private float rightEyeToBrowDistance;

    public LandmarksAnalyzer(FaceLandmarks f) {
        this.face = f;
    }

    public void analyzeFace()
    {
        // Calc all the metrics of the face //

    }

    private void calcDistanceFromBrowToEye(Point a,Point b,char side){

    }

    private void calcEye(ArrayList<Point> eye,ArrayList<Point> brow,char side) {
        // calculate the center of the eye
        // calculate the area of the eye
        //calculate the center of the eyebrow
        //calculate distance from eyebrow to eye
        Point eyeAvg = calcAvg(eye);
        float eyeArea = calcPolygonArea(eye);
        Point browAvg= calcAvg(brow);
        float eyeToBrowDis=calcDistance(browAvg,eyeAvg);
        switch (side) {
            case RIGHT_SIDE:
                rightEyeCenter = eyeAvg;
                rightEyeArea = eyeArea;
                rightBrowCenter=browAvg;
                rightEyeToBrowDistance=eyeToBrowDis;
            case LEFT_SIDE:
                leftEyeCenter = eyeAvg;
                leftEyeArea = eyeArea;
                leftBrowCenter=browAvg;
                leftEyeToBrowDistance=eyeToBrowDis;
            default:
                Log.d("debug", "calceye function in the default");
        }
    }


    private Point calcAvg(ArrayList<Point> land){
        int sumx=0;
        int sumy=0;
        for (Point point:land) {
            sumx+=point.x;
            sumy+=point.y;
        }
        int size=land.size();
        return new Point(sumx/size,sumy/size);
    }

    private float calcPolygonArea(ArrayList<Point> points){
        int numOfPoints = points.size();
        float area=0;
        for(int i=0; i<numOfPoints; i++)
            area+=calcTwoPointsPolygon(points.get(i), points.get((i+1)%numOfPoints));

        return Math.abs(area/2);
    }


    private float calcTwoPointsPolygon(Point p1, Point p2){
        return p1.x*p2.y-p1.y*p2.x;
    }

    private float calcDistance(Point left, Point right){
        return (float) Math.sqrt((Math.pow((right.x-left.x),2)-(Math.pow((right.y-left.y),2))));
    }
}



