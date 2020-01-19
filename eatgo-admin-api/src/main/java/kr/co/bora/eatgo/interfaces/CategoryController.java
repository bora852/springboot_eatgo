package kr.co.bora.eatgo.interfaces;

import com.sun.media.sound.DLSInstrument;
import kr.co.bora.eatgo.application.CategoryService;
import kr.co.bora.eatgo.application.RegionService;
import kr.co.bora.eatgo.domain.Category;
import kr.co.bora.eatgo.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list() {
        List<Category> regions = categoryService.getRegions();

        return regions;
    }

    @PostMapping("/categories")
    public ResponseEntity<?> create(
            @RequestBody Region resource
    ) throws URISyntaxException {
        Region region = regionService.addRegion(resource.getName());

        String url = "/regions/" + region.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
