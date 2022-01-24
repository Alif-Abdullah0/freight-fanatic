package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.hardware.CVPipelines.RingCounter;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

//A new class will probably be created for a normal webcam.
//This code works if you are using a phone as a robot controller, and want to use it as your camera.
public class WebcamCamera extends Mechanism{
    //here I initialize the phone camera and the pipeline; there are other ways to do this if you prefer
    private WebcamName webcamName;
    private OpenCvCamera camera;
    private RingCounter counter = new RingCounter();
    private boolean flash;

    //this basically links variable phoneCam to the actual phone camera; after that, it links phoneCam to the pipeline, counter; I don't know if "link" is the correct term
    public void init(HardwareMap hwMap){
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwMap.appContext.getPackageName());
        //TODO: Fix deviceName, the second parameter passed to hwMap.get function
        webcamName = hwMap.get(WebcamName.class, "NAME_OF_CAMERA_IN_CONFIG_FILE");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        camera.setPipeline(counter);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int error)
            {

            }
        });
    }

    
    //Returns the percentage of the target color in the target region; go into RingCounter to learn more
    public double regionValue(){
        return counter.regionValue();
    }
}
