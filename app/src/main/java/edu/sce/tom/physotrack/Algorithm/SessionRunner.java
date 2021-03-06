package edu.sce.tom.physotrack.Algorithm;
import android.content.Context;

import com.tzutalin.dlib.Constants;
import com.tzutalin.dlib.FaceDet;
import com.tzutalin.dlib.VisionDetRet;

import java.io.File;
import java.util.List;

import edu.sce.tom.physotrack.DataBase.DatabaseHelper;
import edu.sce.tom.physotrack.R;

import static edu.sce.tom.physotrack.Utils.EYEBROWRAISED_EXP;
import static edu.sce.tom.physotrack.Utils.EYECLOSED_EXP;
import static edu.sce.tom.physotrack.Utils.KISS_EXP;
import static edu.sce.tom.physotrack.Utils.NATURAL_EXP;
import static edu.sce.tom.physotrack.Utils.SMILE_EXP;
import static edu.sce.tom.physotrack.Utils.UPPERLIPRAISED_EXP;

public class SessionRunner {
    //attributes//
    //___P - position image path
    //___L - position FaceLandmarks object
    //___AR - position analysis results
    //___R - single image results sumed up
    private String naturalP=null;
    private FaceLandmarks naturalL=null;
    private LandmarksAnalyzer naturalAR=null;
    private ImageResult naturalR=null;

    private String eyebrowRaisedP=null;
    private FaceLandmarks eyebrowRaisedL=null;
    private LandmarksAnalyzer eyebrowRaisedAR=null;
    private ImageResult eyebrowRaisedR=null;

    private String eyesClosedP=null;
    private FaceLandmarks eyesClosedL=null;
    private LandmarksAnalyzer eyesClosedAR=null;
    private ImageResult eyesClosedR=null;

    private String upperlipRasiedP=null;
    private FaceLandmarks upperlipRasiedL=null;
    private LandmarksAnalyzer upperlipRasiedAR=null;
    private ImageResult upperlipRasiedR=null;

    private String smileP=null;
    private FaceLandmarks smileL=null;
    private LandmarksAnalyzer smileAR=null;
    private ImageResult smileR=null;

    private String kissP=null;
    private FaceLandmarks kissL=null;
    private LandmarksAnalyzer kissAR=null;
    private ImageResult kissR=null;

    private FaceDet mFaceDet=null;
    private DatabaseHelper db;

    //ctor//
    //looks for the 68.dat file in not found recreates it from the application
    //and initialize the facedet object for detection
    SessionRunner(Context c) {
        final String targetPath = Constants.getFaceShapeModelPath();
        if (!new File(targetPath).exists()) {

            FileUtils.copyFileFromRawToOthers(c.getApplicationContext(), R.raw.shape_predictor_68_face_landmarks, targetPath);
        }

        if (mFaceDet == null) {
            mFaceDet = new FaceDet(Constants.getFaceShapeModelPath());
        }

        db = new DatabaseHelper(c);
    }

    //run is after (potentially) all 5 photos were taken successfully (all setters returned true)
    //all further results will be saved on the database.
    public void run(){
        //Face Analysis//
        //Calculate the positions metrics only if the path attribute is not null
        if(naturalP!=null){
            naturalAR = new LandmarksAnalyzer(naturalL, NATURAL_EXP);
            naturalAR.analyzeFace();
            naturalR = new ImageResult(naturalAR, NATURAL_EXP);
            naturalR.calcResult();
            db.insertImageResultToDB(naturalR);
            db.insertMetricsToDB(naturalAR);
        }

        if(eyebrowRaisedP!=null){
            eyebrowRaisedAR = new LandmarksAnalyzer(eyebrowRaisedL, EYEBROWRAISED_EXP);
            eyebrowRaisedAR.analyzeFace();
            eyebrowRaisedR = new ImageResult(eyebrowRaisedAR, EYEBROWRAISED_EXP);
            eyebrowRaisedR.calcResult();
            db.insertImageResultToDB(eyebrowRaisedR);
            db.insertMetricsToDB(eyebrowRaisedAR);
        }

        if(eyesClosedP!=null){
            eyesClosedAR = new LandmarksAnalyzer(eyesClosedL, EYECLOSED_EXP);
            eyesClosedAR.analyzeFace();
            eyesClosedR = new ImageResult(eyesClosedAR, EYECLOSED_EXP);
            eyesClosedR.calcResult();
            db.insertImageResultToDB(eyesClosedR);
            db.insertMetricsToDB(eyesClosedAR);
        }

        if(upperlipRasiedP!=null){
            upperlipRasiedAR = new LandmarksAnalyzer(upperlipRasiedL, UPPERLIPRAISED_EXP);
            upperlipRasiedAR.analyzeFace();
            upperlipRasiedR = new ImageResult(upperlipRasiedAR, UPPERLIPRAISED_EXP);
            upperlipRasiedR.calcResult();
            db.insertImageResultToDB(upperlipRasiedR);
            db.insertMetricsToDB(upperlipRasiedAR);
        }

        if(smileP!=null){
            smileAR = new LandmarksAnalyzer(smileL, SMILE_EXP);
            smileAR.analyzeFace();
            smileR = new ImageResult(smileAR, SMILE_EXP);
            smileR.calcResult();
            db.insertImageResultToDB(smileR);
            db.insertMetricsToDB(smileAR);
        }

        if(kissP!=null){
            kissAR = new LandmarksAnalyzer(kissL, KISS_EXP);
            kissAR.analyzeFace();
            kissR = new ImageResult(kissAR, KISS_EXP);
            kissR.calcResult();
            db.insertImageResultToDB(kissR);
            db.insertMetricsToDB(kissAR);
        }
    }

