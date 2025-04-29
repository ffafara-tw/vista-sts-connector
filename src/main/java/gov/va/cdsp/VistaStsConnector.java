package gov.va.cdsp;

import gov.va.mobile.acheron.v1.AppProperties;
import gov.va.mobile.acheron.v1.services.rpc.RPCException;
import gov.va.mobile.acheron.v1.services.rpc.SocketFactory;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionInfo;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionValidator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class VistaStsConnector {

    public static void main(String[] args) throws RPCException, IOException {
        AppProperties appProperties = new AppProperties("stsToken.xml");
        SocketFactory socketFactory = new SocketFactory(appProperties);
        VistaConnectionValidator vistaConnectionValidator = new VistaConnectionValidator(socketFactory, appProperties);
        VistaConnectionInfo vistaConnectionInfo = VistaConnectionInfo.builder()
                .site("500")
                .host("localhost")
                .port(9430)
                .build();
        vistaConnectionValidator.isValid(vistaConnectionInfo);
    }
}
