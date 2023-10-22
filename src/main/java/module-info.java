module com.mdlb.croma {
    requires transitive javafx.controls;

    exports com.mdlb.croma;

    requires org.bytedeco.javacv;
    requires org.bytedeco.opencv;
    requires org.bytedeco.ffmpeg;
    requires java.desktop;

}
