package cn.com.nlsoft;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 * ffmpeg
 * -y
 * -i rtsp://admin:Newland_12345@10.1.5.71:554/h264/ch1/main/av_stream
 * -hls_flags delete_segments
 * -vcodec copy
 * -acodec copy
 * D:\ItTool\EasyDarwin-Windows-7.0.5-Build16.0518\Movies\ffmpeg\p.m3u8
 */
public class App {
    public static void main(String[] args) throws IOException {
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-y");
        command.add("-i");
        command.add("rtsp://admin:Newland_12345@10.1.5.71:554/h264/ch1/main/av_stream");
        command.add("-hls_flags");
        command.add("delete_segments");
        command.add("-vcodec");
        command.add("copy");
        command.add("-acodec");
        command.add("copy");
        command.add("D:\\ItTool\\EasyDarwin-Windows-7.0.5-Build16.0518\\Movies\\ffmpeg\\q.m3u8");

        Process process = null;
        try {
            process = new ProcessBuilder(command).redirectErrorStream(true).start();
//            new PrintStream(process.getErrorStream()).start();
//            new PrintStream(process.getInputStream()).start();
            process.waitFor();
            System.out.println("123123");
//            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("======================>>>>");
    }

    static class PrintStream extends Thread {
        java.io.InputStream __is = null;
        public PrintStream(java.io.InputStream is) {
            __is = is;
        }

        public void run() {
            try {
                while(this != null) {
                    int _ch = __is.read();
                    if(_ch != -1)
                        System.out.print((char)_ch);
                    else break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


