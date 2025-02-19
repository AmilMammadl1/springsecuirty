package com.amiltech.rentacar.spring_security.mapper;

import com.amiltech.rentacar.spring_security.dto.request.UserRequestDTO;
import com.amiltech.rentacar.spring_security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "isActive", constant = "false")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User map(UserRequestDTO userRequestDto);
}
