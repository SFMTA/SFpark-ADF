package sfpark.rateChange.manager.view.helper;

import com.sfmta.websvcs.applyratexml.OdiInvoke;

import com.sfmta.websvcs.applyratexml.OdiInvokePortType;

import com.sfmta.websvcs.applyratexml.types.AgentType;
import com.sfmta.websvcs.applyratexml.types.CommandResultType;
import com.sfmta.websvcs.applyratexml.types.InvokeScenarioRequest;
import com.sfmta.websvcs.applyratexml.types.RepositoryConnectionType;

import com.sfmta.websvcs.applyratexml.types.ScenarioCommandType;
import com.sfmta.websvcs.applyratexml.types.VariableType;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import sfpark.adf.tools.helper.DeveloperMode;
import sfpark.adf.tools.helper.Logger;
import sfpark.adf.tools.model.helper.OperationStatus;
import sfpark.adf.tools.model.provider.PropertyFileProvider;
import sfpark.adf.tools.utilities.generic.StringUtil;

/**
 * Change History:
 * Change ID format is YYYYMMDD-## where you can identify multiple changes
 * Change ID   Developer Name                   Description
 * ----------- -------------------------------- --------------------------------------------------------
 * 20111123-01 Mark Piller - Oracle Consulting  Created this Class
 * 20111129-01 Mark Piller - Oracle Consulting  Added logic to different ODI web services
 */
public final class ODIWebServiceHelper {

    private static final String CLASSNAME = ODIWebServiceHelper.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static final String WEB_SERVICE_URL = "http://lnxappdev1:9704/axis2/services/OdiInvoke.OdiInvokeSOAP11port0";
    private static final String WEB_SERVICE_URL_PROPERTY_KEY = "SFPARK.ADF.TOOLS.ODI.WEBSERVICE.URL";

    // Repository Connection values
    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String JDBC_DRIVER_KEY = "SFPARK.ADF.TOOLS.ODI.JDBC.DRIVER";

    private static final String JDBC_URL = "jdbc:oracle:thin:@lnxoradev1:1521:sfpark1";
    private static final String JDBC_URL_KEY = "SFPARK.ADF.TOOLS.ODI.JDBC.URL";

    private static final String JDBC_USER = "ODI_MSTR";
    private static final String JDBC_USER_KEY = "SFPARK.ADF.TOOLS.ODI.JDBC.USER";

    private static final String JDBC_PASSWORD = "ODI_MSTR";
    private static final String JDBC_PASSWORD_KEY = "SFPARK.ADF.TOOLS.ODI.JDBC.PASSWORD";

    private static final String ODI_USER = "SUPERVISOR";
    private static final String ODI_USER_KEY = "SFPARK.ADF.TOOLS.ODI.ODI.USER";

    private static final String ODI_PASSWORD = "SUNOPSIS";
    private static final String ODI_PASSWORD_KEY = "SFPARK.ADF.TOOLS.ODI.ODI.PASSWORD";

    private static final String WORK_REPOSITORY = "ODIWORK";
    private static final String WORK_REPOSITORY_KEY = "SFPARK.ADF.TOOLS.ODI.WORK.REPOSITORY";

    // Variable values - which are the packages to be called on the server
    private static final String APPLY_RATE_VAR_NAME="SFPARK.RateChgProc_ApplyRateStep_ProcessID";
    private static final String RECONCILE_VAR_NAME="SFPARK.RateChgProc_ReconStep_ProcessID";
    private static final String UPDT_EFF_DT_VAR_NAME="SFPARK.RateChgProc_UpdEffDtStep_ProcessID";

    private static final String APPLY_RATE_VAR_NAME_KEY = "SFPARK.ADF.TOOLS.ODI.APPLY_RATE.VAR.NAME";
    private static final String RECONCILE_VAR_NAME_KEY = "SFPARK.ADF.TOOLS.ODI.RECONCILE.VAR.NAME";
    private static final String UPDT_EFF_DT_VAR_NAME_KEY = "SFPARK.ADF.TOOLS.ODI.UPDT.EFF.DT.VAR.NAME";

    // Scenario Command values
    private static final String APPLY_RATE_SCENARIO_NAME = "PKG_RATECHGPROC_APPLYRATE";
    private static final String RECONCILE_SCENARIO_NAME = "PKG_RATECHGPROC_RECON";
    private static final String UPDT_EFF_DT_SCENARIO_NAME = "PKG_RATECHGPROC_UPDEFFDT";

