package kr.co.bora.eatgo.application;

import kr.co.bora.eatgo.domain.Region;
import kr.co.bora.eatgo.domain.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {
        List<Region> regions = regionRepository.findAll();
        regions.add(Region.builder().name("Seoul").build());
        return regions;
    }
}
