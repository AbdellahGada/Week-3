package comp74.week2.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comp74.week2.model.Model;
import comp74.week2.model.Posting;
import comp74.week2.model.Profile;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class MainController {

    Model model;

    public MainController(Model model) {
        this.model = model;
    }

    @GetMapping("/profiles")
    public List<Profile> getProfileByUserName(
            @RequestParam(name = "userName", required = false, defaultValue = "") String userName) {
        System.out.println("Username: " + userName);
        if (userName.isEmpty())
            return model.getProfiles();
        else
            return model.getProfilesByUserName(userName);
    }

    @GetMapping("/profiles/{id}")
    public Profile getProfileById(@PathVariable Integer id) {
        return model.getProfileById(id);
    }

    @GetMapping("/profiles/{id}/postings")
    public List<Posting> getProfilePostings(@PathVariable Integer id) {
        List<Posting> postings = null;
        Profile profile = model.getProfileById(id);
        if (profile != null)
            postings = profile.getPostings();
        return postings;
    }
    @PostMapping("/profiles")
    public Profile postMethodName(@RequestBody Profile profile) {
        Profile newProfile = model.addProfile(profile);
        return newProfile;
    }

    @PostMapping("/profiles/{profileId}/postings")
    public Posting addPostingToProfile(@PathVariable Integer profileId, @RequestBody Posting posting) {
        Profile profile = model.getProfileById(profileId);
        if (profile != null) {
            Posting newPosting = model.addPosting(profile, posting);
            return newPosting;
        } else {
            return null;
        }
    }
    @PostMapping("/profiles/postings")
    public Posting addPostingByUsername(@RequestParam String userName, @RequestBody Posting posting) {
        Profile profile = model.getProfilesByUserName(userName).get(0);
        if (profile != null) {
            Posting newPosting = model.addPosting(profile, posting);
            return newPosting;
        } else {
            // Handle profile not found
            return null;
        }
    }
}
