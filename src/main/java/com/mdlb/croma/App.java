package com.mdlb.croma;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FFmpegFrameGrabber.Exception, IOException {
        File videoFile = new File("src/main/java/com/mdlb/croma/input/output_h264_video.mp4");

        // Open the video file
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFile)) {
            grabber.start();

            // Iterate through all frames
            for (int i = 0; i < grabber.getLengthInFrames(); i++) {
                // Grab the frame
                Frame frame = grabber.grabImage();

                // Do something with the frame, e.g., save it to a file
                // You can replace this with your own logic
                if (frame != null) {
                    String outputFileName = String.format("output/frame_%04d.jpg", i);
                    // Save the frame as an image file
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage convertedImage = converter.getBufferedImage(frame);
                    converter.close();
                    ImageIO.write(convertedImage, "jpg", new File("src/main/java/com/mdlb/croma/" + outputFileName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch();
    }

}