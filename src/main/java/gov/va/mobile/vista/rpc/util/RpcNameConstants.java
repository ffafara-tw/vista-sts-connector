package gov.va.mobile.vista.rpc.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

@UtilityClass
public final class RpcNameConstants {

    public static final String VIAB_SITENAME = "VIAB SITENAME";
    public static final String DG_SENSITIVE_RECORD_ACCESS = "DG SENSITIVE RECORD ACCESS";
    public static final String DG_SENSITIVE_RECORD_BULLETIN = "DG SENSITIVE RECORD BULLETIN";
    public static final String MBAA_CANCEL_APPOINTMENT = "MBAA CANCEL APPOINTMENT";
    public static final String MBAA_LIST_CANCELLATION_REASONS = "MBAA LIST CANCELLATION REASONS";

    public static final String BMS_RPC = "VIAB BMS";
    public static final String VIAB_EFR = "VIAB EFR";
    public static final String VIAB_PHSR_LAB = "VIAB PHSR LAB";

    public static final String MBAA_NO_PATIENT_CSLT_LOOKUP = "MBAA NO PATIENT CSLT LOOKUP";
    public static final String MBAA_FACILITY_WAIT_LIST = "MBAA FACILITY WAIT LIST";
    public static final String MBAA_ADD_PATIENT_TO_RECALL_LIST = "MBAA ADD TO RECALL LIST";
    public static final String MBAA_RECALL_LIST_BY_FACILITY = "MBAA RECALL FACILITY LIST";
    public static final String MBAA_RECALL_LIST_BY_PATIENT = "MBAA RECALL LIST BY PATIENT";
    public static final String MBAA_REMOVE_FROM_RECALL_LIST = "MBAA REMOVE FROM RECALL LIST";
    public static final String MBAA_PROVIDERS_BY_CLINIC = "MBAA PROVIDERS BY CLINIC";

    public static final String ORQQCN_DEFAULT_REQUEST_REASON = "ORQQCN DEFAULT REQUEST REASON"; // Original RPC
    public static final String VIAB_DEFAULT_REQUEST_REASON = "VIAB DEFAULT REQUEST REASON"; // Replacement RPC

    public static final String ORPRF_HASFLG = "ORPRF HASFLG";
    public static final String MBAA_NEAR_LIST_BY_PATIENT = "MBAA PATIENT NEAR LIST";
    public static final String MBAA_APPOINTMENT_LIST_BY_NAME = "MBAA APPOINTMENT LIST BY NAME";
    public static final String MBAA_APPOINTMENT_MAKE = "MBAA APPOINTMENT MAKE";
    public static final String MBAA_WAIT_LIST_BY_DFN = "MBAA WAIT LIST BY DFN";
    public static final String MBAA_NEAR_LIST_BY_FACILITY = "MBAA FACILITY NEAR LIST";
    public static final String MBAA_REMOVE_FROM_EWL = "MBAA REMOVE FROM EWL";
    public static final String MBAA_UPDATE_NEAR_LIST = "MBAA UPDATE NEAR LIST";
    public static final String MBAA_PATIENT_PENDING_APPT = "MBAA PATIENT PENDING APPT";
    public static final String MBAA_SD_GET_CLINIC_AVAILABILITY = "MBAA GET CLINIC DETAILS";
    public static final String MBAA_GET_CLINIC_AVAILABILITY = "MBAA GET CLINIC AVAILABILITY";
    public static final String MBAA_HAS_CLINIC_ACCESS = "MBAA VERIFY CLINIC ACCESS";
    public static final String MBAA_EWL_NEW = "MBAA EWL NEW";

    public static final String MD_CLIO = "MD CLIO";

