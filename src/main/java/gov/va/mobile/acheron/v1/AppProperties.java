package gov.va.mobile.acheron.v1;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
@Setter
public class AppProperties {
    private int vistaConnectionTimeoutMillis = 10000;
    private int vistaReadTimeoutMillis = 60000;
    private boolean skipAutoSignOn = true;
    private String vistaPingRpc = "XWB IM HERE";
    private String stsToken;
    private String rpcContext = "CDSP RPC CONTEXT";

    public AppProperties(String resourcePath) throws IOException, NullPointerException {
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(resourcePath) == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        this.stsToken = Files.readString(Paths.get(classLoader.getResource(resourcePath).getPath()));
    }
}
