package com.mdlb.croma;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import com.mdlb.croma.frontend.MainWindow;

public class App {

    private void createVideoFromFrames() throws Exception {
        // Set up the recorder
        String outputFileName = "output/video.mp4";
        int frameRate = 30;
        int frameWidth = 640;
        int frameHeight = 480;
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFileName, frameWidth, frameHeight);
        recorder.setVideoCodecName("libx264");
        recorder.setFrameRate(frameRate);
        recorder.start();

        // Load the frames from the image files
        ArrayList<BufferedImage> frames = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String inputFileName = String.format("input/frame_%04d.jpg", i);
            BufferedImage image = ImageIO.read(new File(inputFileName));
            frames.add(image);
        }

        // Write the frames to the video file
        for (BufferedImage image : frames) {
            var converter = new Java2DFrameConverter();
            Frame frame = converter.convert(image);
            recorder.record(frame);
            converter.close();
        }

        // Release the recorder
        recorder.stop();
        recorder.release();
        recorder.close();
    }

    private void extractAudioFromOriginalSource() throws Exception {
        // Set up the grabber
        String inputFileName = "input/video.mp4";
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFileName);
        grabber.start();

        // Set up the recorder
        String outputFileName = "output/audio.mp3";
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFileName, 0);
        recorder.setAudioCodecName("libmp3lame");
        recorder.setAudioChannels(grabber.getAudioChannels());
        recorder.setAudioBitrate(grabber.getAudioBitrate());
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.start();

        // Extract the audio frames and write them to the output file
        Frame frame;
        while ((frame = grabber.grabSamples()) != null) {
            recorder.record(frame);
        }

        // Release the grabber and recorder
        grabber.stop();
        grabber.release();
        recorder.stop();
        recorder.release();
        grabber.close();
        recorder.close();
    }

    private void extractFramesFromOriginalSource() throws Exception {
        ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
        File videoFile = new File("src/main/java/com/mdlb/croma/input/video.webm");

        // Open the video file
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoFile)) {
            grabber.start();
            int frameCount = grabber.getLengthInFrames();
            double frameRate = grabber.getFrameRate();
            String codecName = grabber.getVideoCodecName();
            int frameWidth = grabber.getImageWidth();
            int frameHeight = grabber.getImageHeight();
            int currentFrame = grabber.getFrameNumber();

            // Iterate through all frames
            for (int i = 0; i < grabber.getLengthInFrames(); i++) {
                // Grab the frame
                Frame frame = grabber.grabImage();
                currentFrame = grabber.getFrameNumber();

                // Do something with the frame, e.g., save it to a file
                // You can replace this with your own logic
                if (frame != null) {
                    // String outputFileName = String.format("output/frame_%04d.jpg", i);
                    // Save the frame as an image file
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage convertedImage = converter.getBufferedImage(frame);
                    frames.add(convertedImage);
                    converter.close();
                    // ImageIO.write(convertedImage, "jpg", new File("src/main/java/com/mdlb/croma/"
                    // + outputFileName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainWindow.launch(MainWindow.class, args);
    }

}