    public static final String ORCHECK_GETXTRA = "ORCHECK GETXTRA";
    public static final String OREVNTX1_GETSTS = "OREVNTX1 GETSTS";
    public static final String OREVNTX1_ODPTEVID = "OREVNTX1 ODPTEVID";
    public static final String ORQQAL_LIST = "ORQQAL LIST";
    public static final String ORQQCN_DETAIL = "ORQQCN DETAIL";
    public static final String ORQQCN_LIST = "ORQQCN LIST";
    public static final String ORQPT_CLINIC_PATIENTS = "ORQPT CLINIC PATIENTS";
    public static final String ORQPT_PROVIDER_PATIENTS = "ORQPT PROVIDER PATIENTS";
    public static final String ORQPT_SPECIALTIES = "ORQPT SPECIALTIES";
    public static final String ORQPT_SPECIALTY_PATIENTS = "ORQPT SPECIALTY PATIENTS";
    public static final String ORQPT_TEAMS = "ORQPT TEAMS";
    public static final String ORQPT_TEAM_PATIENTS = "ORQPT TEAM PATIENTS";
    public static final String ORQPT_WARDRMBED = "ORQPT WARDRMBED";
    public static final String ORQPT_WARDS = "ORQPT WARDS";
    public static final String ORQQCN_GET_ORDER_NUMBER = "ORQQCN GET ORDER NUMBER";
    public static final String ORQQCN_SVC_SYNONYMS = "ORQQCN SVC W/SYNONYMS";
    public static final String ORQQPL_PROBLEM_LIST = "ORQQPL PROBLEM LIST";
    public static final String ORQQVI_VITALS = "ORQQVI VITALS";
    public static final String ORVAA_ACTIVE_INSURANCE = "ORVAA VAA";
    public static final String ORWPS_ACTIVE = "ORWPS ACTIVE";
    public static final String ORWDPS1_SCHALL = "ORWDPS1 SCHALL";
    public static final String ORIMO_IMOOD = "ORIMO IMOOD";
    public static final String ORWDLR32_IC_VALID = "ORWDLR32 IC VALID";
    public static final String ORWDXC_DISPLAY = "ORWDXC DISPLAY";
    public static final String ORWDLR32_GET_LAB_TIMES = "ORWDLR32 GET LAB TIMES";
    public static final String ORWDLR32_MAXDAYS = "ORWDLR32 MAXDAYS";
    public static final String ORWDRA32_ISOLATN = "ORWDRA32 ISOLATN";
    public static final String ORWDRA32_PROCMSG = "ORWDRA32 PROCMSG";

    public static final String ORWDRA32_RADSRC = "ORWDRA32 RADSRC"; // Original RPC
    public static final String VIAB_RADSRC = "VIAB RADSRC"; // Replacement RPC

    public static final String ORWDPS2_ADMIN = "ORWDPS2 ADMIN";
    public static final String ORWDPS2_CHKGRP = "ORWDPS2 CHKGRP";
    public static final String ORWDPS2_QOGRP = "ORWDPS2 QOGRP";
    public static final String ORWDPS32_DRUGMSG = "ORWDPS32 DRUGMSG";
    public static final String ORWDPS32_VALROUTE = "ORWDPS32 VALROUTE";
    public static final String ORWDLR33_FUTURE_LAB_COLLECTS = "ORWDLR33 FUTURE LAB COLLECTS";
    public static final String ORWMC_PATIENT_PROCEDURES1 = "ORWMC PATIENT PROCEDURES1";
    public static final String ORWPS_DETAIL = "ORWPS DETAIL";
    public static final String ORWPT_PTINQ = "ORWPT PTINQ";
    public static final String ORWPT1_PCDETAIL = "ORWPT1 PCDETAIL";

    public static final String ORQQCN_PROVDX = "ORQQCN PROVDX"; // Original RPC
    public static final String VIAB_PROVDX = "VIAB PROVDX"; // Replacement RPC

    public static final String ORWSR_RPTLIST = "ORWSR RPTLIST"; // Original RPC
    public static final String VIAB_SRGY_RPTLIST = "VIAB SRGY RPTLIST"; // Replacement RPC

