package co.edu.icesi.dev.outcome_curr_mgmt.mapper.audit;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.audit.ChangeLogOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.audit.Changelog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T02:19:15-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class ChangeLogMapperImpl implements ChangeLogMapper {

    private final DatatypeFactory datatypeFactory;

    public ChangeLogMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public ChangeLogOutDTO fromChangeLog(Changelog changelog) {
        if ( changelog == null ) {
            return null;
        }

        String clogTimestamp = null;
        long clogId = 0L;
        String clogAction = null;
        String clogAffectedRecordId = null;
        String clogAffectedTable = null;
        String clogLogNewVal = null;
        String clogLogOldVal = null;

        clogTimestamp = xmlGregorianCalendarToString( dateToXmlGregorianCalendar( changelog.getClogTimestamp() ), "dd/MM/yyyy HH:mm" );
        clogId = changelog.getClogId();
        clogAction = changelog.getClogAction();
        clogAffectedRecordId = changelog.getClogAffectedRecordId();
        clogAffectedTable = changelog.getClogAffectedTable();
        clogLogNewVal = changelog.getClogLogNewVal();
        clogLogOldVal = changelog.getClogLogOldVal();

        String user = changelog.getUser().getUsrName();

        ChangeLogOutDTO changeLogOutDTO = new ChangeLogOutDTO( clogId, clogAction, clogAffectedRecordId, clogAffectedTable, clogLogNewVal, clogLogOldVal, clogTimestamp, user );

        return changeLogOutDTO;
    }

    private String xmlGregorianCalendarToString( XMLGregorianCalendar xcal, String dateFormat ) {
        if ( xcal == null ) {
            return null;
        }

        if (dateFormat == null ) {
            return xcal.toString();
        }
        else {
            Date d = xcal.toGregorianCalendar().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
            return sdf.format( d );
        }
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }
}