    private static final String APPLY_RATE_SCENARIO_NAME_KEY = "SFPARK.ADF.TOOLS.ODI.APPLY_RATE.SCENARIO.NAME";
    private static final String RECONCILE_SCENARIO_NAME_KEY = "SFPARK.ADF.TOOLS.ODI.RECONCILE.SCENARIO.NAME";
    private static final String UPDT_EFF_DT_SCENARIO_NAME_KEY = "SFPARK.ADF.TOOLS.ODI.UPDT.EFF.DT.SCENARIO.NAME";

    private static final String SCENARIO_VERSION = "001";
    private static final String SCENARIO_VERSION_KEY = "SFPARK.ADF.TOOLS.ODI.SCENARIO.VERSION";

    private static final String SCENARIO_CONTEXT = "Development";
    private static final String SCENARIO_CONTEXT_KEY = "SFPARK.ADF.TOOLS.ODI.SCENARIO.CONTEXT";

    private static final Integer SCENARIO_LOG_LEVEL = 5;
    private static final String SCENARIO_LOG_LEVEL_KEY = "SFPARK.ADF.TOOLS.ODI.SCENARIO.LOG.LEVEL";

    private static final Integer SCENARIO_SYNC_MODE = 1;
    private static final String SCENARIO_SYNC_MODE_KEY = "SFPARK.ADF.TOOLS.ODI.SCENARIO.SYNC.MODE";

    // Agent values
    private static final String AGENT_HOST = "lnxoradev1";
    private static final String AGENT_HOST_KEY = "SFPARK.ADF.TOOLS.ODI.AGENT.HOST";

    private static final int AGENT_PORT = 20920;
    private static final String AGENT_PORT_KEY = "SFPARK.ADF.TOOLS.ODI.AGENT.PORT";

    private static final String APPLY_RATE = "APPLY_RATE";
    private static final String RECONCILE = "RECONCILE";
    private static final String UPDT_EFF_DT = "UPDT_EFF_DT";

    private ODIWebServiceHelper() {
        super();
    }

    public static OperationStatus callODIWebService(String processID, String webServiceName) {
        LOGGER.entering(CLASSNAME, "callODIWebService");

        if (!StringUtil.isDigitsONLY(processID)) {
            LOGGER.debug("processID is not only digits");
            return OperationStatus.failure(new IllegalArgumentException("Process ID should be valid. "));
        }

        String webServiceURL = getWebServiceURL();
        LOGGER.debug("Web Service URL used is: " + webServiceURL);

        if (StringUtil.isBlank(webServiceURL)) {
            return OperationStatus.failure(new NoSuchFieldException("Web Service URL was not found. "));
        }

        // OdiInvoke odiInvoke = new OdiInvoke();
        URL webServiceUrl = null;
        try {
            webServiceUrl = new URL(webServiceURL);
        } catch (MalformedURLException e) {
            // end
        }
        OdiInvoke odiInvoke =
            new OdiInvoke(webServiceUrl, new QName("xmlns.oracle.com/odi/OdiInvoke/",
                                                   "OdiInvoke"));
        OdiInvokePortType webServiceSOAP = odiInvoke.getOdiInvokeSOAP11Port0();

        //LOGGER.debug("Supposed to be setting URL context for this Web Service");
        //((BindingProvider)webServiceSOAP).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, webServiceURL);
        //LOGGER.debug("((BindingProvider)webServiceSOAP).getRequestContext().toString()  " + ((BindingProvider)webServiceSOAP).getRequestContext().toString());

        RepositoryConnectionType repConnType = new RepositoryConnectionType();
        repConnType.setJdbcDriver(getJdbcDriver());
        repConnType.setJdbcUrl(getJdbcUrl());
        repConnType.setJdbcUser(getJdbcUser());
        repConnType.setJdbcPassword(getJdbcPassword());
        repConnType.setOdiUser(getOdiUser());
        repConnType.setOdiPassword(getOdiPassword());
        repConnType.setWorkRepository(getWorkRepository());

        VariableType varType = new VariableType();
        if (webServiceName.equals(APPLY_RATE)) {
            varType.setName(getApplyRateVarName());
        } else if (webServiceName.equals(RECONCILE)) {
            varType.setName(getReconcileVarName());
        } else if (webServiceName.equals(UPDT_EFF_DT)) {
            varType.setName(getUpdtEffDtVarName());
        }
        varType.setValue(processID);

        ScenarioCommandType scenarioCmdType = new ScenarioCommandType();
        if (webServiceName.equals(APPLY_RATE)) {
            scenarioCmdType.setScenName(getApplyRateScenarioName());
        } else if (webServiceName.equals(RECONCILE)) {
            scenarioCmdType.setScenName(getReconcileScenarioName());
        } else if (webServiceName.equals(UPDT_EFF_DT)) {
            scenarioCmdType.setScenName(getUpdtEffDtScenarioName());
        }

        scenarioCmdType.setScenVersion(getScenarioVersion());
        scenarioCmdType.setContext(getScenarioContext());
        scenarioCmdType.setLogLevel(getScenarioLogLevel());
        scenarioCmdType.setSyncMode(getScenarioSyncMode());
        scenarioCmdType.getVariables().add(varType);

        AgentType agentType = new AgentType();
        agentType.setHost(getAgentHost());
        agentType.setPort(getAgentPort());

        InvokeScenarioRequest request = new InvokeScenarioRequest();
        request.setAgent(agentType);
        request.setCommand(scenarioCmdType);
        request.setRepositoryConnection(repConnType);

        CommandResultType commandResultType;

        try {
            LOGGER.debug("Executing WS invokeScenario(request)");
            commandResultType = webServiceSOAP.invokeScenario(request);
        } catch (Exception e) {
            LOGGER.error("Web Service URL is invalid and/or not working. ", e);

            return OperationStatus.failure(new Exception("Web Service Error. Contact System Administrator. "));
        }

        boolean response = commandResultType.isOk();
        LOGGER.debug("Result of calling ODI Web Service: " + response);
        String sMessage = commandResultType.getErrorMessage();
        LOGGER.debug("Message from call to ODI Web Service: " + sMessage);

        if (response == true) {
            LOGGER.debug("Returning Call to ODI Web Service success to calling logic");
            LOGGER.exiting(CLASSNAME, "callODIWebService");
            return OperationStatus.success();
        } else {
            LOGGER.debug("Returning Call to ODI Web Service failure to calling logic");
            LOGGER.exiting(CLASSNAME, "callODIWebService");
            return OperationStatus.failure(new Exception("Web Service Error: " + sMessage +
                                                         " Contact System Administrator. "));
        }

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // HELPER METHODS


    private static String getWebServiceURL() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(WEB_SERVICE_URL_PROPERTY_KEY) :
               WEB_SERVICE_URL;
    }

