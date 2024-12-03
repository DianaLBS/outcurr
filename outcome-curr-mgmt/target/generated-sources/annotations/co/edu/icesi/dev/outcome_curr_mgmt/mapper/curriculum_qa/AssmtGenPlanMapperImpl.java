package co.edu.icesi.dev.outcome_curr_mgmt.mapper.curriculum_qa;

import co.edu.icesi.dev.outcome_curr.mgmt.model.stdindto.curriculum_qa.AssmtGenPlanInDTO;
import co.edu.icesi.dev.outcome_curr.mgmt.model.stdoutdto.curriculum_qa.AssmtGenPlanOutDTO;
import co.edu.icesi.dev.outcome_curr_mgmt.model.entity.curriculum_qa.AssmtGenPlan;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-02T22:10:05-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class AssmtGenPlanMapperImpl implements AssmtGenPlanMapper {

    @Override
    public AssmtGenPlan assmtGenPlanInDTOToAssmtGenPlan(AssmtGenPlanInDTO assmtGenPlanInDTO) {
        if ( assmtGenPlanInDTO == null ) {
            return null;
        }

        AssmtGenPlan.AssmtGenPlanBuilder assmtGenPlan = AssmtGenPlan.builder();

        return assmtGenPlan.build();
    }

    @Override
    public AssmtGenPlanOutDTO assmtGenPlanToAssmtGenPlanOutDTO(AssmtGenPlan assmtGenPlan) {
        if ( assmtGenPlan == null ) {
            return null;
        }

        AssmtGenPlanOutDTO.AssmtGenPlanOutDTOBuilder assmtGenPlanOutDTO = AssmtGenPlanOutDTO.builder();

        assmtGenPlanOutDTO.asgplaId( String.valueOf( assmtGenPlan.getAsgplaId() ) );
        assmtGenPlanOutDTO.asgplaStatus( assmtGenPlan.getAsgplaStatus() );

        return assmtGenPlanOutDTO.build();
    }

    @Override
    public List<AssmtGenPlanOutDTO> assmtGenPlansToAssmtGenPlanOutDTOs(List<AssmtGenPlan> assmtGenPlan) {
        if ( assmtGenPlan == null ) {
            return null;
        }

        List<AssmtGenPlanOutDTO> list = new ArrayList<AssmtGenPlanOutDTO>( assmtGenPlan.size() );
        for ( AssmtGenPlan assmtGenPlan1 : assmtGenPlan ) {
            list.add( assmtGenPlanToAssmtGenPlanOutDTO( assmtGenPlan1 ) );
        }

        return list;
    }

    @Override
    public void updateAssmtGenPlan(AssmtGenPlanInDTO assmtGenPlanInDTO, AssmtGenPlan assmtGenPlan) {
        if ( assmtGenPlanInDTO == null ) {
            return;
        }
    }
}
