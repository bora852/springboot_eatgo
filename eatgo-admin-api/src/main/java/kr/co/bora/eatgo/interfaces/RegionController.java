package kr.co.bora.eatgo.interfaces;

import com.sun.media.sound.DLSInstrument;
import kr.co.bora.eatgo.application.RegionService;
import kr.co.bora.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regions")
    public List<Region> list() {
        List<Region> regions = regionService.getRegions();

        return regions;
    }

    @PostMapping("/regions")
    public ResponseEntity<?> create() throws URISyntaxException {

        String url = "/regions/1";
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
