package co.edu.icesi.dev.outcome_curr_mgmt.mapper.curriculum_qa;

import co.edu.icesi.dev.outcome_curr.mgmt.model.curriculum_qa.ValueDTO;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
    date = "2024-12-03T03:43:20-0500",
=======
    date = "2024-12-03T02:19:19-0500",
>>>>>>> 34859dd2f40d4816f89d6612e1fc3ade8fcc4d72
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.40.0.z20241112-1021, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class CurrMapMapperImpl implements CurrMapMapper {

    @Override
    public ValueDTO fromMapElementToValueDTO(Map.Entry<String, String> entry) {
        if ( entry == null ) {
            return null;
        }

        ValueDTO.ValueDTOBuilder valueDTO = ValueDTO.builder();

        valueDTO.key( entry.getKey() );
        valueDTO.value( entry.getValue() );

        return valueDTO.build();
    }
}
