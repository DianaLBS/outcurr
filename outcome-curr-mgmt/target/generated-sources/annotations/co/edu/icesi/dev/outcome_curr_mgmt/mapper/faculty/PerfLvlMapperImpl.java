package co.edu.icesi.dev.outcome_curr_mgmt.mapper.faculty;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.curriculum_qa.PerfLvlInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.curriculum_qa.PerfLvlOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.faculty.PerfLvl;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2024-12-03T03:43:20-0500",
=======
    date = "2024-12-03T02:19:24-0500",
>>>>>>> 34859dd2f40d4816f89d6612e1fc3ade8fcc4d72
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
