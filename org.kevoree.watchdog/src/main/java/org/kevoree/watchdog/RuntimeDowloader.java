package org.kevoree.watchdog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by duke on 16/05/13.
 */
public class RuntimeDowloader {

    private String getTempPath() {
        String tempPath = System.getProperty("java.io.tmpdir");
        if (!tempPath.endsWith(File.separator)) {
            tempPath = tempPath + File.separator;
        }
        return tempPath;
    }

    public File get(String runtimeURL, String version) throws IOException {
        File runtimeFile = getCache(runtimeURL, version);
        if (!runtimeFile.exists()) {
            URL url = new URL(runtimeURL);
            System.out.println("Runtime not found, downloading it...");
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(runtimeFile);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
        }
        return runtimeFile;
    }

    public File getCache(String runtimeURL, String version) {
        String tempPath = getTempPath();
        File runtimeFile = new File(tempPath + File.separator + "kevoree-runtime-" + version + ".jar");
        return runtimeFile;
    }


}