    public static final String ORWDX_LOCK_ORDER = "ORWDX LOCK ORDER";
    public static final String ORWDXA_VALID = "ORWDXA VALID";
    public static final String ORWDX2_DCREASON = "ORWDX2 DCREASON";
    public static final String ORWU_CLINICLOC = "ORWU CLINLOC";
    public static final String ORWDX_UNLOCK_ORDER = "ORWDX UNLOCK ORDER";
    public static final String ORWU_DT = "ORWU DT";
    public static final String ORWDXR_ISREL = "ORWDXR ISREL";
    public static final String ORWDXR_RNWFLDS = "ORWDXR RNWFLDS";
    public static final String ORWDPS2_MAXREF = "ORWDPS2 MAXREF";
    public static final String ORWDPS32_VALSCH = "ORWDPS32 VALSCH";

    public static final String ORQQCN_EDIT_DEFAULT_REASON = "ORQQCN EDIT DEFAULT REASON"; // Original RPC
    public static final String VIAB_EDIT_DEFAULT_REASON = "VIAB EDIT DEFAULT REASON"; // Replacement RPC

    public static final String ORWDXR01_CANCHG = "ORWDXR01 CANCHG";
    public static final String ORWD1_EXECUTE_SIGN_PRINTORDERS = "ORWD1 RVPRINT";
    public static final String ORWD1_EXECUTE_PRINTORDERS = "ORWD1 PRINTGUI";

    public static final String ORWPCE_CPTMODS = "ORWPCE CPTMODS"; // Original RPC
    public static final String VIAB_CPTMODS = "VIAB CPTMODS"; // Replacement RPC

    public static final String ORWDPS32_ISSUPPLY = "ORWDPS32 ISSPLY";
    public static final String ORWDPS32_ISVALID_QUANTITY = "ORWDPS32 VALQTY";
    public static final String ORWDRA32_APPROVAL = "ORWDRA32 APPROVAL";
    public static final String ORWDRA32_IMTYPSEL = "ORWDRA32 IMTYPSEL";
    public static final String ORWDRA32_RAORDITM = "ORWDRA32 RAORDITM";
    public static final String ORALWORD_ALLWORD = "ORALWORD ALLWORD";
    public static final String ORWDPS1_DOWSCH = "ORWDPS1 DOWSCH";
    public static final String ORWDPS5_LESGRP = "ORWDPS5 LESGRP";
    public static final String ORWDXC_ACCEPT = "ORWDXC ACCEPT";
    public static final String ORWOR_RESULT = "ORWOR RESULT";
    public static final String ORWOR_RESULT_HISTORY = "ORWOR RESULT HISTORY";
    public static final String ORWOR1_GETDSCH = "ORWOR1 GETDSCH";
    public static final String ORWORR_GETTXT = "ORWORR GETTXT";
    public static final String ORWDX_DGNM = "ORWDX DGNM";
    public static final String ORWDX_DLGDEF = "ORWDX DLGDEF";
    public static final String ORWDLR32_IMMED_COLLECT = "ORWDLR32 IMMED COLLECT";
    public static final String ORWDXR_ISCPLX = "ORWDXR ISCPLX";
    public static final String ORWDX_ORDITM = "ORWDX ORDITM";
    public static final String ORWDPS2_CHKPI = "ORWDPS2 CHKPI";
    public static final String ORWDXC_ON = "ORWDXC ON";
    public static final String ORIMO_IMOLOC = "ORIMO IMOLOC";
    public static final String ORWDX1_ORDMATCH = "ORWDX1 ORDMATCH";

    public static final String ORQOR_DETAIL = "ORQOR DETAIL";

    public static final String ORQQCN_ISCONSULT_FOR_PROSTHETICS = "ORQQCN ISPROSVC"; // Original RPC
    public static final String VIAB_ISPROSVC = "VIAB ISPROSVC"; // Replacement RPC

    public static final String ORQQCN_STATUS = "ORQQCN STATUS";
    public static final String ORQPT_PROVIDERS_BYSITE = "ORQPT PROVIDERS";

