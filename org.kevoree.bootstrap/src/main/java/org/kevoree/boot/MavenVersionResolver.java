package org.kevoree.boot;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by duke on 16/05/13.
 */
public class MavenVersionResolver {

    public String getLastVersion(String runtimeSnapshotURL) throws IOException {

        String metaData = runtimeSnapshotURL.substring(0,runtimeSnapshotURL.lastIndexOf("/"));
        metaData = metaData + "/maven-metadata.xml";
        URL metadataURL = new URL(metaData);
        InputStream in = metadataURL.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result, line = reader.readLine();
        result = line;
        while((line=reader.readLine())!=null){
            result+=line;
        }

        String buildNumber = "";
        if(result.contains("<buildNumber>") && result.contains("</buildNumber>")){
            buildNumber = result.substring(result.indexOf("<buildNumber>")+"<buildNumber>".length(),result.indexOf("</buildNumber>"));
        }
        if(result.contains("<timestamp>") && result.contains("</timestamp>")){
           return result.substring(result.indexOf("<timestamp>")+"<timestamp>".length(),result.indexOf("</timestamp>"))+"-"+buildNumber;
        } else {
            return null;
        }
    }

}
