package co.edu.icesi.dev.outcome_curr_mgmt.mapper.management;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.management.AcadPeriodInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.management.AcadPeriodOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.management.AcPeriod;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-02T17:16:10-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class AcademicPeriodMapperImpl implements AcademicPeriodMapper {

    @Override
    public AcPeriod fromAcadPeriodInDTO(AcadPeriodInDTO sourceAcadPeriodInDTO) {
        if ( sourceAcadPeriodInDTO == null ) {
            return null;
        }

        AcPeriod.AcPeriodBuilder acPeriod = AcPeriod.builder();

        acPeriod.acPeriodNameEng( sourceAcadPeriodInDTO.acPeriodNameEng() );
        acPeriod.acPeriodNameSpa( sourceAcadPeriodInDTO.acPeriodNameSpa() );
        acPeriod.acPeriodNumeric( sourceAcadPeriodInDTO.acPeriodNumeric() );

        return acPeriod.build();
    }

    @Override
    public AcadPeriodOutDTO fromAcadPeriod(AcPeriod sourceAcademicPeriod) {
        if ( sourceAcademicPeriod == null ) {
            return null;
        }

        AcadPeriodOutDTO.AcadPeriodOutDTOBuilder acadPeriodOutDTO = AcadPeriodOutDTO.builder();

        acadPeriodOutDTO.acPeriodId( sourceAcademicPeriod.getAcPeriodId() );
        acadPeriodOutDTO.acPeriodNameEng( sourceAcademicPeriod.getAcPeriodNameEng() );
        acadPeriodOutDTO.acPeriodNameSpa( sourceAcademicPeriod.getAcPeriodNameSpa() );
        acadPeriodOutDTO.acPeriodNumeric( sourceAcademicPeriod.getAcPeriodNumeric() );

        return acadPeriodOutDTO.build();
    }
}