    public static final String ORWCIRN_FACLIST = "ORWCIRN FACLIST";
    public static final String ORWCV_DTLVST = "ORWCV DTLVST";
    public static final String ORWCV_VST = "ORWCV VST";
    public static final String ORWD2_DEVINFO = "ORWD2 DEVINFO";
    public static final String ORWDCN32_ORDRMSG = "ORWDCN32 ORDRMSG";
    public static final String ORWDPS32_AUTH = "ORWDPS32 AUTH";
    public static final String ORWDRA32_DEF = "ORWDRA32 DEF";
    public static final String ORWDRA32_LOCTYPE = "ORWDRA32 LOCTYPE";
    public static final String ORWDXA_ISACTOI = "ORWDXA ISACTOI";
    public static final String ORWDXM1_BLDQRSP = "ORWDXM1 BLDQRSP";
    public static final String ORWDX_LOADRSP = "ORWDX LOADRSP";
    public static final String ORWDX_LOCK = "ORWDX LOCK";
    public static final String ORWDX_SEND = "ORWDX SEND";
    public static final String ORWDX_UNLOCK = "ORWDX UNLOCK";
    public static final String ORWDX_WRLST = "ORWDX WRLST";
    public static final String ORWDXA_OFCPLX = "ORWDXA OFCPLX";
    public static final String ORWDXC_SESSION = "ORWDXC SESSION";
    public static final String ORWDXM_DLGNAME = "ORWDXM DLGNAME";
    public static final String ORWGRPC_ITEMDATA = "ORWGRPC ITEMDATA";
    public static final String ORWGRPC_ITEMS = "ORWGRPC ITEMS";
    public static final String ORWLRR_ALLTESTS = "ORWLRR ALLTESTS";
    public static final String ORWLRR_INFO = "ORWLRR INFO";
    public static final String ORWLRR_INTERIMG = "ORWLRR INTERIMG";
    public static final String ORWDPS1_DFLTSPLY = "ORWDPS1 DFLTSPLY";
    public static final String ORWDPS1_ODSLCT = "ORWDPS1 ODSLCT";
    public static final String ORRXN_NONVA_MEDS = "OR_RXN:HERBAL/OTC/NON-VA MEDS~NVA;ORDV06A;0;0";
    public static final String DODRXOP_DOD_MEDS = "OR_DODRXOP:OUTPATIENT MEDICATIONS;1~RXOP;ORDV06;28;10";
    public static final String ORWORDG_MAPSEQ = "ORWORDG MAPSEQ";
    public static final String ORWORDG_ALLTREE = "ORWORDG ALLTREE";
    public static final String ORWORDG_REVSTS = "ORWORDG REVSTS";
    public static final String ORWORR_AGET = "ORWORR AGET";
    public static final String ORWORR_GET4LST = "ORWORR GET4LST";
    public static final String ORWUL_FV4DG = "ORWUL FV4DG";
    public static final String ORWUL_FVIDX = "ORWUL FVIDX";

    public static final String ORWPCE_GET_HEALTH_FACTORS_TY = "ORWPCE GET HEALTH FACTORS TY"; // Original RPC
    public static final String VIABPCE2_GET_HFACTORS_TYPE = "VIABPCE2 GET HFACTORS TYPE"; // Replacement RPC

    public static final String ORWPCE4_LEX = "ORWPCE4 LEX"; // Original RPC
    public static final String VIABPCE4_LEX = "VIABPCE4 LEX"; // Replacement RPC

    public static final String ORWPCE_ACTPROB = "ORWPCE ACTPROB"; // Original RPC
    public static final String VIAB_ACTPROB = "VIAB ACTPROB"; // Replacement RPC

    public static final String ORWPCE_GET_IMMUNIZATION_TYPE = "ORWPCE GET IMMUNIZATION TYPE"; // Original RPC
    public static final String VIAB_GET_IMMUNIZATION_TYPE = "VIAB GET IMMUNIZATION TYPE"; // Replacement RPC

