package co.edu.icesi.dev.outcome_curr_mgmt.mapper.faculty;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.faculty.AcadProgramInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.faculty.AcadProgramOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.faculty.AcadProgram;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T02:19:24-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class AcadProgramMapperImpl implements AcadProgramMapper {

    @Override
    public void updateAcadProgram(AcadProgramInDTO acadProgramInDTO, AcadProgram acadProgram) {
        if ( acadProgramInDTO == null ) {
            return;
        }

        acadProgram.setAcpId( acadProgramInDTO.acpId() );
        acadProgram.setAcpIsActive( acadProgramInDTO.acpIsActive() );
        acadProgram.setAcpProgDescEng( acadProgramInDTO.acpProgDescEng() );
        acadProgram.setAcpProgDescSpa( acadProgramInDTO.acpProgDescSpa() );
        acadProgram.setAcpProgNameEng( acadProgramInDTO.acpProgNameEng() );
        acadProgram.setAcpProgNameSpa( acadProgramInDTO.acpProgNameSpa() );
        acadProgram.setAcpSnies( acadProgramInDTO.acpSnies() );
    }

    @Override
    public AcadProgram acadProgramInDTOToAcadProgram(AcadProgramInDTO acadProgramInDTO) {
        if ( acadProgramInDTO == null ) {
            return null;
        }

        AcadProgram.AcadProgramBuilder acadProgram = AcadProgram.builder();

        acadProgram.acpId( acadProgramInDTO.acpId() );
        acadProgram.acpIsActive( acadProgramInDTO.acpIsActive() );
        acadProgram.acpProgDescEng( acadProgramInDTO.acpProgDescEng() );
        acadProgram.acpProgDescSpa( acadProgramInDTO.acpProgDescSpa() );
        acadProgram.acpProgNameEng( acadProgramInDTO.acpProgNameEng() );
        acadProgram.acpProgNameSpa( acadProgramInDTO.acpProgNameSpa() );
        acadProgram.acpSnies( acadProgramInDTO.acpSnies() );

        return acadProgram.build();
    }

    @Override
    public AcadProgramOutDTO acadProgramToAcadProgramOutDto(AcadProgram acadProgramOutDTO) {
        if ( acadProgramOutDTO == null ) {
            return null;
        }

        AcadProgramOutDTO.AcadProgramOutDTOBuilder acadProgramOutDTO1 = AcadProgramOutDTO.builder();

        acadProgramOutDTO1.acpId( acadProgramOutDTO.getAcpId() );
        acadProgramOutDTO1.acpIsActive( acadProgramOutDTO.getAcpIsActive() );
        acadProgramOutDTO1.acpProgDescEng( acadProgramOutDTO.getAcpProgDescEng() );
        acadProgramOutDTO1.acpProgDescSpa( acadProgramOutDTO.getAcpProgDescSpa() );
        acadProgramOutDTO1.acpProgNameEng( acadProgramOutDTO.getAcpProgNameEng() );
        acadProgramOutDTO1.acpProgNameSpa( acadProgramOutDTO.getAcpProgNameSpa() );
        acadProgramOutDTO1.acpSnies( acadProgramOutDTO.getAcpSnies() );

        return acadProgramOutDTO1.build();
    }

    @Override
    public List<AcadProgramOutDTO> acadProgramsToAcadProgramOutDtos(List<AcadProgram> acadProgramOutDTO) {
        if ( acadProgramOutDTO == null ) {
            return null;
        }

        List<AcadProgramOutDTO> list = new ArrayList<AcadProgramOutDTO>( acadProgramOutDTO.size() );
        for ( AcadProgram acadProgram : acadProgramOutDTO ) {
            list.add( acadProgramToAcadProgramOutDto( acadProgram ) );
        }

        return list;
    }
}
