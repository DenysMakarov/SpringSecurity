package youtube.springsecurity.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import youtube.springsecurity.model.Developer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestController {

    private List<Developer> DEVELOPERS = Stream.of(
            new Developer(1000L,"John", "Johnson"),
            new Developer(2000L, "Peter", "Jackson"),
            new Developer(3000L, "Sam", "Samson")
    ).collect(Collectors.toList());

    @GetMapping
    public List<Developer> getAll() {
        return DEVELOPERS;
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('developer:read')")
    public Developer getById(@PathVariable Long id) {
        return DEVELOPERS.stream().filter(d -> d.getId().equals(id))
                .findFirst().orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('developer:write')")
    public Developer add(@RequestBody Developer developer){
        this.DEVELOPERS.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('developer:write')")
    public void remove(@PathVariable Long id){
        this.DEVELOPERS.removeIf(d -> d.getId().equals(id));
    }

}
