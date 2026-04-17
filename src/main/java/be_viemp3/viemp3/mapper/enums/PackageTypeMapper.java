package be_viemp3.viemp3.mapper.enums;

import be_viemp3.viemp3.dto.response.finance.PackageTypeResponse;
import be_viemp3.viemp3.enums.PackageType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PackageTypeMapper {

    public static PackageTypeResponse toResponse(PackageType type) {
        if (type == null) {
            return null;
        }

        return PackageTypeResponse.builder()
                .value(type.name())
                .build();
    }

    public static List<PackageTypeResponse> toResponseList() {
        return Arrays.stream(PackageType.values())
                .map(PackageTypeMapper::toResponse)
                .collect(Collectors.toList());
    }
}