package org.kevoree.watchdog;

import org.kevoree.resolver.MavenResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

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

    public File getSharedChildClassJar() {
        String tempPath = getTempPath();
        File agentFile = new File(tempPath + File.separator + "shared-childjvm-" + Version.VERSION + ".jar");
        try {
            if (!agentFile.exists() || Version.VERSION.contains("SNAPSHOT")) {
                FileOutputStream fos = new FileOutputStream(agentFile);
                JarOutputStream jarOut = new JarOutputStream(fos);

                String[] files = new String[]{"org/kevoree/watchdog/child/watchdog/ChildRunner.class", "org/kevoree/watchdog/child/watchdog/WatchdogClient.class"};
                for (int i = 0; i < files.length; i++) {
                    String file = files[i];
                    jarOut.putNextEntry(new ZipEntry(file));
                    InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
                    byte data[] = new byte[1024];
                    int count;
                    while ((count = is.read(data, 0, 1024)) != -1) {
                        jarOut.write(data, 0, count);
                    }
                    jarOut.closeEntry();
                    is.close();
                }

                jarOut.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agentFile;
    }


}