    private static String getJdbcDriver() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(JDBC_DRIVER_KEY) : JDBC_DRIVER;
    }

    private static String getJdbcUrl() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(JDBC_URL_KEY) : JDBC_URL;
    }

    private static String getJdbcUser() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(JDBC_USER_KEY) : JDBC_USER;
    }

    private static String getJdbcPassword() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(JDBC_PASSWORD_KEY) : JDBC_PASSWORD;
    }

    private static String getOdiUser() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(ODI_USER_KEY) : ODI_USER;
    }

    private static String getOdiPassword() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(ODI_PASSWORD_KEY) : ODI_PASSWORD;
    }

    private static String getWorkRepository() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(WORK_REPOSITORY_KEY) : WORK_REPOSITORY;
    }


    private static String getApplyRateVarName() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(APPLY_RATE_VAR_NAME_KEY) : APPLY_RATE_VAR_NAME;
    }

    private static String getReconcileVarName() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(RECONCILE_VAR_NAME_KEY) : RECONCILE_VAR_NAME;
    }

    private static String getUpdtEffDtVarName() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(UPDT_EFF_DT_VAR_NAME_KEY) : UPDT_EFF_DT_VAR_NAME;
    }

    private static String getApplyRateScenarioName() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(APPLY_RATE_SCENARIO_NAME_KEY) : APPLY_RATE_SCENARIO_NAME;
    }

    private static String getReconcileScenarioName() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(RECONCILE_SCENARIO_NAME_KEY) : RECONCILE_SCENARIO_NAME;
    }

    private static String getUpdtEffDtScenarioName() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(UPDT_EFF_DT_SCENARIO_NAME_KEY) :
               UPDT_EFF_DT_SCENARIO_NAME;
    }


    private static String getScenarioVersion() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(SCENARIO_VERSION_KEY) : SCENARIO_VERSION;
    }

    private static String getScenarioContext() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(SCENARIO_CONTEXT_KEY) : SCENARIO_CONTEXT;
    }

    private static Integer getScenarioLogLevel() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               Integer.getInteger(PropertyFileProvider.getPropertyValue(SCENARIO_LOG_LEVEL_KEY)) :
               SCENARIO_LOG_LEVEL;
    }

    private static Integer getScenarioSyncMode() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               Integer.getInteger(PropertyFileProvider.getPropertyValue(SCENARIO_SYNC_MODE_KEY)) :
               SCENARIO_SYNC_MODE;
    }

    private static String getAgentHost() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               PropertyFileProvider.getPropertyValue(AGENT_HOST_KEY) : AGENT_HOST;
    }

    private static int getAgentPort() {
        return (DeveloperMode.DEPLOYED_ON_SERVER) ?
               Integer.parseInt(PropertyFileProvider.getPropertyValue(AGENT_PORT_KEY)) :
               AGENT_PORT;
    }

}