    public static final String ORWPCE_NOTEVSTR = "ORWPCE NOTEVSTR"; // Original RPC
    public static final String VIAB_NOTEVSTR = "VIAB NOTEVSTR"; // Replacement RPC

    public static final String ORWPCE_SAVE = "ORWPCE SAVE"; // Original RPC
    public static final String VIABPCE_SAVE = "VIABPCE SAVE"; // Replacement RPC

    public static final String PX_SAVE_DATA = "PX SAVE DATA";

    public static final String VIAB_TIU_SECVST = "VIAB TIU SECVST";

    public static final String ORWPCE_VISIT = "ORWPCE VISIT"; // Original RPC
    public static final String VIAB_VISIT = "VIAB VISIT"; // Replacement RPC

    public static final String ORWPCE_SCDIS = "ORWPCE SCDIS"; // Original RPC
    public static final String VIAB_SCDIS = "VIAB SCDIS"; // Replacement RPC

    public static final String ORWPCE_SCSEL = "ORWPCE SCSEL"; // Original RPC
    public static final String VIAB_SCSEL = "VIAB SCSEL"; // Replacement RPC

    public static final String ORWPT16_ID_INFO = "ORWPT16 ID INFO";
    public static final String ORWPT_ID_INFO = "ORWPT ID INFO";
    public static final String ORWPT_ADMITLST = "ORWPT ADMITLST";
    public static final String ORWPT_BYWARD = "ORWPT BYWARD";
    public static final String ORWPT_DECEASED_DATE = "ORWPT DIEDON";
    public static final String ORWPT_FULLSSN = "ORWPT FULLSSN";
    public static final String ORWPT_LAST5 = "ORWPT LAST5";
    public static final String ORWPT_LIST_ALL = "ORWPT LIST ALL";
    public static final String ORWPT_SELECT = "ORWPT SELECT";
    public static final String ORWRP_GET_DEFAULT_PRINTER = "ORWRP GET DEFAULT PRINTER";
    public static final String ORWRP_REPORT_TEXT = "ORWRP REPORT TEXT";
    public static final String ORWRP_REPORT_LISTS = "ORWRP REPORT LISTS";
    public static final String ORWDPS2_DAY2QTY = "ORWDPS2 DAY2QTY";
    public static final String ORWDPS2_QTY2DAY = "ORWDPS2 QTY2DAY";
    public static final String ORWDPS2_OISLCT = "ORWDPS2 OISLCT";
    public static final String ORWDXC_SAVECHK = "ORWDXC SAVECHK";
    public static final String ORWRP2_HS_COMPONENTS = "ORWRP2 HS COMPONENTS";
    public static final String ORWRP2_HS_REPORT_TEXT = "ORWRP2 HS REPORT TEXT";
    public static final String ORWDLR32_ALLSAMP = "ORWDLR32 ALLSAMP";

    public static final String ORWPS_MEDHIST = "ORWPS MEDHIST"; // Original RPC
    public static final String VIAB_MEDHIST = "VIAB MEDHIST"; // Replacement RPC

    public static final String ORWTIU_CHKTXT = "ORWTIU CHKTXT";
    public static final String ORWTPD_GETDFLT = "ORWTPD GETDFLT";
    public static final String ORWUL_FVSUB = "ORWUL FVSUB";

    public static final String ORDDPAPI_CLOZMSG = "ORDDPAPI CLOZMSG";

    public static final String OREVNTX1_CURSPE = "OREVNTX1 CURSPE"; // Original RPC
    public static final String VIAB_CURSPE = "VIAB CURSPE"; // Replacement RPC

    public static final String OREVNTX1_DLGIEN = "OREVNTX1 DLGIEN";
    public static final String ORWDPS1_FAILDEA = "ORWDPS1 FAILDEA";
    public static final String ORDEA_DEATEXT = "ORDEA DEATEXT";
    public static final String ORDEA_SIGINFO = "ORDEA SIGINFO";

