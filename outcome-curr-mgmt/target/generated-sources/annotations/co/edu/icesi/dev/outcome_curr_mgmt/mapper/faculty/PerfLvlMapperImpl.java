package co.edu.icesi.dev.outcome_curr_mgmt.mapper.faculty;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.curriculum_qa.PerfLvlInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.curriculum_qa.PerfLvlOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.faculty.PerfLvl;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-02T17:16:10-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class PerfLvlMapperImpl implements PerfLvlMapper {

    @Override
    public PerfLvl fromPerfLvlInDTO(PerfLvlInDTO perfLvlInDTO) {
        if ( perfLvlInDTO == null ) {
            return null;
        }

        PerfLvl.PerfLvlBuilder perfLvl = PerfLvl.builder();

        perfLvl.plIsActive( perfLvlInDTO.plIsActive() );
        perfLvl.plNameEng( perfLvlInDTO.plNameEng() );
        perfLvl.plNameSpa( perfLvlInDTO.plNameSpa() );
        perfLvl.plOrder( perfLvlInDTO.plOrder() );

        return perfLvl.build();
    }

    @Override
    public PerfLvlOutDTO fromPerfLvl(PerfLvl perfLvl) {
        if ( perfLvl == null ) {
            return null;
        }

        PerfLvlOutDTO.PerfLvlOutDTOBuilder perfLvlOutDTO = PerfLvlOutDTO.builder();

        perfLvlOutDTO.plId( perfLvl.getPlId() );
        perfLvlOutDTO.plIsActive( perfLvl.getPlIsActive() );
        perfLvlOutDTO.plNameEng( perfLvl.getPlNameEng() );
        perfLvlOutDTO.plNameSpa( perfLvl.getPlNameSpa() );
        perfLvlOutDTO.plOrder( perfLvl.getPlOrder() );

        perfLvlOutDTO.acadProgramId( perfLvl.getAcadProgram().getAcpId() );

        return perfLvlOutDTO.build();
    }
}
