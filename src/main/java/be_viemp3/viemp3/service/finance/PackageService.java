package be_viemp3.viemp3.service.finance;

import be_viemp3.viemp3.common.service.EntityQueryService;
import be_viemp3.viemp3.dto.request.finance.packages.CreatePackageRequest;
import be_viemp3.viemp3.dto.request.finance.packages.UpdatePackageRequest;
import be_viemp3.viemp3.dto.response.finance.DurationTypeResponse;
import be_viemp3.viemp3.dto.response.finance.PackageResponse;
import be_viemp3.viemp3.dto.response.finance.PackageTypeResponse;
import be_viemp3.viemp3.entity.Packages;
import be_viemp3.viemp3.mapper.enums.DurationTypeMapper;
import be_viemp3.viemp3.mapper.enums.PackageTypeMapper;
import be_viemp3.viemp3.mapper.finance.PackageMapper;
import be_viemp3.viemp3.repository.finance.PackageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService {
    private final PackageRepository packageRepository;
    private final EntityQueryService entityService;

    private Double calculateFinalPrice(Double basePrice, int months, Double discountPercent) {
        double totalBeforeDiscount = basePrice * months;
        return totalBeforeDiscount * (1 - (discountPercent / 100));
    }

    @Transactional
    public PackageResponse createPackage(CreatePackageRequest request) {
        Double discount = (request.getDiscountPercent() != null) ? request.getDiscountPercent() : 0.0;

        Packages pkg = new Packages();
        pkg.setPkg(request.getType());
        pkg.setDuration(request.getDuration());
        pkg.setBasePrice(request.getBasePrice());
        pkg.setDiscountPercent(discount);
        pkg.setFinalPrice(calculateFinalPrice(
                request.getBasePrice(),
                request.getDuration().getMonths(),
                request.getDiscountPercent()
        ));

        return PackageMapper.toResponse(packageRepository.save(pkg));
    }

    @Transactional
    public PackageResponse updatePackage(String id, UpdatePackageRequest request) {
        Packages pkg = entityService.findPackageById(id);

        if (request.getType() != null) pkg.setPkg(request.getType());
        if (request.getDuration() != null) pkg.setDuration(request.getDuration());
        if (request.getBasePrice() != null) pkg.setBasePrice(request.getBasePrice());
        if (request.getDiscountPercent() != null) pkg.setDiscountPercent(request.getDiscountPercent());

        // Sau khi update các trường, tính toán lại finalPrice
        pkg.setFinalPrice(calculateFinalPrice(
                pkg.getBasePrice(),
                pkg.getDuration().getMonths(),
                pkg.getDiscountPercent()
        ));

        return PackageMapper.toResponse(packageRepository.save(pkg));
    }

    public void deletePackage(String id) {
        packageRepository.deleteById(id);
    }

    public List<PackageResponse> getAllPackages() {
        return PackageMapper.toResponseList(packageRepository.findAll());
    }

    public PackageResponse getPackageById(String id) {
        Packages pkg = entityService.findPackageById(id);
        return PackageMapper.toResponse(pkg);
    }

    public List<PackageTypeResponse> getAllPackageTypes() {
        return PackageTypeMapper.toResponseList();
    }

    public List<DurationTypeResponse> getAllDurationTypes() {
        return DurationTypeMapper.toResponseList();
    }
}
