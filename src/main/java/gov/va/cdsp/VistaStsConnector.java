package gov.va.cdsp;

import java.io.IOException;

import gov.va.mobile.acheron.v1.AppProperties;
import gov.va.mobile.acheron.v1.services.rpc.RPCException;
import gov.va.mobile.acheron.v1.services.rpc.RpcListValue;
import gov.va.mobile.acheron.v1.services.rpc.RpcLiteralValue;
import gov.va.mobile.acheron.v1.services.rpc.RpcValue;
import gov.va.mobile.acheron.v1.services.rpc.SocketFactory;
import gov.va.mobile.acheron.v1.services.rpc.VistaQuery;
import gov.va.mobile.acheron.v1.services.rpc.VistaResponse;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnection;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionInfo;
import gov.va.mobile.acheron.v1.services.rpc.connection.VistaConnectionValidator;
import gov.va.mobile.vista.rpc.query.LiteralRpcParameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VistaStsConnector {

    public static void main(String[] args) throws RPCException, IOException {
        AppProperties appProperties = new AppProperties("stsToken.xml");
        SocketFactory socketFactory = new SocketFactory(appProperties);
        VistaConnectionValidator vistaConnectionValidator = new VistaConnectionValidator(socketFactory, appProperties);
        VistaConnectionInfo vistaConnectionInfo = VistaConnectionInfo.builder()
                .site("500")
                .host("dev.vista.octo.va.gov")
                .port(9094)
                .build();
        vistaConnectionValidator.isValid(vistaConnectionInfo);
        VistaConnection connection = new VistaConnection(vistaConnectionInfo, socketFactory, appProperties.isSkipAutoSignOn());
        connection.connect();
        connection.login(appProperties.getStsToken(), appProperties.getRpcContext());
        VistaQuery query = new VistaQuery("VPR GET PATIENT DATA", null);
        query.addLiteral(new RpcLiteralValue("DFN;ICN"));
        query.addLiteral(new RpcLiteralValue("demographics"));
        // VistaQuery query = new VistaQuery("VPR GET PATIENT DATA JSON", null);
        // RpcListValue parameterList = new RpcListValue();
        // parameterList.add("patientId", ";6050242829");
        // parameterList.add("domain", "demographics");
        // query.addList(parameterList);
        VistaResponse response = connection.query(query);
        log.info("Received data: {}", response);
        connection.close();
    }
}
