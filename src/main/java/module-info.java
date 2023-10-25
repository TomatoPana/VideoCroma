module com.mdlb.croma {
    requires transitive javafx.controls;

    exports com.mdlb.croma;
    exports com.mdlb.croma.frontend;

    requires org.bytedeco.javacv;
    requires org.bytedeco.opencv;
    requires org.bytedeco.ffmpeg;
    requires java.desktop;

}