    public static final String ORWDXA_DC = "ORWDXA DC";
    public static final String ORWDLR32_DEF = "ORWDLR32 DEF";
    public static final String ORWDLR32_LOAD = "ORWDLR32 LOAD";
    public static final String ORWDLR32_ALLSPEC = "ORWDLR32 ALLSPEC";
    public static final String ORWDXM_FORMID = "ORWDXM FORMID";
    public static final String ORWD2_MANUAL = "ORWD2 MANUAL";
    public static final String ORWOR_PKISITE = "ORWOR PKISITE";
    public static final String ORWDXR_GETPKG = "ORWDXR GETPKG";

    public static final String ORWU_EXTNAME = "ORWU EXTNAME"; // Original RPC
    public static final String VIABU_EXTNAME = "VIABU EXTNAME"; // Replacement RPC

    public static final String ORWU_DEVICE = "ORWU DEVICE";
    public static final String ORWU_INPLOC = "ORWU INPLOC";
    public static final String ORWU_NEWPERS = "ORWU NEWPERS";

    public static final String ORWU_NPHASKEY = "ORWU NPHASKEY";
    public static final String ORWU_USERINFO = "ORWU USERINFO";
    public static final String ORWU_VALIDSIG = "ORWU VALIDSIG";
    public static final String ORWU1_NEWLOC = "ORWU1 NEWLOC";

    public static final String ORWPT1_PRCARE = "ORWPT1 PRCARE";

    public static final String SC_LISTER = "SC LISTER";
    public static final String SC_GETS_ENTRY_DATA = "SC GETS ENTRY DATA";

    public static final String SD_PATIENT_ADMISSIONS = "SD PATIENT ADMISSIONS";

    public static final String TIU_AUTHORIZATION = "TIU AUTHORIZATION";
    public static final String TIU_CREATE_RECORD = "TIU CREATE RECORD";
    public static final String TIU_DETAILED_DISPLAY = "TIU DETAILED DISPLAY";
    public static final String TIU_DOCUMENTS_BY_CONTEXT = "TIU DOCUMENTS BY CONTEXT";
    public static final String TIU_GET_PRF_ACTIONS = "TIU GET PRF ACTIONS";
    public static final String TIU_GET_RECORD_TEXT = "TIU GET RECORD TEXT";
    public static final String TIU_GET_SURROGATE = "VIAB GETSURR";
    public static final String TIU_HAS_AUTHOR_SIGNED = "TIU HAS AUTHOR SIGNED?";
    public static final String TIU_LOCK_RECORD = "TIU LOCK RECORD";
    public static final String TIU_LONG_LIST_OF_TITLES = "TIU LONG LIST OF TITLES";
    public static final String TIU_IS_THIS_A_CONSULT = "TIU IS THIS A CONSULT?";
    public static final String TIU_ONE_VISIT_NOTE = "TIU ONE VISIT NOTE?";
    public static final String TIU_IS_THIS_A_SURGERY = "TIU IS THIS A SURGERY?";
    public static final String TIU_ISPRF = "TIU ISPRF";
    public static final String TIU_REQUIRES_COSIGNATURE = "TIU REQUIRES COSIGNATURE";
    public static final String TIU_SET_ADMINISTRATIVE_CLOSURE = "TIU SET ADMINISTRATIVE CLOSURE";
    public static final String TIU_SET_DOCUMENT_TEXT = "TIU SET DOCUMENT TEXT";
    public static final String TIU_SIGN_RECORD = "TIU SIGN RECORD";
    public static final String TIU_UNLOCK_RECORD = "TIU UNLOCK RECORD";
    public static final String TIU_UPDATE_RECORD = "TIU UPDATE RECORD";
    public static final String TIU_WAS_THIS_SAVED = "TIU WAS THIS SAVED?";
    public static final String TIU_WHICH_SIGNATURE_ACTION = "TIU WHICH SIGNATURE ACTION";
    public static final String TIU_CREATE_ADDENDUM_RECORD = "TIU CREATE ADDENDUM RECORD";
    public static final String TIU_UPDATE_ADDITIONAL_SIGNERS = "TIU UPDATE ADDITIONAL SIGNERS";
    public static final String TIU_TEMPLATE_GETBOIL = "TIU TEMPLATE GETBOIL";
    public static final String TIU_LINK_TO_FLAG = "TIU LINK TO FLAG";
    public static final String GET_TIU_DOC_BY_CONTEXT = "GET TIU DOC BY CONTEXT";