    //setters//
    //returns true if one face was detected and updates all facial landmarks for specific position, updates path for position
    //return false in any case of error (if no face was found or no facial landmark detected or more than one face was detected...etc)
    //if false was returned no path will be updated and the picture will be ignored and a new picture will have to be taken
    public boolean setNaturalP(String Path) {
        List<VisionDetRet> faceList = mFaceDet.detect(Path);
        if (faceList != null && faceList.size() == 1) { // Only one face detected by dlib
            naturalL = new FaceLandmarks(faceList.get(0).getFaceLandmarks());   // Get all landmarks group of the face
            this.naturalP = Path;
            return true;
        }
        this.naturalP = null;
        this.naturalL = null;
        return false;
    }

    public boolean setEyebrowRaisedP(String Path) {
        List<VisionDetRet> faceList = mFaceDet.detect(Path);
        if (faceList != null && faceList.size() == 1) { // Only one face detected by dlib
            eyebrowRaisedL = new FaceLandmarks(faceList.get(0).getFaceLandmarks());   // Get all landmarks group of the face
            this.eyebrowRaisedP = Path;
            return true;
        }
        this.eyebrowRaisedP = null;
        this.eyebrowRaisedL = null;
        return false;
    }

    public boolean setEyesClosedP(String Path) {
        List<VisionDetRet> faceList = mFaceDet.detect(Path);
        if (faceList != null && faceList.size() == 1) { // Only one face detected by dlib
            eyesClosedL = new FaceLandmarks(faceList.get(0).getFaceLandmarks());   // Get all landmarks group of the face
            this.eyesClosedP = Path;
            return true;
        }
        this.eyesClosedP=null;
        this.eyesClosedL=null;
        return false;
    }

    public boolean setUpperlipRasiedP(String Path) {
        List<VisionDetRet> faceList = mFaceDet.detect(Path);
        if (faceList != null && faceList.size() == 1) { // Only one face detected by dlib
            upperlipRasiedL = new FaceLandmarks(faceList.get(0).getFaceLandmarks());   // Get all landmarks group of the face
            this.upperlipRasiedP = Path;
            return true;
        }
        this.upperlipRasiedP =null;
        this.upperlipRasiedL =null;
        return false;
    }

    public boolean setSmileP(String Path) {
        List<VisionDetRet> faceList = mFaceDet.detect(Path);
        if (faceList != null && faceList.size() == 1) { // Only one face detected by dlib
            smileL = new FaceLandmarks(faceList.get(0).getFaceLandmarks());   // Get all landmarks group of the face
            this.smileP = Path;
            return true;
        }
        this.smileL=null;
        this.smileP=null;
        return false;
    }

    public boolean setKissP(String Path) {
        List<VisionDetRet> faceList = mFaceDet.detect(Path);
        if (faceList != null && faceList.size() == 1) { // Only one face detected by dlib
            kissL = new FaceLandmarks(faceList.get(0).getFaceLandmarks());   // Get all landmarks group of the face
            this.kissP = Path;
            return true;
        }
        this.kissP = null;
        this.kissL = null;
        return false;
    }

    public String getNaturalP() {
        return naturalP;
    }
    public FaceLandmarks getNaturalL() {
        return naturalL;
    }
    public String getEyebrowRaisedP() {
        return eyebrowRaisedP;
    }
    public FaceLandmarks getEyebrowRaisedL() {
        return eyebrowRaisedL;
    }
    public String getEyesClosedP() {
        return eyesClosedP;
    }
    public FaceLandmarks getEyesClosedL() {
        return eyesClosedL;
    }
    public String getUpperlipRasiedP() {
        return upperlipRasiedP;
    }
    public FaceLandmarks getUpperlipRasiedL() {
        return upperlipRasiedL;
    }
    public String getSmileP() {
        return smileP;
    }
    public FaceLandmarks getSmileL() {
        return smileL;
    }
    public String getKissP() {
        return kissP;
    }
    public FaceLandmarks getKissL() {
        return kissL;
    }

    public ImageResult getNaturalR() { return naturalR; }
    public ImageResult getEyebrowRaisedR() { return eyebrowRaisedR; }
    public ImageResult getEyesClosedR() { return eyesClosedR; }
    public ImageResult getUpperlipRasiedR() { return upperlipRasiedR; }
    public ImageResult getSmileR() { return smileR; }
    public ImageResult getKissR() { return kissR; }

    public void initialRunner(){
        naturalP=null;
        naturalL=null;
        naturalAR=null;
        naturalR=null;
        eyebrowRaisedP=null;
        eyebrowRaisedL=null;
        eyebrowRaisedAR=null;
        eyebrowRaisedR=null;
        eyesClosedP=null;
        eyesClosedL=null;
        eyesClosedAR=null;
        eyesClosedR=null;
        upperlipRasiedP=null;
        upperlipRasiedL=null;
        upperlipRasiedAR=null;
        upperlipRasiedR=null;
        smileP=null;
        smileL=null;
        smileAR=null;
        smileR=null;
        kissP=null;
        kissL=null;
        kissAR=null;
        kissR=null;
    }
}