    public static final String VAFCTFU_CONVERT_ICN_TO_DFN = "VAFCTFU CONVERT ICN TO DFN";

    public static final String VIABDX_SAVE = "VIABDX SAVE";

    public static final String SDEC_APPSLOTS = "SDEC APPSLOTS";

    public static final String VIAB_PATCH = "VIAB PATCH";

    public static final String VIAB_GET_USER_DIVISIONS = "VIAB GET USER DIVISIONS";
    public static final String VPR_GET_PATIENT_DATA = "VPR GET PATIENT DATA";

    // Cannot use below in VistaLink. Need permission from Kernel Team
    public static final String XWB_CREATE_CONTEXT = "XWB CREATE CONTEXT";
    public static final String XUS_ESSO_VALIDATE = "XUS ESSO VALIDATE";
    public static final String XUS_AV_CODE = "XUS AV CODE";
    public static final String XUS_SIGNON_SETUP = "XUS SIGNON SETUP";

    public static final String XWB_IM_HERE = "XWB IM HERE";

    @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.AvoidReassigningParameters"})
    public static String buildMatchInput(String target) {

        if ((target == null) || (target.trim().length() < 2)) {
            throw new IllegalArgumentException("Invalid search criteria. Target length must be greater than 1: " + target);
        }

        // If first char is numeric, must be SSN
        if (CharUtils.isAsciiNumeric(target.charAt(0))) {

            for (int i = 0; i < target.length(); i++) {
                final char theChar = target.charAt(i);
                if (!Character.isDigit(theChar) && (theChar != '-')) {
                    throw new IllegalArgumentException("Invalid SSN");
                }
            }

            target = RPCStringUtils.removeNonNumericChars(target);

            if (target.length() == 9) {
                return ORWPT_FULLSSN;
            } else {
                throw new IllegalArgumentException("Invalid SSN");
            }
        }

        // First char was not numeric.  Make sure it's alpha.
        target = target.toUpperCase(Locale.ENGLISH).trim();
        if (!CharUtils.isAsciiAlpha(target.charAt(0))) {
            throw new IllegalArgumentException("Invalid search criteria. Target must start with with an alpha character: " + target);
        }

        // First char was alpha.  If second char is numeric, must be last5.
        if (CharUtils.isAsciiNumeric(target.charAt(1))) {
            if (target.length() != 5) {
                throw new IllegalArgumentException("Invalid last 5 identifier: " + target);
            }
            for (int i = 2; i < 5; i++) {
                if (!CharUtils.isAsciiNumeric(target.charAt(i))) {
                    throw new IllegalArgumentException("Invalid last 5 identifier: " + target);
                }
            }
            return "ORWPT LAST5";
        }

        // Second char was not numeric.  Assume it's a preferredTerm.
        if (!isValidName(target)) {
            throw new IllegalArgumentException("Invalid person name: " + target);
        }
        return "ORWPT LIST ALL";
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static boolean isValidName(final String str) {
        if (StringUtils.isEmpty(str) || !CharUtils.isAsciiAlpha(str.charAt(0))) {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            if (!CharUtils.isAsciiAlpha(str.charAt(i))
                    && (str.charAt(i) != ' ')
                    && (str.charAt(i) != '\'')
                    && (str.charAt(i) != '-')
                    && (str.charAt(i) != ',')
                    && (str.charAt(i) != '.')) {
                return false;
            }
        }
        return true;
    }
}